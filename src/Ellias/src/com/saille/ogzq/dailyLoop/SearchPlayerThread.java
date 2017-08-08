package com.saille.ogzq.dailyLoop;

import org.apache.log4j.Logger;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.NameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.message.BasicNameValuePair;

import java.text.SimpleDateFormat;
import java.util.*;

import com.saille.ogzq.ConfigUtils;
import com.saille.ogzq.IDUtils;
import com.saille.ogzq.OperationUtils;
import com.saille.ogzq.OgzqURL;

/**
 * Created by IntelliJ IDEA.
 * User: Ellias
 * Date: 2016-5-25
 * Time: 15:51:11
 * To change this template use File | Settings | File Templates.
 */
public class SearchPlayerThread extends ParentThread {
    private final static Logger LOGGER = Logger.getLogger(SearchPlayerThread.class);
    private static SearchPlayerThread instance;

    private SearchPlayerThread() {
        threadName = "��Ա����";
        setThreadname(threadName);
    }

    public synchronized static SearchPlayerThread getInstance() {
        if(instance == null) {
            instance = new SearchPlayerThread();
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
                    lastDate = today;
                }
                if(now.compareTo(ConfigUtils.BEGINTIME) <= 0) {
                    LOGGER.info("��ǰʱ�䣺" + now + "��С���趨��ʱ�䣺" + ConfigUtils.BEGINTIME + "���ȴ�300��");
                    Thread.sleep(1000 * 60 * 5);
                    continue;
                }

                List<String> ids = IDUtils.GETIDS();
                LOGGER.info("������Աѭ��" + ids.size() + "����");
                for(String id : ids) {
                    if(targetTime.containsKey(id) && targetTime.get(id).longValue() > sysTime) {
                        continue;
                    }

                    if(ConfigUtils.getConf(id, "�Ƿ�������Ա").equals("1")) {
                        OperationUtils.afterFindPlayer(id);
                        int time = OperationUtils.findPlayer(id);

                        if(time > 0) {
                            LOGGER.info(id + "/" + IDUtils.getNick(id) + "������Ա����ʱ" + time + "��");
                            targetTime.put(id, sysTime + time * 1000);
                        } else {
                            if(time == -1) {
                                LOGGER.info(id + "/" + IDUtils.getNick(id) + "��Ա����λ�ò�������������Ա");
                            } else if(time == -2) {
                                LOGGER.info(id + "/" + IDUtils.getNick(id) + "�ɻ������Ѿ�����");
                            } else if(time == -10) {
                                LOGGER.info(id + "/" + IDUtils.getNick(id) + "����˳�򣺷���<�¼�<���<Ӣ��<����");
                            } else if(time == -11) {
                                LOGGER.info(id + "/" + IDUtils.getNick(id) + "�������Ǻ������ﵽ7���ſ�ʹ��");
                            } else if(time == -12) {
                                LOGGER.info(id + "/" + IDUtils.getNick(id) + "�۷�����60������");
                            } else {
                                LOGGER.info(id + "/" + IDUtils.getNick(id) + ": " + time);
                            }
                            targetTime.put(id, sysTime + 1000 * 60 * 10);
                        }
                    }
                }
//                long minvalue = 1000 * 60 * 60 * 2;
//                if(targetTime.size() == 0) {
//                    minvalue = 1000 * 60;
//                } else {
//                    for(String key : targetTime.keySet()) {
//                        long target = targetTime.get(key);
//                        if((target - sysTime) < minvalue) {
//                            minvalue = target - sysTime;
//                        }
//                    }
//                }
//                minvalue = minvalue + new Random().nextInt(5) * 1000;
                long minvalue = getWaitTime(sysTime);
                LOGGER.info("������Աѭ���������ȴ�" + minvalue / 1000 + "��");
                Thread.sleep(minvalue);
//                Thread.sleep(1000 * 60 * 10 + new Random().nextInt(60) * 1000);
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /* ������Ա
    * -1:δ֪����
    * >0: ������ʱ��*/
    public static int findPlayer(String email) throws Exception {
/*
��
��
��
Ӣ
��
��Ӣ
����
Ӣ��
��Ӣ��
����
��Ӣ
����
����Ӣ
������
��Ӣ��
���ⷨ
*/
//        LOGGER.info(email + ": Ѱ����Ա");

        HttpPost pm = new HttpPost(OgzqURL.URL + OgzqURL.MIDDLE_MAN);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("GetMiddleInfo", "1"));
        pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
        String ret = IDUtils.execute(email, pm);
        String level = ret.substring(0, ret.indexOf("|"));
        String conflevel = ConfigUtils.getConf(email, "��Ա������Χ");
        String reslevel = conflevel;
        if(conflevel.equals("-1")) {
            reslevel = level;
        }

        pm = new HttpPost(OgzqURL.URL + OgzqURL.MIDDLE_MAN);
        params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("beforeFindPlayer", "1"));
        params.add(new BasicNameValuePair("LeagueIndex", reslevel));
        pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
        IDUtils.execute(email, pm);

