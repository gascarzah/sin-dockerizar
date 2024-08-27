package net.gafah.companies_crud.repositories;

import net.gafah.companies_crud.entities.Company;
import net.gafah.companies_crud.entities.WebSite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WebSiteRepository extends JpaRepository<WebSite, Long> {


}
