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

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.security.spec.RSAPublicKeySpec;
import java.util.Date;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.ASN1Boolean;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERUTF8String;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.AccessDescription;
import org.bouncycastle.asn1.x509.AuthorityKeyIdentifier;
import org.bouncycastle.asn1.x509.DistributionPoint;
import org.bouncycastle.asn1.x509.DistributionPointName;
import org.bouncycastle.asn1.x509.ExtendedKeyUsage;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.asn1.x509.PolicyInformation;
import org.bouncycastle.asn1.x509.SubjectKeyIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.X509KeyUsage;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

/* loaded from: JPIV Test Data Generator.jar:gov/nist/piv/data/X509Cert.class */
public class X509Cert {
  public static final String TEST_DATA_INDICATOR = " - PIV Test";
  public static final String ID_FPKI_COMMON_CARD_AUTH = "2.16.840.1.101.3.2.1.3.17";
  public static final String ID_FPKI_COMMON_AUTHENTICATION = "2.16.840.1.101.3.2.1.3.13";
  public static final String ID_AD_CALSSUERS = "1.3.6.1.5.5.7.48.2";
  public static final String ID_AD_OCSP = "1.3.6.1.5.5.7.48.1";
  public static final String PIV_INTERIM = "2.16.840.1.101.3.6.9.1";
  public static final String ID_PIV_CARD_AUTH = "2.16.840.1.101.3.6.8";
  public static final String ID_ANY_EXTENDED_KEY_USAGE = "2.5.29.37.0";
  public static final String ID_CLIENT_AUTHENTICATION = "1.3.6.1.5.5.7.3.2";
  public static final String ID_SMART_CARD_LOGON = "1.3.6.1.4.1.311.20.2.2";
  public static final String ID_UPN = "1.3.6.1.4.1.311.20.2.3";
  public static final String PIV_SIGNER_DN = "2.16.840.1.101.3.6.5";
  public static final String DEFAULT_SIGNATURE_ALGORITHM = "SHA1WITHRSA";
  public static final int CERT_TAG = 112;
  public static final int CERT_INFO_TAG = 113;
  public static final int MSCUID_TAG = 114;
  public static final int ERROR_DETECTION_CODE_TAG = 254;
  public static final int PIV_AUTH_CERT_TYPE = 1;
  public static final int CARD_AUTH_CERT_TYPE = 2;
  public static final int SIG_CERT_TYPE = 3;
  public static final int KEY_MGMT_CERT_TYPE = 4;
  private String serialNumber;
  private Date validFrom;
  private Date validTo;
  private PublicKey subjectPublicKey;
  private X500Name issuerUniqueID;
  private X500Name subjectUniqueID;
  private X509KeyUsage keyUsage;
  private final String crlHttpURI;
  private final String crlLdapURI;
  private final String aiaHttpURI;
  private final String aiaLdapURI;
  private String aiaOcspURI;
  private final String dnUPN;
  private final String email;
  private final int certType;
  private final String caAlias;
  private final String privateKeyPassword;
  private final String keystorePassword;
  private final String keystorePath;
  private final String keystoreType;
  private final FASCN fascn;
  private final byte certInfo = 0;
  private int version = 2;
  private String signatureAlgorithm = DEFAULT_SIGNATURE_ALGORITHM;

