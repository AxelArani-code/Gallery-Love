package com.example.gallerylove.model;

public class User {
    private String userName,email, password, sexo;
    public User() {

    }

    public User(String userName, String email, String password, String sexo) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.sexo = sexo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
}
