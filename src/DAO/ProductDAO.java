package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import model.Product;
import utility.ConnectionManager;

public class ProductDAO {
    
    ConnectionManager cm = new ConnectionManager();
    Connection con;
    
    public void addProduct(Product product) throws Exception {
	con = cm.getConnection();
	String  insertProduct = "insert into product(id,name,price, description) values(?,?,?,?)";
	 PreparedStatement ps = con.prepareStatement(insertProduct);
	 ps.setString(1, product.getProductId());
	 ps.setString(2, product.getProductName());
	 ps.setInt(3, product.getPrice());
	 ps.setString(4, product.getDescription());
	 int x = ps.executeUpdate();
	 if(x==1) {
	     System.out.println("Product added.");
	 }else {
	     System.out.println("Error in adding products.");
	 }
	 con.close();
    }
    
    public String generateProductId() throws Exception {
	 int id = 0;
	 String sql = "select count(id)+1 from product";
	     con = cm.getConnection();
	     Statement st = con.createStatement();
	     ResultSet rs = st.executeQuery(sql);
	     if(rs.next()) {
		 id = Integer.parseInt(rs.getString(1));
	     }
	     String prodid = "prod"+id;
	     con.close();
	     return prodid ;
    }
}
