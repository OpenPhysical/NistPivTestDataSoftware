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

package com.tvec.utility.configuration.editor;

import com.tvec.utility.configuration.Configuration;
import com.tvec.utility.configuration.ConfigurationMessage;
import com.tvec.utility.configuration.ConfigurationMessageListener;
import com.tvec.utility.configuration.ConfigurationMessageSender;
import com.tvec.utility.configuration.editor.tree.BaseTreeNode;
import com.tvec.utility.configuration.editor.tree.ConfigurationGroupTreeNode;
import com.tvec.utility.configuration.editor.tree.ConfigurationTree;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.DOMException;

/* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/configuration/editor/Editor.class */
public class Editor extends JPanel implements ConfigurationMessageListener {
  private static final long serialVersionUID = 1;
  private Configuration configuration;
  JSplitPane jSplitPaneMain;
  ConfigurationTree jTreeConfiguration;
  BorderLayout borderLayoutMain;
  private final MyTreeSelectionListener treeListener;
  EmptyPanel emptyPanel;
  JScrollPane jScrollPaneTree;

  public static void main(String[] args) {
    Editor editor = null;
    try {
      Configuration configuration =
          new Configuration(
              "Y:/t_vec_specs/projects/smartcard/piv/testrunner/java_files/utility/PIV_APDU_contact_800_73_1_pcsc_reader.xml");
      editor = new Editor(configuration);
    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    }
    JFrame jf = new JFrame();
    jf.setDefaultCloseOperation(3);
    jf.setSize(640, 480);
    jf.setVisible(true);
    jf.getContentPane().add(editor);
    jf.validate();
  }

  public Editor() {
    this.configuration = new Configuration();
    this.jSplitPaneMain = new JSplitPane();
    this.jTreeConfiguration = new ConfigurationTree();
    this.borderLayoutMain = new BorderLayout();
    this.treeListener = new MyTreeSelectionListener(this, null);
    this.emptyPanel = new EmptyPanel();
    this.jScrollPaneTree = new JScrollPane();
    try {
      jbInit();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    this.jTreeConfiguration.addTreeSelectionListener(this.treeListener);
  }

  public Editor(Configuration configuration) {
    this();
    setConfiguration(configuration);
  }

  public void setConfiguration(Configuration configuration) {
    this.jTreeConfiguration.removeTreeSelectionListener(this.treeListener);
    if (this.configuration != null) {
      this.configuration.deleteConfigurationMessageListener(this);
    }
    this.configuration = configuration;
    this.jTreeConfiguration.setConfiguration(configuration);
    configuration.addConfigurationMessageListener(this);
    this.jTreeConfiguration.addTreeSelectionListener(this.treeListener);
  }

  private void jbInit() throws Exception {
    setLayout(this.borderLayoutMain);
    this.jSplitPaneMain.setRightComponent(this.emptyPanel);
    this.jSplitPaneMain.setLeftComponent(this.jScrollPaneTree);
    this.jSplitPaneMain.setResizeWeight(0.0d);
    this.jTreeConfiguration.setMaximumSize(new Dimension(0, 0));
    this.jTreeConfiguration.setPreferredSize(new Dimension(200, 300));
    this.jScrollPaneTree.setMaximumSize(new Dimension(0, 0));
    this.jScrollPaneTree.setMinimumSize(new Dimension(0, 0));
    this.jScrollPaneTree.setPreferredSize(new Dimension(200, 300));
    add(this.jSplitPaneMain, "Center");
    this.jSplitPaneMain.add(this.jScrollPaneTree, "left");
    this.jScrollPaneTree.getViewport().add(this.jTreeConfiguration);
  }

  @Override // com.tvec.utility.configuration.ConfigurationMessageListener
  public void handleMessage(ConfigurationMessageSender sender, ConfigurationMessage cm) {
    cm.getAction();
  }

  public void toFile(String fileName)
      throws TransformerException, ParserConfigurationException, DOMException, IOException {
    this.configuration.toFile(fileName);
  }

  public void focusLost() {
    Component rightComponent = this.jSplitPaneMain.getRightComponent();
    if (rightComponent instanceof EditPanelInterface) {
      EditPanelInterface epi = (EditPanelInterface) rightComponent;
      epi.focusLost();
    }
  }

  /* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/configuration/editor/Editor$MyTreeSelectionListener.class */
  private class MyTreeSelectionListener implements TreeSelectionListener {
    private MyTreeSelectionListener() {}

    /* synthetic */ MyTreeSelectionListener(
        Editor editor, MyTreeSelectionListener myTreeSelectionListener) {
      this();
    }

    public void valueChanged(TreeSelectionEvent e) {
      JTree theTree = (JTree) e.getSource();
      DefaultMutableTreeNode dmtNode =
          (DefaultMutableTreeNode) theTree.getLastSelectedPathComponent();
      if (dmtNode != null) {
        JComponent display = Editor.this.emptyPanel;
        BaseTreeNode node = (BaseTreeNode) dmtNode.getUserObject();
        switch (node.getNodeType()) {
          case 1:
            ConfigurationGroupTreeNode groupTreeNode = (ConfigurationGroupTreeNode) node;
            try {
              display = groupTreeNode.getDisplay();
            } catch (ClassNotFoundException ex) {
              ex.printStackTrace();
              display = Editor.this.emptyPanel;
            }
            break;
        }
        Component rightComponent = Editor.this.jSplitPaneMain.getRightComponent();
        if (rightComponent instanceof EditPanelInterface) {
          EditPanelInterface epi = (EditPanelInterface) rightComponent;
          epi.focusLost();
        }
        int dividerLoc = Editor.this.jSplitPaneMain.getDividerLocation();
        Editor.this.jSplitPaneMain.setRightComponent(display);
        Editor.this.jSplitPaneMain.setDividerLocation(dividerLoc);
      }
    }
  }
}
