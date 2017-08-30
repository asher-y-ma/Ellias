import com.GlobalConstant;

import java.net.Socket;
import java.text.SimpleDateFormat;
import java.io.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class ProxyTask implements Runnable {
    private Socket socketIn;
    private Socket socketOut;

    private long totalUpload = 0l;//�ܼ����б�����
    private long totalDownload = 0l;//�ܼ����б�����

    public ProxyTask(Socket socket) {
        this.socketIn = socket;
    }

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    /**
     * �����ӵ�����ķ�����
     */
    private static final String AUTHORED = "HTTP/1.1 200 Connection established\r\n\r\n";
    /** �������½ʧ��(��Ӧ����ʱ���漰��½����) */
    //private static final String UNAUTHORED="HTTP/1.1 407 Unauthorized\r\n\r\n";
    /**
     * �ڲ�����
     */
    private static final String SERVERERROR = "HTTP/1.1 500 Connection FAILED\r\n\r\n";

    @Override
    public void run() {

        StringBuilder builder = new StringBuilder();
        try {
            builder.append("\r\n").append("Request Time  ��" + sdf.format(new Date()));

            InputStream isIn = socketIn.getInputStream();
            OutputStream osIn = socketIn.getOutputStream();
            //�ӿͻ����������ж�ȡͷ����������������Ͷ˿�
            HttpHeader header = HttpHeader.readHeader(isIn);

            //���������־��Ϣ
            builder.append("\r\n").append("From    Host  ��" + socketIn.getInetAddress());
            builder.append("\r\n").append("From    Port  ��" + socketIn.getPort());
            builder.append("\r\n").append("Proxy   Method��" + header.getMethod());
            builder.append("\r\n").append("Request Host  ��" + header.getHost());
            builder.append("\r\n").append("Request Port  ��" + header.getPort());

            //���û���������������ַ�Ͷ˿ڣ��򷵻ش�����Ϣ
            if(header.getHost() == null || header.getPort() == null) {
                osIn.write(SERVERERROR.getBytes());
                osIn.flush();
                return;
            }

            // ���������Ͷ˿�
            socketOut = new Socket(header.getHost(), Integer.parseInt(header.getPort()));
            socketOut.setKeepAlive(true);
            InputStream isOut = socketOut.getInputStream();
            OutputStream osOut = socketOut.getOutputStream();
            //�¿�һ���߳̽����ص�����ת�����ͻ���,���л�����⣬��û������ԭ��
            Thread ot = new DataSendThread(isOut, osIn);
            ot.start();
            if(header.getMethod().equals(HttpHeader.METHOD_CONNECT)) {
                // ������ͨ�źŷ��ظ�����ҳ��
                osIn.write(AUTHORED.getBytes());
                osIn.flush();
            } else {
                //http������Ҫ������ͷ��Ҳת����ȥ
                byte[] headerData = header.toString().getBytes();
                totalUpload += headerData.length;
                osOut.write(headerData);
                osOut.flush();
            }
            //��ȡ�ͻ����������������ת����������
            readForwardDate(isIn, osOut);
            //�ȴ���ͻ���ת�����߳̽���
            ot.join();
        } catch(Exception e) {
            e.printStackTrace();
            if(!socketIn.isOutputShutdown()) {
                //��������Է��ش���״̬�Ļ��������ڲ�����
                try {
                    socketIn.getOutputStream().write(SERVERERROR.getBytes());
                } catch(IOException e1) {
                }
            }
        } finally {
            try {
                if(socketIn != null) {
                    socketIn.close();
                }
            } catch(IOException e) {
            }
            if(socketOut != null) {
                try {
                    socketOut.close();
                } catch(IOException e) {
                }
            }
            //��¼��������������������ʱ�䲢��ӡ
            builder.append("\r\n").append("Up    Bytes  ��" + totalUpload);
            builder.append("\r\n").append("Down  Bytes  ��" + totalDownload);
            builder.append("\r\n").append("Closed Time  ��" + sdf.format(new Date()));
            builder.append("\r\n");
            if(builder.toString().indexOf("sdo") >= 0) {
                logRequestMsg(builder.toString());
            }
        }
    }

    /**
     * ������߳̾�������־������
     *
     * @param msg
     */
    private synchronized void logRequestMsg(String msg) {
        System.out.println(msg);
    }

    /**
     * ��ȡ�ͻ��˷��͹��������ݣ����͸���������
     *
     * @param isIn
     * @param osOut
     */
    private void readForwardDate(InputStream isIn, OutputStream osOut) {
        byte[] buffer = new byte[4096];
        try {
            int len;
            while((len = isIn.read(buffer)) != -1) {
                if(len > 0) {
                    System.out.println(new String(buffer));
                    osOut.write(buffer, 0, len);
                    osOut.flush();
                }
                totalUpload += len;
                if(socketIn.isClosed() || socketOut.isClosed()) {
                    break;
                }
            }
        } catch(Exception e) {
            try {
                socketOut.close();// ���Թر�Զ�̷��������ӣ��ж�ת���̵߳Ķ�����״̬
            } catch(IOException e1) {
            }
        }
    }

    /**
     * ���������˷��ص�����ת�����ͻ���
     */
    class DataSendThread extends Thread {
        private InputStream isOut;
        private OutputStream osIn;

        DataSendThread(InputStream isOut, OutputStream osIn) {
            this.isOut = isOut;
            this.osIn = osIn;
        }

        @Override
        public void run() {
            byte[] buffer = new byte[4096];
            try {
                int len;
                while((len = isOut.read(buffer)) != -1) {
                    if(len > 0) {
                        System.out.println(new String(buffer));
                        File f = new File(GlobalConstant.DISKPATH + "temp\\asdf.zip");
                        if(!f.exists()) {
                            f.createNewFile();
                        }
                        FileOutputStream fos = new FileOutputStream(f, true);
                        fos.write(buffer, 0, len);
                        fos.close();
                        // logData(buffer, 0, len);
                        osIn.write(buffer, 0, len);
                        osIn.flush();
                        totalDownload += len;
                    }
                    if(socketIn.isOutputShutdown() || socketOut.isClosed()) {
                        break;
                    }
                }
            } catch(Exception e) {
            }
        }
    }

}

