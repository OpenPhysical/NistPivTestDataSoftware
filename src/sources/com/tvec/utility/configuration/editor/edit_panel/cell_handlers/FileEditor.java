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

/* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/configuration/editor/edit_panel/cell_handlers/FileEditor.class */
public class FileEditor extends JPanel implements BaseHandler {
  private static final long serialVersionUID = 1;
  private BaseRowData baseRowData;
  JTextField jTextFieldFile = new JTextField();
  JButton jButtonSelectFile = new JButton();
  BorderLayout borderLayout1 = new BorderLayout();

  public FileEditor() {
    try {
      jbInit();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  @Override // com.tvec.utility.configuration.editor.edit_panel.cell_handlers.BaseHandler
  public void setRowData(BaseRowData baseRowData) {
    this.baseRowData = baseRowData;
    this.jTextFieldFile.setText(baseRowData.getText());
  }

  @Override // com.tvec.utility.configuration.editor.edit_panel.cell_handlers.BaseHandler
  public String getValue() {
    return this.jTextFieldFile.getText();
  }

  private void jbInit() throws Exception {
    this.jTextFieldFile.setText("jTextField1");
    this.jButtonSelectFile.setToolTipText("Click to open file chooser");
    this.jButtonSelectFile.setText("...");
    this.jButtonSelectFile.addActionListener(new FileEditor_jButtonSelectFile_actionAdapter(this));
    setLayout(this.borderLayout1);
    add(this.jTextFieldFile, "Center");
    add(this.jButtonSelectFile, "East");
  }

  public void jButtonSelectFile_actionPerformed(ActionEvent e) {
    JFileChooser fileChooser = new JFileChooser(this.baseRowData.getText());
    fileChooser.setDialogTitle("Select Directory");
    fileChooser.setApproveButtonText("Select");
    fileChooser.setFileSelectionMode(0);
    if (fileChooser.showOpenDialog(this) == 0) {
      File selectedFile = fileChooser.getSelectedFile();
      this.jTextFieldFile.setText(selectedFile.getAbsolutePath());
    }
  }
}
