package net.gafah.reportms.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gafah.reportms.helpers.ReportHelper;
import net.gafah.reportms.model.Company;
import net.gafah.reportms.model.WebSite;
import net.gafah.reportms.repositories.CompaniesFallbackRepository;
import net.gafah.reportms.repositories.CompaniesRepository;
import net.gafah.reportms.streams.ReportPublisher;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReporteService{

    private final CompaniesRepository companiesRepository;
    private final ReportHelper reportHelper;
    private final CompaniesFallbackRepository companiesFallbackRepository;
    private final Resilience4JCircuitBreakerFactory circuitBreakerFactory;
    private final ReportPublisher reportPublisher;
    private final ObjectMapper objectMapper;
//    @Override
//    public String makeReport(String name) {
//       return reportHelper.readTemplate(this.companiesRepository.getByName(name).orElseThrow());
//    }

    @Override
    public String makeReport(String name) {
        var circuitBreaker = this.circuitBreakerFactory.create("companies-circuitbreaker");
        return circuitBreaker.run(
                () -> this.makeReportMain(name),
                throwable -> this.makeReportFallback(name, throwable)
        );
    }


    @Override
    public String saveReport(String report) {
        var format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        var placeholders = this.reportHelper.getPlaceholdersFromTemplate(report);
        var circuitBreaker = circuitBreakerFactory.create("companies-circuitbreaker-event");//ultimo agregado
        var webSites = Stream.of(placeholders.get(3))
                .map(website -> WebSite.builder().name(website).build())
                .toList();

        var company = Company.builder()
                .name(placeholders.get(0))
                .foundationDate(LocalDate.parse(placeholders.get(1), format))
                .founder(placeholders.get(2))
                .webSites(webSites)
                .build();

        reportPublisher.publishReport(report);
        
//        companiesRepository.postByName(company),
        
        //implementado con CB
         circuitBreaker.run(
                () -> companiesRepository.postByName(company),
                throwable -> reportPublisher.publishCbReport(buildEventMsg(company))
        );
        
        
        
        
        return "Saved";
    }

    @Override
    public void deleteReport(String name) {
        this.companiesRepository.deleteByName(name);
    }

    private String makeReportMain(String name) {
        return reportHelper.readTemplate(this.companiesRepository.getByName(name).orElseThrow());
    }

    private String makeReportFallback(String name, Throwable error) {
        log.warn(error.getMessage());
        return reportHelper.readTemplate(this.companiesFallbackRepository.getByName(name));
    }
    
    private String buildEventMsg(Company company) {
    	
    	try {
			return objectMapper.writeValueAsString(company);
		} catch (JsonProcessingException e) {
			throw new RuntimeException();
		}
    }
}
