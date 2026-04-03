package businessLogic;
import java.io.File;
import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import dataAccess.DataAccess;
import domain.Admin;
import domain.Mugimendua;
import domain.Sale;
import domain.Seller;
import exceptions.FileNotUploadedException;
import exceptions.MustBeLaterThanTodayException;
import exceptions.SaleAlreadyExistException;
import exceptions.EmailIsNotCorrectException;

import java.awt.image.BufferedImage;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;


/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation  implements BLFacade {
	 private static final int baseSize = 160;

		private static final String basePath="src/main/resources/images/";
	DataAccess dbManager;

	public BLFacadeImplementation()  {		
		System.out.println("Creating BLFacadeImplementation instance");
		dbManager=new DataAccess();		
	}
	
    public BLFacadeImplementation(DataAccess da)  {
		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		dbManager=da;		
	}
    

	/**
	 * {@inheritDoc}
	 */
   @WebMethod
	public Sale createSale(String title, String description,int status, float price, Date pubDate, String sellerEmail, File file) throws  FileNotUploadedException, MustBeLaterThanTodayException, SaleAlreadyExistException {
		dbManager.open();
		Sale product=dbManager.createSale(title, description, status, price, pubDate, sellerEmail, file);		
		dbManager.close();
		return product;
   };
	
   /**
    * {@inheritDoc}
    */
	@WebMethod 
	public List<Sale> getSales(String desc){
		dbManager.open();
		List<Sale>  rides=dbManager.getSales(desc);
		dbManager.close();
		return rides;
	}
	
	/**
	    * {@inheritDoc}
	    */
		@WebMethod 
		public List<Sale> getPublishedSales(String desc, Date pubDate) {
			dbManager.open();
			List<Sale>  rides=dbManager.getPublishedSales(desc,pubDate);
			dbManager.close();
			return rides;
		}
	/**
	    * {@inheritDoc}
	    */
	@WebMethod public BufferedImage getFile(String fileName) {
		return dbManager.getFile(fileName);
	}

    
	public void close() {
		DataAccess dB4oManager=new DataAccess();
		dB4oManager.close();

	}

	/**
	 * {@inheritDoc}
	 */
    @WebMethod	
	 public void initializeBD(){
    	dbManager.open();
		dbManager.initializeDB();
		dbManager.close();
	}
    /**
	 * {@inheritDoc}
	 */
    @WebMethod public Image downloadImage(String imageName) {
        File image = new File(basePath+imageName);
        try {
            return ImageIO.read(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @WebMethod public Seller isUserLogin(String username, String password) {
    	dbManager.open();
		Seller  b=dbManager.isUserLogin(username, password);
		dbManager.close();
		return b;
    }
    
    @WebMethod public boolean isCorrectEmail(String email){
    	if (email == null) {
            return false;
        }
    
    	return email.matches("^[^@\\s]+@[^@\\s]+\\.[A-Za-z]+$"); //ikusteko emailak @ aurretik eta atzetik zerbait duela eta hori eta gero . bat eta letrak daudela
		
    }
    
    @WebMethod public boolean exist(String username) {
    	dbManager.open();
		boolean b = (dbManager.exist(username)!=null);
		dbManager.close();
		return b;
        }
    @WebMethod public void register(String username, String pass, String email) {
    	dbManager.open();
		dbManager.register(username, pass, email);
		dbManager.close();
		
    }
    @WebMethod public Seller getUser(String email) {
    	dbManager.open();
    	Seller user=dbManager.exist(email);
    	dbManager.close();
    	return user;
    }
    @WebMethod public void buy(String selleremail, int saleNumber,String buyermail) {
    	dbManager.open();
    	dbManager.buy(selleremail, saleNumber, buyermail);
    	dbManager.close();
    }
    @WebMethod public double getDiruTotala(String mail) {
    	dbManager.open();
    	double dirua= dbManager.getDiruTotala(mail);
    	dbManager.close();
    	return dirua;
    }
    @WebMethod public void diruaSartu(String mail, double dirua) {
    	dbManager.open();
    	dbManager.diruaSartu(mail, dirua);
    	dbManager.close();
    
    }
    @WebMethod public void diruaAtera(String mail, double dirua) {
    	dbManager.open();
    	dbManager.diruaAtera(mail,dirua);
    	dbManager.close();
    	
    }
    @WebMethod public Admin isAdminLogin(String username, String password) {
    	dbManager.open();
		Admin  b=dbManager.isAdminLogin(username, password);
		dbManager.close();
		return b;
    }
    @WebMethod public List<Sale> getAllBought(String zuremail) {
    	dbManager.open();
		List<Sale> l= dbManager.getAllBought(zuremail);
		dbManager.close();
		return l;
    }
    @WebMethod public void erreklamazioaJarri(String zuremail,int saleNumber, String azalpena) {
    	dbManager.open();
    	dbManager.erreklamazioaJarri(zuremail, saleNumber, azalpena);
    	dbManager.close();
    }
}

