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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/configuration/editor/edit_panel/cell_handlers/DialogKeyCryptographicMechanismListEditor.class */
public class DialogKeyCryptographicMechanismListEditor extends JDialog {
  private static final long serialVersionUID = 1;
  public static final int OK_OPTION = 0;
  public static final int CANCEL_OPTION = 1;
  private static final HashMap<Integer, AlgorithmInfo> cryptographicMechanisms = new HashMap<>();
  private static final HashMap<Integer, KeyInfo> keys = new HashMap<>();
  private int selectedOption = 1;
  private final int COLUMN_ID_ROW = 0;
  private final int COLUMN_ID_KEY = 1;
  private final int COLUMN_ID_CRYPTOGRAPHIC_MECHANISM = 2;
  JPanel jPanelButtons = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JTable jTableKeyAlgorithm = new JTable();
  JButton jButtonOK = new JButton();
  JButton jButtonCancel = new JButton();
  JScrollPane jScrollPaneList = new JScrollPane();
  JPanel jPanelEditButtons = new JPanel();
  JButton jButtonAddRow = new JButton();
  JButton jButtonDeleteRow = new JButton();
  JPanel jPanelInstructions = new JPanel();
  JLabel jLabelInstructions = new JLabel();
  FlowLayout flowLayout2 = new FlowLayout();
  FlowLayout flowLayout1 = new FlowLayout();

  static {
    cryptographicMechanisms.put(Integer.valueOf(5), new AlgorithmInfo(5, "0x05, RSA, 3072 bits"));
    cryptographicMechanisms.put(Integer.valueOf(6), new AlgorithmInfo(6, "0x06, RSA, 1024 bits"));
    cryptographicMechanisms.put(Integer.valueOf(7), new AlgorithmInfo(7, "0x07, RSA, 2048 bits"));
    cryptographicMechanisms.put(
        Integer.valueOf(14), new AlgorithmInfo(14, "0x0e, ECC: Curve P-224, 224 bits"));
    cryptographicMechanisms.put(
        Integer.valueOf(15), new AlgorithmInfo(15, "0x0f, ECC: Curve K-233, 233 bits"));
    cryptographicMechanisms.put(
        Integer.valueOf(16), new AlgorithmInfo(16, "0x10, ECC: Curve B-233, 233 bits"));
    cryptographicMechanisms.put(
        Integer.valueOf(17), new AlgorithmInfo(17, "0x11, ECC: Curve P-256, 256 bits"));
    cryptographicMechanisms.put(
        Integer.valueOf(18), new AlgorithmInfo(18, "0x12, ECC: Curve K-283, 283 bits"));
    cryptographicMechanisms.put(
        Integer.valueOf(19), new AlgorithmInfo(19, "0x13, ECC: Curve B-283, 283 bits"));
    keys.put(
        Integer.valueOf(154),
        new KeyInfo(
            154, "0x9A, PIV Authentication Key", new int[] {5, 6, 7, 14, 15, 16, 17, 18, 19}));
    keys.put(
        Integer.valueOf(156),
        new KeyInfo(
            156,
            "0x9C, PIV Card Application Digital Signature Key",
            new int[] {5, 6, 7, 14, 15, 16, 17, 18, 19}));
    keys.put(
        Integer.valueOf(157),
        new KeyInfo(
            157,
            "0x9D, PIV Card Application Key Management Key",
            new int[] {5, 6, 7, 14, 15, 16, 17, 18, 19}));
    keys.put(
        Integer.valueOf(158),
        new KeyInfo(
            158, "0x9E, PIV Card Authentication Key", new int[] {5, 6, 7, 14, 15, 16, 17, 18, 19}));
  }

  /* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/configuration/editor/edit_panel/cell_handlers/DialogKeyCryptographicMechanismListEditor$AlgorithmInfo.class */
  static class AlgorithmInfo {
    int algorithm;
    String name;

    AlgorithmInfo(int pAlgorithm, String pName) {
      this.algorithm = pAlgorithm;
      this.name = pName;
    }

    public int getAlgorithm() {
      return this.algorithm;
    }

