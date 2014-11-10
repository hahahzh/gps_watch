package models;

import javax.persistence.Entity;
import javax.persistence.Table;

import play.data.validation.Match;
import play.data.validation.Required;
import play.data.validation.Unique;
import play.db.jpa.Model;

@Table(name = "role_type")
@Entity
public class RoleType extends Model {
	
	@Required
	@Unique(message = "名称不能重复")
	public String role_name;

	public String toString() {
		return role_name;
	}
}