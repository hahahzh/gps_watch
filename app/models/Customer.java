package models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Index;

import play.data.validation.Email;
import play.data.validation.Match;
import play.data.validation.MaxSize;
import play.data.validation.MinSize;
import play.data.validation.Password;
import play.data.validation.Phone;
import play.data.validation.Required;
import play.db.jpa.Blob;
import play.db.jpa.Model;
import controllers.CRUD.Hidden;

@Table(name = "customer")
@Entity
public class Customer extends Model {

	@MaxSize(5)
	public String cid;

	// 1 IOS正版 2 Android 3 IOS越狱  4 IPad 5 OPad
	public int os;

	@Required
	@Phone
	@Index(name = "idx_m_number")
	public String m_number;

	@Required
	@MaxSize(15)
	@MinSize(6)
	@Match(value = "^\\w*$", message = "Not a valid username")
	public String nickname;

	@Required
	@MaxSize(15)
	@MinSize(5)
	@Password
	public String pwd;

	public String imei;
	
	public String imsi;
	
	public String mac;

	@Hidden
	public Date updatetime;
	// 0 男 1 女
	public Byte gender;
	
	@Email
	@Index(name = "idx_email")
	public String email;
	
	@Index(name = "idx_weixin")
	public String weixin;
	
	public String city;
	
	public Date birthday;
	
	public String height;
	
	public String weight;
	
	public String step;
	
	public Blob portrait;
	
	public String toString() {
		return nickname;
	}
}
