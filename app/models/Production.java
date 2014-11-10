package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Index;

import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.Model;
import utils.DateUtil;
import controllers.CRUD.Hidden;

@Table(name = "production")
@Entity
public class Production extends Model {

	@Required
	@Index(name="p_name_index")
	public String p_name;
	
	@Column(columnDefinition="TEXT")
	@MaxSize(500)
	public String p_desc;
	
	@Hidden
	public String updatetime = DateUtil.reverseDate(new Date(), 0);
	
	public String toString() {
		return p_name;
	}
}
