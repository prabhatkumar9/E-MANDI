package utility;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public  class  ConnectionManager {
    
  public Connection getConnection() throws Exception {
	Properties prop = loadPropertiesFile();
	Class.forName(prop.getProperty("driver"));   /// (String) prop.get("driver")
	Connection con = DriverManager.getConnection(prop.getProperty("url"),prop.getProperty("username"),prop.getProperty("password"));
	return con;
  }
    
    
    public static Properties loadPropertiesFile() throws Exception {
	Properties prop = new Properties();	
	InputStream in = ConnectionManager.class.getClassLoader().getResourceAsStream("jdbc.properties");
	prop.load(in);
	in.close(); 
	return prop;
} 
}

