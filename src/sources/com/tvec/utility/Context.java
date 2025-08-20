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

import java.util.HashMap;

/* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/Context.class */
public class Context extends HashMap<String, Object> {
  private static final long serialVersionUID = 1;

  public String getString(String key) {
    return (String) get(key);
  }

  public Integer getInteger(String key) {
    return (Integer) get(key);
  }

  public long getLong(String key) {
    String x = (String) get(key);
    if (x == null) {
      return 0L;
    }
    return Long.valueOf(x).longValue();
  }

  public int getInt(String key) {
    String x = (String) get(key);
    if (x == null) {
      return 0;
    }
    return Integer.valueOf(x).intValue();
  }

  public short getShort(String key) {
    String x = (String) get(key);
    if (x == null) {
      return (short) 0;
    }
    return Short.valueOf(x).shortValue();
  }

  public boolean getBoolean(String key) {
    String value = (String) get(key);
    return value != null && !value.equalsIgnoreCase("FALSE");
  }
}
