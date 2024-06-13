package com.example.Masterclass.models;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Set;

@Entity
@Table(name = "masterClass")
public class MasterClass {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    @Column(unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String wishes;

    @Column
    private int totalPrice = 0;

    @ManyToMany
    @JoinTable(
            name = "master_class_dish",
            joinColumns = @JoinColumn(name = "master_class_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id")
    )
    private Set<Dish> dishes;

    @ManyToMany
    @JoinTable(
            name = "master_class_services",
            joinColumns = @JoinColumn(name = "master_class_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private Set<Service> services;

    @ManyToOne
    @JoinColumn(name = "master_id")
    private Master master;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDTO userDTO;

    @Column(columnDefinition = "TEXT")
    private String image_url;

    public MasterClass(Master master, Set<Service> services, Set<Dish> dishes, String wishes, String name, UserDTO userDTO) {
        this.master = master;
        this.services = services;
        this.dishes = dishes;
        for (Dish dish : dishes)
        {
            totalPrice += dish.getPrice();
        }
        for (Service service : services)
        {
            totalPrice += service.getPrice();
        }
        this.totalPrice += master.getPrice();
        this.wishes = wishes;
        this.name = name;
        this.userDTO = userDTO;
        this.image_url = master.getImage_url();
    }


    public MasterClass() {
    }

    public int reCalculatePrice(){
        for (Dish dish : dishes)
        {
            totalPrice += dish.getPrice();
        }
        for (Service service : services)
        {
            totalPrice += service.getPrice();
        }
        this.totalPrice += master.getPrice();
        return totalPrice;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public Master getMaster() {
        return master;
    }

    public void setMaster(Master master) {
        this.master = master;
    }

    public Set<Service> getServices() {
        return services;
    }

    public void setServices(Set<Service> services) {
        this.services = services;
    }

    public Set<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(Set<Dish> dishes) {
        this.dishes = dishes;
    }

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

    public String getWishes() {
        return wishes;
    }

    public void setWishes(String wishes) {
        this.wishes = wishes;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
