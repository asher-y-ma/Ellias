package com.saille.bwdl.util;

import com.saille.bwdl.ChengShi;
import com.saille.bwdl.WuJiang;
import com.saille.bwdl.service.BwdlService;
import com.saille.bwdl.service.BwdlSettingService;
import com.saille.bwdl.service.ServiceHelper;

public class CalcUtils {
    public static int[] KAIFAMONEY = {10, 12, 14, 16, 18, 20, 22, 24, 26};
    public static String[] TUDIDESC = {"���ѻ���֮����\n�Ա��ڸ����ɷ�\n��10����", "����ɭ��\n������ũ��ɷ�\n��12����", "��������\n�Կ���ũ��ɷ�\n��14����", "����ˮ��\n�����Թ�ȿɷ�\n��16����", "����ˮ��\n��ˮ�Թ�ȿɷ�\n��18����", "������ֲ����\n����֮����ɷ�\n��20����", "ʩ��������\nʹ֮���Ƹ��ӿɷ�\n��22����", "��������֮ũ��\n����ũ��ɷ�\n��24����", "����Ʒ��\n�������������Σ�\n��26����"};
    public static String[] CHANYEDESC = {"������֮����\n�Ľ�Ϊ������Σ�\n��10����", "Ϊ����������\n��Ӷ�Ͷ�����Σ�\n��12����", "�ڳ�֮����\n�����м���Σ�\n��14����", "ʹ���˻���\n������Ʒ������\n��16����", "�ھ��ɽ\n���ɿ�ʯ��Σ�\n��18����", "Ϊ���ɸ����ʯ\n�ھ��¿�ɽ��Σ�\n��20����", "�����¹�������\n��ϰ�¼�����Σ�\n��22����", "��������ʹ��\n֮������Σ�\n��24����", "�ټ�������ͬ��ҵ\n������������Σ�\n��26����"};
    public static String[] RENKOUDESC = {"�������е�·\n�Ա�����ͨ��Σ�\n��10����", "�����򽨵ø�Ϊ����\n�����ھ�ס��Σ�\n��12����", "�ؿ��·\n���ƽ�ͨ��Σ�\n��14����", "Ϊʹ��������ھ�ס\n����㳡��Σ�\n��16����", "�ڳ��пյ�\n���췿����Σ�\n��18����", "Ϊ�������\n���켯�᳡��Σ�\n��20����", "Ϊ��ס�������\n����������Σ�\n��22����", "Ϊά�������ΰ�\n���ù�Ա��Σ�\n��24����", "��ƽ����֮ɽ��\n���췿����Σ�\n��26����"};

