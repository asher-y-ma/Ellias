package com.tencent.beacon.c.e;

import com.tencent.beacon.e.a;
import com.tencent.beacon.e.b;
import com.tencent.beacon.e.c;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class d extends c
{
  private static Map<String, String> g;
  private static ArrayList<String> h;
  private static e i;
  public byte a = 0;
  public byte b = 0;
  public String c = "";
  public Map<String, String> d = null;
  public ArrayList<String> e = null;
  public e f = null;

  public final void a(a parama)
  {
    this.a = parama.a(this.a, 0, true);
    this.b = parama.a(this.b, 1, true);
    this.c = parama.b(2, true);
    if (g == null)
    {
      g = new HashMap();
      g.put("", "");
    }
    this.d = ((Map)parama.a(g, 3, true));
    if (h == null)
    {
      h = new ArrayList();
      h.add("");
    }
    this.e = ((ArrayList)parama.a(h, 4, false));
    if (i == null)
      i = new e();
    this.f = ((e)parama.a(i, 5, false));
  }

  public final void a(b paramb)
  {
    paramb.a(this.a, 0);
    paramb.a(this.b, 1);
    paramb.a(this.c, 2);
    paramb.a(this.d, 3);
    if (this.e != null)
      paramb.a(this.e, 4);
    if (this.f != null)
      paramb.a(this.f, 5);
  }
}

/* Location:           D:\rm_src\classes_dex2jar\
 * Qualified Name:     com.tencent.beacon.c.e.d
 * JD-Core Version:    0.6.0
 */