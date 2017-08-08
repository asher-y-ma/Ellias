package com.saille.ogzq;

import com.saille.util.UtilFunctions;
import org.apache.log4j.Logger;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.NameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.message.BasicNameValuePair;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;

import java.util.*;
import java.text.SimpleDateFormat;
import java.io.File;
import java.io.FileInputStream;

/**
 * Created by IntelliJ IDEA.
 * User: ELLIAS
 * Date: 2014-4-2
 * Time: 21:58:39
 * To change this template use File | Settings | File Templates.
 * ���������Ƿ���Ҫ����
 * todo:������8������
 */
public class ChampionshipThread extends Thread {
    private final static Logger LOGGER = Logger.getLogger(ChampionshipThread.class);
    private boolean cont = true;
    private List<String> excludeArenaEmail = new ArrayList<String>(); //�Ѿ������ĸ㶨����email
    private List<String> hasArenaEmails = new ArrayList<String>(); //jjc�д��Ķ������ҹҵ�email��δ����Ƿ�ͨ�������ǿ϶��Ѿ����˵�ȥ��
    private List<String> otherArenaEmails = new ArrayList<String>(); //jjc�д��ı��˵ĺţ�δ����Ƿ�ͨ�������ǿ϶��Ѿ����˵�ȥ��
    private List<String> myWeixingEmails = new ArrayList<String>(); //��������
    private List<String> otherWeixingEmails = new ArrayList<String>();

    private List<String[]> otherIds = new ArrayList<String[]>(); //���������ŵ���Ϣ��List<String[]{nick, email, pwd}>
    private Map<String, String> myIds = new Hashtable<String, String>();

