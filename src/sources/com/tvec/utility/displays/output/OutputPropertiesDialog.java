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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/displays/output/OutputPropertiesDialog.class */
public class OutputPropertiesDialog extends JDialog {
  private static final long serialVersionUID = 1;
  private int scrollbackBufferSize;
  public static final int OK_OPTION = 0;
  public static final int CANCEL_OPTION = 2;
  private int selection;
  JButton jButtonOk;
  JPanel jPanelButtons;
  JButton jButtonCancel;
  JPanel jPanelMain;
  JLabel jLabelScrollBackBufferSize;
  JTextField jTextFieldScrollbackBufferSize;

  public OutputPropertiesDialog(Component parent) {
    this();
    setLocationRelativeTo(parent);
  }

  public int getSelection() {
    return this.selection;
  }

  public OutputPropertiesDialog() {
    this.scrollbackBufferSize = 100;
    this.selection = 2;
    this.jButtonOk = new JButton();
    this.jPanelButtons = new JPanel();
    this.jButtonCancel = new JButton();
    this.jPanelMain = new JPanel();
    this.jLabelScrollBackBufferSize = new JLabel();
    this.jTextFieldScrollbackBufferSize = new JTextField();
    try {
      jbInit();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    this.jTextFieldScrollbackBufferSize.setInputVerifier(new ScrollbackBufferVerifier());
    setModal(true);
    pack();
  }

  public void setScrollbackBufferSize(int scrollbackBufferSize) {
    this.scrollbackBufferSize = scrollbackBufferSize;
    this.jTextFieldScrollbackBufferSize.setText(String.valueOf(scrollbackBufferSize));
  }

  public int getScrollbackBufferSize() {
    return this.scrollbackBufferSize;
  }

  private void jbInit() throws Exception {
    this.jButtonOk.setToolTipText("");
    this.jButtonOk.setText("OK");
    this.jButtonOk.addActionListener(new OutputPropertiesDialog_jButtonOk_actionAdapter(this));
    this.jButtonCancel.setText("Cancel");
    this.jButtonCancel.addActionListener(
        new OutputPropertiesDialog_jButtonCancel_actionAdapter(this));
    this.jPanelButtons.setToolTipText("");
    this.jLabelScrollBackBufferSize.setToolTipText("");
    this.jLabelScrollBackBufferSize.setText("Scollback Buffer Size");
    this.jTextFieldScrollbackBufferSize.setMinimumSize(new Dimension(40, 20));
    this.jTextFieldScrollbackBufferSize.setPreferredSize(new Dimension(40, 20));
    getContentPane().add(this.jPanelButtons, "South");
    this.jPanelButtons.add(this.jButtonOk);
    this.jPanelButtons.add(this.jButtonCancel);
    getContentPane().add(this.jPanelMain, "Center");
    this.jPanelMain.add(this.jLabelScrollBackBufferSize);
    this.jPanelMain.add(this.jTextFieldScrollbackBufferSize);
  }

  public void jButtonOk_actionPerformed(ActionEvent e) {
    String bufferText = this.jTextFieldScrollbackBufferSize.getText();
    this.scrollbackBufferSize = Integer.parseInt(bufferText);
    this.selection = 0;
    setVisible(false);
  }

  public void jButtonCancel_actionPerformed(ActionEvent e) {
    this.selection = 2;
    setVisible(false);
  }
}
