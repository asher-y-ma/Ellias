package com.saille.jy;

import org.codehaus.jettison.json.JSONObject;

import java.lang.reflect.Field;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: Ellias
 * Date: 2016-11-23
 * Time: 20:17:44
 * To change this template use File | Settings | File Templates.
 */
public class Person {
    private String name;
    private int hp; //����
    private int mp; //����
    private int level; //�ȼ�
    private int gongji; //����
    private int fangyu; //����
    private int mingzhong; //����
    private int shanbi; //����

    public static Person fromJSON(String str) {
        try {
            JSONObject json = new JSONObject(str);
            Iterator it = json.keys();
            Field[] fields = Person.class.getDeclaredFields();
            Person ret = new Person();
            while(it.hasNext()) {
                String key = it.next().toString();
                Object value = json.get(key);
                for(Field f : fields) {
                    if(f.getName().equals(key)) {
                        f.set(ret, value);
                    }
                }
            }
            return ret;
        } catch(Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String toJSON() {
        try {
            JSONObject json = new JSONObject();
            Field[] fields = this.getClass().getDeclaredFields();
            for(Field f : fields) {
                String name = f.getName();
                Object obj = f.get(this);
                if(obj instanceof String) {
                    json.put(name, obj == null ? "" : (String)obj);
                } else if(obj instanceof Integer) {
                    json.put(name, ((Integer) obj).intValue());
                } else if(obj instanceof Double) {
                    json.put(name, ((Double) obj).doubleValue());
                }
            }
            return json.toString();
        } catch(Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getGongji() {
        return gongji;
    }

    public void setGongji(int gongji) {
        this.gongji = gongji;
    }

    public int getMingzhong() {
        return mingzhong;
    }

    public void setMingzhong(int mingzhong) {
        this.mingzhong = mingzhong;
    }

    public int getShanbi() {
        return shanbi;
    }

    public void setShanbi(int shanbi) {
        this.shanbi = shanbi;
    }

    public int getFangyu() {
        return fangyu;
    }

    public void setFangyu(int fangyu) {
        this.fangyu = fangyu;
    }
}
