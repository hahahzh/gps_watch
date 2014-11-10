package models;

import javax.persistence.Entity;
import javax.persistence.Table;

import play.data.validation.Required;
import play.data.validation.Unique;
import play.db.jpa.Model;

@Table(name = "mobile_type")
@Entity
public class MobileType extends Model {

	@Required
	public String type;
	
	public String toString(){
		return type;
	}
}