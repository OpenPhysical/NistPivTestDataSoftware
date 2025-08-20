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

package com.tvec.utility.configuration.editor;

import com.tvec.utility.configuration.Configuration;
import com.tvec.utility.configuration.ConfigurationGroup;
import com.tvec.utility.configuration.FormatGroup;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/configuration/editor/EmptyPanel.class */
public class EmptyPanel extends JPanel implements EditPanelInterface {
  private static final long serialVersionUID = 1;
  JLabel jLabel1 = new JLabel();
  BorderLayout borderLayout1 = new BorderLayout();

  public EmptyPanel() {
    try {
      jbInit();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  @Override // com.tvec.utility.configuration.editor.EditPanelInterface
  public void init(
      Configuration configuration,
      FormatGroup formatGroup,
      ConfigurationGroup configurationGroup) {}

  @Override // com.tvec.utility.configuration.editor.EditPanelInterface
  public void focusLost() {}

  private void jbInit() throws Exception {
    this.jLabel1.setText("");
    setLayout(this.borderLayout1);
    add(this.jLabel1, "Center");
  }
}
