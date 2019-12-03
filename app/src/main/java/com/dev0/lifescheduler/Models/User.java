package com.dev0.lifescheduler.Models;

public class User {
    private String email, pass, name, phone;

    public User(String email, String pass, String name, String phone) {
        this.email = email;
        this.pass = pass;
        this.name = name;
        this.phone = phone;
    }

    public User() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
