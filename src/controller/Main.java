package controller;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import DAO.CrudDAO;
import DAO.ProductDAO;
import DAO.UserDAO;
import DAO.VerifyUserDAO;
import model.Product;
import model.User;
import service.ValidateUserFields;

public class Main {

    public static void main(String[] args) throws Exception {
	
	int x;
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	//item quantity variable
	int num=0;
	// name of item 
	 String nm   = null;
	 // updated quantity
	 int quantity;
	 // order id or no.
	 String orderId ;
	
	 // initial variables
	String firstname;
	String lastname;
	 String email;
	 String gender;
	 String number;
	 String address;
	 int age;
	 
	 // list for saving cart items 
	 ArrayList<Product> list = new ArrayList<Product>();
	// objects
	User user = new User();
	ValidateUserFields  valuserfield = new ValidateUserFields ();
	UserDAO  userdao = new UserDAO();
	VerifyUserDAO verifydao = new VerifyUserDAO();
	 Product product = new Product();
	 ProductDAO productdao = new ProductDAO();
	 CrudDAO cruddao = new CrudDAO();
	    
	 System.out.println("============= WELCOME TO ONLINE WHOLESALE MARKET =============");
	 System.out.println();
	String yes = null;
	do { 
	    	System.out.println("\t\t 1. Register Here.");
		System.out.println("\t\t 2. Login");
		System.out.println("\t\t 3. Admin Login");
		x = Integer.parseInt(br.readLine().trim());
	switch(x) {
		case 1:
	    		System.out.print("\t Select  Your UserName : ");
	    		String username = br.readLine().trim();
	    		System.out.print("\t Select  Your Password : ");
	    		String password = br.readLine().trim();
	    		boolean valuser = valuserfield.newRegistration(username, password);
	    		if(valuser) {
	    		    user.setUserName(username);
	    		    user.setPassword(password);
	    		    System.out.print("\t Enter Your FirstName : ");
	    		    firstname = br.readLine().trim();
	    		    System.out.print("\t Enter Your LatName : ");
	    		    lastname = br.readLine().trim();
	    		    System.out.print("\t Enter Your Email Address  : ");
	    		    email = br.readLine().trim();
	    		    System.out.print("\t Enter Your Gender : ");
	    		    gender = br.readLine().trim();
	    		    System.out.print("\t Enter Your ContactNumber  : ");
	    		    number = br.readLine().trim();
	    		    System.out.print("\t Enter Your Address  : ");
	    		    address = br.readLine().trim();
	    		    System.out.print("\t Enter Your Age : ");
	    		    age = Integer.parseInt(br.readLine().trim());
	    	  
	    		    // generate user id
	    		    int userid =  userdao.generateUserId();
	    		    // generate customer id
	    		    String custid = userdao.generateCustomerId();
	    		    // setting the user details in user model class
	    		    user.setCustomerId(custid);
	    		    user.setUserId(userid);
	    		    user.setFirstName(firstname);
	    		    user.setLastName(lastname);
	    		    user.setEmailadd(email);
	    		    user.setGender(gender);
	    		    user.setContact(number);
	    		    user.setAddress(address);
	    		    user.setAge(age);
	    		    // Register into table
	    		    userdao.insertUserDetails(user);
	    		}
	    		    break;

		  case 2:
		      	System.out.print("\t Enter username : ");
			String name = br.readLine().trim();
			System.out.print("\t Enter password : ");
			String pass = br.readLine().trim();
			
			if(verifydao.login(name,pass)) {
			  
			    /// getting customer id from table userdetails
			    user = verifydao.loadUserdetails(name,pass);
			    user = verifydao.loadCustomerDetails(user);
			    System.out.println("\t\t Login successful.");
			    int X = 0;
			    do
				{
				System.out.println();
			    System.out.println("\t\t Choose options : ");
			    System.out.println("\t\t 1. Home Page : ");
			    System.out.println("\t\t 2. Go to cart : ");
			    System.out.println("\t\t 3. User details and order History : ");
			     X = Integer.parseInt(br.readLine().trim());
			     System.out.println();
			  
			    switch(X){
				case 1:
				    do {
					  /// show products
					    cruddao.displayProductlist();
					    try {
					    System.out.print("Add to Cart Item by name :     ");
					    nm  = br.readLine().trim();
					    System.out.print("\t\t Enter Quantity :      ");
					    num = Integer.parseInt(br.readLine().trim());
					    list = cruddao.addTocart(nm,list,num);
					    }catch(Exception e) {
						System.out.println("\t Wrong Input ....not Match with items..");
					    }
					    System.out.print("\t\t Want to add more items ? yes/no : ");
					    yes = br.readLine();
				    }while(yes.equals("yes"));
				    break;
				case 2:
				   int CartValue = cruddao.displayCart(list);
				    try {
					    System.out.print("\t\t Want to remove any item ? Enter Item.No.  : ");
					    int index =Integer.parseInt(br.readLine().trim());
					   list =  cruddao.removeItem(index,list);
					}catch(Exception e) {
					    System.out.println();
					}
					System.out.print("\t \t Want to place order ? yes/no : ");
					yes = br.readLine();
					if(yes.equals("yes")) {
					    System.out.print("\t Select Payment type : card / cash on delivery(cod) / wallet ? :     ");
					    String type = br.readLine().trim();
					    orderId = cruddao.placeOrder(list,user.getCustomerId());
					    cruddao.updateStock(list,orderId);
					    String shipId = cruddao.generateShipId();
					    cruddao.shippingDetails(user,orderId,shipId);
					    String inv = cruddao.generateInvoice();
					    cruddao.payment(type,inv,orderId,CartValue);
					    cruddao.pdfBillGeneration(list, user);
					    list.clear();
					}
				    break;
				case 3:
				    /// display user details
				    userdao.fetchUserDetails(user.getCustomerId());
				    userdao.fetchOrderHistory(user.getCustomerId());
				    break;
			    }  
			    System.out.print("\t \t Do you want to continue UserPage ?   yes/no   :     ");
			    yes = br.readLine();
			} while(yes.equals("yes"));
			}else {
				System.out.println("\t\t Incorrect username and password.");
			}
			
			break;
		  case 3:
		      	System.out.print("\t\t Enter Admin username :    ");
			String adminName = br.readLine().trim();
			System.out.print("\t\t Enter Admin password :    ");
			String AdminPass = br.readLine().trim();
			if(verifydao.login(adminName, AdminPass))
			{
			    do
			    {
			    System.out.println("****************Admin Login Successfull**************** ");
			    System.out.println("\t Choose options : ");
			    System.out.println("\t 1. Add Product. ");
			    System.out.println("\t 2. Delete Product or Update.");
			    int  A = Integer.parseInt(br.readLine().trim());
			  
			    switch(A) {
			    case 1:
				try {
				 System.out.print("\t Enter nunber of products you want to add :  ");
				 int  n = Integer.parseInt(br.readLine().trim());
				 for(int i=0;i<n;i++) {
				     System.out.print("\t\t Enter Product Name :    ");
				     String pname = br.readLine().trim();
				     System.out.print("\t\t Add Product Price :    ");
				     int  price = Integer.parseInt(br.readLine().trim());
				     System.out.print("\t\t Add Product Description :    ");
				     String pdes = br.readLine().trim();
				     System.out.print("\t\t Add Product Quatity in Kg  :    ");
				     quantity = Integer.parseInt(br.readLine().trim());
				     
				     /// getting product id
				     String pid = productdao.generateProductId();
				     product.setPrice(price);
				     product.setProductId(pid);
				     product.setProductName(pname);
				     product.setDescription(pdes);
				     /// insert in product table
				     productdao.addProduct(product,quantity);
				 }
			    }catch(Exception e){
				System.out.println("\t Please Enter valid Input.");
			    }
				break;
			    case 2:
				try {
				//displayproduct list AND update product
				cruddao.displayProductlist();
				System.out.println("\t ENTER PRODUCT_ID TO UPDATE OR DELETE PRODUCT .");
				String proid = br.readLine().trim();
				System.out.println();
				cruddao.updateProduct(proid);
				}catch(Exception e) {
				    System.out.println("\t Invalid Input");
				}
				break;
			    }  
			    System.out.print("\t Do you want to continue  Admin Pannel ? yes/no:    ");
			    yes = br.readLine();
			}while(yes.equals("yes"));
			}
		      break;
	    		}
	 System.out.print(" \t Do you want to continue Login Page ? yes/no:    ");
	yes = br.readLine();
	}while(yes.equals("yes"));
	System.out.println();
	System.out.println("***************** THANKYOU FOR VISITING *****************");
	}
    }	
	
   
