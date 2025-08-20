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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/* compiled from: DialogKeyCryptographicMechanismListEditor.java */
/* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/configuration/editor/edit_panel/cell_handlers/DialogKeyCryptographicMechanismListEditor_jButtonOK_actionAdapter.class */
class DialogKeyCryptographicMechanismListEditor_jButtonOK_actionAdapter implements ActionListener {
  private final DialogKeyCryptographicMechanismListEditor adaptee;

  DialogKeyCryptographicMechanismListEditor_jButtonOK_actionAdapter(
      DialogKeyCryptographicMechanismListEditor adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    this.adaptee.jButtonOK_actionPerformed(e);
  }
}
