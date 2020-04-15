package DAO;

import java.io.IOException;

public interface CrudDaoInterface {
    public void updateProduct(String proid) throws NumberFormatException, IOException, Exception;
    public void displayProductlist() throws Exception;
}
