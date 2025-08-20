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
import com.tvec.utility.UByte;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

/* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/configuration/editor/edit_panel/cell_handlers/DialogX509CertificateEditor.class */
public class DialogX509CertificateEditor extends JDialog {
  private static final long serialVersionUID = 1;
  private X509Certificate certificate;
  public static final int OK_OPTION = 0;
  public static final int CANCEL_OPTION = 1;
  private int selectedOption;
  JTabbedPane jTabbedPaneMain;
  JPanel jPanelOperations;
  JEditorPane jEditorPaneInfo;
  JButton jButtonImport;
  JScrollPane jScrollPaneInfo;
  JPanel jPanelSubmit;
  BorderLayout borderLayout1;
  JButton jButtonOK;
  JButton jButtonCancel;
  JButton jButtonClear;
  TitledBorder titledBorder1;
  JScrollPane jScrollPaneTextArea;
  JTextArea jTextAreaByteString;
  JButton jButtonImportBytes;
  JLabel jLabelImport;
  JLabel jLabelClear;
  JButton jButtonImportCertificateString;
  JPanel jPanelImportButtons;
  JPanel jPanelFileImport;
  JPanel jPanelClear;
  JPanel jPanelStringImport;
  BorderLayout borderLayout2;
  BorderLayout borderLayout3;
  FlowLayout flowLayout1;
  JPanel jPanel1;
  GridLayout gridLayout1;

  public DialogX509CertificateEditor(String certificate)
      throws CertificateException, NoSuchProviderException {
    this(certificate.getBytes());
  }

  public DialogX509CertificateEditor(byte[] certificateBytes)
      throws CertificateException, NoSuchProviderException {
    this();
    if (certificateBytes.length > 0) {
      ByteArrayInputStream inStream = new ByteArrayInputStream(certificateBytes);
      try {
        CertificateFactory cf = CertificateFactory.getInstance("X.509", Crypto.CRYPTO_PROVIDER);
        setCertificate((X509Certificate) cf.generateCertificate(inStream));
      } catch (NoSuchProviderException ex) {
        ex.printStackTrace();
      } catch (CertificateException ex2) {
        ex2.printStackTrace();
        JOptionPane.showMessageDialog(
            null, "Error loading existing certificate.", ex2.toString(), 2);
      }
    }
  }

  public DialogX509CertificateEditor(X509Certificate certificate) {
    this();
    setCertificate(certificate);
  }

