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
    
    public int loadUserdetails(String name , String pass) throws SQLException, Exception {
	int id = 0;
	ConnectionManager con = new ConnectionManager();
	Statement st = con.getConnection().createStatement();
	ResultSet rs = st.executeQuery("select userid,custid from userdetails1 where username = '"+name+"' and password = '"+pass+"'");
	while(rs.next())
	{
	    	rs.getInt(1);
		id = rs.getInt(2);
	}
	con.getConnection().close();
	return id;
    }
    
    public User loadCustomerDetails(User user) throws Exception {
	int custid = user.getCustomerId();
	System.out.println("in loadcustomer  "+custid);
	ConnectionManager con = new ConnectionManager();
	Statement st = con.getConnection().createStatement();
	ResultSet rs = st.executeQuery("select firstname,lastname,email,address,gender,age,contact from customer where custid = "+custid);
	while(rs.next()) {
	    user.setFirstName(rs.getString(1));
	    user.setLastName(rs.getString(2));
	    user.setEmailadd(rs.getString(3));
	    user.setAddress(rs.getString(4));
	    user.setGender(rs.getString(5));
	    user.setAge(rs.getInt(6));
	    user.setContact(rs.getString(7));
	}
	con.getConnection().close();
	return user;
    }
}
