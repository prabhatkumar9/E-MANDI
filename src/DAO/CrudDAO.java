package DAO;
import java.sql.Date;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import model.Product;
import model.User;
import utility.ConnectionManager;

public class CrudDAO  implements CrudDaoInterface{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    ConnectionManager cm = new ConnectionManager();
    Connection con;
 
    public void updateProduct(String proid) throws Exception {
	System.out.println("Select What you want to update.");
	System.out.println("1. Product Name.");
	System.out.println("2. Product Price.");
	System.out.println("3. Product Description.");
	System.out.println("4. Delete Product.");
	int choice = Integer.parseInt(br.readLine().trim());
	switch(choice) {
	case 1:
	    System.out.println("Enter New Name  :  ");
	    String name = br.readLine().trim();
	    String updateName = "update product set name ='"+name+"' where id='"+proid+"'";
	    updateDB(updateName);
	    break;
	case 2:
	    System.out.println("Enter New Price  :  ");
	    int price = Integer.parseInt(br.readLine().trim());
	    String updatePrice = "update product set price = "+price+" where id='"+proid+"'";
	    updateDB(updatePrice);
	    break;
	case 3:
	    System.out.println("Enter New Description  :  ");
	    String desc = br.readLine().trim();
	    String updateDes = "update product set description = '"+desc+"' where id='"+proid+"'";
	    updateDB(updateDes);
	    break;
	case 4:
	    String delete = "delete from product where id = '"+proid+"'";
	    updateDB(delete);
	    break;
	}
    }
    
 public void updateDB(String update) throws Exception {
	con = cm.getConnection();
	PreparedStatement ps = con.prepareStatement(update);
	    int x = ps.executeUpdate();
		if(x==1) {
		    System.out.println("Updated Successfully.");
		}
 }
 
 public ResultSet  getDB(String sql) throws Exception {
     	con = cm.getConnection();
	PreparedStatement ps = con.prepareStatement(sql);
	ResultSet rs = ps.executeQuery();
	return rs;
 }
 
    @Override
    public void displayProductlist() throws Exception {
	String sql = "select * from product";
	ResultSet rs = getDB(sql);
	int srNo = 1;
	while(rs.next()) {
	   String id = rs.getString(1);
	   String name = rs.getString(2);
	   int price = rs.getInt(3);
	   String desc = rs.getString(4);
	   System.out.println(srNo+".\t "+"Product ID :  "+id+"\t\t Product Name :  "+name+"\t\t Price :  "+price+"\t\t Description :  "+desc);
	   srNo++;
	}
    }

    
    public ArrayList<Product> addTocart(String nm, ArrayList<Product> list,int num) throws Exception {
	    String sql = "select product.id,product.name,product.price,product.description,stock.quantity from product inner join stock on product.name = stock.stockid where product.name='"+nm+"'";
		con = cm.getConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
		   String id = rs.getString(1);
		   String name = rs.getString(2);
		   int price = rs.getInt(3);
		   String desc = rs.getString(4);
		   int qaunt = rs.getInt(5);
		   if(qaunt>=num) {
		       list.add(new Product(id,name,price*num, desc)); 
		   }else {
		       System.out.println("Stock not available");
		   }
		}
		return list;
	}
    
 
