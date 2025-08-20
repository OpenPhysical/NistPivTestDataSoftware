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

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/* compiled from: OutputPropertiesDialog.java */
/* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/displays/output/ScrollbackBufferVerifier.class */
class ScrollbackBufferVerifier extends InputVerifier {
  ScrollbackBufferVerifier() {}

  public boolean verify(JComponent input) throws NumberFormatException {
    boolean ret = false;
    JTextField tf = (JTextField) input;
    try {
      int bufferSize = Integer.parseInt(tf.getText());
      if (bufferSize < 1) {
        JOptionPane.showMessageDialog(
            null,
            "Invalid Scrollback Buffer Size",
            "Scrollback buffer size must be an integer greater than 0.",
            0);
      } else {
        ret = true;
      }
    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(
          null,
          "Invalid Scrollback Buffer Size",
          "Scrollback buffer size must be an integer greater than 0.",
          0);
    }
    return ret;
  }
}
