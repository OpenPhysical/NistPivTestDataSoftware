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

import java.io.File;
import javax.swing.filechooser.FileFilter;

/* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/configuration/ConfigurationFilter.class */
public class ConfigurationFilter extends FileFilter {
  public boolean accept(File f) {
    if (f.isDirectory()) {
      return true;
    }
    String extension = getExtension(f);
    return extension != null && extension.equals("xml");
  }

  public String getDescription() {
    return "Configuration Files";
  }

  public String getExtension(File f) {
    String ext = null;
    String s = f.getName();
    int i = s.lastIndexOf(46);
    if (i > 0 && i < s.length() - 1) {
      ext = s.substring(i + 1).toLowerCase();
    }
    return ext;
  }
}
