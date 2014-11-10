package models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.data.validation.URL;
import play.db.jpa.Blob;
import play.db.jpa.Model;
import utils.DateUtil;
import controllers.CRUD.Hidden;

@Table(name = "clientversion")
@Entity
public class ClientVersion extends Model {

	@URL
	public String url;
	
	@MaxSize(200)
	public String update_desc;
	
	@Required
	@Index(name = "idx_version")
	public String version;
	
	@Required
	public Blob apk;
	
	@Hidden
	public String updatetime = DateUtil.reverseDate(new Date(), 0);
	
	@Required
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	public MobileType mobiletype;
	
	public String toString() {
		return mobiletype.type + "-" + version;
	}
}