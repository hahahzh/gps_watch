package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.Model;

@Table(name = "smsLog")
@Entity
public class SMSLog extends Model {

	@Required
	@MaxSize(70)
	public String content;
	
	public String phoneNos;

	public String updatetime; 
	
	public String toString() {
		return content;
	}
}