package DAO;

import java.sql.ResultSet;

import model.User;

public interface UserDAOInterface {

    // update user and customer details after new registration
    public void insertUserDetails(User user) throws Exception;
    
    // fetch result form DataBase
    public ResultSet getDb(String sql) throws Exception;
    
    // generate unique user id for each user
    public int generateUserId() throws Exception;
    
    // generate unique customer id for each customer
    public String generateCustomerId() throws Exception;
    
    // fetching user details and order history from Database
    public void fetchUserDetails(String custid) throws Exception ;
    
    // fetching order hsitory from database
    public void fetchOrderHistory(String custid) throws Exception ;
      
}
