package com.saille.ogzq.dailyLoop;

import org.apache.log4j.Logger;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.NameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.message.BasicNameValuePair;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import com.saille.ogzq.ConfigUtils;
import com.saille.ogzq.IDUtils;
import com.saille.ogzq.OperationUtils;
import com.saille.ogzq.OgzqURL;

/**
 * Created by IntelliJ IDEA.
 * User: Ellias
 * Date: 2016-5-27
 * Time: 12:58:19
 * To change this template use File | Settings | File Templates.
 */
public class Worldcup32Thread extends ParentThread{
    private final static Logger LOGGER = Logger.getLogger(Worldcup32Thread.class);
    private static Worldcup32Thread instance;

    private Worldcup32Thread() {
        threadName = "32ǿѲ����";
        setThreadname(threadName);
    }

    public synchronized static Worldcup32Thread getInstance() {
        if(instance == null) {
            instance = new Worldcup32Thread();
        }
        return instance;
    }

    public void run() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("HHmm");
        while(true) {
            try {
                String today = sdf.format(new Date());
                String now = sdf2.format(new Date());
                long sysTime = new Date().getTime();
                lastCheckTime = sdf3.format(new Date());
                
                if(lastDate == null || today.compareTo(lastDate) > 0) { //���µ�һ��
                    LOGGER.info("��ǰ���ڣ�" + today + "������32ǿѲ����");
                    lastDate = today;
                }
                if(now.compareTo(ConfigUtils.BEGINTIME) <= 0) {
                    LOGGER.info("��ǰʱ�䣺" + now + "��С���趨��ʱ�䣺" + ConfigUtils.BEGINTIME + "���ȴ�60��");
                    Thread.sleep(1000 * 60);
                    continue;
                }

                List<String> ids = IDUtils.GETIDS();
                LOGGER.info("32ǿѲ����ѭ��" + ids.size() + "����");
                for(String id : ids) {
                    if(targetTime.containsKey(id) && targetTime.get(id).longValue() > sysTime) {
                        continue;
                    }
                    int result = OperationUtils.doWorldcup32(id);
                    targetTime.put(id, sysTime + result);
                }
                long minvalue = getWaitTime(sysTime);
                LOGGER.info("32ǿѲ����ѭ���������ȴ�" + minvalue / 1000 + "��");
                Thread.sleep(minvalue);
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static int doWorldcup32(String email) throws Exception {
        int page = 0;
        boolean cont = true;
        while(cont) {
            try {
                HttpPost pm = new HttpPost(OgzqURL.URL + "/WorldCup32.aspx");
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("Load1", "" + page));
                pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                String ret = IDUtils.execute(email, pm);
                String[] parts = ret.split("#");
                if(parts[0].indexOf("inmatch|") >= 0) { //������
                    return 1000 * 5;
                } else if(parts[0].split("@").length > 3 && (!"".equals(parts[0].split("@")[3]))) { //��Ҫ�콱
                    String[] cards = parts[0].split("@")[3].split("[|]");
                    boolean hasOpened = false;
                    for(String card : cards) {
                        if(card.split("[*]")[0].equals("0")) {
                            hasOpened = true;
                            break;
                        }
                    }
                    if(!hasOpened) {
                        pm = new HttpPost(OgzqURL.URL + "/WorldCup32.aspx");
                        params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("FlopCard1", "" + ((new Random().nextInt() % 5) + 1)));
                        pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                        ret = IDUtils.execute(email, pm);
                    }

                    pm = new HttpPost(OgzqURL.URL + "/WorldCup32.aspx");
                    params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("AcceptCardAward1", ""));
                    pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                    ret = IDUtils.execute(email, pm);
                    LOGGER.info(email + "/" + IDUtils.getNick(email) + "32ǿѲ�����콱�����" + ret);
                    return 1000 * 5;
                } else { //�����
                    if(parts[0].split("@").length < 3) {
                        return 1000 * 10;
                    }
                    String[] teams = parts[0].split("@")[2].split("[\\^]");
                    for(String t : teams) {
                        int totaltime = Integer.parseInt(t.split("[*]")[4]);
                        if(totaltime == 0) {
                            cont = false;
                        }
                        int resttime = Integer.parseInt(t.split("[*]")[3]);
//                        if(Integer.parseInt(t.split("[*]")[3]) < Integer.parseInt(t.split("[*]")[4])) {
                        if(resttime > 0) {
                            pm = new HttpPost(OgzqURL.URL + "/WorldCup32.aspx");
                            params = new ArrayList<NameValuePair>();
                            params.add(new BasicNameValuePair("Challenge1", t.split("[*]")[0]));
                            pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                            ret = IDUtils.execute(email, pm);
                            LOGGER.info(email + "/" + IDUtils.getNick(email) + "��32ǿѲ������" + ret);
                            if(Integer.parseInt(ret) > 0) {
                                return 1000 * 300;
                            }
                        }
                    }
                    page++;
                    if(page >= 4) {
                        //�ܴ�Ķ�������
                        LOGGER.info(email + "/" + IDUtils.getNick(email) + "32ǿѲ������������");
                        return 1000 * 60 * 60;
                    }
                }
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
        //�ܴ�Ķ�������
        return 1000 * 60 * 60;
    }
}
