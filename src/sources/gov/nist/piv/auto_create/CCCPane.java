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

/* loaded from: JPIV Test Data Generator.jar:gov/nist/piv/auto_create/CCCPane.class */
public class CCCPane extends DataPane {
  private static final long serialVersionUID = 1;

  public CCCPane() {
    super("CCC");
    ListManager cardId = new ListManager("Card ID", 21, 21);
    cardId.setAvailable(new String[] {"123456789012345678901"});
    add(cardId);
  }
}
