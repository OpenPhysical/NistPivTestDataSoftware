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

/* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/ArrayUtils.class */
public enum ArrayUtils {
  ;

  public static byte[] appendBytes(byte[] b1, byte b2) {
    return appendBytes(b1, new byte[] {b2});
  }

  public static byte[] appendBytes(byte[] b1, byte[] b2) {
    byte[] b = new byte[b1.length + b2.length];
    System.arraycopy(b1, 0, b, 0, b1.length);
    System.arraycopy(b2, 0, b, b1.length, b2.length);
    return b;
  }
}