        if(Integer.parseInt(reslevel) <= 5) {
            pm = new HttpPost(OgzqURL.URL + OgzqURL.MIDDLE_MAN);
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("findPlayer", "1"));
            params.add(new BasicNameValuePair("LeagueIndex", reslevel));
            pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            String s = IDUtils.execute(email, pm);
            LOGGER.info(email + "/" + IDUtils.getNick(email) + "������Ա��" + reslevel + "�������" + s);
            if(s.indexOf("|") > 0) {
                return Integer.parseInt(s.split("\\|")[3]);
            } else {
                return -1;
            }
        } else {
            pm = new HttpPost(OgzqURL.URL + "/Ogzd.aspx");
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("SelPlayer1", "" + (Integer.parseInt(reslevel) - 5)));
            pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            String s = IDUtils.execute(email, pm);
            LOGGER.info(email + "/" + IDUtils.getNick(email) + "�����۷���Ա��" + (Integer.parseInt(reslevel) - 5) + "�������" + s);
            if(s.indexOf("|") > 0) {
                return Integer.parseInt(s.split("\\|")[3]);
            } else {
                if("-10".equals(s)) {
                    pm = new HttpPost(OgzqURL.URL + OgzqURL.MIDDLE_MAN);
                    params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("loadPower", "1"));
                    pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                    s = IDUtils.execute(email, pm);
                    return Integer.parseInt(s.split("\\|")[3]);
                } else {
                    return Integer.parseInt(s);
                }
            }
        }
    }

    public static void afterFindPlayer(String email) throws Exception {
        HttpPost pm = new HttpPost(OgzqURL.URL + OgzqURL.MIDDLE_MAN);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("getAndShowFreshMan", "1"));
        pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
        String s = IDUtils.execute(email, pm);
        if(!s.equals("-1")) {
            LOGGER.info(email + "��Ա���������" + s);
            String[] parts = s.split("[|]");
            //5036067|�����|3    |74     |2  |Germany/06/20606.png
            //id     |name  |level|ability|pos|
            if(!IDUtils.checkPlayerInitial(parts[1], parts[4], parts[2])) {
                pm = new HttpPost(OgzqURL.URL + OgzqURL.VIEW_PLAYER);
                params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("LoadPlayer1", parts[0]));
                pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                String ret = IDUtils.execute(email, pm);
                String[] atts = ret.split("[*]");
                String[] abis = new String[8];
                for(int i = 0; i < 8; i++) {
                    abis[i] = atts[9 + i];
                }
                IDUtils.savePlayerInitial(parts[1], parts[4], parts[2], abis);
            }
            if(!OperationUtils.needRemainPlayer(email,parts[1], parts[2], parts[4], parts[3], true) && parts[1].indexOf("����") < 0) {
                OperationUtils.dropPlayer(email, parts[0]);
            }
        }
        //������
        List<Map<String, String>> list = OperationUtils.middleman(email);
        for(Map<String, String> player : list) {
            if(!IDUtils.checkPlayerInitial(player.get("name"), player.get("place"), player.get("level"))) {
                pm = new HttpPost(OgzqURL.URL + OgzqURL.VIEW_PLAYER);
                params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("LoadPlayer1", player.get("id")));
                pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                String ret = IDUtils.execute(email, pm);
                String[] atts = ret.split("[*]");
                String[] abis = new String[8];
                for(int i = 0; i < 8; i++) {
                    abis[i] = atts[9 + i];
                }
                IDUtils.savePlayerInitial(player.get("name"), player.get("place"), player.get("level"), abis);
            }
            if(!OperationUtils.needRemainPlayer(email, player.get("name"), player.get("level"), player.get("place"), player.get("value"), false) && player.get("name").indexOf("����") < 0) {
                LOGGER.info(email + "/" + IDUtils.getNick(email) + "��Ҫ������Ա��" + player.get("name"));
                OperationUtils.dropPlayer(email, player.get("id"));
            }
        }
    }
}
