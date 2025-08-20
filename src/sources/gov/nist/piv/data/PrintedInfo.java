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

import java.util.regex.Pattern;

/* loaded from: JPIV Test Data Generator.jar:gov/nist/piv/data/PrintedInfo.class */
public class PrintedInfo {
  private static final String TEST_DATA_INDICATOR = " - PIV Test";
  private String name = "";
  private String employeeAffiliation1 = "";
  private String employeeAffiliation2 = "";
  private String expirationDate = "";
  private String agencyCardSerialNumber = "";
  private String issuerID = "";
  private final String lrc = "";
  public static final int NAME_TAG = 1;
  public static final int EMP_AFFIL1_TAG = 2;
  public static final int EMP_AFFIL2_TAG = 3;
  public static final int EXPIRATION_DATE_TAG = 4;
  public static final int AGENCY_CARD_SN_TAG = 5;
  public static final int ISSUER_ID_TAG = 6;
  public static final int EDC_TAG = 254;

  public PrintedInfo() {}

  public PrintedInfo(
      String name,
      String employeeAffiliation1,
      String employeeAffiliation2,
      String expirationDate,
      String agencyCardSerialNumber,
      String issuerID)
      throws PrintedInfoFormatException {
    try {
      setName(name);
      setEmployeeAffiliation1(employeeAffiliation1);
      setEmployeeAffiliation2(employeeAffiliation2);
      setExpirationDate(expirationDate);
      setAgencyCardSerialNumber(agencyCardSerialNumber);
      setIssuerID(issuerID);
    } catch (PrintedInfoFormatException e) {
      throw e;
    }
  }

  public byte[] getBytes() {
    byte[] b = Utility.generateTLV(1, this.name);
    return Utility.appendBytes(
        Utility.appendBytes(
            Utility.appendBytes(
                Utility.appendBytes(
                    Utility.appendBytes(
                        Utility.appendBytes(b, Utility.generateTLV(2, this.employeeAffiliation1)),
                        Utility.generateTLV(3, this.employeeAffiliation2)),
                    Utility.generateTLV(4, this.expirationDate)),
                Utility.generateTLV(5, this.agencyCardSerialNumber)),
            Utility.generateTLV(6, this.issuerID)),
        Utility.generateTLV(254, this.lrc));
  }

  public String getAgencyCardSerialNumber() {
    return this.agencyCardSerialNumber;
  }

  public void setAgencyCardSerialNumber(String agencyCardSerialNumber)
      throws PrintedInfoFormatException {
    if (agencyCardSerialNumber.length() > 20) {
      throw new PrintedInfoFormatException(
          "The Agency Card Serial number is at most 10 ASCII characters long.");
    }
    this.agencyCardSerialNumber = agencyCardSerialNumber;
  }

  public String getEmployeeAffiliation1() {
    return this.employeeAffiliation1;
  }

  public void setEmployeeAffiliation1(String employeeAffiliation1)
      throws PrintedInfoFormatException {
    if (employeeAffiliation1.length() > 20) {
      throw new PrintedInfoFormatException(
          "The Employee Affiliation lines are at most 20 ASCII characters long.");
    }
    this.employeeAffiliation1 = employeeAffiliation1;
  }

  public String getEmployeeAffiliation2() {
    return this.employeeAffiliation2;
  }

  public void setEmployeeAffiliation2(String employeeAffiliation2)
      throws PrintedInfoFormatException {
    if (employeeAffiliation2.length() > 20) {
      throw new PrintedInfoFormatException(
          "The Employee Affiliation lines are at most 20 ASCII characters long.");
    }
    this.employeeAffiliation2 = employeeAffiliation2;
  }

  public String getExpirationDate() {
    return this.expirationDate;
  }

  public void setExpirationDate(String expirationDate) throws PrintedInfoFormatException {
    Pattern pattern =
        Pattern.compile("[0-9]{4,4}(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)[0-9]{2,2}");
    if (!pattern.matcher(expirationDate).matches()) {
      throw new PrintedInfoFormatException(
          "Expiration date is formatted incorrectly.  Make sure it is YYYYMMMDD(e.g. \"2011FEB19\"");
    }
    this.expirationDate = expirationDate;
  }

  public String getIssuerID() {
    return this.issuerID;
  }

  public void setIssuerID(String issuerID) throws PrintedInfoFormatException {
    if (issuerID.length() > 15) {
      throw new PrintedInfoFormatException(
          "The IssuerID field can be at most 15 ASCII characters long.");
    }
    this.issuerID = issuerID;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) throws PrintedInfoFormatException {
    if (name.length() != 32) {
      throw new PrintedInfoFormatException(
          "The Printed Name field must be 32 ASCII characters long.");
    }
    this.name = name.substring(0, 32 - " - PIV Test".length()) + " - PIV Test";
  }

  public static void main(String[] args) {
    try {
      System.out.println(
          Utility.byteArray2HexString(
              new PrintedInfo("Chad Blomquist", "Dept. of Commerce", "NIST", "16NOV2005", "", "")
                  .getBytes(),
              " "));
    } catch (PrintedInfoFormatException e) {
      e.printStackTrace();
    }
    System.out.println("success!");
  }
}
