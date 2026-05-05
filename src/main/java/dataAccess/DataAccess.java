package dataAccess;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.jws.WebMethod;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.Seller;
import domain.Admin;
import domain.Chat;
import domain.ErosketaAnitza;
import domain.Erreklamazioa;
import domain.Mezua;
import domain.Mugimendua;
import domain.Sale;
import exceptions.FileNotUploadedException;
import exceptions.MustBeLaterThanTodayException;
import exceptions.SaleAlreadyExistException;
import exceptions.StringIsEmptyException;
import exceptions.UserAlreadyExistException;
import exceptions.EmailIsNotCorrectException;


/**
 * It implements the data access to the objectDb database
 */
public class DataAccess  {
	private  EntityManager  db;
	private  EntityManagerFactory emf;
	private static final int baseSize = 160;

	private static final String basePath="src/main/resources/images/";



	ConfigXML c=ConfigXML.getInstance();

	public DataAccess()  {
		if (c.isDatabaseInitialized()) {
			String fileName=c.getDbFilename();

			File fileToDelete= new File(fileName);
			if(fileToDelete.delete()){
				File fileToDeleteTemp= new File(fileName+"$");
				fileToDeleteTemp.delete();
				System.out.println("File deleted");
			} else {
				System.out.println("Operation failed");
			}
		}
		open();
		if  (c.isDatabaseInitialized()) 
			initializeDB();
		System.out.println("DataAccess created => isDatabaseLocal: "+c.isDatabaseLocal()+" isDatabaseInitialized: "+c.isDatabaseInitialized());

		close();

	}

	public DataAccess(EntityManager db) {
		this.db=db;
	}



