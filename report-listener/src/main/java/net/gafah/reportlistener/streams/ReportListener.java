package net.gafah.reportlistener.streams;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gafah.reportlistener.documents.ReportDocument;
import net.gafah.reportlistener.repositories.ReportRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
@AllArgsConstructor
public class ReportListener {

    private final ReportRepository reportRepository;

    @Bean
    public Consumer<String> consumerReport() {
        return report -> {

            log.info("Saving report: {}", report);
            this.reportRepository.save(ReportDocument.builder().content(report).build());
        };
    }
}