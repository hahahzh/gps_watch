/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * JSON工具类，存放通用的JSON操作
 * @author hanzhao
 */
public abstract class JSONUtil {

    /**
     * 获取一个空白的JSON对象
     * @return
     */
    public static JSONObject getNewJSON() {
            return new JSONObject();
    }
    
    /**
     * 获取一个空白的JSON对象
     * @return
     */
    public static JSONArray getNewJSONArray() {
            return new JSONArray();
    }

    
}
