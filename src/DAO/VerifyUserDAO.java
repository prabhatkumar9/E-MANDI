package DAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.User;
import utility.ConnectionManager;
public class VerifyUserDAO {

    public boolean login(String name,String pass) throws Exception {
	ConnectionManager con = new ConnectionManager();
	Statement st = con.getConnection().createStatement();
	ResultSet rs = st.executeQuery("SELECT * from USERDETAILS1");
	while(rs.next()) {
		if(name.equals(rs.getString("USERNAME")) && pass.equals(rs.getString("PASSWORD"))) {
			con.getConnection().close();
			return true;
		}else {
			con.getConnection().close();
			return false;
		}
	}
	return false;
}
    
    public User loadUserdetails(String name , String pass) throws SQLException, Exception {
	String custid = null;
	int userid = 0;
	User user = new User();
	ConnectionManager con = new ConnectionManager();
	Statement st = con.getConnection().createStatement();
	ResultSet rs = st.executeQuery("select userid,custid from userdetails1 where username = '"+name+"' and password = '"+pass+"'");
	while(rs.next())
	{ 	
	    userid = rs.getInt(1);
	    custid = rs.getString(2);
	}
	con.getConnection().close();
	user.setUserId(userid);
	user.setCustomerId(custid);
	user.setUserName(name);
	user.setPassword(pass);
	return user;
    }
    
    public User loadCustomerDetails(User user) throws Exception {
	String custid = user.getCustomerId();
//	System.out.println("in loadcustomer  "+custid);
	ConnectionManager con = new ConnectionManager();
	Statement st = con.getConnection().createStatement();
	ResultSet rs = st.executeQuery("select custid,firstname,lastname,email,address,gender,age,contact from customer where custid = "+custid);
	while(rs.next()) {
	    user.setCustomerId(rs.getString(1));
	    user.setFirstName(rs.getString(2));
	    user.setLastName(rs.getString(3));
	    user.setEmailadd(rs.getString(4));
	    user.setAddress(rs.getString(5));
	    user.setGender(rs.getString(6));
	    user.setAge(rs.getInt(7));
	    user.setContact(rs.getString(8));
	}
	con.getConnection().close();
	return user;
    }
}
