package com.saille.ogzq;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import javax.servlet.jsp.JspWriter;
import java.util.*;
import java.text.SimpleDateFormat;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import com.saille.util.FileUtils;

/**
 * Created by IntelliJ IDEA.
 * User: ELLIAS
 * Date: 2014-9-6
 * Time: 22:24:36
 * To change this template use File | Settings | File Templates.
 */
public class OtherUtils {
    public static void main(String[] args) {
        try {
//            String email = "robot0005@sina.com", pwd = "lspmgk";
            String email = "313025470@qq.com", pwd = "lspmgk";
            HttpClient client = LoginUtils.Login(email, pwd);
            if(client == null) {
                System.out.println("�������");
            } else {
                useHuidou(client, email);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
//    private final static Logger LOGGER = Logger.getLogger(OtherUtils.class);

    //������������ս
    public static void baomingLM() throws Exception {
        List<String> ids = IDUtils.GETIDS();
        for(String id : ids) {
            System.out.println(id);
            try {
                System.out.println(id);
                HttpPost pm = new HttpPost(OgzqURL.URL + "/OGLM.aspx");
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("OGLMGameLoad1", ""));
                pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                IDUtils.execute(id, pm);

                pm = new HttpPost(OgzqURL.URL + "/OGLM.aspx");
                params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("BaoMing1", ""));
                pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                IDUtils.execute(id, pm);
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    //ʹ�ûض���
    public static void useHuidou(HttpClient client, String email) throws Exception {
        HttpPost pm = null;
        int usetime = 0;

        pm = new HttpPost(OgzqURL.URL + OgzqURL.BAGS);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("type", "0"));
        params.add(new BasicNameValuePair("itemtype", "0"));
        pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
        String str = IDUtils.execute(client, email, pm);
        String[] items = str.split("[|]");
        String huidouId = "";
        int count = 0;
        for(String ii : items) {
            String[] i = ii.split("[*]");
            if(i[3].equals("�ض���")) {
                huidouId = i[0];
                count = Integer.parseInt(i[7]);
                System.out.println("�ض�������" + count + "��");
                break;
            }
        }
        if(count == 0) {
            System.out.println("û�лض���");
            return;
        }

        while(true) {
            pm = new HttpPost(OgzqURL.URL + "/MatchList/TrainMatch/TrainMatch2.aspx");
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("TrainMatchload1", "7*0*0"));
            pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            String s = IDUtils.execute(client, email, pm);

            pm = new HttpPost(OgzqURL.URL + OgzqURL.MATCH_TRAINING); //��ȡ����
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("getAward", "1"));
            pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            String reward = IDUtils.execute(client, email, pm);


            String[] parts = s.split("&");
            if(parts.length < 6) {
                continue;
            }
            System.out.println(email + " ��Ʒ:" + reward + "��ʣ�������" + parts[5]);
            String trainingMatch2 = parts[0];

            int blankMatch = 0;
            int restMatch = 0;
            if(!trainingMatch2.equals("-2")) {
                if(Integer.parseInt(parts[5]) <= 0) {
                    pm = new HttpPost(OgzqURL.URL + OgzqURL.BAGS);
                    params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("ShowTmMsgOK1", "1*" + huidouId));
                    pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                    String ss = IDUtils.execute(client, email, pm);
                    System.out.println(email + "ʹ�ûض�����" + ss);
                    count = count - 1;
                    System.out.println(email + "ʣ�ࣺ" + count + "��");
//                    Thread.sleep(500l);

                    pm = new HttpPost(OgzqURL.URL + "/MatchList/TrainMatch/TrainMatch2.aspx");
                    params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("TrainMatchload1", "7*0*0"));
                    pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                    s = IDUtils.execute(client, email, pm);
                    parts = s.split("&");
                }
                if(parts.length < 6) {
                    continue;
                }
                blankMatch = Integer.parseInt(parts[4]) - Integer.parseInt(parts[5]); //������������ٳ�
                restMatch = Integer.parseInt(parts[5]);
            } else {
                pm = new HttpPost(OgzqURL.URL + OgzqURL.MATCH_TRAINING);
                params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("GetPowerCount", "1"));
                params.add(new BasicNameValuePair("pt", "7"));
                pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                String powerCount = IDUtils.execute(client, email, pm);
                int maxMatch = 0;
                try {
                    maxMatch = Integer.parseInt(powerCount);
                } catch(Exception ex) {
                }
                pm = new HttpPost(OgzqURL.URL + OgzqURL.TEAM_PLAY);
                params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("getB", "7"));
                params.add(new BasicNameValuePair("title", "1"));
                pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                String currentCount = IDUtils.execute(client, email, pm);
                int currentMatch = 0;
                try {
                    currentMatch = Integer.parseInt(currentCount.split("[|]")[0]);
                    restMatch = currentMatch;
                } catch(Exception ex) {
                }
                blankMatch = maxMatch - currentMatch;
            }

            if(restMatch <= 0) { //���ʣ�ೡ�β������ǾͲ���
                System.out.println("�ض������꣬�˳�");
                return;
            }

            { //��70-80ѵ����
                String nextTeam = null;
                int pageCount = 6;
                String pageInfo = null;
                while(nextTeam == null) {
                    pm = new HttpPost(OgzqURL.URL + "/MatchList/TrainMatch/TrainMatch2.aspx");
                    params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("TrainMatchload1", "7*" + pageCount + "*1"));
                    pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                    s = IDUtils.execute(client, email, pm);
                    if(s.indexOf("inmatch") >= 0) {
                        System.out.println("���ڱ���");
                        continue;
                    }
//                System.out.println(s);
                    pageInfo = s.split("&")[3];
                    String[] teams = s.split("&")[8].split("[*]");
                    for(String t : teams) {
                        if(t.split("[|]")[6].equals("-1")) {
                            nextTeam = t.split("[|]")[0];
                            break;
                        }
                    }

                    pageCount--;
                    if(pageCount < 5) {
                        pageCount = 50;
                    }
                }

                pm = new HttpPost(OgzqURL.URL + "/MatchList/TrainMatch/TrainMatch2.aspx");
                params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("InitChallengeMatch1", nextTeam + "*" + pageInfo));
                pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                s = IDUtils.execute(client, email, pm);
                System.out.println(email + "��70-80ѵ������" + s);
                Thread.sleep(1000);
