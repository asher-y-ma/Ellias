package oicq.wlogin_sdk.tools;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

class CryptorImpl
{
  private int contextStart;
  private int crypt;
  private boolean header = true;
  private byte[] key;
  private byte[] out;
  private int padding;
  private byte[] plain;
  private int pos;
  private int preCrypt;
  private byte[] prePlain;
  private Random random = new Random();

  private byte[] decipher(byte[] paramArrayOfByte)
  {
    return decipher(paramArrayOfByte, 0);
  }

  private byte[] decipher(byte[] paramArrayOfByte, int paramInt)
  {
    try
    {
      long l1 = getUnsignedInt(paramArrayOfByte, paramInt, 4);
      long l2 = getUnsignedInt(paramArrayOfByte, paramInt + 4, 4);
      long l3 = getUnsignedInt(this.key, 0, 4);
      long l4 = getUnsignedInt(this.key, 4, 4);
      long l5 = getUnsignedInt(this.key, 8, 4);
      long l6 = getUnsignedInt(this.key, 12, 4);
      long l7 = 0xE3779B90 & 0xFFFFFFFF;
      long l8 = 0x9E3779B9 & 0xFFFFFFFF;
      int j;
      for (int i = 16; ; i = j)
      {
        j = i - 1;
        if (i <= 0)
        {
          ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(8);
          DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
          localDataOutputStream.writeInt((int)l1);
          localDataOutputStream.writeInt((int)l2);
          localDataOutputStream.close();
          byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
          return arrayOfByte;
        }
        l2 = 0xFFFFFFFF & l2 - (l5 + (l1 << 4) ^ l1 + l7 ^ l6 + (l1 >>> 5));
        l1 = 0xFFFFFFFF & l1 - (l3 + (l2 << 4) ^ l2 + l7 ^ l4 + (l2 >>> 5));
        l7 = 0xFFFFFFFF & l7 - l8;
      }
    }
    catch (IOException localIOException)
    {
    }
    return null;
  }

