package com.saille.rxqq;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;

class GuaCPlayer extends Thread {
    private final Logger LOGGER = Logger.getLogger(getClass());
    private String id;
    private int big;

    public GuaCPlayer(String id, int big) {
        this.id = id;
        this.big = big;
    }

    private void guaCPlayer() {
        try {
            int beibaoSize;
            String ret;
            JSONArray tempItems;
            boolean hasGetBack;
            RxqqInstance.yizhiCount.clear();
            String url;
            while(true) {
                String beibao = RxqqUtils.execute(this.id, "http://s3.qiuqiu.2010.sina.com.cn/Package.do?action=getpackage");
                JSONObject json = new JSONObject(beibao);
                if(!json.has("Items")) {
                    this.LOGGER.warn(this.id + ", key: Itemsû�ж�Ӧ�����ݣ�");
                    Iterator it = json.keys();
                    while(it.hasNext()) {
                        String key = (String) it.next();
                        this.LOGGER.info(key + ": " + json.getString(key));
                    }
                }

                JSONArray items = json.getJSONArray("Items");
                int jingcaicount = 0;
                for(int i = 0; i < items.length(); i++) {
                    JSONObject item = items.getJSONObject(i);
                    if((item.getInt("Type") == 4) && ((item.getInt("CardLevel") == 2) || (item.getInt("CardLevel") == 1)) && (item.getInt("IsBinding") == 0)) {
                        int cardLevel = item.getInt("CardLevel");
                        int strengthen = item.getInt("Strengthen");
                        if((this.big == 1) && (strengthen < 3) && (cardLevel == 2)) {
                            if(jingcaicount < 2) {
                                jingcaicount++;
                                this.LOGGER.info(this.id + "��ŵ�C��" + item.getString("Name") + "�ȼ�Ϊ1/2�ȴ����£�����");
                            } else {
                                this.LOGGER.info("�Ѿ��п����о��£���Ҫ����");
                            }
                        } else {
                            if((cardLevel == 2) && (strengthen < 5)) {
                                strengthen = 5;
                            }
                            Integer[] price;
                            if(cardLevel == 1) {
                                price = RxqqInstance.cprice.get("!" + item.getString("Name").trim() + "_" + strengthen);
                            } else {
                                price = RxqqInstance.cprice.get(item.getString("Name").trim() + "_" + strengthen);
                            }
                            if(price == null) {
                                this.LOGGER.info("û���ҵ���Ա�۸�" + item.getString("Name") + "������");
                            } else {
                                String itemId = item.getString("ItemId");
                                url = String.format("http://s3.qiuqiu.2010.sina.com.cn/Auction.do?action=StartAuction&CurrencyType=2&StartPrice=%s&EndPrice=%s&TimeType=2&ItemId=%s", new Object[]{price[0], price[1], itemId});
                                RxqqUtils.execute(this.id, url);
                                this.LOGGER.info(this.id + "��Ա: " + item.getString("Name") + "�Ѿ��ҳ����۸�" + price[0] + "-" + price[1]);
                            }
                        }
                    } else {
                        if(this.big == 1) {
                            continue;
                        }
                        if(this.big == 2) {
                            if((item.getInt("Type") == 4) && (item.getInt("IsBinding") == 0)) {
                                int level = item.getInt("CardLevel");
                                if((level >= 4) && (level <= 6)) {
                                    boolean found = false;
                                    for(String[] s : RxqqInstance.yizhiqiuyuan) {
                                        if(Integer.parseInt(s[1]) == item.getInt("Code")) {
                                            found = true;
                                            Integer count = RxqqInstance.yizhiCount.get(item.getInt("Code"));
                                            if(count == null) {
                                                RxqqInstance.yizhiCount.put(item.getInt("Code"), 1);
                                                url = "http://s3.qiuqiu.2010.sina.com.cn/Auction.do?action=StartAuction&CurrencyType=2&EndPrice=50&StartPrice=50&TimeType=2&ItemId=" + item.getString("ItemId");
                                                RxqqUtils.execute(this.id, url);
                                                this.LOGGER.info("��Ա��" + item.getString("Name") + "(" + level + ")�Ѿ��ҳ�");
                                                break;
                                            }
                                            if(count.intValue() >= 2) {
                                                this.LOGGER.info("��Ա��" + item.getString("Name") + "(" + level + ")��������2�ţ��ֽ�");
                                                RxqqUtils.execute(this.id, "http://s3.qiuqiu.2010.sina.com.cn/Pandora.do?action=postDecompound&itemId=" + item.getString("ItemId"));
                                                break;
                                            }
                                            RxqqInstance.yizhiCount.put(item.getInt("Code"), count.intValue() + 1);
                                            url = "http://s3.qiuqiu.2010.sina.com.cn/Auction.do?action=StartAuction&CurrencyType=2&EndPrice=50&StartPrice=50&TimeType=2&ItemId=" + item.getString("ItemId");
                                            RxqqUtils.execute(this.id, url);
                                            this.LOGGER.info("��Ա��" + item.getString("Name") + "(" + level + ")�Ѿ��ҳ�");

                                            break;
                                        }

                                    }

                                    if((!found) && (item.getString("Name").trim().equals("�������")) && (item.getInt("CardLevel") == 4)) {
                                        url = "http://s3.qiuqiu.2010.sina.com.cn/Auction.do?action=StartAuction&CurrencyType=2&EndPrice=50&StartPrice=50&TimeType=2&ItemId=" + item.getString("ItemId");
                                        RxqqUtils.execute(this.id, url);
                                        this.LOGGER.info("��������Ѿ��ҳ�");
                                        continue;
                                    }
                                }
                            } else {
                                if(item.optInt("IsBinding", 0) != 1) {
                                    continue;
                                }
                                this.LOGGER.info("�󶨿������зֽ⣺" + item.getString("Name"));
                                RxqqUtils.execute(this.id, "http://s3.qiuqiu.2010.sina.com.cn/Pandora.do?action=postDecompound&itemId=" + item.getString("ItemId"));
                            }
                        }

                    }

                }

                beibao = RxqqUtils.execute(this.id, "http://s3.qiuqiu.2010.sina.com.cn/Package.do?action=getpackage");
                json = new JSONObject(beibao);
                items = json.getJSONArray("Items");
                beibaoSize = json.getInt("ItemLimit");
                if(items.length() >= beibaoSize) {
                    this.LOGGER.info(this.id + "��Ʒ����������������(" + beibaoSize + ")��ֹͣ�ҿ�");
                    return;
                }
                url = "http://s3.qiuqiu.2010.sina.com.cn/Auction.do?action=CheckTempPackage";
                ret = RxqqUtils.execute(this.id, url);
                json = new JSONObject(ret);
                tempItems = json.getJSONArray("Info");
                if(tempItems.length() == 0) {
                    this.LOGGER.info(this.id + "��ʱ��������Ϊ0���ҿ����");
                }
                hasGetBack = false;
                for(int i = 0; i < tempItems.length(); i++) {
                    JSONObject player = tempItems.getJSONObject(i);
                    if((player.has("LeftTime")) && (player.getInt("ItemType") == 4) && (player.getInt("IsBinding") == 0)) {
                        if(this.big == 2) {
                            url = "http://s3.qiuqiu.2010.sina.com.cn/Auction.do?action=GetItem&ItemId=" + player.getInt("Id");
                            RxqqUtils.execute(this.id, url);
                            this.LOGGER.info(this.id + "��Աȡ���ɹ�: ");
                            hasGetBack = true;
                            break;
                        }

                        url = "http://s3.qiuqiu.2010.sina.com.cn/Package.do?ver=1.6&action=Description&ItemCode=" + player.getInt("ItemCode");
                        JSONObject desc = new JSONObject(RxqqUtils.execute(this.id, url));
                        int level = desc.getInt("CardLevel");
                        if((level == 2) || (level == 1)) {
                            url = "http://s3.qiuqiu.2010.sina.com.cn/Auction.do?action=GetItem&ItemId=" + player.getInt("Id");
                            RxqqUtils.execute(this.id, url);
                            this.LOGGER.info(this.id + "��Աȡ���ɹ�: " + desc.get("Name"));
                            hasGetBack = true;
                            break;
                        }
                    } else {
                        if(this.big == 1) {
                            continue;
                        }
                        if((this.big != 2) || ((player.getInt("ItemType") != 1) && (player.getInt("ItemType") != 2))) {
                            continue;
                        }
                        url = "http://s3.qiuqiu.2010.sina.com.cn/Auction.do?action=GetItem&ItemId=" + player.getInt("Id");
                        RxqqUtils.execute(this.id, url);
                        hasGetBack = true;
                        this.LOGGER.info("ȡ��װ��");
                        break;
                    }

                }

                if(!hasGetBack) {
                    this.LOGGER.info(this.id + "��Ա�ҿ�����");
                    break;
                }
            }
        } catch(Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            this.LOGGER.error("id: " + this.id + "," + sw.toString());
        }
    }

    public void run() {
        guaCPlayer();
    }
}