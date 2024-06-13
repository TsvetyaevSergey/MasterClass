package com.example.Masterclass.models;


import jakarta.persistence.*;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Set;

@Entity
@Table(name = "service")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    @Column(unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private int price;

    @ManyToMany(mappedBy = "services")
    private Set<MasterClass> masterClasses;

    @Column(columnDefinition = "TEXT")
    private String image_url;

    public Service() {
    }

    public Service(String description, int price, String name, String image_url) {
        this.description = description;
        this.price = price;
        this.name = name;
        this.image_url = image_url;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Set<MasterClass> getMasterClasses() {
        return masterClasses;
    }

    public void setMasterClasses(Set<MasterClass> masterClasses) {
        this.masterClasses = masterClasses;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
