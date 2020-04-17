package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import model.User;
import utility.ConnectionManager;
public class UserDAO {

ConnectionManager cm = new ConnectionManager();
Connection con;

 // update user and customer details after new registration
 public void insertUserDetails(User user) throws Exception {
     con = cm.getConnection();
     String insertTouserdetails = "insert into userdetails1 (userId,custId,username,password) values(?,?,?,?)";
     String insertToCustomer = "insert into customer (custId,firstname,lastname,email,address,gender,age,contact) values(?,?,?,?,?,?,?,?)";
     
     /// inserting into customer table
     PreparedStatement ps1 = con.prepareStatement(insertToCustomer);
     ps1.setString(1,user.getCustomerId());
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
     ps.setString(2,user.getCustomerId());
     ps.setString(3,user.getUserName());
     ps.setString(4,user.getPassword());
     ps1.executeUpdate();
     ps.executeUpdate();  
 }  
 
 // fetch result form DataBase
 public ResultSet getDb(String sql) throws Exception {
     con = cm.getConnection();
     Statement st = con.createStatement();
     ResultSet rs = st.executeQuery(sql);
     return rs; 
 }
  
 // generate unique user id for each user
 public int generateUserId() throws Exception {
     int userid = 0;
     String sql = "select count(userid)+1 from userdetails1";
     ResultSet rs = getDb(sql);
     if(rs.next()) {
	 userid = Integer.parseInt(rs.getString(1));
     }
     return userid;
 }
 
 // generate unique customer id for each customer
 public String generateCustomerId() throws Exception {
     String custid = null;
     String sql = "select count(custid)+1 from customer";
     ResultSet rs = getDb(sql);
     if(rs.next()) {
	custid = rs.getString(1);
     }
     return custid;
 }
 
 // fetching user details and order history from Database
 public void fetchUserDetailsOrderHistory(String custid) throws Exception {
     String sql = "select userdetails1.username, userdetails1.password,\r\n" + 
     	"customer.firstname, customer.lastname ,customer.email, customer.age, customer.gender,\r\n" + 
     	"orders.orderno,orderdetails.productid, orderdetails.quantity,product.name,product.price,\r\n" + 
     	"shipment.address, shipment.shipdate, shipment.contact,\r\n" + 
     	"payment.payno, payment.type, payment.paydate, payment.amount\r\n" + 
     	"from userdetails1\r\n" + 
     	"inner join customer on userdetails1.custid = customer.custid\r\n" + 
     	"inner join orders on customer.custid = orders.custid\r\n" + 
     	"inner join orderdetails on orders.orderno = orderdetails.orderid\r\n" + 
     	"inner join product on orderdetails.productid = product.id\r\n" + 
     	"inner join shipment on orderdetails.orderid = shipment.orderid\r\n" + 
     	"inner join payment on shipment.orderid = payment.orderid\r\n" + 
     	"where custid ="+custid;
     String username = null;
     String pass = null;
     String firstname = null;
     String lastname = null;
     String email = null;
     int age =0;
     String gender = null;
     String ordid = null;
     String prodid = null;
     int quant = 0;
     String pname = null;
     int prdprice =0;
     String add = null;
     LocalDate shipdate = null;
     String contact = null;
     String invono = null;
     String ptype = null;
     LocalDate paydate = null;
     int totalamount = 0;
     
     // fetching data from database
     ResultSet rs = getDb(sql);
     while(rs.next()) {
	 username = rs.getString(1);
	 pass = rs.getString(2);
	 firstname = rs.getString(3);
	 lastname = rs.getString(4);
	 email = rs.getString(5);
	 age = rs.getInt(6);
	 gender = rs.getString(7);
	 ordid = rs.getString(8);
	 prodid = rs.getString(9);
	 quant = rs.getInt(10);
	 pname = rs.getString(11);
	 prdprice = rs.getInt(12);
	 add = rs.getString(13);
	 shipdate = rs.getDate(14).toLocalDate();
	 contact = rs.getString(15);
	 invono = rs.getString(16);
	 ptype = rs.getString(17);
	 paydate = rs.getDate(18).toLocalDate();
	 totalamount = rs.getInt(19); 
	 System.out.println("Shiping Date : "+shipdate+"\t\t Payment Date : "+paydate+"\t\t Type of Payment : "+ptype);
	 System.out.println("Order Id : "+ordid+"\t\t Product Id : "+prodid+"\t\t Product name : "+pname+"\t\t Price : "+prdprice+"\t Quantity : "+quant+" kg. \t\t Invoice : "+invono+"\t Total amount : "+totalamount);
	 System.out.println();
     }
     System.out.println("|====================================================|");
     System.out.println("*___________________________________User details___________________________________________*");
     System.out.println("User Name : "+username+"\t First Name : "+firstname+"\t Last Name : "+lastname);
     System.out.println("Email Address : "+email);
     System.out.println("Age : "+age+"\t Gender : "+gender);
     System.out.println("Contact Number : "+contact);
     System.out.println("Address : "+add); 
     System.out.println("Password : "+pass);
     System.out.println();
 }
}
