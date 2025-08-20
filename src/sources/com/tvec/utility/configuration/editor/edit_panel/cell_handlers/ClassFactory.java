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

package com.tvec.utility.configuration.editor.edit_panel.cell_handlers;

import com.tvec.utility.configuration.FormatEntry;
import com.tvec.utility.configuration.editor.edit_panel.BaseRowData;
import java.awt.Component;

/* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/configuration/editor/edit_panel/cell_handlers/ClassFactory.class */
public enum ClassFactory {
  ;

  public static final Component getComponent(BaseRowData baseRowData) {
    BaseHandler defaultEditor;
    String type = baseRowData.getFormatEntry().getType().toLowerCase();
    if (type.equals(FormatEntry.TYPE_STRING_HEX)) {
      defaultEditor = new HexStringEditor();
    } else if (type.equals(FormatEntry.TYPE_STRING_INT)) {
      defaultEditor = new IntStringEditor();
    } else if (type.equals(FormatEntry.TYPE_STRING_DIRECTORY)) {
      defaultEditor = new DirectoryEditor();
    } else if (type.equals(FormatEntry.TYPE_STRING_FILE)) {
      defaultEditor = new FileEditor();
    } else if (type.equals(FormatEntry.TYPE_MULTI_SELECT_STRING)) {
      defaultEditor = new MultiSelectListEditor();
    } else if (type.equals(FormatEntry.TYPE_X509_CERTIFICATE)) {
      defaultEditor = new X509CertificateEditor();
    } else if (type.equals(FormatEntry.TYPE_PIV_KEY_ALGORITHM_LIST)) {
      defaultEditor = new KeyAlgorithmListEditor();
    } else if (type.equals(FormatEntry.TYPE_PIV_KEY_CRYPTOGRAPHIC_MECHANISM_LIST)) {
      defaultEditor = new KeyCryptographicMechanismListEditor();
    } else {
      defaultEditor = new DefaultEditor();
    }
    if (defaultEditor != null) {
      defaultEditor.setRowData(baseRowData);
    }
    return (Component) defaultEditor;
  }
}
