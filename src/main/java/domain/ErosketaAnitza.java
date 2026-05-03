package domain;

import java.util.ArrayList;
import java.util.Date;
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

import configuration.UtilDate;


@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class ErosketaAnitza {
	@XmlID
	@Id 
	@GeneratedValue
    private Long id;
	private double prezioa;
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<Sale> sales;
	private Seller seller;
	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}
	private Seller user;
	
	public ErosketaAnitza(Seller user, Seller seller) {
		this.prezioa=0;
		this.sales=new ArrayList<Sale>();
		this.user=user;
		this.seller=seller;
	
	}
	
	public double getPrezioa() {
		return prezioa;
	}
	public void setPrezioa(double prezioa) {
		this.prezioa = prezioa;
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
	
	public void DESTROY() {
		this.sales=null;;
		this.prezioa=0.0;
		this.seller= null;
	}
	
	public void addSales(Sale s) {
		this.sales.add(s);
	}
	public void gehituPrezioa(double p) {
		this.prezioa=this.prezioa + p;
	}
	
	public void denaBought() {
		Sale si= null;
		String azalpena="";
		for(int i=0; i<sales.size(); i++) {
			si= sales.get(i);
			user.addBoughtWithoutMugi(si);
			azalpena+=si.getTitle()+ "";
		}
		Double dt=seller.getDiruTotala();
		seller.setDiruTotala(dt+prezioa);
		Date date = UtilDate.trim(new Date());
		seller.addMugimendua(date, azalpena, prezioa);
		user.addMugimendua(date,azalpena,-prezioa);
		DESTROY();
	}
	
	

}