  public DialogX509CertificateEditor() {
    super((Frame) null, true);
    this.selectedOption = 1;
    this.jTabbedPaneMain = new JTabbedPane();
    this.jPanelOperations = new JPanel();
    this.jEditorPaneInfo = new JEditorPane();
    this.jButtonImport = new JButton();
    this.jScrollPaneInfo = new JScrollPane();
    this.jPanelSubmit = new JPanel();
    this.borderLayout1 = new BorderLayout();
    this.jButtonOK = new JButton();
    this.jButtonCancel = new JButton();
    this.jButtonClear = new JButton();
    this.titledBorder1 = new TitledBorder("");
    this.jScrollPaneTextArea = new JScrollPane();
    this.jTextAreaByteString = new JTextArea();
    this.jButtonImportBytes = new JButton();
    this.jLabelImport = new JLabel();
    this.jLabelClear = new JLabel();
    this.jButtonImportCertificateString = new JButton();
    this.jPanelImportButtons = new JPanel();
    this.jPanelFileImport = new JPanel();
    this.jPanelClear = new JPanel();
    this.jPanelStringImport = new JPanel();
    this.borderLayout2 = new BorderLayout();
    this.borderLayout3 = new BorderLayout();
    this.flowLayout1 = new FlowLayout();
    this.jPanel1 = new JPanel();
    this.gridLayout1 = new GridLayout();
    try {
      jbInit();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public void setCertificate(X509Certificate certificate) {
    this.certificate = certificate;
    if (certificate != null) {
      this.jEditorPaneInfo.setText(certificate.toString());
    } else {
      this.jEditorPaneInfo.setText("");
    }
  }

  public X509Certificate getCertificate() {
    return this.certificate;
  }

  private void jbInit() throws Exception {
    this.jButtonImport.setToolTipText("Import certificate from file");
    this.jButtonImport.setText("Import");
    this.jButtonImport.addActionListener(
        new DialogX509CertificateEditor_jButtonImport_actionAdapter(this));
    this.jPanelOperations.setToolTipText("");
    this.jPanelOperations.setLayout(this.borderLayout2);
    setTitle("X509 Certificate Editor");
    getContentPane().setLayout(this.borderLayout1);
    this.jTabbedPaneMain.setMinimumSize(new Dimension(200, 200));
    this.jTabbedPaneMain.setPreferredSize(new Dimension(200, 300));
    this.jEditorPaneInfo.setEditable(false);
    this.jEditorPaneInfo.setText("Info");
    this.jButtonOK.setText("OK");
    this.jButtonOK.addActionListener(new DialogX509CertificateEditor_jButtonOK_actionAdapter(this));
    this.jButtonCancel.setText("Cancel");
    this.jButtonCancel.addActionListener(
        new DialogX509CertificateEditor_jButtonCancel_actionAdapter(this));
    this.jButtonClear.setToolTipText("Clear certificate data");
    this.jButtonClear.setText("Clear");
    this.jButtonClear.addActionListener(
        new DialogX509CertificateEditor_jButtonClear_actionAdapter(this));
    this.jTextAreaByteString.setBorder(this.titledBorder1);
    this.jTextAreaByteString.setMinimumSize(new Dimension(300, 300));
    this.jTextAreaByteString.setText("");
    this.jTextAreaByteString.setLineWrap(true);
    this.jButtonImportBytes.setToolTipText("Imports the certificate specified by the hex string");
    this.jButtonImportBytes.setText("Import Byte String");
    this.jButtonImportBytes.addActionListener(
        new DialogX509CertificateEditor_jButtonImportBytes_actionAdapter(this));
    this.jLabelImport.setText("Import Certificate from File:");
    this.jLabelClear.setText("Clear existing certificate data:");
    this.jScrollPaneTextArea.setMinimumSize(new Dimension(300, 300));
    this.jButtonImportCertificateString.setText("Import Certificate String");
    this.jButtonImportCertificateString.addActionListener(
        new DialogX509CertificateEditor_jButtonImportCertificateString_actionAdapter(this));
    this.jPanelImportButtons.setLayout(this.flowLayout1);
    this.jPanelStringImport.setLayout(this.borderLayout3);
    this.jPanelImportButtons.setMaximumSize(new Dimension(150, 150));
    this.jPanel1.setLayout(this.gridLayout1);
    this.gridLayout1.setRows(2);
    this.jScrollPaneInfo.getViewport().add(this.jEditorPaneInfo);
    getContentPane().add(this.jPanelSubmit, "South");
    this.jPanelSubmit.add(this.jButtonOK);
    this.jPanelSubmit.add(this.jButtonCancel);
    this.jTabbedPaneMain.add(this.jPanelOperations, "Operations");
    this.jTabbedPaneMain.add(this.jScrollPaneInfo, "Info");
    this.jPanelClear.add(this.jLabelClear);
    this.jPanelClear.add(this.jButtonClear);
    this.jScrollPaneTextArea.getViewport().add(this.jTextAreaByteString);
    this.jPanelFileImport.add(this.jLabelImport);
    this.jPanelFileImport.add(this.jButtonImport);
    getContentPane().add(this.jTabbedPaneMain, "Center");
    this.jPanelOperations.add(this.jPanelClear, "South");
    this.jPanelOperations.add(this.jPanelStringImport, "Center");
    this.jPanelOperations.add(this.jPanelFileImport, "North");
    this.jPanelStringImport.add(this.jScrollPaneTextArea, "Center");
    this.jPanelStringImport.add(this.jPanelImportButtons, "East");
    this.jPanelImportButtons.add(this.jPanel1);
    this.jPanel1.add(this.jButtonImportCertificateString);
    this.jPanel1.add(this.jButtonImportBytes);
    setSize(400, 400);
  }

  public void jButtonImport_actionPerformed(ActionEvent e)
      throws NoSuchAlgorithmException,
          SignatureException,
          InvalidKeyException,
          IOException,
          CertificateException,
          NoSuchProviderException {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Select Directory");
    fileChooser.setApproveButtonText("Select");
    fileChooser.setFileSelectionMode(0);
    if (fileChooser.showOpenDialog(this) == 0) {
      File selectedFile = fileChooser.getSelectedFile();
      FileInputStream inStream = null;
      try {
        inStream = new FileInputStream(selectedFile.getAbsolutePath());
        CertificateFactory cf = CertificateFactory.getInstance("X.509", Crypto.CRYPTO_PROVIDER);
        this.certificate = (X509Certificate) cf.generateCertificate(inStream);
        setCertificate(this.certificate);
        try {
          Signature sig =
              Signature.getInstance(this.certificate.getSigAlgName(), Crypto.CRYPTO_PROVIDER);
          sig.initVerify(this.certificate);
          sig.verify(this.certificate.getSignature());
        } catch (SignatureException ex) {
          JOptionPane.showMessageDialog(
              null, "Loaded certificate but could not verify signature.", ex.toString(), 2);
        }
      } catch (Exception ex2) {
        ex2.printStackTrace();
        JOptionPane.showMessageDialog(
            null, "Error occured importing certificate.", ex2.toString(), 2);
      }
      if (inStream != null) {
        try {
          inStream.close();
        } catch (IOException e2) {
        }
      }
    }
  }

  public int showDialog(Component parent) {
    setLocationRelativeTo(parent);
    setVisible(true);
    return this.selectedOption;
  }

  public void jButtonOK_actionPerformed(ActionEvent e) {
    this.selectedOption = 0;
    super.setVisible(false);
  }

  public void jButtonCancel_actionPerformed(ActionEvent e) {
    this.selectedOption = 1;
    super.setVisible(false);
  }

  public void jButtonClear_actionPerformed(ActionEvent e) {
    setCertificate(null);
  }

  public void jButtonImportBytes_actionPerformed(ActionEvent e)
      throws NoSuchAlgorithmException,
          SignatureException,
          InvalidKeyException,
          CertificateException,
          NoSuchProviderException {
    byte[] data = UByte.toSignedByteArray(this.jTextAreaByteString.getText());
    try {
      ByteArrayInputStream inStream = new ByteArrayInputStream(data);
      CertificateFactory cf = CertificateFactory.getInstance("X.509", Crypto.CRYPTO_PROVIDER);
      this.certificate = (X509Certificate) cf.generateCertificate(inStream);
      setCertificate(this.certificate);
      try {
        Signature sig =
            Signature.getInstance(this.certificate.getSigAlgName(), Crypto.CRYPTO_PROVIDER);
        sig.initVerify(this.certificate);
        sig.verify(this.certificate.getSignature());
      } catch (SignatureException ex) {
        JOptionPane.showMessageDialog(
            null, "Loaded certificate but could not verify signature.", ex.toString(), 2);
      }
    } catch (Exception ex2) {
      ex2.printStackTrace();
      JOptionPane.showMessageDialog(
          null, "Error occured importing certificate.", ex2.toString(), 2);
    }
  }

  public void jButtonImportCertificateString_actionPerformed(ActionEvent e)
      throws NoSuchAlgorithmException,
          SignatureException,
          InvalidKeyException,
          CertificateException,
          NoSuchProviderException {
    try {
      ByteArrayInputStream inStream =
          new ByteArrayInputStream(this.jTextAreaByteString.getText().getBytes());
      CertificateFactory cf = CertificateFactory.getInstance("X.509", Crypto.CRYPTO_PROVIDER);
      this.certificate = (X509Certificate) cf.generateCertificate(inStream);
      setCertificate(this.certificate);
      try {
        Signature sig =
            Signature.getInstance(this.certificate.getSigAlgName(), Crypto.CRYPTO_PROVIDER);
        sig.initVerify(this.certificate);
        sig.verify(this.certificate.getSignature());
      } catch (SignatureException ex) {
        JOptionPane.showMessageDialog(
            null, "Loaded certificate but could not verify signature.", ex.toString(), 2);
      }
    } catch (Exception ex2) {
      ex2.printStackTrace();
      JOptionPane.showMessageDialog(
          null, "Error occured importing certificate.", ex2.toString(), 2);
    }
  }
}