  private boolean decrypt8Bytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    int i = 1;
    for (this.pos = 0; ; this.pos = (1 + this.pos))
    {
      if (this.pos >= 8)
      {
        this.prePlain = decipher(this.prePlain);
        if (this.prePlain != null)
          break;
        i = 0;
      }
      do
        return i;
      while (this.contextStart + this.pos >= paramInt2);
      byte[] arrayOfByte = this.prePlain;
      int j = this.pos;
      arrayOfByte[j] = (byte)(arrayOfByte[j] ^ paramArrayOfByte[(paramInt1 + this.crypt + this.pos)]);
    }
    this.contextStart = (8 + this.contextStart);
    this.crypt = (8 + this.crypt);
    this.pos = 0;
    return i;
  }

  private byte[] encipher(byte[] paramArrayOfByte)
  {
    try
    {
      long l1 = getUnsignedInt(paramArrayOfByte, 0, 4);
      long l2 = getUnsignedInt(paramArrayOfByte, 4, 4);
      long l3 = getUnsignedInt(this.key, 0, 4);
      long l4 = getUnsignedInt(this.key, 4, 4);
      long l5 = getUnsignedInt(this.key, 8, 4);
      long l6 = getUnsignedInt(this.key, 12, 4);
      long l7 = 0L;
      long l8 = 0x9E3779B9 & 0xFFFFFFFF;
      int j;
      for (int i = 16; ; i = j)
      {
        j = i - 1;
        if (i <= 0)
        {
          ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(8);
          DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
          localDataOutputStream.writeInt((int)l1);
          localDataOutputStream.writeInt((int)l2);
          localDataOutputStream.close();
          byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
          return arrayOfByte;
        }
        l7 = 0xFFFFFFFF & l7 + l8;
        l1 = 0xFFFFFFFF & l1 + (l3 + (l2 << 4) ^ l2 + l7 ^ l4 + (l2 >>> 5));
        l2 = 0xFFFFFFFF & l2 + (l5 + (l1 << 4) ^ l1 + l7 ^ l6 + (l1 >>> 5));
      }
    }
    catch (IOException localIOException)
    {
    }
    return null;
  }

  private byte[] encrypt(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2)
  {
    this.plain = new byte[8];
    this.prePlain = new byte[8];
    this.pos = 1;
    this.padding = 0;
    this.preCrypt = 0;
    this.crypt = 0;
    this.key = paramArrayOfByte2;
    this.header = true;
    this.pos = ((paramInt2 + 10) % 8);
    if (this.pos != 0)
      this.pos = (8 - this.pos);
    this.out = new byte[10 + (paramInt2 + this.pos)];
    this.plain[0] = (byte)(0xF8 & rand() | this.pos);
    int i = 1;
    int j;
    if (i > this.pos)
    {
      this.pos = (1 + this.pos);
      j = 0;
      label136: if (j < 8)
        break label204;
      this.padding = 1;
    }
    int m;
    while (true)
    {
      if (this.padding > 2)
      {
        m = paramInt1;
        if (paramInt2 > 0)
          break label287;
        this.padding = 1;
        label168: if (this.padding <= 7)
          break label354;
        return this.out;
        this.plain[i] = (byte)(0xFF & rand());
        i++;
        break;
        label204: this.prePlain[j] = 0;
        j++;
        break label136;
      }
      if (this.pos < 8)
      {
        byte[] arrayOfByte1 = this.plain;
        int k = this.pos;
        this.pos = (k + 1);
        arrayOfByte1[k] = (byte)(0xFF & rand());
        this.padding = (1 + this.padding);
      }
      if (this.pos != 8)
        continue;
      encrypt8Bytes();
    }
    label287: int n;
    if (this.pos < 8)
    {
      byte[] arrayOfByte2 = this.plain;
      int i1 = this.pos;
      this.pos = (i1 + 1);
      n = m + 1;
      arrayOfByte2[i1] = paramArrayOfByte1[m];
      paramInt2--;
    }
    while (true)
    {
      if (this.pos == 8)
        encrypt8Bytes();
      m = n;
      break;
      label354: if (this.pos < 8)
      {
        byte[] arrayOfByte3 = this.plain;
        int i2 = this.pos;
        this.pos = (i2 + 1);
        arrayOfByte3[i2] = 0;
        this.padding = (1 + this.padding);
      }
      if (this.pos != 8)
        break label168;
      encrypt8Bytes();
      break label168;
      n = m;
    }
  }

  private void encrypt8Bytes()
  {
    this.pos = 0;
    if (this.pos >= 8)
      System.arraycopy(encipher(this.plain), 0, this.out, this.crypt, 8);
    for (this.pos = 0; ; this.pos = (1 + this.pos))
    {
      if (this.pos >= 8)
      {
        System.arraycopy(this.plain, 0, this.prePlain, 0, 8);
        this.preCrypt = this.crypt;
        this.crypt = (8 + this.crypt);
        this.pos = 0;
        this.header = false;
        return;
        if (this.header)
        {
          byte[] arrayOfByte2 = this.plain;
          int j = this.pos;
          arrayOfByte2[j] = (byte)(arrayOfByte2[j] ^ this.prePlain[this.pos]);
        }
        while (true)
        {
          this.pos = (1 + this.pos);
          break;
          byte[] arrayOfByte1 = this.plain;
          int i = this.pos;
          arrayOfByte1[i] = (byte)(arrayOfByte1[i] ^ this.out[(this.preCrypt + this.pos)]);
        }
      }
      byte[] arrayOfByte3 = this.out;
      int k = this.crypt + this.pos;
      arrayOfByte3[k] = (byte)(arrayOfByte3[k] ^ this.prePlain[this.pos]);
    }
  }

  private static long getUnsignedInt(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    long l = 0L;
    int i;
    if (paramInt2 > 8)
      i = paramInt1 + 8;
    for (int j = paramInt1; ; j++)
    {
      if (j >= i)
      {
        return 0xFFFFFFFF & l | l >>> 32;
        i = paramInt1 + paramInt2;
        break;
      }
      l = l << 8 | 0xFF & paramArrayOfByte[j];
    }
  }

  private int rand()
  {
    return this.random.nextInt();
  }

  protected byte[] decrypt(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2)
  {
    this.preCrypt = 0;
    this.crypt = 0;
    this.key = paramArrayOfByte2;
    byte[] arrayOfByte = new byte[paramInt1 + 8];
    if ((paramInt2 % 8 != 0) || (paramInt2 < 16));
    int i;
    do
    {
      return null;
      this.prePlain = decipher(paramArrayOfByte1, paramInt1);
      this.pos = (0x7 & this.prePlain[0]);
      i = -10 + (paramInt2 - this.pos);
    }
    while (i < 0);
    int j = paramInt1;
    label81: int k;
    if (j >= arrayOfByte.length)
    {
      this.out = new byte[i];
      this.preCrypt = 0;
      this.crypt = 8;
      this.contextStart = 8;
      this.pos = (1 + this.pos);
      this.padding = 1;
      label129: if (this.padding <= 2)
        break label176;
      k = 0;
      label140: if (i != 0)
        break label229;
    }
    for (this.padding = 1; ; this.padding = (1 + this.padding))
    {
      if (this.padding >= 8)
      {
        return this.out;
        arrayOfByte[j] = 0;
        j++;
        break label81;
        label176: if (this.pos < 8)
        {
          this.pos = (1 + this.pos);
          this.padding = (1 + this.padding);
        }
        if (this.pos != 8)
          break label129;
        arrayOfByte = paramArrayOfByte1;
        if (decrypt8Bytes(paramArrayOfByte1, paramInt1, paramInt2))
          break label129;
        return null;
        label229: if (this.pos < 8)
        {
          this.out[k] = (byte)(arrayOfByte[(paramInt1 + this.preCrypt + this.pos)] ^ this.prePlain[this.pos]);
          k++;
          i--;
          this.pos = (1 + this.pos);
        }
        if (this.pos != 8)
          break label140;
        arrayOfByte = paramArrayOfByte1;
        this.preCrypt = (-8 + this.crypt);
        if (decrypt8Bytes(paramArrayOfByte1, paramInt1, paramInt2))
          break label140;
        return null;
      }
      if (this.pos < 8)
      {
        if ((arrayOfByte[(paramInt1 + this.preCrypt + this.pos)] ^ this.prePlain[this.pos]) != 0)
          break;
        this.pos = (1 + this.pos);
      }
      if (this.pos != 8)
        continue;
      arrayOfByte = paramArrayOfByte1;
      this.preCrypt = this.crypt;
      if (!decrypt8Bytes(paramArrayOfByte1, paramInt1, paramInt2))
        break;
    }
  }

  protected byte[] decrypt(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    return decrypt(paramArrayOfByte1, 0, paramArrayOfByte1.length, paramArrayOfByte2);
  }

  protected byte[] encrypt(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    return encrypt(paramArrayOfByte1, 0, paramArrayOfByte1.length, paramArrayOfByte2);
  }
}

/* Location:           D:\rm_src\classes_dex2jar\
 * Qualified Name:     oicq.wlogin_sdk.tools.CryptorImpl
 * JD-Core Version:    0.6.0
 */