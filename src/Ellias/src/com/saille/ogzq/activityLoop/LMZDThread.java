package com.saille.ogzq.activityLoop;

import org.apache.http.NameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.ArrayList;

import com.saille.ogzq.OgzqURL;
import com.saille.ogzq.IDUtils;

/**
 * Created by IntelliJ IDEA.
 * User: Ellias
 * Date: 2015-12-11
 * Time: 17:36:27
 * To change this template use File | Settings | File Templates.
 */
public class LMZDThread extends Thread {
    private final static Logger LOGGER = Logger.getLogger(LMZDThread.class);
    private String id;

    public LMZDThread(String id) {
        this.id = id;
    }

    public void run() {
        while(true) {
            try {
                HttpPost pm = new HttpPost(OgzqURL.URL + "/OGLM.aspx");
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("Limzdz1", "-1"));
                pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                String return_str = IDUtils.execute(id, pm);

                if(return_str.indexOf("inmatch|") != -1) {
                    LOGGER.info("������,��5������");
                    Thread.sleep(5000L);
                    continue;
                }
                String[] list = return_str.split("@");
                String str = list[3];
                LOGGER.debug(str);
                if (StringUtils.isBlank(str)) {
                    Thread.sleep(5000l);
                    continue;
                }
                String[] clublist = str.split("[*]");
                if (Integer.parseInt(list[6]) > 10) {
                  LOGGER.debug("����δ��ʱ��:" + list[6] + ",��Ϣ3��");
                  Thread.sleep(3000L);
                  continue;
                }
                for(int i = 0; i < clublist.length; i++) {
                    list = clublist[i].split("[|]");
                    pm = new HttpPost(OgzqURL.URL + "/OGLM.aspx");
                    params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("Limzdz1", list[0]));
                    pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                    return_str = IDUtils.execute(id, pm);
                    LOGGER.debug(return_str);
                    if(return_str.indexOf("inmatch|") != -1) {
                        Thread.sleep(2000L);
                        continue;
                    }
                    if (StringUtils.isBlank(str)) {
                        Thread.sleep(5000l);
                        continue;
                    }
                    str = return_str.split("[@]")[4];
                    if(str.equals("-1")) {
                        continue;
                    }
                    String[] kengList = str.split("[*]");
                    int[] ilist = { 1, 3, 5, 7, 0, 2, 6, 8 };
//                    int[] arrayOfInt1;
//                    int j = (arrayOfInt1 = ilist).length;
                    for(int j = 0; j < ilist.length; j++) {
                        String[] list2 = kengList[ilist[j]].split("\\&");
                        if (list2[0].equals("1")) {
                            pm = new HttpPost(OgzqURL.URL + "/OGLM.aspx");
                            params = new ArrayList<NameValuePair>();
                            params.add(new BasicNameValuePair("Qianzhan1", ilist[j] + "*" + list[0]));
                            pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                            return_str = IDUtils.execute(id, pm);
                            if(return_str.equals("1")) {
                                return_str = "success";
                            } else if(return_str.equals("-999")) {
                                return_str = "������ָ����ô����ʮ���Ӻ�����";
                            } else if(return_str.equals("-888")) {
                                return_str = "�������Ѳ�������ʱ����ڵ����£����ɼ�������";
                            } else if(return_str.equals("-1")) {
                                return_str = "�Ѿ�ռ���������򳡣�������ռ��Ŷ��";
                            } else if(return_str.equals("-5")) {
                                return_str = "ΪʲôҪ���Լ������򳡣��ѵ��������";
                            } else if(return_str.equals("-6")) {
                                return_str = "��������ս�ѽ�ֹ-6";
                            } else if(return_str.equals("-7")) {
                                return_str = "��������ս�ѽ�ֹ-7";
                            } else if(Integer.parseInt(return_str) > 0) {
                                return_str = "��Ҫ�������CD:" + return_str;
                            }
                            LOGGER.info("���й���������=" + ilist[j] + "*" + list[0] + "�����=" + return_str);
                        }
                    }
                }
                Thread.sleep(1000L);
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
