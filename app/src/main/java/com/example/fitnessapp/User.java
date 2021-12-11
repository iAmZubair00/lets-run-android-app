package com.example.fitnessapp;

public class User {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private boolean isMale;


    // Setter and Getter for id
    public void setId(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    // Setter and Getter for Comment
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }


    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPhone() {
        return phone;
    }

    public void setIsMale(boolean isMale) {
        this.isMale = isMale;
    }
    public boolean getIsMale() {
        return isMale;
    }

}

