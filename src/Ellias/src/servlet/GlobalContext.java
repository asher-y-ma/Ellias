package servlet;

import com.saille.ogzq.AutoReloginLoopThread;
import com.saille.ogzq.dailyLoop.ArenaThread;
import com.saille.baidu.bos.SynchronizeExcel;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.text.MessageFormat;
import java.lang.reflect.Method;
import java.util.Map;

public class GlobalContext implements ApplicationContextAware, InitializingBean {
    private static ApplicationContext springContext = null;
    private static final Logger LOGGER = Logger.getLogger(GlobalContext.class);
    private String[] threads;
    private Map<String, Integer> threadsInterval;
    private int defaultInterval = 60;

    public void setThreads(String[] threads) {
        this.threads = threads;
    }

    public void setThreadsInterval(Map<String, Integer> threadsInterval) {
        this.threadsInterval = threadsInterval;
    }

    public static ApplicationContext getSpringContext() {
        return springContext;
    }

    public void setApplicationContext(ApplicationContext context) throws BeansException {
        springContext = context;
    }

    public void afterPropertiesSet() throws Exception {
        if(threads != null && threads.length > 0) {
            for(String s : threads) {
                Class c = Class.forName(s);
                Method[] methods = c.getDeclaredMethods();
                boolean found = false;
                for(Method m : methods) {
                    if(m.getName().equals("getInstance")) {
                        found = true;
                        int interval = defaultInterval;
                        if(threadsInterval.get(s) != null) {
                            interval = threadsInterval.get(s).intValue();
                        }
                        LOGGER.info("���������߳� ��" + s);
                        Thread t = (Thread) m.invoke(null, new Object[]{interval});
                        t.start();
                    }
                }
                if(!found) {
                    LOGGER.error(s + "û���ҵ�getInstance()����!");
                }
            }
        }
        LOGGER.info("�����߳��������");
//        SynchronizeExcel synchronizeExcel = SynchronizeExcel.getInstance();
//        synchronizeExcel.start();
//        Thread t = new AutoReloginLoopThread();
//        t.start();
    }

    public static Object getContextBean(String name) {
        ApplicationContext springContext = GlobalContext.getSpringContext();
        return springContext.getBean(name);
    }

    public static Object getContextBean(Class cls) {
       ApplicationContext springContext = getSpringContext();
       String[] ss = springContext.getBeanNamesForType(cls);
       if(ss!=null && ss.length>0){
           if(ss.length==1){
               return springContext.getBean(ss[0]);
           }else{
               LOGGER.error( MessageFormat.format("Duplicated definition: {0}",cls));
           }
       }else{
           LOGGER.error(MessageFormat.format("No class definition found: {0}",cls));
       }

        return null;
   }
}