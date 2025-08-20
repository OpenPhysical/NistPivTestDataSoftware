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
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.CertificateException;

/* compiled from: DialogX509CertificateEditor.java */
/* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/configuration/editor/edit_panel/cell_handlers/DialogX509CertificateEditor_jButtonImport_actionAdapter.class */
class DialogX509CertificateEditor_jButtonImport_actionAdapter implements ActionListener {
  private final DialogX509CertificateEditor adaptee;

  DialogX509CertificateEditor_jButtonImport_actionAdapter(DialogX509CertificateEditor adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    try {
      this.adaptee.jButtonImport_actionPerformed(e);
    } catch (NoSuchAlgorithmException
        | SignatureException
        | InvalidKeyException
        | IOException
        | CertificateException
        | NoSuchProviderException ex) {
      ex.printStackTrace();
    }
  }
}
