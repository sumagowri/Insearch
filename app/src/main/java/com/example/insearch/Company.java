package com.example.insearch;

public class Company {

   // public static String getc_name;
    private int id;
    public String c_name;
    private String reg_id;
    private int phone;
    private String email;
    private String password;

    public Company(int id, String c_name, String reg_id, int phone, String email, String password) {
        this.id = id;
        this.c_name = c_name;
        this.reg_id = reg_id;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    public  Company() {
    }
    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", c_name=" + c_name +
                ", reg_id=" + reg_id +
                ", phone=" + phone +
                ", email=" + email +
                ", password=" + password +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getReg_id() {
        return reg_id;
    }

    public void setReg_id(String reg_id) {
        this.reg_id = reg_id;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
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
}
