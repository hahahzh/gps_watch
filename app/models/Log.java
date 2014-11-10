package models;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import play.db.jpa.Model;

@Table(name = "logs")
@Entity
public class Log extends Model {

	public Long c_id;
	
	public Date updatetime;

	public String ip;
	
	public String mac;
	
	public String imei;
	
	public String imsi;
	
	public String type;
	
	public String toString() {
		return c_id+"";
	}
}