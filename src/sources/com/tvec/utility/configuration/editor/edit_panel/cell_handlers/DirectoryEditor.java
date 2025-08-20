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

package com.tvec.utility.configuration.editor.edit_panel.cell_handlers;

import com.tvec.utility.configuration.editor.edit_panel.BaseRowData;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

/* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/configuration/editor/edit_panel/cell_handlers/DirectoryEditor.class */
public class DirectoryEditor extends JPanel implements BaseHandler {
  private static final long serialVersionUID = 1;
  private BaseRowData baseRowData;
  JTextField jTextFieldDirectory = new JTextField();
  JButton jButtonSelectDirectory = new JButton();
  BorderLayout borderLayout1 = new BorderLayout();

  public DirectoryEditor() {
    try {
      jbInit();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  @Override // com.tvec.utility.configuration.editor.edit_panel.cell_handlers.BaseHandler
  public void setRowData(BaseRowData baseRowData) {
    this.baseRowData = baseRowData;
    this.jTextFieldDirectory.setText(baseRowData.getText());
  }

  @Override // com.tvec.utility.configuration.editor.edit_panel.cell_handlers.BaseHandler
  public String getValue() {
    return this.jTextFieldDirectory.getText();
  }

  private void jbInit() throws Exception {
    this.jTextFieldDirectory.setText("jTextField1");
    this.jButtonSelectDirectory.setToolTipText("Click to open directory chooser");
    this.jButtonSelectDirectory.setText("...");
    this.jButtonSelectDirectory.addActionListener(
        new DirectoryEditor_jButtonSelectDirectory_actionAdapter(this));
    setLayout(this.borderLayout1);
    add(this.jTextFieldDirectory, "Center");
    add(this.jButtonSelectDirectory, "East");
  }

  public void jButtonSelectDirectory_actionPerformed(ActionEvent e) {
    JFileChooser fileChooser = new JFileChooser(this.baseRowData.getText());
    fileChooser.setDialogTitle("Select Directory");
    fileChooser.setApproveButtonText("Select");
    fileChooser.setFileSelectionMode(1);
    if (fileChooser.showOpenDialog(this) == 0) {
      File selectedFile = fileChooser.getSelectedFile();
      this.jTextFieldDirectory.setText(selectedFile.getAbsolutePath());
    }
  }
}
