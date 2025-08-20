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

package com.tvec.utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/UByte.class */
public enum UByte {
  ;
  private static final Logger logger = LogManager.getLogger(UByte.class);

  public static void main(String[] args) {
    byte[] test = toSignedByteArray("0xA000000116");
    logger.debug("Test 1: {}", toUnsignedHexString(test));
    byte[] test2 = toSignedByteArray(11259137, 3);
    logger.debug("Test 2: {}", toUnsignedHexString(test2));
    logger.debug("Hex 1: {}", Integer.toHexString(1));
    logger.debug("Hex 15: {}", Integer.toHexString(15));
    logger.debug("Hex 16: {}", Integer.toHexString(16));
    logger.debug("Hex 17: {}", Integer.toHexString(17));
    byte[] t = toSignedByteArray("f8ffaa01");
    logger.debug("Test 3: {}", toUnsignedHexString(t));
    logger.debug("Test 4: {}", Integer.toHexString(toUnsigned(t[0])));
    logger.debug("Signed byte 255: {}", (int) toSignedByte(255));
    logger.debug("Signed byte 128: {}", (int) toSignedByte(128));
    logger.debug("Signed byte 127: {}", (int) toSignedByte(127));
    logger.debug("Signed byte 126: {}", (int) toSignedByte(126));
    byte tb = toSignedByte(255);
    logger.debug("Test byte: {}", (int) tb);
    logger.debug("Unsigned test byte: {}", (int) toUnsigned(tb));
    byte[] t2 = toSignedByteArray(-268435457, 4);
    logger.debug("-268435457");
    logger.debug("Unsigned: {}", toUnsigned(t2));
    logger.debug("Hex string: {}", toUnsignedHexString(t2));
  }

  public static int toUnsigned(byte[] b) {
    return toUnsigned(b, 0, 4);
  }

  public static int toUnsigned(byte[] b, int begin, int end) {
    int out = 0;
    for (int i = begin; i < b.length && i < end && i < begin + 4; i++) {
      out = (out << 8) | toUnsigned(b[i]);
    }
    return out;
  }

  public static long toUnsignedLong(byte[] b, int begin, int end) {
    int out = 0;
    for (int i = begin; i < b.length && i < end && i < begin + 8; i++) {
      out = (out << 8) | toUnsigned(b[i]);
    }
    return out;
  }

  public static int toUnsigned(byte hi, byte lo) {
    int out = (toUnsigned(hi) << 8) | toUnsigned(lo);
    return out;
  }

  public static short[] toUnsignedShortArray(byte[] b) {
    short[] out = new short[b.length];
    for (int i = 0; i < b.length; i++) {
      out[i] = toUnsigned(b[i]);
    }
    return out;
  }

  public static byte toSignedByte(int in) {
    if (in < 0 || in > 255) {
      throw new NumberFormatException(
          "Value " + in + " not valid for conversion to signed byte must be between 0-255");
    }
    return (byte) in;
  }

  public static short toUnsigned(byte in) {
    return (short) (in & 255);
  }

  public static byte[] toSignedByteArray(String in) {
    String in2 = in.toLowerCase();
    if (in2.startsWith("0x")) {
      in2 = in2.substring(2);
    }
    if (in2.length() % 2 > 0) {
      in2 = "0" + in2;
    }
    byte[] out = new byte[in2.length() / 2];
    for (int i = 0; i < out.length; i++) {
      out[i] = toSignedByte(Integer.parseInt(in2.substring(i * 2, (i * 2) + 2), 16));
    }
    return out;
  }

  public static String toUnsignedHexString(byte in) {
    byte[] t = {in};
    return toUnsignedHexString(t);
  }

  public static String toUnsignedHexString(byte[] in) {
    return toUnsignedHexString(in, "");
  }

  public static String toUnsignedHexString(byte[] in, String seperator) {
    StringBuffer sb = new StringBuffer(in.length);
    for (byte b : in) {
      if (sb.length() > 0) {
        sb.append(seperator);
      }
      String t = Integer.toHexString(toUnsigned(b));
      if (t.length() < 2) {
        sb.append("0");
      }
      sb.append(t);
    }
    return sb.toString();
  }

  public static byte[] toSignedByteArray(char[] in) {
    byte[] ret = new byte[in.length];
    for (int i = 0; i < in.length; i++) {
      ret[i] = toSignedByte(in[i]);
    }
    return ret;
  }

  public static byte[] toSignedByteArray(int in) {
    return toSignedByteArray(in, 2);
  }

  public static byte[] toSignedByteArray(int in, int length) {
    byte[] out = new byte[length];
    for (int i = 0; i < length; i++) {
      int cur = (in >> (i * 8)) & 255;
      out[(length - i) - 1] = toSignedByte(cur);
    }
    return out;
  }
}
