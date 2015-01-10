package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import play.db.jpa.Model;

@Entity
@Table(name="bs_locations")
public class BSLocation extends Model{
	/**
	 * 国家码
	 */
	public int mcc;
	/**
	 * 移动网号码
	 */
	public int mnc;
	
	public int lac;
	public int cellid;
	String signal;
	
	/**
	 * 定位器纬度，格式为DD.FFFFFF
	 */
	public double latitude;
	/**
	 * 定位器经度,格式为DDD.FFFFFF
	 */
	public double longitude;
	/**
	 * 精度,单位米
	 */
	public int cell_accuracy;
	/**
	 * 坐标类型
	 */
	public int cell_coordinateType;
	
	public int ta;
	/**
	 * 更新时间
	 */
	public Date updateTime;
}