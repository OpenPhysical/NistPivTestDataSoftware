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

package com.tvec.utility.configuration.editor.edit_panel;

import com.tvec.utility.configuration.Configuration;
import com.tvec.utility.configuration.ConfigurationEntry;
import com.tvec.utility.configuration.FormatEntry;

/* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/configuration/editor/edit_panel/BaseRowData.class */
public class BaseRowData {
  Configuration configuration;
  ConfigurationEntry configEntry;
  FormatEntry formatEntry;

  public BaseRowData(
      Configuration configuration, ConfigurationEntry configEntry, FormatEntry formatEntry) {
    this.formatEntry = formatEntry;
    this.configuration = configuration;
    this.configEntry = configEntry;
  }

  public FormatEntry getFormatEntry() {
    return this.formatEntry;
  }

  public Configuration getConfiguration() {
    return this.configuration;
  }

  public ConfigurationEntry getConfigurationEntry() {
    return this.configEntry;
  }

  public String getText() {
    return this.configEntry.getValueString();
  }

  public void setText(String value) {
    this.configEntry.setValue(value);
  }
}
