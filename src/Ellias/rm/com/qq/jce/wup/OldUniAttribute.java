package com.qq.jce.wup;

import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;
import com.qq.taf.jce.JceStruct;
import com.qq.taf.jce.JceUtil;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

class OldUniAttribute
{
  protected HashMap<String, HashMap<String, byte[]>> _data = new HashMap();
  JceInputStream _is = new JceInputStream();
  protected HashMap<String, Object> cachedClassName = new HashMap();
  private HashMap<String, Object> cachedData = new HashMap();
  protected String encodeName = "GBK";

  private void checkObjectType(ArrayList<String> paramArrayList, Object paramObject)
  {
    if (paramObject.getClass().isArray())
    {
      if (!paramObject.getClass().getComponentType().toString().equals("byte"))
        throw new IllegalArgumentException("only byte[] is supported");
      if (Array.getLength(paramObject) > 0)
      {
        paramArrayList.add("java.util.List");
        checkObjectType(paramArrayList, Array.get(paramObject, 0));
        return;
      }
      paramArrayList.add("Array");
      paramArrayList.add("?");
      return;
    }
    if ((paramObject instanceof Array))
      throw new IllegalArgumentException("can not support Array, please use List");
    if ((paramObject instanceof List))
    {
      paramArrayList.add("java.util.List");
      List localList = (List)paramObject;
      if (localList.size() > 0)
      {
        checkObjectType(paramArrayList, localList.get(0));
        return;
      }
      paramArrayList.add("?");
      return;
    }
    if ((paramObject instanceof Map))
    {
      paramArrayList.add("java.util.Map");
      Map localMap = (Map)paramObject;
      if (localMap.size() > 0)
      {
        Object localObject1 = localMap.keySet().iterator().next();
        Object localObject2 = localMap.get(localObject1);
        paramArrayList.add(localObject1.getClass().getName());
        checkObjectType(paramArrayList, localObject2);
        return;
      }
      paramArrayList.add("?");
      paramArrayList.add("?");
      return;
    }
    paramArrayList.add(paramObject.getClass().getName());
  }

  private Object getCacheProxy(String paramString, boolean paramBoolean, ClassLoader paramClassLoader)
  {
    if (this.cachedClassName.containsKey(paramString))
      return this.cachedClassName.get(paramString);
    Object localObject = BasicClassTypeUtil.createClassByUni(paramString, paramBoolean, paramClassLoader);
    this.cachedClassName.put(paramString, localObject);
    return localObject;
  }

  private void saveDataCache(String paramString, Object paramObject)
  {
    this.cachedData.put(paramString, paramObject);
  }

  public void clearCacheData()
  {
    this.cachedData.clear();
  }

  public boolean containsKey(String paramString)
  {
    return this._data.containsKey(paramString);
  }

  public void decode(byte[] paramArrayOfByte)
  {
    this._is.wrap(paramArrayOfByte);
    this._is.setServerEncoding(this.encodeName);
    HashMap localHashMap1 = new HashMap(1);
    HashMap localHashMap2 = new HashMap(1);
    localHashMap2.put("", new byte[0]);
    localHashMap1.put("", localHashMap2);
    this._data = this._is.readMap(localHashMap1, 0, false);
  }

  public byte[] encode()
  {
    JceOutputStream localJceOutputStream = new JceOutputStream(0);
    localJceOutputStream.setServerEncoding(this.encodeName);
    localJceOutputStream.write(this._data, 0);
    return JceUtil.getJceBufArray(localJceOutputStream.getByteBuffer());
  }

  public <T> T get(String paramString, Object paramObject, boolean paramBoolean, ClassLoader paramClassLoader)
  {
    if (!this._data.containsKey(paramString))
      return paramObject;
    if (this.cachedData.containsKey(paramString))
      return this.cachedData.get(paramString);
    HashMap localHashMap = (HashMap)this._data.get(paramString);
    String str = "";
    byte[] arrayOfByte = new byte[0];
    Iterator localIterator = localHashMap.entrySet().iterator();
    if (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      str = (String)localEntry.getKey();
      arrayOfByte = (byte[])localEntry.getValue();
    }
    try
    {
      Object localObject1 = getCacheProxy(str, paramBoolean, paramClassLoader);
      this._is.wrap(arrayOfByte);
      this._is.setServerEncoding(this.encodeName);
      Object localObject2 = this._is.read(localObject1, 0, true);
      saveDataCache(paramString, localObject2);
      return localObject2;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      saveDataCache(paramString, paramObject);
    }
    return paramObject;
  }