//                return;
            }
        }
    }

    //��������������۷�ս
    public static void threeTopChallenge(String player, List<String[]> ids, boolean needstop) throws Exception {

        HttpClient[] clients = new HttpClient[ids.size()];
        boolean goon = true;
        SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
        while(goon) {
                if(needstop) {
                if(Integer.parseInt(sdf.format(new Date())) >= 1500) {
                    break;
                }
            }
            System.out.println("ѭ���۷�ս");
            for(int i = 0; i < ids.size(); i++) {
                String[] id = ids.get(i);
                System.out.println(i + "/" + ids.size() + ": " + id[2]);
                try {
                    if(clients[i] == null) {
                        System.out.println("��¼��");
                        clients[i] = LoginUtils.Login(id[0], id[1]);
                    }
                    HttpPost pm = new HttpPost(OgzqURL.URL + "/" + player + "_Top_Club.aspx");
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("type", "1"));
                    pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                    String s = IDUtils.execute(clients[i], id[0], pm);
                    if(s.startsWith("1@")) {
                        continue;
                    } else if("-2".equals(s)) {
                        clients[i] = LoginUtils.Login(id[0], id[1]);
                    }
                    int rest = Integer.parseInt(s);
                    if(rest > 0) {
                        continue;
                    } else if(rest == -1) {
                        goon = false;
                        break;
                    } else if(rest == 0) {
                        pm = new HttpPost(OgzqURL.URL + "/" + player + "_Top_Club.aspx");
                        params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("type", "2"));
                        pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                        s = IDUtils.execute(clients[i], id[0], pm);
                        System.out.println(id[2] + "���۷�ս��" + s);
                    }
                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
            System.out.println("ѭ���۷�ս���ȴ��´�ѭ��......................");
            Thread.sleep(20000);
        }
    }

    //�ϱ���������
    public static void laoBingSell(JspWriter out) throws Exception {
        List<String> ids = IDUtils.GETIDS();
        Map<String, String[]> price = new HashMap<String, String[]>();
/*        �����濨	16	23017
        ���ްͿ�	16	23018
        ������	11	23019
        �����ǿ�	0	#N/A
        ������	0	#N/A
        �����ؿ�	34	23012
        ��������	33	23013
        ��ϣ����	9	23014
        �ޱ���	3	23015
        ����������	0	#N/A
        ���а¿�	21	23007
        �����¿�	18	23008
        �����¿�	11	23009
        ��¡����	0	#N/A
        ��ά��	0	#N/A
        Ƥ���忨	18	23001
        ˹�ڵ¿�	21	23002
        ��׿�	5	23003
        ���׿���˹��	7	23004
        ���˹��	1	23005
        �����¿�	0	#N/A*/

        price.put("23017", new String[]{"100", "1000"});
        price.put("23018", new String[]{"100", "1000"});
        price.put("23019", new String[]{"100", "1000"});
        price.put("23020", new String[]{"200", "2000"});

        price.put("23012", new String[]{"100", "1000"});
        price.put("23013", new String[]{"100", "1000"});
        price.put("23014", new String[]{"100", "1000"});
        price.put("23015", new String[]{"200", "2000"});

        price.put("23007", new String[]{"100", "1000"});
        price.put("23008", new String[]{"100", "1000"});
        price.put("23009", new String[]{"100", "1000"});
        price.put("23010", new String[]{"200", "2000"});

        price.put("23001", new String[]{"100", "1000"});
        price.put("23002", new String[]{"100", "1000"});
        price.put("23003", new String[]{"100", "1000"});
        price.put("23004", new String[]{"100", "1000"});
        price.put("23005", new String[]{"200", "2000"});

        for(String id : ids) {
            System.out.println(ids.indexOf(id) + "/" + ids.size() + ":" + id);
            if("meijianbai@hotmail.com ".equals(id) ||
                    "rentao@vip.sina.com ".equals(id) ||
                    "sevarsti@sina.com ".equals(id) ||
                    "qilili19841212@126.com ".equals(id) ||
                    "544397212@qq.com ".equals(id) ||
                    "104722123@qq.com ".equals(id) ||
                    "7125608@163.com ".equals(id)) {
                continue;
            }
            HttpPost pm = new HttpPost(OgzqURL.URL + "/LaoBingChuanQi.aspx");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("showItems", "1"));
            pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            String s = IDUtils.execute(id, pm);
            if("-1".equals(s) || "-9".equals(s)) {
                continue;
            }
            String[] cards = s.split("[*]");
            for(String card : cards) {
                String itemCode = card.split("[|]")[0];
                String count = card.split("[|]")[3];
                String cantrade = card.split("[|]")[4];
                if(!price.containsKey(itemCode)) {
//            if("-1".equals(s)) {
                    continue;
                }
//                if("1".equals(cantrade)) {
                    pm = new HttpPost(OgzqURL.URL + "/LaoBingChuanQi.aspx");
                    params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("doGuaDan", "1"));
                    params.add(new BasicNameValuePair("itemCode", itemCode));
                    params.add(new BasicNameValuePair("Num", "1"));
                    params.add(new BasicNameValuePair("qiBuJia", price.get(itemCode)[0]));
                    params.add(new BasicNameValuePair("yiKouJia", price.get(itemCode)[1]));
                    pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                    s = IDUtils.execute(id, pm);
                    System.out.println(id + "����" + card.split("[|]")[5] + ":" + count + "��" + card.split("[|]")[1]);
//                }
            }
        }
    }

    //��ȡ�ϱ�����Ŀ�
    public static void loadLaoBingItem(JspWriter out) throws Exception {
        List<String> ids = IDUtils.GETIDS();
        for(String id : ids) {
            System.out.println(ids.indexOf(id) + "/" + ids.size() + ":" + id);
            HttpPost pm = new HttpPost(OgzqURL.URL + "/LaoBingChuanQi.aspx");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("showItems", "1"));
            pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            String s = IDUtils.execute(id, pm);
            if("-1".equals(s)) {
                continue;
            }
            String[] cards = s.split("[*]");
            //        ��1��Ϣ * ��2��Ϣ * ��3.....   ��û�е��ߣ�����-1
            //		������Ϣ��ʽ��itemCode|ItemName|imgURL|����|CanTrade|description
            // result = "23002|˹�ڵ¿�|23002.png|2|0|ʹ�øÿ�����ʹ���������9��������Ա�ĵȼ�������10��*23009|�����¿�|23009.png|1|0|ʹ�øÿ�����ʹ���������9��������Ա�ĵȼ�������10��*23013|��������|23013.png|2|0|ʹ�øÿ�����ʹ���������9��������Ա�ĵȼ�������10��";
            for(String c : cards) {
                out.println(id + "/" + c + "<br/>");
            }
        }
    }

    //�ϱ������ٻ�
    public static void loadLaoBing(JspWriter out) throws Exception {
        List<String> ids = IDUtils.GETIDS();
        for(String id : ids) {
            System.out.println(ids.indexOf(id) + "/" + ids.size() + ":" + id);
            HttpPost pm = new HttpPost(OgzqURL.URL + "/LaoBingChuanQi.aspx");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("LoadTab01", "1"));
            pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            String s = IDUtils.execute(id, pm);

            //�����ٻ�ʣ����� @ ����ٻ�ʣ����� @ �����ٻ�CD������(û��cdʱΪ0) @ ���������� @ ���ѽ����
            //result = "5@5@5@10000@100��2014��7��22��10:00��7��30��24��";
            if(s.indexOf("��") > 0) {
                s = s.substring(0, s.indexOf("��"));
            }
            String[] parts = s.split("@");
            System.out.println("ʣ�������" + parts[0]);
            if(Integer.parseInt(parts[0]) > 0) { //ʣ���ٻ�����
                if(Integer.parseInt(parts[2]) > 0) {
                    System.out.println("CD: " + parts[2]);
                } else {
                    pm = new HttpPost(OgzqURL.URL + "/LaoBingChuanQi.aspx");
                    params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("zhaoHuan", "1"));
                    params.add(new BasicNameValuePair("mode", "1"));
                    pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                    s = IDUtils.execute(id, pm);
                    System.out.println("�ٻ������" + s);
                    out.println(id + "===" + s + "<br/>");
                }
            }
        }
    }

    //��4��װ���ϳ�1����ʯ
    public static void ronglianGem(JspWriter out) throws Exception {
        List<String> ids = IDUtils.GETIDS();
//        ids.clear();ids.add("robot0001@sina.com");
        for(String id : ids) {
            System.out.println(ids.indexOf(id) + "/" + ids.size() + ":" + id);
            HttpPost pm = new HttpPost(OgzqURL.URL + OgzqURL.BAGS);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("type", "0"));
            params.add(new BasicNameValuePair("itemtype", "2"));
            pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            String s = IDUtils.execute(id, pm);
            String[] items = s.split("[|]");
            for(String item : items) {
                String itemname = item.split("[*]")[3];
                String itemid = item.split("[*]")[0];
                int itemcount = Integer.parseInt(item.split("[*]")[7]);
                if(itemcount >= 5) {
                    if(itemname.equals("սѥ5��")) {
//                    if(itemname.equals("ս��4��") ||
//                            itemname.equals("����4��") ||
//                            itemname.equals("սѥ4��") ||
//                            itemname.equals("����4��")) {
                        for(int i = 0; i < itemcount / 5; i++) {
                            pm = new HttpPost(OgzqURL.URL + OgzqURL.BAGS);
                            params = new ArrayList<NameValuePair>();
                            params.add(new BasicNameValuePair("Ronglian1", itemid + "*" + itemid + "*" + itemid + "*" + itemid + "*" + itemid));
                            pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                            out.println(IDUtils.execute(id, pm) + "<br/>");
                        }
                    }
                }
            }
        }
    }

    //������
    public static void fishBlow(String id, String pwd) throws Exception {
        HttpClient client = LoginUtils.Login(id, pwd);
        HttpPost pm = new HttpPost(OgzqURL.URL + "/Fish_Blow.aspx");
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("type", "1"));
        pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
        String s = IDUtils.execute(client, id, pm);
        if(s.indexOf("��") >= 0) {
            s = s.substring(0, s.indexOf("��"));
        }
        int times = Integer.parseInt(s.substring(s.lastIndexOf("@") + 1));
        for(int i = 0; i < times; i++) {
            pm = new HttpPost(OgzqURL.URL + "/Fish_Blow.aspx");
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("type", "2"));
            pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            s = IDUtils.execute(client, id, pm);

            s = s.substring(s.lastIndexOf("|") + 1); //type
            pm = new HttpPost(OgzqURL.URL + "/Fish_Blow.aspx");
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("type", "4"));
            params.add(new BasicNameValuePair("FishType", s));
            params.add(new BasicNameValuePair("FishSize", "1"));
            pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            s = IDUtils.execute(client, id, pm);
        }
    }

    //Ծ����
    public static void yuelongmen(JspWriter out) throws Exception {
        List<String> ids = IDUtils.GETIDS();
        for(String id : ids) {
            System.out.println(ids.indexOf(id) + "/" + ids.size() + ": " + id + "/" + IDUtils.getNick(id));
            int silver = Integer.parseInt(IDUtils.IDInfos.get(id).get("silver"));
            if(silver < 1000000) {
                continue;
            }
            if(!"1".equals(ConfigUtils.getConf(id, "�Ѽ�����"))) {
                continue;
            }
            HttpPost pm = new HttpPost(OgzqURL.URL + "/Fish_Jump.aspx");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("type", "1"));
            pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            String s = IDUtils.execute(id, pm);
            s = s.substring(0, s.indexOf("��"));
            int total = Integer.parseInt(s.substring(s.lastIndexOf("@") + 1));

            for(int i = 0; (i * 500) < (silver); i++) {
                if(total >= 9999) {
                    out.println("end<br/>");
                    return;
                }
                pm = new HttpPost(OgzqURL.URL + "/Fish_Jump.aspx");
                params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("type", "2"));
                params.add(new BasicNameValuePair("index", "3"));
                pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                s = IDUtils.execute(id, pm);
                if("1".equals(s)) {
                    total++;
                }
            }
        }
    }

    //���ѵ����
    public void checkTrainPoint(JspWriter out) throws Exception {
        List<String[]> ids = IDUtils.JIONGIDS;
        for(String[] id : ids) {
            HttpClient client = LoginUtils.Login(id[0], id[1]);
            HttpPost pm = new HttpPost(OgzqURL.URL + OgzqURL.TRAINING);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("type", "0"));
            pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            String str = IDUtils.execute(client, id[0], pm);
            List ret = new ArrayList();

            String point = str.substring(0, str.indexOf("^"));
            str = str.substring(str.indexOf("^") + 1);
            Map pp = new Hashtable();
//                pp.put("point", point);
            out.println(id[0] + ":" + id[2] + ":" + point + "<br/>");
        }
    }

    //����۷����
    public static void jiongTopChallenge() throws Exception {
//        List<String[]> jiongIds = IDUtils.JIONGIDS;
        List<String[]> jiongIds = new ArrayList<String[]>();
        List<String> myId = IDUtils.GETIDS();
        for(String id : myId) {
            if(id.indexOf("txjcf") >= 0) {
                continue;
            }
            jiongIds.add(new String[]{id, IDUtils.IDPWDS.get(id), IDUtils.getNick(id), "��"});
        }

        boolean goon = true;
        while(goon) {
//            Map<String, HttpClient> clients = new HashMap<String, HttpClient>();
            for(int i = 0; i < jiongIds.size(); i++) {
                System.out.println(i + "/" + jiongIds.size() + ":" + jiongIds.get(i)[2]);
                try {
//                    HttpClient c = clients.get(jiongIds.get(i)[0]);
//                    if(c == null) {
//                        System.out.println("login: " + jiongIds.get(i)[0] + "/" + jiongIds.get(i)[2]);
//                        c = LoginUtils.Login(jiongIds.get(i)[0], jiongIds.get(i)[1]);
//                        clients.put(jiongIds.get(i)[0], c);
//                    }
                    HttpPost pm = new HttpPost(OgzqURL.URL + "/TopChallenge.aspx");
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("type", "2"));
                    pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                    String s = IDUtils.execute(jiongIds.get(i)[0], pm);
                    System.out.println(jiongIds.get(i)[0] + "------------" + new Date() + ": " + s);
                    if("".equals(s)) {
                        goon=false;
                        break;
                    }
                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
            try {
                Thread.sleep(20 * 1000 + 5);
            } catch(Exception ex) {}
        }
    }

    //�۷����
    public static void topChallenge(String email) throws Exception {
        boolean goon = true;
        while(goon) {
            try {
                HttpPost pm = new HttpPost(OgzqURL.URL + "/TopChallenge.aspx");
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("type", "2"));
                pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                String s = IDUtils.execute(email, pm);
                System.out.println(email + "------------" + new Date() + ":" + s);
                if("".equals(s)) {
                    goon=false;
                    continue;
                }
                if("-2".equals(s)) {
                    Thread.sleep(20 * 1000 + 5);
                }
                else if(s.indexOf("|") >= 0 && s.charAt(0) == '0') {
                    int resttime = Integer.parseInt(s.substring(s.indexOf("|") + 1));
                    if(resttime > 1) {
                        System.out.println(email + "�ӳ�ʱ�䣺" + resttime);
                        Thread.sleep((resttime - 1) * 1000 + 5);
                    } else {
                        Thread.sleep(5);
                    }
                } else {
                    Thread.sleep(20 * 1000 + 5);
                }
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    //������
    public static void sellItems(String email) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
        Calendar c = Calendar.getInstance();
        while(Integer.parseInt(sdf.format(c.getTime())) < 20 || Integer.parseInt(sdf.format(c.getTime())) > 2355) {
            HttpPost pm = new HttpPost(OgzqURL.URL + OgzqURL.BAGS);
            List<Map<String, String>> bags = OperationUtils.listBags(email, "1");
            for(Map<String, String> b : bags) {
                String itemId = b.get("itemid");
//                if(itemId.equals("1202")) { //2����
//                    List<NameValuePair> params = new ArrayList<NameValuePair>();
//                    params.add(new BasicNameValuePair("type", "5"));
//                    params.add(new BasicNameValuePair("aii", b.get("id")));
//                    params.add(new BasicNameValuePair("propcount", "1000"));
//                    params.add(new BasicNameValuePair("propprice", "2"));
//                    pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
//                    LOGGER.info(IDUtils.execute(email, pm));
//                    Thread.sleep(1000);
                if(itemId.equals("1303")) { //3��ͧ
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("type", "5"));
                    params.add(new BasicNameValuePair("aii", b.get("id")));
                    params.add(new BasicNameValuePair("propcount", "120"));
                    params.add(new BasicNameValuePair("propprice", "5"));
                    pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                    System.out.println(IDUtils.execute(email, pm));
                    Thread.sleep(1000);
//                } else if(itemId.equals("1403")) { //3�ɻ�
//                    List<NameValuePair> params = new ArrayList<NameValuePair>();
//                    params.add(new BasicNameValuePair("type", "5"));
//                    params.add(new BasicNameValuePair("aii", b.get("id")));
//                    params.add(new BasicNameValuePair("propcount", "333"));
//                    params.add(new BasicNameValuePair("propprice", "6"));
//                    pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
//                    LOGGER.info(IDUtils.execute(email, pm));
//                    Thread.sleep(1000);
                }
            }
            c.setTime(new Date());
        }
    }

    //�����ŵ��ϱ��ٻ�
    public void otherLoadLaoBing(JspWriter out) throws Exception {
        File f = new File("D:\\all.txt");
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        String s;
        File saveFile = new File("D:\\laobing.txt");
        File exceptionFile = new File("D:\\silvers2.txt");
        int count = 0;
        while((s = br.readLine()) != null) {
            try {
                System.out.println(s + (count++));
                String[] parts = s.split("\t");
                String email = parts[0];
                String pwd = parts[1];
                HttpClient client = LoginUtils.Login(email, pwd);

                System.out.println("email:" + email);
                HttpPost pm = new HttpPost(OgzqURL.URL + "/LaoBingChuanQi.aspx");
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("LoadTab01", "1"));
                pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                s = IDUtils.execute(client, email, pm);

                if(s.indexOf("��") > 0) {
                    s = s.substring(0, s.indexOf("��"));
                }
                parts = s.split("@");
                if(Integer.parseInt(parts[0]) > 0) { //ʣ���ٻ�����
                    System.out.println("ʣ�������" + parts[0]);
                    if(Integer.parseInt(parts[2]) > 0) {
                        System.out.println("CD: " + parts[2]);
                    } else {
                        pm = new HttpPost(OgzqURL.URL + "/LaoBingChuanQi.aspx");
                        params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("zhaoHuan", "1"));
                        params.add(new BasicNameValuePair("mode", "1"));
                        pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                        s = IDUtils.execute(client, email, pm);
                        System.out.println("�ٻ������" + s);
                        FileUtils.WriteFile(saveFile, email + "\t" + s + "\r\n", true);
                    }
                }
            } catch(Exception ex) {
                FileUtils.WriteFile(exceptionFile, s + "\r\n", true);
            }
        }
    }

    //ŷ�ڵ���ʱ
    public static void ogCountdown() throws Exception {
        List<String> ids = IDUtils.GETIDS();
        Calendar c = Calendar.getInstance();
        int d = c.get(Calendar.DATE) - 19;
        for(String id : ids) {
            System.out.println(ids.indexOf(id) + "/" + ids.size() + ":" + id);
            HttpPost pm = new HttpPost(OgzqURL.URL + "/OG_CountDown.aspx");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("type", "1"));
            pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            IDUtils.execute(id, pm);

//            pm = new HttpPost(OgzqURL.URL + "/OG_CountDown.aspx");
//            params = new ArrayList<NameValuePair>();
//            params.add(new BasicNameValuePair("type", "8"));
//            params.add(new BasicNameValuePair("index", "" + d));
//            pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
//            IDUtils.execute(id, pm);
            for(int i = 0; i < 5; i++) {
                pm = new HttpPost(OgzqURL.URL + "/OG_CountDown.aspx");
                params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("type", "6"));
                params.add(new BasicNameValuePair("index", "1"));
                pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                IDUtils.execute(id, pm);
            }
        }
    }

    //������ʹ�õ���
    public static void useItem(JspWriter out, String email, String pwd) throws Exception {
        HttpClient client = LoginUtils.Login(email, pwd);

        HttpPost pm = new HttpPost(OgzqURL.URL + OgzqURL.BAGS);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("type", "0"));
        params.add(new BasicNameValuePair("itemtype", "0"));
        pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

        String str = IDUtils.execute(client, email, pm);
        List<Map> ret = new ArrayList<Map>();

        String[] items = str.split("[|]");
        for(String item : items) {
            String[] atts = item.split("[*]");
            Map m = new Hashtable();
            m.put("email", email);
            m.put("id", atts[0]);
            m.put("place", atts[1]);
            m.put("name", atts[3]);
            m.put("number", atts[7]);
            m.put("itemid", atts[11]);
            ret.add(m);
        }

        for(Map<String, String> item : ret) {
            if(item.get("name").startsWith("ѵ����") ||
                    item.get("name").startsWith("ս�����ֿ�") ||
                    item.get("name").indexOf("װ����") >= 0 ||
                    item.get("name").startsWith("ŷ����Ӯ�Ұ�") ||
                    item.get("name").indexOf("���鿨") >= 0 ||
                    item.get("name").indexOf("�����") >= 0 ||
                    item.get("name").startsWith("ŷ�ں��˰�") ||
                    item.get("name").startsWith("ÿ�յ�¼���") ||
                    item.get("name").startsWith("�������ֿ�") ||
                    item.get("name").startsWith("ͻ�ƻ���") ||
                    item.get("name").indexOf("װ����") >= 0 ||
                    item.get("name").startsWith("ǩ��") ||
                    item.get("name").startsWith("���籭���ֿ�") ||
                    item.get("name").startsWith("��Ա�ɳ����ֿ�") ||
                    item.get("name").startsWith("���Ҷһ���")) {
                int n = Integer.parseInt(item.get("number"));
                System.out.println(item.get("email") + "ʹ�ã�" + item.get("name") + "��" + n + "��<br/>\r\n");
                for(int j = 0; j < n; j++) {
                    System.out.println(item.get("email") + ",use:" + item.get("name") + ":" + j + "/" + n);
                    pm = new HttpPost(OgzqURL.URL + OgzqURL.BAGS);
                    params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("type", "7"));
                    params.add(new BasicNameValuePair("aii", item.get("id")));
                    params.add(new BasicNameValuePair("isAll", "0"));
                    pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                    IDUtils.execute(client, email, pm);
                }
            }
        }
    }

    //��������������ս
    public void threeTopPerson(String player, JspWriter out) throws Exception {
        List<String[]> ids = new ArrayList<String[]>();
        List<String> allids = IDUtils.GETIDS();
        for(String id : allids) {
            if(id.indexOf("txjcf") >= 0) {
                continue;
            }
            ids.add(new String[]{id, IDUtils.IDPWDS.get(id), IDUtils.getNick(id)});
        }
//        ids.add(new String[]{"sevarsti@sina.com", "pmgkpmgk", "��翹���"});
//        ids.add(new String[]{"super88man66@126.com", "072022021", "����ʱ��"});
//        ids.add(new String[]{"leonis11@e7wan", "123678", "HDFC"});
//        ids.add(new String[]{"SKY@163.COM", "030978", "��Ӣ����"});
//        ids.add(new String[]{"345777992@qq.com", "55885588", "����ѩ123"});
//        ids.add(new String[]{"meijianbai@hotmail.com", "meiweilin", "�ᰮ��"});
//        ids.add(new String[]{"fly123450@hupu6", "www123", "��ղķʿ"});
//        ids.add(new String[]{"sw197297@163.com", "smy19780405", "��������֮��"});
//        ids.add(new String[]{"270738347@qq.com", "zmi198678", "ZM�����"});
//        ids.add(new String[]{"��������@hupu6", "58817039", "U�����"});
//        ids.add(new String[]{"michael110110@hupu7", "zengyu520", "��ɷ"});
//        ids.add(new String[]{"764832681@qq.com", "15896205678", "���ӻ�Ҫ"});
//        ids.add(new String[]{"lixiang2160@126.com", "j23w3a15n41", "griggs"});
//        ids.add(new String[]{"rentao@vip.sina.com", "meiweilin", "�ʼ�GFG"});
//        ids.add(new String[]{"yuliang0526@163.com", "yuliang83012", "������"});
//        ids.add(new String[]{"544397212@qq.com", "1231hcqsy", "Xս��ŷ��"});
//        ids.add(new String[]{"81430914@qq.com", "14789632", "������"});
//        ids.add(new String[]{"2359178129@qq.com", "mimi20080731", "쫷���è��"});
//        ids.add(new String[]{"hujiekaka@163.com", "593162040", "��˹ؼ��Ľ"});

        HttpClient[] clients = new HttpClient[ids.size()];
        boolean goon = true;
        while(goon) {
            System.out.println("ѭ���۷�gerenս");
            for(int i = 0; i < ids.size(); i++) {
                String[] id = ids.get(i);
                System.out.println(i + "/" + ids.size() + ": " + id[2]);
                try {
                    if(clients[i] == null) {
                        System.out.println("��¼��");
//                        clients[i] = LoginUtils.Login(id[0], id[1]);
                        clients[i] = IDUtils.IDS.get(id[0]);
                    }
                    HttpPost pm = new HttpPost(OgzqURL.URL + "/" + player + "_Top_Person.aspx");
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("type", "1"));
                    pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                    String s = IDUtils.execute(clients[i], id[0], pm);
                    if(s.indexOf("inmatch|") >= 0) {
                        continue;
                    }
                    if(s.indexOf("��") >= 0) {
                        s = s.substring(0, s.indexOf("��"));
                    }
                    if("".equals(s)) {
                        goon = false;
                        break;
                    }
                    boolean can = s.charAt(s.length() - 1) == '1';
                    if(can) {
                        pm = new HttpPost(OgzqURL.URL + "/" + player + "_Top_Person.aspx");
                        params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("type", "2"));
                        pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                        s = IDUtils.execute(clients[i], id[0], pm);
                        System.out.println(id[2] + "���۷�ս��" + s);
                    } else {
                        goon = false;
                    }
                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
            System.out.println("ѭ���۷�ս���ȴ��´�ѭ��......................");
            Thread.sleep(20000);
        }
    }

    //ѡ���ˣ�ϲ�����裬��������
    public void selectLeague(JspWriter out) throws Exception {
        List<String> ids = IDUtils.GETIDS();
        for(String id : ids) {
            HttpPost pm = new HttpPost(OgzqURL.URL + "/OGLM.aspx");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("OGLMLoad1", ""));
            pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            String s = IDUtils.execute(id, pm);
            out.println(id+":"+s+"<br/>");
            if(!"-3".equals(s)) {
//                7psg, 40manutd
                if(s.split("#")[0].equals("1")) { //like
                    pm = new HttpPost(OgzqURL.URL + "/OGLM.aspx");
                    params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("League_Add1", "1*7"));
                    pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                    s = IDUtils.execute(id, pm);
                    out.println(id+":"+s+"<br/>");
                } else { //hate
                    pm = new HttpPost(OgzqURL.URL + "/OGLM.aspx");
                    params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("League_Add1", "2*40"));
                    pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                    s = IDUtils.execute(id, pm);
                    out.println(id+":"+s+"<br/>");
                }
            }
        }
    }
}
