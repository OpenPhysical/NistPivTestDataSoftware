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

import com.tvec.utility.UByte;
import com.tvec.utility.displays.output.OutputMessage;
import com.tvec.utility.displays.output.OutputMessageListener;
import com.tvec.utility.displays.output.OutputMessageSender;
import gov.nist.piv.data.Biometric;
import gov.nist.piv.data.CCC;
import gov.nist.piv.data.CHUID;
import gov.nist.piv.data.CbeffFormatException;
import gov.nist.piv.data.FASCN;
import gov.nist.piv.data.PrintedInfo;
import gov.nist.piv.data.SecurityObject;
import gov.nist.piv.data.X509Cert;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.PublicKey;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/* loaded from: JPIV Test Data Generator.jar:gov/nist/piv/auto_create/SampleGenerator.class */
public class SampleGenerator implements Runnable, OutputMessageSender {
  private static final Logger logger = LogManager.getLogger(SampleGenerator.class);
  private static final Logger xpathLogger = LogManager.getLogger("gov.nist.piv.xpath");
  private File outputDirectory;
  private static final int CONTAINER_COUNT = 10;
  private static final int CCC = 0;
  private static final int CHUID = 1;
  private static final int PIV_AUTH_CERT = 2;
  private static final int CARD_AUTH_CERT = 3;
  private static final int DIGITAL_SIGNATURE_CERT = 4;
  private static final int KEY_MANAGEMENT_CERT = 5;
  private static final int FINGERPRINTS = 6;
  private static final int FACIAL_IMAGE = 7;
  private static final int PRINTED_INFO = 8;
  private static final int SECURITY_OBJECT = 9;
  private Document document;
  private int samplesToCreate = 1;
  private final HashSet<SampleGeneratorEventListener> eventListeners = new HashSet<>();
  private boolean abort = false;
  private final String DIRECTORY_PREFIX = "sample_set_";
  private final Vector<OutputMessageListener> outputListeners = new Vector<>();

  @Override // com.tvec.utility.displays.output.OutputMessageSender
  public void addOutputMessageListener(OutputMessageListener listener) {
    this.outputListeners.add(listener);
  }

  @Override // com.tvec.utility.displays.output.OutputMessageSender
  public void deleteOutputMessageListener(OutputMessageListener listener) {
    this.outputListeners.remove(listener);
  }

  public void sendOutputMessage(String message) {
    sendOutputMessage(new OutputMessage(message));
  }

  @Override // com.tvec.utility.displays.output.OutputMessageSender
  public void sendOutputMessage(OutputMessage om) {
    for (int i = 0; i < this.outputListeners.size(); i++) {
      this.outputListeners.elementAt(i).handleMessage(this, om);
    }
  }

  public void setOutputDirectory(File outputDirectory) {
    this.outputDirectory = outputDirectory;
  }

  public File getOutputDirectory() {
    return this.outputDirectory;
  }

  public void setSamplesToCreate(int samplesToCreate) {
    if (samplesToCreate < 1) {
      throw new RuntimeException("Samples to create must be greater than 1.");
    }
    this.samplesToCreate = samplesToCreate;
  }

  public void setAbort() {
    this.abort = true;
  }

  public boolean getAbort() {
    return this.abort;
  }

  public void setInput(Document document) {
    this.document = document;
  }

  public int getSamplesToCreate() {
    return this.samplesToCreate;
  }

  public void addEventListener(SampleGeneratorEventListener eventListener) {
    this.eventListeners.add(eventListener);
  }

  public void removeEventListener(SampleGeneratorEventListener eventListener) {
    this.eventListeners.remove(eventListener);
  }

  public void removeAllEventListeners() {
    this.eventListeners.clear();
  }

  private void sendEvent(int eventCode) {
    Iterator<SampleGeneratorEventListener> it = this.eventListeners.iterator();
    while (it.hasNext()) {
      SampleGeneratorEventListener listener = it.next();
      listener.processSampleGeneratorEvent(eventCode);
    }
  }

