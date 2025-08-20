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

package com.tvec.utility;

import gov.nist.piv.data.SecurityObject;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidator;
import java.security.cert.CertPathValidatorException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.PKIXCertPathValidatorResult;
import java.security.cert.PKIXParameters;
import java.security.cert.PolicyNode;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.ECField;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.EllipticCurve;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.x500.X500Principal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.IEKeySpec;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

/* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/Crypto.class */
public class Crypto implements Serializable {
  private static final long serialVersionUID = 1;
  private static final Logger logger = LogManager.getLogger(Crypto.class);
  public static final String OID_SHA1 = "1.3.14.3.2.26";
  public static final String OID_SHA224 = "2.16.840.1.101.3.4.2.4";
  public static final String OID_SHA256 = "2.16.840.1.101.3.4.2.1";
  public static final String OID_RSA = "1.2.840.113549.1.1.1";
  public static final String OID_ECDSA = "1.2.840.10045.2.1";
  public static final String OID_SIGNATURE_ALGORITHM_SHA1_RSA_PKCS15 = "1.2.840.113549.1.1.5";
  public static final String OID_SIGNATURE_ALGORITHM_SHA256_RSA_PKCS15 = "1.2.840.113549.1.1.11";
  public static final String OID_SIGNATURE_ALGORITHM_SHA256_RSA_PSS = "1.2.840.113549.1.1.10";
  public static final String OID_SIGNATURE_ALGORITHM_SHA1_ECDSA = "1.2.840.10045.4.1";
  public static final String OID_SIGNATURE_ALGORITHM_SHA224_ECDSA = "1.2.840.10045.4.3.1";
  public static final String OID_SIGNATURE_ALGORITHM_SHA256_ECDSA = "1.2.840.10045.4.3.2";
  public static final String OID_SIGNATURE_ALGORITHM_SHA384_ECDSA = "1.2.840.10045.4.3.3";
  public static final String OID_SIGNATURE_ALGORITHM_SHA512_ECDSA = "1.2.840.10045.4.3.4";
  public static final byte[] SIGNATURE_HEADER_SHA1;
  public static final byte[] SIGNATURE_HEADER_MD5;
  public static String CRYPTO_PROVIDER = "BC";
  public static HashMap<String, String> signatureAlgorithmNameLookup = new HashMap<>(10);

  static {
    signatureAlgorithmNameLookup.put(OID_SIGNATURE_ALGORITHM_SHA1_RSA_PKCS15, "SHA1 RSA PKCS15");
    signatureAlgorithmNameLookup.put(
        OID_SIGNATURE_ALGORITHM_SHA256_RSA_PKCS15, "SHA256 RSA PKCS15");
    signatureAlgorithmNameLookup.put(OID_SIGNATURE_ALGORITHM_SHA256_RSA_PSS, "SHA256 RSA PSS");
    signatureAlgorithmNameLookup.put(OID_SIGNATURE_ALGORITHM_SHA1_ECDSA, "SHA1 ECDSA");
    signatureAlgorithmNameLookup.put(OID_SIGNATURE_ALGORITHM_SHA224_ECDSA, "SHA24 ECDSA");
    signatureAlgorithmNameLookup.put(OID_SIGNATURE_ALGORITHM_SHA256_ECDSA, "SHA256 ECDSA");
    signatureAlgorithmNameLookup.put(OID_SIGNATURE_ALGORITHM_SHA384_ECDSA, "SHA384 ECDSA");
    signatureAlgorithmNameLookup.put(OID_SIGNATURE_ALGORITHM_SHA512_ECDSA, "SHA512 ECDSA");
    Security.addProvider(new BouncyCastleProvider());
    SIGNATURE_HEADER_SHA1 = new byte[] {48, 33, 48, 9, 6, 5, 43, 14, 3, 2, 26, 5, 0, 4, 20};
    SIGNATURE_HEADER_MD5 =
        new byte[] {48, 32, 48, 12, 6, 8, 42, -122, 72, -122, -9, 13, 2, 5, 5, 0, 4, 16};
  }

  public static final String getAlgorithmForOID(ASN1ObjectIdentifier OID) {
    return getAlgorithmForOID(OID.getId());
  }

