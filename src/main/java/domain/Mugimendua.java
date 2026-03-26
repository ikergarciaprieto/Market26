package domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Mugimendua {

	@XmlID
	@Id 
	private String deskripzioa;
	private Date data;
	private Double dirua;
	
	public Mugimendua() {
		super();
	}

	public Mugimendua(Date data, String deskripzioa, Double dirua) {
		this.data = data;
		this.deskripzioa = deskripzioa;
		this.dirua = dirua;
	}
	public String getDeskripzioa() {
		return deskripzioa;
	}

	public void setDeskripzioa(String deskripzioa) {
		this.deskripzioa = deskripzioa;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Double getDirua() {
		return dirua;
	}

	public void setDirua(Double dirua) {
		this.dirua = dirua;
	}

}
