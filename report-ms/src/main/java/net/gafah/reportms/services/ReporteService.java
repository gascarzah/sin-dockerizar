package net.gafah.reportms.services;

public interface ReporteService {
    String makeReport(String name);
    String saveReport(String report);
    void deleteReport(String name);
}
