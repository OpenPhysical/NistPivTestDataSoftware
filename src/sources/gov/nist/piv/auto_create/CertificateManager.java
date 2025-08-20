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

package gov.nist.piv.auto_create;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/* loaded from: JPIV Test Data Generator.jar:gov/nist/piv/auto_create/CertificateManager.class */
public class CertificateManager extends DataManager {
  private static final long serialVersionUID = 1;
  private JLabel jLabelHeader;
  private JLabel jLabelKeystoreType;
  private JTextField jTextFieldKeystoreType;
  private JLabel jLabelKeystorePath;
  private JLabel jLabelKeystorePassword;
  private JLabel jLabelCAAlias;
  private JLabel jLabelCAPrivateKeyPassword;
  private JLabel jLabelContentSignerAlias;
  private JLabel jLabelContentSignerPrivateKeyPassword;
  private JButton jButtonTest;
  private JPasswordField jPasswordFieldKeystore;
  private JPasswordField jPasswordFieldCAPrivateKey;
  private JPasswordField jPasswordFieldContentSignerPrivateKey;
  private JTextField jTextFieldCAAlias;
  private JTextField jTextFieldContentSignerAlias;
  private JPanel jPanelKeystorePath;
  private JTextField jTextFieldKeystorePath;
  private JButton jButtonChoosePath;
  public static final String ATTRIBUTE_HEADER = "header";
  public static final String ATTRIBUTE_IS_PASSWORD = "isPassword";
  public static final String TAG_KEYSTORE_TYPE = "KeyStoreType";
  public static final String TAG_KEYSTORE_PATH = "KeyStorePath";
  public static final String TAG_KEYSTORE_PASSWORD = "KeyStorePassword";
  public static final String TAG_CERTIFICATE_ALIAS = "CertificateAlias";
  public static final String TAG_PRIVATE_KEY_PASSWORD = "PrivateKeyPassword";
  public static final String TAG_CONTENT_SIGNER_ALIAS = "ContentSignerAlias";
  public static final String TAG_CONTENT_SIGNER_PRIVATE_KEY_PASSWORD =
      "ContentSignerPrivateKeyPassword";

  public CertificateManager(String header) {
    this();
    setHeader(header);
  }

  public CertificateManager() {
    this.jLabelHeader = null;
    this.jLabelKeystoreType = null;
    this.jTextFieldKeystoreType = null;
    this.jLabelKeystorePath = null;
    this.jLabelKeystorePassword = null;
    this.jLabelCAAlias = null;
    this.jLabelCAPrivateKeyPassword = null;
    this.jLabelContentSignerAlias = null;
    this.jLabelContentSignerPrivateKeyPassword = null;
    this.jButtonTest = null;
    this.jPasswordFieldKeystore = null;
    this.jPasswordFieldCAPrivateKey = null;
    this.jPasswordFieldContentSignerPrivateKey = null;
    this.jTextFieldCAAlias = null;
    this.jTextFieldContentSignerAlias = null;
    this.jPanelKeystorePath = null;
    this.jTextFieldKeystorePath = null;
    this.jButtonChoosePath = null;
    initialize();
  }

