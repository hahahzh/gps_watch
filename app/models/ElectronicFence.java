package models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import play.data.validation.Required;
import play.db.jpa.Model;

@Table(name="electronic_fence")
@Entity
public class ElectronicFence extends Model{
	/**
	 * 定位器
	 */
	@ManyToOne(optional = false, cascade = CascadeType.REMOVE)
	public RWatch rWatch;
	/**
	 * 是否开启或者关闭
	 */
	@Column(name="switch_on")
	public Integer on;
	/**
	 * 定位器纬度，格式为DD.FFFFFF
	 */
	public double lat;
	/**
	 * 定位器经度,格式为DDD.FFFFFF
	 */
	public double lon;
	
	/**
	 * 半径
	 */
	public double radius;
	
	/**
	 * 最后修改日期
	 */
	public Date dateTime;
	
	@Required
	public Integer num;
	
	public String toString() {
		return "1";
	}
	
}