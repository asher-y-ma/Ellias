package com.tencent.a.a;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Message;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class a extends g
  implements Handler.Callback
{
  private h a;
  private FileWriter b;
  private File c;
  private char[] d;
  private volatile e e;
  private volatile e f;
  private volatile e g;
  private volatile e h;
  private volatile boolean i = false;
  private HandlerThread j;
  private Handler k;

  public a(int paramInt, boolean paramBoolean, b paramb, h paramh)
  {
    super(paramInt, paramBoolean, paramb);
    a(paramh);
    this.e = new e();
    this.f = new e();
    this.g = this.e;
    this.h = this.f;
    this.d = new char[paramh.f()];
    paramh.b();
    h();
    this.j = new HandlerThread(paramh.c(), paramh.i());
    if (this.j != null)
      this.j.start();
    if ((this.j.isAlive()) && (this.j.getLooper() != null))
      this.k = new Handler(this.j.getLooper(), this);
    f();
  }

  public a(h paramh)
  {
    this(63, true, b.a, paramh);
  }

  private void f()
  {
    if (this.k != null)
      this.k.sendEmptyMessageDelayed(1024, c().g());
  }

  // ERROR //
  private void g()
  {
    // Byte code:
    //   0: invokestatic 117	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   3: aload_0
    //   4: getfield 72	com/tencent/a/a/a:j	Landroid/os/HandlerThread;
    //   7: if_acmpeq +4 -> 11
    //   10: return
    //   11: aload_0
    //   12: getfield 32	com/tencent/a/a/a:i	Z
    //   15: ifne -5 -> 10
    //   18: aload_0
    //   19: iconst_1
    //   20: putfield 32	com/tencent/a/a/a:i	Z
    //   23: aload_0
    //   24: invokespecial 119	com/tencent/a/a/a:j	()V
    //   27: aload_0
    //   28: getfield 48	com/tencent/a/a/a:h	Lcom/tencent/a/a/e;
    //   31: aload_0
    //   32: invokespecial 60	com/tencent/a/a/a:h	()Ljava/io/Writer;
    //   35: aload_0
    //   36: getfield 55	com/tencent/a/a/a:d	[C
    //   39: invokevirtual 122	com/tencent/a/a/e:a	(Ljava/io/Writer;[C)V
    //   42: aload_0
    //   43: getfield 48	com/tencent/a/a/a:h	Lcom/tencent/a/a/e;
    //   46: invokevirtual 123	com/tencent/a/a/e:b	()V
    //   49: aload_0
    //   50: iconst_0
    //   51: putfield 32	com/tencent/a/a/a:i	Z
    //   54: return
    //   55: astore_2
    //   56: aload_0
    //   57: getfield 48	com/tencent/a/a/a:h	Lcom/tencent/a/a/e;
    //   60: invokevirtual 123	com/tencent/a/a/e:b	()V
    //   63: goto -14 -> 49
    //   66: astore_1
    //   67: aload_0
    //   68: getfield 48	com/tencent/a/a/a:h	Lcom/tencent/a/a/e;
    //   71: invokevirtual 123	com/tencent/a/a/e:b	()V
    //   74: aload_1
    //   75: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   27	42	55	java/io/IOException
    //   27	42	66	finally
  }

  private Writer h()
  {
    File localFile = c().a();
    if ((localFile != null) && (!localFile.equals(this.c)))
    {
      this.c = localFile;
      i();
    }
    try
    {
      this.b = new FileWriter(this.c, true);
      return this.b;
    }
    catch (IOException localIOException)
    {
    }
    return null;
  }

  private void i()
  {
    try
    {
      if (this.b != null)
      {
        this.b.flush();
        this.b.close();
      }
      return;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }

  private void j()
  {
    monitorenter;
    try
    {
      if (this.g == this.e)
        this.g = this.f;
      for (this.h = this.e; ; this.h = this.f)
      {
        return;
        this.g = this.e;
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void a()
  {
    if (this.k.hasMessages(1024))
      this.k.removeMessages(1024);
  }

  protected void a(int paramInt, Thread paramThread, long paramLong, String paramString1, String paramString2, Throwable paramThrowable)
  {
    a(e().a(paramInt, paramThread, paramLong, paramString1, paramString2, paramThrowable));
  }

  public void a(h paramh)
  {
    this.a = paramh;
  }

  protected void a(String paramString)
  {
    this.g.a(paramString);
    if (this.g.a() >= c().f())
      a();
  }

  public void b()
  {
    i();
    this.j.quit();
  }

  public h c()
  {
    return this.a;
  }

  public boolean handleMessage(Message paramMessage)
  {
    switch (paramMessage.what)
    {
    default:
    case 1024:
    }
    while (true)
    {
      return true;
      g();
      f();
    }
  }
}

/* Location:           D:\rm_src\classes_dex2jar\
 * Qualified Name:     com.tencent.a.a.a
 * JD-Core Version:    0.6.0
 */