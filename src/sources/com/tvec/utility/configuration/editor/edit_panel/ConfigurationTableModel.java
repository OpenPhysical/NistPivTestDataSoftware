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
import com.tvec.utility.configuration.ConfigurationEntry;
import com.tvec.utility.configuration.ConfigurationGroup;
import com.tvec.utility.configuration.FormatEntry;
import com.tvec.utility.configuration.FormatGroup;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

/* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/configuration/editor/edit_panel/ConfigurationTableModel.class */
public class ConfigurationTableModel extends AbstractTableModel {
  private static final long serialVersionUID = 1;
  public static final String COL_NAME_TEXT = "Name";
  public static final String COL_TYPE_TEXT = "Type";
  public static final String COL_VALUE_TEXT = "Value";
  public static final int COL_NAME_ID = 0;
  public static final int COL_TYPE_ID = 1;
  public static final int COL_VALUE_ID = 2;
  protected Configuration configuration;
  protected ConfigurationGroup configurationGroup;
  protected FormatGroup formatGroup;
  private boolean tableChanged;
  private final String[] columnNames;
  private final Vector<BaseRowData> rowData;

  public ConfigurationTableModel() {
    this.formatGroup = new FormatGroup();
    this.tableChanged = false;
    this.columnNames = new String[] {COL_NAME_TEXT, COL_TYPE_TEXT, COL_VALUE_TEXT};
    this.rowData = new Vector<>();
  }

  public ConfigurationTableModel(
      Configuration configuration, FormatGroup formatGroup, ConfigurationGroup configurationGroup) {
    this();
    this.configuration = configuration;
    this.configurationGroup = configurationGroup;
    this.formatGroup = formatGroup;
    FormatEntry[] formatArray = formatGroup.getEntryArray();
    for (FormatEntry formatEntry : formatArray) {
      ConfigurationEntry configEntry = configurationGroup.getEntry(formatEntry.getName());
      BaseRowData brd = new BaseRowData(configuration, configEntry, formatEntry);
      this.rowData.add(brd);
    }
  }

  public boolean tableChanged() {
    return this.tableChanged;
  }

  public void setTableChanged(boolean tableChanged) {
    this.tableChanged = tableChanged;
    fireTableDataChanged();
  }

  public int getColumnCount() {
    return this.columnNames.length;
  }

  public int getRowCount() {
    return this.rowData.size();
  }

  public int getColumnID(String colName) {
    int out = -1;
    int i = 0;
    while (true) {
      if (i >= this.columnNames.length) {
        break;
      }
      if (!this.columnNames[i].equals(colName)) {
        i++;
      } else {
        out = i;
        break;
      }
    }
    return out;
  }

  public String getColumnName(int col) {
    return this.columnNames[col];
  }

  public String getKeyName(int row) {
    BaseRowData brd = this.rowData.elementAt(row);
    FormatEntry formatEntry = brd.getFormatEntry();
    return formatEntry.getName();
  }

  public Object getValueAt(int row, int col) {
    BaseRowData brd = this.rowData.elementAt(row);
    FormatEntry formatEntry = brd.getFormatEntry();
    Object out = null;
    switch (col) {
      case 0:
        out = formatEntry.getDisplayName();
        if (out == null || out.equals("")) {
          out = formatEntry.getName();
          break;
        }
        break;
      case 1:
        out = formatEntry.getType();
        if (out.equals("DropdownEdit")) {
          out = "string";
          break;
        }
        break;
      case 2:
        out = brd;
        break;
    }
    return out;
  }

  public Class<?> getColumnClass(int c) {
    return getValueAt(0, c).getClass();
  }

  public boolean isCellEditable(int row, int col) {
    boolean out = col == 2;
    return out;
  }

  public void setValueAt(Object value, int row, int col) {
    boolean valueSet = false;
    switch (col) {
      case 2:
        if (value instanceof String) {
          BaseRowData brd = this.rowData.elementAt(row);
          ConfigurationEntry configEntry = brd.getConfigurationEntry();
          if (!configEntry.getValueString().equals(value)) {
            configEntry.setValue((String) value);
            valueSet = true;
            break;
          }
        }
        break;
    }
    if (valueSet) {
      setTableChanged(true);
      fireTableRowsUpdated(row, row);
    }
  }
}
