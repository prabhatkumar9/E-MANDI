package service;

import java.sql.SQLException;

import model.User;

public interface VerifyUserInterface {

    // verify existing user
    public boolean login(String name,String pass) throws Exception;
    
    // fetch user details from database table
    public User loadUserdetails(String name , String pass) throws SQLException, Exception;
    
    // setting user details to user class
    public User loadCustomerDetails(User user) throws Exception;
    
}
