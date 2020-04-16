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
	System.out.println("1. Register Here.");
	System.out.println("2. Login");
	System.out.println("3. Admin Login");
	int x;
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	x = Integer.parseInt(br.readLine().trim());
	
	////item quantity variable
	int num=0;
	//// name of item 
	 String nm   = null;
	 //// updated quantity
	 int quantity;
	
	String firstname;
	String lastname;
	 String email;
	 String gender;
	 String number;
	 String address;
	 int age;
	 
	 /// list for saving cart items 
	 ArrayList<Product> list = new ArrayList<Product>();
	// objects
	User user = new User();
	ValidateUserFields  valuserfield = new ValidateUserFields ();
	UserDAO  userdao = new UserDAO();
	VerifyUserDAO verifydao = new VerifyUserDAO();
	 Product product = new Product();
	 ProductDAO productdao = new ProductDAO();
	 CrudDAO cruddao = new CrudDAO();
	
	
	String yes = null;
	do {
	    
	switch(x) {
		case 1:
	    		System.out.println("Select  Your UserName : ");
	    		String username = br.readLine().trim();
	    		System.out.println("Select  Your Password : ");
	    		String password = br.readLine().trim();
	    		boolean valuser = valuserfield.newRegistration(username, password);
	    		if(valuser) {
	    		    user.setUserName(username);
	    		    user.setPassword(password);
	    		    System.out.println("Enter Your FirstName : ");
	    		    firstname = br.readLine().trim();
	    		    System.out.println("Enter Your LatName : ");
	    		    lastname = br.readLine().trim();
	    		    System.out.println("Enter Your Email Address  : ");
	    		    email = br.readLine().trim();
	    		    System.out.println("Enter Your Gender : ");
	    		    gender = br.readLine().trim();
	    		    System.out.println("Enter Your ContactNumber  : ");
	    		    number = br.readLine().trim();
	    		    System.out.println("Enter Your Address  : ");
	    		    address = br.readLine().trim();
	    		    System.out.println("Enter Your Age : ");
	    		    age = Integer.parseInt(br.readLine().trim());
	    	  
	    		    int userid =  userdao.generateUserId();
	    		    String custid = userdao.generateCustomerId();
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
		      	System.out.println("Enter username : ");
			String name = br.readLine().trim();
			System.out.println("Enter password : ");
			String pass = br.readLine().trim();
			
			if(verifydao.login(name,pass)) {
			  
			    /// getting customer id from table userdetails
			    user = verifydao.loadUserdetails(name,pass);
			    user = verifydao.loadCustomerDetails(user);
			    System.out.println("Login successful.");
			    int X = 0;
			    do
				{
			    System.out.println("Choose options : ");
			    System.out.println("1. Home Page : ");
			    System.out.println("2. Go to cart : ");
			    System.out.println("3. User details and order History : ");
			     X = Integer.parseInt(br.readLine().trim());
			  
			    switch(X){
				case 1:
				    do {
					  /// show products
					    cruddao.displayProductlist();
					    System.out.println("Add to Cart Item by name : ");
					    nm  = br.readLine().trim();
					    System.out.println("Enter Quantity : ");
					    num = Integer.parseInt(br.readLine().trim());
					    list = cruddao.addTocart(nm,list,num);
					    System.out.print("Want to add more items : ");
					    yes = br.readLine();
				    }while(yes.equals("yes"));
				    break;
				case 2:
				   cruddao.displayCart(list);
				    try {
					    System.out.println("Want to remove any item ? Enter Item.No.  : ");
					    int index =Integer.parseInt(br.readLine().trim());
					   list =  cruddao.removeItem(index,list);
					}catch(Exception e) {
					    System.out.println();
					}
					System.out.println("Want to place order ? yes/no : ");
					yes = br.readLine();
					if(yes.equals("yes")) {
					    cruddao.placeOrder(list,user.getCustomerId());
					    cruddao.updateStock(list);
					}

//				    cruddao.shippingDetails(user);
				    break;
				case 3:
				    /// display user details
				    System.out.println(user.getUserId());
				    System.out.println(user.getCustomerId());
				    System.out.println(user.getUserName());
				    System.out.println(user.getFirstName());
				    System.out.println(user.getLastName());
				    System.out.println(user.getEmailadd());
				    System.out.println(user.getGender());
				    System.out.println(user.getAge());
				    System.out.println(user.getAddress());
				    System.out.println(user.getPassword());
				    break;
			    }  
			    System.out.print("Do you want to continue User?  yes/no : ");
			    yes = br.readLine();
			} while(yes.equals("yes"));
			}else {
				System.out.println("Incorrect username and password.");
			}
			
			break;
		  case 3:
		      	System.out.println("Enter Admin username : ");
			String adminName = br.readLine().trim();
			System.out.println("Enter Admin password : ");
			String AdminPass = br.readLine().trim();
			if(verifydao.login(adminName, AdminPass))
			{
			    do
			    {
			    System.out.println("Admin Login Successfull. ");
			    System.out.println("Choose options : ");
			    System.out.println("1. Add Product. ");
			    System.out.println("2. Delete Product or Update.");
			    int  A = Integer.parseInt(br.readLine().trim());
			  
			    switch(A) {
			    case 1:
				 System.out.println("Enter nunber of products you want to add : ");
				 int  n = Integer.parseInt(br.readLine().trim());
				 for(int i=0;i<n;i++) {
				     System.out.println("Enter Product Name : ");
				     String pname = br.readLine().trim();
				     System.out.println("Add Product Price :  ");
				     int  price = Integer.parseInt(br.readLine().trim());
				     System.out.println("Add Product Description :  ");
				     String pdes = br.readLine().trim();
				     System.out.println("Add Product Quatity in Kg  :  ");
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
				break;
			    case 2:
				//displayproduct list AND update product
				cruddao.displayProductlist();
				System.out.println("ENTER PRODUCT_ID TO UPDATE OR DELETE PRODUCT .");
				String proid = br.readLine().trim();
				System.out.println("");
				cruddao.updateProduct(proid);
				break;
			    }  
			    System.out.print("Do you want to continue  Admin Pannel ? yes/no: ");
			    yes = br.readLine();
			}while(yes.equals("yes"));
			}
			
		      break;
	    		}
	 System.out.print("Do you want to continue Login Page ? yes/no: ");
	yes = br.readLine();
	}while(yes.equals("yes"));
	}
    }	
	
   
