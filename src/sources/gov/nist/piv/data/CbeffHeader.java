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

/* loaded from: JPIV Test Data Generator.jar:gov/nist/piv/data/CbeffHeader.class */
public class CbeffHeader {
  public static final byte PATRON_HEADER_VERSION = 3;
  public static final byte SBH_SECURIRY_OPTIONS = 13;
  public static final byte[] BDB_FORMAT_OWNER = {0, 27};
  public static final byte[] BDB_FORMAT_TYPE_FINGER_IMAGE = {4, 1};
  public static final byte[] BDB_FORMAT_TYPE_FINGER_MINUTIAE = {2, 1};
  public static final byte[] BDB_FORMAT_TYPE_FACIAL_IMAGE = {5, 1};
  public static final byte[] BIOMETRIC_TYPE_FINGER = {0, 0, 8};
  public static final byte[] BIOMETRIC_TYPE_FACIAL = {0, 0, 2};
  public static final byte BIOMETRIC_DATA_TYPE_RAW = 32;
  public static final byte BIOMETRIC_DATA_TYPE_INTERMETIATE = 64;
  public static final byte BIOMETRIC_DATA_TYPE_PROCESSED = Byte.MIN_VALUE;
  public static final byte BIOMETRIC_DATA_QUALITY_UNSUPPORTED = -2;
  public static final byte BIOMETRIC_DATA_QUALITY_COMPUTATION_FAILED = -1;
  private byte[] bioDataBlockLength;
  private byte[] sigBlockLength;
  private byte[] bdbFormatType;
  private byte[] creationDate;
  private byte[] validityPeriod;
  private byte[] biometricType;
  private byte biometricDataType;
  private byte biometricDataQuality;
  private byte[] creator;
  private FASCN fascn;

  public static byte[] cbeffDate(String date) throws CbeffHeaderFormatException {
    if (date.length() != 14) {
      throw new CbeffHeaderFormatException(
          "The date input must be a 14-character Sting: YYYYMMDDhhmmss");
    }
    byte[] cbeffDate = new byte[8];
    for (int i = 0; i < 7; i++) {
      cbeffDate[i] = (byte) ((((date.charAt(2 * i) - '0') * 10) + date.charAt((2 * i) + 1)) - 48);
    }
    cbeffDate[7] = 90;
    return cbeffDate;
  }

  public CbeffHeader(
      int bioLength,
      int signatureLength,
      byte[] bdbFormatType,
      byte[] creationDate,
      byte[] validityStarts,
      int monthsValid,
      byte[] biometricType,
      byte biometricDataType,
      byte biometricDataQuality,
      byte[] creator,
      FASCN fascn)
      throws CbeffHeaderFormatException {
    try {
      setBioDataBlockLength(bioLength);
      setSigBlockLength(signatureLength);
      setBdbFormatType(bdbFormatType);
      setCreationDate(creationDate);
      setValidityPeriod(validityStarts, monthsValid);
      setBiometricType(biometricType);
      setBiometricDataType(biometricDataType);
      setBiometricDataQuality(biometricDataQuality);
      setCreator(creator);
      setFascn(fascn);
    } catch (CbeffHeaderFormatException e) {
      throw e;
    }
  }

  public byte[] getBytes() {
    byte[] header = {3};
    return Utility.appendBytes(
        Utility.appendBytes(
            Utility.appendBytes(
                Utility.appendBytes(
                    Utility.appendBytes(
                        Utility.appendBytes(
                            Utility.appendBytes(
                                Utility.appendBytes(
                                    Utility.appendBytes(
                                        Utility.appendBytes(
                                            Utility.appendBytes(
                                                Utility.appendBytes(
                                                    Utility.appendBytes(header, new byte[] {13}),
                                                    this.bioDataBlockLength),
                                                this.sigBlockLength),
                                            BDB_FORMAT_OWNER),
                                        this.bdbFormatType),
                                    this.creationDate),
                                this.validityPeriod),
                            this.biometricType),
                        new byte[] {this.biometricDataType}),
                    new byte[] {this.biometricDataQuality}),
                this.creator),
            this.fascn.getBytes()),
        new byte[4]);
  }

  public byte[] getBdbFormatType() {
    return this.bdbFormatType;
  }

  public void setBdbFormatType(byte[] bdbFormatType) throws CbeffHeaderFormatException {
    if (bdbFormatType.length != 2) {
      throw new CbeffHeaderFormatException(
          "The BDB Format Type must be a 2-byte value.  It is recommended that you use one of the pre-defined static values that is built into this class.");
    }
    this.bdbFormatType = bdbFormatType;
  }

