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

import com.tvec.utility.configuration.FormatEntry;
import com.tvec.utility.configuration.FormatEntryOption;
import com.tvec.utility.configuration.FormatEntryOptions;
import com.tvec.utility.configuration.editor.edit_panel.BaseRowData;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/configuration/editor/edit_panel/cell_handlers/MultiSelectListEditor.class */
public class MultiSelectListEditor extends JPanel implements BaseHandler {
  private static final long serialVersionUID = 1;
  private BaseRowData baseRowData;
  JButton jButtonEdit = new JButton();
  BorderLayout borderLayout1 = new BorderLayout();
  JTextField jTextFieldList = new JTextField();

  public MultiSelectListEditor() {
    try {
      jbInit();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  @Override // com.tvec.utility.configuration.editor.edit_panel.cell_handlers.BaseHandler
  public void setRowData(BaseRowData baseRowData) {
    this.baseRowData = baseRowData;
    this.jTextFieldList.setText(baseRowData.getText());
  }

  @Override // com.tvec.utility.configuration.editor.edit_panel.cell_handlers.BaseHandler
  public String getValue() {
    return this.jTextFieldList.getText();
  }

  private void jbInit() throws Exception {
    this.jButtonEdit.setMinimumSize(new Dimension(11, 20));
    this.jButtonEdit.setPreferredSize(new Dimension(30, 20));
    this.jButtonEdit.setToolTipText("Click to edit selections");
    this.jButtonEdit.setText("...");
    this.jButtonEdit.addActionListener(new MultiSelectListEditor_jButtonEdit_actionAdapter(this));
    setLayout(this.borderLayout1);
    this.jTextFieldList.setEditable(false);
    this.jTextFieldList.setText("");
    setBorder(null);
    setMaximumSize(new Dimension(32768, 32768));
    setMinimumSize(new Dimension(141, 20));
    setPreferredSize(new Dimension(141, 30));
    add(this.jTextFieldList, "Center");
    add(this.jButtonEdit, "East");
  }

  public void jButtonEdit_actionPerformed(ActionEvent e) {
    FormatEntry formatEntry = this.baseRowData.getFormatEntry();
    FormatEntryOptions options = formatEntry.getOptions();
    FormatEntryOption[] optionArray = options.getOptionArray();
    String[] currentSelections = getValue().split(";");
    DialogMultiSelectListEditor dialog =
        new DialogMultiSelectListEditor(optionArray, currentSelections);
    if (dialog.showDialog(this) == 0) {
      this.jTextFieldList.setText(dialog.getStringList());
    }
  }
}