  public static final String getAlgorithmForOID(String OID) {
    String ret = "";
    if (OID.equals(OID_SHA1)) {
      ret = "SHA-1";
    } else if (OID.equals(OID_SHA224)) {
      ret = "SHA-224";
    } else if (OID.equals(OID_SHA256)) {
      ret = "SHA-256";
    } else if (OID.equals(OID_RSA)) {
      ret = SecurityObject.DEFAULT_SIG_ALG;
    } else if (OID.equals(OID_ECDSA)) {
      ret = "ECDSA";
    }
    return ret;
  }

  public static void init() {}

  public static SecretKey generateSecretKey(String algorithm, int keyLength)
      throws NoSuchAlgorithmException, NoSuchProviderException {
    KeyGenerator keyGen = KeyGenerator.getInstance(algorithm, CRYPTO_PROVIDER);
    keyGen.init(keyLength);
    return keyGen.generateKey();
  }

  public static KeyPair generateKeyPair(String algorithm, int keyLength)
      throws NoSuchAlgorithmException, NoSuchProviderException {
    KeyPairGenerator kpg = KeyPairGenerator.getInstance(algorithm, CRYPTO_PROVIDER);
    kpg.initialize(keyLength);
    return kpg.generateKeyPair();
  }

  public static byte[] getRandomBytes(int length)
      throws NoSuchAlgorithmException, NoSuchProviderException {
    SecureRandom sr = null;
    try {
      new SecureRandom();
      SecureRandom.getInstance("SHA1PRNG");
      sr = SecureRandom.getInstance("SHA1PRNG", CRYPTO_PROVIDER);
    } catch (NoSuchAlgorithmException e) {
    } catch (NoSuchProviderException e2) {
    }
    byte[] out = new byte[length];
    sr.nextBytes(out);
    return out;
  }

  public static byte[] encrypt(Key key, byte[] data)
      throws BadPaddingException,
          NoSuchPaddingException,
          IllegalBlockSizeException,
          NoSuchAlgorithmException,
          InvalidKeyException,
          IOException,
          NoSuchProviderException {
    return encrypt(key, data, key.getAlgorithm());
  }

  /* JADX WARN: Finally extract failed */
  /* JADX WARN: Removed duplicated region for block: B:54:0x0129 A[EXC_TOP_SPLITTER, SYNTHETIC] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
      To view partially-correct add '--show-bad-code' argument
  */
  public static byte[] encrypt(java.security.Key r6, byte[] r7, java.lang.String r8)
      throws javax.crypto.BadPaddingException,
          javax.crypto.NoSuchPaddingException,
          javax.crypto.IllegalBlockSizeException,
          java.security.NoSuchAlgorithmException,
          java.security.InvalidKeyException,
          java.io.IOException,
          java.security.NoSuchProviderException {
    /*
        Method dump skipped, instructions count: 324
        To view this dump add '--comments-level debug' option
    */
    throw new UnsupportedOperationException(
        "Method not decompiled: com.tvec.utility.Crypto.encrypt(java.security.Key, byte[], java.lang.String):byte[]");
  }

  public static byte[] decrypt(Key key, byte[] data)
      throws BadPaddingException,
          NoSuchPaddingException,
          IllegalBlockSizeException,
          NoSuchAlgorithmException,
          InvalidKeyException,
          IOException,
          NoSuchProviderException {
    return decrypt(key, data, key.getAlgorithm());
  }

  /* JADX WARN: Finally extract failed */
  /* JADX WARN: Removed duplicated region for block: B:54:0x010c A[EXC_TOP_SPLITTER, SYNTHETIC] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
      To view partially-correct add '--show-bad-code' argument
  */
  public static byte[] decrypt(java.security.Key r6, byte[] r7, java.lang.String r8)
      throws javax.crypto.BadPaddingException,
          javax.crypto.NoSuchPaddingException,
          javax.crypto.IllegalBlockSizeException,
          java.security.NoSuchAlgorithmException,
          java.security.InvalidKeyException,
          java.io.IOException,
          java.security.NoSuchProviderException {
    /*
        Method dump skipped, instructions count: 295
        To view this dump add '--comments-level debug' option
    */
    throw new UnsupportedOperationException(
        "Method not decompiled: com.tvec.utility.Crypto.decrypt(java.security.Key, byte[], java.lang.String):byte[]");
  }

