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

import com.tvec.utility.Crypto;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoGeneratorBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/* loaded from: JPIV Test Data Generator.jar:gov/nist/piv/data/SecurityObject.class */
public class SecurityObject {
  public static final String ID_ICAO_LDS_SECURITY_OBJECT = "1.3.27.1.1.1";
  public static final String DEFAULT_HASH_ALG = "SHA-1";
  public static final String DEFAULT_SIG_ALG = "RSA";
  public static final int DEFAULT_KEY_SIZE = 1024;
  public static final boolean INCLUDE_LRC = false;
  public static final int DG_TO_CONTAINER_ID_MAP_TAG = 186;
  public static final int SECURITY_OBJECT_TAG = 187;
  public static final int ERROR_DETECTION_CODE_TAG = 254;
  public static final int CCC_CONTAINER_ID = 56064;
  public static final int CHUID_CONTAINER_ID = 12288;
  public static final int PIV_AUTH_CERT_CONTAINER_ID = 257;
  public static final int FINGERPRINTS_CONTAINER_ID = 24592;
  public static final int PRINTED_INFO_CONTAINER_ID = 12289;
  public static final int FACIAL_IMAGE_CONTAINER_ID = 24624;
  public static final int DIGITAL_SIGNATURE_CERT_CONTAINER_ID = 256;
  public static final int KEY_MGMT_CERT_CONTAINER_ID = 258;
  public static final int CARD_AUTH_CERT_CONTAINER_ID = 1280;
  public static final int CCC_DG = 1;
  public static final int CHUID_DG = 2;
  public static final int PIV_AUTH_CERT_DG = 3;
  public static final int FINGERPRINTS_DG = 4;
  public static final int PRINTED_INFO_DG = 6;
  public static final int FACIAL_IMAGE_DG = 7;
  public static final int DIGITAL_SIG_CERT_DG = 8;
  public static final int KEY_MGMT_CERT_DG = 9;
  public static final int CARD_AUTH_CERT_DG = 10;
  private final HashMap<Integer, byte[]> digests = new HashMap<>();
  private final String hashAlg;
  private final String contentSignerAlias;
  private final String contentSignerPrivateKeyPassword;
  private final String keystorePassword;
  private final String keystorePath;
  private final String keystoreType;
  private byte[] signedBytes;

  public byte[] getDigestByContainerID(int cid) {
    switch (cid) {
      case DIGITAL_SIGNATURE_CERT_CONTAINER_ID /* 256 */:
        return this.digests.get(8);
      case PIV_AUTH_CERT_CONTAINER_ID /* 257 */:
        return this.digests.get(3);
      case KEY_MGMT_CERT_CONTAINER_ID /* 258 */:
        return this.digests.get(9);
      case CARD_AUTH_CERT_CONTAINER_ID /* 1280 */:
        return this.digests.get(10);
      case CHUID_CONTAINER_ID /* 12288 */:
        return this.digests.get(2);
      case PRINTED_INFO_CONTAINER_ID /* 12289 */:
        return this.digests.get(6);
      case FINGERPRINTS_CONTAINER_ID /* 24592 */:
        return this.digests.get(4);
      case FACIAL_IMAGE_CONTAINER_ID /* 24624 */:
        return this.digests.get(7);
      case CCC_CONTAINER_ID /* 56064 */:
        return this.digests.get(1);
      default:
        return null;
    }
  }

  public byte[] getDigestByDG(int dg) {
    if (this.digests.containsKey(Integer.valueOf(dg))) {
      return this.digests.get(Integer.valueOf(dg));
    }
    return null;
  }

  public SecurityObject(
      String keystoreType,
      String keystorePath,
      String keystorePassword,
      String contentSignerCertAlias,
      String contentSignerPrivateKeyPassword) {
    this.digests.put(Integer.valueOf(1), new byte[0]);
    this.digests.put(Integer.valueOf(2), new byte[0]);
    this.digests.put(Integer.valueOf(3), new byte[0]);
    this.digests.put(Integer.valueOf(4), new byte[0]);
    this.digests.put(Integer.valueOf(6), new byte[0]);
    this.digests.put(Integer.valueOf(7), new byte[0]);
    this.digests.put(Integer.valueOf(8), new byte[0]);
    this.digests.put(Integer.valueOf(9), new byte[0]);
    this.digests.put(Integer.valueOf(10), new byte[0]);
    this.contentSignerAlias = contentSignerCertAlias == null ? "" : contentSignerCertAlias;
    this.contentSignerPrivateKeyPassword =
        contentSignerPrivateKeyPassword == null ? "" : contentSignerPrivateKeyPassword;
    this.keystoreType = keystoreType == null ? "" : keystoreType;
    this.keystorePath = keystorePath == null ? "" : keystorePath;
    this.keystorePassword = keystorePassword == null ? "" : keystorePassword;
    this.hashAlg = "SHA-1";
  }