  public byte[] getBioDataBlockLength() {
    return this.bioDataBlockLength;
  }

  public void setBioDataBlockLength(byte[] bioDataBlockLength) {
    this.bioDataBlockLength = bioDataBlockLength;
  }

  public void setBioDataBlockLength(int bioLength) {
    this.bioDataBlockLength =
        new byte[] {
          (byte) (bioLength / 16777216),
          (byte) ((bioLength % 16777216) / 65536),
          (byte) ((bioLength % 65536) / SecurityObject.DIGITAL_SIGNATURE_CERT_CONTAINER_ID),
          (byte) (bioLength % SecurityObject.DIGITAL_SIGNATURE_CERT_CONTAINER_ID)
        };
  }

  public byte getBiometricDataQuality() {
    return this.biometricDataQuality;
  }

  public void setBiometricDataQuality(byte biometricDataQuality) throws CbeffHeaderFormatException {
    if (biometricDataQuality > 100) {
      throw new CbeffHeaderFormatException(
          "The Biometric Data Quality is a number between -2 and 100.  See SP 800-76 for more information.");
    }
    this.biometricDataQuality = biometricDataQuality;
  }

  public byte getBiometricDataType() {
    return this.biometricDataType;
  }

  public void setBiometricDataType(byte biometricDataType) {
    this.biometricDataType = biometricDataType;
  }

  public byte[] getBiometricType() {
    return this.biometricType;
  }

  public void setBiometricType(byte[] biometricType) throws CbeffHeaderFormatException {
    if (biometricType.length != 3) {
      throw new CbeffHeaderFormatException(
          "The BDB Format Type must be a 3-byte value.  Use one of the pre-defined static values that is built into this class.");
    }
    this.biometricType = biometricType;
  }

  public byte[] getCreationDate() {
    return this.creationDate;
  }

  public void setCreationDate(byte[] creationDate) throws CbeffHeaderFormatException {
    if (creationDate.length != 8) {
      throw new CbeffHeaderFormatException(
          "The given creation date must be 8 bytes.  Refer to the SP 800-76 document for more information.");
    }
    this.creationDate = creationDate;
  }

  public byte[] getCreator() {
    return this.creator;
  }

  public void setCreator(byte[] creator) throws CbeffHeaderFormatException {
    if (creator.length > 18) {
      throw new CbeffHeaderFormatException(
          "The given Creator string is greater than the maximum of 18 characters.  Please pick a shorter name.");
    }
    this.creator = new byte[18];
    System.arraycopy(creator, 0, this.creator, 0, creator.length);
    for (int i2 = creator.length; i2 < 18; i2++) {
      this.creator[i2] = 0;
    }
  }

  public FASCN getFascn() {
    return this.fascn;
  }

  public void setFascn(FASCN fascn) {
    this.fascn = fascn;
  }

  public byte[] getSigBlockLength() {
    return this.sigBlockLength;
  }

  public void setSigBlockLength(byte[] sigBlockLength) {
    this.sigBlockLength = sigBlockLength;
  }

  public void setSigBlockLength(int signatureLength) throws CbeffHeaderFormatException {
    if (signatureLength > 65535) {
      throw new CbeffHeaderFormatException(
          "The incoming signature length value is too large.  Only 2 bytes in the header are reserved for the length of the signature block.");
    }
    this.sigBlockLength =
        new byte[] {
          (byte) ((signatureLength % 65536) / SecurityObject.DIGITAL_SIGNATURE_CERT_CONTAINER_ID),
          (byte) (signatureLength % SecurityObject.DIGITAL_SIGNATURE_CERT_CONTAINER_ID)
        };
  }

  public byte[] getValidityPeriod() {
    return this.validityPeriod;
  }

  public void setValidityPeriod(byte[] validityPeriod) {
    this.validityPeriod = validityPeriod;
  }

  public void setValidityPeriod(byte[] start, int months) throws CbeffHeaderFormatException {
    if (start.length != 8) {
      throw new CbeffHeaderFormatException(
          "The value of the start date of the Validity Period is malformatted.  It must be 8-bytes long, formatted as per SP 800-76.");
    }
    int endYear = (start[0] * 100) + start[1] + (months / 12);
    int endMonth = start[2] + (months % 12);
    if (endMonth > 12) {
      endYear++;
      endMonth %= 12;
    }
    this.validityPeriod = Utility.appendBytes(start, start);
    this.validityPeriod[8] = (byte) (endYear / 100);
    this.validityPeriod[9] = (byte) (endYear % 100);
    this.validityPeriod[10] = (byte) endMonth;
  }
}
