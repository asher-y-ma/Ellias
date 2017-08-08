package com.saille.ogzq;

import org.apache.http.NameValuePair;
import org.apache.http.HttpResponse;
import org.apache.http.protocol.HTTP;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.HttpClient;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import com.saille.util.CommonUtils;
import com.saille.ogzq.LoginUtils;
import com.saille.ogzq.OgzqURL;

/**
 * Created by IntelliJ IDEA.
 * User: Ellias
 * Date: 2013-10-26
 * Time: 18:02:32
 * To change this template use File | Settings | File Templates.
 */
public class RobNaiBa2 extends Thread {
    private HttpClient client;
    private Logger LOGGER = Logger.getLogger(this.getClass());
    private String id;
    private String nick;
    public boolean cont = true;

    public static void main(String[] args) {
        RobNaiBa2 instance = new RobNaiBa2("yuliang0526@163.com", "yuliang83012", "������", 0, 24, 0, 100);
        instance.start();
    }
    public RobNaiBa2(String id, String pwd, String nick, int robIndex, int lingxian, int robType, int max) {
        this.id = id;
        this.nick = nick;
        try {
            this.client = LoginUtils.Login(id, pwd);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    public void run() {
        while(cont) {
            try {
                HttpPost pm = new HttpPost(OgzqURL.URL + "/Activity.aspx");
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("NaiBaLoad1", "1"));
                pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                String s = execute(pm);
                String[] ss = s.split("@");
                int restTime = Integer.parseInt(ss[ss.length - 1]);
                LOGGER.info("ʣ��ʱ�䣺" + restTime);
                if(restTime <= 0) {
                    LOGGER.info("ʣ��ʱ����0������");
                    cont = false;
                    break;
                }
                if(restTime > 3) {
                    LOGGER.info(this.id + "/" + this.nick + "��ʣ��ʱ�䣺" + restTime + "�룬����3�룬����");
                } else {
                    for(int i = 0; i < 20; i++) {
                        LOGGER.info(this.id + "/" + this.nick + ":" + i + "/30");
                        pm = new HttpPost(OgzqURL.URL + "/Activity.aspx");
                        params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("BuyNaiBa1", "10159"));
                        pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                        LOGGER.info(this.id + "/" + this.nick + ":" + parseResult(execute(pm)));
                    }
                    cont = false;
                    break;
                }
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
        LOGGER.info(this.id + "/" + this.nick + "===�̰�ѭ������===");
    }

    private String parseResult(String s) {
        String ret;
        if(s.equals("-1") || s.equals("-2") || s.equals("-3")) {
            if("-1".equals(s)) {
                ret = ("===��Ҳ���===");
            } else {
                ret = ("===����ʧ��===");
            }
        } else {
            ret = s;
        }
        return s + ":" + ret;
    }

    private String execute(HttpPost pm) {
        int count = 0;
        while(count < 3) {
            try {
                if(client == null) {
                    return "";
                }
                String ret = "";
                synchronized(client) {
                    pm.getParams().setParameter("Connection", "keep-alive");
                    HttpResponse response = client.execute(pm);
                    ret = CommonUtils.getString(response.getEntity().getContent(), "utf-8");
//                    pm.abort();
                    pm.releaseConnection();
                    if(ret.indexOf("ASP.NET") != -1 || ret.indexOf("δ�������������õ������ʵ��") != -1) {

                    }
                }
                return ret;
            } catch(Exception ex) {
                System.out.println(this.id + "/" + pm.getURI().getPath() + " occurs error: " + ex.getMessage());
//                ex.printStackTrace();
                count++;
                continue;
            }
        }
        return null;
    }
}
