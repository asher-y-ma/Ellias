package com.saille.ogzq;

import com.saille.util.CommonUtils;
import com.saille.util.UtilFunctions;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

import java.util.*;
import java.io.*;

public class IDUtils {
    private static final Logger LOGGER = Logger.getLogger(IDUtils.class);
    public static Map<String, HttpClient> IDS = new Hashtable<String, HttpClient>();
    public static Map<String, String> IDPWDS = new Hashtable<String, String>();

    public static Map<String, String[]> IDObjIds = new Hashtable<String, String[]>();
    public static Map<String, Map<String, String>> IDInfos = new Hashtable<String, Map<String, String>>();
    public static Map<String, Map<String, String>> IDTasks = new Hashtable<String, Map<String, String>>();
    public static Map<String, List<Map<String, String>>> IDTaskInfos = new Hashtable<String, List<Map<String, String>>>();
    public static Map<String, Long> IDThreads = new Hashtable<String, Long>();
    public static Map<String, Boolean> JOINEDTEAMGAME = new Hashtable<String, Boolean>();
    public static Map<String, Boolean> WorldCupFinished = new Hashtable<String, Boolean>();
    public static Map<String, int[]> FubenStatus = new Hashtable<String, int[]>(); //ÿ��id�ļ�������ʣ�ೡ��
    public static Map<String, String[]> JJCAgainst = new Hashtable<String, String[]>(); //ÿ���ž������Ķ���

    public static Map<String, String> SigninDate = new Hashtable<String, String>(); //ÿ�����ճ�ǩ�����ڣ���¼�����Ƿ�ǩ����

    /* �ձ��õ��� */
    public static Map<String, ShoubianThread> ShoubianThreads = new Hashtable<String, ShoubianThread>();
    public static Map<String, String> ShoubianTime = new Hashtable<String, String>();
    public static String XiaohaoShoubianTime = null;

    /* ��������ս�õ��� */
    public static Map<String, OGLMZDZThread> OGLMZDZThreads = new Hashtable<String, OGLMZDZThread>();
    public static Map<String, String> OGLMZDZTime = new Hashtable<String, String>();
    public static Map<String, OtherLMZDThread> OTHEROGLMZDZThreads = new Hashtable<String, OtherLMZDThread>();
    public static Map<String, String> OTHEROGLMZDZTime = new Hashtable<String, String>();

    public static FirstLoginTeamgameThread firstLoginTeamgameThread = null;
    public static String firstLoginTeamgameThreadTime = null;

    public static OtherWorldcup32Thread otherWorldcup32Thread = null;
    public static String otherWorldcup32ThreadTime = null;

    public static Map<String, int[]> AllPlayer = new Hashtable<String, int[]>();
    public static List<String> XiangQianPlayer = new ArrayList<String>();
    public static Map<String, Integer> AllXiangQianPlayer = new Hashtable<String, Integer>();
    public static Map<String, Integer> AllPlayerInitial = new Hashtable<String, Integer>();
    public static Date LastModify = new Date();