  public X509Cert(
      int certType,
      String serialNumber,
      String signatureAlgorithm,
      Date validFrom,
      Date validTo,
      PublicKey subjectPublicKey,
      X500Name subjectDN,
      String crlHttpURI,
      String crlLdapURI,
      String aiaHttpURI,
      String aiaLdapURI,
      String aiaOcspURI,
      String keystoreType,
      String keystorePath,
      String keystorePassword,
      String caCertAlias,
      String privateKeyPassword,
      FASCN fascn,
      String dnUPN,
      String email)
      throws Exception {
    setSerialNumber(serialNumber);
    setSignatureAlgorithm(signatureAlgorithm);
    setValidFrom(validFrom);
    setValidTo(validTo);
    setSubjectPublicKey(subjectPublicKey);
    setSubjectUniqueID(subjectDN);
    this.crlHttpURI = crlHttpURI;
    this.crlLdapURI = crlLdapURI;
    this.aiaHttpURI = aiaHttpURI;
    this.aiaLdapURI = aiaLdapURI;
    this.aiaOcspURI = aiaOcspURI;
    this.dnUPN = dnUPN;
    this.email = email;
    this.certType = certType;
    switch (certType) {
      case 1:
        this.keyUsage = new X509KeyUsage(128);
        break;
      case 2:
        this.keyUsage = new X509KeyUsage(128);
        break;
      case 3:
        this.keyUsage = new X509KeyUsage(192);
        break;
      case 4:
        if (subjectPublicKey.getAlgorithm().equals(SecurityObject.DEFAULT_SIG_ALG)) {
          this.keyUsage = new X509KeyUsage(32);
          break;
        } else if (subjectPublicKey.getAlgorithm().equals("ECC")) {
          this.keyUsage = new X509KeyUsage(8);
          break;
        }
        break;
      default:
        throw new Exception("Invalid Certificate Type.");
    }
    this.caAlias = caCertAlias == null ? "" : caCertAlias;
    this.privateKeyPassword = privateKeyPassword == null ? "" : privateKeyPassword;
    this.keystorePath = keystorePath == null ? "" : keystorePath;
    this.keystorePassword = keystorePassword == null ? "" : keystorePassword;
    this.keystoreType = keystoreType == null ? "" : keystoreType;
    this.fascn = fascn;
  }

