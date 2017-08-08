package com.tencent.wpa;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;
import com.tencent.connect.auth.QQAuth;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.BaseApi.TempRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.tencent.utils.HttpUtils;
import java.util.List;

public class WPA extends BaseApi
{
  public WPA(Context paramContext, QQAuth paramQQAuth, QQToken paramQQToken)
  {
    super(paramContext, paramQQAuth, paramQQToken);
  }

  public WPA(Context paramContext, QQToken paramQQToken)
  {
    super(paramContext, paramQQToken);
  }

  public void getWPAUserOnlineState(String paramString, IUiListener paramIUiListener)
  {
    if (paramString == null)
      try
      {
        throw new Exception("uin null");
      }
      catch (Exception localException)
      {
        if (paramIUiListener != null)
          paramIUiListener.onError(new UiError(-5, "传入参数有误!", null));
        return;
      }
    if (paramString.length() < 5)
      throw new Exception("uin length < 5");
    while (true)
    {
      if (i < paramString.length())
      {
        if (!Character.isDigit(paramString.charAt(i)))
          throw new Exception("uin not digit");
        i++;
        continue;
      }
      String str = "http://webpresence.qq.com/getonline?Type=1&" + paramString + ":";
      BaseApi.TempRequestListener localTempRequestListener = new BaseApi.TempRequestListener(this, paramIUiListener);
      HttpUtils.requestAsync(this.mToken, this.mContext, str, null, "GET", localTempRequestListener);
      return;
      int i = 0;
    }
  }

  public int startWPAConversation(String paramString1, String paramString2)
  {
    Intent localIntent = new Intent("android.intent.action.VIEW");
    if (TextUtils.isEmpty(paramString1))
      return -1;
    if (paramString1.length() < 5)
      return -3;
    for (int i = 0; i < paramString1.length(); i++)
      if (!Character.isDigit(paramString1.charAt(i)))
        return -4;
    String str = "";
    if (!TextUtils.isEmpty(paramString2))
      str = Base64.encodeToString(paramString2.getBytes(), 2);
    localIntent.setData(Uri.parse(String.format("mqqwpa://im/chat?chat_type=wpa&uin=%1$s&version=1&src_type=app&attach_content=%2$s", new Object[] { paramString1, str })));
    PackageManager localPackageManager = this.mContext.getPackageManager();
    if (localPackageManager.queryIntentActivities(localIntent, 65536).size() > 0)
    {
      this.mContext.startActivity(localIntent);
      return 0;
    }
    localIntent.setData(Uri.parse("http://www.myapp.com/forward/a/45592?g_f=990935"));
    if (localPackageManager.queryIntentActivities(localIntent, 65536).size() > 0)
    {
      this.mContext.startActivity(localIntent);
      return 0;
    }
    return -2;
  }
}

/* Location:           D:\rm_src\classes_dex2jar\
 * Qualified Name:     com.tencent.wpa.WPA
 * JD-Core Version:    0.6.0
 */