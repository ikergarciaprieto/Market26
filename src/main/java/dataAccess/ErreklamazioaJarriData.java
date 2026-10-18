package dataAccess;

import java.io.File;
import java.util.Date;

public class ErreklamazioaJarriData {
    private String mail;
    private int saleNumber;
    private String azalpena;
    
    public ErreklamazioaJarriData(String m, int sNum, String azal)
    {
    	mail = m;
    	saleNumber = sNum;
    	azalpena = azal;
    }

    public String getMail() { return mail; }
    public int getSaleNumber() { return saleNumber; }
    public String getAzalpena() { return azalpena; }
}