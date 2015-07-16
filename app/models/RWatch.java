package models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import play.data.validation.MaxSize;
import play.data.validation.Phone;
import play.data.validation.Required;
import play.db.jpa.Blob;
import play.db.jpa.Model;

@Table(name = "rwatch")
@Entity
public class RWatch extends Model {

	@MaxSize(30)
	public String imei;
	
	public String mac;
	
	public String m_number;
	
	@Phone
	public String guardian_number1;
	public String name_number1;
	
	@Phone
	public String guardian_number2;
	public String name_number2;
	
	@Phone
	public String guardian_number3;
	public String name_number3;
	
	@Phone
	public String guardian_number4;
	public String name_number4;
	
	public String nickname;
	
	public String serialNumber;
	
	public String channel;
	
	public String whiteList;
	
	public Date bindDate;
	
	public Integer mode;
	
	//M28 M26
	@ManyToOne(cascade= CascadeType.REFRESH, optional = true)
	public Production production;
	
	// @Required
	// @ManyToOne(fetch=FetchType.LAZY,cascade= CascadeType.REFRESH, optional = true)
	@ManyToOne(cascade= CascadeType.REFRESH, optional = true)
	public Customer c;

	public Boolean remind_open_close;

	public Boolean remind_low_power;
	
	public String toString(){
		if(nickname != null)return nickname;
		else if(m_number != null) return m_number;
		else return guardian_number1;
	}
}