  public static X509Certificate getCertificateFromString(String certificateString)
      throws CertificateException, NoSuchProviderException {
    X509Certificate cert = null;
    byte[] certificateBytes = certificateString.getBytes();
    if (certificateBytes.length > 0) {
      ByteArrayInputStream inStream = new ByteArrayInputStream(certificateBytes);
      try {
        CertificateFactory cf = CertificateFactory.getInstance("X.509", CRYPTO_PROVIDER);
        cert = (X509Certificate) cf.generateCertificate(inStream);
      } catch (NoSuchProviderException ex) {
        ex.printStackTrace();
      }
    }
    return cert;
  }

  public static String getCertificateString(X509Certificate certificate)
      throws CertificateEncodingException {
    if (certificate != null) {
      byte[] buf = certificate.getEncoded();
      StringBuffer sb = new StringBuffer(buf.length + 100);
      sb.append("-----BEGIN CERTIFICATE-----\n");
      byte[] bytes = Base64.getEncoder().encode(buf);
      int len = bytes.length;
      for (int i = 0; i < len; i += 64) {
        sb.append(new String(bytes, i, i + 64 < len ? 64 : len - i));
        sb.append("\n");
      }
      sb.append("-----END CERTIFICATE-----");
      return sb.toString();
    }
    return "";
  }

  public static String getAlgorithmList() {
    Set<String> result = new HashSet<>();
    Provider provider = Security.getProvider(CRYPTO_PROVIDER);
    logger.debug("Crypto provider info: {}", provider.getInfo());
    Set keys = provider.keySet();
    Iterator it = keys.iterator();
    while (it.hasNext()) {
      String key = ((String) it.next()).split(" ")[0];
      if (key.startsWith("Alg.Alias.")) {
        key = key.substring(10);
      }
      if (key.contains(OID_SIGNATURE_ALGORITHM_SHA1_RSA_PKCS15)) {
        result.add(key);
      }
    }
    String[] temp = result.toArray(new String[result.size()]);
    for (String str : temp) {
      logger.debug("Algorithm: {}", str);
    }
    return "";
  }

  public static final void main(String[] args)
      throws NoSuchPaddingException,
          NoSuchAlgorithmException,
          InvalidKeyException,
          NoSuchProviderException,
          InvalidAlgorithmParameterException {
    test();
  }

  public static void test()
      throws NoSuchPaddingException,
          NoSuchAlgorithmException,
          InvalidKeyException,
          NoSuchProviderException,
          InvalidAlgorithmParameterException {
    testEncryptDecrypt();
  }

  private static void readWrite() throws IOException, CertificateException {
    FileInputStream inStream = null;
    try {
      inStream =
          new FileInputStream(
              "S:\\Synergy Software Solutions\\SSL Certificate and key\\synergyss050120.crt");
    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    }
    try {
      CertificateFactory cf = CertificateFactory.getInstance("X.509");
      X509Certificate cert = (X509Certificate) cf.generateCertificate(inStream);
      String certString = getCertificateString(cert);
      FileOutputStream out =
          new FileOutputStream("S:\\Synergy Software Solutions\\SSL Certificate and key\\test.crt");
      out.write(certString.getBytes());
      out.close();
      try {
        inStream.close();
      } catch (IOException e) {
      }
      inStream =
          new FileInputStream("S:\\Synergy Software Solutions\\SSL Certificate and key\\test.crt");
    } catch (Exception ex2) {
      ex2.printStackTrace();
    }
    if (inStream != null) {
      try {
        inStream.close();
      } catch (IOException e2) {
      }
    }
  }

  private static void testCertificates()
      throws IOException, CertificateException, NoSuchProviderException {
    FileInputStream inStream = null;
    try {
      inStream = new FileInputStream("S:\\jbproject\\piv_card\\cert_test.cer");
    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    }
    try {
      CertificateFactory cf = CertificateFactory.getInstance("X.509", CRYPTO_PROVIDER);
      Collection<? extends Certificate> c = cf.generateCertificates(inStream);
      for (Certificate cert : c) {
        logger.debug("Certificate: {}", cert);
      }
    } catch (Exception ex2) {
      ex2.printStackTrace();
    }
    if (inStream != null) {
      try {
        inStream.close();
      } catch (IOException e) {
      }
    }
  }

