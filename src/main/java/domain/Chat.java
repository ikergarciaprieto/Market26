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

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Chat {
	@XmlID
	@Id 
	@GeneratedValue
    private Long id;
	private Seller user1;
	private Seller user2;
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<Mezua> mezuak = new ArrayList<Mezua>();
	
	public Seller getUser1() {
		return user1;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public void setUser1(Seller user1) {
		this.user1 = user1;
	}


	public Seller getUser2() {
		return user2;
	}


	public void setUser2(Seller user2) {
		this.user2 = user2;
	}


	public List<Mezua> getMezuak() {
		return mezuak;
	}


	public void setMezuak(List<Mezua> mezuak) {
		this.mezuak = mezuak;
	}


	public Chat(Seller user1 , Seller user2) {
		this.user1=user1;
		this.user2=user2;
		
	}


	@Override
	public String toString() {
		return ("Chat: "+user1.getEmail()+"; "+user2.getEmail());
	}
	public void addMezuak(Mezua m) {
		this.mezuak.add(m);
	}
	public Mezua createMezua(String mezu,Date date,Seller bidaltze) {
		Mezua m = new Mezua(mezu, date, bidaltze, this);
		return m;
	}

}
