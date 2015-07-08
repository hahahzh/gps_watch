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

@Table(name="time_interval")
@Entity
public class TimeInterval extends Model{
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
	 * 最后修改日期
	 */
	public Date dateTime;
	
	public String startTime;
	
	public String endTime;
	
	@Column(name="t_interval")
	public Integer interval;
	
	// 0 免打扰 1 时段1 2 时段2 .。。
	public Integer type;
	
	public String toString() {
		return "1";
	}
	
}