    public String getAlgorithmHexString() {
      String out = "";
      if (this.algorithm < 16) {
        out = out + "0";
      }
      return out + Integer.toHexString(this.algorithm);
    }

    public String getName() {
      return this.name;
    }
  }

  /* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/configuration/editor/edit_panel/cell_handlers/DialogKeyCryptographicMechanismListEditor$KeyInfo.class */
  static class KeyInfo {
    int key;
    String name;
    int[] validAlgorithms;

    public KeyInfo(int pKey, String pName, int[] pValidAlgorithms) {
      this.validAlgorithms = new int[0];
      this.key = pKey;
      this.name = pName;
      if (pValidAlgorithms != null) {
        this.validAlgorithms = pValidAlgorithms;
      }
    }

    public int getKey() {
      return this.key;
    }

    public String getKeyHexString() {
      String out = "";
      if (this.key < 16) {
        out = out + "0";
      }
      return out + Integer.toHexString(this.key);
    }

    public String getName() {
      return this.name;
    }

    public int[] getValidAlgorithms() {
      return this.validAlgorithms;
    }
  }

  public DialogKeyCryptographicMechanismListEditor(String[] currentSelections) {
    try {
      jbInit();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    this.jTableKeyAlgorithm.setModel(new MyTableModel(currentSelections));
    TableColumn rowColumn = this.jTableKeyAlgorithm.getColumnModel().getColumn(this.COLUMN_ID_ROW);
    rowColumn.setPreferredWidth(30);
    rowColumn.setMaxWidth(30);
    MyCellRenderer myCellRenderer = new MyCellRenderer();
    TableColumn keyColumn = this.jTableKeyAlgorithm.getColumnModel().getColumn(this.COLUMN_ID_KEY);
    keyColumn.setCellEditor(new MyKeyCellEditor());
    keyColumn.setCellRenderer(myCellRenderer);
    TableColumn algorithmColumn =
        this.jTableKeyAlgorithm.getColumnModel().getColumn(this.COLUMN_ID_CRYPTOGRAPHIC_MECHANISM);
    algorithmColumn.setCellEditor(new MyAlgorithmCellEditor());
    algorithmColumn.setCellRenderer(myCellRenderer);
  }

  /* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/configuration/editor/edit_panel/cell_handlers/DialogKeyCryptographicMechanismListEditor$MyCellRenderer.class */
  static class MyCellRenderer extends DefaultTableCellRenderer {
    private static final long serialVersionUID = 1;

    public void setValue(Object value) {
      if (value instanceof KeyInfo) {
        KeyInfo keyInfo = (KeyInfo) value;
        setText(keyInfo.getName());
      } else if (value instanceof AlgorithmInfo) {
        AlgorithmInfo algorithmInfo = (AlgorithmInfo) value;
        setText(algorithmInfo.getName());
      } else {
        setText((String) value);
      }
    }
  }

  /* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/configuration/editor/edit_panel/cell_handlers/DialogKeyCryptographicMechanismListEditor$MyKeyCellEditor.class */
  private class MyKeyCellEditor extends AbstractCellEditor implements TableCellEditor {
    private static final long serialVersionUID = 1;
    JComboBox<String> comboBox = new JComboBox<>();

    public MyKeyCellEditor() {}

    public Object getCellEditorValue() {
      return this.comboBox.getSelectedItem();
    }

    public Component getTableCellEditorComponent(
        JTable table, Object value, boolean isSelected, int row, int column) {
      KeyInfo currentKeyInfo = (KeyInfo) value;
      KeyInfo[] keyInfoArray =
          DialogKeyCryptographicMechanismListEditor.keys.values().toArray(new KeyInfo[0]);
      String[] keyNames = new String[keyInfoArray.length];
      for (int i = 0; i < keyInfoArray.length; i++) {
        keyNames[i] = keyInfoArray[i].getName();
      }
      List<String> list = Arrays.asList(keyNames);
      Collections.sort(list);
      this.comboBox = new JComboBox<>(list.toArray(new String[0]));
      this.comboBox.setSelectedItem(currentKeyInfo.getName());
      return this.comboBox;
    }
  }

  /* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/configuration/editor/edit_panel/cell_handlers/DialogKeyCryptographicMechanismListEditor$MyAlgorithmCellEditor.class */
  private class MyAlgorithmCellEditor extends AbstractCellEditor implements TableCellEditor {
    private static final long serialVersionUID = 1;
    JComboBox<String> comboBox = new JComboBox<>();

    public MyAlgorithmCellEditor() {}

    public Object getCellEditorValue() {
      return this.comboBox.getSelectedItem();
    }

    public Component getTableCellEditorComponent(
        JTable table, Object value, boolean isSelected, int row, int column) {
      AlgorithmInfo currentAlgorithmInfo = (AlgorithmInfo) value;
      TableModel tableModel = table.getModel();
      KeyInfo keyInfo =
          (KeyInfo)
              tableModel.getValueAt(
                  row, DialogKeyCryptographicMechanismListEditor.this.COLUMN_ID_KEY);
      int[] validAlgorithms = keyInfo.getValidAlgorithms();
      Vector<String> algorithmList = new Vector<>(validAlgorithms.length);
      boolean foundValue = false;
      for (int i : validAlgorithms) {
        AlgorithmInfo algorithmInfo =
            DialogKeyCryptographicMechanismListEditor.cryptographicMechanisms.get(
                Integer.valueOf(i));
        String algorithmName = algorithmInfo.getName();
        algorithmList.add(algorithmName);
        if (!foundValue) {
          foundValue = algorithmName.equals(currentAlgorithmInfo.getName());
        }
      }
      List<String> list = Arrays.asList(algorithmList.toArray(new String[0]));
      Collections.sort(list);
      this.comboBox = new JComboBox<>(list.toArray(new String[0]));
      if (foundValue) {
        this.comboBox.setSelectedItem(value);
      } else {
        this.comboBox.setSelectedIndex(0);
      }
      return this.comboBox;
    }
  }

  public String getStringList() {
    MyTableModel tableModel = (MyTableModel) this.jTableKeyAlgorithm.getModel();
    return tableModel.getStringList();
  }

  /* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/configuration/editor/edit_panel/cell_handlers/DialogKeyCryptographicMechanismListEditor$MyTableModel.class */
  private class MyTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 1;
    private final String[] COLUMN_HEADERS = {"#", "Key", "Cryptographic Mechanism"};
    Vector<KeyMechanismEntry> rowData;

    public MyTableModel(String[] data) {
      this.rowData = new Vector<>();
      this.rowData = new Vector<>(data.length);
      for (String keyAlgorithmString : data) {
        String key = keyAlgorithmString.substring(0, 2);
        String algorithm = keyAlgorithmString.substring(2);
        this.rowData.add(new KeyMechanismEntry(this, key, algorithm));
      }
    }

    public int getRowCount() {
      return this.rowData.size();
    }

    public String getColumnName(int col) {
      return this.COLUMN_HEADERS[col];
    }

    public int getColumnCount() {
      return this.COLUMN_HEADERS.length;
    }

    public Object getValueAt(int row, int col) {
      Object out = "unknown";
      if (col == DialogKeyCryptographicMechanismListEditor.this.COLUMN_ID_ROW) {
        out = String.valueOf(row + 1);
      } else if (col == DialogKeyCryptographicMechanismListEditor.this.COLUMN_ID_KEY) {
        KeyMechanismEntry kaEntry = this.rowData.elementAt(row);
        out = kaEntry.getKeyInfo();
      } else if (col
          == DialogKeyCryptographicMechanismListEditor.this.COLUMN_ID_CRYPTOGRAPHIC_MECHANISM) {
        KeyMechanismEntry kaEntry2 = this.rowData.elementAt(row);
        out = kaEntry2.getAlgorithmInfo();
      }
      return out;
    }

    public boolean isCellEditable(int row, int col) {
      return col != DialogKeyCryptographicMechanismListEditor.this.COLUMN_ID_ROW;
    }

    public void setValueAt(Object value, int row, int col) {
      if (col == DialogKeyCryptographicMechanismListEditor.this.COLUMN_ID_KEY) {
        String newValue = (String) value;
        String hexString = newValue.substring(2, 4);
        KeyMechanismEntry kaEntry = this.rowData.elementAt(row);
        kaEntry.setKey(Integer.parseInt(hexString, 16));
        if (!kaEntry.isValidKeyAlgorithmCombination()) {
          kaEntry.setAlgorithm(kaEntry.getKeyInfo().getValidAlgorithms()[0]);
          fireTableRowsUpdated(row, row);
          return;
        } else {
          fireTableCellUpdated(row, col);
          return;
        }
      }
      if (col == DialogKeyCryptographicMechanismListEditor.this.COLUMN_ID_CRYPTOGRAPHIC_MECHANISM) {
        String newValue2 = (String) value;
        String hexString2 = newValue2.substring(2, 4);
        this.rowData.elementAt(row).setAlgorithm(Integer.parseInt(hexString2, 16));
        fireTableCellUpdated(row, col);
      }
    }

    public void deleteRow(int row) {
      this.rowData.removeElementAt(row);
      fireTableRowsDeleted(row, row);
    }

    public void addRow(int key, int algorithm) {
      this.rowData.add(new KeyMechanismEntry(key, algorithm));
      fireTableRowsInserted(this.rowData.size() - 1, this.rowData.size() - 1);
    }

    public String getStringList() {
      StringBuffer out = new StringBuffer(255);
      int rowCount = getRowCount();
      for (int row = 0; row < rowCount; row++) {
        KeyMechanismEntry kaEntry = this.rowData.elementAt(row);
        if (row > 0) {
          out.append(";");
        }
        out.append(kaEntry.toString());
      }
      return out.toString();
    }

    /* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/configuration/editor/edit_panel/cell_handlers/DialogKeyCryptographicMechanismListEditor$MyTableModel$KeyMechanismEntry.class */
    class KeyMechanismEntry {
      private KeyInfo keyInfo;
      private AlgorithmInfo algorithmInfo;

      public KeyMechanismEntry(MyTableModel myTableModel, String key, String algorithm) {
        this(Integer.parseInt(key, 16), Integer.parseInt(algorithm, 16));
      }

      public KeyMechanismEntry(int pKey, int pAlgorithm) {
        this.keyInfo = DialogKeyCryptographicMechanismListEditor.keys.get(Integer.valueOf(pKey));
        this.algorithmInfo =
            DialogKeyCryptographicMechanismListEditor.cryptographicMechanisms.get(
                Integer.valueOf(pAlgorithm));
      }

      public void setKey(int pKey) {
        this.keyInfo = DialogKeyCryptographicMechanismListEditor.keys.get(Integer.valueOf(pKey));
      }

      public KeyInfo getKeyInfo() {
        return this.keyInfo;
      }

      public void setAlgorithm(int pAlgorithm) {
        this.algorithmInfo =
            DialogKeyCryptographicMechanismListEditor.cryptographicMechanisms.get(
                Integer.valueOf(pAlgorithm));
      }

      public AlgorithmInfo getAlgorithmInfo() {
        return this.algorithmInfo;
      }

      public boolean isValidKeyAlgorithmCombination() {
        int[] validAlgorithms = getKeyInfo().getValidAlgorithms();
        boolean isValidKeyAlgorithm = false;
        int currentAlgorithm = getAlgorithmInfo().getAlgorithm();
        int i = 0;
        while (true) {
          if (i >= validAlgorithms.length) {
            break;
          }
          if (validAlgorithms[i] != currentAlgorithm) {
            i++;
          } else {
            isValidKeyAlgorithm = true;
            break;
          }
        }
        return isValidKeyAlgorithm;
      }

      public String toString() {
        return getKeyInfo().getKeyHexString() + getAlgorithmInfo().getAlgorithmHexString();
      }
    }
  }