final class HttpHeader {

    private List<String> header = new ArrayList<String>();

    private String method;
    private String host;
    private String port;

    public static final int MAXLINESIZE = 4096;

    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_CONNECT = "CONNECT";

    private HttpHeader() {
    }

    /**
     * ���������ж�ȡ����ͷ����Ϣ�������ڷ���������֮���κ����ݶ�ȡ֮ǰ
     *
     * @param in
     * @return
     * @throws IOException
     */
    public static final HttpHeader readHeader(InputStream in) throws IOException {
        HttpHeader header = new HttpHeader();
        StringBuilder sb = new StringBuilder();
        //�ȶ�������Э������
        char c = 0;
        while((c = (char) in.read()) != '\n') {
            sb.append(c);
            if(sb.length() == MAXLINESIZE) {//�����ܹ�����ͷ���ֶ�
                break;
            }
        }
        //����ʶ�������ʽ����������������˳�
        if(header.addHeaderMethod(sb.toString()) != null) {
            do {
                sb = new StringBuilder();
                while((c = (char) in.read()) != '\n') {
                    sb.append(c);
                    if(sb.length() == MAXLINESIZE) {//�����ܹ�����ͷ���ֶ�
                        break;
                    }
                }
                if(sb.length() > 1 && header.notTooLong()) {//���ͷ��������Ϣ���࣬����ʣ�µĲ���
                    header.addHeaderString(sb.substring(0, sb.length() - 1));
                } else {
                    break;
                }
            } while(true);
        }

        return header;
    }

    /**
     * @param str
     */
    private void addHeaderString(String str) {
        str = str.replaceAll("\r", "");
        header.add(str);
        if(str.startsWith("Host")) {//���������Ͷ˿�
            String[] hosts = str.split(":");
            host = hosts[1].trim();
            if(method.endsWith(METHOD_CONNECT)) {
                port = hosts.length == 3 ? hosts[2] : "443";//httpsĬ�϶˿�Ϊ443
            } else if(method.endsWith(METHOD_GET) || method.endsWith(METHOD_POST)) {
                port = hosts.length == 3 ? hosts[2] : "80";//httpĬ�϶˿�Ϊ80
            }
        }
    }

    /**
     * �ж�����ʽ
     *
     * @param str
     * @return
     */
    private String addHeaderMethod(String str) {
        str = str.replaceAll("\r", "");
        header.add(str);
        if(str.startsWith(METHOD_CONNECT)) {//https�����������
            method = METHOD_CONNECT;
        } else if(str.startsWith(METHOD_GET)) {//http GET����
            method = METHOD_GET;
        } else if(str.startsWith(METHOD_POST)) {//http POST����
            method = METHOD_POST;
        }
        return method;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(String str : header) {
            sb.append(str).append("\r\n");
        }
        sb.append("\r\n");
        return sb.toString();
    }

    public boolean notTooLong() {
        return header.size() <= 16;
    }


    public List<String> getHeader() {
        return header;
    }


    public void setHeader(List<String> header) {
        this.header = header;
    }


    public String getMethod() {
        return method;
    }


    public void setMethod(String method) {
        this.method = method;
    }

    public String getHost() {
        return host;
    }


    public void setHost(String host) {
        this.host = host;
    }


    public String getPort() {
        return port;
    }


    public void setPort(String port) {
        this.port = port;
    }

}