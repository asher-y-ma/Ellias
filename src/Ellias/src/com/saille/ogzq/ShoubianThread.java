package com.saille.ogzq;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.log4j.Logger;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Ellias
 * Date: 2013-9-10
 * Time: 18:40:12
 * To change this template use File | Settings | File Templates.
 */
public class ShoubianThread extends Thread {
    private final Logger LOGGER = Logger.getLogger(this.getClass());
    private String email = "sevarsti@sina.com";
    private SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
    private String[] coaches = new String[]{"�ϵϰ���", "��¬", "�������", "��Ƥ", "����ɭ", "ϣ����"};
    private List<String[]> coachStatus = new ArrayList<String[]>();
    private Map<String, Integer> abilities = new Hashtable<String, Integer>();
    private List<String> cannotShoubian = new ArrayList<String>();
    public boolean cont = true;
    public boolean running = false;

    public ShoubianThread(String email) {
        this.setName("ShoubianThread-" + email);
        this.email = email;
    }

    public void run() {
        if(IDUtils.ShoubianThreads.containsKey(this.email) && IDUtils.ShoubianThreads.get(this.email) != this) {
            IDUtils.ShoubianThreads.get(this.email).cont = false;
        }
        IDUtils.ShoubianThreads.put(this.email, this);
        while(cont) {
            this.running = true;
            try {
                Calendar c = Calendar.getInstance();
                if(Integer.parseInt(sdf.format(c.getTime())) < 1829) {
                    try{
                        LOGGER.info("ʱ��δ������ʱ30��");
                        Thread.sleep(30 * 1000);
                        continue;
                    } catch(Exception ex) {}
                } else if(Integer.parseInt(sdf.format(c.getTime())) >= 2001) {
                    LOGGER.info("���ս�������˳��ձ�");
                    break;
                }
                getCurrentCoach();
                int idx = -1;
                boolean noWeixing = false;
                if(coachStatus.size() == 0) {
                    continue;
                }
                for(String coachName : coaches) {
                    for(idx = 0; idx < 6; idx++) {
                        if(coachStatus.get(idx)[4].equals(coachName)) {
                            if(coachStatus.get(idx)[14].equals("0")) { //û������
                                noWeixing = true;
                            }
                            break;
                        }
                    }
                    if(noWeixing) {
                        break;
                    }
                }
                if(!noWeixing) { //��������
                    LOGGER.info(email + "�������ǣ��˳�ѭ��");
                    OperationUtils.changeTactic(email, 1);
                    break;
                }
                LOGGER.info(email + "��Ҫ�ձ����ǵĽ�����" + idx + "/" + coachStatus.get(idx)[4]);
                String[] coach = coachStatus.get(idx);
                changeCoach(coach[4]);

                String[] nextClubId = getNextOpponent();
                if(nextClubId == null) { //û���¸����֣�˵��û�취�ձ���
                    LOGGER.info(email + "û����һ�����֣�����ѭ��");
                    break;
                }
                LOGGER.info(email + "��һ�����֣�" + nextClubId[0] + "/" + nextClubId[1]);
                boolean inMatch = doMatch(nextClubId[0], nextClubId[1]);
                try {
                    if(inMatch) {
                        Thread.sleep(5000);
                    }
                } catch(Exception ex) {}
                Thread.sleep(1000);
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
        this.running = false;
    }

    //����ֵ���Ƿ�������
    private boolean doMatch(String clubId, String nick) throws Exception {
        HttpPost pm = new HttpPost(OgzqURL.URL + "/MatchList/TeamGame/TeamGame.aspx");
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("insertMatch", clubId));
        params.add(new BasicNameValuePair("MatchPrice", "-1200"));
        pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
        String ret = IDUtils.execute(this.email, pm);
        int intRet = Integer.parseInt(ret);
        LOGGER.info(email + "���б��������" + intRet);
        if(intRet > 0) {
            return true;
        } else if(intRet == -1) { //��սʧ�ܡ�
        } else if(intRet == -2) { //������սÿ��9��00~20��00������
        } else if(intRet == -3) { //��������ս�����ε���ӡ�
        } else if(intRet == -4) { //����������ڱ�������ʱ������ս��
            return true;
        } else if(intRet == -5) { //�Է����ڱ�������ʱ������ս��
        } else if(intRet == -100) { //��������ս�����Ѵ�60�����ޣ�������������ս��
        } else if(intRet == -10 || intRet == -8) { //��սʧ�ܡ�
        } else if(intRet == -9) { //����ѡ���г̶�
        } else if(intRet == -6) { //��Ҳ���
        } else if(intRet == -7) { //�������˲�ģʽ������ս
        } else if(intRet == -11) { //������ѱ����
        } else if(intRet == -10001) { //����ǰ�������������Ƕӣ��������������
        } else if(intRet == -10002) { //������������Ƕӣ������ձ�������ҡ�
            LOGGER.info(email + "�����ǣ������ձ࣬�˳�ѭ��");
            this.cont = false;
            return false;
        } else if(intRet == -10003) { //����������˶ӣ������ձࡣ
            cannotShoubian.add(nick);
        } else if(intRet == -10004) { //������Ѿ���������ҵ����Ƕӣ������ձࡣ
            cannotShoubian.add(nick);
        } else if(intRet == -100041) { //������Ѿ����������Ƕӣ������ձࡣ
            cannotShoubian.add(nick);
        } else if(intRet == -10005) { //����ҽ����Ѳ��ܳ�Ϊ���Ƕӡ�
            cannotShoubian.add(nick);
        } else if(intRet == -10006) { //��û����������
        } else if(intRet == -10008) { //������ʹ�ö�ȡ���ܣ�����ִ�е�ǰ����
        } else if(intRet == -1000) { //�ձ��˲�
        }
        return false;
    }

    private String[] getNextOpponent() throws Exception {
        String[] places = MonitorTeamgameThread.getInstance().myPlaces.get(email);
        if(places == null) {
            return null;
        }
        String myPlace = places[6];
        int myPage = Integer.parseInt(myPlace.substring(0, myPlace.indexOf("-")));
        int myIndex = Integer.parseInt(myPlace.substring(myPlace.indexOf("-") + 1));
        int count = 0;
        int myShili = Integer.parseInt(IDUtils.IDInfos.get(email).get("shili"));
        String[] retClubId = null;
        String[] retBackup = null;
        for(int i = 0; i < 10; i++) {
            int page = myPage - 1, idx = myIndex + 1 + i;
            if(idx > 10) {
                page += 1;
                idx -= 10;
            }
            String[] opp = MonitorTeamgameThread.getInstance().places.get((page+1) + "-" + (idx));
            if(opp == null) {
                return null;
            }
            if(opp[9].equals("��")) {
                continue;
            }
            if(cannotShoubian.contains(opp[2])) {
                continue;
            }
            int oppAbility = 0;
            if(abilities.containsKey(opp[2])) {
                oppAbility = abilities.get(opp[2]);
            } else {
                oppAbility = getPlayerAbility(opp[0], opp[9]);
                abilities.put(opp[2], oppAbility);
            }
            if(oppAbility > (myShili - 5000)) {
                cannotShoubian.add(opp[2]);
                continue;
            }
            if(opp[7].equals("3")) { //����У�ֱ�ӷ���
                return new String[]{opp[0], opp[2]};
            } else if(opp[7].equals("0")) { //�ޱ���
                retClubId = new String[]{opp[0], opp[2]};
            } else {
                retBackup = new String[]{opp[0], opp[2]}; //�б����Ķ�����Ϊ��ѡ������δ�ձ����Ӷ��ڱ�������ֱ������
            }
        }
        if(retClubId == null) {
            if(retBackup == null) {
                LOGGER.info(email + "û�п��Դ�ı�����Ҳû�б��ö�");
                return null;
            } else {
                LOGGER.info(email + "û�п��Դ�ı��������ر�����ӣ�" + retBackup == null ? "" : (retBackup[0] + "/" + retBackup[1]));
                retClubId = retBackup;
            }
        }
        LOGGER.info(email + "���֣�" + retClubId[0] + "/" + retClubId[1]);
        return retClubId;
    }

    private int getPlayerAbility(String playerId, String club) throws Exception {
        HttpPost pm = new HttpPost(OgzqURL.URL + "/TeamAndPlayer/Team.aspx");
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("LoadTeam1", playerId));
        pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
        String ret = IDUtils.execute(email, pm);
        String ability = ret.substring(0, ret.lastIndexOf("[" + club + "]"));
        ability = ability.substring(0, ability.lastIndexOf(".png"));
        ability = ability.substring(0, ability.lastIndexOf("#"));
        ability = ability.substring(ability.lastIndexOf("#") + 1);
        return Integer.parseInt(ability);
    }