	/**
	 * This method  initializes the database with some products and sellers.
	 * This method is invoked by the business logic (constructor of BLFacadeImplementation) when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	public void initializeDB(){

		db.getTransaction().begin();

		try { 

			//Create sellers 
			Seller seller1=new Seller("seller1@gmail.com","Aitor Fernandez","aurrera");
			Seller seller2=new Seller("seller22@gmail.com","Ane Gaztañaga", "aurrera");
			Seller seller3=new Seller("seller3@gmail.com","Test Seller", "aurrera");


			//Create products
			Date today = UtilDate.trim(new Date());


			seller1.addSale("futbol baloia", "oso polita, gutxi erabilita", 2, 10,  today, null);
			seller1.addSale("salomon mendiko botak", "44 zenbakia, 3 ateraldi",2,  20,  today, null);
			seller1.addSale("samsung 42\" telebista", "berria, erabili gabe", 1, 175,  today, null);
			seller1.addMugimendua(today, "ur botila", -10.00);
			seller1.addMugimendua(today, "Betaurrekoak", +200.00);


			seller2.addSale("imac 27", "7 urte, dena ondo dabil", 1, 200,today, null);
			seller2.addSale("iphone 17", "oso gutxi erabilita", 2, 400, today, null);
			seller2.addSale("orbea mendiko bizikleta", "29\" 10 urte, mantenua behar du", 3,225, today, null);
			seller2.addSale("polar kilor erlojua", "Vantage M, ondo dago", 3, 30, today, null);

			seller3.addSale("sukaldeko mahaia", "1.8*0.8, 4 aulkiekin. Prezio finkoa", 3,45, today, null);


			db.persist(seller1);
			db.persist(seller2);
			db.persist(seller3);
			
			Admin admin1 = new Admin("123456789a","Pako","aurrera");
			db.persist(admin1);

			db.getTransaction().commit();
			System.out.println("Db initialized");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}


	/**
	 * This method creates/adds a product to a seller
	 * 
	 * @param title of the product
	 * @param description of the product
	 * @param status 
	 * @param selling price
	 * @param category of a product
	 * @param publicationDate
	 * @return Product
	 * @throws SaleAlreadyExistException if the same product already exists for the seller
	 */
	public Sale createSale(String title, String description, int status, float price,  Date pubDate, String sellerEmail, File file) throws  FileNotUploadedException, MustBeLaterThanTodayException, SaleAlreadyExistException {


		System.out.println(">> DataAccess: createProduct=> title= "+title+" seller="+sellerEmail);
		try {


			if(pubDate.before(UtilDate.trim(new Date()))) {
				throw new MustBeLaterThanTodayException(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.ErrorSaleMustBeLaterThanToday"));
			}
			if (file==null)
				throw new FileNotUploadedException(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.ErrorFileNotUploadedException"));

			db.getTransaction().begin();

			Seller seller = db.find(Seller.class, sellerEmail);
			if (seller.doesSaleExist(title)) {
				db.getTransaction().commit();
				throw new SaleAlreadyExistException(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.SaleAlreadyExist"));
			}

			Sale sale = seller.addSale(title, description, status, price, pubDate, file);
			//next instruction can be obviated

			db.persist(seller); 
			db.getTransaction().commit();
			System.out.println("sale stored "+sale+ " "+seller);



			System.out.println("hasta aqui");

			return sale;
		} catch (NullPointerException e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			db.getTransaction().commit();
			return null;
		}


	}

	/**
	 * This method retrieves all the products that contain a desc text in a title
	 * 
	 * @param desc the text to search
	 * @return collection of products that contain desc in a title
	 */
	public List<Sale> getSales(String desc) {
		System.out.println(">> DataAccess: getProducts=> from= "+desc);

		List<Sale> res = new ArrayList<Sale>();	
		TypedQuery<Sale> query = db.createQuery("SELECT s FROM Sale s WHERE s.title LIKE ?1",Sale.class);   
		query.setParameter(1, "%"+desc+"%");

		List<Sale> sales = query.getResultList();
		for (Sale sale:sales){
			res.add(sale);
		}
		return res;
	}

	/**
	 * This method retrieves the products that contain a desc text in a title and the publicationDate today or before
	 * 
	 * @param desc the text to search
	 * @return collection of products that contain desc in a title
	 */
	public List<Sale> getPublishedSales(String desc, Date pubDate) {
		System.out.println(">> DataAccess: getProducts=> from= "+desc);

		List<Sale> res = new ArrayList<Sale>();	//query-ari gehituta bought==false
		TypedQuery<Sale> query = db.createQuery("SELECT s FROM Sale s WHERE s.title LIKE ?1 AND s.pubDate <=?2 AND s.bought=false",Sale.class);   
		query.setParameter(1, "%"+desc+"%");
		query.setParameter(2,pubDate);

		List<Sale> sales = query.getResultList();
		for (Sale sale:sales){

			res.add(sale);
		}
		return res;
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
		System.out.println("DataAccess opened => isDatabaseLocal: "+c.isDatabaseLocal());


	}

	public BufferedImage getFile(String fileName) {
		File file=new File(basePath+fileName);
		BufferedImage targetImg=null;
		try {
			targetImg = rescale(ImageIO.read(file));
		} catch (IOException ex) {
			//Logger.getLogger(MainAppFrame.class.getName()).log(Level.SEVERE, null, ex);
		}
		return targetImg;

	}

	public BufferedImage rescale(BufferedImage originalImage)
	{
		System.out.println("rescale "+originalImage);
		BufferedImage resizedImage = new BufferedImage(baseSize, baseSize, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, baseSize, baseSize, null);
		g.dispose();
		return resizedImage;
	}



	public void close(){
		db.close();
		System.out.println("DataAcess closed");
	}

	public Seller isUserLogin(String username, String password) {
		TypedQuery<Seller> query = db.createQuery("SELECT s FROM Seller s WHERE s.name=?1 AND s.password=?2",Seller.class);   
		query.setParameter(1, username);
		query.setParameter(2, password);
		if(!query.getResultList().isEmpty()) {
			return query.getResultList().get(0);
		}else {
			return null;
		}
	}
	public Seller exist(String email) {//find EMAIL behar du
		Seller u = db.find(Seller.class,email);
		return u;

	}

	public void register(String username, String pass, String email) {
		db.getTransaction().begin();
		Seller u = new Seller(email,username, pass);
		db.persist(u); 
		db.getTransaction().commit();
		System.out.println("seller created");

	}

	public void buy(String selleremail, int saleNumber,String buyermail){
		db.getTransaction().begin();
		Seller buyer= db.find(Seller.class,buyermail);
		Sale boughtsale=db.find(Sale.class,saleNumber);
		Seller seller= db.find(Seller.class,selleremail);

		if(boughtsale!=null) {//gertatzen da ez dela sale-a kentzen saltzailearen Sales listatik 
			Date today = UtilDate.trim(new Date());
			buyer.addBought(boughtsale, today);
			seller.removeSale(boughtsale,today);
			System.out.println("Erosi da");
		}else {
			System.out.println("sale ez da aurkitu");
		}
		db.getTransaction().commit();
	}
	public double getDiruTotala(String mail) {
		Seller u = db.find(Seller.class,mail);
		return u.getDiruTotala();
	}
	public void diruaSartu(String mail, double dirua) {
		Seller u = db.find(Seller.class,mail);
		db.getTransaction().begin();
		Date today = UtilDate.trim(new Date());
		u.diruaSartu(dirua, today);
		db.getTransaction().commit();

	}


	public void diruaAtera(String mail, double dirua) {
		Seller u= db.find(Seller.class,mail);
		db.getTransaction().begin();
		Date today = UtilDate.trim(new Date());
		u.diruaAtera(dirua, today);
		db.getTransaction().commit();


	}
	public Admin isAdminLogin(String nan, String password) {
		TypedQuery<Admin> query = db.createQuery("SELECT s FROM Admin s WHERE s.NAN=?1 AND s.pasahitza=?2",Admin.class);   
		query.setParameter(1, nan);
		query.setParameter(2, password);
		if(!query.getResultList().isEmpty()) {
			return query.getResultList().get(0);
		}else {
			return null;
		}
	}
	public List<Sale> getAllBought(String zuremail){
		List<Sale> l = new ArrayList<Sale>();
		//bought true diren sale guztiak hartu behar ditugu
		Seller s = db.find(Seller.class, zuremail);
		l = s.getBought();
		return l;
	}
	public void erreklamazioaJarri(String zuremail,int saleNumber, String azalpena) {
		Seller user = db.find(Seller.class, zuremail);
		Sale sale = db.find(Sale.class, saleNumber);
		db.getTransaction().begin();
		sale.setErreklamatuta(true);
		Erreklamazioa errek = new Erreklamazioa(sale,UtilDate.trim(new Date()),azalpena,user);
		user.addJarritakoErreklamazioak(errek);
		sale.getSeller().addJasotakoErreklamazioak(errek);
		db.getTransaction().commit();
	}
	
	public List<Erreklamazioa> getJrerreklamazioak(String mail){
		Seller user = db.find(Seller.class, mail);
		List<Erreklamazioa> jErreList= user.getJarritakoErreklamazioak();
		return jErreList;
		
	}
	public List<Erreklamazioa> getJserreklamazioak(String mail){
		Seller user = db.find(Seller.class, mail);
		List<Erreklamazioa> jErreList= user.getJasotakoErreklamazioak();
		return jErreList;
		
	}
	
	public List<Seller> getAllUsers(){
		TypedQuery<Seller>query= db.createQuery("SELECT s FROM Seller s", Seller.class);
		List<Seller>users=query.getResultList();
		return users;
		
	}
	public void acceptReclamation(boolean b,long errekId) {
		Erreklamazioa errek=db.find(Erreklamazioa.class, errekId);
		db.getTransaction().begin();
		if(b) {
			errek.setOnartua(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.Onartua"));
			//erosleari dirua itzuli@WebMethod public List<Sale> obtainList(String usermail)
			Double diru = (double) errek.getSale().getPrice();
			
			errek.getErreklamatzenDuena().setDiruTotala(errek.getErreklamatzenDuena().getDiruTotala()+diru);
			errek.getErreklamatzenDuena().removeBought(errek.getSale());
			String desk = errek.getSale().getTitle();
			errek.getErreklamatzenDuena().addMugimendua(UtilDate.trim(new Date()), desk, diru);
			
			//seller-ari dirua kendu
			
		}else {
			errek.setOnartua(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.EzOnartua"));
			errek.getSale().setErreklamatuta(false);
			//dirua ez itzuli
		}
		
		db.getTransaction().commit();
	}
	
	public List<Sale> obtainList(String usermail){
		
		Seller user= db.find(Seller.class, usermail);
		List<Sale> karritosales= null;
		ErosketaAnitza karrito= user.getErosketaAnitza();
		if(karrito!=null) {
		karritosales=karrito.getSales();
		}
		return karritosales;
		
	}
	public boolean karrituraEraman(String mail,int salenum) {
		Seller user = db.find(Seller.class, mail);
		Sale sale = db.find(Sale.class, salenum);
		boolean b = true;
		db.getTransaction().begin();
			try {
				ErosketaAnitza ea = user.getKarrito();
				if(ea==null) {
					ea = user.createErosketaAnitza(sale);	
				}else {//komprobatu pertsona berdinaren produktuak direla
					if(sale.getSeller()!=ea.getSeller()) {
						throw new UserAlreadyExistException();//Ez dut beste bat egingo bakarrik honetarako
					}
				}
				db.persist(ea);
				ea.addSales(sale);
				sale.setAnitza(ea);
				ea.gehituPrezioa(sale.getPrice());
			}catch(UserAlreadyExistException e) {
				b = false;
			}
		db.getTransaction().commit();
		return b;
	}
	public void karrituaErosi(String mail) {
		Seller user=db.find(Seller.class, mail);
		ErosketaAnitza ea = user.getKarrito();
		Double dt=user.getDiruTotala();
		Double p = ea.getPrezioa();
		if(dt>=p) {
			db.getTransaction().begin();
			ea.denaBought();
			db.getTransaction().commit();
		}
		
	}
	public List<Chat> chatakLortu(String mail) {
		List<Chat> a = new ArrayList<Chat>();
		Seller user = db.find(Seller.class, mail);
		a.addAll(user.getChats());
		return a;
	}
	public boolean chatIreki(String mail,String mail2) {
		//true ondo joan bada
		boolean b=true;
		
		Seller nor = db.find(Seller.class, mail);
		Seller nori = db.find(Seller.class, mail2);
		TypedQuery<Chat>query= db.createQuery("SELECT s FROM Chat s WHERE (user1=?1 AND user2=?2) OR (user1=?2 AND user2=?1)", Chat.class);
		query.setParameter(1, nor);
		query.setParameter(2, nori);
		List<Chat>list=query.getResultList();//begiratu hasieratu ez dela dagoeneko
		db.getTransaction().begin();
		if(!list.isEmpty()) {
			b=false;
		}else {
			Chat c = nor.createChat(nori);
			db.persist(c);
		}
		db.getTransaction().commit();
		return b;
	}
	public void mezuaBidali(String mail,Long idchat,String text) {
		Seller bidaltze = db.find(Seller.class, mail);
		Chat chat = db.find(Chat.class, idchat);
		db.getTransaction().begin();
		Mezua m = chat.createMezua(text,UtilDate.trim(new Date()),bidaltze);
		bidaltze.addBidalimezuak(m);
		chat.addMezuak(m);
		db.persist(m);
		db.getTransaction().commit();
	}
	public List<Mezua> mezuakLortu(Long idChat){
		Chat c = db.find(Chat.class, idChat);
		return c.getMezuak();
	}
	public Double getKarritoPrezio(String username) {
		double a = db.find(Seller.class, username).getKarrito().getPrezioa();
		return a;
	}
	public void DESTROY(String usermail) {
		Seller user = db.find(Seller.class, usermail);
		ErosketaAnitza karrito = user.getErosketaAnitza();
		db.getTransaction().begin();
		
		for(Sale s: karrito.getSales()) {
			s.setAnitza(null);
		}
		user.setKarrito(null);
		
		db.remove(karrito);
		db.getTransaction().commit();
	}
	
}