  private void jbInit() throws Exception {
    getContentPane().setLayout(this.borderLayout1);
    this.jButtonOK.setActionCommand("jButtonOK");
    this.jButtonOK.addActionListener(
        new DialogKeyCryptographicMechanismListEditor_jButtonOK_actionAdapter(this));
    this.jButtonCancel.setActionCommand("jButtonCancel");
    this.jButtonCancel.setText("Cancel");
    this.jButtonCancel.addActionListener(
        new DialogKeyCryptographicMechanismListEditor_jButtonCancel_actionAdapter(this));
    setModal(true);
    setResizable(true);
    setTitle("Key/Cryptographic Mechanism Selections");
    this.jButtonAddRow.setText("Add Row");
    this.jButtonAddRow.addActionListener(
        new DialogKeyCryptographicMechanismListEditor_jButtonAddRow_actionAdapter(this));
    this.jButtonDeleteRow.setToolTipText("");
    this.jButtonDeleteRow.setText("Delete Selected Row");
    this.jButtonDeleteRow.addActionListener(
        new DialogKeyCryptographicMechanismListEditor_jButtonDeleteRow_actionAdapter(this));
    this.jPanelEditButtons.setLayout(this.flowLayout1);
    this.jScrollPaneList.setMinimumSize(new Dimension(200, 22));
    this.jPanelEditButtons.setMinimumSize(new Dimension(22, 33));
    this.jPanelEditButtons.setPreferredSize(new Dimension(145, 33));
    this.jLabelInstructions.setPreferredSize(new Dimension(400, 45));
    this.jLabelInstructions.setToolTipText("");
    this.jLabelInstructions.setText(
        "<html>Click on a cell to change a selection.<br>Available cryptographic mechanisms will change to match key selection.</html>");
    this.jPanelInstructions.setLayout(this.flowLayout2);
    this.jPanelButtons.add(this.jButtonOK);
    this.jPanelButtons.add(this.jButtonCancel);
    this.jButtonOK.setText("OK");
    getContentPane().add(this.jPanelButtons, "South");
    getContentPane().add(this.jScrollPaneList, "Center");
    this.jScrollPaneList.getViewport().add(this.jTableKeyAlgorithm);
    getContentPane().add(this.jPanelEditButtons, "East");
    this.jPanelEditButtons.add(this.jButtonAddRow, null);
    this.jPanelEditButtons.add(this.jButtonDeleteRow, null);
    getContentPane().add(this.jPanelInstructions, "North");
    this.jPanelInstructions.add(this.jLabelInstructions);
    setSize(600, 400);
  }

