package net.gafah.companies_crud.listeners;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gafah.companies_crud.entities.Company;
import net.gafah.companies_crud.services.CompanyService;

@Slf4j
@AllArgsConstructor
@Component
public class CompanyCbListener {

	private final CompanyService companyService;
	private final ObjectMapper objectMapper;
	
	@KafkaListener(topics = "consumerCbReport", groupId = "circuitbreaker")
	public void insertMsgEvent(String companyEvent) throws JsonProcessingException {
		log.info("Received event circuitbreaker {}",companyEvent);
		
		var company = objectMapper.readValue(companyEvent, Company.class);
		
		companyService.create(company);
	}
}
