package mqq.a.a.b;

import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public final class b
  implements c
{
  private String a = null;

  public final void a(String paramString)
  {
    if (paramString != null)
      this.a = paramString;
  }

  public final byte[] a(byte[] paramArrayOfByte)
    throws Exception
  {
    if ((this.a == null) || (paramArrayOfByte == null))
      return null;
    Cipher localCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
    DESKeySpec localDESKeySpec = new DESKeySpec(this.a.getBytes("UTF-8"));
    localCipher.init(2, SecretKeyFactory.getInstance("DES").generateSecret(localDESKeySpec), new IvParameterSpec(this.a.getBytes("UTF-8")));
    return localCipher.doFinal(paramArrayOfByte);
  }

  public final byte[] b(byte[] paramArrayOfByte)
    throws Exception, NoSuchAlgorithmException
  {
    if ((this.a == null) || (paramArrayOfByte == null))
      return null;
    Cipher localCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
    DESKeySpec localDESKeySpec = new DESKeySpec(this.a.getBytes("UTF-8"));
    localCipher.init(1, SecretKeyFactory.getInstance("DES").generateSecret(localDESKeySpec), new IvParameterSpec(this.a.getBytes("UTF-8")));
    return localCipher.doFinal(paramArrayOfByte);
  }
}

/* Location:           D:\rm_src\classes_dex2jar\
 * Qualified Name:     mqq.a.a.b.b
 * JD-Core Version:    0.6.0
 */