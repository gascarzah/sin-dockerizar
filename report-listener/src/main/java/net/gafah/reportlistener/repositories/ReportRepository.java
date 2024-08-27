package net.gafah.reportlistener.repositories;


import net.gafah.reportlistener.documents.ReportDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReportRepository extends MongoRepository<ReportDocument, String> {
}
