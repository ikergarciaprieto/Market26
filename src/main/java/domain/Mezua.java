package domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Mezua {
	@XmlID
	@Id 
	@GeneratedValue
    private Long id;
	private String mezua;
	private Date date;
	private Seller bidaliDuena;
	private Chat chat;
	
	public Mezua(String mezua, Date data,Seller bidali,Chat ch) {
		this.mezua=mezua;
		this.date=data;
		this.bidaliDuena=bidali;
		this.chat=ch;
	}

	public String getMezua() {
		return mezua;
	}

	public void setMezua(String mezua) {
		this.mezua = mezua;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Seller getBidaliDuena() {
		return bidaliDuena;
	}

	public void setBidaliDuena(Seller bidaliDuena) {
		this.bidaliDuena = bidaliDuena;
	}

	public Chat getChat() {
		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}

	@Override
	public String toString() {
		String a = (mezua+" ["+bidaliDuena.getEmail()+"; "+date+"]");
		return a;
	}
	
}
