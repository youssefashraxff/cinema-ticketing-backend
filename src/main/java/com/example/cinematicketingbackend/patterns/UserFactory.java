package com.example.cinematicketingbackend.patterns;
import com.example.cinematicketingbackend.model.User;
import com.example.cinematicketingbackend.model.Customer;
import com.example.cinematicketingbackend.model.Admin;
import java.util.UUID;

public class UserFactory {

    public static User createUser(String type,String username,String password,String email){
        String id = UUID.randomUUID().toString();

        switch (type.toLowerCase()){
            case"customer":
                return new Customer(id,username,password,email);
            case"admin":
                return new Admin(id,username,password,email,"EMP","All");
            default:
                throw new IllegalArgumentException("Invalid user type:"+type);
        }
    }

}
