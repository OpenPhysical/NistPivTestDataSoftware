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

import com.tvec.utility.configuration.FormatEntryOption;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.Vector;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/configuration/editor/edit_panel/cell_handlers/DialogMultiSelectListEditor.class */
public class DialogMultiSelectListEditor extends JDialog {
  private static final long serialVersionUID = 1;
  public static final int OK_OPTION = 0;
  public static final int CANCEL_OPTION = 1;
  private int selectedOption = 1;
  JPanel jPanelButtons = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JList<FormatEntryOption> jListOptions = new JList<>();
  JButton jButtonOK = new JButton();
  JButton jButtonCancel = new JButton();
  JScrollPane jScrollPaneList = new JScrollPane();

  public DialogMultiSelectListEditor(FormatEntryOption[] options, String[] currentSelections) {
    try {
      jbInit();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    DefaultListModel<FormatEntryOption> listModel = new DefaultListModel<>();
    Vector<Integer> selections = new Vector<>(currentSelections.length);
    for (int i = 0; options != null && i < options.length; i++) {
      listModel.addElement(options[i]);
      int selPos = 0;
      while (true) {
        if (selPos < currentSelections.length) {
          if (!options[i].getValue().equals(currentSelections[selPos])) {
            selPos++;
          } else {
            selections.add(Integer.valueOf(i));
            break;
          }
        }
      }
    }
    this.jListOptions.setModel(listModel);
    if (selections.size() > 0) {
      int[] indices = new int[selections.size()];
      for (int i2 = 0; i2 < selections.size(); i2++) {
        indices[i2] = selections.elementAt(i2).intValue();
      }
      this.jListOptions.setSelectedIndices(indices);
    }
  }

  /* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/configuration/editor/edit_panel/cell_handlers/DialogMultiSelectListEditor$MyListCellRenederer.class */
  private class MyListCellRenederer extends DefaultListCellRenderer {
    private static final long serialVersionUID = 1;

    public MyListCellRenederer() {}

    public Component getListCellRendererComponent(
        JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
      String displayString = ((FormatEntryOption) value).getDisplayName();
      Component out =
          super.getListCellRendererComponent(list, displayString, index, isSelected, cellHasFocus);
      return out;
    }
  }

  private void jbInit() throws Exception {
    getContentPane().setLayout(this.borderLayout1);
    this.jButtonOK.setActionCommand("jButtonOK");
    this.jButtonOK.addActionListener(new DialogMultiSelectListEditor_jButtonOK_actionAdapter(this));
    this.jButtonCancel.setActionCommand("jButtonCancel");
    this.jButtonCancel.setText("Cancel");
    this.jButtonCancel.addActionListener(
        new DialogMultiSelectListEditor_jButtonCancel_actionAdapter(this));
    this.jListOptions.setPreferredSize(new Dimension(0, 200));
    this.jListOptions.setCellRenderer(new MyListCellRenederer());
    setModal(true);
    setResizable(false);
    setTitle("Option Selections");
    this.jPanelButtons.add(this.jButtonOK);
    this.jPanelButtons.add(this.jButtonCancel);
    this.jScrollPaneList.getViewport().add(this.jListOptions);
    this.jButtonOK.setText("OK");
    getContentPane().add(this.jPanelButtons, "South");
    getContentPane().add(this.jScrollPaneList, "Center");
    setSize(400, 400);
  }

  public int showDialog(Component parent) {
    setLocationRelativeTo(parent);
    setVisible(true);
    return this.selectedOption;
  }

  public void jButtonOK_actionPerformed(ActionEvent e) {
    this.selectedOption = 0;
    setVisible(false);
  }

  public void jButtonCancel_actionPerformed(ActionEvent e) {
    this.selectedOption = 1;
    setVisible(false);
  }

  public String getStringList() {
    Object[] selectedOptions = this.jListOptions.getSelectedValuesList().toArray();
    StringBuffer out = new StringBuffer(255);
    for (int i = 0; i < selectedOptions.length; i++) {
      if (i > 0) {
        out.append(";");
      }
      out.append(((FormatEntryOption) selectedOptions[i]).getValue());
    }
    return out.toString();
  }
}
