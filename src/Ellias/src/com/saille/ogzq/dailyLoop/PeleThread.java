package com.saille.ogzq.dailyLoop;

import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.*;

import com.saille.ogzq.ConfigUtils;
import com.saille.ogzq.IDUtils;
import com.saille.ogzq.OperationUtils;

/**
 * Created by IntelliJ IDEA.
 * User: Ellias
 * Date: 2016-5-26
 * Time: 17:50:05
 * To change this template use File | Settings | File Templates.
 */
public class PeleThread extends ParentThread {
    private final static Logger LOGGER = Logger.getLogger(PeleThread.class);
    private static PeleThread instance;

    private PeleThread() {
        threadName = "��������";
        setThreadname(threadName);
    }

    public synchronized static PeleThread getInstance() {
        if(instance == null) {
            instance = new PeleThread();
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
                    targetTime.clear();
                    lastDate = today;
                }
                if(now.compareTo(ConfigUtils.BEGINTIME) <= 0) {
                    LOGGER.info("��ǰʱ�䣺" + now + "��С���趨��ʱ�䣺" + ConfigUtils.BEGINTIME + "���ȴ�300��");
                    Thread.sleep(1000 * 60 * 5);
                    continue;
                }

                List<String> ids = IDUtils.GETIDS();
                LOGGER.info("��������ѭ��" + ids.size() + "����");
                for(String id : ids) {
                    if(targetTime.containsKey(id) && targetTime.get(id).longValue() > sysTime) {
                        continue;
                    }
                    String peleType = OperationUtils.doPele(id);
                    if("-1".equals(peleType)) { //δ֪״̬���ӳ�1����
                        targetTime.put(id, sysTime + 1000 * 60);
                    } else if("0".equals(peleType)) { //ȫ�������ˣ��ӳ�1Сʱ
                        targetTime.put(id, sysTime + 1000 * 60 * 60);
                    } else if("1".equals(peleType)) { //������
                        targetTime.put(id, sysTime + 1000 * 60);
                    } else if("2".equals(peleType)) { //����״̬���������
                        targetTime.put(id, sysTime + 1000 * 60 * 5);
                    }
                }

                long minvalue = getWaitTime(sysTime);
                LOGGER.info("��������ѭ���������ȴ�" + minvalue / 1000 + "��");
                Thread.sleep(minvalue);
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
