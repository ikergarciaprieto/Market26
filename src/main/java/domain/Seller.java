package domain;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Seller implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlID
	@Id 
	private String email;
	private String name; 
	private String password;
	private double diruTotala = 0;
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<Sale> sales=new ArrayList<Sale>();
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<Sale> boughtsales= new ArrayList<Sale>();
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<Mugimendua> mugimenduList= new ArrayList<Mugimendua>();


	public Seller() {
		super();
	}

	public Seller(String email, String name, String password) {
		this.password = password;
		this.email = email;
		this.name = name;
	}
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public List<Sale> getSales(){
		List<Sale> sales2= new ArrayList<Sale>();
		for(int i=0; i<sales.size(); i++) {
			sales2.add(sales.get(i));
		}
		return sales2;
	}
	public List<Sale> getBought(){
		List<Sale> sales2= new ArrayList<Sale>();
		for(int i=0; i<boughtsales.size(); i++) {
			sales2.add(sales.get(i));
		}
		return sales2;
	}
	public double getDiruTotala() {
		return diruTotala;
	}
	
	public String toString(){
		return email+";"+name+sales;
	}
	
	/**
	 * This method creates/adds a sale to a seller
	 * 
	 * @param title of the sale
	 * @param description of the sale
	 * @param status 
	 * @param selling price
	 * @param publicationDate
	 * @return Sale
	 */
	
	


	public Sale addSale(String title, String description, int status, float price,  Date pubDate, File file)  {
		
		Sale sale=new Sale(title, description, status, price,  pubDate, file, this);
        sales.add(sale);
        return sale;
	}
	
	public List<Mugimendua> getMugimenduak(){
		return mugimenduList;
	}
	
	
public void addBought(Sale boughtsale) {
	
		boughtsale.setBought(true);
		boughtsales.add(boughtsale);
		
	}

public void diruaSartu(double diruKop, Date data) {
	diruTotala = diruTotala + diruKop;
	addMugimendua(data, "Dirua sartu da kontuan", diruKop);
    
}
	
	public void diruaAtera(double diruKop, Date data) {
	
		diruTotala = diruTotala - diruKop;
		addMugimendua(data, "Dirua kendu da kontutik", -diruKop);
		
	}
	
	/**
	 * This method checks if the ride already exists for that driver
	 * 
	 * @param from the origin location 
	 * @param to the destination location 
	 * @param date the date of the ride 
	 * @return true if the ride exists and false in other case
	 */
	public boolean doesSaleExist(String title)  {	
		for (Sale s:sales)
			if ( s.getTitle().compareTo(title)==0 )
			 return true;
		return false;
	}
		
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Seller other = (Seller) obj;
		if (email != other.email)
			return false;
		return true;
	}
	
	public Mugimendua addMugimendua(Date data, String deskripzioa, Double dirua)  {
		Mugimendua mov = new Mugimendua(data, deskripzioa, dirua);
        mugimenduList.add(mov);
        return mov;
	}
	
	public void removeSale(float price) {
		diruTotala = diruTotala + price;
	}
	

	
}
