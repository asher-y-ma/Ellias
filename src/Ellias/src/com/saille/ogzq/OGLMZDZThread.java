package com.saille.ogzq;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ellias
 * Date: 14-5-6
 * Time: ����1:25
 * To change this template use File | Settings | File Templates.
 */
public class OGLMZDZThread extends Thread {
    private String email;
    private int min;
    private int max;
    private final static SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
    private final static Logger LOGGER = Logger.getLogger(OGLMZDZThread.class);

    public OGLMZDZThread(String email, int min, int max) {
        this.setName("OGLMZDZThread-" + email);
        this.email = email;
        this.min = min;
        this.max = max;
    }

    public void run() {
        SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
        Date date = new Date();
        int defCD = 0;
        while(Integer.parseInt(sdf.format(date)) <= 2000) {
            try{
                Thread.sleep(5000);
                date = new Date();
            } catch(Exception ex) {}
        }
        while(Integer.parseInt(sdf.format(date)) <= 2200) {
            try{
                HttpPost pm = new HttpPost(OgzqURL.URL + "/OGLM.aspx");
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("Limzdz1", "-1"));
                pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                String s = IDUtils.execute(email, pm);
                if(s.indexOf("inmatch|") >= 0) {
                    //�ڱ�����
                } else {
//ʥ�ն���@
//33375@
//npc7.png@
//40|����|npc40.png|0|0*19|�����ɵ�|npc19.png|0|0*20|����Ľ���|npc20.png|0|0*27|����|npc27.png|0|0*28|����ͼ˹|npc28.png|0|0*29|��������|npc29.png|0|0*30|AC����|npc30.png|0|0*35|�ȴ�|npc35.png|0|0*36|��ɭ��|npc36.png|0|0*37|������|npc37.png|0|0*38|�ж���|npc38.png|0|0*39|����|npc39.png|0|0*47|����ﾺ��|npc47.png|0|0*49|�ʼ������|npc49.png|0|0*50|��������|npc50.png|0|0@
//3&���ж�|CNTV3��|FCD|����76��|1*3&����׺�����|937753��|����������|JJ����14��|0*2&0015|����132��|30018|npc37.png|������|6|8|35|0|1237*3&����Ǯ��|05��65��|bingbao|05��65��|1*3&����|ƽ��1��|2584981014|JJ����1��|1*3&AAAA|G36165��|VO�����|����10��|1*3&ŵ����18��|ya24769��|�ǰ�|05��9��|0*3&ˮƿ��|93775��|�Ի�֮��|05��27��|1*2&NACL|����1��|88552|npc39.png|����|7|8|41|1|348@
//0@0@
//�����˵�<b style="color:red;">DW�����й�</b>����ռ����[<b style="color:red;">������</b>]��<b style="color:red;">0015</b>�������2���˻��֣����20���˹���@
//��翹���|npc58.png|42721|ʥ�ն���@45448@npc58.png
                    String[] list = s.split("@");
                    defCD = Integer.parseInt(list[5]); //6=attCD
                    if(defCD > 0) {
                        Thread.sleep(defCD * 900);
                    }
                    if(!"-1".equals(list[4])) {
                        String[] kengList = list[4].split("[*]");
                        int currentJifen = 0, currentIdx = -1;
                        for(int i = 0; i < kengList.length; i++) {
                            String[] keng = kengList[i].split("&"); //7: ����
                            if("2".equals(keng[0])) { //��ռ����
                                int shili = Integer.parseInt(keng[1].split("[|]")[2]);
//                                int myShili = Integer.parseInt(IDUtils.IDInfos.get(email).get("shili"));
                                if(shili > min && shili <= max) {
                                    if(list[0].equals(list[8].split("[|]")[3])) {
                                        LOGGER.info(email + "/" + IDUtils.getNick(email) + "��������������ս�����֣�" + keng[1].split("[|]")[7] + "��λ�ã�" + i);
                                        if(Integer.parseInt(keng[1].split("[|]")[7]) > currentJifen) {
                                            currentJifen = Integer.parseInt(keng[1].split("[|]")[7]);
                                            currentIdx = i;
                                        }
                                    }
                                }
                            }
                        }
                        if(currentIdx >= 0) {
                            pm = new HttpPost(OgzqURL.URL + "/OGLM.aspx");
                            params = new ArrayList<NameValuePair>();
                            params.add(new BasicNameValuePair("GuardRoom1", "" + currentIdx));
                            pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                            s = IDUtils.execute(email, pm);
                            if("-999".equals(s)) {
                                s = "����ָ����ô����Ϣʮ��������ɣ�";
                            } else if("-888".equals(s)) {
                                s = "�������Ѳ�������ʱ����ڵ����£����ɼ�������";
                            } else if("-2".equals(s)) {
                                s = "�Ѿ��б������ڷ��ش���";
                            } else if("-3".equals(s)) {
                                s = "���ܷ��س��������������";
                            } else if("-4".equals(s)) {
                                s = "�����ڹ�ռ�������ˣ����ɲ������";
                            } else if("-5".equals(s)) {
                                s = "ʣ��CDʱ�䣺" + defCD;
                            } else if("-6".equals(s)) {
                                s = "�ף�����������ģ�";
                            } else if("-7".equals(s)) {
                                s = "��������ս�ѽ�ֹ";
                            }
                            LOGGER.info(email + "/" + IDUtils.getNick(email) + "����������ս�����֣�" + currentJifen + ": " + s);
                            System.out.println(s);
                        }
                    }
                }
            } catch(Exception ex) {
            }
            try{
                Thread.sleep(1000);
            } catch(Exception ex) {}
            date = new Date();
        }
    }
}
