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

/* loaded from: JPIV Test Data Generator.jar:gov/nist/piv/auto_create/CHUIDPane.class */
public class CHUIDPane extends DataPane {
  private static final long serialVersionUID = 1;

  public CHUIDPane() {
    super("CHUID");
    ListManager fascn = new ListManager("FASC-N", 25, 25);
    fascn.setDescription("White space will be removed from FASC-N.");
    fascn.setRemoveWhiteSpace(true);
    fascn.setAvailable(
        new String[] {
          "D6 50 18 58 21 0C 2D 31 71 B5 25 A1 68 5A 08 C9 2A DE 0A 61 86 50 18 43 E2"
              .replaceAll(" ", "")
        });
    add(fascn);
    ListManager guid = new ListManager("GUID", 16, 16);
    guid.setAvailable(new String[] {"1234567890123456"});
    add(guid);
    ListManager expDate = new ListManager("Expiration Date", 8, 8);
    expDate.setDescription("YYYMMDD format");
    expDate.setAvailable(new String[] {"20090824"});
    add(expDate);
  }
}
