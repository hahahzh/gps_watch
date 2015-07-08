package models;

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
@Table(name = "white_phone")
@Entity
public class WhitePhone extends Model {

    /**
     * 定位器
     */
    @Required
    @ManyToOne(optional = false, cascade = CascadeType.REMOVE)
    public RWatch rWatch;

    /**
     * 电话号
     */
    public String m_number;

    @Required
    @MaxSize(9)
    public String name;
}
