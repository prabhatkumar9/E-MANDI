package DAO;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Product;
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
	    String updateName = "update product set name = "+name+" where id="+proid;
	    updateDB(updateName);
	    break;
	case 2:
	    System.out.println("Enter New Price  :  ");
	    int price = Integer.parseInt(br.readLine().trim());
	    String updatePrice = "update product set price = "+price+" where id="+proid;
	    updateDB(updatePrice);
	    break;
	case 3:
	    System.out.println("Enter New Description  :  ");
	    String desc = br.readLine().trim();
	    String updateDes = "update product set description = "+desc+" where id="+proid;
	    updateDB(updateDes);
	    break;
	case 4:
	    String delete = "delete from product where id = "+proid;
	    updateDB(delete);
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
 
    @Override
    public void displayProductlist() throws Exception {
	String sql = "select * from product";
	con = cm.getConnection();
	PreparedStatement ps = con.prepareStatement(sql);
	ResultSet rs = ps.executeQuery();
	int srNo = 1;
	while(rs.next()) {
	   String id = rs.getString(1);
	   String name = rs.getString(2);
	   int price = rs.getInt(3);
	   String desc = rs.getString(4);
	   System.out.println(srNo+".\t "+"Product ID :  "+id+"\t\t Product Name :  "+name+"\t\t Price :  "+price+"\t\t Description :  "+desc);
	   srNo++;
	}
	con.close();
    }

    	////add item to cart and display serial no of item.
	public ArrayList<Product> addTocart(String nm, ArrayList<Product> list) throws Exception {
//	    ArrayList<Product> list = new ArrayList<>();
	    String sql = "select * from product where name = '"+nm+"'";
		con = cm.getConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
		   String id = rs.getString(1);
		   String name = rs.getString(2);
		   int price = rs.getInt(3);
		   String desc = rs.getString(4);
		  list.add(new Product(id,name,price, desc)); 
		}
		return list;
	}
  
	public void displayCart(ArrayList<Product> cartlist) {
	    try {
		int lenOfList = cartlist.size();
		int cartTotal = 0; 
		int n = 1;
		System.out.println();
		System.out.println("ITEMS IN CART : ");
		System.out.println();
		for (int i = 0; i < lenOfList; i++) {
			Product product = cartlist.get(i);
			cartTotal = cartTotal+product.getPrice();
			System.out.println("**********************************************************");
			System.out.println("itemNo. : "+(i+n));
			System.out.println("Product ID    : "+product.getProductId());
			System.out.println("Product Name     : "+product.getProductName());
			System.out.println("Product Price  : "+product.getPrice());
			System.out.println("Description      : "+product.getDescription());
			System.out.println("**********************************************************");
		}
		/// total amount to pay
		System.out.println("TOTAL CART VALUE : "+cartTotal);
		System.out.println();
		
		try {
		    System.out.println("Want to remove any item ? Enter SR.No.  : ");
		    int index =Integer.parseInt(br.readLine().trim());
		    removeItem(index,cartlist);
		}catch(Exception e) {
		    System.out.println();
		}
		String yes;
		System.out.println("want to place order ? yes/no : ");
		yes = br.readLine();
		while(yes.equals("yes")) {
		    placeOrder(cartlist);
		}

	}catch(Exception e) {
		System.out.println("CART IS EMPTY.");
		System.out.println();
		}
	}
	
	public void removeItem(int n,ArrayList<Product> cartlist) {
	    int index = n-1;
	    cartlist.remove(index);
	    displayCart(cartlist);
	}
	
	public void placeOrder(ArrayList<Product> cartlist) {
	    
	}
}