    public void run() {
        while(cont) {
            try {
                String cunfuTactic = null;
                /************��龺�������֣���Ϊ����Ҫ������ҳ������ʱ����Ժ��Բ��ƣ����Բ����ж�״ֱ̬�Ӽ��*************/
                List<String> keys = ArenaThread.GETIDS();
                List<String> nicks = new ArrayList<String>();
                for(String s : keys) {
                    nicks.add(ArenaThread.NICK.get(s));
                }
                for(String k : keys) {
                    if(excludeArenaEmail.contains(k)) {
                        continue;
                    }
                    String[] against = ArenaThread.JJCAgainst.get(k);
                    if(against != null) {
                        String[][] atts = new String[against.length][];
                        for(int i = 0; i < against.length; i++) {
                            atts[i] = against[i].split("[|]");
                            if("�Ҵ�".equals(atts[i][2]) && "0".equals(atts[i][9])) {
                                if(IDUtils.IDInfos.containsKey(k)) {
                                    if(!hasArenaEmails.contains(k)) {
                                        hasArenaEmails.add(k);
                                        /* ����ID����Ϊ����Ҫ�򾺼��� */
                                        ConfigUtils.saveConf("�Ƿ��߾�����." + k, "0");
                                    }
                                } else {
                                    if(!otherArenaEmails.contains(k)) {
                                        otherArenaEmails.add(k);
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
                /************����ҵľ���������*************/
                if(hasArenaEmails.size() > 0) { //�ҵľ������д�򣬾Ͳ���Ҫ���������ˣ�����֮ǰ��Ҫ��֤�Զ��򾺼����ص���

                } else if(otherArenaEmails.size() > 0) { //���˵ľ������д��
                    for(int i = otherArenaEmails.size() - 1; i >= 0; i--) {
                        String email = otherArenaEmails.get(i);
                        HttpPost pm = new HttpPost(OgzqURL.URL + OgzqURL.ARENA);
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("load", "1"));
                        pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

                        String ret = IDUtils.execute(email, pm);
                        if(ret.indexOf("inmatch") != -1) {
                            continue;
                        }
                        try {
                            int retValue = Integer.parseInt(ret);
                            if(retValue < 0) {
                                continue;
                            }
                        } catch(NumberFormatException ex) {}
                        ret = ret.substring(0, ret.indexOf("&"));
                        String[] teams = ret.split("[*]");
                        for(String t : teams) {
                            String[] atts = t.split("[|]");
                            if("�Ҵ�".equals(atts[2]) && "1".equals(atts[9])) {
                                excludeArenaEmail.add(email);
                                otherArenaEmails.remove(i);
                            }
                        }
                    }
                } else { //��û�д����Ҫ�������
                    if(Integer.parseInt(new SimpleDateFormat("HHmm").format(new Date())) >=1830) {
                        HttpPost pm = new HttpPost(OgzqURL.URL + "/TeamAndPlayer/Team.aspx");
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("LoadTeam1", "26048"));
                        pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                        String ret = IDUtils.execute(IDUtils.GETIDS().get(0), pm);
                        String weixingstr = ret.split("#")[30];
                        String hasWeixing = weixingstr.split("@")[0];
                        if(hasWeixing.equals("3") || hasWeixing.equals("4") || hasWeixing.equals("5")) {
                            weixingstr = weixingstr.split("@")[1];
                            String[] weixings = weixingstr.split("[*]");
//                747|������ʿ|npc58.png|26530|75|ս������200*
//                12138|XD������|npc54.png|18653|74|*
//                517|߱��|npc54.png|18429|74|
                            for(String weixing: weixings) {
                                String[] parts = weixing.split("[|]");
                                if(this.isMyId(parts[1])) {
                                    myWeixingEmails.add(this.myIds.get(parts[1]));
                                } else {
                                    otherWeixingEmails.add(parts[1]);
                                }
                            }
                        }
                    }
                }

                /*
                step:
                1���������ʱ����10�������⣬�ȴ�
                2���������һ�ֶ��֣����ʵ����>8000���Ǿ͵ȴ�
                3�����ʵ����<8000���Ǿ����δ��ҵľ����������˵ľ����������Ǵ���������
                4���ҵ�֮�󣬵�¼��һ�ֶ��ֺţ��ȵ����10���ӣ�����
                 */
                HttpPost pm = new HttpPost(OgzqURL.URL + "/ChampionCup.aspx");
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("type", "6"));
                pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                String s = IDUtils.execute(IDUtils.GETIDS().get(0), pm);
                s = s.substring(0, s.indexOf("^"));
                String[] teams = s.split("[*]");
                String opponent = null;
                String opponentId = null;
                String cunfuId = null;
                if(teams[16].equals("||||||||||")) { //16ǿ
                    for(int i = 0; i < 16; i++) {
                        if(teams[i].split("[|]")[1].equals("�Ҵ�")) {
                            cunfuId = teams[i].split("[|]")[0];
                            if(i % 2 == 0) {
                                opponent = teams[i + 1].split("[|]")[1];
                                opponentId = teams[i + 1].split("[|]")[0];
                            } else {
                                opponent = teams[i - 1].split("[|]")[1];
                                opponentId = teams[i - 1].split("[|]")[0];
                            }
                            break;
                        }
                    }
                } else if(teams[24].equals("||||||||||")) { //8ǿ
                    for(int i = 16; i < 24; i++) {
                        if(teams[i].split("[|]")[1].equals("�Ҵ�")) {
                            if(i % 2 == 0) {
                                opponent = teams[i + 1].split("[|]")[2];
                            } else {
                                opponent = teams[i - 1].split("[|]")[2];
                            }
                            break;
                        }
                    }
                } else if(teams[28].equals("||||||||||")) { //��ǿ�������
                    for(int i = 24; i < 28; i++) {
                        if(teams[i].split("[|]")[1].equals("�Ҵ�")) {
                            if(i % 2 == 0) {
                                opponent = teams[i + 1].split("[|]")[2];
                            } else {
                                opponent = teams[i - 1].split("[|]")[2];
                            }
                            break;
                        }
                    }
                }
                //������ʵ��
                System.out.println("opponent: " + opponent + ", id=" + opponentId);
                int cunfuShili = 0, opponentShili = 0;
                pm = new HttpPost(OgzqURL.URL + "/TeamAndPlayer/Team.aspx");
                params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("LoadTeam1", cunfuId));
                pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                String ret = IDUtils.execute(IDUtils.GETIDS().get(0), pm);
                cunfuShili = Integer.parseInt(ret.split("#")[24]);

                pm = new HttpPost(OgzqURL.URL + "/TeamAndPlayer/Team.aspx");
                params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("LoadTeam1", opponentId));
                pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                ret = IDUtils.execute(IDUtils.GETIDS().get(0), pm);
                opponentShili = Integer.parseInt(ret.split("#")[24]);

                if(cunfuShili > (opponentShili + 10000)) {
//                    ������ԣ�ɶ����������
                } else {
                    Date now = new Date();
                    SimpleDateFormat sdf2 = new SimpleDateFormat("mmss");
                    if(Integer.parseInt(sdf2.format(now)) % 3000 > 2945) { //�����15������
                        if(hasArenaEmails.size() > 0) {
                            pm = new HttpPost(OgzqURL.URL + OgzqURL.ARENA);
                            params = new ArrayList<NameValuePair>();
                            params.add(new BasicNameValuePair("load", "1"));
                            pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

                            ret = IDUtils.execute(hasArenaEmails.get(0), pm);
                            if(ret.indexOf("inmatch") != -1) {
//                                inmatch|1|80864874|67545a
                                pm = new HttpPost(OgzqURL.URL + "/MatchEngine.aspx");
                                params = new ArrayList<NameValuePair>();
                                params.add(new BasicNameValuePair("ImaEndMatchID", ret.split("[|]")[2]));
                                params.add(new BasicNameValuePair("ImaMatchCategory", "5"));
                                pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                                ret = IDUtils.execute(hasArenaEmails.get(0), pm);
                                Thread.sleep(2000);

                                pm = new HttpPost(OgzqURL.URL + OgzqURL.ARENA);
                                params = new ArrayList<NameValuePair>();
                                params.add(new BasicNameValuePair("load", "1"));
                                pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                                ret = IDUtils.execute(hasArenaEmails.get(0), pm);
                                System.out.println(ret);
                            }
                            ret = ret.substring(0, ret.indexOf("&"));
                            String[] tts = ret.split("[*]");
                            for(String t : tts) {
                                if(t.split("[|]")[2].equals("�Ҵ�")) {
                                    pm = new HttpPost(OgzqURL.URL + OgzqURL.ARENA);
                                    params = new ArrayList<NameValuePair>();
                                    params.add(new BasicNameValuePair("insertMatch", t.split("[|]")[0]));
                                    params.add(new BasicNameValuePair("prop", "2"));
                                    pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                                    String matchId = IDUtils.execute(hasArenaEmails.get(0), pm);

                                    pm = new HttpPost(OgzqURL.URL + "/MatchEngine.aspx");
                                    params = new ArrayList<NameValuePair>();
                                    params.add(new BasicNameValuePair("MatchID", matchId));
                                    params.add(new BasicNameValuePair("MatchCategory", "5"));
                                    params.add(new BasicNameValuePair("KFC", "0"));
                                    pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                                    String matchDetail = IDUtils.execute(myWeixingEmails.get(0), pm);
                                    cunfuTactic = getTactic(matchDetail);
                                }
                            }
                        } else if(otherArenaEmails.size() > 0) {

                        } else { //�����Ƿ���
                            if(myWeixingEmails.size() > 0) {
                                pm = new HttpPost(OgzqURL.URL + "/TeamAndPlayer/Team.aspx");
                                params = new ArrayList<NameValuePair>();
                                params.add(new BasicNameValuePair("BeginGame1", cunfuId));
                                pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                                String matchId = IDUtils.execute(myWeixingEmails.get(0), pm);

                                pm = new HttpPost(OgzqURL.URL + "/MatchEngine.aspx");
                                params = new ArrayList<NameValuePair>();
                                params.add(new BasicNameValuePair("MatchID", matchId));
                                params.add(new BasicNameValuePair("MatchCategory", "14"));
                                params.add(new BasicNameValuePair("KFC", "0"));
                                pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                                String matchDetail = IDUtils.execute(myWeixingEmails.get(0), pm);
                                cunfuTactic = getTactic(matchDetail);
                            }
                        }
                    }
                }
            } catch(Exception ex) {
                UtilFunctions.LogError(LOGGER, ex);
            }
        }
    }

    private String getTactic(String in) {
//        20140405/80871784~1758~21888|
//        ����VSC��|
//        npc58.png|
//        36512|
//        Donadoni|
//        npc53.png|
//        2|
//        4|
//        69|
//        74|
//        0a2873|
//        ffffff|
//        17161|
//        17161|
//        18075|
//        15665|
//        0~5~0|
//        0|
//        0~0~1~1~0~74
        String[] parts = in.split("[|]");
        if(parts[1].equals("�Ҵ�")) {
            return parts[6];
        } else if(parts[4].equals("�Ҵ�")) {
            return parts[7];
        }
        return null;
    }

    private boolean isMyId(String nick) {
        if(this.myIds == null || this.myIds.size() == 0) {
            List<String> ids = IDUtils.GETIDS();
            for(String id : ids) {
                String n = IDUtils.getNick(id);
                myIds.put(n, id);
            }
        }
        return myIds.containsKey(nick);
    }

    private boolean checkOtherIds(String nick) {
        if(this.otherIds == null || this.otherIds.size() == 0) {
            File f = new File("D:\\excel\\1.xls");
            if(!f.exists()) {
                f = new File("C:\\Users\\ELLIAS\\Desktop\\1.xls");
            }
            try {
                FileInputStream fis = new FileInputStream(f);
                HSSFWorkbook workbook = new HSSFWorkbook(fis);
                HSSFSheet sheet = workbook.getSheet("����");
                for(int i = 2; i <= sheet.getLastRowNum(); i++) {
                    HSSFRow row = sheet.getRow(i);
//                    String nick =
                }
            } catch(Exception ex) {
                UtilFunctions.LogError(LOGGER, ex);
            }
        }
//        todo
        return false;
    }
}
