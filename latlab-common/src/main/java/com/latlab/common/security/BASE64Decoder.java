/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.latlab.common.security;

/**
 *
 * @author Edwin
 */
public class BASE64Decoder
{
  private static final int EIGHT_BIT_MASK = 255;

  public byte[] decodeBuffer(String data)
  {
    StringWrapper wrapper = new StringWrapper(data);

    int length = wrapper.getUsefulLength() * 3 / 4;

    byte[] byteArray = new byte[length];

    int byteTriplet = 0;
    int index = 0;

    while (index + 2 < length)
    {
      index = processByteArray(wrapper, byteArray, index);
    }

    checkIfWeHave1ByteLeft(index, length, wrapper, byteArray);

    checkIfWeHave2BytesLeft(index, length, wrapper, byteArray);

    return byteArray;
  }

  private void checkIfWeHave2BytesLeft(int index, int length, StringWrapper wrapper, byte[] byteArray)
  {
    if (index == length - 2)
    {
      int byteTriplet = charToInt(wrapper.getNextUsefulChar());
      byteTriplet <<= 6;
      byteTriplet |= charToInt(wrapper.getNextUsefulChar());
      byteTriplet <<= 6;
      byteTriplet |= charToInt(wrapper.getNextUsefulChar());

      byteTriplet >>= 2;
      byteArray[(index + 1)] = (byte)(byteTriplet & 0xFF);
      byteTriplet >>= 8;
      byteArray[index] = (byte)(byteTriplet & 0xFF);
    }
  }

  private void checkIfWeHave1ByteLeft(int byteIndex, int byteArrayLength, StringWrapper wrapper, byte[] result)
  {
    if (byteIndex == byteArrayLength - 1)
    {
      int byteTriplet = charToInt(wrapper.getNextUsefulChar());
      byteTriplet <<= 6;
      byteTriplet |= charToInt(wrapper.getNextUsefulChar());

      byteTriplet >>= 4;
      result[byteIndex] = (byte)(byteTriplet & 0xFF);
    }
  }

  private int processByteArray(StringWrapper wrapper, byte[] result, int byteIndex)
  {
    int byteTriplet = charToInt(wrapper.getNextUsefulChar());
    byteTriplet <<= 6;
    byteTriplet |= charToInt(wrapper.getNextUsefulChar());
    byteTriplet <<= 6;
    byteTriplet |= charToInt(wrapper.getNextUsefulChar());
    byteTriplet <<= 6;
    byteTriplet |= charToInt(wrapper.getNextUsefulChar());

    result[(byteIndex + 2)] = (byte)(byteTriplet & 0xFF);
    byteTriplet >>= 8;
    result[(byteIndex + 1)] = (byte)(byteTriplet & 0xFF);
    byteTriplet >>= 8;
    result[byteIndex] = (byte)(byteTriplet & 0xFF);
    byteIndex += 3;
    return byteIndex;
  }

  private int charToInt(char c)
  {
    if ((c >= 'A') && (c <= 'Z')) {
      return c - 'A';
    }

    if ((c >= 'a') && (c <= 'z')) {
      return c - 'a' + 26;
    }

    if ((c >= '0') && (c <= '9')) {
      return c - '0' + 52;
    }

    if (c == '+') {
      return 62;
    }

    if (c == '/') {
      return 63;
    }

    throw new IllegalArgumentException(c + " is not a valid Base64 character.");
  }

  private class StringWrapper
  {
    private String mString;
    private int mIndex = 0;
    private int mUsefulLength;

    private boolean isUsefulChar(char c)
    {
      return ((c >= 'A') && (c <= 'Z')) || ((c >= 'a') && (c <= 'z')) || ((c >= '0') && (c <= '9')) || (c == '+') || (c == '/');
    }

    public StringWrapper(String s)
    {
      this.mString = s;
      this.mUsefulLength = 0;
      int length = this.mString.length();
      for (int i = 0; i < length; i++)
        if (isUsefulChar(this.mString.charAt(i)))
          this.mUsefulLength += 1;
    }

    public int getUsefulLength()
    {
      return this.mUsefulLength;
    }

    public char getNextUsefulChar()
    {
      char result = '_';
      while (!isUsefulChar(result)) {
        result = this.mString.charAt(this.mIndex++);
      }

      return result;
    }
  }
}