  private void initialize() {
    GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
    gridBagConstraints15.fill = 3;
    gridBagConstraints15.gridy = 7;
    gridBagConstraints15.weightx = 1.0d;
    gridBagConstraints15.anchor = 17;
    gridBagConstraints15.gridx = 1;
    GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
    gridBagConstraints14.fill = 3;
    gridBagConstraints14.gridy = 6;
    gridBagConstraints14.weightx = 1.0d;
    gridBagConstraints14.anchor = 17;
    gridBagConstraints14.gridx = 1;
    GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
    gridBagConstraints11.gridx = 1;
    gridBagConstraints11.anchor = 17;
    gridBagConstraints11.fill = 0;
    gridBagConstraints11.gridy = 2;
    GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
    gridBagConstraints10.fill = 3;
    gridBagConstraints10.gridy = 4;
    gridBagConstraints10.weightx = 1.0d;
    gridBagConstraints10.anchor = 17;
    gridBagConstraints10.gridx = 1;
    GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
    gridBagConstraints9.fill = 3;
    gridBagConstraints9.gridy = 5;
    gridBagConstraints9.weightx = 1.0d;
    gridBagConstraints9.anchor = 17;
    gridBagConstraints9.gridx = 1;
    GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
    gridBagConstraints5.fill = 3;
    gridBagConstraints5.gridy = 3;
    gridBagConstraints5.weightx = 1.0d;
    gridBagConstraints5.anchor = 17;
    gridBagConstraints5.gridx = 1;
    GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
    gridBagConstraints8.gridx = 1;
    gridBagConstraints8.insets = new Insets(5, 5, 5, 5);
    gridBagConstraints8.anchor = 17;
    gridBagConstraints8.gridy = 8;
    GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
    gridBagConstraints13.gridx = 0;
    gridBagConstraints13.anchor = 17;
    gridBagConstraints13.gridy = 7;
    this.jLabelContentSignerPrivateKeyPassword = new JLabel();
    this.jLabelContentSignerPrivateKeyPassword.setText("Content Signer Private Key Password:");
    GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
    gridBagConstraints12.gridx = 0;
    gridBagConstraints12.anchor = 17;
    gridBagConstraints12.gridy = 6;
    this.jLabelContentSignerAlias = new JLabel();
    this.jLabelContentSignerAlias.setText("Content Signer Alias:");
    GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
    gridBagConstraints7.gridx = 0;
    gridBagConstraints7.anchor = 17;
    gridBagConstraints7.gridy = 5;
    this.jLabelCAPrivateKeyPassword = new JLabel();
    this.jLabelCAPrivateKeyPassword.setText("Issuing CA Private Key Password:");
    GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
    gridBagConstraints6.gridx = 0;
    gridBagConstraints6.anchor = 17;
    gridBagConstraints6.gridy = 4;
    this.jLabelCAAlias = new JLabel();
    this.jLabelCAAlias.setText("Issuing CA Alias:");
    GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
    gridBagConstraints4.gridx = 0;
    gridBagConstraints4.anchor = 17;
    gridBagConstraints4.gridy = 3;
    this.jLabelKeystorePassword = new JLabel();
    this.jLabelKeystorePassword.setText("Keystore Password:");
    GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
    gridBagConstraints3.gridx = 0;
    gridBagConstraints3.anchor = 17;
    gridBagConstraints3.gridy = 2;
    this.jLabelKeystorePath = new JLabel();
    this.jLabelKeystorePath.setText("Keystore Path:");
    GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
    gridBagConstraints2.fill = 3;
    gridBagConstraints2.gridy = 1;
    gridBagConstraints2.weightx = 1.0d;
    gridBagConstraints2.anchor = 17;
    gridBagConstraints2.gridx = 1;
    GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
    gridBagConstraints1.gridx = 0;
    gridBagConstraints1.anchor = 17;
    gridBagConstraints1.gridy = 1;
    this.jLabelKeystoreType = new JLabel();
    this.jLabelKeystoreType.setText("Keystore Type:");
    GridBagConstraints gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.anchor = 17;
    gridBagConstraints.gridy = 0;
    this.jLabelHeader = new JLabel();
    this.jLabelHeader.setText("Certificate");
    this.jLabelHeader.setForeground(Color.blue);
    this.jLabelHeader.setFont(new Font("Dialog", 1, 14));
    setLayout(new GridBagLayout());
    add(this.jLabelHeader, gridBagConstraints);
    add(this.jLabelKeystoreType, gridBagConstraints1);
    add(getJTextFieldKeystoreType(), gridBagConstraints2);
    add(this.jLabelKeystorePath, gridBagConstraints3);
    add(getJPanelKeystorePath(), gridBagConstraints11);
    add(this.jLabelKeystorePassword, gridBagConstraints4);
    add(getJPasswordFieldKeystore(), gridBagConstraints5);
    add(this.jLabelCAAlias, gridBagConstraints6);
    add(getJTextFieldCAAlias(), gridBagConstraints10);
    add(this.jLabelCAPrivateKeyPassword, gridBagConstraints7);
    add(getJPasswordFieldCAPrivateKey(), gridBagConstraints9);
    add(this.jLabelContentSignerAlias, gridBagConstraints12);
    add(getJTextFieldContentSignerAlias(), gridBagConstraints14);
    add(this.jLabelContentSignerPrivateKeyPassword, gridBagConstraints13);
    add(getJPasswordFieldContentSignerPrivateKey(), gridBagConstraints15);
    add(getJButtonTest(), gridBagConstraints8);
  }