  public static boolean ValidateCert(X509Certificate cert) throws Exception {
    boolean result = false;
    String subjectDN = cert.getSubjectX500Principal().getName();
    String issuerDN = cert.getIssuerX500Principal().getName();
    if (subjectDN.equals(issuerDN)) {
      try {
        logger.debug("This is a self-signed certificate. Verifying signature ...");
        cert.verify(cert.getPublicKey());
        logger.debug("Signature verified");
        result = true;
      } catch (Exception e) {
        logger.warn("Failed to verify self-signed certificate");
      }
    } else {
      try {
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(
            new FileInputStream("C://Program Files//java//jre1.5.0_06//lib//security//cacerts"),
            null);
        if (ks.containsAlias("equifaxsecureglobalebusinessca1")) {
          logger.debug("keystore contains alias equifaxsecureglobalebusinessca1");
        }
        if (ks.isCertificateEntry("equifaxsecureglobalebusinessca1")) {
          logger.debug("equifaxsecureglobalebusinessca1 is a certificate in the keystore");
        }
        X509Certificate issuerCert =
            (X509Certificate) ks.getCertificate("equifaxsecureglobalebusinessca1");
        logger.debug("issuer DN: {}", issuerCert.getSubjectX500Principal().getName());
        PublicKey issuerPK = issuerCert.getPublicKey();
        cert.verify(issuerPK);
        logger.debug("Certificate verified");
        Certificate[] certArray = ks.getCertificateChain("equifaxsecureglobalebusinessca1");
        if (certArray == null) {
          throw new Exception("Alias equifaxsecureglobalebusinessca1 is not a certificate chain");
        }
        List<Certificate> certList = Arrays.asList(certArray);
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        CertPath cp = cf.generateCertPath(certList);
        CertPathValidator cpv = CertPathValidator.getInstance("PKIX");
        TrustAnchor anchor =
            new TrustAnchor(
                (X509Certificate) ks.getCertificate("equifaxsecureglobalebusinessca1"), null);
        PKIXParameters params = new PKIXParameters(Collections.singleton(anchor));
        params.setRevocationEnabled(false);
        try {
          PKIXCertPathValidatorResult result2 =
              (PKIXCertPathValidatorResult) cpv.validate(cp, params);
          PolicyNode policyTree = result2.getPolicyTree();
          PublicKey subjectPublicKey = result2.getPublicKey();
          cert.verify(subjectPublicKey);
          logger.info("Certificate validated");
          logger.debug("Policy Tree: {}", policyTree);
          System.out.println("Subject Public key:\n" + subjectPublicKey);
        } catch (CertPathValidatorException cpve) {
          System.out.println(
              "Validation failure, cert[" + cpve.getIndex() + "] :" + cpve.getMessage());
        }
      } catch (Exception e2) {
        e2.printStackTrace();
      }
    }
    return result;
  }

