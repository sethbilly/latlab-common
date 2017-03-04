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
import java.util.Arrays;

public class BASE64Encoder
{
  private int uByteToInt(byte b)
  {
    if (b >= 0) {
      return b;
    }

    return 256 + b;
  }

  public String encode(byte[] data)
  {
    int charCount = data.length * 4 / 3 + 4;

    StringBuffer result = new StringBuffer(charCount * 77 / 76);

    int byteArrayLength = data.length;
    int byteArrayIndex = 0;
    int byteTriplet = 0;
    while (byteArrayIndex < byteArrayLength - 2) {
      byteArrayIndex = processData(data, byteArrayIndex, result);
    }

    byteArrayIndex = checkIfWeHave1ByteLeftOver(byteArrayIndex, byteArrayLength, data, result);
    checkIfWeHaveTwoBytesLeftOver(byteArrayIndex, byteArrayLength, data, result);

    return result.toString();
  }

  private int processData(byte[] data, int index, StringBuffer result)
  {
    int byteTriplet = uByteToInt(data[(index++)]);

    byteTriplet <<= 8;
    byteTriplet |= uByteToInt(data[(index++)]);
    byteTriplet <<= 8;
    byteTriplet |= uByteToInt(data[(index++)]);

    byte b4 = (byte)(0x3F & byteTriplet);

    byteTriplet >>= 6;
    byte b3 = (byte)(0x3F & byteTriplet);
    byteTriplet >>= 6;
    byte b2 = (byte)(0x3F & byteTriplet);
    byteTriplet >>= 6;
    byte b1 = (byte)(0x3F & byteTriplet);

    result.append(byteToChar(b1));
    result.append(byteToChar(b2));
    result.append(byteToChar(b3));
    result.append(byteToChar(b4));

    if (index % 57 == 0) {
      result.append("\n");
    }
    return index;
  }

  private void checkIfWeHaveTwoBytesLeftOver(int index, int length, byte[] byteArray, StringBuffer sb)
  {
    if (index == length - 2)
    {
      int byteTriplet = twoBytesToInt(byteArray, index);

      byteTriplet <<= 2;

      byte b3 = (byte)(0x3F & byteTriplet);
      byteTriplet >>= 6;
      byte b2 = (byte)(0x3F & byteTriplet);
      byteTriplet >>= 6;
      byte b1 = (byte)(0x3F & byteTriplet);

      sb.append(byteToChar(b1));
      sb.append(byteToChar(b2));
      sb.append(byteToChar(b3));

      sb.append("=");
    }
  }

  private int checkIfWeHave1ByteLeftOver(int index, int length, byte[] byteArray, StringBuffer sb)
  {
    if (index == length - 1)
    {
      int byteTriplet = uByteToInt(byteArray[(index++)]);

      byteTriplet <<= 4;

      byte b2 = (byte)(0x3F & byteTriplet);
      byteTriplet >>= 6;
      byte b1 = (byte)(0x3F & byteTriplet);

      sb.append(byteToChar(b1));
      sb.append(byteToChar(b2));

      sb.append("==");
    }
    return index;
  }

  private int twoBytesToInt(byte[] data, int byteArrayIndex)
  {
    int byteTriplet = uByteToInt(data[(byteArrayIndex++)]);

    byteTriplet <<= 8;
    byteTriplet |= uByteToInt(data[(byteArrayIndex++)]);
    return byteTriplet;
  }

  private char byteToChar(byte b)
  {
    if (b < 26) {
      return (char)(65 + b);
    }

    if (b < 52) {
      return (char)(97 + (b - 26));
    }

    if (b < 62) {
      return (char)(48 + (b - 52));
    }

    if (b == 62) {
      return '+';
    }

    if (b == 63) {
      return '/';
    }

    throw new IllegalArgumentException("Byte " + new Integer(b) + " is not a valid Base64 value");
  }

  public static void main(String[] args)
    throws Exception
  {
    testCodec();
  }

  private static void testCodec()
  {
    for (int j = 0; j < 100; j++)
      test(new BASE64Encoder(), new BASE64Decoder());
  }

  private static void test(BASE64Encoder encoder, BASE64Decoder decoder)
  {
    byte[] test = new byte[(int)(100000.0D * Math.random())];
    for (int i = 0; i < test.length; i++) {
      test[i] = (byte)(int)(256.0D * Math.random());
    }

    String string = encoder.encode(test);
    byte[] result = decoder.decodeBuffer(string);

    if ((!Arrays.equals(test, result)) || (test.length != result.length))
      System.out.println("ARRAYS DO NOT MATCH!");
  }
}