  public void setHeader(String header) {
    if (header == null) {
      header = "";
    }
    this.jLabelHeader.setText(header);
  }

  public String getHeader() {
    return this.jLabelHeader.getText();
  }

  @Override // gov.nist.piv.auto_create.DataManager
  public void addElement(Document document, Element parent, int level) throws DOMException {
    String baseIndent = "";
    for (int i = 0; i < level; i++) {
      baseIndent = baseIndent + "\t";
    }
    Element root = document.createElement(TAG_DATA_MANAGER);
    parent.appendChild(root);
    root.setAttribute(ATTRIBUTE_TYPE, CertificateManager.class.getName());
    root.setAttribute(ATTRIBUTE_HEADER, getHeader());
    root.appendChild(document.createTextNode("\n"));
    root.appendChild(document.createTextNode(baseIndent + "\t"));
    Element tag = document.createElement(TAG_KEYSTORE_TYPE);
    tag.setTextContent(getJTextFieldKeystoreType().getText());
    root.appendChild(tag);
    root.appendChild(document.createTextNode("\n"));
    root.appendChild(document.createTextNode(baseIndent + "\t"));
    Element tag2 = document.createElement(TAG_KEYSTORE_PATH);
    tag2.setTextContent(getJTextFieldKeystorePath().getText());
    root.appendChild(tag2);
    root.appendChild(document.createTextNode("\n"));
    root.appendChild(document.createTextNode(baseIndent + "\t"));
    Element tag3 = document.createElement(TAG_KEYSTORE_PASSWORD);
    tag3.setAttribute(ATTRIBUTE_IS_PASSWORD, "true");
    tag3.setTextContent(new String(getJPasswordFieldKeystore().getPassword()));
    root.appendChild(tag3);
    root.appendChild(document.createTextNode("\n"));
    root.appendChild(document.createTextNode(baseIndent + "\t"));
    Element tag4 = document.createElement(TAG_CERTIFICATE_ALIAS);
    tag4.setTextContent(getJTextFieldCAAlias().getText());
    root.appendChild(tag4);
    root.appendChild(document.createTextNode("\n"));
    root.appendChild(document.createTextNode(baseIndent + "\t"));
    Element tag5 = document.createElement(TAG_PRIVATE_KEY_PASSWORD);
    tag5.setAttribute(ATTRIBUTE_IS_PASSWORD, "true");
    tag5.setTextContent(new String(getJPasswordFieldCAPrivateKey().getPassword()));
    root.appendChild(tag5);
    root.appendChild(document.createTextNode(baseIndent + "\t"));
    Element tag6 = document.createElement(TAG_CONTENT_SIGNER_ALIAS);
    tag6.setTextContent(getJTextFieldContentSignerAlias().getText());
    root.appendChild(tag6);
    root.appendChild(document.createTextNode("\n"));
    root.appendChild(document.createTextNode(baseIndent + "\t"));
    Element tag7 = document.createElement(TAG_CONTENT_SIGNER_PRIVATE_KEY_PASSWORD);
    tag7.setAttribute(ATTRIBUTE_IS_PASSWORD, "true");
    tag7.setTextContent(new String(getJPasswordFieldContentSignerPrivateKey().getPassword()));
    root.appendChild(tag7);
    root.appendChild(document.createTextNode("\n"));
    root.appendChild(document.createTextNode(baseIndent));
  }

