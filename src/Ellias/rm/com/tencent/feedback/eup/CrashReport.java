package com.tencent.feedback.eup;

import android.content.Context;
import com.tencent.feedback.common.f;
import com.tencent.feedback.eup.jni.NativeExceptionUpload;
import com.tencent.feedback.upload.UploadHandleListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

public class CrashReport
{
  public static boolean addPlugin(Context paramContext, String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1 == null) || (paramString2 == null))
      return false;
    com.tencent.feedback.common.c localc = com.tencent.feedback.common.c.p();
    if (localc == null)
    {
      com.tencent.feedback.common.c.a(paramContext, "10000", "unknown");
      localc = com.tencent.feedback.common.c.p();
    }
    if (paramString3 == null)
      paramString3 = "";
    return localc.a(paramString1, paramString2, paramString3);
  }

  public static void clearSDKTotalConsume(Context paramContext)
  {
    f.c(paramContext);
  }

  public static int countExceptionDatas(Context paramContext)
  {
    if (!e.m())
      return -1;
    e locale = e.k();
    if (locale == null)
    {
      com.tencent.feedback.common.e.c("rqdp{  instance == null}", new Object[0]);
      return -1;
    }
    return locale.g();
  }

  public static int countStoredRecord(Context paramContext)
  {
    return c.a(paramContext);
  }

  public static boolean doUploadExceptionDatas()
  {
    return e.l();
  }

  public static CrashStrategyBean getCrashRuntimeStrategy()
  {
    try
    {
      CrashStrategyBean localCrashStrategyBean = e.k().q();
      return localCrashStrategyBean;
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
    return null;
  }

  public static long getSDKTotalConsume(Context paramContext, boolean paramBoolean)
  {
    com.tencent.feedback.common.a.e locale = f.b(paramContext);
    if (locale != null)
    {
      if (paramBoolean)
        return locale.e;
      return locale.d + locale.e;
    }
    return -1L;
  }

  public static boolean handleCatchException(Thread paramThread, Throwable paramThrowable, String paramString, byte[] paramArrayOfByte)
  {
    return e.a(paramThread, paramThrowable, paramString, paramArrayOfByte);
  }

  public static void initCrashReport(Context paramContext)
  {
    initCrashReport(paramContext, null, null, true, null);
  }

  public static void initCrashReport(Context paramContext, CrashHandleListener paramCrashHandleListener, UploadHandleListener paramUploadHandleListener, boolean paramBoolean, CrashStrategyBean paramCrashStrategyBean)
  {
    e.a(paramContext, "10000", false, e.a(paramContext, paramBoolean), paramUploadHandleListener, paramCrashHandleListener, paramCrashStrategyBean);
  }

  public static void initCrashReport(Context paramContext, CrashHandleListener paramCrashHandleListener, UploadHandleListener paramUploadHandleListener, boolean paramBoolean, CrashStrategyBean paramCrashStrategyBean, long paramLong)
  {
    if (paramLong > 0L)
    {
      if (paramLong > 10000L)
        paramLong = 10000L;
      com.tencent.feedback.common.b.d.a(paramLong);
    }
    e.a(paramContext, "10000", false, e.a(paramContext, paramBoolean), paramUploadHandleListener, paramCrashHandleListener, paramCrashStrategyBean);
  }

  public static void initCrashReport(Context paramContext, boolean paramBoolean)
  {
    e.a(paramContext, "10000", false, e.a(paramContext, paramBoolean), null, null, null);
  }

  public static void initNativeCrashReport(Context paramContext, String paramString, boolean paramBoolean)
  {
    initNativeCrashReport(paramContext, paramString, paramBoolean, null, null);
  }

  public static void initNativeCrashReport(Context paramContext, String paramString, boolean paramBoolean, List<File> paramList)
  {
    initNativeCrashReport(paramContext, paramString, paramBoolean, paramList, null);
  }

  public static void initNativeCrashReport(Context paramContext, String paramString, boolean paramBoolean, List<File> paramList, File paramFile)
  {
    if (paramFile != null)
    {
      if (!NativeExceptionUpload.loadRQDNativeLib(paramFile))
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = paramFile.getAbsoluteFile();
        com.tencent.feedback.common.e.d("load lib fail %s close native return!", arrayOfObject);
        return;
      }
      com.tencent.feedback.common.e.a("load lib sucess from specify!", new Object[0]);
    }
    label297: label302: label306: 
    while (true)
    {
      NativeExceptionUpload.setmHandler(com.tencent.feedback.eup.jni.b.a(paramContext));
      if (paramFile != null)
      {
        if (paramList == null)
          paramList = new ArrayList();
        paramList.add(paramFile);
      }
      label95: boolean bool;
      if ((paramContext == null) || (paramString == null))
      {
        com.tencent.feedback.common.e.c("rqdp{  nreg param!}", new Object[0]);
        com.tencent.feedback.common.d.a(paramContext);
        String str1 = com.tencent.feedback.common.d.d();
        com.tencent.feedback.common.d.a(paramContext);
        NativeExceptionUpload.registEUP(paramString, str1, Integer.parseInt(com.tencent.feedback.common.d.c()));
        NativeExceptionUpload.enableNativeEUP(true);
        if (!paramBoolean)
          break label297;
        NativeExceptionUpload.setNativeLogMode(3);
        return;
        if (paramList == null)
          break label302;
        bool = NativeExceptionUpload.loadRQDNativeLib(paramList);
        if (bool)
          com.tencent.feedback.common.e.a("load lib sucess from list!", new Object[0]);
      }
      while (true)
      {
        if (bool)
          break label306;
        if (!NativeExceptionUpload.loadRQDNativeLib())
        {
          com.tencent.feedback.common.e.d("load lib fail default close native return!", new Object[0]);
          return;
        }
        com.tencent.feedback.common.e.a("load lib sucess default!", new Object[0]);
        break;
        CrashStrategyBean localCrashStrategyBean = e.k().q();
        long l = com.tencent.feedback.anr.a.f() - 1000 * (3600 * (24 * localCrashStrategyBean.getRecordOverDays()));
        int i = localCrashStrategyBean.getMaxStoredNum();
        com.tencent.feedback.common.b.b().a(new com.tencent.feedback.eup.jni.c(paramString, l, i));
        String str2 = "/data/data/" + paramContext.getPackageName() + "/lib/";
        com.tencent.feedback.common.b.b().a(new com.tencent.feedback.eup.jni.a(paramContext, str2, paramList));
        break label95;
        NativeExceptionUpload.setNativeLogMode(5);
        return;
        bool = false;
      }
    }
  }

  public static void removePlugin(Context paramContext, String paramString)
  {
    if (paramString != null)
    {
      com.tencent.feedback.common.c localc = com.tencent.feedback.common.c.p();
      if (localc == null)
      {
        com.tencent.feedback.common.c.a(paramContext, "10000", "unknown");
        localc = com.tencent.feedback.common.c.p();
      }
      localc.d(paramString);
    }
  }

  public static void setCrashReportAble(boolean paramBoolean)
  {
    e locale = e.k();
    if (locale != null)
      locale.a(paramBoolean);
  }

  public static void setLogAble(boolean paramBoolean1, boolean paramBoolean2)
  {
    com.tencent.feedback.common.e.a = paramBoolean1;
    com.tencent.feedback.common.e.b = paramBoolean2;
  }

  public static void setNativeCrashReportAble(boolean paramBoolean)
  {
    NativeExceptionUpload.enableNativeEUP(paramBoolean);
  }

  public static void setThreadPoolService(ScheduledExecutorService paramScheduledExecutorService)
  {
    com.tencent.feedback.common.b.a(com.tencent.feedback.common.b.a(paramScheduledExecutorService));
  }

  public static void setUserId(Context paramContext, String paramString)
  {
    com.tencent.feedback.common.c localc = com.tencent.feedback.common.c.p();
    if (localc == null)
    {
      com.tencent.feedback.common.c.a(paramContext, paramString, "unknown");
      com.tencent.feedback.common.c.p();
      return;
    }
    localc.a(paramString);
  }
}

/* Location:           D:\rm_src\classes_dex2jar\
 * Qualified Name:     com.tencent.feedback.eup.CrashReport
 * JD-Core Version:    0.6.0
 */