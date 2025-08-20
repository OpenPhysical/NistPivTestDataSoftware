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

/* loaded from: JPIV Test Data Generator.jar:gov/nist/piv/data/CCC.class */
public class CCC {
  public static final int CARD_ID_TAG = 240;
  public static final int CCVN_TAG = 241;
  public static final int CGVN_TAG = 242;
  public static final int APP_CARD_URL_TAG = 243;
  public static final int PKCS15_TAG = 244;
  public static final int REGISTERED_DATA_MODEL_NUMBER_TAG = 245;
  public static final int ACR_TABLE_TAG = 246;
  public static final int CARD_APDUS_TAG = 247;
  public static final int REDIRECTION_TAG = 250;
  public static final int CAPABILITY_TUPLES_TAG = 251;
  public static final int STATUS_TUPLES_TAG = 252;
  public static final int NEXT_CCC_TAG = 253;
  public static final int EXTENDED_APP_CARD_URL_TAG = 227;
  public static final int SECURITY_OBJECT_BUFFER_TAG = 180;
  public static final int ERROR_DETECTION_CODE_TAG = 254;
  public static final byte[] ccvn = {33};
  public static final byte[] cgvn = {33};
  public static final byte[] pkcs15 = {17};
  public static final byte[] rdmn = {16};
  public static final byte[] acrTable = new byte[17];
  private final byte[] cardID;

  public CCC(String cardID) throws Exception {
    if (cardID.length() > 21) {
      throw new Exception("The cardID cannot be longer than 21 bytes.");
    }
    this.cardID = cardID.getBytes();
  }

  public byte[] getBytes() {
    byte[] ccc = Utility.generateTLV(CARD_ID_TAG, this.cardID);
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
                                                    ccc, Utility.generateTLV(CCVN_TAG, ccvn)),
                                                Utility.generateTLV(CGVN_TAG, cgvn)),
                                            new byte[] {-13, 0}),
                                        Utility.generateTLV(PKCS15_TAG, pkcs15)),
                                    Utility.generateTLV(REGISTERED_DATA_MODEL_NUMBER_TAG, rdmn)),
                                Utility.generateTLV(ACR_TABLE_TAG, acrTable)),
                            Utility.generateTLV(CARD_APDUS_TAG, new byte[0])),
                        Utility.generateTLV(REDIRECTION_TAG, new byte[0])),
                    Utility.generateTLV(CAPABILITY_TUPLES_TAG, new byte[0])),
                Utility.generateTLV(STATUS_TUPLES_TAG, new byte[0])),
            Utility.generateTLV(NEXT_CCC_TAG, new byte[0])),
        Utility.generateTLV(254, new byte[0]));
  }
}
