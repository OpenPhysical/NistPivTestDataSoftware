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

package com.tvec.utility.configuration;

import java.awt.Component;

/* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/configuration/ConfigurationMessage.class */
public class ConfigurationMessage {
  public static final int ACTION_UPDATE = 1;
  public static final int ACTION_RELOAD = 2;
  public static final int ACTION_EDIT_ENTRY = 3;
  private String key;
  private final int action;
  private Component parent;

  public ConfigurationMessage(String key, int action) {
    this.key = key;
    this.action = action;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public void setParent(Component parent) {
    this.parent = parent;
  }

  public Component getParent() {
    return this.parent;
  }

  public String getKey() {
    return this.key;
  }

  public int getAction() {
    return this.action;
  }
}
