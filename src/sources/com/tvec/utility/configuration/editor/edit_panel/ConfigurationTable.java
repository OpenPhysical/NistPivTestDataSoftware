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
import com.tvec.utility.configuration.FormatGroup;
import com.tvec.utility.configuration.editor.edit_panel.cell_handlers.BaseHandler;
import com.tvec.utility.configuration.editor.edit_panel.cell_handlers.ClassFactory;
import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/configuration/editor/edit_panel/ConfigurationTable.class */
public class ConfigurationTable extends JTable {
  private static final long serialVersionUID = 1;
  private Configuration configuration;
  private ConfigurationGroup configurationGroup;
  private FormatGroup formatGroup;

  public ConfigurationTable() {
    try {
      jbInit();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public void init(
      Configuration configuration, FormatGroup formatGroup, ConfigurationGroup configurationGroup) {
    this.configuration = configuration;
    this.formatGroup = formatGroup;
    this.configurationGroup = configurationGroup;
    ConfigurationTableModel ctm =
        new ConfigurationTableModel(configuration, formatGroup, configurationGroup);
    setModel(ctm);
  }

  public Configuration getConfiguration() {
    return this.configuration;
  }

  public ConfigurationGroup getConfigurationGroup() {
    return this.configurationGroup;
  }

  public FormatGroup getFormatGroup() {
    return this.formatGroup;
  }

  public void setChanged() {
    setChanged(true);
  }

  public void setChanged(boolean changed) {
    ConfigurationTableModel ctm = (ConfigurationTableModel) getModel();
    ctm.setTableChanged(changed);
  }

  private void jbInit() {
    setModel(new ConfigurationTableModel());
    setSelectionMode(0);
    setDefaultEditor(BaseRowData.class, new MyTableCellEditor());
    setDefaultRenderer(BaseRowData.class, new MyTableCellRenderer());
  }

  /* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/configuration/editor/edit_panel/ConfigurationTable$MyTableCellEditor.class */
  public class MyTableCellEditor extends AbstractCellEditor implements TableCellEditor {
    private static final long serialVersionUID = 1;
    Component component;
    BaseRowData bvt;

    public MyTableCellEditor() {}

    public Component getTableCellEditorComponent(
        JTable table, Object value, boolean isSelected, int rowIndex, int vColIndex) {
      this.bvt = (BaseRowData) value;
      this.component = ClassFactory.getComponent(this.bvt);
      return this.component;
    }

    public Object getCellEditorValue() {
      return ((BaseHandler) this.component).getValue();
    }
  }

  /* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/configuration/editor/edit_panel/ConfigurationTable$MyTableCellRenderer.class */
  class MyTableCellRenderer extends JLabel implements TableCellRenderer {
    private static final long serialVersionUID = 1;

    MyTableCellRenderer() {}

    public Component getTableCellRendererComponent(
        JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      if (value instanceof BaseRowData) {
        BaseRowData bvt = (BaseRowData) value;
        setText(bvt.getText());
      }
      return this;
    }
  }
}
