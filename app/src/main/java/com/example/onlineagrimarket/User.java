package com.example.onlineagrimarket;

public class User {

    public String firstname;
    public String lastname;
    public String phoneNumber;
    public  User()
    {

    }

    public void setFirstname(String fname)
    {
        firstname = fname;
    }
    public void setLastname(String lname)
    {
        lastname = lname;
    }
    public void setPhoneNumber(String phone)
    {
        phoneNumber = phone;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
