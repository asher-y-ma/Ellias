package oicq.wlogin_sdk.tools;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

public class MD5
{
  static final byte[] PADDING;
  static final int S11 = 7;
  static final int S12 = 12;
  static final int S13 = 17;
  static final int S14 = 22;
  static final int S21 = 5;
  static final int S22 = 9;
  static final int S23 = 14;
  static final int S24 = 20;
  static final int S31 = 4;
  static final int S32 = 11;
  static final int S33 = 16;
  static final int S34 = 23;
  static final int S41 = 6;
  static final int S42 = 10;
  static final int S43 = 15;
  static final int S44 = 21;
  private byte[] buffer = new byte[64];
  private long[] count = new long[2];
  private byte[] digest = new byte[16];
  public String digestHexStr;
  private long[] state = new long[4];

  static
  {
    byte[] arrayOfByte = new byte[64];
    arrayOfByte[0] = -128;
    PADDING = arrayOfByte;
  }

  public MD5()
  {
    md5Init();
  }

  private void Decode(long[] paramArrayOfLong, byte[] paramArrayOfByte, int paramInt)
  {
    int i = 0;
    for (int j = 0; ; j += 4)
    {
      if (j >= paramInt)
        return;
      paramArrayOfLong[i] = (b2iu(paramArrayOfByte[j]) | b2iu(paramArrayOfByte[(j + 1)]) << 8 | b2iu(paramArrayOfByte[(j + 2)]) << 16 | b2iu(paramArrayOfByte[(j + 3)]) << 24);
      i++;
    }
  }

  private void Encode(byte[] paramArrayOfByte, long[] paramArrayOfLong, int paramInt)
  {
    int i = 0;
    for (int j = 0; ; j += 4)
    {
      if (j >= paramInt)
        return;
      paramArrayOfByte[j] = (byte)(int)(0xFF & paramArrayOfLong[i]);
      paramArrayOfByte[(j + 1)] = (byte)(int)(0xFF & paramArrayOfLong[i] >>> 8);
      paramArrayOfByte[(j + 2)] = (byte)(int)(0xFF & paramArrayOfLong[i] >>> 16);
      paramArrayOfByte[(j + 3)] = (byte)(int)(0xFF & paramArrayOfLong[i] >>> 24);
      i++;
    }
  }

  private long F(long paramLong1, long paramLong2, long paramLong3)
  {
    return paramLong1 & paramLong2 | paramLong3 & (0xFFFFFFFF ^ paramLong1);
  }

  private long FF(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7)
  {
    long l = paramLong1 + (paramLong7 + (paramLong5 + F(paramLong2, paramLong3, paramLong4)));
    return paramLong2 + ((int)l << (int)paramLong6 | (int)l >>> (int)(32L - paramLong6));
  }

  private long G(long paramLong1, long paramLong2, long paramLong3)
  {
    return paramLong1 & paramLong3 | paramLong2 & (0xFFFFFFFF ^ paramLong3);
  }

  private long GG(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7)
  {
    long l = paramLong1 + (paramLong7 + (paramLong5 + G(paramLong2, paramLong3, paramLong4)));
    return paramLong2 + ((int)l << (int)paramLong6 | (int)l >>> (int)(32L - paramLong6));
  }

  private long H(long paramLong1, long paramLong2, long paramLong3)
  {
    return paramLong3 ^ (paramLong1 ^ paramLong2);
  }

  private long HH(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7)
  {
    long l = paramLong1 + (paramLong7 + (paramLong5 + H(paramLong2, paramLong3, paramLong4)));
    return paramLong2 + ((int)l << (int)paramLong6 | (int)l >>> (int)(32L - paramLong6));
  }

  private long I(long paramLong1, long paramLong2, long paramLong3)
  {
    return paramLong2 ^ (paramLong1 | 0xFFFFFFFF ^ paramLong3);
  }

  private long II(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7)
  {
    long l = paramLong1 + (paramLong7 + (paramLong5 + I(paramLong2, paramLong3, paramLong4)));
    return paramLong2 + ((int)l << (int)paramLong6 | (int)l >>> (int)(32L - paramLong6));
  }