  public byte[] getBytes() throws Exception {
    PolicyInformation pi;
    try {
      KeyStore keystore = KeyStore.getInstance(this.keystoreType);
      keystore.load(new FileInputStream(this.keystorePath), this.keystorePassword.toCharArray());
      X509Certificate cacert = (X509Certificate) keystore.getCertificate(this.caAlias);
      PrivateKey privateKey =
          (PrivateKey) keystore.getKey(this.caAlias, this.privateKeyPassword.toCharArray());
      X500Principal name = cacert.getSubjectX500Principal();
      String dn = name.getName();
      X500NameBuilder issuerNameBuilder = new X500NameBuilder(BCStyle.INSTANCE);
      issuerNameBuilder.addRDN(BCStyle.C, extractDNComponent(dn, "C"));
      issuerNameBuilder.addRDN(BCStyle.O, extractDNComponent(dn, "O"));
      issuerNameBuilder.addRDN(BCStyle.CN, extractDNComponent(dn, "CN"));
      X500Name issuerName = issuerNameBuilder.build();
      X509v3CertificateBuilder certGenerator =
          new JcaX509v3CertificateBuilder(
              issuerName,
              new BigInteger(this.serialNumber),
              this.validFrom,
              this.validTo,
              this.subjectUniqueID,
              this.subjectPublicKey);
      SubjectPublicKeyInfo pki =
          SubjectPublicKeyInfo.getInstance(
              new ASN1InputStream(new ByteArrayInputStream(cacert.getPublicKey().getEncoded()))
                  .readObject());
      AuthorityKeyIdentifier akid =
          new JcaX509ExtensionUtils().createAuthorityKeyIdentifier(cacert);
      certGenerator.addExtension(Extension.authorityKeyIdentifier, false, akid);
      SubjectPublicKeyInfo pki2 =
          SubjectPublicKeyInfo.getInstance(
              new ASN1InputStream(new ByteArrayInputStream(this.subjectPublicKey.getEncoded()))
                  .readObject());
      SubjectKeyIdentifier skid = new SubjectKeyIdentifier(pki2.getPublicKeyData().getBytes());
      certGenerator.addExtension(Extension.subjectKeyIdentifier, false, skid);
      certGenerator.addExtension(Extension.keyUsage, true, this.keyUsage);
      if (this.certType == 1) {
        KeyPurposeId[] keyPurposes = {
          KeyPurposeId.getInstance(new ASN1ObjectIdentifier(ID_CLIENT_AUTHENTICATION)),
          KeyPurposeId.getInstance(new ASN1ObjectIdentifier(ID_SMART_CARD_LOGON)),
          KeyPurposeId.getInstance(new ASN1ObjectIdentifier(ID_ANY_EXTENDED_KEY_USAGE))
        };
        ExtendedKeyUsage eku = new ExtendedKeyUsage(keyPurposes);
        certGenerator.addExtension(Extension.extendedKeyUsage, false, eku);
      } else if (this.certType == 2) {
        KeyPurposeId[] keyPurposes = {
          KeyPurposeId.getInstance(new ASN1ObjectIdentifier(ID_PIV_CARD_AUTH))
        };
        ExtendedKeyUsage eku2 = new ExtendedKeyUsage(keyPurposes);
        certGenerator.addExtension(Extension.extendedKeyUsage, true, eku2);
      }
      if (this.certType == 2) {
        pi = new PolicyInformation(new ASN1ObjectIdentifier(ID_FPKI_COMMON_CARD_AUTH));
      } else {
        pi = new PolicyInformation(new ASN1ObjectIdentifier(ID_FPKI_COMMON_AUTHENTICATION));
      }
      ASN1EncodableVector ev3 = new ASN1EncodableVector();
      ev3.add(pi);
      certGenerator.addExtension(Extension.certificatePolicies, false, new DERSequence(ev3));
      GeneralName httpGName = new GeneralName(6, this.crlHttpURI);
      GeneralName ldapGName = new GeneralName(6, this.crlLdapURI);
      GeneralName[] gNameArray = {httpGName, ldapGName};
      GeneralNames gnames = new GeneralNames(gNameArray);
      DistributionPointName dpn = new DistributionPointName(0, gnames);
      DistributionPoint dp = new DistributionPoint(dpn, null, null);
      certGenerator.addExtension(Extension.cRLDistributionPoints, false, new DERSequence(dp));
      if (this.aiaOcspURI == null) {
        this.aiaOcspURI = "";
      }
      AccessDescription ocspAD =
          new AccessDescription(
              new ASN1ObjectIdentifier(ID_AD_OCSP), new GeneralName(6, this.aiaOcspURI));
      ASN1EncodableVector ev5 = new ASN1EncodableVector();
      ev5.add(ocspAD);
      if (this.aiaHttpURI != null && !this.aiaHttpURI.equals("")) {
        ev5.add(
            new AccessDescription(
                new ASN1ObjectIdentifier(ID_AD_CALSSUERS), new GeneralName(6, this.aiaHttpURI)));
      }
      if (this.aiaLdapURI != null && !this.aiaLdapURI.equals("")) {
        ev5.add(
            new AccessDescription(
                new ASN1ObjectIdentifier(ID_AD_CALSSUERS), new GeneralName(6, this.aiaLdapURI)));
      }
      certGenerator.addExtension(Extension.authorityInfoAccess, false, new DERSequence(ev5));
      if (this.certType == 1 || this.certType == 2) {
        ASN1EncodableVector subjectAltNames = new ASN1EncodableVector();
        boolean emptySubjectAltName = true;
        if (this.fascn != null) {
          ASN1EncodableVector ev6 = new ASN1EncodableVector();
          ev6.add(new ASN1ObjectIdentifier(FASCN.PIV_FASCN));
          ev6.add(new GeneralName(0, new DERSequence(new DEROctetString(this.fascn.getBytes()))));
          GeneralName fascnGName = new GeneralName(0, new DERSequence(ev6));
          subjectAltNames.add(fascnGName);
          emptySubjectAltName = false;
        }
        if (this.certType == 1) {
          ASN1EncodableVector ev7 = new ASN1EncodableVector();
          ev7.add(new ASN1ObjectIdentifier(ID_UPN));
          ev7.add(new GeneralName(0, new DERSequence(new DERUTF8String(this.dnUPN))));
          GeneralName dnUPNGName = new GeneralName(0, new DERSequence(ev7));
          subjectAltNames.add(dnUPNGName);
          emptySubjectAltName = false;
        }
        if (!emptySubjectAltName) {
          certGenerator.addExtension(
              Extension.subjectAlternativeName, false, new DERSequence(subjectAltNames));
        }
      } else {
        GeneralName emailGName = new GeneralName(1, new DERIA5String(this.email));
        GeneralName[] emailGNameArray = {emailGName};
        certGenerator.addExtension(
            Extension.subjectAlternativeName, false, new GeneralNames(emailGNameArray));
      }
      if (this.certType == 1 || this.certType == 2) {
        certGenerator.addExtension(new ASN1ObjectIdentifier(PIV_INTERIM), false, ASN1Boolean.FALSE);
      }
      ContentSigner contentSigner =
          new JcaContentSignerBuilder(this.signatureAlgorithm).setProvider("BC").build(privateKey);
      X509CertificateHolder certHolder = certGenerator.build(contentSigner);
      X509Certificate signedCert =
          new JcaX509CertificateConverter().setProvider("BC").getCertificate(certHolder);
      byte[] ret = Utility.generateTLV(CERT_TAG, signedCert.getEncoded());
      return Utility.appendBytes(
          Utility.appendBytes(ret, Utility.generateTLV(CERT_INFO_TAG, new byte[] {this.certInfo})),
          Utility.generateTLV(254, new byte[0]));
    } catch (Exception e) {
      throw e;
    }
  }

