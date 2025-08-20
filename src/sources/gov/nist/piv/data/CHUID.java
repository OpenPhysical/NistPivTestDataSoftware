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

/* loaded from: JPIV Test Data Generator.jar:gov/nist/piv/data/CHUID.class */
public class CHUID {
  public static final boolean INCLUDE_EDC = false;
  public static final int BUFFER_LENGTH_TAG = 238;
  public static final int FASCN_TAG = 48;
  public static final int GUID_TAG = 52;
  public static final int EXPIRATION_DATE_TAG = 53;
  public static final int KEYMAP_TAG = 61;
  public static final int SIGNATURE_TAG = 62;
  public static final int EDC_TAG = 254;
  private byte[] signedBytes;
  public static final String ID_PIV_CHUID_Security_Object = "2.16.840.1.101.3.6.1";
  private FASCN fascn;
  private String guid;
  private String expirationDate;
  private String keyMap;
  private final byte errorDetectionCode;
  private String contentSignerAlias;
  private String contentSignerPrivateKeyPassword;
  private String keystorePassword;
  private String keystorePath;
  private String keystoreType;

  public CHUID() {
    this.keyMap = "";
    this.errorDetectionCode = (byte) 0;
  }

  public CHUID(
      FASCN fascn,
      String guid,
      String expirationDate,
      String keystoreType,
      String keystorePath,
      String keystorePassword,
      String contentSignerCertAlias,
      String contentSignerPrivateKeyPassword)
      throws Exception {
    try {
      setFascn(fascn);
      setGuid(guid);
      setExpirationDate(expirationDate);
      this.keyMap = "";
      this.errorDetectionCode = (byte) 0;
      this.contentSignerAlias = contentSignerCertAlias == null ? "" : contentSignerCertAlias;
      this.contentSignerPrivateKeyPassword =
          contentSignerPrivateKeyPassword == null ? "" : contentSignerPrivateKeyPassword;
      this.keystoreType = keystoreType == null ? "" : keystoreType;
      this.keystorePath = keystorePath == null ? "" : keystorePath;
      this.keystorePassword = keystorePassword == null ? "" : keystorePassword;
    } catch (Exception e) {
      throw e;
    }
  }

  public byte[] getSignature(byte[] data) throws Exception {
    try {
      KeyStore keyStore = KeyStore.getInstance(this.keystoreType);
      keyStore.load(new FileInputStream(this.keystorePath), this.keystorePassword.toCharArray());
      X509Certificate contentSignerCert =
          (X509Certificate) keyStore.getCertificate(this.contentSignerAlias);
      PrivateKey privateKey =
          (PrivateKey)
              keyStore.getKey(
                  this.contentSignerAlias, this.contentSignerPrivateKeyPassword.toCharArray());
      try {
        CMSSignedDataGenerator sigGenerator = new CMSSignedDataGenerator();
        ASN1EncodableVector signedAttrs = new ASN1EncodableVector();
        ASN1EncodableVector ev = new ASN1EncodableVector();
        ev.add(new ASN1ObjectIdentifier(X509Cert.PIV_SIGNER_DN));
        X500Principal name = contentSignerCert.getSubjectX500Principal();
        String dn = name.getName();
        X500NameBuilder nameBuilder = new X500NameBuilder(BCStyle.INSTANCE);
        nameBuilder.addRDN(BCStyle.CN, extractDNComponent(dn, "CN"));
        nameBuilder.addRDN(BCStyle.O, extractDNComponent(dn, "O"));
        nameBuilder.addRDN(BCStyle.C, extractDNComponent(dn, "C"));
        ev.add(new DERSet(nameBuilder.build()));
        signedAttrs.add(new DERSequence(ev));
        sigGenerator.addSignerInfoGenerator(
            new JcaSimpleSignerInfoGeneratorBuilder()
                .setProvider("BC")
                .build("SHA1withRSA", privateKey, contentSignerCert));
        ArrayList<X509Certificate> certList = new ArrayList<>();
        certList.add(contentSignerCert);
        sigGenerator.addCertificates(new JcaCertStore(certList));
        CMSSignedData signedData = sigGenerator.generate(new CMSProcessableByteArray(data), false);
        return Utility.BERtoDER(signedData.getEncoded());
      } catch (NoSuchAlgorithmException e) {
        throw e;
      }
    } catch (Exception e2) {
      throw e2;
    }
  }

