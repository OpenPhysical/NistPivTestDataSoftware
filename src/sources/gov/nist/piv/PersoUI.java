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
 * This software is provided “as is”, without warranty of any kind, including implied warranties of
 * merchantability or fitness for a particular purpose.
 */

package gov.nist.piv;

import com.tvec.utility.displays.output.Output;
import com.tvec.utility.displays.output.OutputMessage;
import com.tvec.utility.displays.output.OutputMessageListener;
import com.tvec.utility.displays.output.OutputMessageSender;
import gov.nist.piv.auto_create.AutoCreateTabPane;
import gov.nist.piv.data.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/* loaded from: JPIV Test Data Generator.jar:gov/nist/piv/PersoUI.class */
public class PersoUI extends JFrame
    implements ActionListener,
        KeyListener,
        UndoableEditListener,
        OutputMessageSender,
        OutputMessageListener {
  private static final long serialVersionUID = 1;
  private static final String VERSION = "2.0.0";
  private static final String AGENCY_CODE = "3201";
  private static final String ORGANIZATIONAL_IDENTIFIER = "3201";
  private final JFileChooser fc;
  private final JSplitPane split;
  private final JPanel cryptoPanel;
  private final JTextField keystoreTypeTextField;
  private final JTextField keystorePathTextField;
  private final JLabel keystorePasswordLabel;
  private final JPasswordField keystorePasswordField;
  private final JLabel caAliasLabel;
  private final JTextField caAliasTextField;
  private final JLabel privateKeyPasswordLabel;
  private final JPasswordField privateKeyPasswordField;
  private final JLabel contentSignerAliasLabel;
  private final JTextField contentSignerAliasTextField;
  private final JLabel contentSignerPrivateKeyPasswordLabel;
  private final JPasswordField contentSignerPrivateKeyPasswordField;
  private final JLabel keystoreTypeLabel;
  private final JLabel keystorePathLabel;
  private final JButton keystorePathBrowseButton;
  private final JButton keystoreLoadButton;
  private final JPanel chuidPanel;
  private final JTextField agencyCodeTextField;
  private final JTextField systemCodeTextField;
  private final JTextField credentialNumberTextField;
  private final JTextField credentialSeriesTextField;
  private final JTextField individualCredentialIssueTextField;
  private final JTextField personIdentifierTextField;
  private final JTextField organizationalCategoryTextField;
  private final JTextField organizationalIdentifierTextField;
  private final JTextField associationCategoryTextField;
  private final JTextArea fascnTextArea;
  private final JLabel agencyCodeLabel;
  private final JLabel systemCodeLabel;
  private final JLabel credentialNumberLabel;
  private final JLabel credentialSeriesLabel;
  private final JLabel individualCredentialIssueLabel;
  private final JLabel personIdentifierLabel;
  private final JLabel organizationalCategoryLabel;
  private final JLabel organizationalIdentifierLabel;
  private final JLabel associationCategoryLabel;
  private final JLabel fascnLabel;
  private final JButton generateFascnButton;
  private final JTextField guidTextField;
  private final JTextField chuidExpirationDateTextField;
  private final JLabel guidLabel;
  private final JLabel chuidExpirationDateLabel;
  private final JButton generateChuidButton;
  private final JButton signChuidButton;
  private final JButton saveChuidButton;
  private final JPanel cccPanel;
  private final JTextField cardIDTextField;
  private final JLabel cardIDLabel;
  private final JButton generateCCCButton;
  private final JButton saveCCCButton;
  private final JPanel certPanel;
  private final JRadioButton pivCertRadioButton;
  private final JRadioButton sigCertRadioButton;
  private final JRadioButton cardAuthCertRadioButton;
  private final JRadioButton keyMgmtCertRadioButton;
  private final JTextField serialNumberTextField;
  private final JLabel serialNumberLabel;
  private final JTextField signatureAlgorithmTextField;
  private final JLabel signatureAlgorithmLabel;
  private final JTextField validFromTextField;
  private final JLabel validFromLabel;
  private final JTextField validToTextField;
  private final JLabel validToLabel;
  private final JRadioButton keyFromFileRadioButton;
  private final JRadioButton keyFromTextFieldRadioButton;
  private final JTextField keyPathTextField;
  private final JLabel keyPathLabel;
  private final JButton keyPathBrowseButton;
  private final JTextArea keyTextArea;
  private final JLabel keyLabel;
  private final JTextField dnCommonNameTextField;
  private final JLabel dnCommonNameLabel;
  private final JTextField dnOrganizationTextField;
  private final JLabel dnOrganizationLabel;
  private final JTextField dnOrganizationalUnitTextField;
  private final JLabel dnOrganizationalUnitLabel;
  private final JTextField dnCountryTextField;
  private final JLabel dnCountryLabel;
  private final JLabel crlHttpURILabel;
  private final JTextField crlHttpURITextField;
  private final JLabel crlLdapURILabel;
  private final JTextField crlLdapURITextField;
  private final JLabel aiaHttpURILabel;
  private final JTextField aiaHttpURITextField;
  private final JLabel aiaLdapURILabel;
  private final JTextField aiaLdapURITextField;
  private final JTextField aiaOcspURITextField;
  private final JLabel aiaOcspURILabel;
  private final JTextField dnUPNTextField;
  private final JLabel dnUPNLabel;
  private final JLabel emailLabel;
  private final JTextField emailTextField;
  private final JButton generateCertButton;
  private final JButton saveCertButton;
  private final JPanel bioPanel;
  private final JRadioButton fingersRadioButton;
  private final JRadioButton faceRadioButton;
  private final JTextField biometricDataPathTextField;
  private final JButton browseBioButton;
  private final JTextField biometricCreationDateTextField;
  private final JTextField validityStartDateTextField;
  private final JTextField monthsValidTextField;
  private final JCheckBox includeCertCheckBox;
  private final JLabel bioDataPathLabel;
  private final JLabel biometricCreationDateLabel;
  private final JLabel validityStartDateLabel;
  private final JLabel monthsValidLabel;
  private final JButton generateBiometricButton;
  private final JButton saveBiometricButton;
  private final JPanel printedPanel;
  private final JTextField printedNameTextField;
  private final JTextField employeeAffiliation1TextField;
  private final JTextField employeeAffiliation2TextField;
  private final JTextField expirationDateTextField;
  private final JTextField agencyCardSerialNumberTextField;
  private final JTextField issuerIDTextField;
  private final JLabel printedNameLabel;
  private final JLabel employeeAffiliation1Label;
  private final JLabel employeeAffiliation2Label;
  private final JLabel expirationDateLabel;
  private final JLabel agencyCardSerialNumberLabel;
  private final JLabel issuerIDLabel;
  private final JButton generatePrintedButton;
  private final JButton savePrintedButton;
  private final JPanel secobjPanel;
  private final JTextField cccHashTextField;
  private final JTextField chuidHashTextField;
  private final JTextField pivCertHashTextField;
  private final JTextField finger1HashTextField;
  private final JTextField printedHashTextField;
  private final JTextField faceHashTextField;
  private final JTextField sigCertHashTextField;
  private final JTextField keyMgmtCertHashTextField;
  private final JTextField cardAuthCertHashTextField;
  private final JLabel cccHashLabel;
  private final JLabel chuidHashLabel;
  private final JLabel pivCertHashLabel;
  private final JLabel finger1HashLabel;
  private final JLabel printedHashLabel;
  private final JLabel faceHashLabel;
  private final JLabel sigCertHashLabel;
  private final JLabel keyMgmtCertHashLabel;
  private final JLabel cardAuthCertHashLabel;
  private final JButton setHashButton;
  private final JButton generateSecObjButton;
  private final JButton saveSecObjButton;
  private final Vector<OutputMessageListener> outputListeners = new Vector<>();
  private UndoManager undo;
  private FASCN fascn;
  private KeyStore keyStore;
  private X509Certificate caCert;
  private X509Certificate contentSignerCert;
  private JTabbedPane tabs;
  private CHUID chuid;
  private byte[] signedChuidBytes;
  private CCC ccc;
  private PrintedInfo printed;
  private X509Cert pivAuthCert;
  private X509Cert sigCert;
  private X509Cert cardAuthCert;
  private X509Cert keyMgmtCert;
  private Biometric fingers;
  private Biometric face;
  private SecurityObject secobj;
  private Output output;

  public PersoUI() {
    String version = PersoUI.class.getPackage().getImplementationVersion();
    setTitle("PIV PersoUI v" + (version == null ? VERSION : version));
    Security.addProvider(new BouncyCastleProvider());
    init();
    this.fc = new JFileChooser(new File("."));
    JPanel keystorePanel = new JPanel();
    GridBagLayout gridbag = new GridBagLayout();
    keystorePanel.setLayout(gridbag);
    GridBagConstraints constraints = new GridBagConstraints();
    this.keystoreTypeTextField = new JTextField(10);
    this.keystoreTypeTextField.setText("JKS");
    this.keystorePathTextField = new JTextField(10);
    this.caAliasTextField = new JTextField(10);
    this.privateKeyPasswordField = new JPasswordField(10);
    this.contentSignerAliasTextField = new JTextField(10);
    this.contentSignerPrivateKeyPasswordField = new JPasswordField(10);
    this.keystorePasswordField = new JPasswordField(10);
    this.keystorePathBrowseButton = new JButton("...");
    this.keystoreTypeLabel = new JLabel("Keystore Type: ");
    this.keystorePathLabel = new JLabel("Keystore Path: ");
    this.keystorePasswordLabel = new JLabel("Keystore Password: ");
    this.caAliasLabel = new JLabel("CA Alias: ");
    this.privateKeyPasswordLabel = new JLabel("CA Private Key Password: ");
    this.contentSignerAliasLabel = new JLabel("Content Signer Alias: ");
    this.contentSignerPrivateKeyPasswordLabel = new JLabel("Content Signer Private Key Password: ");
    this.keystoreLoadButton = new JButton("Load Certs");
    constraints.gridwidth = 23;
    constraints.anchor = 17;
    keystorePanel.add(this.keystoreTypeLabel, constraints);
    constraints.gridwidth = 0;
    keystorePanel.add(this.keystoreTypeTextField, constraints);
    constraints.gridwidth = -1;
    keystorePanel.add(this.keystorePathLabel, constraints);
    keystorePanel.add(this.keystorePathTextField, constraints);
    constraints.gridwidth = 0;
    keystorePanel.add(this.keystorePathBrowseButton, constraints);
    constraints.gridwidth = -1;
    keystorePanel.add(this.keystorePasswordLabel, constraints);
    constraints.gridwidth = 0;
    keystorePanel.add(this.keystorePasswordField, constraints);
    constraints.gridwidth = -1;
    keystorePanel.add(this.caAliasLabel, constraints);
    constraints.gridwidth = 0;
    keystorePanel.add(this.caAliasTextField, constraints);
    constraints.gridwidth = -1;
    keystorePanel.add(this.privateKeyPasswordLabel, constraints);
    constraints.gridwidth = 0;
    keystorePanel.add(this.privateKeyPasswordField, constraints);
    constraints.gridwidth = -1;
    keystorePanel.add(this.contentSignerAliasLabel, constraints);
    constraints.gridwidth = 0;
    keystorePanel.add(this.contentSignerAliasTextField, constraints);
    constraints.gridwidth = -1;
    keystorePanel.add(this.contentSignerPrivateKeyPasswordLabel, constraints);
    constraints.gridwidth = 0;
    keystorePanel.add(this.contentSignerPrivateKeyPasswordField, constraints);
    constraints.gridwidth = -1;
    keystorePanel.add(new JLabel(""), constraints);
    constraints.gridwidth = 0;
    constraints.anchor = 13;
    keystorePanel.add(this.keystoreLoadButton, constraints);
    this.keystorePathBrowseButton.addActionListener(this);
    this.keystorePathBrowseButton.setActionCommand("keystore browse");
    this.keystoreLoadButton.addActionListener(this);
    this.keystoreLoadButton.setActionCommand("keystore load");
    keystorePanel.setBorder(
        BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Key Store"),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    this.cryptoPanel = new JPanel(new BorderLayout());
    this.cryptoPanel.add(keystorePanel, "First");
    this.tabs.add(this.cryptoPanel, "Crypto Provider");
    JPanel fascnPane = new JPanel();
    fascnPane.setLayout(gridbag);
    this.agencyCodeTextField = new JTextField(10);
    this.agencyCodeTextField.setText("3201");
    this.agencyCodeTextField.setEditable(false);
    this.systemCodeTextField = new JTextField(10);
    this.credentialNumberTextField = new JTextField(10);
    this.credentialSeriesTextField = new JTextField(10);
    this.individualCredentialIssueTextField = new JTextField(10);
    this.personIdentifierTextField = new JTextField(10);
    this.organizationalCategoryTextField = new JTextField(10);
    this.organizationalIdentifierTextField = new JTextField(10);
    this.organizationalIdentifierTextField.setText("3201");
    this.organizationalIdentifierTextField.setEditable(false);
    this.associationCategoryTextField = new JTextField(10);
    this.fascnTextArea = new JTextArea(2, 10);
    this.fascnTextArea.setEditable(false);
    this.fascnLabel = new JLabel("FASC-N: ");
    this.fascnLabel.setLabelFor(this.fascnTextArea);
    this.agencyCodeLabel = new JLabel("Agency Code: ");
    this.agencyCodeLabel.setLabelFor(this.agencyCodeTextField);
    this.agencyCodeLabel.setToolTipText(
        "This is a 4-digit number, representing the \"Agency Code\" of the agency generating this data");
    this.systemCodeLabel = new JLabel("System Code: ");
    this.systemCodeLabel.setLabelFor(this.systemCodeTextField);
    this.systemCodeLabel.setToolTipText(
        "This is a 4-digit number used to identify which of a number of possible personalization systems there are");
    this.credentialNumberLabel = new JLabel("Credential Number: ");
    this.credentialNumberLabel.setLabelFor(this.credentialNumberTextField);
    this.credentialNumberLabel.setToolTipText("This is a 6-digit credential number");
    this.credentialSeriesLabel = new JLabel("Credential Series: ");
    this.credentialSeriesLabel.setLabelFor(this.credentialSeriesTextField);
    this.credentialSeriesLabel.setToolTipText("This is a 1-digit number");
    this.individualCredentialIssueLabel = new JLabel("Individual Credential Issue: ");
    this.individualCredentialIssueLabel.setLabelFor(this.individualCredentialIssueTextField);
    this.individualCredentialIssueLabel.setToolTipText("This is a 1 digit number");
    this.personIdentifierLabel = new JLabel("Person Identifier: ");
    this.personIdentifierLabel.setLabelFor(this.personIdentifierTextField);
    this.personIdentifierLabel.setToolTipText(
        "This is a 10-digit number, unique to the card holder.");
    this.organizationalCategoryLabel = new JLabel("Organizational Category: ");
    this.organizationalCategoryLabel.setLabelFor(this.organizationalCategoryTextField);
    this.organizationalCategoryLabel.setToolTipText("This is a 1 digit number");
    this.organizationalIdentifierLabel = new JLabel("Organizational Identifier: ");
    this.organizationalIdentifierLabel.setLabelFor(this.organizationalIdentifierTextField);
    this.organizationalIdentifierLabel.setToolTipText(
        "This is a 4 digit number, likely identical to the previous Agency Code");
    this.associationCategoryLabel = new JLabel("Association Category: ");
    this.associationCategoryLabel.setLabelFor(this.associationCategoryTextField);
    this.associationCategoryLabel.setToolTipText("This is a 1 digit number");
    this.generateFascnButton = new JButton("Generate");
    this.generateFascnButton.addActionListener(this);
    this.generateFascnButton.setActionCommand("generate fascn");
    JLabel[] labels = {
      this.agencyCodeLabel,
      this.systemCodeLabel,
      this.credentialNumberLabel,
      this.credentialSeriesLabel,
      this.individualCredentialIssueLabel,
      this.personIdentifierLabel,
      this.organizationalCategoryLabel,
      this.organizationalIdentifierLabel,
      this.associationCategoryLabel
    };
    JTextField[] textFields = {
      this.agencyCodeTextField,
      this.systemCodeTextField,
      this.credentialNumberTextField,
      this.credentialSeriesTextField,
      this.individualCredentialIssueTextField,
      this.personIdentifierTextField,
      this.organizationalCategoryTextField,
      this.organizationalIdentifierTextField,
      this.associationCategoryTextField
    };
    addLabelTextRows(labels, textFields, gridbag, fascnPane);
    GridBagConstraints constraints2 = new GridBagConstraints();
    constraints2.gridwidth = -1;
    constraints2.anchor = 13;
    constraints2.weightx = 0.0d;
    fascnPane.add(this.fascnLabel, constraints2);
    constraints2.gridwidth = 0;
    constraints2.anchor = 17;
    constraints2.weightx = 1.0d;
    fascnPane.add(this.fascnTextArea, constraints2);
    constraints2.gridwidth = -1;
    constraints2.anchor = 13;
    constraints2.weightx = 0.0d;
    fascnPane.add(new JLabel(""), constraints2);
    constraints2.gridwidth = 0;
    constraints2.anchor = 17;
    constraints2.weightx = 1.0d;
    fascnPane.add(this.generateFascnButton, constraints2);
    fascnPane.setBorder(
        BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("FASC-N"),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    JPanel chuidPane = new JPanel();
    new GridBagConstraints();
    chuidPane.setLayout(gridbag);
    this.guidTextField = new JTextField(10);
    this.guidLabel = new JLabel("GUID: ");
    this.guidLabel.setLabelFor(this.agencyCodeTextField);
    this.guidLabel.setToolTipText(
        "This is a 16-digit number indicating a Globally Unique ID number.  It can be all zeros.");
    this.chuidExpirationDateTextField = new JTextField(10);
    this.chuidExpirationDateLabel = new JLabel("Expiration Date (YYYYMMDD) :");
    this.chuidExpirationDateLabel.setLabelFor(this.chuidExpirationDateTextField);
    this.chuidExpirationDateLabel.setToolTipText(
        "This is the expiration date of the CHUID.  Format is 'YYYYMMDD'.");
    this.generateChuidButton = new JButton("Generate");
    this.generateChuidButton.setActionCommand("generate chuid");
    this.generateChuidButton.setEnabled(false);
    this.generateChuidButton.addActionListener(this);
    this.generateChuidButton.setToolTipText("This is disabled until a FASC-N is generated");
    this.signChuidButton = new JButton("Sign");
    this.signChuidButton.setActionCommand("sign chuid");
    this.signChuidButton.addActionListener(this);
    this.signChuidButton.setEnabled(false);
    this.signChuidButton.setToolTipText("This is disabled until a CHUID is generated.");
    this.saveChuidButton = new JButton("Save");
    this.saveChuidButton.setActionCommand("save chuid");
    this.saveChuidButton.addActionListener(this);
    this.saveChuidButton.setEnabled(false);
    this.saveChuidButton.setToolTipText("This is disabled until the CHUID is signed.");
    GridBagConstraints c = new GridBagConstraints();
    c.gridwidth = -1;
    c.anchor = 12;
    c.weightx = 0.0d;
    chuidPane.add(this.guidLabel, c);
    c.gridwidth = 0;
    c.anchor = 17;
    c.weightx = 1.0d;
    chuidPane.add(this.guidTextField, c);
    c.gridwidth = -1;
    c.anchor = 13;
    c.weightx = 0.0d;
    chuidPane.add(this.chuidExpirationDateLabel, c);
    c.gridwidth = 0;
    c.anchor = 17;
    c.weightx = 1.0d;
    chuidPane.add(this.chuidExpirationDateTextField, c);
    c.gridwidth = 0;
    c.anchor = 13;
    c.weightx = 0.0d;
    chuidPane.add(new JLabel(""), c);
    c.gridwidth = 0;
    c.anchor = 17;
    c.weightx = 1.0d;
    chuidPane.add(this.generateChuidButton, c);
    c.gridwidth = -1;
    c.anchor = 13;
    c.weightx = 0.0d;
    chuidPane.add(new JLabel(""), c);
    c.gridwidth = 0;
    c.anchor = 17;
    c.weightx = 1.0d;
    chuidPane.add(this.generateChuidButton, c);
    c.gridwidth = -1;
    c.anchor = 13;
    c.weightx = 0.0d;
    chuidPane.add(new JLabel(""), c);
    c.gridwidth = 0;
    c.anchor = 17;
    c.weightx = 1.0d;
    chuidPane.add(this.saveChuidButton, c);
    chuidPane.setBorder(
        BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("CHUID"),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    this.chuidPanel = new JPanel(new BorderLayout());
    this.chuidPanel.add(fascnPane, "First");
    this.chuidPanel.add(chuidPane, "Center");
    this.tabs.add(this.chuidPanel, "CHUID");
    JPanel cccPane = new JPanel();
    cccPane.setLayout(gridbag);
    this.cardIDTextField = new JTextField(10);
    this.cardIDLabel = new JLabel("Card ID: ");
    this.cardIDLabel.setLabelFor(this.cardIDTextField);
    this.generateCCCButton = new JButton("Generate");
    this.generateCCCButton.setActionCommand("generate ccc");
    this.generateCCCButton.addActionListener(this);
    this.generateCCCButton.setToolTipText(
        "Pressing this button will generate a CCC with the specified Card ID.");
    this.saveCCCButton = new JButton("Save");
    this.saveCCCButton.setActionCommand("save ccc");
    this.saveCCCButton.addActionListener(this);
    this.saveCCCButton.setToolTipText("This will open a dialog for saving the CCC data to a file.");
    this.saveCCCButton.setEnabled(false);
    GridBagConstraints constraints3 = new GridBagConstraints();
    constraints3.gridwidth = 23;
    constraints3.anchor = 17;
    cccPane.add(this.cardIDLabel, constraints3);
    constraints3.gridwidth = 0;
    cccPane.add(this.cardIDTextField, constraints3);
    constraints3.gridwidth = -1;
    cccPane.add(new JLabel(""), constraints3);
    constraints3.gridwidth = 0;
    constraints3.anchor = 13;
    cccPane.add(this.generateCCCButton, constraints3);
    constraints3.gridwidth = -1;
    cccPane.add(new JLabel(""), constraints3);
    constraints3.gridwidth = 0;
    constraints3.anchor = 13;
    cccPane.add(this.saveCCCButton, constraints3);
    this.cccPanel = new JPanel(new BorderLayout());
    this.cccPanel.add(cccPane, "First");
    this.tabs.add(this.cccPanel, "CCC");
    JPanel certPane = new JPanel();
    certPane.setLayout(gridbag);
    this.pivCertRadioButton = new JRadioButton("PIV Auth Cert");
    this.pivCertRadioButton.setSelected(true);
    this.sigCertRadioButton = new JRadioButton("Digital Signature Cert");
    this.cardAuthCertRadioButton = new JRadioButton("Card Authentication Cert");
    this.keyMgmtCertRadioButton = new JRadioButton("Key Management Cert");
    ButtonGroup certBG = new ButtonGroup();
    certBG.add(this.pivCertRadioButton);
    certBG.add(this.sigCertRadioButton);
    certBG.add(this.cardAuthCertRadioButton);
    certBG.add(this.keyMgmtCertRadioButton);
    this.serialNumberTextField = new JTextField(10);
    this.serialNumberLabel = new JLabel("Cert Serial Number: ");
    this.signatureAlgorithmTextField = new JTextField(10);
    this.signatureAlgorithmTextField.setText(X509Cert.DEFAULT_SIGNATURE_ALGORITHM);
    this.signatureAlgorithmLabel = new JLabel("Signature Algorithm: ");
    this.signatureAlgorithmTextField.setEditable(false);
    this.validFromTextField = new JTextField(10);
    this.validFromLabel = new JLabel("Valid from : ");
    this.validFromLabel.setToolTipText(
        "Time and date that begins the validity of the certificate.  YYYYMMDDhhmmss");
    this.validToTextField = new JTextField(10);
    this.validToLabel = new JLabel("Valid to: ");
    this.validToLabel.setToolTipText(
        "Time and date that the the certificate expires.  YYYYMMDDhhmmss");
    this.keyFromFileRadioButton = new JRadioButton("Get public key from file");
    this.keyFromFileRadioButton.setSelected(true);
    this.keyFromFileRadioButton.addActionListener(this);
    this.keyFromFileRadioButton.setActionCommand("key from file");
    this.keyFromTextFieldRadioButton = new JRadioButton("Get public key from text");
    this.keyFromTextFieldRadioButton.addActionListener(this);
    this.keyFromTextFieldRadioButton.setActionCommand("key from text field");
    ButtonGroup keyBG = new ButtonGroup();
    keyBG.add(this.keyFromFileRadioButton);
    keyBG.add(this.keyFromTextFieldRadioButton);
    this.keyPathTextField = new JTextField(31);
    this.keyPathLabel = new JLabel("Path to key file: ");
    this.keyPathLabel.setToolTipText(
        "The binary response from a successful pivGenerateAsymmetricKeyPair function call to a PIV card.\nCurrently, only RSA1024 is supported.  The entire construct has a tag of 0x7f49.  The N-modulus has a tag of 0x81.  The Public Exponent has a tag of 0x82.");
    this.keyPathBrowseButton = new JButton("...");
    this.keyPathBrowseButton.addActionListener(this);
    this.keyPathBrowseButton.setActionCommand("browse key");
    this.keyTextArea = new JTextArea(4, 30);
    this.keyTextArea.setEnabled(false);
    this.keyTextArea.setLineWrap(true);
    this.keyTextArea.setAutoscrolls(true);
    JScrollPane scroll = new JScrollPane(this.keyTextArea);
    scroll.setVerticalScrollBarPolicy(22);
    this.keyLabel = new JLabel("Public key: ");
    this.keyLabel.setEnabled(false);
    this.keyLabel.setToolTipText(
        "The ASCII hexidecimal representation of the response of a successful pivGenerateAsymmetricKeyPair function call to a PIV card.\nCurrently, only RSA1024 is supported.  The entire construct has a tag of 0x7f49.  The N-modulus has a tag of 0x81.  The Public Exponent has a tag of 0x82.");
    this.dnCommonNameTextField = new JTextField(10);
    this.dnCommonNameLabel = new JLabel("Common Name: ");
    this.dnOrganizationTextField = new JTextField(10);
    this.dnOrganizationTextField.setText("U.S. Government");
    this.dnOrganizationLabel = new JLabel("Organization: ");
    this.dnOrganizationalUnitTextField = new JTextField(10);
    this.dnOrganizationalUnitLabel = new JLabel("Organizational Unit: ");
    this.dnOrganizationalUnitLabel.setToolTipText(
        "This should be the agency or department to which the cardholder is associated.");
    this.dnCountryTextField = new JTextField(10);
    this.dnCountryTextField.setText("US");
    this.dnCountryLabel = new JLabel("Country: ");
    this.crlLdapURILabel = new JLabel("CRL ldap URI: ");
    this.crlLdapURITextField = new JTextField(30);
    this.crlLdapURITextField.setText(
        "ldap://smime2.nist.gov/cn=Good%20CA,o=Test%20Certificates,c=US?certificateRevocationList");
    this.crlHttpURILabel = new JLabel("CRL http URI: ");
    this.crlHttpURITextField = new JTextField(30);
    this.crlHttpURITextField.setText(
        "http://fictitious.nist.gov/fictitiousCRLdirectory/fictitiousCRL1.crl");
    this.aiaLdapURILabel = new JLabel("Authority Info Access ldap URI: ");
    this.aiaLdapURITextField = new JTextField(30);
    this.aiaLdapURITextField.setText(
        "ldap://smime2.nist.gov/cn=Good%20CA,o=Test%20Certificates,c=US?cACertificate,crossCertificatePair");
    this.aiaHttpURILabel = new JLabel("Authority Info Access http URI: ");
    this.aiaHttpURITextField = new JTextField(30);
    this.aiaHttpURITextField.setText(
        "http://fictitious.nist.gov/fictitiousCertsOnlyCMSdirectory/certsIssuedToGoodCA.p7c");
    this.aiaOcspURILabel = new JLabel("Authority Info Access ocsp URI: ");
    this.aiaOcspURITextField = new JTextField(30);
    this.aiaOcspURITextField.setText("http://fictitious.nist.gov/fictitiousOCSPLocation/");
    this.dnUPNTextField = new JTextField(32);
    this.dnUPNLabel = new JLabel("UPN: ");
    this.emailTextField = new JTextField(32);
    this.emailLabel = new JLabel("Email: ");
    this.generateCertButton = new JButton("Generate");
    this.generateCertButton.addActionListener(this);
    this.generateCertButton.setActionCommand("generate cert");
    this.saveCertButton = new JButton("Save");
    this.saveCertButton.addActionListener(this);
    this.saveCertButton.setActionCommand("save cert");
    this.saveCertButton.setEnabled(false);
    GridBagConstraints constraints4 = new GridBagConstraints();
    constraints4.gridwidth = -1;
    constraints4.anchor = 17;
    certPane.add(this.pivCertRadioButton, constraints4);
    constraints4.gridwidth = 0;
    certPane.add(this.sigCertRadioButton, constraints4);
    constraints4.gridwidth = -1;
    certPane.add(this.cardAuthCertRadioButton, constraints4);
    constraints4.gridwidth = 0;
    certPane.add(this.keyMgmtCertRadioButton, constraints4);
    GridBagConstraints constraints5 = new GridBagConstraints();
    constraints5.anchor = 13;
    constraints5.gridwidth = -1;
    certPane.add(this.serialNumberLabel, constraints5);
    constraints5.anchor = 17;
    constraints5.gridwidth = 0;
    certPane.add(this.serialNumberTextField, constraints5);
    constraints5.anchor = 13;
    constraints5.gridwidth = -1;
    certPane.add(this.signatureAlgorithmLabel, constraints5);
    constraints5.anchor = 17;
    constraints5.gridwidth = 0;
    certPane.add(this.signatureAlgorithmTextField, constraints5);
    constraints5.anchor = 13;
    constraints5.gridwidth = -1;
    certPane.add(this.validFromLabel, constraints5);
    constraints5.anchor = 17;
    constraints5.gridwidth = 0;
    certPane.add(this.validFromTextField, constraints5);
    constraints5.anchor = 13;
    constraints5.gridwidth = -1;
    certPane.add(this.validToLabel, constraints5);
    constraints5.anchor = 17;
    constraints5.gridwidth = 0;
    certPane.add(this.validToTextField, constraints5);
    certPane.add(this.keyFromFileRadioButton, constraints5);
    constraints5.anchor = 13;
    constraints5.gridwidth = -1;
    certPane.add(this.keyPathLabel, constraints5);
    certPane.add(this.keyPathTextField, constraints5);
    constraints5.anchor = 17;
    constraints5.gridwidth = 0;
    certPane.add(this.keyPathBrowseButton, constraints5);
    certPane.add(this.keyFromTextFieldRadioButton, constraints5);
    constraints5.anchor = 13;
    constraints5.gridwidth = -1;
    certPane.add(this.keyLabel, constraints5);
    constraints5.gridwidth = 0;
    certPane.add(scroll, constraints5);
    constraints5.anchor = 13;
    constraints5.gridwidth = -1;
    certPane.add(this.dnCommonNameLabel, constraints5);
    constraints5.anchor = 17;
    constraints5.gridwidth = 0;
    certPane.add(this.dnCommonNameTextField, constraints5);
    constraints5.anchor = 13;
    constraints5.gridwidth = -1;
    certPane.add(this.dnOrganizationLabel, constraints5);
    constraints5.anchor = 17;
    constraints5.gridwidth = 0;
    certPane.add(this.dnOrganizationTextField, constraints5);
    constraints5.anchor = 13;
    constraints5.gridwidth = -1;
    certPane.add(this.dnOrganizationalUnitLabel, constraints5);
    constraints5.anchor = 17;
    constraints5.gridwidth = 0;
    certPane.add(this.dnOrganizationalUnitTextField, constraints5);
    constraints5.anchor = 13;
    constraints5.gridwidth = -1;
    certPane.add(this.dnCountryLabel, constraints5);
    constraints5.anchor = 17;
    constraints5.gridwidth = 0;
    certPane.add(this.dnCountryTextField, constraints5);
    constraints5.anchor = 13;
    constraints5.gridwidth = -1;
    certPane.add(this.crlHttpURILabel, constraints5);
    constraints5.anchor = 17;
    constraints5.gridwidth = 0;
    certPane.add(this.crlHttpURITextField, constraints5);
    constraints5.anchor = 13;
    constraints5.gridwidth = -1;
    certPane.add(this.crlLdapURILabel, constraints5);
    constraints5.anchor = 17;
    constraints5.gridwidth = 0;
    certPane.add(this.crlLdapURITextField, constraints5);
    constraints5.anchor = 13;
    constraints5.gridwidth = -1;
    certPane.add(this.aiaHttpURILabel, constraints5);
    constraints5.anchor = 17;
    constraints5.gridwidth = 0;
    certPane.add(this.aiaHttpURITextField, constraints5);
    constraints5.anchor = 13;
    constraints5.gridwidth = -1;
    certPane.add(this.aiaLdapURILabel, constraints5);
    constraints5.anchor = 17;
    constraints5.gridwidth = 0;
    certPane.add(this.aiaLdapURITextField, constraints5);
    constraints5.anchor = 13;
    constraints5.gridwidth = -1;
    certPane.add(this.aiaOcspURILabel, constraints5);
    constraints5.anchor = 17;
    constraints5.gridwidth = 0;
    certPane.add(this.aiaOcspURITextField, constraints5);
    constraints5.anchor = 13;
    constraints5.gridwidth = -1;
    certPane.add(this.dnUPNLabel, constraints5);
    constraints5.anchor = 17;
    constraints5.gridwidth = 0;
    certPane.add(this.dnUPNTextField, constraints5);
    constraints5.anchor = 13;
    constraints5.gridwidth = -1;
    certPane.add(this.emailLabel, constraints5);
    constraints5.anchor = 17;
    constraints5.gridwidth = 0;
    certPane.add(this.emailTextField, constraints5);
    constraints5.anchor = 13;
    constraints5.gridwidth = -1;
    certPane.add(new JLabel(""), constraints5);
    constraints5.anchor = 17;
    constraints5.gridwidth = 0;
    certPane.add(this.generateCertButton, constraints5);
    constraints5.anchor = 13;
    constraints5.gridwidth = -1;
    certPane.add(new JLabel(""), constraints5);
    constraints5.anchor = 17;
    constraints5.gridwidth = 0;
    certPane.add(this.saveCertButton, constraints5);
    this.certPanel = new JPanel(new BorderLayout());
    this.certPanel.add(certPane, "First");
    this.tabs.add(this.certPanel, "Certificates");
    JPanel bioPane = new JPanel();
    bioPane.setLayout(gridbag);
    this.fingersRadioButton = new JRadioButton("Fingerprints");
    this.fingersRadioButton.setSelected(true);
    this.fingersRadioButton.addActionListener(this);
    this.fingersRadioButton.setActionCommand("finger button");
    this.faceRadioButton = new JRadioButton("Facial Image");
    this.faceRadioButton.addActionListener(this);
    this.faceRadioButton.setActionCommand("face button");
    ButtonGroup bg = new ButtonGroup();
    bg.add(this.fingersRadioButton);
    bg.add(this.faceRadioButton);
    this.biometricDataPathTextField = new JTextField(10);
    this.biometricCreationDateTextField = new JTextField(10);
    this.validityStartDateTextField = new JTextField(10);
    this.monthsValidTextField = new JTextField(10);
    this.includeCertCheckBox = new JCheckBox("Include Signing Certificate");
    this.includeCertCheckBox.setSelected(false);
    this.bioDataPathLabel = new JLabel("Path to first fingerprint data file: ");
    this.biometricCreationDateLabel = new JLabel("Fingerprint file creationn date: ");
    this.biometricCreationDateLabel.setToolTipText(
        "The date this file was created.  (YYYYMMDDhhmmss)");
    this.validityStartDateLabel = new JLabel("Validity Start Date:");
    this.validityStartDateLabel.setToolTipText(
        "The date at which this biometric credential becomes useable.  YYYYMMDDhhmmss");
    this.monthsValidLabel = new JLabel("Number of months valid");
    this.browseBioButton = new JButton("...");
    this.browseBioButton.addActionListener(this);
    this.browseBioButton.setActionCommand("browse bio");
    this.generateBiometricButton = new JButton("Generate");
    this.generateBiometricButton.addActionListener(this);
    this.generateBiometricButton.setActionCommand("generate bio");
    this.saveBiometricButton = new JButton("Save");
    this.saveBiometricButton.addActionListener(this);
    this.saveBiometricButton.setActionCommand("save bio");
    this.saveBiometricButton.setEnabled(false);
    GridBagConstraints constraints6 = new GridBagConstraints();
    constraints6.gridwidth = -1;
    constraints6.anchor = 17;
    bioPane.add(this.fingersRadioButton, constraints6);
    constraints6.gridwidth = 0;
    bioPane.add(this.faceRadioButton, constraints6);
    constraints6.gridwidth = 0;
    bioPane.add(this.includeCertCheckBox, constraints6);
    constraints6.gridwidth = -1;
    bioPane.add(this.bioDataPathLabel, constraints6);
    constraints6.gridwidth = -1;
    bioPane.add(this.biometricDataPathTextField, constraints6);
    constraints6.gridwidth = 0;
    bioPane.add(this.browseBioButton, constraints6);
    constraints6.gridwidth = -1;
    bioPane.add(this.biometricCreationDateLabel, constraints6);
    constraints6.gridwidth = 0;
    bioPane.add(this.biometricCreationDateTextField, constraints6);
    constraints6.gridwidth = -1;
    bioPane.add(this.validityStartDateLabel, constraints6);
    constraints6.gridwidth = 0;
    bioPane.add(this.validityStartDateTextField, constraints6);
    constraints6.gridwidth = -1;
    bioPane.add(this.monthsValidLabel, constraints6);
    constraints6.gridwidth = 0;
    bioPane.add(this.monthsValidTextField, constraints6);
    constraints6.gridwidth = -1;
    constraints6.gridwidth = -1;
    bioPane.add(new JLabel(""), constraints6);
    constraints6.gridwidth = 0;
    constraints6.anchor = 13;
    bioPane.add(this.generateBiometricButton, constraints6);
    constraints6.gridwidth = -1;
    bioPane.add(new JLabel(""), constraints6);
    constraints6.gridwidth = 0;
    constraints6.anchor = 13;
    bioPane.add(this.saveBiometricButton, constraints6);
    this.bioPanel = new JPanel(new BorderLayout());
    this.bioPanel.add(bioPane, "First");
    this.tabs.add(this.bioPanel, "Biometrics");
    JPanel printedPane = new JPanel();
    printedPane.setLayout(gridbag);
    this.printedNameTextField = new JTextField(10);
    this.employeeAffiliation1TextField = new JTextField(10);
    this.employeeAffiliation2TextField = new JTextField(10);
    this.expirationDateTextField = new JTextField(10);
    this.agencyCardSerialNumberTextField = new JTextField(10);
    this.issuerIDTextField = new JTextField(10);
    this.printedNameLabel = new JLabel("Printed Name: ");
    this.printedNameLabel.setLabelFor(this.printedNameTextField);
    this.employeeAffiliation1Label = new JLabel("Employee Affiliation 1: ");
    this.employeeAffiliation1Label.setLabelFor(this.employeeAffiliation1TextField);
    this.employeeAffiliation1Label.setToolTipText("20 Character maximum");
    this.employeeAffiliation2Label = new JLabel("Employee Affiliation 2: ");
    this.employeeAffiliation2Label.setLabelFor(this.employeeAffiliation2TextField);
    this.employeeAffiliation2Label.setToolTipText("20 Character maximum");
    this.expirationDateLabel = new JLabel("Expiration Date: ");
    this.expirationDateLabel.setLabelFor(this.expirationDateTextField);
    this.expirationDateLabel.setToolTipText("Date format should be YYYYMMMDD (2006FEB19).");
    this.agencyCardSerialNumberLabel = new JLabel("Card Serial Number: ");
    this.agencyCardSerialNumberLabel.setLabelFor(this.agencyCardSerialNumberTextField);
    this.issuerIDLabel = new JLabel("Issuer ID: ");
    this.issuerIDLabel.setLabelFor(this.issuerIDTextField);
    this.generatePrintedButton = new JButton("Generate");
    this.generatePrintedButton.setActionCommand("generate printed");
    this.generatePrintedButton.addActionListener(this);
    this.generatePrintedButton.setToolTipText(
        "Pressing this button will generate the Printed Info.");
    this.savePrintedButton = new JButton("Save");
    this.savePrintedButton.setActionCommand("save printed");
    this.savePrintedButton.addActionListener(this);
    this.savePrintedButton.setToolTipText(
        "This will open a dialog for saving the Printed Info data to a file.");
    this.savePrintedButton.setEnabled(false);
    GridBagConstraints constraints7 = new GridBagConstraints();
    constraints7.gridwidth = 23;
    constraints7.anchor = 17;
    printedPane.add(this.printedNameLabel, constraints7);
    constraints7.gridwidth = 0;
    printedPane.add(this.printedNameTextField, constraints7);
    constraints7.gridwidth = -1;
    printedPane.add(this.employeeAffiliation1Label, constraints7);
    constraints7.gridwidth = 0;
    printedPane.add(this.employeeAffiliation1TextField, constraints7);
    constraints7.gridwidth = -1;
    printedPane.add(this.employeeAffiliation2Label, constraints7);
    constraints7.gridwidth = 0;
    printedPane.add(this.employeeAffiliation2TextField, constraints7);
    constraints7.gridwidth = -1;
    printedPane.add(this.expirationDateLabel, constraints7);
    constraints7.gridwidth = 0;
    printedPane.add(this.expirationDateTextField, constraints7);
    constraints7.gridwidth = -1;
    printedPane.add(this.agencyCardSerialNumberLabel, constraints7);
    constraints7.gridwidth = 0;
    printedPane.add(this.agencyCardSerialNumberTextField, constraints7);
    constraints7.gridwidth = -1;
    printedPane.add(this.issuerIDLabel, constraints7);
    constraints7.gridwidth = 0;
    printedPane.add(this.issuerIDTextField, constraints7);
    constraints7.gridwidth = -1;
    printedPane.add(new JLabel(""), constraints7);
    constraints7.gridwidth = 0;
    constraints7.anchor = 13;
    printedPane.add(this.generatePrintedButton, constraints7);
    constraints7.gridwidth = -1;
    printedPane.add(new JLabel(""), constraints7);
    constraints7.gridwidth = 0;
    constraints7.anchor = 13;
    printedPane.add(this.savePrintedButton, constraints7);
    this.printedPanel = new JPanel(new BorderLayout());
    this.printedPanel.add(printedPane, "First");
    this.tabs.add(this.printedPanel, "Printed Info");
    JPanel secObjPane = new JPanel();
    secObjPane.setLayout(gridbag);
    this.cccHashTextField = new JTextField(50);
    this.cccHashTextField.setEditable(false);
    this.chuidHashTextField = new JTextField(50);
    this.chuidHashTextField.setEditable(false);
    this.pivCertHashTextField = new JTextField(50);
    this.pivCertHashTextField.setEditable(false);
    this.finger1HashTextField = new JTextField(50);
    this.finger1HashTextField.setEditable(false);
    this.printedHashTextField = new JTextField(50);
    this.printedHashTextField.setEditable(false);
    this.faceHashTextField = new JTextField(50);
    this.faceHashTextField.setEditable(false);
    this.sigCertHashTextField = new JTextField(50);
    this.sigCertHashTextField.setEditable(false);
    this.keyMgmtCertHashTextField = new JTextField(50);
    this.keyMgmtCertHashTextField.setEditable(false);
    this.cardAuthCertHashTextField = new JTextField(50);
    this.cardAuthCertHashTextField.setEditable(false);
    this.cccHashLabel = new JLabel("CCC: ");
    this.chuidHashLabel = new JLabel("CHUID: ");
    this.pivCertHashLabel = new JLabel("PIV Auth Cert: ");
    this.finger1HashLabel = new JLabel("Fingerprint 1: ");
    this.printedHashLabel = new JLabel("Printed Info: ");
    this.faceHashLabel = new JLabel("Facial Image: ");
    this.sigCertHashLabel = new JLabel("Signature Cert: ");
    this.keyMgmtCertHashLabel = new JLabel("Key Mgmt Cert: ");
    this.cardAuthCertHashLabel = new JLabel("Card Auth. Cert: ");
    this.setHashButton = new JButton("Set Hashes");
    this.setHashButton.addActionListener(this);
    this.setHashButton.setActionCommand("set hashes");
    this.generateSecObjButton = new JButton("Generate");
    this.generateSecObjButton.addActionListener(this);
    this.generateSecObjButton.setActionCommand("generate secobj");
    this.generateSecObjButton.setEnabled(false);
    this.saveSecObjButton = new JButton("Save");
    this.saveSecObjButton.addActionListener(this);
    this.saveSecObjButton.setActionCommand("save secobj");
    this.saveSecObjButton.setEnabled(false);
    GridBagConstraints constraints8 = new GridBagConstraints();
    constraints8.gridwidth = 23;
    constraints8.anchor = 17;
    secObjPane.add(this.cccHashLabel, constraints8);
    constraints8.gridwidth = 0;
    secObjPane.add(this.cccHashTextField, constraints8);
    constraints8.gridwidth = -1;
    secObjPane.add(this.chuidHashLabel, constraints8);
    constraints8.gridwidth = 0;
    secObjPane.add(this.chuidHashTextField, constraints8);
    constraints8.gridwidth = -1;
    secObjPane.add(this.pivCertHashLabel, constraints8);
    constraints8.gridwidth = 0;
    secObjPane.add(this.pivCertHashTextField, constraints8);
    constraints8.gridwidth = -1;
    secObjPane.add(this.finger1HashLabel, constraints8);
    constraints8.gridwidth = 0;
    secObjPane.add(this.finger1HashTextField, constraints8);
    constraints8.gridwidth = -1;
    secObjPane.add(this.printedHashLabel, constraints8);
    constraints8.gridwidth = 0;
    secObjPane.add(this.printedHashTextField, constraints8);
    constraints8.gridwidth = -1;
    secObjPane.add(this.faceHashLabel, constraints8);
    constraints8.gridwidth = 0;
    secObjPane.add(this.faceHashTextField, constraints8);
    constraints8.gridwidth = -1;
    secObjPane.add(this.sigCertHashLabel, constraints8);
    constraints8.gridwidth = 0;
    secObjPane.add(this.sigCertHashTextField, constraints8);
    constraints8.gridwidth = -1;
    secObjPane.add(this.keyMgmtCertHashLabel, constraints8);
    constraints8.gridwidth = 0;
    secObjPane.add(this.keyMgmtCertHashTextField, constraints8);
    constraints8.gridwidth = -1;
    secObjPane.add(this.cardAuthCertHashLabel, constraints8);
    constraints8.gridwidth = 0;
    secObjPane.add(this.cardAuthCertHashTextField, constraints8);
    secObjPane.add(new JLabel(""), constraints8);
    constraints8.gridwidth = 0;
    constraints8.anchor = 13;
    secObjPane.add(this.setHashButton, constraints8);
    constraints8.gridwidth = -1;
    secObjPane.add(new JLabel(""), constraints8);
    constraints8.gridwidth = 0;
    secObjPane.add(this.generateSecObjButton, constraints8);
    constraints8.gridwidth = -1;
    secObjPane.add(new JLabel(""), constraints8);
    constraints8.gridwidth = 0;
    secObjPane.add(this.saveSecObjButton, constraints8);
    this.secobjPanel = new JPanel(new BorderLayout());
    this.secobjPanel.add(secObjPane, "First");
    this.tabs.add(this.secobjPanel, "Security Object");
    sendOutputMessage("PersoUI Console Output Window");
    this.split = new JSplitPane(0, this.tabs, this.output);
    this.split.setResizeWeight(1.0d);
    getContentPane().add(this.split);
    setDefaultLookAndFeelDecorated(true);
    pack();
    setDefaultCloseOperation(3);
    setSize(840, 852);
    getSplitPane().setDividerLocation(getHeight() - 300);
    setVisible(true);
  }

  /**
   * The entry point of the application. Executes the main logic of the program by initializing the
   * application interface.
   *
   * @param args Command-line arguments passed to the program.
   */
  public static void main(String[] args) {
    new PersoUI();
  }

  /**
   * Adds a listener to receive output messages.
   *
   * @param listener the {@code OutputMessageListener} to be added
   */
  @Override // com.tvec.utility.displays.output.OutputMessageSender
  public void addOutputMessageListener(OutputMessageListener listener) {
    this.outputListeners.add(listener);
  }

  /**
   * Removes the specified {@link OutputMessageListener} from the list of listeners. This ensures
   * the listener will no longer receive output messages from the sender.
   *
   * @param listener the {@link OutputMessageListener} to be removed
   */
  @Override // com.tvec.utility.displays.output.OutputMessageSender
  public void deleteOutputMessageListener(OutputMessageListener listener) {
    this.outputListeners.remove(listener);
  }

  /**
   * Sends an output message constructed from the given string input.
   *
   * @param message the message text to be sent as an output message
   */
  public void sendOutputMessage(String message) {
    sendOutputMessage(new OutputMessage(message));
  }

  /**
   * Sends the specified output message to all registered output listeners.
   *
   * @param om the {@link OutputMessage} to be sent to the listeners
   */
  @Override // com.tvec.utility.displays.output.OutputMessageSender
  public void sendOutputMessage(OutputMessage om) {
    for (int i = 0; i < this.outputListeners.size(); i++) {
      this.outputListeners.elementAt(i).handleMessage(this, om);
    }
  }

  /**
   * Handles an output message sent by an {@link OutputMessageSender}.
   *
   * @param oms the sender of the output message
   * @param om the output message to be processed
   */
  @Override // com.tvec.utility.displays.output.OutputMessageListener
  public void handleMessage(OutputMessageSender oms, OutputMessage om) {
    sendOutputMessage(om);
  }

  /**
   * Initializes the user interface components and sets up the tabbed pane with its contents.
   *
   * <p>This method creates a `JTabbedPane` to manage different sections of the UI and adds an
   * `AutoCreateTabPane` instance to it, which listens for output messages from this class.
   * Additionally, it initializes an `Output` instance and registers it as an output message
   * listener.
   *
   * <p>Responsibilities: - Instantiates and configures the main tabbed pane for the user interface.
   * - Attaches an `AutoCreateTabPane` to the tabbed pane under the title "Auto Create". -
   * Configures the `Output` object to handle output messages. - Adds the `Output` object as a
   * listener to relay output messages from this class.
   */
  private void init() {
    this.tabs = new JTabbedPane();
    AutoCreateTabPane autoCreatePane = new AutoCreateTabPane();
    autoCreatePane.addOutputMessageListener(this);
    this.tabs.add(autoCreatePane, "Auto Create");
    this.output = new Output();
    addOutputMessageListener(this.output);
  }

  /**
   * Saves the CHUID (Cardholder Unique Identifier) data to a file selected by the user through a
   * file chooser dialog. Writes the signed CHUID bytes to the selected file and provides feedback
   * on the success or failure of the operation.
   *
   * @throws IOException if an I/O error occurs during the file writing process.
   */
  private void saveChuid() throws IOException {
    int returnVal = this.fc.showSaveDialog(this.split);
    if (returnVal != 0) {
      return;
    }
    try {
      FileOutputStream fos = new FileOutputStream(this.fc.getSelectedFile());
      fos.write(this.signedChuidBytes);
      fos.close();
      sendOutputMessage("CHUID written to " + this.fc.getSelectedFile().getName());
    } catch (FileNotFoundException e) {
      sendOutputMessage("Could not open the specified file for writing.\n" + e);
    } catch (IOException e2) {
      sendOutputMessage("The file opened, but I could now write to it.\n" + e2);
    }
  }

  /**
   * Generates a FASC-N (Federal Agency Smart Credential Number) using the input fields provided by
   * the user. Parses and validates input fields to construct the corresponding FASC-N object, and
   * updates the UI accordingly.
   *
   * <p>The method performs the following actions: - Reads values from text fields in the UI
   * corresponding to various FASC-N components, such as agency code, system code, credential
   * number, and others. - Validates the input values with error handling. If a value is invalid, an
   * error message is displayed, and the associated label is colored red to highlight the issue. -
   * Constructs a FASC-N object if all inputs are valid, updates the FASC-N text area with the
   * generated FASC-N data in hexadecimal format, and enables related UI components (e.g., enabling
   * a button for generating a CHUID). - Outputs success or error messages to indicate the status of
   * FASC-N creation.
   *
   * @throws NumberFormatException if the user input for parsing into numbers fails unexpectedly.
   */
  private void generateFascn() throws NumberFormatException {
    sendOutputMessage("Attempting to construct a FASC-N with the given data...");
    int agencyCode = 0;
    int systemCode = 0;
    int credentialNumber = 0;
    int credentialSeries = 0;
    int individualCredentialIssue = 0;
    long personIdentifier = 0;
    int organizationalCategory = 0;
    int organizationalIdentifier = 0;
    int associationCategory = 0;
    try {
      agencyCode = Integer.parseInt(this.agencyCodeTextField.getText());
      this.agencyCodeLabel.setForeground(Color.BLACK);
    } catch (Exception e) {
      sendOutputMessage("Invalid Agency Code...\n" + e);
      this.agencyCodeLabel.setForeground(Color.RED);
    }
    try {
      systemCode = Integer.parseInt(this.systemCodeTextField.getText());
      this.systemCodeLabel.setForeground(Color.BLACK);
    } catch (Exception e2) {
      sendOutputMessage("Invalid System Code...\n" + e2);
      this.systemCodeLabel.setForeground(Color.RED);
    }
    try {
      credentialNumber = Integer.parseInt(this.credentialNumberTextField.getText());
      this.credentialNumberLabel.setForeground(Color.BLACK);
    } catch (Exception e3) {
      sendOutputMessage("Invalid Credential Number...\n" + e3);
      this.credentialNumberLabel.setForeground(Color.RED);
    }
    try {
      credentialSeries = Integer.parseInt(this.credentialSeriesTextField.getText());
      this.credentialSeriesLabel.setForeground(Color.BLACK);
    } catch (Exception e4) {
      sendOutputMessage("Invalid Credential Series Number...\n" + e4);
      this.credentialSeriesLabel.setForeground(Color.RED);
    }
    try {
      individualCredentialIssue =
          Integer.parseInt(this.individualCredentialIssueTextField.getText());
      this.individualCredentialIssueLabel.setForeground(Color.BLACK);
    } catch (Exception e5) {
      sendOutputMessage("Invalid Individual Credential Issue Number...\n" + e5);
      this.individualCredentialIssueLabel.setForeground(Color.RED);
    }
    try {
      personIdentifier = Long.parseLong(this.personIdentifierTextField.getText());
      this.personIdentifierLabel.setForeground(Color.BLACK);
    } catch (Exception e6) {
      sendOutputMessage("Invalid Person Identifier...\n" + e6);
      this.personIdentifierLabel.setForeground(Color.RED);
    }
    try {
      organizationalCategory = Integer.parseInt(this.organizationalCategoryTextField.getText());
      this.organizationalCategoryLabel.setForeground(Color.BLACK);
    } catch (Exception e7) {
      sendOutputMessage("Invalid Organizational Category Number...\n" + e7);
      this.organizationalCategoryLabel.setForeground(Color.RED);
    }
    try {
      organizationalIdentifier = Integer.parseInt(this.organizationalIdentifierTextField.getText());
      this.organizationalIdentifierLabel.setForeground(Color.BLACK);
    } catch (Exception e8) {
      sendOutputMessage("Invalid Organizational Identifier Number...\n" + e8);
      this.organizationalIdentifierLabel.setForeground(Color.RED);
    }
    try {
      associationCategory = Integer.parseInt(this.associationCategoryTextField.getText());
      this.associationCategoryLabel.setForeground(Color.BLACK);
    } catch (Exception e9) {
      sendOutputMessage("Invalid Association Category Number...\n" + e9);
      this.associationCategoryLabel.setForeground(Color.RED);
    }
    try {
      this.fascn =
          new FASCN(
              agencyCode,
              systemCode,
              credentialNumber,
              credentialSeries,
              individualCredentialIssue,
              personIdentifier,
              organizationalCategory,
              organizationalIdentifier,
              associationCategory);
      this.fascnTextArea.setText(Utility.byteArray2HexString(this.fascn.getBytes()));
      this.generateChuidButton.setEnabled(true);
      this.generateChuidButton.setToolTipText(
          "Click this once you have entred the GUID and Expiration Date.");
      sendOutputMessage("FASC-N created successfully!");
    } catch (Exception e10) {
      sendOutputMessage("FASC-N generation failed...");
      sendOutputMessage(e10.toString());
    }
  }

  /**
   * Generates a Card Holder Unique Identifier (CHUID) using the provided input data. The method
   * checks if the necessary content signing certificate is present before proceeding. If not, it
   * prompts the user to configure the certificate accordingly.
   *
   * <p>Once invoked, it attempts to create a CHUID object with the provided input values, and if
   * successful, proceeds to sign the CHUID. In the case of an error during CHUID generation, a
   * message is displayed indicating the failure, along with the exception details.
   *
   * <p>Pre-requisites: - The content signing certificate must be configured in the Crypto Provider
   * tab.
   *
   * <p>Error messaging and status updates are sent to the user via the `sendOutputMessage` method.
   */
  private void generateChuid() {
    if (this.contentSignerCert == null) {
      sendOutputMessage(
          "Please navigate to the Crypto Provider tab and indicate the content signing certificate before proceeding.");
      return;
    }
    sendOutputMessage("Attempting to generate the CHUID...");
    try {
      this.chuid =
          new CHUID(
              this.fascn,
              this.guidTextField.getText(),
              this.chuidExpirationDateTextField.getText(),
              this.keystoreTypeTextField.getText(),
              this.keystorePathTextField.getText(),
              new String(this.keystorePasswordField.getPassword()),
              this.contentSignerAliasTextField.getText(),
              new String(this.contentSignerPrivateKeyPasswordField.getPassword()));
      sendOutputMessage("Successfully created a CHUID!!");
      signChuid();
    } catch (Exception e) {
      sendOutputMessage("Could not generate CHUID.  Double check the input data.\n" + e);
    }
  }

  /**
   * Signs the CHUID (Cardholder Unique Identifier). This method attempts to sign the CHUID and
   * prepares it for saving. Once signed, the save button is enabled, and the tooltip text is
   * updated to indicate that the CHUID can now be saved to a file. If signing fails, an error
   * message is output.
   */
  private void signChuid() {
    sendOutputMessage("Trying to sign the CHUID...");
    try {
      this.signedChuidBytes = this.chuid.getBytes();
      sendOutputMessage("CHUID signed and ready to save to a file!!");
      this.saveChuidButton.setEnabled(true);
      this.saveChuidButton.setToolTipText("Click here to save the CHUID to a file.");
    } catch (Exception e) {
      sendOutputMessage("signing of the chuid failed...\n" + e);
    }
  }

  /**
   * Opens a file chooser dialog to allow the user to browse and select a keystore file. If the user
   * selects a file and confirms their choice, the method sets the selected file's absolute path in
   * the keystore path text field.
   *
   * <p>The method interacts with a file chooser component (`fc`) to present the dialog and manage
   * file selection. This process updates the text field UI element with the chosen file's path.
   */
  private void browseKeystore() {
    File f;
    int returnVal = this.fc.showOpenDialog(this);
    if (returnVal == 0 && (f = this.fc.getSelectedFile()) != null) {
      this.keystorePathTextField.setText(f.getAbsolutePath());
    }
  }

  /**
   * Generates a CCC (presumably a cryptographic card certificate) based on the text provided in the
   * cardIDTextField. It attempts to create a new CCC object using the current card ID and processes
   * its bytes to confirm successful generation.
   *
   * <p>If the CCC generation is successful, a success message is sent, and the saveCCCButton is
   * enabled, allowing the user to save the generated CCC. If the generation fails due to an
   * exception, an error message is displayed indicating the failure.
   *
   * <p>This method relies on a valid card ID being provided through the cardIDTextField for proper
   * execution.
   */
  private void generateCCC() {
    sendOutputMessage("Attempting to generate a CCC...");
    try {
      this.ccc = new CCC(this.cardIDTextField.getText());
      this.ccc.getBytes();
      sendOutputMessage("Generation successful!!");
      this.saveCCCButton.setEnabled(true);
    } catch (Exception e) {
      sendOutputMessage("Generation failed.  Check the validity of the Card ID...");
    }
  }

  /**
   * Saves the CCC data to a file selected by the user through a file chooser dialog.
   *
   * <p>This method initiates a save file dialog for the user to specify the destination file. If
   * the user confirms the file selection, the content of the CCC is written to the specified file.
   * Proper error messages are displayed if the file cannot be opened or written to.
   *
   * @throws IOException if an I/O error occurs during the save process.
   */
  private void saveCCC() throws IOException {
    int returnVal = this.fc.showSaveDialog(this);
    if (returnVal != 0) {
      return;
    }
    try {
      FileOutputStream fos = new FileOutputStream(this.fc.getSelectedFile());
      fos.write(this.ccc.getBytes());
      fos.close();
    } catch (FileNotFoundException e) {
      sendOutputMessage("Could not open the specified file for writing.\n" + e);
    } catch (IOException e2) {
      sendOutputMessage("The file opened, but I could now write to it.\n" + e2);
    }
    sendOutputMessage("CCC written to " + this.fc.getSelectedFile().getName());
  }

  /**
   * Generates an X.509 certificate based on the input parameters and selected options in the UI.
   *
   * <p>This method validates the required preconditions before generating the certificate: -
   * Ensures a CA signing certificate is loaded. - Ensures a FASC-N is created if specific
   * certificate types are selected.
   *
   * <p>The certificate type is determined based on the selected radio button, and the key data is
   * retrieved from either a text field or a file. It constructs the Distinguished Name (DN) using
   * the user-provided input for various fields and initializes a new certificate with these
   * details.
   *
   * <p>After successful generation, the generated certificate is saved to the corresponding member
   * variable based on the selected certificate type, and the "Save Certificate" button is enabled.
   *
   * <p>If any errors occur during the process, an error message is displayed and the stack trace is
   * logged for debugging purposes.
   *
   * @throws IOException if there is an issue reading the key data from the specified file path
   */
  private void generateCert() throws IOException {
    PublicKey key;
    if (this.caCert == null) {
      sendOutputMessage("Please load a CA signing certificate on the Crypto Provider pane.");
      return;
    }
    if ((this.pivCertRadioButton.isSelected() || this.cardAuthCertRadioButton.isSelected())
        && this.fascn == null) {
      sendOutputMessage("Please navigate to the CHUID tab and create a FASC-N before continuing.");
      return;
    }
    sendOutputMessage("Attempting to generate the certificate...");
    int certType = 1;
    if (this.sigCertRadioButton.isSelected()) {
      certType = 3;
    } else if (this.cardAuthCertRadioButton.isSelected()) {
      certType = 2;
    } else if (this.keyMgmtCertRadioButton.isSelected()) {
      certType = 4;
    }
    try {
      if (this.keyFromTextFieldRadioButton.isSelected()) {
        key = X509Cert.pivRSA_to_PublicKey(Utility.hexString2ByteArray(this.keyTextArea.getText()));
      } else {
        FileInputStream fis = new FileInputStream(this.keyPathTextField.getText());
        byte[] ba = new byte[fis.available()];
        fis.read(ba);
        fis.close();
        key = X509Cert.pivRSA_to_PublicKey(ba);
      }
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
      X500NameBuilder nameBuilder = new X500NameBuilder(BCStyle.INSTANCE);
      nameBuilder.addRDN(BCStyle.C, this.dnCountryTextField.getText());
      nameBuilder.addRDN(
          BCStyle.OU, this.dnOrganizationalUnitTextField.getText() + X509Cert.TEST_DATA_INDICATOR);
      nameBuilder.addRDN(BCStyle.O, this.dnOrganizationTextField.getText());
      nameBuilder.addRDN(
          BCStyle.CN, this.dnCommonNameTextField.getText() + X509Cert.TEST_DATA_INDICATOR);
      X500Name name = nameBuilder.build();
      X509Cert cert =
          new X509Cert(
              certType,
              this.serialNumberTextField.getText(),
              this.signatureAlgorithmTextField.getText(),
              sdf.parse(this.validFromTextField.getText()),
              sdf.parse(this.validToTextField.getText()),
              key,
              name,
              this.crlHttpURITextField.getText(),
              this.crlLdapURITextField.getText(),
              this.aiaHttpURITextField.getText(),
              this.aiaLdapURITextField.getText(),
              this.aiaOcspURITextField.getText(),
              this.keystoreTypeTextField.getText(),
              this.keystorePathTextField.getText(),
              new String(this.keystorePasswordField.getPassword()),
              this.caAliasTextField.getText(),
              new String(this.privateKeyPasswordField.getPassword()),
              this.fascn,
              this.dnUPNTextField.getText(),
              this.emailTextField.getText());
      if (this.pivCertRadioButton.isSelected()) {
        this.pivAuthCert = cert;
      } else if (this.sigCertRadioButton.isSelected()) {
        this.sigCert = cert;
      } else if (this.cardAuthCertRadioButton.isSelected()) {
        this.cardAuthCert = cert;
      } else if (this.keyMgmtCertRadioButton.isSelected()) {
        this.keyMgmtCert = cert;
      }
      this.saveCertButton.setEnabled(true);
      sendOutputMessage("Generation successful!");
    } catch (Exception e) {
      sendOutputMessage("Failed to generate certificate...\n" + e);
      e.printStackTrace();
    }
  }

  /**
   * Saves the selected certificate to a file chosen by the user through a file chooser dialog.
   *
   * <p>This method prompts the user with a save file dialog to select a file location where the
   * certificate will be saved. Based on the selected radio button in the UI, one of the available
   * certificates (authentication, signature, card authentication, or key management certificate) is
   * chosen and written to the specified file.
   *
   * <p>If an error occurs during the file operation, appropriate messages are displayed to inform
   * the user about the issue. The possible errors include the following: - File not found or cannot
   * be opened for writing. - IOError while writing the certificate. - Certificate generation
   * failure.
   *
   * @throws IOException if an I/O error occurs while attempting to write the certificate.
   */
  private void saveCert() throws IOException {
    int returnVal = this.fc.showSaveDialog(this);
    if (returnVal != 0) {
      return;
    }
    X509Cert cert = this.pivAuthCert;
    if (this.sigCertRadioButton.isSelected()) {
      cert = this.sigCert;
    } else if (this.cardAuthCertRadioButton.isSelected()) {
      cert = this.cardAuthCert;
    } else if (this.keyMgmtCertRadioButton.isSelected()) {
      cert = this.keyMgmtCert;
    }
    try {
      FileOutputStream fos = new FileOutputStream(this.fc.getSelectedFile());
      fos.write(cert.getBytes());
      fos.close();
      sendOutputMessage("Certificate written to " + this.fc.getSelectedFile().getName());
    } catch (FileNotFoundException e) {
      sendOutputMessage("Could not open the specified file for writing.\n" + e);
    } catch (IOException e2) {
      sendOutputMessage("The file opened, but I could now write to it.\n" + e2);
    } catch (Exception e3) {
      sendOutputMessage("Couldn't correctly generate the certificate\n" + e3);
      e3.printStackTrace();
    }
  }

  /**
   * Updates the UI and internal state based on selecting a facial image.
   *
   * <p>This method sets appropriate labels for the facial image's creation date and data file path,
   * toggles the save biometric button's enabled state depending on whether the facial image data is
   * present, and sends an output message indicating the selection of a facial image.
   *
   * <p>Actions performed: - Updates the text of the biometric creation date label to indicate it
   * corresponds to a facial image. - Updates the text of the bio-data path label to specify the
   * path to the facial image data file. - Enables or disables the save biometric button based on
   * whether a facial image is currently selected. - Sends a message to output indicating the
   * selection of a face.
   */
  private void selectFace() {
    this.biometricCreationDateLabel.setText("Facial Image creation date: ");
    this.bioDataPathLabel.setText("Path to facial image data file: ");
    this.saveBiometricButton.setEnabled(this.face == null);
    sendOutputMessage("face");
  }

  /**
   * Updates the UI components related to the fingerprint selection. This method sets the labels and
   * button state to reflect the current status of the fingerprint data. Additionally, it sends an
   * output message indicating the fingerprint selection process.
   *
   * <p>- Sets the label showing the path to the fingerprint data file. - Updates the label to
   * display the fingerprint creation date. - Toggles the state of the save button based on the
   * availability of fingerprint data. - Sends an output message with "fingers" as content for
   * external handling.
   */
  private void selectFinger() {
    this.bioDataPathLabel.setText("Path to the fingerprint data file: ");
    this.biometricCreationDateLabel.setText("Fingerprint creation date: ");
    this.saveBiometricButton.setEnabled(this.fingers == null);
    sendOutputMessage("fingers");
  }

  /**
   * Handles the action of browsing for a biometric data file. This method allows a user to select a
   * file using a file chooser dialog. If a file is selected and the action is confirmed, the
   * absolute path of the selected file is set into the biometric data path text field.
   */
  private void browseBio() {
    File f;
    int returnVal = this.fc.showOpenDialog(this);
    if (returnVal == 0 && (f = this.fc.getSelectedFile()) != null) {
      this.biometricDataPathTextField.setText(f.getAbsolutePath());
    }
  }

  /**
   * Generates a biometric data object based on the provided input data and settings.
   *
   * <p>This method attempts to create a Biometric object using the input fields and settings
   * present in the user interface. It reads biometric data from a file specified by the user,
   * validates required inputs, and creates the appropriate biometric data object (either for
   * fingers or face). If the operation is successful, the biometric data will be saved and the save
   * button will be enabled. Any errors encountered during the process will be logged to the output
   * stream.
   *
   * @throws IOException if there is an issue reading the biometric data file.
   */
  private void generateBio() throws IOException {
    sendOutputMessage("Attempting to generate the biometric data object...");
    if (this.fascn == null) {
      sendOutputMessage(
          "FASC-N required.  Please select the CHUID tab and generate one before proceeding.");
      return;
    }
    byte[] bArr = new byte[0];
    try {
      FileInputStream fis = new FileInputStream(this.biometricDataPathTextField.getText());
      byte[] bioData = new byte[fis.available()];
      fis.read(bioData);
      fis.close();
      try {
        Biometric bio =
            new Biometric(
                this.fingersRadioButton.isSelected() ? 1 : 2,
                bioData,
                this.biometricCreationDateTextField.getText(),
                this.validityStartDateTextField.getText(),
                Integer.parseInt(this.monthsValidTextField.getText()),
                this.fascn,
                this.keystoreTypeTextField.getText(),
                this.keystorePathTextField.getText(),
                new String(this.keystorePasswordField.getPassword()),
                this.contentSignerAliasTextField.getText(),
                new String(this.contentSignerPrivateKeyPasswordField.getPassword()),
                this.includeCertCheckBox.isSelected());
        if (this.fingersRadioButton.isSelected()) {
          this.fingers = bio;
        } else {
          this.face = bio;
        }
        sendOutputMessage("Biometric data element successfully created!!");
        this.saveBiometricButton.setEnabled(true);
      } catch (Exception e) {
        sendOutputMessage("Could not generate the biometric file:\n" + e);
      }
    } catch (Exception e2) {
      sendOutputMessage("Could not open the file specified as the first fingerprint.\n" + e2);
    }
  }

  /**
   * Saves biometric data to a file selected by the user through a file chooser dialog.
   *
   * <p>The method first prompts the user to select a file for saving using a file chooser dialog.
   * If the user confirms the file selection, it attempts to write the biometric data (either face
   * or fingers data) to the selected file based on the user's radio button selection.
   *
   * <p>If an error occurs during the file writing process, an appropriate error message is
   * displayed.
   *
   * @throws IOException if an I/O error occurs during file operations.
   */
  private void saveBio() throws IOException {
    int returnVal = this.fc.showSaveDialog(this);
    if (returnVal != 0) {
      return;
    }
    try {
      FileOutputStream fos = new FileOutputStream(this.fc.getSelectedFile());
      if (this.faceRadioButton.isSelected()) {
        fos.write(this.face.getBytes());
      } else if (this.fingersRadioButton.isSelected()) {
        fos.write(this.fingers.getBytes());
      }
      fos.close();
      sendOutputMessage("Biometric Data Element written to " + this.fc.getSelectedFile().getName());
    } catch (FileNotFoundException e) {
      sendOutputMessage("Could not open the specified file for writing.\n" + e);
    } catch (IOException e2) {
      sendOutputMessage("The file opened, but I could now write to it.\n" + e2);
    } catch (Exception e3) {
      sendOutputMessage("Couldn't read the byte[] from the biometric data element...\n" + e3);
    }
  }

  /**
   * Generates a PrintedInfo object using data from text fields and enables the save button.
   *
   * <p>This method retrieves text inputs from several text fields, constructs a PrintedInfo object
   * with those values, and assigns it to the `printed` property. If the creation of the PrintedInfo
   * object is successful, it enables the save button and sends a success message. In case of an
   * exception, an error message is sent.
   *
   * <p>Operational flow: - Sends a message that the creation of Printed information is being
   * attempted. - Extracts text values from various input fields. - Attempts to create a PrintedInfo
   * object with these values. - If successful, sends a success message and enables the save button.
   * - If an exception occurs, sends an error message containing the exception details.
   *
   * <p>Exception Handling: - Catches any exception that occurs during the creation of the
   * PrintedInfo object and logs the error.
   */
  private void generatePrinted() {
    sendOutputMessage("Attempting to generate the Printed Data...");
    try {
      this.printed =
          new PrintedInfo(
              this.printedNameTextField.getText(),
              this.employeeAffiliation1TextField.getText(),
              this.employeeAffiliation2TextField.getText(),
              this.expirationDateTextField.getText(),
              this.agencyCardSerialNumberTextField.getText(),
              this.issuerIDTextField.getText());
      sendOutputMessage("Printed info successfully created!");
      this.savePrintedButton.setEnabled(true);
    } catch (Exception e) {
      sendOutputMessage("Could not generate Printed info: \n" + e);
    }
  }

  /**
   * Saves the content of the `printed` field to a file selected by the user using a file chooser.
   * The method opens a save dialog to prompt the user to choose a file location. If the save dialog
   * is canceled, the operation is aborted. Writes the byte representation of the `printed` string
   * to the selected file. Sends an output message indicating the success or failure of the
   * file-saving operation.
   *
   * @throws IOException if an I/O error occurs during the file writing process.
   */
  private void savePrinted() throws IOException {
    int returnVal = this.fc.showSaveDialog(this);
    if (returnVal != 0) {
      return;
    }
    try {
      FileOutputStream fos = new FileOutputStream(this.fc.getSelectedFile());
      fos.write(this.printed.getBytes());
      fos.close();
      sendOutputMessage("Printed Info written to " + this.fc.getSelectedFile().getName());
    } catch (FileNotFoundException e) {
      sendOutputMessage("Could not open the specified file for writing.\n" + e);
    } catch (IOException e2) {
      sendOutputMessage("The file opened, but I could now write to it.\n" + e2);
    }
  }

  /**
   * Configures and sets cryptographic hashes for various data groups associated with the security
   * object (secobj). This includes computing and assigning hashes for data entities such as CCC,
   * CHUID, Printed Info, PIV Authentication Certificate, Digital Signature Certificate, Card
   * Authentication Certificate, Key Management Certificate, Fingerprints, and Face data, if they
   * are not null.
   *
   * <p>If any data entity is null, a warning message is displayed, indicating that the
   * corresponding hash could not be generated. If an exception occurs during the hash generation
   * process, detailed error messages are displayed for debugging purposes. The method ensures the
   * security object is initialized if it is null.
   *
   * <p>Additionally, the operation enables the "Generate Security Object" button at the end of the
   * process.
   */
  private void setHashes() {
    if (this.secobj == null) {
      initSecObj();
    }
    sendOutputMessage("Attempting to generate hashes...");
    if (this.ccc != null) {
      try {
        this.secobj.setDigest(this.ccc.getBytes(), 1);
        this.cccHashTextField.setText(Utility.byteArray2HexString(this.secobj.getDigestByDG(1)));
      } catch (Exception e) {
        sendOutputMessage("Couldn't set the hash for the CCC.\n" + e);
      }
    } else {
      sendOutputMessage("WARNING: CCC IS NULL, no hash for SEC OBJ!");
    }
    if (this.chuid != null) {
      try {
        this.secobj.setDigest(this.chuid.getBytes(), 2);
        this.chuidHashTextField.setText(Utility.byteArray2HexString(this.secobj.getDigestByDG(2)));
      } catch (Exception e2) {
        sendOutputMessage("Couldn't set the hash for the CHUID.\n" + e2);
      }
    } else {
      sendOutputMessage("WARNING: CHUID IS NULL, no hash for SEC OBJ!");
    }
    if (this.printed != null) {
      try {
        this.secobj.setDigest(this.printed.getBytes(), 6);
        this.printedHashTextField.setText(
            Utility.byteArray2HexString(this.secobj.getDigestByDG(6)));
      } catch (Exception e3) {
        sendOutputMessage("Couldn't set the hash for the Printed Info.\n" + e3);
      }
    } else {
      sendOutputMessage("WARNING: PRINTED INFO IS NULL, no hash for SEC OBJ!");
    }
    if (this.pivAuthCert != null) {
      try {
        this.secobj.setDigest(this.pivAuthCert.getBytes(), 3);
        this.pivCertHashTextField.setText(
            Utility.byteArray2HexString(this.secobj.getDigestByDG(3)));
      } catch (Exception e4) {
        sendOutputMessage("Couldn't set the hash for the PIV Auth Cert.\n" + e4);
      }
    } else {
      sendOutputMessage("WARNING: PIV AUTH CERT IS NULL, no hash for SEC OBJ!");
    }
    if (this.sigCert != null) {
      try {
        this.secobj.setDigest(this.sigCert.getBytes(), 8);
        this.sigCertHashTextField.setText(
            Utility.byteArray2HexString(this.secobj.getDigestByDG(8)));
      } catch (Exception e5) {
        sendOutputMessage("Couldn't set the hash for the Digitial Signature Cert.\n" + e5);
      }
    } else {
      sendOutputMessage("WARNING: SIGN CERT IS NULL, no hash for SEC OBJ!");
    }
    if (this.cardAuthCert != null) {
      try {
        this.secobj.setDigest(this.cardAuthCert.getBytes(), 10);
        this.cardAuthCertHashTextField.setText(
            Utility.byteArray2HexString(this.secobj.getDigestByDG(10)));
      } catch (Exception e6) {
        sendOutputMessage("Couldn't set the hash for the Card Authentication Cert.\n" + e6);
      }
    } else {
      sendOutputMessage("WARNING: CARD AUTH CERT IS NULL, no hash for SEC OBJ!");
    }
    if (this.keyMgmtCert != null) {
      try {
        this.secobj.setDigest(this.keyMgmtCert.getBytes(), 9);
        this.keyMgmtCertHashTextField.setText(
            Utility.byteArray2HexString(this.secobj.getDigestByDG(9)));
      } catch (Exception e7) {
        sendOutputMessage("Couldn't set the hash for the Key Management Cert.\n" + e7);
      }
    } else {
      sendOutputMessage("WARNING: KEY MGMT CERT IS NULL, no hash for SEC OBJ!");
    }
    if (this.fingers != null) {
      try {
        this.secobj.setDigest(this.fingers.getBytes(), 4);
        this.finger1HashTextField.setText(
            Utility.byteArray2HexString(this.secobj.getDigestByDG(4)));
      } catch (Exception e8) {
        sendOutputMessage("Couldn't set the hash for Fingerprints.\n" + e8);
      }
    } else {
      sendOutputMessage("WARNING: FINGERPRINTS DATA IS NULL, no hash for SEC OBJ!");
    }
    if (this.face != null) {
      try {
        this.secobj.setDigest(this.face.getBytes(), 7);
        this.faceHashTextField.setText(Utility.byteArray2HexString(this.secobj.getDigestByDG(7)));
      } catch (Exception e9) {
        sendOutputMessage("Couldn't set the hash for the CCC.\n" + e9);
      }
    } else {
      sendOutputMessage("WARNING: FACE DATA IS NULL, no hash for SEC OBJ!");
    }
    this.generateSecObjButton.setEnabled(true);
  }

  /**
   * Initializes the security object by setting it up with user-provided input values and disables
   * the "Generate" and "Save" buttons once the initialization is complete.
   *
   * <ul>
   *   - Reads inputs from UI components such as text fields and password fields. - Creates a new
   *   instance of the SecurityObject. - Displays messages indicating the initialization process
   *   status.
   */
  private void initSecObj() {
    sendOutputMessage("Initializing the Security Object...");
    this.secobj =
        new SecurityObject(
            this.keystoreTypeTextField.getText(),
            this.keystorePathTextField.getText(),
            new String(this.keystorePasswordField.getPassword()),
            this.contentSignerAliasTextField.getText(),
            new String(this.contentSignerPrivateKeyPasswordField.getPassword()));
    sendOutputMessage("Initialized.");
    this.generateSecObjButton.setEnabled(false);
    this.saveSecObjButton.setEnabled(false);
  }

  /**
   * Generates the Security Object and performs necessary initializations and validations.
   *
   * <p>This method ensures that the content signing certificate is provided and initializes the
   * security object if not already set. It involves: - Validating the presence of the required
   * content signing certificate. - Initializing and setting up the security object if it is null. -
   * Attempting to generate the byte representation of the security object.
   *
   * <p>If the content signing certificate is missing, the user is prompted to provide it. Upon
   * successful creation of the security object, an appropriate success message is displayed, and
   * the save button for the security object is enabled. If an error occurs during the generation
   * process, an error message is communicated to the user.
   *
   * @throws Exception if there is an error while generating the security object.
   */
  private void generateSecObj() {
    if (this.contentSignerCert == null) {
      sendOutputMessage(
          "Please navigate to the Crypto Provider tab and indicate the content signing certificate before proceeding.");
      return;
    }
    if (this.secobj == null) {
      initSecObj();
      setHashes();
    }
    try {
      this.secobj.getBytes();
      sendOutputMessage("Security Object successfully created!!");
      this.saveSecObjButton.setEnabled(true);
    } catch (Exception e) {
      sendOutputMessage("Could not generate the Signed Security Object...");
    }
  }

  /**
   * Saves the security object to a file selected through a save dialog.
   *
   * <p>This method opens a save dialog for the user to select a file location. If a file is
   * selected, the security object's byte representation is written to the file. The method provides
   * feedback on success or failure via the `sendOutputMessage` method. Multiple exceptions are
   * handled to ensure appropriate messaging is provided in case of
   */
  private void saveSecObj() throws IOException {
    int returnVal = this.fc.showSaveDialog(this);
    if (returnVal != 0) {
      return;
    }
    try {
      FileOutputStream fos = new FileOutputStream(this.fc.getSelectedFile());
      fos.write(this.secobj.getBytes());
      fos.close();
      sendOutputMessage("Security Object written to " + this.fc.getSelectedFile().getName());
    } catch (FileNotFoundException e) {
      sendOutputMessage("Could not open the specified file for writing.\n" + e);
    } catch (IOException e2) {
      sendOutputMessage("The file opened, but I could now write to it.\n" + e2);
    } catch (Exception e3) {
      sendOutputMessage("Could not get the security object:\n" + e3);
    }
  }

  /**
   * Loads a keystore and retrieves the associated certificates and private keys based on the
   * specified aliases and passwords. The method initializes the keystore, retrieves the CA
   * certificate and private key, as well as the content signer certificate and private key.
   *
   * <p>Exceptions are handled internally, and appropriate error messages are displayed to the user
   * for invalid inputs, incorrect passwords, or missing required data.
   *
   * <p>Throws: - NoSuchAlgorithmException: If the cryptographic algorithm required to access the
   * keystore is not available. - UnrecoverableKeyException: If a key in the keystore cannot be
   * recovered (e.g., incorrect password). - IOException: If an I/O error occurs while loading the
   * keystore file. - CertificateException: If an issue occurs while loading certificates from the
   * keystore. - KeyStoreException: If the keystore type is invalid or another keystore-related
   * error occurs.
   */
  private void loadKeystore()
      throws NoSuchAlgorithmException,
          UnrecoverableKeyException,
          IOException,
          CertificateException,
          KeyStoreException {
    sendOutputMessage("Attempting to load keystore...");
    try {
      this.keyStore = KeyStore.getInstance(this.keystoreTypeTextField.getText());
      try {
        this.keyStore.load(
            new FileInputStream(this.keystorePathTextField.getText()),
            this.keystorePasswordField.getPassword());
        try {
          this.caCert =
              (X509Certificate) this.keyStore.getCertificate(this.caAliasTextField.getText());
          if (this.caCert == null) {
            sendOutputMessage(
                "Could not find the CA certificate associated with the given alias...");
            return;
          }
          try {
            this.keyStore.getKey(
                this.caAliasTextField.getText(), this.privateKeyPasswordField.getPassword());
            try {
              this.contentSignerCert =
                  (X509Certificate)
                      this.keyStore.getCertificate(this.contentSignerAliasTextField.getText());
              if (this.contentSignerCert == null) {
                sendOutputMessage(
                    "Could not find the Content Signer certificate associated with the given alias...");
                this.caCert = null;
                return;
              }
              try {
                this.keyStore.getKey(
                    this.contentSignerAliasTextField.getText(),
                    this.contentSignerPrivateKeyPasswordField.getPassword());
                sendOutputMessage("Successfully loaded the certificates and private keys!!");
              } catch (Exception e) {
                sendOutputMessage("Password for the Content Signer private key seems invalid...");
                this.contentSignerCert = null;
                this.caCert = null;
              }
            } catch (Exception e2) {
              sendOutputMessage(
                  "Could not find the Content Signer certificate associated with the given alias...");
              this.caCert = null;
            }
          } catch (Exception e3) {
            sendOutputMessage("Password for the CA private key seems invalid...");
            this.caCert = null;
          }
        } catch (Exception e4) {
          sendOutputMessage("Could not find the CA certificate associated with the given alias...");
        }
      } catch (Exception e5) {
        sendOutputMessage(
            "Could not open keystore file.  Double check the path, and the keystore password");
      }
    } catch (Exception e6) {
      sendOutputMessage("Invalid Key Store type specified...");
    }
  }

  /**
   * Configures the UI components related to key input to support loading a key from a file.
   *
   * <p>This method enables and disables specific UI fields to allow the user to input a key path
   * through a file selection workflow. When this method is invoked: - The key path text field and
   * its associated label are enabled. - The key input text area and its associated label are
   * disabled. - The key path browse button is enabled.
   *
   * <p>This configuration is typically used when the user's key input is expected to come from a
   * file rather than manual entry.
   */
  private void keyFromFile() {
    this.keyPathTextField.setEnabled(true);
    this.keyPathLabel.setEnabled(true);
    this.keyTextArea.setEnabled(false);
    this.keyLabel.setEnabled(false);
    this.keyPathBrowseButton.setEnabled(true);
  }

  /**
   * Disables and enables specific UI components related to key input configuration. This method
   * adjusts the state of various UI elements when switching input modes to allow the user to input
   * a key directly via a text field. Specifically: - Disables components related to file-based key
   * input. - Enables text field and label components for manual key input. Responsibilities: -
   * Disables the key path text field,
   */
  private void keyFromTextField() {
    this.keyPathTextField.setEnabled(false);
    this.keyPathLabel.setEnabled(false);
    this.keyTextArea.setEnabled(true);
    this.keyLabel.setEnabled(true);
    this.keyPathBrowseButton.setEnabled(false);
  }

  /**
   * Opens a file chooser dialog to select a file and updates the key path text field with the
   * absolute path of the selected file if a valid file is chosen. The method utilizes a file
   * chooser dialog (`fc`) to allow the user to browse and select a file. If the user selects a file
   * and confirms the selection, the absolute path of the chosen file is set in the
   * `keyPathTextField`.
   */
  private void browseKey() {
    File f;
    int returnVal = this.fc.showOpenDialog(this);
    if (returnVal == 0 && (f = this.fc.getSelectedFile()) != null) {
      this.keyPathTextField.setText(f.getAbsolutePath());
    }
  }

  /**
   * Handles the action event by invoking the implemented logic and capturing any exceptions that
   * may occur during processing. Displays an error message to the user in case of an exception.
   *
   * @param e the ActionEvent object containing event details, such as the source of the event and
   *     its associated command.
   */
  public void actionPerformed(ActionEvent e) {
    try {
      actionPerformedImpl(e);
    } catch (NoSuchAlgorithmException
        | UnrecoverableKeyException
        | NumberFormatException
        | IOException
        | CertificateException
        | KeyStoreException ex) {
      ex.printStackTrace();
      JOptionPane.showMessageDialog(
          this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  /**
   * Handles various actions triggered by user interactions or commands. This method performs
   * specific operations based on the action command contained in the provided ActionEvent object.
   *
   * @param e the ActionEvent containing the action command that determines which operation to
   *     perform. The action command should match one of the predefined command strings such as
   *     "generate fascn", "generate chuid", "keystore browse", and others.
   * @throws NoSuchAlgorithmException if a particular cryptographic algorithm is requested but not
   *     available in the specific environment.
   * @throws UnrecoverableKeyException if a key in the keystore cannot be recovered.
   * @throws NumberFormatException if a required number format is invalid.
   * @throws IOException if there is an input/output issue during execution.
   * @throws CertificateException if there is an issue processing certificates.
   * @throws KeyStoreException if there is an error related to the keystore access or manipulation.
   */
  private void actionPerformedImpl(ActionEvent e)
      throws NoSuchAlgorithmException,
          UnrecoverableKeyException,
          NumberFormatException,
          IOException,
          CertificateException,
          KeyStoreException {
    if ("generate fascn".equals(e.getActionCommand())) {
      generateFascn();
      return;
    }
    if ("generate chuid".equals(e.getActionCommand())) {
      generateChuid();
      return;
    }
    if ("keystore browse".equals(e.getActionCommand())) {
      browseKeystore();
      return;
    }
    if ("keystore load".equals(e.getActionCommand())) {
      loadKeystore();
      return;
    }
    if ("sign chuid".equals(e.getActionCommand())) {
      signChuid();
      return;
    }
    if ("save chuid".equals(e.getActionCommand())) {
      saveChuid();
      return;
    }
    if ("generate ccc".equals(e.getActionCommand())) {
      generateCCC();
      return;
    }
    if ("save ccc".equals(e.getActionCommand())) {
      saveCCC();
      return;
    }
    if ("generate cert".equals(e.getActionCommand())) {
      generateCert();
      return;
    }
    if ("save cert".equals(e.getActionCommand())) {
      saveCert();
      return;
    }
    if ("browse bio".equals(e.getActionCommand())) {
      browseBio();
      return;
    }
    if ("generate bio".equals(e.getActionCommand())) {
      generateBio();
      return;
    }
    if ("save bio".equals(e.getActionCommand())) {
      saveBio();
      return;
    }
    if ("generate printed".equals(e.getActionCommand())) {
      generatePrinted();
      return;
    }
    if ("save printed".equals(e.getActionCommand())) {
      savePrinted();
      return;
    }
    if ("set hashes".equals(e.getActionCommand())) {
      setHashes();
      return;
    }
    if ("generate secobj".equals(e.getActionCommand())) {
      generateSecObj();
      return;
    }
    if ("save secobj".equals(e.getActionCommand())) {
      saveSecObj();
      return;
    }
    if ("browse key".equals(e.getActionCommand())) {
      browseKey();
      return;
    }
    if ("key from file".equals(e.getActionCommand())) {
      keyFromFile();
      return;
    }
    if ("key from text field".equals(e.getActionCommand())) {
      keyFromTextField();
    } else if ("face button".equals(e.getActionCommand())) {
      selectFace();
    } else if ("finger button".equals(e.getActionCommand())) {
      selectFinger();
    }
  }

  /** */
  private void addLabelTextRows(
      JLabel[] labels, JTextField[] textFields, GridBagLayout gridbag, Container container) {
    GridBagConstraints c = new GridBagConstraints();
    int numLabels = labels.length;
    for (int i = 0; i < numLabels; i++) {
      c.gridwidth = -1;
      c.anchor = 13;
      container.add(labels[i], c);
      c.gridwidth = 0;
      c.anchor = 17;
      container.add(textFields[i], c);
    }
  }

  /**
   * Retrieves the split pane associated with the user interface.
   *
   * @return the {@code JSplitPane} instance representing the split pane layout of the application
   */
  public JSplitPane getSplitPane() {
    return this.split;
  }

  /** */
  public void keyPressed(KeyEvent e) {
    if ((e.getKeyCode() == 90 && (e.getModifiersEx() & 128) != 0 && (e.getModifiersEx() & 64) == 0)
        || (e.getKeyCode() == 8
            && (e.getModifiersEx() & 512) != 0
            && (e.getModifiersEx() & 64) == 0)) {
      try {
        this.undo.undo();
      } catch (CannotUndoException e2) {
      }
    } else if (e.getKeyCode() == 89 && (e.getModifiersEx() & 128) != 0) {
      try {
        this.undo.redo();
      } catch (CannotRedoException e3) {
      }
    }
  }

  /**
   * Handles the event when a key is released.
   *
   * @param e the {@link KeyEvent} containing information about the released key
   */
  public void keyReleased(KeyEvent e) {}

  /**
   * Handles the event when a key is typed. This method is intended to define what actions should
   * occur when a key typing action is detected.
   *
   * @param e the KeyEvent object that contains information about the key event
   */
  public void keyTyped(KeyEvent e) {}

  /**
   * Invoked when an undoable edit event occurs. This method adds the edit associated with the event
   * to the undo manager.
   *
   * @param e the {@link UndoableEditEvent} that contains the edit to be added
   */
  public void undoableEditHappened(UndoableEditEvent e) {
    this.undo.addEdit(e.getEdit());
  }
}
