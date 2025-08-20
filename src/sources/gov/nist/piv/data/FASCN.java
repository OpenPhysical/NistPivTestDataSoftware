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

/* loaded from: JPIV Test Data Generator.jar:gov/nist/piv/data/FASCN.class */
public class FASCN {
  public static final String PIV_FASCN = "2.16.840.1.101.3.6.6";
  private int agencyCode;
  private int systemCode;
  private int credentialNumber;
  private int credentialSeries;
  private int individualCredentialIssue;
  private long personIdentifier;
  private int organizationalCategory;
  private int organizationalIdentifier;
  private int associationCategory;

  public FASCN(
      int agencyCode,
      int systemCode,
      int credentialNumber,
      int credentialSeries,
      int individualCredentialIssue,
      long personIdentifier,
      int organizationalCategory,
      int organizationalIdentifier,
      int associationCategory)
      throws Exception {
    this.agencyCode = 0;
    this.systemCode = 0;
    this.credentialNumber = 0;
    this.credentialSeries = 0;
    this.individualCredentialIssue = 0;
    this.personIdentifier = 0L;
    this.organizationalCategory = 0;
    this.organizationalIdentifier = 0;
    this.associationCategory = 0;
    try {
      setAgencyCode(agencyCode);
      setSystemCode(systemCode);
      setCredentialNumber(credentialNumber);
      setCredentialSeries(credentialSeries);
      setIndividualCredentialIssue(individualCredentialIssue);
      setPersonIdentifier(personIdentifier);
      setOrganizationalCategory(organizationalCategory);
      setOrganizationalIdentifier(organizationalIdentifier);
      setAssociationCategory(associationCategory);
    } catch (Exception e) {
      throw e;
    }
  }

  public byte[] getBytes() {
    String s = Integer.toString(this.agencyCode);
    for (int i = s.length(); i < 4; i++) {
      s = "0" + s;
    }
    String fascn = "S" + s.substring(0, 4) + "F";
    String sc = Integer.toString(this.systemCode);
    for (int i2 = sc.length(); i2 < 4; i2++) {
      sc = "0" + sc;
    }
    String fascn2 = fascn + sc.substring(0, 4) + "F";
    String cn = Integer.toString(this.credentialNumber);
    for (int i3 = cn.length(); i3 < 6; i3++) {
      cn = "0" + cn;
    }
    String fascn3 =
        fascn2
            + cn.substring(0, 6)
            + "F"
            + Integer.toString(this.credentialSeries).charAt(0)
            + "F"
            + Integer.toString(this.individualCredentialIssue).charAt(0)
            + "F";
    String s2 = Long.toString(this.personIdentifier);
    for (int i4 = s2.length(); i4 < 10; i4++) {
      s2 = "0" + s2;
    }
    String fascn4 = fascn3 + s2 + Integer.toString(this.associationCategory).charAt(0);
    String s3 = Integer.toString(this.organizationalIdentifier);
    for (int i5 = s3.length(); i5 < 4; i5++) {
      s3 = "0" + s3;
    }
    String fascn5 =
        fascn4 + s3.substring(0, 4) + Integer.toString(this.organizationalCategory).charAt(0) + "E";
    String bits = "";
    for (int i6 = 0; i6 < fascn5.length(); i6++) {
      bits = bits + Utility.ascii2BcdBitString(fascn5.charAt(i6));
    }
    String lrc = "";
    for (int i7 = 0; i7 < 4; i7++) {
      int parity = 0;
      for (int j = 0; j < 39; j++) {
        parity += bits.charAt(i7 + (j * 5)) - '0';
      }
      lrc = lrc + (parity % 2 == 1 ? "1" : "0");
    }
    int parity2 = 0;
    for (int i8 = 0; i8 < lrc.length(); i8++) {
      parity2 += lrc.charAt(i8) - '0';
    }
    String bits2 = bits + lrc + (parity2 % 2 == 1 ? "0" : "1");
    byte[] bcd = new byte[25];
    for (int i9 = 0; i9 < 25; i9++) {
      for (int j2 = i9 * 8; j2 < (i9 * 8) + 8; j2++) {
        bcd[i9] = (byte) ((bcd[i9] << 1) + (bits2.charAt(j2) == '1' ? 1 : 0));
      }
    }
    return bcd;
  }

