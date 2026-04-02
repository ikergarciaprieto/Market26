package domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Erreklamazioa {
	
	@XmlID
	@Id 
	@GeneratedValue
    private Long id;
	@Temporal(TemporalType.TIMESTAMP)
	private Date data;
	private String deskripzioa;
	private Sale sale;
	
	public Erreklamazioa() {
		super();
	}
	
	public Erreklamazioa(Sale sale, Date data, String deskripzioa) {
		this.data = data;
		this.deskripzioa = deskripzioa;
		this.sale = sale;
	}
	
	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getDeskripzioa() {
		return deskripzioa;
	}
	public void setDeskripzioa(String deskripzioa) {
		this.deskripzioa = deskripzioa;
	}
	
}
