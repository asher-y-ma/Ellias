package com.tencent.feedback.common;

import android.content.Context;

public final class h
{
  private static h f;
  private String a = "";
  private String b = "";
  private String c = "";
  private String d = "";
  private String e = "";

  private h(Context paramContext)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    d.a(paramContext);
    this.b = d.b(paramContext);
    d.a(paramContext);
    this.c = d.e(paramContext);
    d.a(paramContext);
    this.d = d.c(paramContext);
    d.a(paramContext);
    this.e = d.d(paramContext);
    try
    {
      String str = a.b(paramContext, "QIMEI_DENGTA", "");
      if ((str != null) && (!"".equals(str)))
        this.a = str;
      return;
    }
    catch (Exception localException)
    {
    }
  }

  public static h a(Context paramContext)
  {
    if (f == null)
      f = new h(paramContext);
    return f;
  }

  public final String a()
  {
    return this.a;
  }

  public final void a(String paramString)
  {
    this.a = paramString;
  }

  public final String b()
  {
    return this.b;
  }

  public final String c()
  {
    return this.c;
  }

  public final String d()
  {
    return this.d;
  }

  public final String e()
  {
    return this.e;
  }
}

/* Location:           D:\rm_src\classes_dex2jar\
 * Qualified Name:     com.tencent.feedback.common.h
 * JD-Core Version:    0.6.0
 */