    public static List<String[]> GJXLS = new ArrayList<String[]>();
    static {
        try {
            File f = new File(ConfigUtils.class.getResource("../../../../../ogzq/gjxls.ini").getPath());
            if(f.exists()) {
                try {
                    FileReader fr = new FileReader(f);
                    BufferedReader br = new BufferedReader(fr);
                    String s;
                    while((s = br.readLine()) != null) {
                        GJXLS.add(s.split("[|]"));
                    }
                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch(Exception ex) {}
    }

    public static List<String[]> JIONGIDS = new ArrayList<String[]>();
    public static List<String[]> GIDS = new ArrayList<String[]>();
    public static List<String[]> WEIIDS = new ArrayList<String[]>();
    public static List<String[]> XYIDS = new ArrayList<String[]>();
    public static List<String[]> NBIDS = new ArrayList<String[]>();
    public static List<String[]> SIDS = new ArrayList<String[]>();
    public static List<String[]> OTHERIDS = new ArrayList<String[]>();
    static {
        JIONGIDS.add(new String[]{"leonis11@e7wan*HDFC","123789","HDFC"});
        JIONGIDS.add(new String[]{"751541263@qq.com*����ɽ","3239092","����ɽ"});
        JIONGIDS.add(new String[]{"yuliang0526@163.com*�������ѧʿ","yyx121101","�������ѧʿ"});
        JIONGIDS.add(new String[]{"sw197297@163.com*��������֮��","smy19780405","��������֮��"});
        JIONGIDS.add(new String[]{"270738347@qq.com*ZM�����","zmi198678","ZM�����"});
        JIONGIDS.add(new String[]{"345777992@qq.com*����ѩ123","55885588","����ѩ123"});
        JIONGIDS.add(new String[]{"super88man66@126.com*����ʱ��","072022021","����ʱ��"});
        JIONGIDS.add(new String[]{"meijianbai@hotmail.com*�ᰮ��","meiweilin","�ᰮ��"});
        JIONGIDS.add(new String[]{"hujiekaka@163.com*��˹ؼ��Ľ","593162040","��˹ؼ��Ľ"});
        JIONGIDS.add(new String[]{"SKY@163.COM*��Ӣ������ħ","030978","��Ӣ������ħ"});
        JIONGIDS.add(new String[]{"690496636@qq.com*Carlos��ά˹","852456..","Carlos��ά˹"});
//        JIONGIDS.add(new String[]{"sevarsti@sina.com*��翹���","pmgkpmgk","��翹���"});
        JIONGIDS.add(new String[]{"rinoawing@qq.com*ŵΩ��","840907","ŵΩ��"});
        JIONGIDS.add(new String[]{"284230685@qq.com*C������","5858xjf5858","C������"});
        JIONGIDS.add(new String[]{"michael110110@hupu1*��ɷ","zengyu520","��ɷ"});
        JIONGIDS.add(new String[]{"Gino_88@hupu1*�ڳ�","vanny6620013","�ڳ�"});
        JIONGIDS.add(new String[]{"jiangshulei1988@vip.qq.com*���������","jsl88970746","���������"});
        JIONGIDS.add(new String[]{"cxz289@hupu1*CXZ","Cxz289","cxz"});
        JIONGIDS.add(new String[]{"no9_bj@sohu.com*ŷ��ս��","hanwen93","ŷ��ս��"});
        JIONGIDS.add(new String[]{"freewxlcg@163.com*СС��Ʒ","79667777","СС��Ʒ"});
        JIONGIDS.add(new String[]{"79792581@qq.com*С����","2112560715wH","С����"});
        JIONGIDS.add(new String[]{"ahfyyjq@sina.com*MilanJunior","yjq19751006","MilanJunior"});
        JIONGIDS.add(new String[]{"Gino_88@hupu1*GloryUnited","vanny6620013","GloryUnited"});
        JIONGIDS.add(new String[]{"vealzhf@hotmail.com*9869","222222","9869"});

        GIDS.add(new String[]{"88232241@qq.com*����ңԶ","hanwen93","����ңԶ"});
        GIDS.add(new String[]{"��������@hupu1*�������","58817039","�������"});
        GIDS.add(new String[]{"zxj13321667675@126.com*�ѼѾ��ֲ�","112918","�ѼѾ��ֲ�"});
        GIDS.add(new String[]{"wen52014@126.com*��������","hanwen","��������"});
        GIDS.add(new String[]{"181001147@qq.com*Ե�۷���","87765192","Ե�۷���"});
        GIDS.add(new String[]{"manking93@163.com*����","hanwen93","����"});
        GIDS.add(new String[]{"rentao@vip.sina.com*�ʼ�GFG","meiweilin","�ʼ�GFG"});
        GIDS.add(new String[]{"song11@163.com*������ҵFC","leewei917129","������ҵFC"});
        GIDS.add(new String[]{"lixiang2160@126.com*griggs","j23w3a15n41","griggs"});
        GIDS.add(new String[]{"764832681@qq.com*���ӻ�Ҫ","15896205678","���ӻ�Ҫ"});
        GIDS.add(new String[]{"smy1978@163.com*���ɷ���1","smy19780405","���ɷ���1"});
        GIDS.add(new String[]{"jx327645683*hamasi","871111","hamasi"});
        GIDS.add(new String[]{"anjou22@139.com*���Ҷӷ�����","liwei781012","���Ҷӷ�����"});
        GIDS.add(new String[]{"2359178129@qq.com*쫷���è��","mimi20080731","쫷���è��"});
        GIDS.add(new String[]{"fly123450@hupu1*��ղķʿ","www123","��ղķʿ"});
        GIDS.add(new String[]{"9253448@qq.com*���Ҷ������","19840711","���Ҷ������"});
        GIDS.add(new String[]{"12403303@qq.com*����СѾѾ","821015tt","����СѾѾ"});
        GIDS.add(new String[]{"andy9@9.com*���Һ����","a5258048","���Һ����"});
        GIDS.add(new String[]{"qilili19841212@126.com*YI������","www123","YI������"});
        GIDS.add(new String[]{"81430914@qq.com*������","14789632","������"});
        GIDS.add(new String[]{"broad@hupu5*����messi", "cao123", "����messi"});
        GIDS.add(new String[]{"104722123@qq.com*NB����","www123","NB����"});
        GIDS.add(new String[]{"104722123@qq.com*���Ҷ�C��","www123","���Ҷ�C��"});
        GIDS.add(new String[]{"masu2@qq.com*��","84786056","��"});

        WEIIDS.add(new String[]{"kkoanbfm@vip.qq.com*Sky����","yuliang83012","Sky����"});
        WEIIDS.add(new String[]{"hahako@yeah.net*SD��Ү","593425kk","SD��Ү"});
        WEIIDS.add(new String[]{"544397212@qq.com*������","hcqsy0809","������"});
        WEIIDS.add(new String[]{"������ү@hupu1*FCB��ү","huangjiaqi520","FCB��ү"});
        WEIIDS.add(new String[]{"leizhensd82@163.com*ŶҲbaba","8425792","ŶҲbaba"});
        WEIIDS.add(new String[]{"617566442@qq.com*�񸸶�","qq251314","�񸸶�"});
        WEIIDS.add(new String[]{"���ݿ�@hupu1*��ɷII","zengyu520","��ɷII"});
        WEIIDS.add(new String[]{"313724849@qq.com*W������w","8751107","W������w"});
        WEIIDS.add(new String[]{"63208995@qq.com*C�޼���","hb6620013","C�޼���"});
        WEIIDS.add(new String[]{"tim1983831@e7wan*TIMI","1983831","TIMI"});
        WEIIDS.add(new String[]{"602888616@qq.com*lianni","123456","lianni"});
        WEIIDS.add(new String[]{"156451865@qq.com*����������ѵ","lspmgk","����������ѵ"});
        WEIIDS.add(new String[]{"chulang@163.com*��������","lspmgk","��������"});
        WEIIDS.add(new String[]{"fanmingsuo@163.com*��֮mrfan","880612","��֮mrfan"});
        WEIIDS.add(new String[]{"82382037111@qq.com*ƺʯ2012","cllbwcnm","ƺʯ2012"});
        WEIIDS.add(new String[]{"272271809@qq.com*˦˦Сߴߴ","19920217","˦˦Сߴߴ"});
        WEIIDS.add(new String[]{"823820371@qq.com*��ɽ1976","lspmgk","��ɽ1976"});
        WEIIDS.add(new String[]{"Italyone2003@yahoo.com.cn*���ĸ��˵۹�","lspmgk","���ĸ��˵۹�"});
        WEIIDS.add(new String[]{"707535504@qq.com*BF����","xulei521","BF����"});
        WEIIDS.add(new String[]{"278224476@qq.com@qq*argetina","123q456w789e","argetina"});
        WEIIDS.add(new String[]{"wzb0817@163.com*����","123123","����"});

        XYIDS.add(new String[]{"fanmingsuo2@163.com*MFAN","880612","MFAN"});
        XYIDS.add(new String[]{"jiangshulei1988@163.com*��ԥ����","jsl88970746","��ԥ����"});
        XYIDS.add(new String[]{"killer160@126.com*����������","123456","����������"});
        XYIDS.add(new String[]{"7125608@163.com*�ҽв�����","fhx520723","�ҽв�����"});
        XYIDS.add(new String[]{"403114076@qq.com*���Ҷӷ�־��","hb6620013","���Ҷӷ�־��"});
        XYIDS.add(new String[]{"270373371@qq.com*��love","shilei001","��love"});
        XYIDS.add(new String[]{"yz_jzq@163.com*��������","lspmgk","��������"});
        XYIDS.add(new String[]{"wjq801109@163.com*yao����","lspmgk","yao����"});
        XYIDS.add(new String[]{"karen525@tom.com*����ƭ��","25313923","����ƭ��"});
        XYIDS.add(new String[]{"fanmingsuo1@163.com*fan7","840907","fan7"});
        XYIDS.add(new String[]{"421623479@qq.com*����Ԫ�϶�","fhx520723","����Ԫ�϶�"});
        XYIDS.add(new String[]{"zhao6527@163.com*���ͷ�ؼ","lspmgk","���ͷ�ؼ"});
        XYIDS.add(new String[]{"101977723@qq.com*����ħ����","lspmgk","����ħ����"});
        XYIDS.add(new String[]{"1412913604@qq.com*A߱��","www123","A߱��"});
        XYIDS.add(new String[]{"472545875@qq.com*Angelababy","454870","Angelababy"});
        XYIDS.add(new String[]{"xieqigan@qq.com*С����","lspmgk","С����"});
        XYIDS.add(new String[]{"115271540@qq.com*Magic����","lspmgk","Magic����"});
        XYIDS.add(new String[]{"279524695@qq.com*����С��","www123","����С��"});
        XYIDS.add(new String[]{"zhouzhu998@sina.com*ŷ���޵���","yuliang83012","ŷ���޵���"});
        XYIDS.add(new String[]{"279512194@qq.com*����Ҭ��կ","lspmgk","����Ҭ��կ"});
        XYIDS.add(new String[]{"kouling.859@163.com*����y","www123","����y"});
        XYIDS.add(new String[]{"fanmingsuo3@163.com*��fman","880612","��fman"});

        NBIDS.add(new String[]{"freeseas*ɳ�轴","11335577","ɳ�轴"});
        NBIDS.add(new String[]{"3897021733@qq.com*��������","lspmgk","��������"});
        NBIDS.add(new String[]{"1422485013@qq.com*B߱��","www123","B߱��"});
        NBIDS.add(new String[]{"www.569323373@qq.163.com*˼������","lspmgk","˼������"});
        NBIDS.add(new String[]{"fanmingsuo4@163.com*mrf","880612","mrf"});
        NBIDS.add(new String[]{"woyaofabiaola@163.com*�ܵ��Ȳ���","lspmgk","�ܵ��Ȳ���"});
        NBIDS.add(new String[]{"www.273919473@qq.com*��¡","lspmgk","��¡"});
        NBIDS.add(new String[]{"355537@qq.com*��ԭ���Ϻ�","123456","��ԭ���Ϻ�"});
        NBIDS.add(new String[]{"189153298@qq.com*����","xy721207","����"});
        NBIDS.add(new String[]{"justin801022@163.com*��ح��","lspmgk","��ح��"});
        NBIDS.add(new String[]{"444825566@qq.com*�µ�����8��","lspmgk","�µ�����8��"});
        NBIDS.add(new String[]{"530528694@qq.com*СU","www123","СU"});
        NBIDS.add(new String[]{"zjzhaoxiaojun03@126.com*ϼɽ֮��","lspmgk","ϼɽ֮��"});
        NBIDS.add(new String[]{"stevending1@163.com*EV��Լ��","lspmgk","EV��Լ��"});
        NBIDS.add(new String[]{"mmzhaoxiaojun@126.com*RedBull8","lspmgk","RedBull8"});
        NBIDS.add(new String[]{"273734644@qq.com*˹�ʹ�ĵ�","lspmgk","˹�ʹ�ĵ�"});
        NBIDS.add(new String[]{"zjzhaoxiaojun02@126.com*տ���޵�","lspmgk","տ���޵�"});
        NBIDS.add(new String[]{"mrshanyao@163.com*ͦ�Ŵ����","lspmgk","ͦ�Ŵ����"});
        NBIDS.add(new String[]{"zjzhaoxiaojun@126.com*RedBull1","lspmgk","RedBull1"});
        NBIDS.add(new String[]{"124906932@qq.com*XD������","lspmgk","XD������"});
        NBIDS.add(new String[]{"313025470@qq.com*��֮������","lspmgk","��֮������"});

        SIDS.add(new String[]{"020637@163.com*÷��÷��","lspmgk","÷��÷��"});
        SIDS.add(new String[]{"shmilyfc@vip.qq.com*��������䳾","lspmgk","��������䳾"});
        SIDS.add(new String[]{"366440852@qq.com*��AC����","lspmgk","��AC����"});
        SIDS.add(new String[]{"757210292@qq.com*��Զkk","lspmgk","��Զkk"});
        SIDS.add(new String[]{"550267853@qq.com*�й�Ů��","lspmgk","�й�Ů��"});
        SIDS.add(new String[]{"zjzhaoxiaojun01@126.com*�㶫��ţ��","lspmgk","�㶫��ţ��"});
        SIDS.add(new String[]{"110932097@qq.com*ѾѾ����","821015tt","ѾѾ����"});
        SIDS.add(new String[]{"921851791@qq.com*�ɰ�СѾѾ","821015tt","�ɰ�СѾѾ"});
        SIDS.add(new String[]{"316639404@qq.com*o����o","lspmgk","o����o"});
        SIDS.add(new String[]{"zjzhaoxi7aojun@126.com*RED","lspmgk","RED"});
        SIDS.add(new String[]{"liaoqing262@163.com*������","lspmgk","������"});
        SIDS.add(new String[]{"183444340@qq.com*����ҵ���","lspmgk","����ҵ���"});
        SIDS.add(new String[]{"707000142@qq.com*Ű����κ","lspmgk","Ű����κ"});
        SIDS.add(new String[]{"348794240@qq.com*С����ʿ","lspmgk","С����ʿ"});
        SIDS.add(new String[]{"276300227@qq.com*��������","lspmgk","��������"});
        SIDS.add(new String[]{"zjzhaoxiaojun04@126.com*�࿲֮��","lspmgk","�࿲֮��"});
        SIDS.add(new String[]{"wqs1613@163.com*��������","lspmgk","��������"});
        SIDS.add(new String[]{"zhlzxd@163.com*��Ģ��","lspmgk","��Ģ��"});
        SIDS.add(new String[]{"LI5JIN9@SINA.COM*I������SK","lspmgk","I������SK"});
        SIDS.add(new String[]{"haibo414@126.com*ʲô���߰���","lspmgk","ʲô���߰���"});

//        NBIDS.add(new String[]{"xy721207@qq.com*����¹��","xy721207","����¹��"});
        OTHERIDS.add(new String[]{"245381501@qq.com*QQCC","123456","QQCC"});
    }

    public static List<String[]> GETJIONGIDS() {
        List<String[]> ret = new ArrayList<String[]>();
        for(String[] s : JIONGIDS) {
            ret.add(s);
        }
        return ret;
    }

    public static String getNick(String id) {
        return IDInfos.get(id).get("nick");
    }

    public static List<String> GETIDS() {
        Set<String> ss = IDS.keySet();
        List<String> ret = new ArrayList<String>();
        for(String s : ss) {
            ret.add(s);
        }
        Collections.sort(ret);
        return ret;
    }

    private static void loadXiangQianPlayer() {
        try {
            File f = new File("D:\\excel\\1.xls");
            if(!f.exists()) {
                f = new File("C:\\Documents and Settings\\Ellias\\����\\1.xls");
            }
            FileInputStream fis = new FileInputStream(f);
            HSSFWorkbook workbook = new HSSFWorkbook(fis);
            HSSFSheet sheet = workbook.getSheet("��Ա");
            AllXiangQianPlayer.clear();
            for(int i = 1; i < 551; i++) {
                HSSFRow row = sheet.getRow(i);
                if(row == null) {
                    continue;
                }
                String name = row.getCell(3).getRichStringCellValue().getString();
                String level = row.getCell(4).getRichStringCellValue().getString();
                String pos = row.getCell(5).getRichStringCellValue().getString();
                HSSFCell cell = row.getCell(16);
                String key = name + "_" + level.charAt(0) + "_" + pos.charAt(0);
                AllXiangQianPlayer.put(key, cell == null ? 0 : 1);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void loadPlayerInitial() {
        try {
            File f = new File("D:\\excel\\1.xls");
            if(!f.exists()) {
                f = new File("C:\\Documents and Settings\\Ellias\\����\\1.xls");
            }

            FileInputStream fis = new FileInputStream(f);
            HSSFWorkbook workbook = new HSSFWorkbook(fis);
            HSSFSheet sheet = workbook.getSheet("��ʼ����");
            AllPlayerInitial.clear();
            for(int i = 2; i <= sheet.getLastRowNum(); i++) {
                HSSFRow row = sheet.getRow(i);
                if(row == null) {
                    continue;
                }
                String name = row.getCell(0).getRichStringCellValue().getString();
                String pos = row.getCell(1).getRichStringCellValue().getString();
                String level = row.getCell(2).getRichStringCellValue().getString();

                String key = name + "_" + level.charAt(0) + "_" + pos.charAt(0);

                AllPlayerInitial.put(key, 1);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private synchronized static void loadPlayerDetail() {
        try {
            File f = new File("D:\\excel\\1.xls");
            if(!f.exists()) {
                f = new File("C:\\Documents and Settings\\Ellias\\����\\1.xls");
            }

            LastModify.setTime(f.lastModified());
            FileInputStream fis = new FileInputStream(f);
            HSSFWorkbook workbook = new HSSFWorkbook(fis);
            HSSFSheet sheet = workbook.getSheet("��Ա��ϸ");
            AllPlayer.clear();
            for(int i = 2; i <= sheet.getLastRowNum(); i++) {
                HSSFRow row = sheet.getRow(i);
                if(row == null) {
                    continue;
                }
                String name = row.getCell(1).getRichStringCellValue().getString();
                String level = row.getCell(2).getRichStringCellValue().getString();
                String pos = row.getCell(3).getRichStringCellValue().getString();
                int ability = (int)row.getCell(4).getNumericCellValue();
                String key = name + "_" + level.charAt(0) + "_" + pos.charAt(0);
                int full = StringUtils.isEmpty(row.getCell(14).getRichStringCellValue().getString()) ? 0 : 1;
                if(AllPlayer.containsKey(key)) {
                    throw new Exception("�����ظ���Ա��");
                } else {
                    AllPlayer.put(key, new int[]{ability, full});
                }
            }

            sheet = workbook.getSheet("��Ա");
            XiangQianPlayer.clear();
            for(int i = 1; i <= sheet.getLastRowNum(); i++) {
                HSSFRow row = sheet.getRow(i);
                HSSFCell cell = row.getCell(8);
                if(cell == null || cell.getCellType() != HSSFCell.CELL_TYPE_NUMERIC) {
                    continue;
                }
                if(((int) cell.getNumericCellValue()) != 1) {
                    continue;
                }
                String name = row.getCell(3).getRichStringCellValue().getString();
                String level = row.getCell(4).getRichStringCellValue().getString();
                String pos = row.getCell(5).getRichStringCellValue().getString();
                String key = name + "_" + level.charAt(0) + "_" + pos.charAt(0);
                XiangQianPlayer.add(key);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public static synchronized void loadPlayer() {
        loadPlayerDetail();
        loadXiangQianPlayer();
        loadPlayerInitial();
    }

    /* ����Ƿ��и���Ա�ĳ�ʼ�������ݣ�û������txt������ */
    public static boolean checkPlayerInitial(String name, String position, String level) {
        String key = name + "_" + level + "_" + position;
        if(AllPlayerInitial == null || AllPlayerInitial.size() == 0) {
            loadPlayer();
        }
        Boolean has = false;
        Integer i = AllPlayerInitial.get(key);
        has = i != null && i == 1;
        return has;
    }

    public static synchronized void savePlayerInitial(String name, String pos, String level, String[] atts) {
        Map<String, String> l = new Hashtable<String, String>();
        l.put("1", "1��ͨ");
        l.put("2", "2����");
        l.put("3", "3��Ӣ");
        l.put("4", "4�ܳ�");
        l.put("5", "5����");
        l.put("6", "6����");
        Map<String, String> p = new Hashtable<String, String>();
        p.put("1", "1�Ž�");
        p.put("2", "2����");
        p.put("3", "3�г�");
        p.put("4", "4ǰ��");
        try {
            File f = new File("D:\\newPlayerInitial.txt");
            if(!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(f, true);
            StringBuffer sb = new StringBuffer("\r\n" + name + "\t" + p.get(pos) + "\t" + l.get(level));
            for(String att : atts) {
                sb.append("\t").append(att);
            }
            fos.write(sb.toString().getBytes());
            fos.close();
        } catch(Exception ex) {
            UtilFunctions.LogError(LOGGER, ex);
        }
    }

    public synchronized static int[] checkPlayer(String name, String position, String level) {
        String key = name + "_" + level + "_" + position;
        if(AllPlayer == null || AllPlayer.size() == 0) {
            loadPlayer();
        }
        if(AllPlayer.containsKey(key)) {
            return AllPlayer.get(key);
        } else {
            return new int[]{0, 0};
        }
    }

    public synchronized static boolean hasXiangQianInfo(String name, String position, String level) {
        String key = name + "_" + level + "_" + position;
        if(AllXiangQianPlayer == null || AllXiangQianPlayer.size() == 0) {
            loadXiangQianPlayer();
        }
        Integer i = AllXiangQianPlayer.get(key);
        return i == null || i == 1;
    }

    public synchronized static boolean isXiangQianPlayer(String name, String position, String level) {
        String key = name + "_" + level + "_" + position;
        if(XiangQianPlayer == null || XiangQianPlayer.size() == 0) {
            loadPlayer();
        }
        return XiangQianPlayer.contains(key);
    }

    public static String execute(HttpClient client, String email, HttpPost pm) {
        int count = 0;
        int retry = 0;
        while(count < 3) {
            CloseableHttpResponse response = null;
            InputStream is = null;
            try {
                if(client == null) {
                    return "";
                }
                String ret = "";
                synchronized(client) {
                    pm.getParams().setParameter("Connection", "keep-alive");
                    response = (CloseableHttpResponse) client.execute(pm);
                    ret = CommonUtils.getString(response.getEntity().getContent(), "utf-8");
                    is = response.getEntity().getContent();
                    is.close();
                    response.close();
//                    pm.abort();
                    pm.releaseConnection();
                    return ret;
//                    if(ret.indexOf("ASP.NET") != -1 || ret.indexOf("δ�������������õ������ʵ��") != -1) {
//                        if(email.indexOf("sevarsti") != -1) {
//                            client = LoginUtils.Login(email, "pmgkpmgk");
//                        } else {
//                            client = LoginUtils.Login(email, "lspmgk");
//                        }
//                        IDUtils.IDS.put(email, client);
//                        OperationUtils.defaults(email, 7);
//                        IDUtils.WorldCupFinished.put(email, false);
//
//                        AutoLoopThread thread = new AutoLoopThread(email);
//                        thread.start();
//                    }
                }
//                return ret;
            } catch(Exception ex) {
                System.out.println(email + "/" + pm.getURI().getPath() + " occurs error: " + ex.getMessage() + ": " + retry);
                if("Timeout waiting for connection from pool".equals(ex.getMessage())) {
                } else {
                    count++;
                }
                retry++;
//                ex.printStackTrace();
                continue;
            } finally{
                if(is != null) {
                    try {is.close();} catch(Exception ex) {ex.printStackTrace();}
                }
                if(response != null) {
                    try {response.close();} catch(Exception ex) {ex.printStackTrace();}
                }
                pm.releaseConnection();
            }
        }
        return null;
    }

    public static String execute(String email, HttpPost pm) {
        int count = 0;
        try {
            HttpClient client = IDS.get(email);
            if(client == null) {
                return "";
            }
            String ret = execute(client, email, pm);
            if(ret.indexOf("ASP.NET") != -1 || ret.indexOf("δ�������������õ������ʵ��") != -1 ||
                    ret.indexOf("action=\"MiddleMan.aspx\"") >= 0) {
                    client = LoginUtils.Login(email, IDPWDS.get(email));
                IDUtils.IDS.put(email, client);
                OperationUtils.defaults(email, 7);
                IDUtils.WorldCupFinished.put(email, false);
//                IDUtils.FubenStatus.put(email, new int[]{5,5,5,5,5});

                AutoLoopThread thread = new AutoLoopThread(email);
                thread.start();
            }
            return ret;
        } catch(Exception ex) {
            System.out.println(email + "/" + pm.getURI().getPath() + " occurs error: " + ex.getMessage());
            pm.releaseConnection();
        }
        return null;
    }

    public synchronized static void insertXiangqianPlayer(String email, int level, String[] playerDetail) {
        List<String[]> currPlayers = getXiangqianPlayer(email, level);
        if(currPlayers == null) {
            currPlayers = new ArrayList<String[]>();
        }
        currPlayers.add(playerDetail);
        TeamXiangqianCacheThread.saveCache(email, level, currPlayers);
    }

    public synchronized static void deleteXiangqianPlayer(String email, int level, String playerId) {
        List<String[]> currPlayers = getXiangqianPlayer(email, level);
        if(currPlayers == null || currPlayers.size() == 0) {
            return;
        }
        for(int i = 0; i < currPlayers.size(); i++) {
            if(currPlayers.get(i)[0].equals(playerId)) {
                currPlayers.remove(i);
                TeamXiangqianCacheThread.saveCache(email, level, currPlayers);
                return;
            }
        }
    }

    public static List<String[]> getXiangqianPlayer(String email, int level) {
        FileReader fr = null;
        BufferedReader br = null;
        try {
            List<String[]> ret = new ArrayList<String[]>();
            for(int i = (level == 0 ? 1 : level); i <= (level == 0 ? 6 : level); i++) {
                String filepath = TeamXiangqianCacheThread.class.getResource("../../../../../ogzq/cache/xiangqian/").getPath() + email + "_" + i + ".txt";
                filepath = filepath.replaceAll("[*]", "~");
                File f = new File(filepath);
                if(f.exists()) {
                    fr = new FileReader(f);
                    br = new BufferedReader(fr);
                    String s;
                    while((s = br.readLine()) != null) {
                        if(!"".equals(s)) {
                            String[] ss = s.split("[|]");
                            ret.add(ss);
                        }
                    }
                }
            }
            return ret;
        } catch(Exception ex) {
            ex.printStackTrace();
            return null;
        } finally{
            if(br != null) {
                try {
                    br.close();
                } catch(Exception ex) {}
            }
            if(fr != null) {
                try {
                    fr.close();
                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static boolean isZijinPlayer(String playerId) {
        String[] zijins = new String[]{"671158","593008","620890","262936","821915","823685","849215","509416","629122","668440","618592","253864","377230","782633","335795","624935","632467","552736","618514","244450","768971","782627","342503","821603","622066","257068","549850","622864","848999","692656","330460","771919"};
        for(String s : zijins) {
            if(s.equals(playerId)) {
                return true;
            } else {
//                return false;
            }
        }
        return false;
    }
    /*
    * ��������ImaMatchCategory
    * 5: ������, KFC=0
    * 38: ����۷�
    * */
}