  public <T> T get(String paramString, boolean paramBoolean, ClassLoader paramClassLoader)
    throws ObjectCreateException
  {
    if (!this._data.containsKey(paramString))
      return null;
    if (this.cachedData.containsKey(paramString))
      return this.cachedData.get(paramString);
    HashMap localHashMap = (HashMap)this._data.get(paramString);
    byte[] arrayOfByte = new byte[0];
    Iterator localIterator = localHashMap.entrySet().iterator();
    boolean bool = localIterator.hasNext();
    String str = null;
    if (bool)
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      str = (String)localEntry.getKey();
      arrayOfByte = (byte[])localEntry.getValue();
    }
    try
    {
      Object localObject1 = getCacheProxy(str, paramBoolean, paramClassLoader);
      this._is.wrap(arrayOfByte);
      this._is.setServerEncoding(this.encodeName);
      Object localObject2 = this._is.read(localObject1, 0, true);
      saveDataCache(paramString, localObject2);
      return localObject2;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    throw new ObjectCreateException(localException);
  }

  public String getEncodeName()
  {
    return this.encodeName;
  }

  public <T> T getJceStruct(String paramString, boolean paramBoolean, ClassLoader paramClassLoader)
    throws ObjectCreateException
  {
    if (!this._data.containsKey(paramString))
      return null;
    if (this.cachedData.containsKey(paramString))
      return this.cachedData.get(paramString);
    HashMap localHashMap = (HashMap)this._data.get(paramString);
    byte[] arrayOfByte = new byte[0];
    Iterator localIterator = localHashMap.entrySet().iterator();
    boolean bool = localIterator.hasNext();
    String str = null;
    if (bool)
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      str = (String)localEntry.getKey();
      arrayOfByte = (byte[])localEntry.getValue();
    }
    try
    {
      Object localObject = getCacheProxy(str, paramBoolean, paramClassLoader);
      this._is.wrap(arrayOfByte);
      this._is.setServerEncoding(this.encodeName);
      JceStruct localJceStruct = this._is.directRead((JceStruct)localObject, 0, true);
      saveDataCache(paramString, localJceStruct);
      return localJceStruct;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    throw new ObjectCreateException(localException);
  }

  public Set<String> getKeySet()
  {
    return Collections.unmodifiableSet(this._data.keySet());
  }

  public boolean isEmpty()
  {
    return this._data.isEmpty();
  }

  public <T> void put(String paramString, T paramT)
  {
    if (paramString == null)
      throw new IllegalArgumentException("put key can not is null");
    if (paramT == null)
      throw new IllegalArgumentException("put value can not is null");
    if ((paramT instanceof Set))
      throw new IllegalArgumentException("can not support Set");
    JceOutputStream localJceOutputStream = new JceOutputStream();
    localJceOutputStream.setServerEncoding(this.encodeName);
    localJceOutputStream.write(paramT, 0);
    byte[] arrayOfByte = JceUtil.getJceBufArray(localJceOutputStream.getByteBuffer());
    HashMap localHashMap = new HashMap(1);
    ArrayList localArrayList = new ArrayList(1);
    checkObjectType(localArrayList, paramT);
    localHashMap.put(BasicClassTypeUtil.transTypeList(localArrayList), arrayOfByte);
    this.cachedData.remove(paramString);
    this._data.put(paramString, localHashMap);
  }

  public <T> T remove(String paramString, boolean paramBoolean, ClassLoader paramClassLoader)
    throws ObjectCreateException
  {
    if (!this._data.containsKey(paramString))
      return null;
    HashMap localHashMap = (HashMap)this._data.remove(paramString);
    String str = "";
    byte[] arrayOfByte = new byte[0];
    Iterator localIterator = localHashMap.entrySet().iterator();
    if (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      str = (String)localEntry.getKey();
      arrayOfByte = (byte[])localEntry.getValue();
    }
    try
    {
      Object localObject1 = BasicClassTypeUtil.createClassByUni(str, paramBoolean, paramClassLoader);
      this._is.wrap(arrayOfByte);
      this._is.setServerEncoding(this.encodeName);
      Object localObject2 = this._is.read(localObject1, 0, true);
      return localObject2;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    throw new ObjectCreateException(localException);
  }

  public void setEncodeName(String paramString)
  {
    this.encodeName = paramString;
  }

  public int size()
  {
    return this._data.size();
  }
}

/* Location:           D:\rm_src\classes_dex2jar\
 * Qualified Name:     com.qq.jce.wup.OldUniAttribute
 * JD-Core Version:    0.6.0
 */