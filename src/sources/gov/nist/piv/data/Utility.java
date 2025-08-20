/*
 * Portions of this software were developed by the National Institute of Standards and Technology (NIST)
 * in the course of official duties. Under 17 U.S.C. §105, such works are not subject to copyright in
 * the United States.
 *
 * All other portions were developed by the OpenPhysical Foundation and/or project contributors,
 * and are dedicated to the public domain.
 *
 * See LICENSE.md for full terms.
 *
 * This software is provided "as is", without warranty of any kind, including implied warranties of
 * merchantability or fitness for a particular purpose.
 */

package gov.nist.piv.data;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1OutputStream;
import org.bouncycastle.asn1.ASN1Primitive;

/* loaded from: JPIV Test Data Generator.jar:gov/nist/piv/data/Utility.class */
public enum Utility {
  ;
  public static final String DEFAULT_HASH_ALG = "SHA-1";

  public static byte[] generateTLV(int tag, String v) {
    String tl = Integer.toHexString(tag);
    if (Math.abs(tag) <= 16777215 && tag < 0) {
      tl = tl.substring(2);
    }
    if (Math.abs(tag) <= 65535 && tag < 0) {
      tl = tl.substring(2);
    }
    if (Math.abs(tag) <= 255 && tag < 0) {
      tl = tl.substring(2);
    }
    String tl2 = (tl.length() % 2 == 1 ? "0" : "") + tl + generateBERLength(v.length());
    byte[] b = new byte[(tl2.length() / 2) + v.length()];
    for (int i = 0; i < tl2.length(); i += 2) {
      String s = tl2.substring(i, i + 2);
      b[i / 2] = (byte) Integer.parseInt(s, 16);
    }
    for (int i2 = 0; i2 < v.length(); i2++) {
      b[(tl2.length() / 2) + i2] = (byte) v.charAt(i2);
    }
    return b;
  }

  public static byte[] generateTLV(int tag, byte[] v) {
    String tl = Integer.toHexString(tag);
    if (Math.abs(tag) <= 16777215 && tag < 0) {
      tl = tl.substring(2);
    }
    if (Math.abs(tag) <= 65535 && tag < 0) {
      tl = tl.substring(2);
    }
    if (Math.abs(tag) <= 255 && tag < 0) {
      tl = tl.substring(2);
    }
    String tl2 = (tl.length() % 2 == 1 ? "0" : "") + tl + generateBERLength(v.length);
    byte[] b = new byte[(tl2.length() / 2) + v.length];
    for (int i = 0; i < tl2.length(); i += 2) {
      String s = tl2.substring(i, i + 2);
      b[i / 2] = (byte) Integer.parseInt(s, 16);
    }
    System.arraycopy(v, 0, b, (tl2.length() / 2), v.length);
    return b;
  }

  public static byte[] appendBytes(byte[] b1, byte[] b2) {
    byte[] b = new byte[b1.length + b2.length];
    System.arraycopy(b1, 0, b, 0, b1.length);
    System.arraycopy(b2, 0, b, b1.length, b2.length);
    return b;
  }

  public static String generateBERLength(int len) {
    if (len < 128) {
      String s = Integer.toHexString(len);
      return (s.length() == 1 ? "0" : "") + s;
    }
    String s2 = Integer.toHexString(len);
    String s3 = (s2.length() % 2 == 1 ? "0" : "") + s2;
    return Integer.toHexString(128 + (s3.length() / 2)) + s3;
  }

  public static byte[] BERtoDER(byte[] bin) throws Exception {
    try {
      ASN1InputStream aIn = new ASN1InputStream(new ByteArrayInputStream(bin));
      ASN1Primitive aASN1Primitive = aIn.readObject();
      ByteArrayOutputStream aOutStream = new ByteArrayOutputStream();
      ASN1OutputStream aDEROutStream = ASN1OutputStream.create(aOutStream, "DER");
      aDEROutStream.writeObject(aASN1Primitive);
      aOutStream.close();
      return aOutStream.toByteArray();
    } catch (Exception e) {
      throw e;
    }
  }

  public static String ascii2BcdBitString(char digit) {
    switch (digit) {
      case CHUID.FASCN_TAG /* 48 */:
        return "00001";
      case '1':
        return "10000";
      case '2':
        return "01000";
      case '3':
        return "11001";
      case CHUID.GUID_TAG /* 52 */:
        return "00100";
      case CHUID.EXPIRATION_DATE_TAG /* 53 */:
        return "10101";
      case '6':
        return "01101";
      case '7':
        return "11100";
      case '8':
        return "00010";
      case '9':
        return "10011";
      case 'E':
        return "11111";
      case 'F':
        return "10110";
      case 'S':
        return "11010";
      default:
        return "";
    }
  }

  public static char bcdBitString2ascii(String in) {
    if (in.equals("00001")) {
      return '0';
    }
    if (in.equals("10000")) {
      return '1';
    }
    if (in.equals("01000")) {
      return '2';
    }
    if (in.equals("11001")) {
      return '3';
    }
    if (in.equals("00100")) {
      return '4';
    }
    if (in.equals("10101")) {
      return '5';
    }
    if (in.equals("01101")) {
      return '6';
    }
    if (in.equals("11100")) {
      return '7';
    }
    if (in.equals("00010")) {
      return '8';
    }
    if (in.equals("10011")) {
      return '9';
    }
    if (in.equals("11010")) {
      return 'S';
    }
    if (in.equals("10110")) {
      return 'F';
    }
    if (in.equals("11111")) {
      return 'E';
    }
    return '\n';
  }

  public static String stripWhiteSpace(String s) {
    return s.replaceAll("\t", "").replaceAll("\n", "").replaceAll(" ", "");
  }

  public static byte[] hexString2ByteArray(String s) {
    String s2 = stripWhiteSpace(s);
    if (s2.length() % 2 == 1) {
      s2 = "0" + s2;
    }
    byte[] ret = new byte[s2.length() / 2];
    for (int i = 0; i < s2.length(); i += 2) {
      ret[i / 2] = (byte) Integer.parseInt(s2.substring(i, i + 2), 16);
    }
    return ret;
  }

  public static String byteArray2HexString(byte[] array) {
    return byteArray2HexString(array, " ");
  }

  public static String byteArray2HexString(byte[] array, String seperator) {
    if (array == null || array.length <= 0) {
      return "";
    }
    String[] nibbles = {
      "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"
    };
    String ret = "";
    for (int i = 0; i < array.length; i++) {
      ret = ret + nibbles[(array[i] & 240) >>> 4] + nibbles[array[i] & 15];
      if (i != array.length - 1) {
        ret = ret + seperator;
      }
    }
    return ret;
  }

  public static byte[] generateHash(byte[] b) {
    try {
      return generateHash(b, "SHA-1");
    } catch (NoSuchAlgorithmException e) {
      return null;
    }
  }

  public static byte[] generateHash(byte[] b, String hashAlg) throws NoSuchAlgorithmException {
    try {
      return MessageDigest.getInstance(hashAlg).digest(b);
    } catch (NoSuchAlgorithmException e) {
      throw e;
    }
  }

  public static byte calculateLRC(byte[] b) {
    if (b.length == 0) {
      return (byte) 0;
    }
    byte lrc = b[0];
    for (int i = 1; i < b.length; i++) {
      lrc = (byte) (lrc ^ b[i]);
    }
    return lrc;
  }
}