  private static void testCertificates2() {
    KeyPair kp;
    try {
      if (CRYPTO_PROVIDER.equals("BC")) {
        kp = generateKeyPair(SecurityObject.DEFAULT_SIG_ALG, SecurityObject.DEFAULT_KEY_SIZE);
      } else {
        kp = generateKeyPair("DiffieHellman", SecurityObject.DEFAULT_KEY_SIZE);
      }
      byte[] serno = getRandomBytes(8);
      BigInteger sn = new BigInteger(serno).abs();

      X500Principal issuerDN = new X500Principal("CN=TVEC");
      X500Principal subjectDN = new X500Principal("CN=TVEC");

      Date firstDate = new Date();
      firstDate.setTime(firstDate.getTime() - 600000);
      Date lastDate = new Date();
      lastDate.setTime(lastDate.getTime() + 889032704);

      JcaX509v3CertificateBuilder certBuilder =
          new JcaX509v3CertificateBuilder(
              issuerDN, sn, firstDate, lastDate, subjectDN, kp.getPublic());

      ContentSigner signer = new JcaContentSignerBuilder("SHA1withRSA").build(kp.getPrivate());
      X509CertificateHolder certHolder = certBuilder.build(signer);
      X509Certificate cert = new JcaX509CertificateConverter().getCertificate(certHolder);

      System.out.println(
          "b10967b1a30080911ff35615414f4d74c0b5ebd37856e6c5bec04c3e8c36b5b1e3d1825ba81d5aeb6528581ba51bde8276548d3321c96c2ca76747d8a9606a8e8ee37b2993a5035e2fb45026a7fbf71d909e6729a94a4242f69232714372295c37ebcdbf7d3dcff3ef5596629a6813cf9a441434b5086fe95d405c63d54c1dd1"
              .length());
      System.out.println("cert " + cert.toString());
      cert.verify(cert.getPublicKey());
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private static void testEncryptDecrypt()
      throws NoSuchPaddingException,
          NoSuchAlgorithmException,
          InvalidKeyException,
          NoSuchProviderException,
          InvalidAlgorithmParameterException {
    try {
      getRandomBytes(10);
      System.out.println("DESede/ECB/PKCS5Padding test");
      KeyGenerator keyGen = KeyGenerator.getInstance("DESede", CRYPTO_PROVIDER);
      keyGen.init(192);
      SecretKey sk = keyGen.generateKey();
      byte[] format = sk.getEncoded();
      System.out.println("encoded symmetric key:" + UByte.toUnsignedHexString(format));
      SecretKey newKey = new SecretKeySpec(format, "DESede");
      byte[] challenge = "chris".getBytes();
      byte[] dec =
          decrypt(
              newKey,
              encrypt(newKey, challenge, "DESede/ECB/PKCS5Padding"),
              "DESede/ECB/PKCS5Padding");
      System.out.println(
          "orig: "
              + UByte.toUnsignedHexString(challenge)
              + " decoded:"
              + UByte.toUnsignedHexString(dec));
      System.out.println("DESede/ECB/NoPadding test");
      byte[] format2 = UByte.toSignedByteArray("0123456789abcdef23456789abcdef01456789abcdef0123");
      System.out.println("encoded symmetric key:" + UByte.toUnsignedHexString(format2));
      SecretKey newKey2 = new SecretKeySpec(format2, "DESede");
      byte[] enc = UByte.toSignedByteArray("1397db51047bb51e");
      byte[] dec2 = decrypt(newKey2, enc, "DESede/ECB/NoPadding");
      System.out.println(
          "enc:" + UByte.toUnsignedHexString(enc) + "\ndecoded:" + UByte.toUnsignedHexString(dec2));
      byte[] challenge2 = UByte.toSignedByteArray("11616d5a8630b2ba");
      byte[] enc2 = encrypt(newKey2, challenge2, "DESede/ECB/NoPadding");
      byte[] dec3 = decrypt(newKey2, enc2, "DESede/ECB/NoPadding");
      System.out.println(
          "\norig: "
              + UByte.toUnsignedHexString(challenge2)
              + "\nenc:"
              + UByte.toUnsignedHexString(enc2)
              + "\ndecoded:"
              + UByte.toUnsignedHexString(dec3));
      System.out.println("AES/ECB/NoPadding test");
      KeyGenerator keyGen2 = KeyGenerator.getInstance("AES", CRYPTO_PROVIDER);
      keyGen2.init(192);
      SecretKey sk2 = keyGen2.generateKey();
      byte[] format3 = sk2.getEncoded();
      System.out.println("encoded symmetric key:" + UByte.toUnsignedHexString(format3));
      SecretKey newKey3 = new SecretKeySpec(format3, "AES");
      byte[] challenge3 = getRandomBytes(16);
      byte[] dec4 =
          decrypt(newKey3, encrypt(newKey3, challenge3, "AES/ECB/NoPadding"), "AES/ECB/NoPadding");
      System.out.println(
          "orig: "
              + UByte.toUnsignedHexString(challenge3)
              + " decoded:"
              + UByte.toUnsignedHexString(dec4));
      System.out.println("RSA 1024 test");
      KeyFactory keyFactory = KeyFactory.getInstance(SecurityObject.DEFAULT_SIG_ALG);
      KeyPair kp = generateKeyPair(SecurityObject.DEFAULT_SIG_ALG, SecurityObject.DEFAULT_KEY_SIZE);
      getRandomBytes(128);
      byte[] challenge4 =
          UByte.toSignedByteArray(
              "30819f300d06092a864886f70d010101050003818d00308189028181009dbffabab34773a0574fa029a3a4929ebb084bb54f1239c8a33635a9f0cd7bd6f7c405ce0cfee8ceb658246631ac44b575f05f1c3d2e5ab796bb37db49ebff68c1df27c88d5aac5b38d9c6434eadb030cd91f949c348b041e1db9930da86b6d7a16fc61ddddc0cc3cc64377b0b73661b0daefa14d1c8db34f6418c994c33d0970203010001");
      System.out.println(kp.getPrivate().toString());
      System.out.println(kp.getPublic().toString());
      byte[] enc3 = encrypt(kp.getPrivate(), challenge4, SecurityObject.DEFAULT_SIG_ALG);
      System.out.println("challenge:" + UByte.toUnsignedHexString(challenge4));
      System.out.println("encrypted challenge:" + UByte.toUnsignedHexString(enc3));
      byte[] dec5 = decrypt(kp.getPublic(), enc3, SecurityObject.DEFAULT_SIG_ALG);
      System.out.println("Array compare :" + Arrays.equals(challenge4, dec5));
      RSAPublicKey publicKey = (RSAPublicKey) kp.getPublic();
      RSAPrivateKey privateKey = (RSAPrivateKey) kp.getPrivate();
      BigInteger pubExp = publicKey.getPublicExponent();
      BigInteger modulus = publicKey.getModulus();
      BigInteger priExp = privateKey.getPrivateExponent();
      System.out.println("pubExp:" + pubExp.toString());
      System.out.println("priExp:" + priExp.toString());
      System.out.println("modulus:" + modulus.toString());
      RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(modulus, pubExp);
      RSAPublicKey publicKey2 = (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);
      RSAPrivateKeySpec privateKeySpec = new RSAPrivateKeySpec(modulus, priExp);
      RSAPrivateKey privateKey2 = (RSAPrivateKey) keyFactory.generatePrivate(privateKeySpec);
      byte[] enc4 = encrypt(privateKey2, challenge4);
      System.out.println("challenge:" + UByte.toUnsignedHexString(challenge4));
      System.out.println("encrypted challenge:" + UByte.toUnsignedHexString(enc4));
      byte[] dec6 = decrypt(publicKey2, enc4);
      System.out.println("Array compare :" + Arrays.equals(challenge4, dec6));
      System.out.println("After BigInteger tests");
      byte[] publicKeyBytes = kp.getPublic().getEncoded();
      byte[] privateKeyBytes = kp.getPrivate().getEncoded();
      System.out.println(UByte.toUnsignedHexString(publicKeyBytes));
      RSAPublicKey publicKey3 =
          (RSAPublicKey) keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
      System.out.println("publickey: " + publicKey3.toString());
      System.out.println(UByte.toUnsignedHexString(publicKey3.getEncoded()));
      KeyFactory factory = KeyFactory.getInstance(SecurityObject.DEFAULT_SIG_ALG, CRYPTO_PROVIDER);
      System.out.println("pub key format" + kp.getPublic().getFormat());
      System.out.println("pri key format" + kp.getPrivate().getFormat());
      EncodedKeySpec encodedPrivateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
      RSAPrivateKey privateKey3 = (RSAPrivateKey) factory.generatePrivate(encodedPrivateKeySpec);
      EncodedKeySpec encodedPublicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
      System.out.println(UByte.toUnsignedHexString(encodedPublicKeySpec.getEncoded()));
      KeyPair kp2 = new KeyPair(factory.generatePublic(encodedPublicKeySpec), privateKey3);
      byte[] dec7 = decrypt(kp2.getPublic(), encrypt(kp2.getPrivate(), "christest2".getBytes()));
      System.out.println(new String(dec7));
      System.out.println("\nnew test");
      RSAPublicKeySpec publicKeySpec2 =
          new RSAPublicKeySpec(
              new BigInteger(
                  "110775760721057452390557282651428071261346016167261286887615957924070906360182996180880456877857452746235615251324345729344444751846208032898629623348190716062443044962027233264360659989556540191765500247940324648498848633372371830825510803810920475116821956641168788347496876408791222231995622269126943428759"),
              new BigInteger("65537"));
      RSAPublicKey publicKey4 = (RSAPublicKey) keyFactory.generatePublic(publicKeySpec2);
      byte[] dec8 = decrypt(publicKey4, challenge4, "RSA/NONE/NOPADDING");
      System.out.println("dec:" + UByte.toUnsignedHexString(dec8));
      System.out.println("Key Info - algorithm:" + publicKey4.getAlgorithm());
      System.out.println("Key Info - format:" + publicKey4.getFormat());
      System.out.println(
          "Key Info - key bytes:" + UByte.toUnsignedHexString(publicKey4.getEncoded()));
      System.out.println("\nafter new test");
      System.out.println("ECC 224 test");
      ECField field = new ECFieldFp(BigInteger.valueOf(23L));
      EllipticCurve curve = new EllipticCurve(field, BigInteger.ONE, BigInteger.ONE);
      ECPoint basePoint = new ECPoint(BigInteger.ONE, BigInteger.ONE);
      ECParameterSpec params = new ECParameterSpec(curve, basePoint, BigInteger.TEN, 10);
      KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ECDH", CRYPTO_PROVIDER);
      keyPairGenerator.initialize(params);
      KeyPair kp3 = keyPairGenerator.generateKeyPair();
      ECPrivateKey ecPrivateKey = (ECPrivateKey) kp3.getPrivate();
      ECPublicKey ecPublicKey = (ECPublicKey) kp3.getPublic();
      Key iEKeySpec = new IEKeySpec(ecPrivateKey, null);
      try {
        try {
          try {
            Cipher cipher = Cipher.getInstance("IES", CRYPTO_PROVIDER);
            cipher.init(1, iEKeySpec);
          } catch (InvalidKeyException e) {
            System.out.println("Invalid key used to encrypt message!");
            e.printStackTrace();
          }
        } catch (NoSuchProviderException e2) {
          System.out.println("No such Provider found!");
          e2.printStackTrace();
        }
      } catch (NoSuchAlgorithmException e3) {
        System.out.println("No such Algorithm found in this Provider!");
        e3.printStackTrace();
      } catch (NoSuchPaddingException e4) {
        System.out.println("No such Padding available!");
        e4.printStackTrace();
      }
      encrypt(iEKeySpec, challenge4, SecurityObject.DEFAULT_SIG_ALG);
      byte[] enc5 = encrypt(kp3.getPrivate(), challenge4, SecurityObject.DEFAULT_SIG_ALG);
      System.out.println("challenge:" + UByte.toUnsignedHexString(challenge4));
      System.out.println("encrypted challenge:" + UByte.toUnsignedHexString(enc5));
      byte[] dec9 = decrypt(kp3.getPublic(), enc5, SecurityObject.DEFAULT_SIG_ALG);
      System.out.println("Array compare :" + Arrays.equals(challenge4, dec9));
      ECPoint pubPoint = ecPublicKey.getW();
      ECParameterSpec ecParameterSpec = ecPublicKey.getParams();
      BigInteger priInteger = ecPrivateKey.getS();
      ECParameterSpec ecPriParameterSpec = ecPrivateKey.getParams();
      ECPublicKeySpec ecPublicKeySpec = new ECPublicKeySpec(pubPoint, ecParameterSpec);
      ECPrivateKeySpec ecPrivateKeySpec = new ECPrivateKeySpec(priInteger, ecPriParameterSpec);
      byte[] enc6 = encrypt(privateKey3, challenge4);
      System.out.println("challenge:" + UByte.toUnsignedHexString(challenge4));
      System.out.println("encrypted challenge:" + UByte.toUnsignedHexString(enc6));
      byte[] dec10 = decrypt(publicKey4, enc6);
      System.out.println("Array compare :" + Arrays.equals(challenge4, dec10));
      System.out.println("After BigInteger tests");
      byte[] publicKeyBytes2 = kp3.getPublic().getEncoded();
      byte[] privateKeyBytes2 = kp3.getPrivate().getEncoded();
      System.out.println(UByte.toUnsignedHexString(publicKeyBytes2));
      RSAPublicKey publicKey5 =
          (RSAPublicKey) keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes2));
      System.out.println("publickey: " + publicKey5.toString());
      System.out.println(UByte.toUnsignedHexString(publicKey5.getEncoded()));
      KeyFactory factory2 = KeyFactory.getInstance("EC", CRYPTO_PROVIDER);
      System.out.println("pub key format" + kp3.getPublic().getFormat());
      System.out.println("pri key format" + kp3.getPrivate().getFormat());
      EncodedKeySpec encodedPrivateKeySpec2 = new PKCS8EncodedKeySpec(privateKeyBytes2);
      EncodedKeySpec encodedPublicKeySpec2 = new X509EncodedKeySpec(publicKeyBytes2);
      System.out.println(UByte.toUnsignedHexString(encodedPublicKeySpec2.getEncoded()));
      KeyPair kp4 = new KeyPair(publicKey5, privateKey3);
      byte[] dec11 = decrypt(kp4.getPublic(), encrypt(kp4.getPrivate(), "christest2".getBytes()));
      System.out.println(new String(dec11));
    } catch (Exception e5) {
      e5.printStackTrace();
      System.out.println(e5);
    }
  }
}
