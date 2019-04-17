package com.htp.domain;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.Serializable;

@Component("employee")
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    private int employee_id;
    private String name;
    private String surname;
    private String email;
    private String login;
    private String password;
    private String specialty;
    private String role;
    private String employeeSector_id;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Employee() {
    }

    public Employee(int employee_id, String name, String surname, String email, String login, String password, String specialty, String role, String employeeSector_id) {
        this.employee_id = employee_id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.login = login;
        this.password = password;
        this.specialty = specialty;
        this.role = role;
        this.employeeSector_id = employeeSector_id;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmployeeSector_id() {
        return employeeSector_id;
    }

    public void setEmployeeSector_id(String employeeSector_id) {
        this.employeeSector_id = employeeSector_id;
    }

    @PostConstruct
    public void init() {
        System.out.println("Init method!");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Destroy!");
    }
}
