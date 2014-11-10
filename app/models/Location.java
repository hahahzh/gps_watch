package models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.jpa.JPA;
import play.db.jpa.Model;

@Entity
@Table(name="locations")
public class Location extends Model{

	@ManyToOne(cascade = CascadeType.ALL, optional = false)
	public RWatch rwatch;
	/**
	 * 定位器纬度，格式为DD.FFFFFF
	 */
	public double latitude;
	/**
	 * 定位器经度,格式为DDD.FFFFFF
	 */
	public double longitude;
	
	/**
	 * 基站信息定位器纬度，格式为DD.FFFFFF
	 */
	public double cell_latitude;
	/**
	 * 基站信息定位器经度,格式为DDD.FFFFFF
	 */
	public double cell_longitude;
	
	/**
	 * 基站精度,单位米
	 */
	public int cell_accuracy; 
	/**
	 * 基站坐标类型
	 */
	public int cell_coordinateType;
	
	
	/**
	 * 速度，单位m/s，保留两位小数
	 */
	public double speed;
	/**
	 * 方位角，正北为0 度，分辨率1 度，顺时针方向
	 */
	public int direction;
	/**
	 * 该位置信息的时间，最大长度16（YYYYMMDDTHHMMSSZ），标准时间，例如20110712T054025Z
	 */
	public Date dateTime;
	
	/**
	 * 服务器收到坐标的时间
	 */
	public Date receivedTime;
	/**
	 * 状态码
	 */
	public String status;
	/**
	 * 是否被家长机读取过
	 */
	public boolean readed;
	/**
	 * 国家码
	 */
	public int mcc;
	/**
	 * 移动网号码
	 */
	public int mnc;
	/**
	 * 位置区域码
	 */
	public int lac;
	/**
	 * 基站id
	 */
	public int cellid;
	/**
	 * 位置信息是否有效
     * 1 有效
     * 0 无效
	 */
	public int valid;
	/**
	 * 纬度标志
     * 1 北纬
     * 2 南纬
	 */
	public int latitudeFlag;
	/**
	 * 经度标志
     * 1 东经
     * 0 西经
	 */
	public int longitudeFlag;

	@Override
	public String toString(){
		return "latitude = "+latitude+" longitude = "+longitude;
	}
	
}