  public FASCN(byte[] in) {
    this.agencyCode = 0;
    this.systemCode = 0;
    this.credentialNumber = 0;
    this.credentialSeries = 0;
    this.individualCredentialIssue = 0;
    this.personIdentifier = 0L;
    this.organizationalCategory = 0;
    this.organizationalIdentifier = 0;
    this.associationCategory = 0;
    String bitString = "";
    for (int i = 0; i < 25; i++) {
      for (int j = 7; j >= 0; j--) {
        bitString = bitString + ((in[i] & (1 << j)) != 0 ? 1 : 0);
      }
    }
    String bitString2 = bitString.substring(0, bitString.length() - 5);
    String fascnString = "";
    for (int i2 = 0; i2 < bitString2.length() / 5; i2++) {
      fascnString =
          fascnString + Utility.bcdBitString2ascii(bitString2.substring(i2 * 5, (i2 * 5) + 5));
    }
    this.agencyCode = Integer.parseInt(fascnString.substring(1, 1 + 4));
    int offset = 1 + 5;
    this.systemCode = Integer.parseInt(fascnString.substring(offset, offset + 4));
    int offset2 = offset + 5;
    this.credentialNumber = Integer.parseInt(fascnString.substring(offset2, offset2 + 6));
    int offset3 = offset2 + 7;
    this.credentialSeries = Integer.parseInt(fascnString.substring(offset3, offset3 + 1));
    int offset4 = offset3 + 2;
    this.individualCredentialIssue = Integer.parseInt(fascnString.substring(offset4, offset4 + 1));
    int offset5 = offset4 + 2;
    this.personIdentifier = Long.parseLong(fascnString.substring(offset5, offset5 + 10));
    int offset6 = offset5 + 10;
    this.associationCategory = Integer.parseInt(fascnString.substring(offset6, offset6 + 1));
    int offset7 = offset6 + 1;
    this.organizationalIdentifier = Integer.parseInt(fascnString.substring(offset7, offset7 + 4));
    int offset8 = offset7 + 4;
    this.organizationalCategory = Integer.parseInt(fascnString.substring(offset8, offset8 + 1));
    int i3 = offset8 + 1;
  }

  public static void main(String[] args) {
    System.out.println("Testing the FASC-N Generator...");
    try {
      String generated =
          Utility.byteArray2HexString(
              new FASCN(1341, 1, 987654, 1, 1, Long.parseLong("1234567890"), 1, 1341, 1).getBytes(),
              " ");
      System.out.println(
          "What we want: D4 32 48 58 21 0C 2D 31 71 B5 25 A1 68 58 21 0C 26 2E 36 A4 84 32 48 43 F3\nWhat we got:  "
              + generated);
      if ("D4 32 48 58 21 0C 2D 31 71 B5 25 A1 68 58 21 0C 26 2E 36 A4 84 32 48 43 F3"
          .equals(generated)) {
        System.out.println("success!  :)");
      } else {
        System.out.println("failure... :(");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public int getAgencyCode() {
    return this.agencyCode;
  }

  public void setAgencyCode(int agencyCode) throws Exception {
    this.agencyCode = agencyCode % 10000;
  }

  public int getAssociationCategory() {
    return this.associationCategory;
  }

  public void setAssociationCategory(int associationCategory) throws Exception {
    this.associationCategory = associationCategory % 10;
  }

  public int getCredentialNumber() {
    return this.credentialNumber;
  }

  public void setCredentialNumber(int credentialNumber) throws Exception {
    this.credentialNumber = credentialNumber;
  }

  public int getCredentialSeries() {
    return this.credentialSeries;
  }

  public void setCredentialSeries(int credentialSeries) throws Exception {
    this.credentialSeries = credentialSeries % 10;
  }

  public int getIndividualCredentialIssue() {
    return this.individualCredentialIssue;
  }

  public void setIndividualCredentialIssue(int individualCredentialIssue) throws Exception {
    this.individualCredentialIssue = individualCredentialIssue % 10;
  }

  public long getPersonIdentifier() {
    return this.personIdentifier;
  }

  public void setPersonIdentifier(long personIdentifier) throws Exception {
    String s = Long.toString(personIdentifier);
    if (s.length() > 10) {
      personIdentifier = Long.parseLong(s.substring(s.length() - 10));
    }
    this.personIdentifier = personIdentifier;
  }

  public int getOrganizationalCategory() {
    return this.organizationalCategory;
  }

  public void setOrganizationalCategory(int organizationalCategory) throws Exception {
    this.organizationalCategory = organizationalCategory % 10;
  }

  public int getOrganizationalIdentifier() {
    return this.organizationalIdentifier;
  }

  public void setOrganizationalIdentifier(int organizationalIdentifier) throws Exception {
    this.organizationalIdentifier = organizationalIdentifier % 10000;
  }

  public int getSystemCode() {
    return this.systemCode;
  }

  public void setSystemCode(int systemCode) throws Exception {
    this.systemCode = systemCode % 10000;
  }
}
