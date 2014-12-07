package models;


import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import play.data.validation.Required;
import play.db.jpa.Model;

@Table(name = "serial_number")
@Entity
public class SN extends Model {

	@Index(name = "serial_number_sn")
	public String sn;
}