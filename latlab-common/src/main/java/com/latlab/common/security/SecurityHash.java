/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.latlab.common.security;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Encoder;

public final class SecurityHash
{
    public static enum HashAlgorithm
    {
        MD5,SHA
    }


  private static SecurityHash instance;

  private SecurityHash()
  {
  }

  public synchronized String hash(String plaintext, HashAlgorithm hashAlgorithm)
  {
      if(plaintext == null)
          return "";
      
    MessageDigest md = null;
    try
    {
        switch(hashAlgorithm)
        {
            case MD5:
                md = MessageDigest.getInstance("MD5"); //step 2
        }
        
    }
    catch(NoSuchAlgorithmException e)
    {
        Logger.getLogger(SecurityHash.class.getName()).log(Level.SEVERE, e.toString());
      return plaintext;
    }

    try
    {
        System.out.println("original text is " + plaintext);
      md.update(plaintext.getBytes("UTF-8")); //step 3
    }
    catch(UnsupportedEncodingException e)
    {
      e.printStackTrace();
      return plaintext;
    }

    byte raw[] = md.digest(); //step 4
    String hash = (new BASE64Encoder()).encode(raw); //step 5
    return hash; //step 6
  }
  
  public synchronized String shaHash(String plaintext)
  {
      if(plaintext == null)
      {
          return null;
      }
      
    MessageDigest md = null;
    try
    {
      md = MessageDigest.getInstance("SHA"); //step 2

    }
    catch(NoSuchAlgorithmException e)
    {
      e.printStackTrace();
      return plaintext;
    }

    try
    {
      md.update(plaintext.getBytes("UTF-8")); //step 3
    }
    catch(UnsupportedEncodingException e)
    {
      e.printStackTrace();
      return plaintext;
    }

    byte raw[] = md.digest(); //step 4
    String hash = (new BASE64Encoder()).encode(raw); //step 5
    return hash; //step 6
  }

  public static synchronized SecurityHash getInstance() //step 1
  {
    if(instance == null)
    {
       instance = new SecurityHash();
    }
    return instance;
  }
  
  private static String aesEncoder(String password) {
    SecretKeySpec secretKey = null;
    try {
        Cipher desCipher = Cipher.getInstance("AES");
        desCipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] cleartext = password.getBytes();
        byte[] ciphertext = desCipher.doFinal(cleartext);

        String codedpassword = "";
        return codedpassword;
    } catch (Exception ex) {
        ex.printStackTrace();
        return password;
    }
}

private static String aesDecoder(String codedpassword) {
    SecretKeySpec secretKey = null;
    try {
        Cipher desCipher = Cipher.getInstance("AES");
        desCipher.init(Cipher.DECRYPT_MODE, secretKey);

        codedpassword = null;
//        byte[] ciphertext = decoder.decodeBuffer(codedpassword);

//        byte[] cleartext = desCipher.doFinal(ciphertext);
        byte[] cleartext = desCipher.doFinal(codedpassword.getBytes());
        String password = new String(cleartext);
        return password;
    } catch (Exception ex) {
        ex.printStackTrace();
        return codedpassword;
    }
}

private static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while(two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String MD5(String text){
        try
        {
            MessageDigest md;
            md = MessageDigest.getInstance("MD5");
            byte[] md5hash = new byte[32];
            md.update(text.getBytes("iso-8859-1"), 0, text.length());
            md5hash = md.digest();
            return convertToHex(md5hash);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex)
        {
            Logger.getLogger(SecurityHash.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    
    public static String simpleMD5(String password) 
    {
        String result = password;
        if (password != null) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5"); //or "SHA-1"
                md.update(password.getBytes());
                BigInteger hash = new BigInteger(1, md.digest());
                result = hash.toString(16);
                while (result.length() < 32) { //40 for SHA-1
                    result = "0" + result;
                }
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(SecurityHash.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            result = "<Password is NULL>";
        }
        return result;
    }
    
    public static void main(String [] args)
    {
        System.out.println(SecurityHash.getInstance().shaHash("precious"));
    }
    
}
