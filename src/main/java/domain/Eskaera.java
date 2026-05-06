package domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Eskaera {
	@XmlID
	@Id 
	@GeneratedValue
    private Long eskaeraNumber;
	private String title;
	private String desk;
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<Sale> sales;
	private Seller user;
	
	
	public Eskaera(String title, String desk, Seller user) {
		this.title=title;
		this.desk=desk;
		this.user=user;
	}
	
	public Long getEskaeraNumber() {
		return eskaeraNumber;
	}


	public void setEskaeraNumber(Long eskaeraNumber) {
		this.eskaeraNumber = eskaeraNumber;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDesk() {
		return desk;
	}


	public void setDesk(String desk) {
		this.desk = desk;
	}


	public List<Sale> getSales() {
		return sales;
	}


	public void setSales(List<Sale> sales) {
		this.sales = sales;
	}


	public Seller getUser() {
		return user;
	}


	public void setUser(Seller user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return (this.title+" ["+this.desk+"]");
	}

	

}
