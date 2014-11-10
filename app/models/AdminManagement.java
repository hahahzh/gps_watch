package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.data.validation.MaxSize;
import play.data.validation.MinSize;
import play.data.validation.Password;
import play.data.validation.Required;
import play.db.jpa.Model;

@Table(name = "appadmin")
@Entity
public class AdminManagement extends Model {

	@Required
	@MaxSize(15)
	@MinSize(3)
	public String name;

	@Required
	@MaxSize(15)
	@MinSize(5)
	@Password
	public String pwd;
	
	@Required
	@ManyToOne(fetch=FetchType.LAZY,cascade=javax.persistence.CascadeType.REFRESH, optional = true)
	public RoleType role;

	public String toString() {
		return name;
	}
}