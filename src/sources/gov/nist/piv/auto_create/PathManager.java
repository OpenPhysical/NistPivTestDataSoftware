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

package gov.nist.piv.auto_create;

import gov.nist.piv.data.CCC;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/* loaded from: JPIV Test Data Generator.jar:gov/nist/piv/auto_create/PathManager.class */
public class PathManager extends DataManager {
  private static final long serialVersionUID = 1;
  private JList<String> jListAvailable;
  private DefaultListModel<String> listModel;
  private JLabel jLabelHeader;
  private JLabel jLabelAdd;
  private JTextField jTextFieldNew;
  private JPanel jPanelAdd;
  private JButton jButtonAdd;
  private JLabel jLabelAvailable;
  private JScrollPane jScrollPaneList;
  private JPanel jPanelAvailable;
  private JButton jButtonDelete;
  public static String ATTRIBUTE_NAME = "name";
  public static String ATTRIBUTE_HEADER = CertificateManager.ATTRIBUTE_HEADER;
  public static String TAG_DESCRIPTION = "description";
  public static String TAG_VALUES = "values";
  public static String TAG_VALUE = "value";
  private JLabel jLabelDescription;
  private JPanel jPanelDescription;
  private boolean removeWhiteSpace;
  private JLabel jLabelPlaceHolder;
  private JButton jButtonChoose;

  public PathManager(Node node) throws DOMException {
    this();
    loadValues(node);
  }

  @Override // gov.nist.piv.auto_create.DataManager
  public void loadValues(Node node) throws DOMException {
    Vector<String> values = new Vector<>();
    if (node == null || !node.getNodeName().equals(TAG_DATA_MANAGER)) {
      throw new RuntimeException("PathManager: PathManager not created with valid node");
    }
    HashMap<String, String> hm = XMLUtils.getAttributes(node);
    String dmType = hm.get(ATTRIBUTE_TYPE);
    if (!dmType.equals(PathManager.class.getName())) {
      throw new RuntimeException("PathManager: PathManager not created with valid node");
    }
    String name = hm.get(ATTRIBUTE_HEADER);
    setHeader(name);
    Node firstChild = node.getFirstChild();
    while (true) {
      Node child = firstChild;
      if (child != null) {
        String type = child.getNodeName();
        if (type.equals(TAG_DESCRIPTION)) {
          setDescription(child.getTextContent());
        } else if (type.equals(TAG_VALUES)) {
          Node firstChild2 = child.getFirstChild();
          while (true) {
            Node value = firstChild2;
            if (value != null) {
              if (value.getNodeName().equals(TAG_VALUE)) {
                values.add(value.getTextContent());
              }
              firstChild2 = value.getNextSibling();
            } else {
              setAvailable(values.toArray(new String[0]));
              return;
            }
          }
        }
        firstChild = child.getNextSibling();
      } else {
        return;
      }
    }
  }

  @Override // gov.nist.piv.auto_create.DataManager
  public void addElement(Document document, Element element, int level) throws DOMException {
    String baseIndent = "";
    for (int i = 0; i < level; i++) {
      baseIndent = baseIndent + "\t";
    }
    Element root = document.createElement(TAG_DATA_MANAGER);
    element.appendChild(root);
    root.setAttribute(ATTRIBUTE_TYPE, PathManager.class.getName());
    root.setAttribute(ATTRIBUTE_HEADER, getHeader());
    root.appendChild(document.createTextNode("\n"));
    Element description = document.createElement(TAG_DESCRIPTION);
    root.appendChild(document.createTextNode(baseIndent + "\t"));
    root.appendChild(description);
    description.setTextContent(getDescription());
    root.appendChild(document.createTextNode("\n"));
    Element values = document.createElement(TAG_VALUES);
    root.appendChild(document.createTextNode(baseIndent + "\t"));
    root.appendChild(values);
    values.appendChild(document.createTextNode("\n"));
    String[] available = getAvailable();
    for (String obj : available) {
      values.appendChild(document.createTextNode(baseIndent + "\t\t"));
      Element value = document.createElement(TAG_VALUE);
      value.setTextContent(obj);
      values.appendChild(value);
      values.appendChild(document.createTextNode("\n"));
    }
    values.appendChild(document.createTextNode(baseIndent + "\t"));
    root.appendChild(document.createTextNode("\n"));
    root.appendChild(document.createTextNode(baseIndent));
  }

