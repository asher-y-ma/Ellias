package com.saille.ogzq.activityLoop;

import org.apache.log4j.Logger;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.util.List;
import java.util.ArrayList;

import com.saille.ogzq.IDUtils;
import com.saille.ogzq.OgzqURL;
import com.saille.ogzq.OperationUtils;
import com.saille.util.UtilFunctions;

/**
 * Created by IntelliJ IDEA.
 * User: Ellias
 * Date: 2015-12-7
 * Time: 14:12:00
 * To change this template use File | Settings | File Templates.
 */
public class DaliwanThread extends Thread {
    private final static Logger LOGGER = Logger.getLogger(DaliwanThread.class);
    private static DaliwanThread instance = null;

    private DaliwanThread() {

    }

    public static DaliwanThread getInstance() {
        if(instance == null) {
            instance = new DaliwanThread();
        }
        return instance;
    }

    public void run() {
        List<String> ids = IDUtils.GETIDS();
        for(int i = ids.size() - 1; i >= 0; i--) {
            if(ids.get(i).indexOf("txjcf") >= 0) {
                ids.remove(i);
            }
        }
        while(ids.size() > 0) {
            for(int i = ids.size() - 1; i >= 0; i--) {
                String id = ids.get(i);
                try {
                    HttpPost pm = new HttpPost(OgzqURL.URL + "/DaLiWan.aspx");
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("type", "1"));
                    pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                    String s = IDUtils.execute(id, pm);
                    if(s.indexOf("inmatch") >= 0) {
                        continue;
                    }
                    if(s.indexOf("��") >= 0) {
                        s = s.substring(0, s.indexOf("��"));
                    }
                    String[] parts = s.split("\\@");
                    if(parts[0].equals("1")) { //��սʱ��
                        int matchtimes = Integer.parseInt(parts[6]) / 1000;
                        int daliwancount = Integer.parseInt(parts[2]);
                        int silver = Integer.parseInt(IDUtils.IDInfos.get(id).get("silver"));
                        if(silver > 1000000 && matchtimes < 10) {
                            pm = new HttpPost(OgzqURL.URL + "/DaLiWan.aspx");
                            params = new ArrayList<NameValuePair>();
                            params.add(new BasicNameValuePair("type", "2"));
                            pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                            s = IDUtils.execute(id, pm);
                            LOGGER.info(id + "/" + IDUtils.getNick(id) + "�ߴ����������" + s + "���Ѵ����=" + matchtimes);
                        } else {
                            if(matchtimes >= 10) {
                                ids.remove(i);
                            }
                        }
                    } else if(parts[0].equals("2")) { //�ӳ�ʱ��
                        if(parts[1].equals("0")) {
                            String[] players = parts[3].split("\\*");
                            if(players.length == 1 && players[0].equals("0")) {
                                ids.remove(i);
                                continue;
                            }
                            String[] upplayer = null;
                            int upshili = 99999;
                            for(String player : players) {
                                String[] subs = player.split("\\|"); //690094|10056|ŷ��C��*695956|220301|��ŵ����*712132|110000072|������
                                boolean canup = false;
                                if(subs[2].equals("������") ||
                                        subs[2].equals("��ŵ����") ||
                                        subs[2].equals("ŷ��C��") ||
                                        subs[2].equals("����÷��")) {
                                    canup = true;
                                } else if(id.indexOf("blue") == 0) {
                                    if(subs[2].equals("����Ү") ||
                                            subs[2].equals("����Ү") ||
                                            subs[2].equals("���к�") ||
                                            subs[2].equals("�ʵ���") ||
                                            subs[2].equals("���˹ά��") ||
                                            subs[2].equals("ķ������") ||
                                            subs[2].equals("���") ||
                                            subs[2].equals("��������") ||
                                            subs[2].equals("������") ||
                                            subs[2].equals("��˹��˹")) {
                                        canup = true;
                                    }
                                } else if(id.indexOf("orange") == 0) {
                                    if(subs[2].equals("������ʲ���Ʒ�˹��") ||
                                            subs[2].equals("�����ꡤ����˹") ||
                                            subs[2].equals("�����ల") ||
                                            subs[2].equals("��ʩ÷����") ||
                                            subs[2].equals("��������") ||
                                            subs[2].equals("��ѥ����") ||
                                            subs[2].equals("��ѥС��")) {
                                        canup = true;
                                    }
                                } else if(id.indexOf("bixi") == 0) {
                                    if(subs[2].equals("���Ͷ���˹") ||
                                            subs[2].equals("��������˹��") ||
                                            subs[2].equals("��ѡ��C��") ||
                                            subs[2].equals("��ѡ��÷��") ||
                                            subs[2].equals("��ѡ��С��") ||
                                            subs[2].equals("��ѥC��")) {
                                        canup = true;
                                    }
                                }
                                if(canup) {
                                    pm = new HttpPost(OgzqURL.URL + "/DaLiWan.aspx");
                                    params = new ArrayList<NameValuePair>();
                                    params.add(new BasicNameValuePair("type", "3"));
                                    params.add(new BasicNameValuePair("playerid", subs[0]));
                                    params.add(new BasicNameValuePair("playerDataId", subs[1]));
                                    pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                                    s = IDUtils.execute(id, pm);
                                    int newshili = Integer.parseInt(s.split("\\|")[2]);
                                    if(newshili < upshili) {
                                        upshili = newshili;
                                        upplayer = subs;
                                    }
                                }
                            }
                            if(upplayer != null) {
                                pm = new HttpPost(OgzqURL.URL + "/DaLiWan.aspx");
                                params = new ArrayList<NameValuePair>();
                                params.add(new BasicNameValuePair("type", "4"));
                                params.add(new BasicNameValuePair("playerid", upplayer[0]));
                                params.add(new BasicNameValuePair("playerDataId", upplayer[1]));
                                pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                                s = IDUtils.execute(id, pm);
                                if(s.equals("1")) {
                                    s = "�ɹ�";
                                    OperationUtils.trainFull(id, upplayer[0], true);
                                } else if(s.equals("-1")) {
                                    s = "�����費��";
                                } else if(s.equals("-2")) {
                                    s = "ֻ�ܶ�һ����Աʹ�ô�����";
                                } else {
                                    s = "����ʧ��";
                                }

                                LOGGER.info(id + "/" + IDUtils.getNick(id) + "ʹ�ô����裬��Ա=" + upplayer[2] + "�������" + s);
                            }
                            ids.remove(i);
                        } else {
                            ids.remove(i);
                            continue;
                        }
                    } else {
                        LOGGER.error("δ֪ʱ������ͣ�");
                        ids.clear();
                        break;
                    }
//	ʱ�������(1:��սʱ�䣬2�����Լӳ�ʱ��)@
// �Ƿ��Ѽӳɣ�0��û�У�1���ˣ�@
// �ѻ�ô���������(�����Ƿ�����)@
// ��ѡ��Ա�б�@
// �ӳ�ǰ��Ա��Ϣ@�ӳɺ���Ա��Ϣ@�ۼƻ��ѵ����@�ۼƻ������@�ۼƻ�ô��������
//	��ѡ��Ա�б�playerid|PlayerDataID|CnName * playerid|PlayerDataID|CnName * playerid|PlayerDataID|CnName    û�п�ѡ��Աʱ������0
//	��Ա��Ϣ��ʽ��Playerid|PlayerDataID|PlayerInfo_Power|role|Photo|CnName|PlayerQuality    û�����һ�μӳ�ʱ������0
//	���ڱ�����������أ�inmatch|matchId|matchCategry
//result = "1@0@17@2375679|20711|��������*3643717|21001|ŵ����*2873783|21006|���*3114835|21007|ʩκ��˹̩��*2850083|21009|�ޱ�*4620747|40509|����*1163933|40516|��ٯ*4396585|41010|³��*3834245|50911|������*8606594|110000040|��������˹��@0@0";
//1@
//0@
//1@
//690094|10056|ŷ��C��*695956|220301|��ŵ����*712132|110000072|������@
//0@
//0@
//1000@
//1000@
//10
//��12��7��|12��8��
                } catch(Exception ex) {
                    UtilFunctions.LogError(LOGGER, ex);
                }
            }
            try {
                Thread.sleep(1000 * 60 * 5);
            } catch(Exception ex) {}
        }
        LOGGER.info("��������̽���");
        DaliwanThread.instance = null;
    }
}