  public byte[] resignCHUID() throws Exception {
    byte[] chuid =
        Utility.appendBytes(
            Utility.appendBytes(
                Utility.generateTLV(48, this.fascn.getBytes()), Utility.generateTLV(52, this.guid)),
            Utility.generateTLV(53, this.expirationDate));
    if (this.keyMap != null) {
      chuid = Utility.appendBytes(chuid, Utility.generateTLV(61, this.keyMap));
    }
    byte[] datachuid = chuid.clone();
    try {
      byte[] data2sign1 = Utility.appendBytes(datachuid, new byte[] {-2, 0});
      byte[] datachuid2 =
          Utility.appendBytes(data2sign1, Utility.generateTLV(62, getSignature(data2sign1)));
      byte[] datachuid3 = Utility.appendBytes(datachuid2, new byte[] {-2, 0});
      byte[] bufferLength = {
        (byte) (datachuid3.length & 255), (byte) ((datachuid3.length >>> 8) & 255)
      };
      byte[] chuid2 =
          Utility.appendBytes(Utility.generateTLV(BUFFER_LENGTH_TAG, bufferLength), chuid);
      try {
        byte[] data2sign2 = Utility.appendBytes(chuid2, new byte[] {-2, 0});
        byte[] chuid3 =
            Utility.appendBytes(
                Utility.appendBytes(chuid2, Utility.generateTLV(62, getSignature(data2sign2))),
                new byte[] {-2, 0});
        this.signedBytes = chuid3.clone();
        return chuid3;
      } catch (Exception e) {
        throw e;
      }
    } catch (Exception e2) {
      throw e2;
    }
  }

  public byte[] getBytes() throws Exception {
    if (this.signedBytes != null) {
      return this.signedBytes;
    }
    try {
      return resignCHUID();
    } catch (Exception e) {
      throw e;
    }
  }

  public static int lenlen(int size) {
    if (size < 128) {
      return 1;
    }
    return ((int) Math.floor(Math.log(size))) - 5;
  }

  public static void main(String[] args) throws IOException {
    Security.addProvider(new BouncyCastleProvider());
    try {
      byte[] chuid =
          new CHUID(
                  new FASCN(1341, 1, 987654, 1, 1, Long.parseLong("1234567890"), 1, 1341, 1),
                  "0000000000000000",
                  "20051116",
                  "JKS",
                  "keystore/pivcerts",
                  "pivpw1",
                  "pivtestsigner",
                  "pivtestsignerpw")
              .getBytes();
      FileOutputStream fos = new FileOutputStream("chuid");
      fos.write(chuid);
      fos.close();
      System.out.println(Utility.byteArray2HexString(chuid));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public byte getErrorDetectionCode() {
    return this.errorDetectionCode;
  }

  public String getExpirationDate() {
    return this.expirationDate;
  }

  public void setExpirationDate(String expirationDate) {
    this.expirationDate = expirationDate;
  }

  public FASCN getFascn() {
    return this.fascn;
  }

  public void setFascn(FASCN fascn) {
    this.fascn = fascn;
  }

  public String getGuid() {
    return this.guid;
  }

  public void setGuid(String guid) throws Exception {
    if (guid.length() != 16) {
      throw new Exception("The GUID must be 16 digits!");
    }
    this.guid = guid;
  }

  public String getKeyMap() {
    return this.keyMap;
  }

  public void setKeyMap(String keyMap) {
    this.keyMap = keyMap;
  }

  public String getContentSignerAlias() {
    return this.contentSignerAlias;
  }

  public void setContentSignerAlias(String contentSignerAlias) {
    this.contentSignerAlias = contentSignerAlias;
  }

  public String getKeystorePassword() {
    return this.keystorePassword;
  }

  public void setKeystorePassword(String keystorePassword) {
    this.keystorePassword = keystorePassword;
  }

  public String getKeystorePath() {
    return this.keystorePath;
  }

  public void setKeystorePath(String keystorePath) {
    this.keystorePath = keystorePath;
  }

  public String getKeystoreType() {
    return this.keystoreType;
  }

  public void setKeystoreType(String keystoreType) {
    this.keystoreType = keystoreType;
  }

  public String getContentSignerPrivateKeyPassword() {
    return this.contentSignerPrivateKeyPassword;
  }

  public void setContentSignerPrivateKeyPassword(String contentSignerPrivateKeyPassword) {
    this.contentSignerPrivateKeyPassword = contentSignerPrivateKeyPassword;
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