  @Override // gov.nist.piv.auto_create.DataManager
  public void loadValues(Node node) throws DOMException {
    if (node == null || !node.getNodeName().equals(TAG_DATA_MANAGER)) {
      throw new RuntimeException(
          "CertificateManager: CertificateManager not created with valid node");
    }
    HashMap<String, String> hm = XMLUtils.getAttributes(node);
    String dmType = hm.get(ATTRIBUTE_TYPE);
    if (!dmType.equals(CertificateManager.class.getName())) {
      throw new RuntimeException(
          "CertificateManager: CertificateManager not created with valid node");
    }
    setHeader(hm.get(ATTRIBUTE_HEADER));
    Node firstChild = node.getFirstChild();
    while (true) {
      Node child = firstChild;
      if (child != null) {
        String type = child.getNodeName();
        if (type.equals(TAG_KEYSTORE_TYPE)) {
          getJTextFieldKeystoreType().setText(child.getTextContent());
        } else if (type.equals(TAG_KEYSTORE_PATH)) {
          getJTextFieldKeystorePath().setText(child.getTextContent());
        } else if (type.equals(TAG_KEYSTORE_PASSWORD)) {
          getJPasswordFieldKeystore().setText(child.getTextContent());
        } else if (type.equals(TAG_CERTIFICATE_ALIAS)) {
          getJTextFieldCAAlias().setText(child.getTextContent());
        } else if (type.equals(TAG_PRIVATE_KEY_PASSWORD)) {
          getJPasswordFieldCAPrivateKey().setText(child.getTextContent());
        } else if (type.equals(TAG_CONTENT_SIGNER_ALIAS)) {
          getJTextFieldContentSignerAlias().setText(child.getTextContent());
        } else if (type.equals(TAG_CONTENT_SIGNER_PRIVATE_KEY_PASSWORD)) {
          getJPasswordFieldContentSignerPrivateKey().setText(child.getTextContent());
        }
        firstChild = child.getNextSibling();
      } else {
        return;
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public JTextField getJTextFieldKeystoreType() {
    if (this.jTextFieldKeystoreType == null) {
      this.jTextFieldKeystoreType = new JTextField();
      this.jTextFieldKeystoreType.setColumns(8);
      this.jTextFieldKeystoreType.setText("JKS");
    }
    return this.jTextFieldKeystoreType;
  }

  private JButton getJButtonTest() {
    if (this.jButtonTest == null) {
      this.jButtonTest = new JButton();
      this.jButtonTest.setText("Test");
      this.jButtonTest.addActionListener(
          new ActionListener() { // from class: gov.nist.piv.auto_create.CertificateManager.1
            public void actionPerformed(ActionEvent event) {
              try {
                KeyStore keyStore =
                    KeyStore.getInstance(
                        CertificateManager.this.getJTextFieldKeystoreType().getText());
                try {
                  keyStore.load(
                      new FileInputStream(CertificateManager.this.jTextFieldKeystorePath.getText()),
                      CertificateManager.this.getJPasswordFieldKeystore().getPassword());
                  try {
                    X509Certificate caCert =
                        (X509Certificate)
                            keyStore.getCertificate(
                                CertificateManager.this.getJTextFieldCAAlias().getText());
                    if (caCert == null) {
                      CertificateManager.this.sendOutputMessage(
                          "Could not find the CA certificate associated with the given alias...");
                      return;
                    }
                    try {
                      keyStore.getKey(
                          CertificateManager.this.getJTextFieldCAAlias().getText(),
                          CertificateManager.this.getJPasswordFieldCAPrivateKey().getPassword());
                      try {
                        X509Certificate contentSignerCert =
                            (X509Certificate)
                                keyStore.getCertificate(
                                    CertificateManager.this
                                        .getJTextFieldContentSignerAlias()
                                        .getText());
                        if (contentSignerCert == null) {
                          CertificateManager.this.sendOutputMessage(
                              "Could not find the Content Signer certificate associated with the given alias...");
                          return;
                        }
                        try {
                          keyStore.getKey(
                              CertificateManager.this.getJTextFieldContentSignerAlias().getText(),
                              CertificateManager.this
                                  .getJPasswordFieldContentSignerPrivateKey()
                                  .getPassword());
                          CertificateManager.this.sendOutputMessage(
                              "Successfully loaded the certificates and private keys!!");
                        } catch (Exception e) {
                          CertificateManager.this.sendOutputMessage(
                              "Password for the Content Signer private key seems invalid...");
                        }
                      } catch (Exception e2) {
                        CertificateManager.this.sendOutputMessage(
                            "Could not find the Content Signer certificate associated with the given alias...");
                      }
                    } catch (Exception e3) {
                      CertificateManager.this.sendOutputMessage(
                          "Password for the CA private key seems invalid...");
                    }
                  } catch (Exception e4) {
                    CertificateManager.this.sendOutputMessage(
                        "Could not find the CA certificate associated with the given alias...");
                  }
                } catch (Exception e5) {
                  CertificateManager.this.sendOutputMessage(
                      "Could not open keystore file.  Double check the path, and the keystore password");
                }
              } catch (Exception e6) {
                CertificateManager.this.sendOutputMessage("Invalid Key Store type specified...");
              }
            }
          });
    }
    return this.jButtonTest;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public JPasswordField getJPasswordFieldKeystore() {
    if (this.jPasswordFieldKeystore == null) {
      this.jPasswordFieldKeystore = new JPasswordField();
      this.jPasswordFieldKeystore.setColumns(10);
    }
    return this.jPasswordFieldKeystore;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public JPasswordField getJPasswordFieldContentSignerPrivateKey() {
    if (this.jPasswordFieldContentSignerPrivateKey == null) {
      this.jPasswordFieldContentSignerPrivateKey = new JPasswordField();
      this.jPasswordFieldContentSignerPrivateKey.setColumns(10);
    }
    return this.jPasswordFieldContentSignerPrivateKey;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public JPasswordField getJPasswordFieldCAPrivateKey() {
    if (this.jPasswordFieldCAPrivateKey == null) {
      this.jPasswordFieldCAPrivateKey = new JPasswordField();
      this.jPasswordFieldCAPrivateKey.setColumns(10);
    }
    return this.jPasswordFieldCAPrivateKey;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public JTextField getJTextFieldContentSignerAlias() {
    if (this.jTextFieldContentSignerAlias == null) {
      this.jTextFieldContentSignerAlias = new JTextField();
      this.jTextFieldContentSignerAlias.setColumns(10);
    }
    return this.jTextFieldContentSignerAlias;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public JTextField getJTextFieldCAAlias() {
    if (this.jTextFieldCAAlias == null) {
      this.jTextFieldCAAlias = new JTextField();
      this.jTextFieldCAAlias.setColumns(10);
    }
    return this.jTextFieldCAAlias;
  }

  private JPanel getJPanelKeystorePath() {
    if (this.jPanelKeystorePath == null) {
      GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
      gridBagConstraints12.fill = 3;
      gridBagConstraints12.weightx = 1.0d;
      this.jPanelKeystorePath = new JPanel();
      this.jPanelKeystorePath.setLayout(new GridBagLayout());
      this.jPanelKeystorePath.add(getJTextFieldKeystorePath(), gridBagConstraints12);
      this.jPanelKeystorePath.add(getJButtonChoosePath(), new GridBagConstraints());
    }
    return this.jPanelKeystorePath;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public JTextField getJTextFieldKeystorePath() {
    if (this.jTextFieldKeystorePath == null) {
      this.jTextFieldKeystorePath = new JTextField();
      this.jTextFieldKeystorePath.setColumns(20);
    }
    return this.jTextFieldKeystorePath;
  }

  private JButton getJButtonChoosePath() {
    if (this.jButtonChoosePath == null) {
      this.jButtonChoosePath = new JButton();
      this.jButtonChoosePath.setText("...");
      this.jButtonChoosePath.addActionListener(
          new ActionListener() { // from class: gov.nist.piv.auto_create.CertificateManager.2
            public void actionPerformed(ActionEvent e) {
              String curDir = System.getProperty("user.dir");
              JFileChooser fileChooser = new JFileChooser(curDir);
              if (fileChooser.showOpenDialog(CertificateManager.this) == 0) {
                CertificateManager.this
                    .getJTextFieldKeystorePath()
                    .setText(fileChooser.getSelectedFile().getAbsolutePath());
              }
            }
          });
    }
    return this.jButtonChoosePath;
  }
}
