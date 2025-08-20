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

package com.tvec.utility.displays.output;

/* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/displays/output/ErrorMessageSender.class */
public interface ErrorMessageSender {
  void addErrorMessageListener(ErrorMessageListener errorMessageListener);

  void deleteErrorMessageListener(ErrorMessageListener errorMessageListener);

  void sendErrorMessage(ErrorMessage errorMessage);
}