    public static int getRandom(int a, int b) {
        int min = Math.min(a, b);
        int max = Math.max(a, b);
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    public static String kaifaTudi(int wujiangId, int chengshiId, int jin) {
        BwdlService service = ServiceHelper.getBwdlService();
        BwdlSettingService settingService = ServiceHelper.getSettingService();
        int maxTudi = settingService.getTudiMax(0);
        WuJiang wujiang = service.getWuJiang(wujiangId);
        ChengShi chengshi = service.getChengShi(chengshiId);
        if(chengshi.getTudi() >= maxTudi) {
            return "������Ϊ�˻�������";
        }
        if(chengshi.getJin() < jin) {
            return "���˳��ڲ����㹻��Ǯ\n���´�����ָ��";
        }
        int rand = getRandom(10, 17);
        int value = jin * wujiang.getZhi() / 100 * rand / 10;
        int result = chengshi.getTudi() + value;
        if(result > maxTudi) {
            value = maxTudi - chengshi.getTudi();
        }
        chengshi.setJin(chengshi.getJin() - jin);
        if(value > 31) {
            chengshi.setTongzhi(chengshi.getTongzhi() + 2);
        } else {
            chengshi.setTongzhi(chengshi.getTongzhi() + 1);
        }
        int tongzhiMax = settingService.getTongzhiMax(0);
        if(chengshi.getTongzhi() > tongzhiMax) {
            chengshi.setTongzhi(tongzhiMax);
        }
        chengshi.setTudi(chengshi.getTudi() + value);
        service.saveChengShi(chengshi);
        return "����ֵ����  " + value + "��";
    }

    public static int kaifaChanye(int wujiangId, int chengshiId, int jin) {
        BwdlService service = ServiceHelper.getBwdlService();
        BwdlSettingService settingService = ServiceHelper.getSettingService();
        int maxChanye = settingService.getChanyeMax(0);
        WuJiang wujiang = service.getWuJiang(wujiangId);
        ChengShi chengshi = service.getChengShi(chengshiId);
        if(chengshi.getChanye() >= maxChanye) {
            return 0;
        }
        int rand = getRandom(10, 17);
        int value = jin * wujiang.getZhi() * rand / 10;
        int result = chengshi.getChanye() + value;
        if(result > maxChanye) {
            value = maxChanye - chengshi.getChanye();
        }
        chengshi.setChanye(chengshi.getChanye() + value);
        service.saveChengShi(chengshi);
        return value;
    }

    public static int kaifaRenkou(int wujiangId, int chengshiId, int jin) {
        BwdlService service = ServiceHelper.getBwdlService();
        BwdlSettingService settingService = ServiceHelper.getSettingService();
        int maxRenkou = settingService.getRenkouMax(0);
        WuJiang wujiang = service.getWuJiang(wujiangId);
        ChengShi chengshi = service.getChengShi(chengshiId);
        if(chengshi.getRenkou() >= maxRenkou) {
            return 0;
        }
        int rand = getRandom(10, 17);
        int value = jin * wujiang.getZhi() * rand / 10 / 2;
        int result = chengshi.getRenkou() + value;
        if(result > maxRenkou) {
            value = maxRenkou - chengshi.getRenkou();
        }
        chengshi.setRenkou(chengshi.getRenkou() + value);
        service.saveChengShi(chengshi);
        return value;
    }

    public static int dushu(int wujiangId) {
        BwdlService service = ServiceHelper.getBwdlService();
        BwdlSettingService settingService = ServiceHelper.getSettingService();
        WuJiang wujiang = service.getWuJiang(wujiangId);
        int dushuMax = settingService.getDushuMax(0);
        int dushuMid = settingService.getDushuMid(0);
        int dushuMin = settingService.getDushuMin(0);
        if(wujiang.getZhi() >= dushuMax) {
            return 0;
        }
        int value = 0;
        if(wujiang.getZhi() > dushuMid) {
            value = getRandom(6, 10);
        } else if(wujiang.getZhi() > dushuMin) {
            value = getRandom(8, 12);
        } else {
            value = getRandom(5, 9);
        }
        wujiang.setZhi(wujiang.getZhi() + value);
        service.saveWuJiang(wujiang);
        return value;
    }

    public static String souji(int wujiangId, int chengShiId) {
        BwdlService service = ServiceHelper.getBwdlService();
        BwdlSettingService settingService = ServiceHelper.getSettingService();
        WuJiang wujiang = service.getWuJiang(wujiangId);
        ChengShi chengshi = service.getChengShi(chengShiId);

        String ret = null;

        int rand = getRandom(1, 8);
        int result = 0;
        if(wujiang.getDe() <= 40) {
            if(wujiang.getZhi() <= 40) {
                if(rand < 3) {
                    result = 1;
                } else {
                    result = 8;
                }
            } else if(wujiang.getZhi() <= 80) {
                if(rand < 3) {
                    result = 1;
                } else if(rand < 5) {
                    result = 2;
                } else {
                    result = 8;
                }
            } else if(rand < 2) {
                result = 2;
            } else if(rand < 4) {
                result = 3;
            } else if(rand < 7) {
                result = 4;
            } else {
                result = 8;
            }
        } else if(wujiang.getDe() <= 80) {
            if(wujiang.getZhi() <= 40) {
                if(rand < 4) {
                    result = 1;
                } else if(rand < 4) {
                    result = 2;
                } else if(rand < 6) {
                    result = 6;
                } else {
                    result = 8;
                }
            } else if(wujiang.getZhi() <= 80) {
                if(rand < 3) {
                    result = 2;
                } else if(rand < 6) {
                    result = 6;
                } else {
                    result = 8;
                }
            } else if(rand < 2) {
                result = 3;
            } else if(rand < 4) {
                result = 4;
            } else if(rand < 7) {
                result = 6;
            } else {
                result = 8;
            }

        } else if(wujiang.getZhi() <= 40) {
            if(rand < 4) {
                result = 1;
            } else if(rand < 5) {
                result = 5;
            } else if(rand < 7) {
                result = 6;
            } else {
                result = 8;
            }
        } else if(wujiang.getZhi() <= 80) {
            if(rand < 3) {
                result = 3;
            } else if(rand < 5) {
                result = 5;
            } else if(rand < 8) {
                result = 6;
            } else {
                result = 8;
            }
        } else if(rand < 3) {
            result = 3;
        } else if(rand < 5) {
            result = 4;
        } else if(rand < 8) {
            result = 5;
        } else {
            result = 8;
        }

        int value = 0;
        int maxJin = settingService.getJinMax(0);
        int maxMi = settingService.getMiMax(0);
        int maxBao = settingService.getBaoMax(0);
        switch(result) {
            case 1:
                value = getRandom(30, 70);
                chengshi.setJin(chengshi.getJin() + value);
                if(chengshi.getJin() > maxJin) {
                    chengshi.setJin(maxJin);
                }
                service.saveChengShi(chengshi);
                ret = "�ڳ����ռ��鱨֮ʱ\n�������������\nû�� ?����";
                break;
            case 2:
                ret = "������ż������\n��þ��� ?ʯ��";
                break;
            case 3:
                value = getRandom(80, 120);
                chengshi.setJin(chengshi.getJin() + value);
                if(chengshi.getJin() > maxJin) {
                    chengshi.setJin(maxJin);
                }
                service.saveChengShi(chengshi);
                ret = "������ż������\n��þ��� ?����";
                break;
            case 4:
                value = getRandom(1, 3);
                chengshi.setBao(chengshi.getBao() + value);
                if(chengshi.getJin() > maxBao) {
                    chengshi.setBao(maxBao);
                }
                service.saveChengShi(chengshi);
                ret = "���� ?������\n������ֿ���";
                break;
            case 5:
                ret = "����һλǰ;����֮�佫\n�Ƿ���Ϊ���£�";
                break;
            case 6:
                ret = "����һλǰ;����֮�佫\n���佫��Ҫ ?����\n�Ƿ���裿";
                break;
            case 8:
                ret = "�ǳ���ϧ\n����ֵ��һ��֮��";
            case 7:
        }
        return ret;
    }
}