    private void changeCoach(String coachName) throws Exception {
        HttpPost pm = new HttpPost(OgzqURL.URL + "/Coach/Coach.aspx");
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("UpdateMyTrainMan1", coachName));
        pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
        IDUtils.execute(email, pm);
        LOGGER.info(email + "��������Ϊ��" + coachName);
    }

    private void getCurrentCoach() throws Exception {
        HttpPost pm = new HttpPost(OgzqURL.URL + "/Coach/Coach.aspx");
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("LoadCoach1", ""));
        pm.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
        String ret = IDUtils.execute(email, pm);
        if(StringUtils.isEmpty(ret)) {
            return;
        }
        ret = ret.substring(ret.indexOf("@") + 1);
        ret = ret.substring(0, ret.indexOf("@"));
        String[] coaches = ret.split("\\^");
        coachStatus = new ArrayList<String[]>();
        for(String c : coaches) {
//            1|0      |2|1|��¬    |7 |9400 |-999 |0   |44 |30 |18 |10|0 |0   |0|0|0|0|0|0^
//            1|0      |2|2|ϣ����  |7 |9400 |-999 |1   |30 |44 |18 |10|0 |0   |0|0|0|0|0|0^
//            1|0      |2|3|��Ƥ    |7 |9400 |-999 |2   |18 |30 |44 |10|0 |0   |0|0|0|0|0|0^
//            1|0      |1|4|�ϵϰ���|5 |73618|70000|0   |92 |60 |34 |16|86|0   |0|0|0|0|0|0^
//            1|0      |0|5|����ɭ  |2 |5987 |13000|1   |42 |68 |22 |10|10|0   |0|0|0|0|0|0^
//            1|1      |0|6|�������|1 |3316 |5000 |2   |18 |36 |60 |8 |57|0   |0|0|0|0|0|0
//     �Ƿ�Ƹ�� current     name     lv exp   next  type att mid def       ����
//            0 1       2 3 4        5  6     7     8    9   10  11  12 13 14
            coachStatus.add(c.split("[|]"));
        }
    }
}
