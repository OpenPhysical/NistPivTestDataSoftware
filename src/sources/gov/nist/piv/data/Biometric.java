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

package gov.nist.piv.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoGeneratorBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/* loaded from: JPIV Test Data Generator.jar:gov/nist/piv/data/Biometric.class */
public class Biometric {
  public static final byte BIOMETRIC_DATA_TAG = -68;
  public static final byte LRC_TAG = -2;
  public static final String ID_PIV_BIOMETRIC_OBJECT = "2.16.840.1.101.3.6.2";
  public static final int BIOMETRIC_TYPE_FINGER_MINUTIAE = 1;
  public static final int BIOMETRIC_TYPE_FACIAL_IMAGE = 2;
  private byte[] biometricData;
  private byte[] cbeffHeader;
  private final byte[] fascn;
  private byte[] signedData;
  private final String contentSignerAlias;
  private final String contentSignerPrivateKeyPassword;
  private final String keystorePassword;
  private final String keystorePath;
  private final String keystoreType;
  private final boolean includeCert;

  public Biometric(
      int biometricType,
      byte[] biometricData,
      String biometricCreationDate,
      String validityStartDate,
      int monthsValid,
      FASCN fascn,
      String keystoreType,
      String keystorePath,
      String keystorePassword,
      String contentSignerCertAlias,
      String contentSignerPrivateKeyPassword,
      boolean includeCert)
      throws CbeffFormatException {
    setBiometricData(biometricData);
    try {
      setCbeffHeader(
          biometricType,
          biometricData.length,
          0,
          biometricCreationDate,
          validityStartDate,
          monthsValid,
          fascn);
      this.includeCert = includeCert;
      this.fascn = fascn.getBytes();
      this.contentSignerAlias = contentSignerCertAlias == null ? "" : contentSignerCertAlias;
      this.contentSignerPrivateKeyPassword =
          contentSignerPrivateKeyPassword == null ? "" : contentSignerPrivateKeyPassword;
      this.keystoreType = keystoreType == null ? "" : keystoreType;
      this.keystorePath = keystorePath == null ? "" : keystorePath;
      this.keystorePassword = keystorePassword == null ? "" : keystorePassword;
    } catch (CbeffHeaderFormatException e) {
      throw e;
    }
  }

  private int getSignatureLength() {
    try {
      return getSignature(Utility.appendBytes(this.cbeffHeader, this.biometricData)).length;
    } catch (Exception e) {
      e.printStackTrace();
      return -1;
    }
  }

  private byte[] getSignature(byte[] data) throws Exception {
    try {
      KeyStore keyStore = KeyStore.getInstance(this.keystoreType);
      keyStore.load(new FileInputStream(this.keystorePath), this.keystorePassword.toCharArray());
      X509Certificate contentSignerCert =
          (X509Certificate) keyStore.getCertificate(this.contentSignerAlias);
      PrivateKey privateKey =
          (PrivateKey)
              keyStore.getKey(
                  this.contentSignerAlias, this.contentSignerPrivateKeyPassword.toCharArray());
      CMSSignedDataGenerator sigGenerator = new CMSSignedDataGenerator();
      ASN1EncodableVector signedAttrs = new ASN1EncodableVector();
      ASN1EncodableVector ev = new ASN1EncodableVector();
      ev.add(new ASN1ObjectIdentifier(FASCN.PIV_FASCN));
      ev.add(new DERSet(new DEROctetString(this.fascn)));
      signedAttrs.add(new DERSequence(ev));
      ASN1EncodableVector ev2 = new ASN1EncodableVector();
      ev2.add(new ASN1ObjectIdentifier(X509Cert.PIV_SIGNER_DN));
      X500Principal name = contentSignerCert.getSubjectX500Principal();
      String dn = name.getName();
      X500NameBuilder nameBuilder = new X500NameBuilder(BCStyle.INSTANCE);
      nameBuilder.addRDN(BCStyle.CN, extractDNComponent(dn, "CN"));
      nameBuilder.addRDN(BCStyle.O, extractDNComponent(dn, "O"));
      nameBuilder.addRDN(BCStyle.C, extractDNComponent(dn, "C"));
      ev2.add(new DERSet(nameBuilder.build()));
      signedAttrs.add(new DERSequence(ev2));
      sigGenerator.addSignerInfoGenerator(
          new JcaSimpleSignerInfoGeneratorBuilder()
              .setProvider("BC")
              .build("SHA1withRSA", privateKey, contentSignerCert));
      if (this.includeCert) {
        ArrayList<X509Certificate> certList = new ArrayList<>();
        certList.add(contentSignerCert);
        sigGenerator.addCertificates(new JcaCertStore(certList));
      }
      CMSSignedData signedData = sigGenerator.generate(new CMSProcessableByteArray(data), false);
      return Utility.BERtoDER(signedData.getEncoded());
    } catch (NoSuchAlgorithmException e) {
      throw e;
    }
  }

