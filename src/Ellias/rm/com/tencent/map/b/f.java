package com.tencent.map.b;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Looper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class f
{
  private Context a = null;
  private WifiManager b = null;
  private c c = null;
  private Handler d = null;
  private Runnable e = new g(this);
  private int f = 1;
  private a g = null;
  private b h = null;
  private boolean i = false;
  private byte[] j = new byte[0];

  public final void a()
  {
    synchronized (this.j)
    {
      if (!this.i)
        return;
      if ((this.a == null) || (this.c == null))
        return;
    }
    try
    {
      this.a.unregisterReceiver(this.c);
      label50: this.d.removeCallbacks(this.e);
      this.i = false;
      monitorexit;
      return;
    }
    catch (Exception localException)
    {
      break label50;
    }
  }

  public final void a(long paramLong)
  {
    if ((this.d != null) && (this.i))
    {
      this.d.removeCallbacks(this.e);
      this.d.postDelayed(this.e, paramLong);
    }
  }

  public final boolean a(Context paramContext, a parama, int paramInt)
  {
    synchronized (this.j)
    {
      if (this.i)
        return true;
      if ((paramContext == null) || (parama == null))
        return false;
      this.d = new Handler(Looper.getMainLooper());
      this.a = paramContext;
      this.g = parama;
      this.f = 1;
      try
      {
        this.b = ((WifiManager)this.a.getSystemService("wifi"));
        IntentFilter localIntentFilter = new IntentFilter();
        this.c = new c();
        if (this.b != null)
        {
          c localc = this.c;
          if (localc != null);
        }
        else
        {
          return false;
        }
        localIntentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        localIntentFilter.addAction("android.net.wifi.SCAN_RESULTS");
        this.a.registerReceiver(this.c, localIntentFilter);
        a(0L);
        this.i = true;
        return this.i;
      }
      catch (Exception localException)
      {
        return false;
      }
    }
  }

  public final boolean b()
  {
    return this.i;
  }

  public final boolean c()
  {
    if ((this.a == null) || (this.b == null))
      return false;
    return this.b.isWifiEnabled();
  }

  public static abstract interface a
  {
    public abstract void a(f.b paramb);

    public abstract void b(int paramInt);
  }

  public final class b
    implements Cloneable
  {
    private List<ScanResult> a = null;

    public b(long arg2, int arg4)
    {
      if (??? != null)
      {
        this.a = new ArrayList();
        Iterator localIterator = ???.iterator();
        while (localIterator.hasNext())
        {
          ScanResult localScanResult = (ScanResult)localIterator.next();
          this.a.add(localScanResult);
        }
      }
    }

    public final List<ScanResult> a()
    {
      return this.a;
    }

    public final Object clone()
    {
      try
      {
        localb = (b)super.clone();
        if (this.a != null)
        {
          localb.a = new ArrayList();
          localb.a.addAll(this.a);
        }
        return localb;
      }
      catch (Exception localException)
      {
        while (true)
          b localb = null;
      }
    }
  }

  public final class c extends BroadcastReceiver
  {
    private int a = 4;
    private List<ScanResult> b = null;
    private boolean c = false;

    public c()
    {
    }

    private void a(List<ScanResult> paramList)
    {
      if (paramList == null)
        return;
      if (this.c)
      {
        if (this.b == null)
          this.b = new ArrayList();
        int i = this.b.size();
        Iterator localIterator2 = paramList.iterator();
        label49: ScanResult localScanResult2;
        if (localIterator2.hasNext())
          localScanResult2 = (ScanResult)localIterator2.next();
        for (int j = 0; ; j++)
        {
          if (j < i)
          {
            if (!((ScanResult)this.b.get(j)).BSSID.equals(localScanResult2.BSSID))
              continue;
            this.b.remove(j);
          }
          this.b.add(localScanResult2);
          break label49;
          break;
        }
      }
      if (this.b == null)
        this.b = new ArrayList();
      while (true)
      {
        Iterator localIterator1 = paramList.iterator();
        while (localIterator1.hasNext())
        {
          ScanResult localScanResult1 = (ScanResult)localIterator1.next();
          this.b.add(localScanResult1);
        }
        break;
        this.b.clear();
      }
    }

    public final void onReceive(Context paramContext, Intent paramIntent)
    {
      if (paramIntent.getAction().equals("android.net.wifi.WIFI_STATE_CHANGED"))
      {
        this.a = paramIntent.getIntExtra("wifi_state", 4);
        if (f.b(f.this) != null)
          f.b(f.this).b(this.a);
      }
      List localList;
      if ((paramIntent.getAction().equals("android.net.wifi.SCAN_RESULTS")) || (paramIntent.getAction().equals("android.net.wifi.WIFI_STATE_CHANGED")))
      {
        WifiManager localWifiManager = f.c(f.this);
        localList = null;
        if (localWifiManager != null)
          localList = f.c(f.this).getScanResults();
        if ((!paramIntent.getAction().equals("android.net.wifi.WIFI_STATE_CHANGED")) || ((localList != null) && ((localList == null) || (localList.size() != 0))));
      }
      else
      {
        return;
      }
      if ((!this.c) && (this.b != null) && (this.b.size() >= 4) && (localList != null) && (localList.size() <= 2))
      {
        a(localList);
        this.c = true;
        f.this.a(0L);
        return;
      }
      a(localList);
      this.c = false;
      f.a(f.this, new f.b(f.this, this.b, System.currentTimeMillis(), this.a));
      if (f.b(f.this) != null)
        f.b(f.this).a(f.d(f.this));
      f.this.a(20000L * f.e(f.this));
    }
  }
}

/* Location:           D:\rm_src\classes_dex2jar\
 * Qualified Name:     com.tencent.map.b.f
 * JD-Core Version:    0.6.0
 */