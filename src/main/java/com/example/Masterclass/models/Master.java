package com.example.Masterclass.models;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

@Entity
@Table(name = "master")
public class Master {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    @Column(unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String achievements;

    @Column
    private int price;

    @Column(columnDefinition = "TEXT")
    private String image_url;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "master_id",referencedColumnName = "id")
    private List<MasterClass> masterClasses;

    public Master() {
    }

    public Master(String name, String achievements, String description, int price, String image_url) {
        this.name = name;
        this.achievements = achievements;
        this.description = description;
        this.price = price;
        this.image_url=image_url;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public List<MasterClass> getMasterClasses() {
        return masterClasses;
    }

    public void setMasterClasses(List<MasterClass> masterClasses) {
        this.masterClasses = masterClasses;
    }

    public String getAchievements() {
        return achievements;
    }

    public void setAchievements(String achievements) {
        this.achievements = achievements;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