  public PathManager(String name) {
    this();
    setHeader(name);
  }

  public PathManager() {
    this.jListAvailable = null;
    this.jLabelHeader = null;
    this.jLabelAdd = null;
    this.jTextFieldNew = null;
    this.jPanelAdd = null;
    this.jButtonAdd = null;
    this.jLabelAvailable = null;
    this.jScrollPaneList = null;
    this.jPanelAvailable = null;
    this.jButtonDelete = null;
    this.jLabelDescription = null;
    this.jPanelDescription = null;
    this.jLabelPlaceHolder = null;
    this.jButtonChoose = null;
    initialize();
  }

  public void setHeader(String header) {
    this.jLabelHeader.setText(header);
  }

  public String getHeader() {
    return this.jLabelHeader.getText();
  }

  public void setRemoveWhiteSpace(boolean removeWhiteSpace) {
    this.removeWhiteSpace = removeWhiteSpace;
  }

  public boolean getRemoveWhiteSpace() {
    return this.removeWhiteSpace;
  }

  public void setAvailable(String[] list) {
    getJListAvailable(); // Ensure list is initialized
    for (String obj : list) {
      this.listModel.addElement(obj);
    }
  }

  public String[] getAvailable() {
    getJListAvailable(); // Ensure list is initialized
    Object[] array = this.listModel.toArray();
    String[] result = new String[array.length];
    System.arraycopy(array, 0, result, 0, array.length);
    return result;
  }

