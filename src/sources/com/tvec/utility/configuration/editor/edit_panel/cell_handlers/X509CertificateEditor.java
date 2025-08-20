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

import com.tvec.utility.Crypto;
import com.tvec.utility.configuration.editor.edit_panel.BaseRowData;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.security.auth.x500.X500Principal;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/configuration/editor/edit_panel/cell_handlers/X509CertificateEditor.class */
public class X509CertificateEditor extends JPanel implements BaseHandler {
  private static final long serialVersionUID = 1;
  protected BaseRowData baseRowData;
  JButton jButtonSelectFile = new JButton();
  BorderLayout borderLayout1 = new BorderLayout();
  JTextField jTextFieldCertificate = new JTextField();
  X509Certificate certificate;

  public X509CertificateEditor() {
    try {
      jbInit();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void setDisplayText() {
    if (this.certificate != null) {
      X500Principal principal = this.certificate.getIssuerX500Principal();
      this.jTextFieldCertificate.setText(principal.toString());
    } else {
      this.jTextFieldCertificate.setText("");
    }
  }

  @Override // com.tvec.utility.configuration.editor.edit_panel.cell_handlers.BaseHandler
  public void setRowData(BaseRowData baseRowData) {
    this.baseRowData = baseRowData;
    try {
      this.certificate = Crypto.getCertificateFromString(baseRowData.getText());
      setDisplayText();
    } catch (CertificateException | NoSuchProviderException ex) {
      ex.printStackTrace();
      JOptionPane.showMessageDialog(
          null, "Error creating certifcate from configuration string.", ex.toString(), 2);
    }
  }

  @Override // com.tvec.utility.configuration.editor.edit_panel.cell_handlers.BaseHandler
  public String getValue() {
    String certificateString = "";
    if (this.certificate != null) {
      try {
        certificateString = Crypto.getCertificateString(this.certificate);
      } catch (CertificateEncodingException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(
            null, "Unable to export certificate as string.", ex.toString(), 2);
      }
    }
    return certificateString;
  }

  private void jbInit() throws Exception {
    this.jButtonSelectFile.setMinimumSize(new Dimension(11, 20));
    this.jButtonSelectFile.setPreferredSize(new Dimension(30, 20));
    this.jButtonSelectFile.setToolTipText("Click to open certificate chooser/viewer");
    this.jButtonSelectFile.setText("...");
    this.jButtonSelectFile.addActionListener(
        new X509CertificateEditor_jButtonSelectFile_actionAdapter(this));
    setLayout(this.borderLayout1);
    this.jTextFieldCertificate.setEditable(false);
    this.jTextFieldCertificate.setText("");
    setBorder(null);
    setMaximumSize(new Dimension(32768, 32768));
    setMinimumSize(new Dimension(141, 20));
    setPreferredSize(new Dimension(141, 30));
    add(this.jTextFieldCertificate, "Center");
    add(this.jButtonSelectFile, "East");
  }

  public void jButtonSelectFile_actionPerformed(ActionEvent e) {
    DialogX509CertificateEditor dialog = new DialogX509CertificateEditor(this.certificate);
    if (dialog.showDialog(this) == 0) {
      this.certificate = dialog.getCertificate();
      setDisplayText();
    }
  }

  public void jButtonDeleteCertificate_actionPerformed(ActionEvent e) {
    this.certificate = null;
    setDisplayText();
  }
}
