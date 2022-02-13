package com.softtech.softtechspringboot.learning.dto;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
public class UserDto {

    private Long id;
    private String name;
    private String surname;
    private String password;
    private String role;
    private String email;

    public UserDto() {
    }

    public UserDto(Long id, String name, String surname, String password, String role, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.role = role;
        this.email = email;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
