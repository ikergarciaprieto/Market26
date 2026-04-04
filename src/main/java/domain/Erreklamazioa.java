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
	public long getId() {
		return id;
	}

	@Temporal(TemporalType.TIMESTAMP)
	private Date data;
	private String deskripzioa;
	private Sale sale;
	private String onartua;
	private Seller erreklamatzenDuena;
	// -onartua- String bat da hiru posizio edo daudelako, -ez begiratuta-, -onartua- eta -ez onartua-
	
	
	public Erreklamazioa() {
		super();
	}

	public Erreklamazioa(Sale sale, Date data, String deskripzioa, Seller AerreklamatzenDuena) {
		this.data = data;
		this.deskripzioa = deskripzioa;
		this.sale = sale;
		this.erreklamatzenDuena = AerreklamatzenDuena;
		this.onartua="ez begiratuta";
	}
	
	public Seller getErreklamatzenDuena() {
		return erreklamatzenDuena;
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
	
	public String toString() {
		return sale.toString() + data.toString() +""+  deskripzioa; 
	}
	
	public String getOnartua() {
		return onartua;
	}
	
	public void setOnartua(String onartua) {
		this.onartua = onartua;
	}
	
}
