package com.example.cinematicketingbackend.patterns;
import com.example.cinematicketingbackend.model.User;
import com.example.cinematicketingbackend.model.Customer;
import com.example.cinematicketingbackend.model.Admin;
import java.util.Random;

public class UserFactory {

    public static User createUser(String type,String username,String password,String email){
        Random random = new Random();
        String id = String.valueOf(100 + random.nextInt(900));


        switch (type.toLowerCase()){
            case"customer":
                return new Customer(id,username,password,email);
            case"admin":
                return new Admin(id,username,password,email,"EMP");
            default:
                throw new IllegalArgumentException("Invalid user type:"+type);
        }
    }

}
