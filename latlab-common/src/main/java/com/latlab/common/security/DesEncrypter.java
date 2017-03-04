/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.stately.common.security;

/**
 *
 * @author Edwin
 */
import java.io.PrintStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class DesEncrypter
  implements Serializable
{
  private static final String formatname = "RAW";
  private static final String algnameAES = "AES";
  private static final String algnameDES = "DES";
  private static final String xformAES = "AES/ECB/PKCS5Padding";
  private static final String ENCODED_KEYBYTES = "K9QgWmJVceZL+sv5i8U5Fw==";
  protected Cipher ecipher;
  protected Cipher dcipher;
  protected Key key = null;
  private String algname = null;
  private String xform = null;
  private byte[] encodedAlgParams = null;

  public DesEncrypter()
  {
    this(null);
  }

  public DesEncrypter(Key key) {
    this(key, "AES", "AES/ECB/PKCS5Padding");
  }

  public DesEncrypter(String algname, String xform) {
    this(null, algname, xform);
  }

  public DesEncrypter(Key key, String algname, String xform) {
    this.key = key;
    this.algname = algname;
    this.xform = xform;

    init();
  }

  public void init()
  {
    try {
      this.ecipher = Cipher.getInstance(this.xform);

      this.dcipher = Cipher.getInstance(this.xform);
    }
    catch (NoSuchPaddingException ex) {
      System.out.println("init NoSuchPaddingException: " + ex);
    } catch (NoSuchAlgorithmException ex) {
      System.out.println("init NoSuchAlgorithmException: " + ex);
    }
  }

  public byte[] encrypt_dk(String str)
  {
    return encrypt(str, getKey_dk());
  }

  public String encrypt_str(String str)
  {
    return byteToStringBase64(encrypt(str, getKey_dk()));
  }

  public byte[] encrypt(String str) {
    if (this.key == null) {
      return null;
    }

    return encrypt(str, this.key);
  }

  public byte[] encrypt(String str, Key key)
  {
    if (key == null) {
      return null;
    }

    try
    {
      this.ecipher.init(1, key);

      byte[] data = str.getBytes();

      byte[] enc = this.ecipher.doFinal(data);

      return enc;
    } catch (BadPaddingException ex) {
      System.out.println("encrypt BadPaddingException: " + ex);
    } catch (IllegalBlockSizeException ex) {
      System.out.println("encrypt IllegalBlockSizeException: " + ex);
    } catch (InvalidKeyException ex) {
      System.out.println("encrypt InvalidKeyException: " + ex);
    }

    return null;
  }

  public String decrypt_str(String encriptedStr)
  {
    byte[] keybytes = stringToBytesBase64(encriptedStr);

    return byteToStringBase64(decrypt(keybytes, getKey_dk()));
  }

  public String decrypt_utf8(String encriptedStr) {
    byte[] keybytes = stringToBytesBase64(encriptedStr);
    return byteToStringutf8(decrypt(keybytes, getKey_dk()));
  }

  public byte[] decrypt_dk(byte[] wrappedKey) {
    System.out.println("DesEncrypter (decrypt) byteToStringutf8 = " + byteToStringutf8(decrypt(wrappedKey, getKey_dk())));
    return decrypt(wrappedKey, getKey_dk());
  }

  public byte[] decrypt(byte[] wrappedKey) {
    return decrypt(wrappedKey, this.key);
  }

  public byte[] decrypt(byte[] wrappedKey, Key key) {
    if (key == null) {
      return null;
    }
    try
    {
      this.dcipher.init(2, key);

      return this.dcipher.doFinal(wrappedKey);
    }
    catch (BadPaddingException ex) {
      System.out.println("decrypt BadPaddingException: " + ex);
    } catch (IllegalBlockSizeException ex) {
      System.out.println("decrypt IllegalBlockSizeException: " + ex);
    } catch (InvalidKeyException ex) {
      System.out.println("decrypt InvalidKeyException: " + ex);
    }

    return null;
  }

  public Key genKey()
  {
    try
    {
      this.key = KeyGenerator.getInstance(this.algname).generateKey();
    }
    catch (NoSuchAlgorithmException ex)
    {
      System.out.println("getKey() NoSuchAlgorithmException: " + ex);
      this.key = null;
    }

    System.out.println("leaving genKey() key = " + this.key);
    return this.key;
  }

  public byte[] genKeyData()
  {
    try
    {
      this.key = KeyGenerator.getInstance(this.algname).generateKey();

      return this.key.getEncoded();
    } catch (NoSuchAlgorithmException ex) {
      System.out.println("genKeyData() NoSuchAlgorithmException: " + ex);
      this.key = null;
    }

    return null;
  }

  public Key getKey_dk()
  {
    return getKey(stringToBytesBase64(getENCODED_KEYBYTES()));
  }

  public Key getKey() {
    return this.key;
  }

  public Key getKey(byte[] keydata)
  {
    SecretKeySpec skeySpec = new SecretKeySpec(keydata, this.algname);

    return skeySpec;
  }

  public byte[] getKeyData_dk() {
    return stringToBytesBase64(getENCODED_KEYBYTES());
  }

  public byte[] getKeyData() {
    return this.key.getEncoded();
  }

  public void setKey(Key key) {
    this.key = key;
  }

  public void setKey(byte[] wrappedKey)
  {
    this.key = unWrapKey(wrappedKey);
  }

  public byte[] wrapKey()
  {
    try
    {
      genKey();

      this.ecipher.init(3, this.key);

      byte[] bytekey = this.ecipher.wrap(this.key);

      return bytekey;
    }
    catch (InvalidKeyException ex) {
      System.out.println("wrapKey InvalidKeyException: " + ex);
    } catch (IllegalBlockSizeException ex) {
      System.out.println("wrapKey IllegalBlockSizeException: " + ex);
    }

    return null;
  }

  public Key unWrapKey(byte[] wrappedKey)
  {
    if (wrappedKey != null) {
      System.out.println("unWrapKey wrappedKey.length " + wrappedKey.length);
    }

    try
    {
      this.ecipher.init(4, (Key)null);

      return this.ecipher.unwrap(wrappedKey, getAlgname(), 3);
    } catch (Exception ex) {
      System.out.println("unWrapKey Exception: " + ex);
    }

    return null;
  }

  public static String byteToStringBase64(byte[] wrappedKey) {
    if (wrappedKey == null) {
      return null;
    }

    try
    {
      return new BASE64Encoder().encode(wrappedKey);
    } catch (Exception e) {
      System.out.println("DesEncrypter:byteToStringBase64() Exception: " + e);
    }

    return null;
  }

  public static byte[] stringToBytesBase64(String encodedStr) {
    if (encodedStr == null) {
      return null;
    }

    try
    {
      return new BASE64Decoder().decodeBuffer(encodedStr);
    } catch (Exception e) {
      System.out.println("DesEncrypter:stringToBytesBase64() IOException: " + e);
    }

    return null;
  }

  public static String byteToStringutf8(byte[] wrappedKey) {
    if (wrappedKey == null) {
      return null;
    }
    try
    {
      return new String(wrappedKey, "UTF8");
    } catch (UnsupportedEncodingException ex) {
      System.out.println("DesEncrypter byteToStringutf8 UnsupportedEncodingException: " + ex);
    }

    return null;
  }

  public static byte[] stringToBytesutf8(String encodedStr) {
    if (encodedStr == null) {
      return null;
    }
    try
    {
      return encodedStr.getBytes("UTF8");
    } catch (UnsupportedEncodingException ex) {
      System.out.println("DesEncrypter byteToStringutf8 UnsupportedEncodingException: " + ex);
    }

    return null;
  }

  public static String decryptString(DesEncrypter encrypter, String str, String authkey) {
    String decryptstr = null;

    if ((str != null) && (authkey != null))
    {
      byte[] decrypteddata = encrypter.decrypt(stringToBytesBase64(str), encrypter.getKey(stringToBytesBase64(authkey)));

      decryptstr = new String(decrypteddata);
    }

    return decryptstr;
  }

  public String getXform() {
    return this.xform;
  }

  public void setXform(String xform) {
    this.xform = xform;

    init();
  }

  public String getAlgname() {
    return this.algname;
  }

  public void setAlgname(String algname) {
    this.algname = algname;
  }

  public static String getENCODED_KEYBYTES() {
    return "K9QgWmJVceZL+sv5i8U5Fw==";
  }

  public void test1() {
    byte[] keybytes = genKeyData();
    System.out.println("DesEncrypter (main) (encrypter.genKeyData keybytes = " + keybytes);
    String keystring = byteToStringBase64(keybytes);
    System.out.println("DesEncrypter (main) DesEncrypter.byteToStringBase64 keystring = " + keystring);
  }

  public void test2() {
    byte[] keybytes = genKeyData();
    String keystring = byteToStringutf8(keybytes);
    System.out.println("DesEncrypter (main) DesEncrypter.byteToStringutf8 keystring = " + keystring);
    Key lkey = getKey(keybytes);
    System.out.println("DesEncrypter (main) (encrypter.getKey) lkey = " + lkey);
  }

  public void test_dk(String ourstr)
  {
    String keystring = getENCODED_KEYBYTES();
    System.out.println("DesEncrypter (main)DesEncrypter.getENCODED_KEYBYTES() keystring = " + keystring);
    byte[] keybytes = stringToBytesBase64(keystring);
    System.out.println("DesEncrypter (DesEncrypter.getENCODED_KEYBYTES) keybytes = " + keybytes);
    Key lkey = getKey(keybytes);
    System.out.println("DesEncrypter (main) (DesEncrypter.getENCODED_KEYBYTES) lkey = " + lkey);

    byte[] encrypteddata = encrypt(ourstr, this.key);
    String encryptedourstr = byteToStringBase64(encrypteddata);
    System.out.println("DesEncrypter (main) encryptedourstr = " + encryptedourstr);

    byte[] descriptkeybytes = stringToBytesBase64(keystring);
    System.out.println("DesEncrypter (DesEncrypter.stringToBytesBase64) descriptkeybytes = " + descriptkeybytes);

    Key descriptkey = getKey(descriptkeybytes);
    System.out.println("DesEncrypter (main) descriptkey = " + descriptkey);
    byte[] decrypteddata = decrypt(encrypteddata, descriptkey);
    String decryptedstringb64 = byteToStringBase64(decrypteddata);
    System.out.println("DesEncrypter (main) decryptedstringb64 = " + decryptedstringb64);
    String decryptedstringutf8 = byteToStringutf8(decrypteddata);
    System.out.println("DesEncrypter (main) decryptedstringutf8 = " + decryptedstringutf8);
    System.out.println("\n");
    System.out.println("DesEncrypter (main) DesEncrypter.byteToStringutf8 keystring = " + keystring);
    System.out.println("\n");
    System.out.println("DesEncrypter (main) keybytes = " + keybytes);
    System.out.println("DesEncrypter (main) descriptkeybytes = " + descriptkeybytes);
    System.out.println("\n");
    System.out.println("DesEncrypter (main) (encrypter.getKey) key = " + this.key);
    System.out.println("DesEncrypter (main) descriptkey = " + descriptkey);
  }

  public static void main(String[] argv) {
    System.out.println("entering DesEncrypter (main) ... ");
    try
    {
      String ourstr = "Don't tell anybody!";
      System.out.println("(main) ourstr = " + ourstr);

      DesEncrypter encrypter = new DesEncrypter();

      String encriptstr = encrypter.encrypt_str(ourstr);
      System.out.println("encriptstr = " + encriptstr);

      String descriptstr = encrypter.decrypt_str(encriptstr);

      String byteToStringutf8 = encrypter.decrypt_utf8(encriptstr);
      System.out.println("DesEncrypter (main) byteToStringutf8 = " + byteToStringutf8);
    } catch (Exception ex) {
      System.out.println("main Exception: " + ex);
    }
  }
}