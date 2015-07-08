package models;

import org.hibernate.annotations.Index;
import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by samliu on 15/1/6.
 */
@Table(name = "no_disturbing")
@Entity
public class NoDisturbing extends Model {

    /**
     * 定位器
     */
    @Required
    @ManyToOne(optional = false, cascade = CascadeType.REMOVE)
    public RWatch rWatch;

    /**
     * 序号
     */
    public Integer num;

    /**
     * 一周各天
     */
    public Boolean monday;
    public Boolean tuesday;
    public Boolean wensday;
    public Boolean thusday;
    public Boolean friday;
    public Boolean satuday;
    public Boolean sunday;

    @Required
    @MaxSize(5)
    public String fromTime;
    @Required
    @MaxSize(5)
    public String toTime;
}