  private void initialize() {
    GridBagConstraints gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    this.jLabelPlaceHolder = new JLabel();
    this.jLabelPlaceHolder.setText("place holder");
    this.jLabelPlaceHolder.setVisible(false);
    GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
    gridBagConstraints31.gridx = 1;
    gridBagConstraints31.anchor = 17;
    gridBagConstraints31.gridy = 2;
    this.jLabelDescription = new JLabel();
    this.jLabelDescription.setText("");
    GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
    gridBagConstraints11.gridx = 1;
    gridBagConstraints11.anchor = 17;
    gridBagConstraints11.gridy = 3;
    GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
    gridBagConstraints5.gridx = 0;
    gridBagConstraints5.anchor = 11;
    gridBagConstraints5.gridy = 3;
    GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
    gridBagConstraints4.gridy = 1;
    gridBagConstraints4.anchor = 17;
    gridBagConstraints4.gridx = 1;
    GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
    gridBagConstraints3.gridx = 0;
    gridBagConstraints3.gridy = 1;
    GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
    gridBagConstraints1.gridx = 0;
    gridBagConstraints1.anchor = 17;
    gridBagConstraints1.gridwidth = 2;
    gridBagConstraints1.gridy = 0;
    this.jLabelAdd = new JLabel();
    this.jLabelAdd.setText("Add New:");
    this.jLabelHeader = new JLabel();
    this.jLabelHeader.setText("Header");
    this.jLabelHeader.setForeground(Color.blue);
    this.jLabelHeader.setFont(new Font("Dialog", 1, 14));
    this.jLabelAvailable = new JLabel();
    this.jLabelAvailable.setText("Available:");
    setLayout(new GridBagLayout());
    setBounds(new Rectangle(0, 0, 394, CCC.EXTENDED_APP_CARD_URL_TAG));
    add(this.jLabelHeader, gridBagConstraints1);
    add(this.jLabelAdd, gridBagConstraints3);
    add(getJPanelAdd(), gridBagConstraints4);
    add(this.jLabelAvailable, gridBagConstraints5);
    add(getJPanelAvailable(), gridBagConstraints11);
    add(getJPanelDescription(), gridBagConstraints31);
    add(this.jLabelPlaceHolder, gridBagConstraints);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public JList getJListAvailable() {
    if (this.jListAvailable == null) {
      this.listModel = new DefaultListModel<String>();
      this.jListAvailable = new JList<String>(this.listModel);
    }
    return this.jListAvailable;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public JTextField getJTextFieldNew() {
    if (this.jTextFieldNew == null) {
      this.jTextFieldNew = new JTextField();
      this.jTextFieldNew.setColumns(20);
      this.jTextFieldNew.setText("");
    }
    return this.jTextFieldNew;
  }

  public void setDescription(String description) {
    if (description == null) {
      description = "";
    }
    this.jLabelDescription.setText(description);
  }

  public String getDescription() {
    return this.jLabelDescription.getText();
  }

  private JPanel getJPanelAdd() {
    if (this.jPanelAdd == null) {
      FlowLayout flowLayout = new FlowLayout();
      flowLayout.setAlignment(0);
      this.jPanelAdd = new JPanel();
      this.jPanelAdd.setLayout(flowLayout);
      this.jPanelAdd.add(getJTextFieldNew(), null);
      this.jPanelAdd.add(getJButtonChoose(), null);
      this.jPanelAdd.add(getJButtonAdd(), null);
    }
    return this.jPanelAdd;
  }

  private JButton getJButtonAdd() {
    if (this.jButtonAdd == null) {
      this.jButtonAdd = new JButton();
      this.jButtonAdd.setText("Add");
      this.jButtonAdd.addActionListener(
          new ActionListener() { // from class: gov.nist.piv.auto_create.PathManager.1
            public void actionPerformed(ActionEvent e) {
              PathManager.this.getJListAvailable();
              if (!PathManager.this.listModel.contains(PathManager.this.jTextFieldNew.getText())) {
                String value = PathManager.this.jTextFieldNew.getText();
                if (PathManager.this.getRemoveWhiteSpace()) {
                  value = value.replaceAll(" ", "");
                }
                listModel.addElement(value);
              }
            }
          });
    }
    return this.jButtonAdd;
  }

  private JScrollPane getJScrollPaneList() {
    if (this.jScrollPaneList == null) {
      this.jScrollPaneList = new JScrollPane();
      this.jScrollPaneList.setPreferredSize(new Dimension(200, 150));
      this.jScrollPaneList.setViewportView(getJListAvailable());
    }
    return this.jScrollPaneList;
  }

  private JPanel getJPanelAvailable() {
    if (this.jPanelAvailable == null) {
      this.jPanelAvailable = new JPanel();
      this.jPanelAvailable.setLayout(new FlowLayout());
      this.jPanelAvailable.add(getJScrollPaneList(), null);
      this.jPanelAvailable.add(getJButtonDelete(), null);
    }
    return this.jPanelAvailable;
  }

  private JButton getJButtonDelete() {
    if (this.jButtonDelete == null) {
      this.jButtonDelete = new JButton();
      this.jButtonDelete.setText("Delete Selected");
      this.jButtonDelete.addActionListener(
          new ActionListener() { // from class: gov.nist.piv.auto_create.PathManager.2
            public void actionPerformed(ActionEvent e) {
              JList list = PathManager.this.getJListAvailable();
              int[] selectedItems = list.getSelectedIndices();
              for (int i = selectedItems.length - 1; i >= 0; i--) {
                PathManager.this.listModel.remove(selectedItems[i]);
              }
            }
          });
    }
    return this.jButtonDelete;
  }

  private JPanel getJPanelDescription() {
    if (this.jPanelDescription == null) {
      this.jPanelDescription = new JPanel();
      this.jPanelDescription.setLayout(new FlowLayout());
      this.jPanelDescription.add(this.jLabelDescription, null);
    }
    return this.jPanelDescription;
  }

  private JButton getJButtonChoose() {
    if (this.jButtonChoose == null) {
      this.jButtonChoose = new JButton();
      this.jButtonChoose.setText("...");
      this.jButtonChoose.addActionListener(
          new ActionListener() { // from class: gov.nist.piv.auto_create.PathManager.3
            public void actionPerformed(ActionEvent e) {
              String curDir = System.getProperty("user.dir");
              JFileChooser fileChooser = new JFileChooser(curDir);
              if (fileChooser.showOpenDialog(PathManager.this) == 0) {
                PathManager.this
                    .getJTextFieldNew()
                    .setText(fileChooser.getSelectedFile().getAbsolutePath());
              }
            }
          });
    }
    return this.jButtonChoose;
  }
}