  private Document getInputDocument() {
    return this.document;
  }

  private String getRandomField(String container, String field)
      throws XPathExpressionException, DOMException {
    String result = null;
    XPathFactory factory = XPathFactory.newInstance();
    XPath xpath = factory.newXPath();
    String xpathString =
        "//DataPane[@name='" + container + "']/DataManager[@header='" + field + "']/values/value";
    try {
      XPathExpression expr = xpath.compile(xpathString);
      NodeList nodes = (NodeList) expr.evaluate(getInputDocument(), XPathConstants.NODESET);
      if (nodes != null && nodes.getLength() > 0) {
        int selection = new Random().nextInt(nodes.getLength());
        result = nodes.item(selection).getTextContent();
        logger.debug("XPath query: {} -> result: {}", xpathString, result);
      } else {
        logger.debug("XPath query: {} -> no results found", xpathString);
      }
    } catch (XPathExpressionException e) {
      logger.debug("XPath query failed: {} - {}", xpathString, e.getMessage());
    }
    return result;
  }

  private String getValue(String xpathString) throws XPathExpressionException, DOMException {
    String result = null;
    XPathFactory factory = XPathFactory.newInstance();
    XPath xpath = factory.newXPath();
    try {
      xpathLogger.debug("Executing XPath query: {}", xpathString);
      XPathExpression expr = xpath.compile(xpathString);
      Node node = (Node) expr.evaluate(getInputDocument(), XPathConstants.NODE);
      if (node != null) {
        result = node.getTextContent();
        xpathLogger.debug("XPath result for '{}': '{}'", xpathString, result);
      } else {
        xpathLogger.debug("XPath query '{}' returned no node", xpathString);
      }
    } catch (XPathExpressionException e) {
      logger.error("XPath expression error for '{}': {}", xpathString, e.getMessage());
      throw e;
    }
    return result;
  }

  private String getDirectValue(String xpath) throws XPathExpressionException, DOMException {
    // This method gets values that are stored directly as element content
    // rather than in values/value structure
    return getValue(xpath);
  }

  @Override // java.lang.Runnable
  public void run() {
    try {
      runImpl();
    } catch (Exception e) {
      sendOutputMessage("Error in run: " + e.getMessage());
      logger.error("Error in sample generator run", e);
    }
  }