//	public void displayCart(int num, ArrayList<Product> cartlist)  
    
	public void displayCart(ArrayList<Product> cartlist) throws Exception{
	    String sql = null;
	    con = cm.getConnection();
	   
	    try {
		int lenOfList = cartlist.size();
		int cartTotal = 0; 
		int n = 1;
		int itemprice=0;
		System.out.println();
		System.out.println("ITEMS IN CART : ");
		System.out.println();
		for (int i = 0; i < lenOfList; i++) {
			Product product = cartlist.get(i);
			String pid = product.getProductId();
			cartTotal = cartTotal+product.getPrice();
			sql = "select price from product where id = '"+pid+"'";
			 PreparedStatement ps = con.prepareStatement(sql);
			 ResultSet rs = ps.executeQuery();
			 while(rs.next()) {
			     itemprice = rs.getInt(1);
			 }
			System.out.println("***********************************************************************************************************************************************************************");
			System.out.println("itemNo. : "+(i+n)+"\tItem : " +product.getPrice()/itemprice+" Kg. "+product.getProductName()+" \tPrice : "+product.getPrice()/itemprice+"x"+itemprice+" = "+product.getPrice()+" Rs. \tDescription : "+product.getDescription());
			System.out.println();
		}
		/// total amount to pay
		System.out.println("TOTAL CART VALUE : "+cartTotal);
		System.out.println();
	}catch(Exception e) {
		System.out.println("CART IS EMPTY.");
		System.out.println();
		}
	}
	
	public ArrayList<Product> removeItem(int n,ArrayList<Product> cartlist) {
	    int index = n-1;
	    cartlist.remove(index);
	    return cartlist;
	}
	
	
	
	public void placeOrder(ArrayList<Product> cartlist,String custid) throws Exception {
	    int cartValue =  0;
	    LocalDate date = LocalDate.now();
	    String orderNo = generateOrderNo();
	    String sql = "insert into orders(orderno,custid,orderdate) values(?,?,?)";
		 con = cm.getConnection();
		 PreparedStatement ps = con.prepareStatement(sql);
		 ps.setString(1, orderNo);
		 ps.setString(2, custid);
		 ps.setDate(3,Date.valueOf(date));
		int y =  ps.executeUpdate();
		if(y==1) {
		    System.out.println("Order placed.");
		}
		 
		///display order pdf
		for(int i=0;i<cartlist.size();i++) {
		 Product item = cartlist.get(i);
			 cartValue += item.getPrice();
			 String name = item.getProductName();
			 String id = item.getProductId();
			 String desc = item.getDescription();
			 int itemprice = item.getPrice();
	
	    }
	}
	
	public void updateStock(ArrayList<Product> cartlist) throws Exception {
	    	for(int i=0;i<cartlist.size();i++) {
	    	    Product p = cartlist.get(i);
	    	    String nm = p.getProductName();
	    	    String sql = "select product.price,stock.quantity from product inner join stock on product.name=stock.stockid where product.name='"+nm+"'";
	    	    con = cm.getConnection();
	    	    //// getting quantity from stock
	    	    PreparedStatement ps = con.prepareStatement(sql);
	    	    ResultSet rs = ps.executeQuery();
	    	    int quantity = 0;
	    	    int price = 0;
	    	    while(rs.next()) {
	    		price  = rs.getInt(1);
	    		quantity= rs.getInt(2);
	    	    }
	    	    int Amount = p.getPrice();
	    	    int no = Amount/price;
	    	    quantity = quantity-no;
			String updatestock = "update  stock set quantity = ?  where stockid ='"+nm+"'";
			PreparedStatement ps1 = con.prepareStatement(updatestock);
			ps1.setInt(1, quantity);
			int y =ps1.executeUpdate();
			if(y==1) {
			    System.out.println("Updated quantity in database.");
			}
	    	}
	}
	
	
	/// generate unique order number
	public String generateOrderNo() throws Exception {
		 int id = 0;
		 String sql = "select count(orderno)+1 from orders";
		     con = cm.getConnection();
		     Statement st = con.createStatement();
		     ResultSet rs = st.executeQuery(sql);
		     if(rs.next()) {
			 id = Integer.parseInt(rs.getString(1));
		     }
		     String ordNo = "order"+id;
		     return ordNo ;
	    }
	
	/// shippment details
	public void shippingDetails(User user) {
	    user.getAddress();
	    user.getContact();
	}
	
	
	public String generateShipId() throws Exception {
		 int id = 0;
		 String sql = "select count(orderno)+1 from orders";
		     con = cm.getConnection();
		     Statement st = con.createStatement();
		     ResultSet rs = st.executeQuery(sql);
		     if(rs.next()) {
			 id = Integer.parseInt(rs.getString(1));
		     }
		     String ShipNo = "ShipNo"+id;
		     return ShipNo ;
	    }
	
}