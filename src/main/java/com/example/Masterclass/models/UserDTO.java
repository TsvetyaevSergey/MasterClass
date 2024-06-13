package com.example.Masterclass.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class UserDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @NotEmpty
    @Column(unique = true)
    private String name;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String roles;

    @NotNull
    @NotEmpty
    @Column(unique = true)
    private String email;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private List<MasterClass> masterClasses;

    public UserDTO(String name, String password, String email, String roles) {
        this.name = name;
        this.password = password;
        this.email = email;

        switch (roles) {
            case "admin":
                this.roles = "ROLE_ADMIN";
                break;
            case "user":
                this.roles = "ROLE_USER";
                break;
            default:
                throw new IllegalArgumentException("Неизвестная роль: " + roles);
        }
    }

    public List<MasterClass> getMasterClasses() {
        return masterClasses;
    }

    public void setMasterClasses(List<MasterClass> masterClasses) {
        this.masterClasses = masterClasses;
    }

    public UserDTO() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
