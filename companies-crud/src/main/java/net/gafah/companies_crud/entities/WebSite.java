package net.gafah.companies_crud.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
public class WebSite implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(columnDefinition = "category")
    @Enumerated(EnumType.STRING)
    private Category category;
    private String description;

}
