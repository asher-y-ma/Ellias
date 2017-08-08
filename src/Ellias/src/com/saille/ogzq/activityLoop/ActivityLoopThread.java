package com.saille.ogzq.activityLoop;

import org.apache.log4j.Logger;

import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;

import com.saille.ogzq.OgzqUtils;

/**
 * Created by IntelliJ IDEA.
 * User: Ellias
 * Date: 2016-6-3
 * Time: 9:22:35
 * To change this template use File | Settings | File Templates.
 */
public class ActivityLoopThread extends Thread {
    private static ActivityLoopThread instance = null;
    private final static Logger LOGGER = Logger.getLogger(ActivityLoopThread.class);
    private static String lastCheckDate = "00000000";
    private static Map<String, String> lastExecuteDate = new HashMap<String, String>();
//    ÿ��һ��
//          ��˫��,�۷���ս������תתת
//    ÿ�ܶ���
//          ��˫��,�۷���ս,����תתת,�ھ�����,��Ա����Ӫ
//    ÿ������
//          ��Ա����Ӫ
//    ÿ���ģ�
//          ҹ������,ŷ��������
//    ÿ���壺
//          ҹ������,ŷ��������,������
//    ÿ������
//          ҹ������,������
//    ÿ���գ�
//          ҹ������
    private ActivityLoopThread() {}

    public synchronized static ActivityLoopThread getInstance() {
        if(instance == null) {
            instance = new ActivityLoopThread();
        }
        return instance;
    }

    public void run() {
        LOGGER.info("���������߳�");
        while(true) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                SimpleDateFormat sdf2 = new SimpleDateFormat("HHmm");
                String today = sdf.format(new Date());
                String now = sdf2.format(new Date());
//                if(today.compareTo(lastCheckDate) > 0) {
                    if(now.compareTo("1100") > 0) {
                        Calendar c = Calendar.getInstance();
                        int weekday = c.get(Calendar.DAY_OF_WEEK);
                        switch(weekday) {
                            case Calendar.MONDAY:
                                {
                                    try {
                                        if(!lastExecuteDate.containsKey("egg") || today.compareTo(lastExecuteDate.get("egg")) > 0) {
                                            LOGGER.info("��������ҵ�");
                                            OgzqUtils.crazyEggFree();
                                            lastExecuteDate.put("egg", today);
                                        }
                                    } catch(Exception ex) {
                                        ex.printStackTrace();
                                    }
                                    try {
                                        if(!lastExecuteDate.containsKey("topchallenge") || today.compareTo(lastExecuteDate.get("topchallenge")) > 0) {
                                            LOGGER.info("���а����۷�");
                                            BayernThread.getInstance().start();
                                            lastExecuteDate.put("topchallenge", today);
                                        }
                                    } catch(Exception ex) {
                                        ex.printStackTrace();
                                    }
                                    try {
                                        if(!lastExecuteDate.containsKey("superzhuanzhuan") || today.compareTo(lastExecuteDate.get("superzhuanzhuan")) > 0) {
                                            LOGGER.info("���г���תת");
                                            OgzqUtils.superZhuanzhuan();
                                            lastExecuteDate.put("superzhuanzhuan", today);
                                        }
                                    } catch(Exception ex) {
                                        ex.printStackTrace();
                                    }
                                }
                                break;
                            case Calendar.TUESDAY:
                                {
                                    try {
                                        if(!lastExecuteDate.containsKey("egg") || today.compareTo(lastExecuteDate.get("egg")) > 0) {
                                            LOGGER.info("��������ҵ�");
                                            OgzqUtils.crazyEggFree();
                                            lastExecuteDate.put("egg", today);
                                        }
                                    } catch(Exception ex) {
                                        ex.printStackTrace();
                                    }
                                    try {
                                        if(!lastExecuteDate.containsKey("topchallenge") || today.compareTo(lastExecuteDate.get("topchallenge")) > 0) {
                                            LOGGER.info("���а����۷�");
                                            BayernThread.getInstance().start();
                                            lastExecuteDate.put("topchallenge", today);
                                        }
                                    } catch(Exception ex) {
                                        ex.printStackTrace();
                                    }
                                    try {
                                        if(!lastExecuteDate.containsKey("superzhuanzhuan") || today.compareTo(lastExecuteDate.get("superzhuanzhuan")) > 0) {
                                            LOGGER.info("���г���תת");
                                            OgzqUtils.superZhuanzhuan();
                                            lastExecuteDate.put("superzhuanzhuan", today);
                                        }
                                    } catch(Exception ex) {
                                        ex.printStackTrace();
                                    }
                                }
                                break;
                            case Calendar.THURSDAY:
                                {
                                    try {
                                        if(!lastExecuteDate.containsKey("liansuo") || today.compareTo(lastExecuteDate.get("liansuo")) > 0) {
                                            LOGGER.info("����������");
                                            OgzqUtils.liansuo();
                                            lastExecuteDate.put("liansuo", today);
                                        }
                                    } catch(Exception ex) {
                                        ex.printStackTrace();
                                    }
                                }
                                break;
                            case Calendar.FRIDAY:
                                {
                                    try {
                                        if(!lastExecuteDate.containsKey("liansuo") || today.compareTo(lastExecuteDate.get("liansuo")) > 0) {
                                            LOGGER.info("����������");
                                            OgzqUtils.liansuo();
                                            lastExecuteDate.put("liansuo", today);
                                        }
                                    } catch(Exception ex) {
                                        ex.printStackTrace();
                                    }
                                    try {
                                        if(!lastExecuteDate.containsKey("daliwan") || today.compareTo(lastExecuteDate.get("daliwan")) > 0) {
                                            LOGGER.info("���д�����");
                                            DaliwanThread.getInstance().start();
                                            lastExecuteDate.put("daliwan", today);
                                        }
                                    } catch(Exception ex) {
                                        ex.printStackTrace();
                                    }
                                }
                                break;
                            case Calendar.SATURDAY:
                                {
                                    try {
                                        if(!lastExecuteDate.containsKey("gezipu") || today.compareTo(lastExecuteDate.get("gezipu")) > 0) {
                                            LOGGER.info("���и�����");
                                            GetGridGiftThread.getInstance().start();
                                            lastExecuteDate.put("gezipu", today);
                                        }
                                    } catch(Exception ex) {
                                        ex.printStackTrace();
                                    }
                                    try {
                                        if(!lastExecuteDate.containsKey("daliwan") || today.compareTo(lastExecuteDate.get("daliwan")) > 0) {
                                            LOGGER.info("���д�����");
                                            DaliwanThread.getInstance().start();
                                            lastExecuteDate.put("daliwan", today);
                                        }
                                    } catch(Exception ex) {
                                        ex.printStackTrace();
                                    }
                                }
                                break;
                            case Calendar.SUNDAY:
                                break;
                        }
                    }
//                }
                LOGGER.info("��̼߳�����");
                Thread.sleep(1000 * 60 * 60);
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
