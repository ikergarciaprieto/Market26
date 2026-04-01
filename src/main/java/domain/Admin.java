package domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Admin {
	
	@XmlID
	@Id
	private String NAN;
	private String izena;
	private String pasahitza;

	public Admin(String NAN, String izena, String pasahitza) {
		this.NAN = NAN;
		this.izena = izena;
		this.pasahitza = pasahitza;
		
	}

	public Admin() {
		super();
	}

	public String getNAN() {
		return NAN;
	}

	public void setNAN(String nAN) {
		NAN = nAN;
	}

	public String getIzena() {
		return izena;
	}

	public void setIzena(String izena) {
		this.izena = izena;
	}

	public String getPasahitza() {
		return pasahitza;
	}

	public void setPasahitza(String pasahitza) {
		this.pasahitza = pasahitza;
	}
}