  public int showDialog(Component parent) {
    setLocationRelativeTo(parent);
    setVisible(true);
    return this.selectedOption;
  }

  public void jButtonOK_actionPerformed(ActionEvent e) {
    stopCellEditing();
    this.selectedOption = 0;
    setVisible(false);
  }

  public void jButtonCancel_actionPerformed(ActionEvent e) {
    stopCellEditing();
    this.selectedOption = 1;
    setVisible(false);
  }

  public void jButtonAddRow_actionPerformed(ActionEvent e) {
    stopCellEditing();
    MyTableModel tableModel = (MyTableModel) this.jTableKeyAlgorithm.getModel();
    tableModel.addRow(154, 5);
  }

  public void jButtonDeleteRow_actionPerformed(ActionEvent e) {
    stopCellEditing();
    int[] selectedRows = this.jTableKeyAlgorithm.getSelectedRows();
    for (int index = selectedRows.length - 1; index > -1; index--) {
      MyTableModel tableModel = (MyTableModel) this.jTableKeyAlgorithm.getModel();
      tableModel.deleteRow(selectedRows[index]);
    }
  }

  public boolean stopCellEditing() {
    try {
      int column = this.jTableKeyAlgorithm.getEditingColumn();
      if (column > -1) {
        TableCellEditor cellEditor =
            this.jTableKeyAlgorithm.getColumnModel().getColumn(column).getCellEditor();
        if (cellEditor == null) {
          cellEditor =
              this.jTableKeyAlgorithm.getDefaultEditor(
                  this.jTableKeyAlgorithm.getColumnClass(column));
        }
        if (cellEditor != null) {
          cellEditor.stopCellEditing();
          return true;
        }
        return true;
      }
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
