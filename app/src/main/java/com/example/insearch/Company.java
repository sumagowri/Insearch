package com.example.insearch;

public class Company {

   // public static String getc_name;
    public String c_name;
    private String reg_id;
    private long phone;
    private String address;
    private String email;
    private String password;

    public Company( String c_name, String reg_id, long phone, String address, String email, String password) {
        this.c_name = c_name;
        this.reg_id = reg_id;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.password = password;
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

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

