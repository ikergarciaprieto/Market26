package testOperations;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import configuration.ConfigXML;
import domain.Erreklamazioa;
import domain.Sale;
import domain.Seller;


public class TestDataAccess {
	protected  EntityManager  db;
	protected  EntityManagerFactory emf;

	ConfigXML  c=ConfigXML.getInstance();


	public TestDataAccess()  {
		
		System.out.println("TestDataAccess created");

		//open();
		
	}

	
	public void open(){
		

		String fileName=c.getDbFilename();
		
		if (c.isDatabaseLocal()) {
			  emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			  db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			  properties.put("javax.persistence.jdbc.user", c.getUser());
			  properties.put("javax.persistence.jdbc.password", c.getPassword());

			  emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);

			  db = emf.createEntityManager();
    	   }
		System.out.println("TestDataAccess opened");

		
	}
	public void close(){
		db.close();
		System.out.println("TestDataAccess closed");
	}

	public boolean removeSeller(String email) {
		System.out.println(">> TestDataAccess: removeSeller");
		Seller d = db.find(Seller.class, email);
		if (d!=null) {
			db.getTransaction().begin();
			db.remove(d);
			db.getTransaction().commit();
			return true;
		} else 
		return false;
    }
	public Seller createSeller(String email, String name) {
		System.out.println(">> TestDataAccess: addSeller");
		Seller seller=null;
			db.getTransaction().begin();
			try {
			    seller=new Seller(email,name, "aurrera");
				db.persist(seller);
				db.getTransaction().commit();
			}
			catch (Exception e){
				e.printStackTrace();
			}
			return seller;
    }
	public boolean existSeller(String email) {
		 return  db.find(Seller.class, email)!=null;
		 

	}
		
		public Seller addSellerWithSale(String email, String sellerName, String title, String description, int status, float price,  Date pubDate, File file) {
			System.out.println(">> TestDataAccess: addSellerWithSale");
				Seller seller=null;
				db.getTransaction().begin();
				try {
					seller = db.find(Seller.class, email);
					if (seller==null) {
						seller=new Seller(email, sellerName, "aurrera");
				    	db.persist(seller);
					}
					seller.addSale(title, description, status, price, pubDate, file);
					db.getTransaction().commit();
					System.out.println("Seller created "+seller);
					
					return seller;
					
				}
				catch (Exception e){
					e.printStackTrace();
				}
				return null;
	    }
		
	public boolean existSale(String sellerEmail, String title) {
			System.out.println(">> TestDataAccess: existSale");
			Seller s = db.find(Seller.class, sellerEmail);
			if (s!=null) {
				return s.doesSaleExist(title);
			} else 
			return false;
		}
	
	public Sale removeSale(String sellerEmail, String name, String description, Date date) {
	    System.out.println(">> TestDataAccess: removeRide");
	    Seller s = db.find(Seller.class, sellerEmail);
	    
	    if (s != null) {
	        db.getTransaction().begin();
	        
	        Sale saleToRemove = null;
	        
	        
	        List<Sale> sales = s.getSales();
	        if (sales != null && !sales.isEmpty()) {
	            saleToRemove = sales.get(0);
	        }
	        
	        if (saleToRemove != null) {
	            s.removeSale(saleToRemove, date); 
	            db.remove(saleToRemove);          
	        }
	        
	        db.getTransaction().commit();
	        
	        
	        return saleToRemove;

	    } else {
	        return null;
	    }
	}
	
	public long addErreklamazioa(String buyerEmail, String sellerEmail, double salePrice, double hasierakoDirua) {
		
		db.getTransaction().begin();
		
		Seller seller = this.createSeller(sellerEmail, "Iker");
		
		Sale sale = seller.addSale("Ordenagailua", "6Gb-ko memoria", 1, (float) salePrice, new Date(), null);
		sale.setErreklamatuta(true);
		
		Seller erreklamatzenDuena = this.createSeller(buyerEmail, "Aitzol");
		erreklamatzenDuena.setDiruTotala(hasierakoDirua);
		
		Erreklamazioa errek = new Erreklamazioa(sale, new Date(), "Oso ondo dago", erreklamatzenDuena);
		
		db.persist(erreklamatzenDuena);
	    db.persist(seller);
	    db.persist(errek);
	    
	    db.getTransaction().commit();
	    
	    return errek.getId();
	}
	
	public boolean removeErreklamazioa(long errekId, String buyerEmail, String sellerEmail) {
        db.getTransaction().begin();

        Erreklamazioa errek = db.find(Erreklamazioa.class, errekId);
        if (errek != null) {
            db.remove(errek);
        }

        Seller buyer = db.find(Seller.class, buyerEmail);
        if (buyer != null) {
            db.remove(buyer);
        }

        Seller seller = db.find(Seller.class, sellerEmail);
        if (seller != null) {
            db.remove(seller);
        }

        db.getTransaction().commit();
        return true;
    }
	
	public Erreklamazioa getErreklamazioa(long errekId) {
        return db.find(Erreklamazioa.class, errekId);
    }
	
	public Seller getSeller(String mail) {
        return db.find(Seller.class, mail);
    }

		
}