  public static long b2iu(byte paramByte)
  {
    if (paramByte < 0)
      paramByte &= 255;
    return paramByte;
  }

  public static String byteHEX(byte paramByte)
  {
    char[] arrayOfChar1 = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70 };
    char[] arrayOfChar2 = new char[2];
    arrayOfChar2[0] = arrayOfChar1[(0xF & paramByte >>> 4)];
    arrayOfChar2[1] = arrayOfChar1[(paramByte & 0xF)];
    return new String(arrayOfChar2);
  }

  public static String getFileMD5(File paramFile)
    throws FileNotFoundException
  {
    FileInputStream localFileInputStream = new FileInputStream(paramFile);
    byte[] arrayOfByte1 = new byte[1024];
    char[] arrayOfChar1 = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102 };
    while (true)
    {
      byte[] arrayOfByte2;
      char[] arrayOfChar2;
      int j;
      try
      {
        MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
        int i = localFileInputStream.read(arrayOfByte1, 0, 1024);
        if (i != -1)
          continue;
        localFileInputStream.close();
        arrayOfByte2 = localMessageDigest.digest();
        arrayOfChar2 = new char[32];
        j = 0;
        k = 0;
        if (j >= 16)
        {
          return new String(arrayOfChar2);
          localMessageDigest.update(arrayOfByte1, 0, i);
          continue;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return null;
      }
      int m = arrayOfByte2[j];
      int n = k + 1;
      arrayOfChar2[k] = arrayOfChar1[(0xF & m >>> 4)];
      int k = n + 1;
      arrayOfChar2[n] = arrayOfChar1[(m & 0xF)];
      j++;
    }
  }

  public static String getMD5String(byte[] paramArrayOfByte)
  {
    char[] arrayOfChar1 = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102 };
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.update(paramArrayOfByte);
      byte[] arrayOfByte = localMessageDigest.digest();
      char[] arrayOfChar2 = new char[32];
      int i = 0;
      int j = 0;
      while (true)
      {
        if (i >= 16)
          return new String(arrayOfChar2);
        int k = arrayOfByte[i];
        int m = j + 1;
        arrayOfChar2[j] = arrayOfChar1[(0xF & k >>> 4)];
        j = m + 1;
        arrayOfChar2[m] = arrayOfChar1[(k & 0xF)];
        i++;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  private void md5Final()
  {
    byte[] arrayOfByte = new byte[8];
    Encode(arrayOfByte, this.count, 8);
    int i = 0x3F & (int)(this.count[0] >>> 3);
    if (i < 56);
    for (int j = 56 - i; ; j = 120 - i)
    {
      md5Update(PADDING, j);
      md5Update(arrayOfByte, 8);
      Encode(this.digest, this.state, 16);
      return;
    }
  }

  private void md5Init()
  {
    this.count[0] = 0L;
    this.count[1] = 0L;
    this.state[0] = 1732584193L;
    this.state[1] = 4023233417L;
    this.state[2] = 2562383102L;
    this.state[3] = 271733878L;
  }

  private void md5Memcpy(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt1, int paramInt2, int paramInt3)
  {
    for (int i = 0; ; i++)
    {
      if (i >= paramInt3)
        return;
      paramArrayOfByte1[(paramInt1 + i)] = paramArrayOfByte2[(paramInt2 + i)];
    }
  }

  private void md5Transform(byte[] paramArrayOfByte)
  {
    long l1 = this.state[0];
    long l2 = this.state[1];
    long l3 = this.state[2];
    long l4 = this.state[3];
    long[] arrayOfLong1 = new long[16];
    Decode(arrayOfLong1, paramArrayOfByte, 64);
    long l5 = FF(l1, l2, l3, l4, arrayOfLong1[0], 7L, 3614090360L);
    long l6 = FF(l4, l5, l2, l3, arrayOfLong1[1], 12L, 3905402710L);
    long l7 = FF(l3, l6, l5, l2, arrayOfLong1[2], 17L, 606105819L);
    long l8 = FF(l2, l7, l6, l5, arrayOfLong1[3], 22L, 3250441966L);
    long l9 = FF(l5, l8, l7, l6, arrayOfLong1[4], 7L, 4118548399L);
    long l10 = FF(l6, l9, l8, l7, arrayOfLong1[5], 12L, 1200080426L);
    long l11 = FF(l7, l10, l9, l8, arrayOfLong1[6], 17L, 2821735955L);
    long l12 = FF(l8, l11, l10, l9, arrayOfLong1[7], 22L, 4249261313L);
    long l13 = FF(l9, l12, l11, l10, arrayOfLong1[8], 7L, 1770035416L);
    long l14 = FF(l10, l13, l12, l11, arrayOfLong1[9], 12L, 2336552879L);
    long l15 = FF(l11, l14, l13, l12, arrayOfLong1[10], 17L, 4294925233L);
    long l16 = FF(l12, l15, l14, l13, arrayOfLong1[11], 22L, 2304563134L);
    long l17 = FF(l13, l16, l15, l14, arrayOfLong1[12], 7L, 1804603682L);
    long l18 = FF(l14, l17, l16, l15, arrayOfLong1[13], 12L, 4254626195L);
    long l19 = FF(l15, l18, l17, l16, arrayOfLong1[14], 17L, 2792965006L);
    long l20 = FF(l16, l19, l18, l17, arrayOfLong1[15], 22L, 1236535329L);
    long l21 = GG(l17, l20, l19, l18, arrayOfLong1[1], 5L, 4129170786L);
    long l22 = GG(l18, l21, l20, l19, arrayOfLong1[6], 9L, 3225465664L);
    long l23 = GG(l19, l22, l21, l20, arrayOfLong1[11], 14L, 643717713L);
    long l24 = GG(l20, l23, l22, l21, arrayOfLong1[0], 20L, 3921069994L);
    long l25 = GG(l21, l24, l23, l22, arrayOfLong1[5], 5L, 3593408605L);
    long l26 = GG(l22, l25, l24, l23, arrayOfLong1[10], 9L, 38016083L);
    long l27 = GG(l23, l26, l25, l24, arrayOfLong1[15], 14L, 3634488961L);
    long l28 = GG(l24, l27, l26, l25, arrayOfLong1[4], 20L, 3889429448L);
    long l29 = GG(l25, l28, l27, l26, arrayOfLong1[9], 5L, 568446438L);
    long l30 = GG(l26, l29, l28, l27, arrayOfLong1[14], 9L, 3275163606L);
    long l31 = GG(l27, l30, l29, l28, arrayOfLong1[3], 14L, 4107603335L);
    long l32 = GG(l28, l31, l30, l29, arrayOfLong1[8], 20L, 1163531501L);
    long l33 = GG(l29, l32, l31, l30, arrayOfLong1[13], 5L, 2850285829L);
    long l34 = GG(l30, l33, l32, l31, arrayOfLong1[2], 9L, 4243563512L);
    long l35 = GG(l31, l34, l33, l32, arrayOfLong1[7], 14L, 1735328473L);
    long l36 = GG(l32, l35, l34, l33, arrayOfLong1[12], 20L, 2368359562L);
    long l37 = HH(l33, l36, l35, l34, arrayOfLong1[5], 4L, 4294588738L);
    long l38 = HH(l34, l37, l36, l35, arrayOfLong1[8], 11L, 2272392833L);
    long l39 = HH(l35, l38, l37, l36, arrayOfLong1[11], 16L, 1839030562L);
    long l40 = HH(l36, l39, l38, l37, arrayOfLong1[14], 23L, 4259657740L);
    long l41 = HH(l37, l40, l39, l38, arrayOfLong1[1], 4L, 2763975236L);
    long l42 = HH(l38, l41, l40, l39, arrayOfLong1[4], 11L, 1272893353L);
    long l43 = HH(l39, l42, l41, l40, arrayOfLong1[7], 16L, 4139469664L);
    long l44 = HH(l40, l43, l42, l41, arrayOfLong1[10], 23L, 3200236656L);
    long l45 = HH(l41, l44, l43, l42, arrayOfLong1[13], 4L, 681279174L);
    long l46 = HH(l42, l45, l44, l43, arrayOfLong1[0], 11L, 3936430074L);
    long l47 = HH(l43, l46, l45, l44, arrayOfLong1[3], 16L, 3572445317L);
    long l48 = HH(l44, l47, l46, l45, arrayOfLong1[6], 23L, 76029189L);
    long l49 = HH(l45, l48, l47, l46, arrayOfLong1[9], 4L, 3654602809L);
    long l50 = HH(l46, l49, l48, l47, arrayOfLong1[12], 11L, 3873151461L);
    long l51 = HH(l47, l50, l49, l48, arrayOfLong1[15], 16L, 530742520L);
    long l52 = HH(l48, l51, l50, l49, arrayOfLong1[2], 23L, 3299628645L);
    long l53 = II(l49, l52, l51, l50, arrayOfLong1[0], 6L, 4096336452L);
    long l54 = II(l50, l53, l52, l51, arrayOfLong1[7], 10L, 1126891415L);
    long l55 = II(l51, l54, l53, l52, arrayOfLong1[14], 15L, 2878612391L);
    long l56 = II(l52, l55, l54, l53, arrayOfLong1[5], 21L, 4237533241L);
    long l57 = II(l53, l56, l55, l54, arrayOfLong1[12], 6L, 1700485571L);
    long l58 = II(l54, l57, l56, l55, arrayOfLong1[3], 10L, 2399980690L);
    long l59 = II(l55, l58, l57, l56, arrayOfLong1[10], 15L, 4293915773L);
    long l60 = II(l56, l59, l58, l57, arrayOfLong1[1], 21L, 2240044497L);
    long l61 = II(l57, l60, l59, l58, arrayOfLong1[8], 6L, 1873313359L);
    long l62 = II(l58, l61, l60, l59, arrayOfLong1[15], 10L, 4264355552L);
    long l63 = II(l59, l62, l61, l60, arrayOfLong1[6], 15L, 2734768916L);
    long l64 = II(l60, l63, l62, l61, arrayOfLong1[13], 21L, 1309151649L);
    long l65 = II(l61, l64, l63, l62, arrayOfLong1[4], 6L, 4149444226L);
    long l66 = II(l62, l65, l64, l63, arrayOfLong1[11], 10L, 3174756917L);
    long l67 = II(l63, l66, l65, l64, arrayOfLong1[2], 15L, 718787259L);
    long l68 = II(l64, l67, l66, l65, arrayOfLong1[9], 21L, 3951481745L);
    long[] arrayOfLong2 = this.state;
    arrayOfLong2[0] = (l65 + arrayOfLong2[0]);
    long[] arrayOfLong3 = this.state;
    arrayOfLong3[1] = (l68 + arrayOfLong3[1]);
    long[] arrayOfLong4 = this.state;
    arrayOfLong4[2] = (l67 + arrayOfLong4[2]);
    long[] arrayOfLong5 = this.state;
    arrayOfLong5[3] = (l66 + arrayOfLong5[3]);
  }

  private void md5Update(byte[] paramArrayOfByte, int paramInt)
  {
    byte[] arrayOfByte1 = new byte[64];
    int i = 0x3F & (int)(this.count[0] >>> 3);
    long[] arrayOfLong1 = this.count;
    long l = arrayOfLong1[0] + (paramInt << 3);
    arrayOfLong1[0] = l;
    if (l < paramInt << 3)
    {
      long[] arrayOfLong3 = this.count;
      arrayOfLong3[1] = (1L + arrayOfLong3[1]);
    }
    long[] arrayOfLong2 = this.count;
    arrayOfLong2[1] += (paramInt >>> 29);
    int j = 64 - i;
    int k;
    if (paramInt >= j)
    {
      md5Memcpy(this.buffer, paramArrayOfByte, i, 0, j);
      md5Transform(this.buffer);
      k = j;
      if (k + 63 >= paramInt)
        i = 0;
    }
    while (true)
    {
      byte[] arrayOfByte2 = this.buffer;
      int m = paramInt - k;
      md5Memcpy(arrayOfByte2, paramArrayOfByte, i, k, m);
      return;
      md5Memcpy(arrayOfByte1, paramArrayOfByte, 0, k, 64);
      md5Transform(arrayOfByte1);
      k += 64;
      break;
      k = 0;
    }
  }

  // ERROR //
  private boolean md5Update(InputStream paramInputStream, long paramLong)
  {
    // Byte code:
    //   0: bipush 64
    //   2: newarray byte
    //   4: astore 4
    //   6: bipush 63
    //   8: aload_0
    //   9: getfield 57	oicq/wlogin_sdk/tools/MD5:count	[J
    //   12: iconst_0
    //   13: laload
    //   14: iconst_3
    //   15: lushr
    //   16: l2i
    //   17: iand
    //   18: istore 5
    //   20: aload_0
    //   21: getfield 57	oicq/wlogin_sdk/tools/MD5:count	[J
    //   24: astore 6
    //   26: aload 6
    //   28: iconst_0
    //   29: laload
    //   30: lload_2
    //   31: iconst_3
    //   32: lshl
    //   33: ladd
    //   34: lstore 7
    //   36: aload 6
    //   38: iconst_0
    //   39: lload 7
    //   41: lastore
    //   42: lload 7
    //   44: lload_2
    //   45: iconst_3
    //   46: lshl
    //   47: lcmp
    //   48: ifge +19 -> 67
    //   51: aload_0
    //   52: getfield 57	oicq/wlogin_sdk/tools/MD5:count	[J
    //   55: astore 22
    //   57: aload 22
    //   59: iconst_1
    //   60: lconst_1
    //   61: aload 22
    //   63: iconst_1
    //   64: laload
    //   65: ladd
    //   66: lastore
    //   67: aload_0
    //   68: getfield 57	oicq/wlogin_sdk/tools/MD5:count	[J
    //   71: astore 9
    //   73: aload 9
    //   75: iconst_1
    //   76: aload 9
    //   78: iconst_1
    //   79: laload
    //   80: lload_2
    //   81: bipush 29
    //   83: lushr
    //   84: ladd
    //   85: lastore
    //   86: bipush 64
    //   88: iload 5
    //   90: isub
    //   91: istore 10
    //   93: lload_2
    //   94: iload 10
    //   96: i2l
    //   97: lcmp
    //   98: iflt +140 -> 238
    //   101: iload 10
    //   103: newarray byte
    //   105: astore 17
    //   107: aload_1
    //   108: aload 17
    //   110: iconst_0
    //   111: iload 10
    //   113: invokevirtual 361	java/io/InputStream:read	([BII)I
    //   116: pop
    //   117: aload_0
    //   118: aload_0
    //   119: getfield 59	oicq/wlogin_sdk/tools/MD5:buffer	[B
    //   122: aload 17
    //   124: iload 5
    //   126: iconst_0
    //   127: iload 10
    //   129: invokespecial 355	oicq/wlogin_sdk/tools/MD5:md5Memcpy	([B[BIII)V
    //   132: aload_0
    //   133: aload_0
    //   134: getfield 59	oicq/wlogin_sdk/tools/MD5:buffer	[B
    //   137: invokespecial 357	oicq/wlogin_sdk/tools/MD5:md5Transform	([B)V
    //   140: iload 10
    //   142: istore 11
    //   144: iload 11
    //   146: bipush 63
    //   148: iadd
    //   149: i2l
    //   150: lload_2
    //   151: lcmp
    //   152: iflt +58 -> 210
    //   155: iconst_0
    //   156: istore 5
    //   158: lload_2
    //   159: iload 11
    //   161: i2l
    //   162: lsub
    //   163: l2i
    //   164: newarray byte
    //   166: astore 12
    //   168: aload_1
    //   169: aload 12
    //   171: invokevirtual 364	java/io/InputStream:read	([B)I
    //   174: pop
    //   175: aload_0
    //   176: getfield 59	oicq/wlogin_sdk/tools/MD5:buffer	[B
    //   179: astore 15
    //   181: aload 12
    //   183: arraylength
    //   184: istore 16
    //   186: aload_0
    //   187: aload 15
    //   189: aload 12
    //   191: iload 5
    //   193: iconst_0
    //   194: iload 16
    //   196: invokespecial 355	oicq/wlogin_sdk/tools/MD5:md5Memcpy	([B[BIII)V
    //   199: iconst_1
    //   200: ireturn
    //   201: astore 18
    //   203: aload 18
    //   205: invokevirtual 160	java/lang/Exception:printStackTrace	()V
    //   208: iconst_0
    //   209: ireturn
    //   210: aload_1
    //   211: aload 4
    //   213: invokevirtual 364	java/io/InputStream:read	([B)I
    //   216: pop
    //   217: aload_0
    //   218: aload 4
    //   220: invokespecial 357	oicq/wlogin_sdk/tools/MD5:md5Transform	([B)V
    //   223: iinc 11 64
    //   226: goto -82 -> 144
    //   229: astore 20
    //   231: aload 20
    //   233: invokevirtual 160	java/lang/Exception:printStackTrace	()V
    //   236: iconst_0
    //   237: ireturn
    //   238: iconst_0
    //   239: istore 11
    //   241: goto -83 -> 158
    //   244: astore 13
    //   246: aload 13
    //   248: invokevirtual 160	java/lang/Exception:printStackTrace	()V
    //   251: iconst_0
    //   252: ireturn
    //
    // Exception table:
    //   from	to	target	type
    //   107	117	201	java/lang/Exception
    //   210	217	229	java/lang/Exception
    //   168	175	244	java/lang/Exception
  }

  public static String toMD5(String paramString)
  {
    try
    {
      byte[] arrayOfByte3 = paramString.getBytes("ISO8859_1");
      arrayOfByte1 = arrayOfByte3;
      arrayOfByte2 = new MD5().getMD5(arrayOfByte1);
      str = "";
      i = 0;
      if (i >= 16)
        return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      while (true)
      {
        byte[] arrayOfByte2;
        int i;
        byte[] arrayOfByte1 = paramString.getBytes();
        continue;
        String str = str + byteHEX(arrayOfByte2[i]);
        i++;
      }
    }
  }

  public static String toMD5(byte[] paramArrayOfByte)
  {
    byte[] arrayOfByte = new MD5().getMD5(paramArrayOfByte);
    String str = "";
    for (int i = 0; ; i++)
    {
      if (i >= 16)
        return str;
      str = str + byteHEX(arrayOfByte[i]);
    }
  }

  public static byte[] toMD5Byte(InputStream paramInputStream, long paramLong)
  {
    return new MD5().getMD5(paramInputStream, paramLong);
  }

  public static byte[] toMD5Byte(String paramString)
  {
    try
    {
      byte[] arrayOfByte2 = paramString.getBytes("ISO8859_1");
      arrayOfByte1 = arrayOfByte2;
      return new MD5().getMD5(arrayOfByte1);
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      while (true)
        byte[] arrayOfByte1 = paramString.getBytes();
    }
  }

  public static byte[] toMD5Byte(byte[] paramArrayOfByte)
  {
    return new MD5().getMD5(paramArrayOfByte);
  }

  public byte[] getMD5(InputStream paramInputStream, long paramLong)
  {
    md5Init();
    if (!md5Update(paramInputStream, paramLong))
      return new byte[16];
    md5Final();
    return this.digest;
  }

  public byte[] getMD5(byte[] paramArrayOfByte)
  {
    md5Init();
    md5Update(new ByteArrayInputStream(paramArrayOfByte), paramArrayOfByte.length);
    md5Final();
    return this.digest;
  }
}

/* Location:           D:\rm_src\classes_dex2jar\
 * Qualified Name:     oicq.wlogin_sdk.tools.MD5
 * JD-Core Version:    0.6.0
 */