  public SecurityObject(
      String keystoreType,
      String keystorePath,
      String keystorePassword,
      String contentSignerCertAlias,
      String contentSignerPrivateKeyPassword,
      String hashAlg) {
    this.digests.put(Integer.valueOf(1), new byte[0]);
    this.digests.put(Integer.valueOf(2), new byte[0]);
    this.digests.put(Integer.valueOf(3), new byte[0]);
    this.digests.put(Integer.valueOf(4), new byte[0]);
    this.digests.put(Integer.valueOf(6), new byte[0]);
    this.digests.put(Integer.valueOf(7), new byte[0]);
    this.digests.put(Integer.valueOf(8), new byte[0]);
    this.digests.put(Integer.valueOf(9), new byte[0]);
    this.digests.put(Integer.valueOf(10), new byte[0]);
    this.contentSignerAlias = contentSignerCertAlias == null ? "" : contentSignerCertAlias;
    this.contentSignerPrivateKeyPassword =
        contentSignerPrivateKeyPassword == null ? "" : contentSignerPrivateKeyPassword;
    this.keystoreType = keystoreType == null ? "" : keystoreType;
    this.keystorePath = keystorePath == null ? "" : keystorePath;
    this.keystorePassword = keystorePassword == null ? "" : keystorePassword;
    this.hashAlg = hashAlg;
  }

  public byte[] getSignedSecurityObject() throws Exception {
    Security.addProvider(new BouncyCastleProvider());
    DERSequence lds = getLDSSecurityObject();
    try {
      KeyStore keyStore = KeyStore.getInstance(this.keystoreType);
      keyStore.load(new FileInputStream(this.keystorePath), this.keystorePassword.toCharArray());
      Certificate contentSignerCert = keyStore.getCertificate(this.contentSignerAlias);
      PrivateKey privateKey =
          (PrivateKey)
              keyStore.getKey(
                  this.contentSignerAlias, this.contentSignerPrivateKeyPassword.toCharArray());
      try {
        CMSSignedDataGenerator sigGenerator = new CMSSignedDataGenerator();
        sigGenerator.addSignerInfoGenerator(
            new JcaSimpleSignerInfoGeneratorBuilder()
                .setProvider("BC")
                .build("SHA1withRSA", privateKey, (X509Certificate) contentSignerCert));
        CMSSignedData signedData =
            sigGenerator.generate(new CMSProcessableByteArray(lds.getEncoded()), true);
        return Utility.BERtoDER(signedData.getEncoded());
      } catch (Exception e) {
        throw e;
      }
    } catch (Exception e2) {
      throw e2;
    }
  }

  public byte[] resign() throws Exception {
    try {
      byte[] securityObject = Utility.generateTLV(DG_TO_CONTAINER_ID_MAP_TAG, getMapper());
      try {
        return Utility.appendBytes(
            Utility.appendBytes(
                securityObject,
                Utility.generateTLV(SECURITY_OBJECT_TAG, getSignedSecurityObject())),
            new byte[] {-2, 0});
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
      return resign();
    } catch (Exception e) {
      throw e;
    }
  }

  public byte[] getMapper() {
    return new byte[] {
      1, -37, 0, 2, 48, 0, 3, 1, 1, 4, 96, 16, 6, 48, 1, 7, 96, 48, 8, 1, 0, 9, 1, 2, 10, 5, 0
    };
  }

  public DERSequence getLDSSecurityObject() {
    ASN1EncodableVector lds = new ASN1EncodableVector();
    lds.add(new ASN1Integer(Integer.valueOf(0).intValue()));
    ASN1EncodableVector ev = new ASN1EncodableVector();
    ev.add(new ASN1ObjectIdentifier(Crypto.OID_SHA1));
    ev.add(DERNull.INSTANCE);
    lds.add(new DERSequence(ev));
    ASN1EncodableVector dgHashValues = new ASN1EncodableVector();
    Iterator<Integer> it = new TreeSet<Integer>(this.digests.keySet()).iterator();
    while (it.hasNext()) {
      Integer dg = (Integer) it.next();
      ASN1EncodableVector ev2 = new ASN1EncodableVector();
      ev2.add(new ASN1Integer(dg.intValue()));
      ev2.add(new DEROctetString(this.digests.get(dg)));
      dgHashValues.add(new DERSequence(ev2));
    }
    lds.add(new DERSequence(dgHashValues));
    return new DERSequence(lds);
  }

  public void setDigest(byte[] data, int dataGroupNumber)
      throws NoSuchAlgorithmException, InvalidDataGroupNumberException {
    this.signedBytes = null;
    Integer dg = Integer.valueOf(dataGroupNumber);
    if (!this.digests.containsKey(dg)) {
      throw new InvalidDataGroupNumberException(
          dataGroupNumber
              + " is not a known Data Group Number.  Please check your values and try again.");
    }
    byte[] digest = new byte[0];
    try {
      MessageDigest hasher = MessageDigest.getInstance(this.hashAlg);
      digest = hasher.digest(data);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    this.digests.remove(dg);
    this.digests.put(dg, digest);
  }

  public static void main(String[] args) throws IOException {
    SecurityObject so =
        new SecurityObject(
            "JKS", "keystore/pivcerts", "pivpw1", "pivtestsigner", "pivtestsignerpw");
    try {
      so.setDigest(new byte[] {0, 1, 2, 3, 4, 5, 6, 7, 8}, 2);
    } catch (Exception e) {
      e.printStackTrace();
    }
    try {
      FileOutputStream fos = new FileOutputStream("secobj");
      byte[] signedData = so.getBytes();
      fos.write(signedData);
      fos.close();
    } catch (Exception e2) {
    }
  }
}
