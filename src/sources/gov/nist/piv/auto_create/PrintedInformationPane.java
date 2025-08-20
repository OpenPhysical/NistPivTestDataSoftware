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

import org.w3c.dom.Node;

/* loaded from: JPIV Test Data Generator.jar:gov/nist/piv/auto_create/PrintedInformationPane.class */
public class PrintedInformationPane extends DataPane {
  private static final long serialVersionUID = 1;

  public PrintedInformationPane() {
    super("PrintedInformation");
    ListManager printedName = new ListManager("Printed Name", 32, 32);
    printedName.setAvailable(new String[] {"123456789012345678901 - PIV Test"});
    add(printedName);
    ListManager employeeAffilation1 = new ListManager("Employee Affiliation Line 1", 20, 20);
    employeeAffilation1.setAvailable(new String[] {"12345678901234567890"});
    add(employeeAffilation1);
    ListManager employeeAffilation2 = new ListManager("Employee Affiliation Line 2", 20, 20);
    employeeAffilation2.setAvailable(new String[] {"12345678901234567890"});
    add(employeeAffilation2);
    ListManager expirationDate = new ListManager("Expiration Date", 9, 9);
    expirationDate.setAvailable(new String[] {"2008AUG24"});
    add(expirationDate);
    ListManager serialNumber = new ListManager("Card Serial Number", 10, 10);
    serialNumber.setAvailable(new String[] {"1234567890"});
    add(serialNumber);
    ListManager issuerId = new ListManager("Issuer Identification", 15, 15);
    issuerId.setAvailable(new String[] {"123456789012345"});
    add(issuerId);
  }

  public PrintedInformationPane(Node node) {
    try {
      loadData(node);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
