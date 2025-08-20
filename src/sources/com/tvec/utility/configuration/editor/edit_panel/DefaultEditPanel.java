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

package com.tvec.utility.configuration.editor.edit_panel;

import com.tvec.utility.configuration.Configuration;
import com.tvec.utility.configuration.ConfigurationGroup;
import com.tvec.utility.configuration.FormatEntry;
import com.tvec.utility.configuration.FormatGroup;
import com.tvec.utility.configuration.editor.EditPanelInterface;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/configuration/editor/edit_panel/DefaultEditPanel.class */
public class DefaultEditPanel extends JPanel implements EditPanelInterface {
  private static final long serialVersionUID = 1;
  public Configuration configuration;
  public ConfigurationGroup configurationGroup;
  public FormatGroup formatGroup;
  JScrollPane jScrollPaneTable = new JScrollPane();
  ConfigurationTable jTableConfiguration = new ConfigurationTable();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanelHelp = new JPanel();
  JScrollPane jScrollPaneHelp = new JScrollPane();
  JLabel jLabelHelp = new JLabel();
  BorderLayout borderLayout2 = new BorderLayout();
  JEditorPane jEditorPaneHelp = new JEditorPane();

  public DefaultEditPanel() {
    try {
      jbInit();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    this.jEditorPaneHelp.setContentType("text/html");
  }

  @Override // com.tvec.utility.configuration.editor.EditPanelInterface
  public void init(
      Configuration config, FormatGroup formatGroup, ConfigurationGroup configurationGroup) {
    this.configuration = config;
    this.configurationGroup = configurationGroup;
    this.formatGroup = formatGroup;
    this.jTableConfiguration.init(this.configuration, formatGroup, configurationGroup);
  }

  @Override // com.tvec.utility.configuration.editor.EditPanelInterface
  public void focusLost() {
    if (this.jTableConfiguration.getCellEditor() != null) {
      this.jTableConfiguration.getCellEditor().stopCellEditing();
    }
  }

  private void jbInit() throws Exception {
    setLayout(this.borderLayout1);
    this.jLabelHelp.setText("Help:");
    this.jPanelHelp.setLayout(this.borderLayout2);
    this.jEditorPaneHelp.setText("jEditorPane1");
    this.jPanelHelp.setPreferredSize(new Dimension(400, 100));
    add(this.jPanelHelp, "South");
    this.jScrollPaneTable.getViewport().add(this.jTableConfiguration);
    add(this.jScrollPaneTable, "Center");
    this.jPanelHelp.add(this.jScrollPaneHelp, "Center");
    this.jScrollPaneHelp.getViewport().add(this.jEditorPaneHelp);
    this.jPanelHelp.add(this.jLabelHelp, "North");
    ListSelectionModel rowSM = this.jTableConfiguration.getSelectionModel();
    rowSM.addListSelectionListener(
        new ListSelectionListener() { // from class:
          // com.tvec.utility.configuration.editor.edit_panel.DefaultEditPanel.1
          public void valueChanged(ListSelectionEvent e) {
            if (e.getValueIsAdjusting()) {
              return;
            }
            ListSelectionModel lsm = (ListSelectionModel) e.getSource();
            if (lsm.isSelectionEmpty()) {
              DefaultEditPanel.this.jEditorPaneHelp.setText("");
              return;
            }
            int selectedRow = lsm.getMinSelectionIndex();
            String keyName =
                DefaultEditPanel.this.formatGroup.getEntryArray()[selectedRow].getName();
            FormatEntry formatNode = DefaultEditPanel.this.formatGroup.getEntry(keyName);
            DefaultEditPanel.this.jEditorPaneHelp.setText(formatNode.getHelp());
          }
        });
  }
}