  public byte[] getCbeffStructure() throws CbeffFormatException {
    int sigLength = getSignatureLength();
    if (sigLength >= 65536) {
      throw new CbeffFormatException(
          "The signature block's length was too large (bigger than 65535 bytes!?).  Try it again with a (much much) smaller signing certificate");
    }
    this.cbeffHeader[6] = (byte) (sigLength / SecurityObject.DIGITAL_SIGNATURE_CERT_CONTAINER_ID);
    this.cbeffHeader[7] = (byte) (sigLength % SecurityObject.DIGITAL_SIGNATURE_CERT_CONTAINER_ID);
    byte[] data = Utility.appendBytes(this.cbeffHeader, this.biometricData);
    try {
      data = Utility.appendBytes(data, getSignature(data));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return data;
  }

  public byte[] resign() throws Exception {
    try {
      byte[] data =
          Utility.appendBytes(
              Utility.generateTLV(-68, getCbeffStructure()), Utility.generateTLV(-2, new byte[0]));
      this.signedData = data.clone();
      return data;
    } catch (Exception e) {
      throw e;
    }
  }

  public byte[] getBytes() throws Exception {
    if (this.signedData != null) {
      return this.signedData;
    }
    try {
      return resign();
    } catch (Exception e) {
      throw e;
    }
  }

  public static void main(String[] args) throws IOException {
    Security.addProvider(new BouncyCastleProvider());
    try {
      File f = new File("biometric/rfing.A");
      byte[] bio = new byte[(int) f.length()];
      FileInputStream fis = new FileInputStream(f);
      fis.read(bio);
      FileOutputStream fos = new FileOutputStream("rfing");
      byte[] signedData =
          new Biometric(
                  1,
                  bio,
                  "20060302103742",
                  "20060302103742",
                  60,
                  new FASCN(1341, 1, 987654, 1, 1, Long.parseLong("1234567890"), 1, 1341, 1),
                  "JKS",
                  "keystore/pivcerts",
                  "pivpw1",
                  "pivtestsigner",
                  "pivtestsignerpw",
                  true)
              .getBytes();
      fos.write(signedData);
      fos.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public byte[] getCbeffHeader() {
    return this.cbeffHeader;
  }

  public void setCbeffHeader(byte[] cbeffHeader) {
    this.cbeffHeader = cbeffHeader;
  }

  public void setCbeffHeader(
      int type,
      int dataLength,
      int sigLength,
      String biometricCreationDate,
      String validityStartDate,
      int monthsValid,
      FASCN fascn)
      throws CbeffHeaderFormatException {
    byte[] bdbFormatType = null;
    byte[] biometricType = null;
    byte biometricDataType = 0;
    if (type == 1) {
      bdbFormatType = CbeffHeader.BDB_FORMAT_TYPE_FINGER_MINUTIAE;
      biometricType = CbeffHeader.BIOMETRIC_TYPE_FINGER;
      biometricDataType = Byte.MIN_VALUE;
    } else if (type == 2) {
      bdbFormatType = CbeffHeader.BDB_FORMAT_TYPE_FACIAL_IMAGE;
      biometricType = CbeffHeader.BIOMETRIC_TYPE_FACIAL;
      biometricDataType = 32;
    }
    try {
      this.cbeffHeader =
          new CbeffHeader(
                  dataLength,
                  sigLength,
                  bdbFormatType,
                  CbeffHeader.cbeffDate(biometricCreationDate),
                  CbeffHeader.cbeffDate(validityStartDate),
                  monthsValid,
                  biometricType,
                  biometricDataType,
                  (byte) -2,
                  new byte[] {78, 73, 83, 84, 32, 67, 114, 101, 97, 116, 111, 114},
                  fascn)
              .getBytes();
    } catch (CbeffHeaderFormatException e) {
      throw e;
    }
  }

  public byte[] getBiometricData() {
    return this.biometricData;
  }

  public void setBiometricData(byte[] biometricData) {
    this.biometricData = biometricData;
  }

  // Helper method to extract DN components from X500Principal name string
  private static String extractDNComponent(String dn, String component) {
    // X500Principal.getName() returns RFC2253 format like "CN=Test, O=Org, C=US"
    String pattern = component + "=";
    int start = dn.indexOf(pattern);
    if (start == -1) {
      return "";
    }
    start += pattern.length();
    int end = dn.indexOf(',', start);
    if (end == -1) {
      end = dn.length();
    }
    return dn.substring(start, end).trim();
  }
}
