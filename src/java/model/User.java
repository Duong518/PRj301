package model;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String email;
    private String password;
    private String recoveryCode;
    private String fullName;
    private int age;
    private String address;
    private int phone;

    public User() {
    }

    public User(int id, String email, String password, String recoveryCode, String fullName, int age, String address, int phone) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.recoveryCode = recoveryCode;
        this.fullName = fullName;
        this.age = age;
        this.address = address;
        this.phone = phone;
    }

    
    
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRecoveryCode() {
        return recoveryCode;
    }

    public String getFullName() {
        return fullName;
    }

    public int getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    public int getPhone() {
        return phone;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRecoveryCode(String recoveryCode) {
        this.recoveryCode = recoveryCode;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

   

    @Override
    public String toString() {
        return "User [id=" + id + ", email=" + email + "]";
    }
}
