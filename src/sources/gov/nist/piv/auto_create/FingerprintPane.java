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

/* loaded from: JPIV Test Data Generator.jar:gov/nist/piv/auto_create/FingerprintPane.class */
public class FingerprintPane extends DataPane {
  private static final long serialVersionUID = 1;

  public FingerprintPane() {
    super("Fingerprint");
    PathManager fingerprintData = new PathManager("Fingerprint Data");
    fingerprintData.setDescription("Path to the fingerprint data.");
    fingerprintData.setAvailable(new String[] {"./extra_files/minutiae"});
    add(fingerprintData);
    ListManager creationDate = new ListManager("Creation Date", 14, 14);
    creationDate.setDescription("The date this file was created.  (YYYYMMDDhhmmss)");
    creationDate.setAvailable(new String[] {"20070419112233"});
    add(creationDate);
    ListManager validityStartDate = new ListManager("Validity Start Date", 14, 14);
    validityStartDate.setDescription(
        "The date at which this biometric becomes useable.  YYYYMMDDhhmmss");
    validityStartDate.setAvailable(new String[] {"20070419112233"});
    add(validityStartDate);
    ListManager monthsValid = new ListManager("Months Valid", 2, 2);
    monthsValid.setDescription("Number of months valid");
    monthsValid.setAvailable(new String[] {"48"});
    add(monthsValid);
  }
}