  public static PublicKey pivRSA_to_PublicKey(String piv) throws Exception {
    try {
      return pivRSA_to_PublicKey(Utility.hexString2ByteArray(piv));
    } catch (Exception e) {
      throw e;
    }
  }

  public static PublicKey pivRSA_to_PublicKey(byte[] piv) throws Exception {
    int offset;
    int offset2;
    if (piv.length < 10) {
      throw new Exception("The input is too small to be a public key.");
    }
    if (piv[0] != Byte.MAX_VALUE || piv[1] != 73) {
      throw new Exception("Malformed input.  It should have the Tag 0x7f49");
    }
    byte[] exp = null;
    byte[] mod = null;
    int len = 0;
    if ((piv[2] & 255) >= 128) {
      offset = 2 + 1;
      int lenlen = piv[2] & Byte.MAX_VALUE;
      for (int i = 0; i < lenlen; i++) {
        int i2 = offset;
        offset++;
        len = (len * SecurityObject.DIGITAL_SIGNATURE_CERT_CONTAINER_ID) + (piv[i2] & 255);
      }
    } else {
      offset = 2 + 1;
      len = piv[2] & 255;
    }
    if (piv.length != len + offset) {
      throw new Exception("Malformed input. Outermost length value is incorrect.");
    }
    for (int j = 0; j < 2; j++) {
      if (piv.length < offset + 2) {
        throw new Exception("Malformed input.");
      }
      int i3 = offset;
      int offset3 = offset + 1;
      byte tag = piv[i3];
      int len2 = 0;
      if ((piv[offset3] & 255) >= 128) {
        offset2 = offset3 + 1;
        int lenlen2 = piv[offset3] & Byte.MAX_VALUE;
        for (int i4 = 0; i4 < lenlen2; i4++) {
          int i5 = offset2;
          offset2++;
          len2 = (len2 * SecurityObject.DIGITAL_SIGNATURE_CERT_CONTAINER_ID) + (piv[i5] & 255);
        }
      } else {
        offset2 = offset3 + 1;
        len2 = piv[offset3] & 255;
      }
      byte[] ba = new byte[len2];
      System.arraycopy(piv, offset2, ba, 0, len2);
      if (tag == -127) {
        mod = new byte[len2];
        System.arraycopy(ba, 0, mod, 0, len2);
      } else if (tag == -126) {
        exp = new byte[len2];
        System.arraycopy(ba, 0, exp, 0, len2);
      } else {
        throw new Exception("Malformed input.");
      }
      offset = offset2 + len2;
    }
    try {
      return KeyFactory.getInstance(SecurityObject.DEFAULT_SIG_ALG)
          .generatePublic(new RSAPublicKeySpec(new BigInteger(mod), new BigInteger(exp)));
    } catch (Exception e) {
      throw e;
    }
  }

  public static void main(String[] args) {}

  public int getVersion() {
    return this.version;
  }

  public void setVersion(int version) {
    this.version = version;
  }

  public String getSerialNumber() {
    return this.serialNumber;
  }

  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  public String getSignatureAlgorithm() {
    return this.signatureAlgorithm;
  }

  public void setSignatureAlgorithm(String signatureAlgorithm) {
    this.signatureAlgorithm = signatureAlgorithm;
  }

  public Date getValidFrom() {
    return this.validFrom;
  }

  public void setValidFrom(Date validFrom) {
    this.validFrom = validFrom;
  }

  public Date getValidTo() {
    return this.validTo;
  }

  public void setValidTo(Date validTo) {
    this.validTo = validTo;
  }

  public PublicKey getSubjectPublicKey() {
    return this.subjectPublicKey;
  }

  public void setSubjectPublicKey(PublicKey subjectPublicKey) {
    this.subjectPublicKey = subjectPublicKey;
  }

  public X500Name getIssuerUniqueID() {
    return this.issuerUniqueID;
  }

  public void setIssuerUniqueID(X500Name issuerUniqueID) {
    this.issuerUniqueID = issuerUniqueID;
  }

  public X500Name getSubjectUniqueID() {
    return this.subjectUniqueID;
  }

  public void setSubjectUniqueID(X500Name subjectUniqueID) {
    this.subjectUniqueID = subjectUniqueID;
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
