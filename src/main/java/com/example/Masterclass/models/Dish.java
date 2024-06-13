package com.example.Masterclass.models;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Set;

@Entity
@Table(name = "dish")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    @Column(unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String ingredients;

    @Column
    private int price;

    @Column
    private String category;

    @Column(columnDefinition = "TEXT")
    private String image_url;

    @ManyToMany(mappedBy = "dishes")
    private Set<MasterClass> masterClasses;

    public Dish() {
    }

    public Dish(String name, String description, String ingredients, int price, String category, String image_url) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.price = price;
        this.category = category;
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
// Геттеры и сеттеры для всех полей

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