  private void runImpl()
      throws XPathExpressionException, InterruptedException, DOMException, IOException {
    this.abort = false;
    File rootOutDir = getOutputDirectory();
    String keyStoreType =
        getDirectValue("//DataPane[@name='KeyStore']/DataManager[@header='KeyStore']/KeyStoreType");
    String keyStorePath =
        getDirectValue("//DataPane[@name='KeyStore']/DataManager[@header='KeyStore']/KeyStorePath");
    String keyStorePassword =
        getDirectValue(
            "//DataPane[@name='KeyStore']/DataManager[@header='KeyStore']/KeyStorePassword");
    String caAlias =
        getDirectValue(
            "//DataPane[@name='KeyStore']/DataManager[@header='KeyStore']/CertificateAlias");
    String caPrivateKeyPassword =
        getDirectValue(
            "//DataPane[@name='KeyStore']/DataManager[@header='KeyStore']/PrivateKeyPassword");
    String contentSignerAlias =
        getDirectValue(
            "//DataPane[@name='KeyStore']/DataManager[@header='KeyStore']/ContentSignerAlias");
    String contentSignerPrivateKeyPassword =
        getDirectValue(
            "//DataPane[@name='KeyStore']/DataManager[@header='KeyStore']/ContentSignerPrivateKeyPassword");

    // Log configuration details
    logger.info("=== PIV Sample Generation Configuration ===");
    logger.debug("KeyStore Type: {}", keyStoreType);
    logger.debug("KeyStore Path: {}", keyStorePath);
    logger.debug(
        "KeyStore Password Length: {}",
        keyStorePassword != null ? keyStorePassword.length() : "null");
    logger.debug("CA Alias: {}", caAlias);
    logger.debug(
        "CA Private Key Password Length: {}",
        caPrivateKeyPassword != null ? caPrivateKeyPassword.length() : "null");
    logger.debug("Content Signer Alias: {}", contentSignerAlias);
    logger.debug(
        "Content Signer Password Length: {}",
        contentSignerPrivateKeyPassword != null
            ? contentSignerPrivateKeyPassword.length()
            : "null");
    logger.info("============================================");
    int samples = getSamplesToCreate();
    for (int sampleNumber = 1; sampleNumber <= samples && !getAbort(); sampleNumber++) {
      String outDirString =
          rootOutDir.getAbsolutePath()
              + File.separator
              + this.DIRECTORY_PREFIX
              + String.format("%03d", Integer.valueOf(sampleNumber));
      File outDir = new File(outDirString);
      logger.info("Generating PIV samples in directory: {}", outDir.getAbsolutePath());
      sendOutputMessage("Generating PIV samples in directory: " + outDir.getAbsolutePath());
      outDir.mkdirs();
      SecurityObject securityObject =
          new SecurityObject(
              keyStoreType,
              keyStorePath,
              keyStorePassword,
              contentSignerAlias,
              contentSignerPrivateKeyPassword);
      FASCN fascn = null;
      for (int container = 0; container < 10 && !getAbort(); container++) {
        byte[] out = null;
        String fileName = "";
        switch (container) {
          case 0:
            fileName = "CARD_CAPABILITY_CONTAINER";
            String cardId = getRandomField("CCC", "Card ID");
            try {
              CCC ccc = new CCC(cardId);
              out = ccc.getBytes();
              securityObject.setDigest(out, 1);
              break;
            } catch (Exception e) {
              sendOutputMessage(e.getMessage());
              e.printStackTrace();
              break;
            }
          case 1:
            fileName = "CHUID";
            String fascnByteString = getRandomField("CHUID", "FASC-N");
            String guid = getRandomField("CHUID", "GUID");
            String expirationDate = getRandomField("CHUID", "Expiration Date");
            fascn = new FASCN(UByte.toSignedByteArray(fascnByteString));
            try {
              CHUID chuid =
                  new CHUID(
                      fascn,
                      guid,
                      expirationDate,
                      keyStoreType,
                      keyStorePath,
                      keyStorePassword,
                      contentSignerAlias,
                      contentSignerPrivateKeyPassword);
              out = chuid.getBytes();
              securityObject.setDigest(out, 2);
              break;
            } catch (Exception e2) {
              sendOutputMessage(e2.getMessage());
              e2.printStackTrace();
              break;
            }
          case 2:
          case 3:
          case 4:
          case 5:
            int certType = 0;
            if (container == 2) {
              fileName = "X509_CERTIFICATE_PIV_AUTHENTICATION";
              certType = 1;
            } else if (container == 4) {
              fileName = "X509_CERTIFICATE_DIGITAL_SIGNATURE";
              certType = 3;
            } else if (container == 3) {
              fileName = "X509_CERTIFICATE_CARD_AUTHENTICATION";
              certType = 2;
            } else if (container == 5) {
              fileName = "X509_CERTIFICATE_KEY_MANAGEMENT";
              certType = 4;
            }
            String serialNumber = getRandomField("Certificates", "Cert Serial Number");
            String validFrom = getRandomField("Certificates", "Valid from");
            String validTo = getRandomField("Certificates", "Valid to");
            String publicKeyFile = getRandomField("Certificates", "Public Key File");
            String commonName = getRandomField("Certificates", "Common Name");
            String organization = getRandomField("Certificates", "Organization");
            String organizationalUnit = getRandomField("Certificates", "Organizational Unit");
            String country = getRandomField("Certificates", "Country");
            X500NameBuilder nameBuilder = new X500NameBuilder(BCStyle.INSTANCE);
            nameBuilder.addRDN(BCStyle.C, country);
            nameBuilder.addRDN(BCStyle.O, organization);
            nameBuilder.addRDN(BCStyle.OU, organizationalUnit);
            nameBuilder.addRDN(BCStyle.CN, commonName);
            X500Name name = nameBuilder.build();
            String crlHttpURI = getRandomField("Certificates", "CRL http URI");
            String crlLdapURI = getRandomField("Certificates", "CRL ldap URI");
            String aiaHttpURI = getRandomField("Certificates", "Authority Info Access http URI");
            String aiaLdapURI = getRandomField("Certificates", "Authority Info Access ldap URI");
            String aiaOcspURI = getRandomField("Certificates", "Authority Info Access ocsp URI");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            try {
              FileInputStream fis = new FileInputStream(publicKeyFile);
              byte[] ba = new byte[fis.available()];
              fis.read(ba);
              fis.close();
              PublicKey key = X509Cert.pivRSA_to_PublicKey(ba);
              out =
                  new X509Cert(
                          certType,
                          serialNumber,
                          X509Cert.DEFAULT_SIGNATURE_ALGORITHM,
                          sdf.parse(validFrom),
                          sdf.parse(validTo),
                          key,
                          name,
                          crlHttpURI,
                          crlLdapURI,
                          aiaHttpURI,
                          aiaLdapURI,
                          aiaOcspURI,
                          keyStoreType,
                          keyStorePath,
                          keyStorePassword,
                          caAlias,
                          caPrivateKeyPassword,
                          fascn,
                          "common_name@pivdemo.org",
                          "common_name@pivdemo.org")
                      .getBytes();
              if (container == 2) {
                securityObject.setDigest(out, 3);
                break;
              } else if (container == 3) {
                securityObject.setDigest(out, 10);
                break;
              } else if (container == 4) {
                securityObject.setDigest(out, 8);
                break;
              } else if (container == 5) {
                securityObject.setDigest(out, 9);
                break;
              }
            } catch (ParseException e3) {
              sendOutputMessage(e3.getMessage());
              e3.printStackTrace();
              break;
            } catch (Exception e4) {
              sendOutputMessage(e4.getMessage());
              e4.printStackTrace();
              break;
            }
            break;
          case 6:
            fileName = "CARD_HOLDER_FINGERPRINTS";
            String bioDataPath = getRandomField("Fingerprint", "Fingerprint Data");
            String createDate = getRandomField("Fingerprint", "Creation Date");
            String validDate = getRandomField("Fingerprint", "Validity Start Date");
            String monthsValid = getRandomField("Fingerprint", "Months Valid");
            File bioData = new File(bioDataPath);
            byte[] bio = new byte[(int) bioData.length()];
            try {
              FileInputStream fis2 = new FileInputStream(bioData);
              fis2.read(bio);
              out =
                  new Biometric(
                          1,
                          bio,
                          createDate,
                          validDate,
                          Integer.parseInt(monthsValid),
                          fascn,
                          keyStoreType,
                          keyStorePath,
                          keyStorePassword,
                          contentSignerAlias,
                          contentSignerPrivateKeyPassword,
                          false)
                      .getBytes();
              securityObject.setDigest(out, 4);
              break;
            } catch (CbeffFormatException e5) {
              sendOutputMessage(e5.getMessage());
              e5.printStackTrace();
              break;
            } catch (FileNotFoundException e6) {
              sendOutputMessage(e6.getMessage());
              e6.printStackTrace();
              break;
            } catch (IOException e7) {
              sendOutputMessage(e7.getMessage());
              e7.printStackTrace();
              break;
            } catch (NumberFormatException e8) {
              sendOutputMessage(e8.getMessage());
              e8.printStackTrace();
              break;
            } catch (Exception e9) {
              sendOutputMessage(e9.getMessage());
              e9.printStackTrace();
              break;
            }
          case 7:
            fileName = "CARD_HOLDER_FACIAL_IMAGE";
            String bioDataPath2 = getRandomField("Facial Image", "Facial Image Data");
            String createDate2 = getRandomField("Facial Image", "Creation Date");
            String validDate2 = getRandomField("Facial Image", "Validity Start Date");
            String monthsValid2 = getRandomField("Facial Image", "Months Valid");
            File bioData2 = new File(bioDataPath2);
            byte[] bio2 = new byte[(int) bioData2.length()];
            try {
              FileInputStream fis3 = new FileInputStream(bioData2);
              fis3.read(bio2);
              out =
                  new Biometric(
                          2,
                          bio2,
                          createDate2,
                          validDate2,
                          Integer.parseInt(monthsValid2),
                          fascn,
                          keyStoreType,
                          keyStorePath,
                          keyStorePassword,
                          contentSignerAlias,
                          contentSignerPrivateKeyPassword,
                          false)
                      .getBytes();
              securityObject.setDigest(out, 7);
              break;
            } catch (CbeffFormatException e10) {
              sendOutputMessage(e10.getMessage());
              e10.printStackTrace();
              break;
            } catch (FileNotFoundException e11) {
              sendOutputMessage(e11.getMessage());
              e11.printStackTrace();
              break;
            } catch (IOException e12) {
              sendOutputMessage(e12.getMessage());
              e12.printStackTrace();
              break;
            } catch (NumberFormatException e13) {
              sendOutputMessage(e13.getMessage());
              e13.printStackTrace();
              break;
            } catch (Exception e14) {
              sendOutputMessage(e14.getMessage());
              e14.printStackTrace();
              break;
            }
          case 8:
            fileName = "PRINTED_INFORMATION";
            String name2 = getRandomField("PrintedInformation", "Printed Name");
            String affiliation1 =
                getRandomField("PrintedInformation", "Employee Affiliation Line 1");
            String affiliation2 =
                getRandomField("PrintedInformation", "Employee Affiliation Line 2");
            String expirationDate2 = getRandomField("PrintedInformation", "Expiration Date");
            String cardSerialNumber = getRandomField("PrintedInformation", "Card Serial Number");
            String issuerId = getRandomField("PrintedInformation", "Issuer Identification");
            try {
              PrintedInfo printedInfo =
                  new PrintedInfo(
                      name2,
                      affiliation1,
                      affiliation2,
                      expirationDate2,
                      cardSerialNumber,
                      issuerId);
              out = printedInfo.getBytes();
              securityObject.setDigest(out, 6);
              break;
            } catch (Exception e15) {
              sendOutputMessage(e15.getMessage());
              e15.printStackTrace();
              break;
            }
          case 9:
            fileName = "SECURITY_OBJECT";
            try {
              out = securityObject.getBytes();
              break;
            } catch (Exception e16) {
              sendOutputMessage(e16.getMessage());
              e16.printStackTrace();
              break;
            }
        }
        if (out != null && !fileName.equals("")) {
          File outFile = new File(outDir.getAbsolutePath(), fileName);
          try {
            FileOutputStream outStream = new FileOutputStream(outFile);
            outStream.write(out);
            outStream.close();
            logger.info("Created PIV sample: {}", outFile.getAbsolutePath());
            sendOutputMessage("Created sample: " + outFile.getAbsolutePath());
          } catch (FileNotFoundException e17) {
            sendOutputMessage(e17.getMessage());
            e17.printStackTrace();
          } catch (IOException e18) {
            sendOutputMessage(e18.getMessage());
            e18.printStackTrace();
          }
        }
      }
    }
    try {
      Thread.sleep(1000L);
    } catch (InterruptedException e19) {
      e19.printStackTrace();
    }
    sendEvent(0);
  }
}
