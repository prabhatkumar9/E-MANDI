package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import model.User;
import utility.ConnectionManager;
public class UserDAO {

ConnectionManager cm = new ConnectionManager();
Connection con;

 public void insertUserDetails(User user) throws Exception {
     con = cm.getConnection();
     String insertTouserdetails = "insert into userdetails1 (userId,custId,username,password) values(?,?,?,?)";
     String insertToCustomer = "insert into customer (custId,firstname,lastname,email,address,gender,age,contact) values(?,?,?,?,?,?,?,?)";
     
     /// inserting into customer table
     PreparedStatement ps1 = con.prepareStatement(insertToCustomer);
     ps1.setInt(1,user.getCustomerId());
     ps1.setString(2,user.getFirstName());
     ps1.setString(3,user.getLastName());
     ps1.setString(4,user.getEmailadd());
     ps1.setString(5,user.getAddress());
     ps1.setString(6, user.getGender());
     ps1.setInt(7, user.getAge());
     ps1.setString(8,user.getContact());
	     
     /// inserting into userdetails table
     PreparedStatement ps =  con.prepareStatement(insertTouserdetails);
     ps.setInt(1, user.getUserId());
     ps.setInt(2,user.getCustomerId());
     ps.setString(3,user.getUserName());
     ps.setString(4,user.getPassword());
    
     int x1 = ps1.executeUpdate();
     int x = ps.executeUpdate();
     if(x1==1) {
	 System.out.println("Inserted into customer.");
     }
     else {
	 System.out.println("Some error in ps1 ");
     }
     if(x==1) {
	 System.out.println("Inserted Successfuly");
     }
     else {
	 System.out.println("Some error in ps ");
     }
 }  
 
 
		//String sql = "select count(userid)+1 from userdetails1";
		//int userid = generateUserId(sql);
 public int generateUserId() throws Exception {
     int userid = 0;
     String sql = "select count(userid)+1 from userdetails1";
     con = cm.getConnection();
     Statement st = con.createStatement();
     ResultSet rs = st.executeQuery(sql);
     if(rs.next()) {
	 userid = Integer.parseInt(rs.getString(1));
     }
     return userid;
 }
 
}
