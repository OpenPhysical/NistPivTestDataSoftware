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

package com.tvec.utility.configuration.editor.tree;

import com.tvec.utility.JARUtils;
import com.tvec.utility.configuration.Configuration;
import com.tvec.utility.configuration.ConfigurationGroup;
import com.tvec.utility.configuration.ConfigurationMessage;
import com.tvec.utility.configuration.ConfigurationMessageListener;
import com.tvec.utility.configuration.ConfigurationMessageSender;
import com.tvec.utility.configuration.Format;
import com.tvec.utility.configuration.FormatGroup;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.InputStream;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/configuration/editor/tree/ConfigurationTree.class */
public class ConfigurationTree extends JTree implements ConfigurationMessageListener {
  private static final long serialVersionUID = 1;
  private Configuration configuration;

  public ConfigurationTree() {
    ToolTipManager.sharedInstance().registerComponent(this);
    setCellRenderer(new MyRenderer());
    addMouseListener(new MyMouseListener(this, null));
    setRootVisible(false);
    setShowsRootHandles(true);
    getModel().addTreeModelListener(new MyTreeModelListener());
  }

  public ConfigurationTree(Configuration configuration) {
    this();
    setConfiguration(configuration);
  }

  public void setConfiguration(Configuration configuration) {
    if (this.configuration != null) {
      this.configuration.deleteConfigurationMessageListener(this);
    }
    this.configuration = configuration;
    if (configuration.isInitialized()) {
      updateTree();
    } else {
      BaseTreeNode btn = new RootTreeNode(this, configuration);
      DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(btn, btn.allowsChildren());
      setModel(new DefaultTreeModel(rootNode, true));
    }
    configuration.addConfigurationMessageListener(this);
  }

  private void updateTree() {
    try {
      InputStream in = JARUtils.getInputStream(this.configuration.getFormatName());
      Format format = new Format(in);
      BaseTreeNode btn = new RootTreeNode(this, this.configuration);
      DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(btn, btn.allowsChildren());
      FormatGroup[] formatGroupArray = format.getGroupArray();
      addConfigurationGroupNodes(rootNode, formatGroupArray);
      setModel(new DefaultTreeModel(rootNode, true));
      DefaultMutableTreeNode root = (DefaultMutableTreeNode) getModel().getRoot();
      setSelectionPath(new TreePath(root));
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  private void addConfigurationGroupNodes(
      DefaultMutableTreeNode parent, FormatGroup[] formatGroupArray) {
    for (FormatGroup formatGroup : formatGroupArray) {
      if (formatGroup.isVisible()) {
        ConfigurationGroup configGroup = this.configuration.getGroup(formatGroup.getName());
        ConfigurationGroupTreeNode cgtn =
            new ConfigurationGroupTreeNode(this, this.configuration, formatGroup, configGroup);
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(cgtn, cgtn.allowsChildren());
        parent.add(node);
        addConfigurationGroupNodes(node, formatGroup.getGroupArray(), configGroup);
      }
    }
  }

  private void addConfigurationGroupNodes(
      DefaultMutableTreeNode parent,
      FormatGroup[] formatGroupArray,
      ConfigurationGroup configurationGroup) {
    for (FormatGroup formatGroup : formatGroupArray) {
      if (formatGroup.isVisible()) {
        ConfigurationGroup configGroup = configurationGroup.getGroup(formatGroup.getName());
        ConfigurationGroupTreeNode cgtn =
            new ConfigurationGroupTreeNode(this, this.configuration, formatGroup, configGroup);
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(cgtn, cgtn.allowsChildren());
        parent.add(node);
        addConfigurationGroupNodes(node, formatGroup.getGroupArray(), configGroup);
      }
    }
  }

  /* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/configuration/editor/tree/ConfigurationTree$MyTreeModelListener.class */
  class MyTreeModelListener implements TreeModelListener {
    MyTreeModelListener() {}

    public void treeNodesChanged(TreeModelEvent e) {
      TreePath parentPath = e.getTreePath();
      Object[] children = e.getChildren();
      ConfigurationTree.this.scrollPathToVisible(
          parentPath.pathByAddingChild(children[children.length - 1]));
    }

    public void treeNodesInserted(TreeModelEvent e) {
      TreePath parentPath = e.getTreePath();
      Object[] children = e.getChildren();
      ConfigurationTree.this.scrollPathToVisible(
          parentPath.pathByAddingChild(children[children.length - 1]));
    }

    public void treeNodesRemoved(TreeModelEvent e) {}

    public void treeStructureChanged(TreeModelEvent e) {}
  }

  /* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/configuration/editor/tree/ConfigurationTree$MyRenderer.class */
  class MyRenderer extends DefaultTreeCellRenderer {
    private static final long serialVersionUID = 1;
    ImageIcon iconInfoError;

    public MyRenderer() {}

    public Component getTreeCellRendererComponent(
        JTree tree,
        Object value,
        boolean sel,
        boolean expanded,
        boolean leaf,
        int row,
        boolean hasFocus) {
      super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
      setToolTipText(value.toString());
      Icon icon = getIcon(value);
      if (icon != null) {
        setIcon(icon);
      }
      return this;
    }

    private Icon getIcon(Object value) {
      return null;
    }
  }

  /* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/configuration/editor/tree/ConfigurationTree$MyMouseListener.class */
  class MyMouseListener extends MouseAdapter implements ActionListener {
    private BaseTreeNode selectedItem;

    /* synthetic */ MyMouseListener(
        ConfigurationTree configurationTree, MyMouseListener myMouseListener) {
      this();
    }

    private MyMouseListener() {}

    public void mousePressed(MouseEvent e) {
      showPopup(e);
    }

    public void mouseReleased(MouseEvent e) {
      showPopup(e);
    }

    private void showPopup(MouseEvent e) {
      TreePath selPath;
      JTree theTree = (JTree) e.getSource();
      if (e.isPopupTrigger()
          && (selPath = theTree.getPathForLocation(e.getX(), e.getY())) != null) {
        theTree.setSelectionPath(selPath);
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) selPath.getLastPathComponent();
        if (node != null) {
          this.selectedItem = (BaseTreeNode) node.getUserObject();
          JPopupMenu menu = this.selectedItem.getTreeMenu(this, selPath);
          if (menu != null) {
            menu.show(e.getComponent(), e.getX(), e.getY());
          }
        }
      }
    }

    public void actionPerformed(ActionEvent e) {
      this.selectedItem.processTreeMenuCommand(e);
    }
  }

  @Override // com.tvec.utility.configuration.ConfigurationMessageListener
  public void handleMessage(ConfigurationMessageSender sender, ConfigurationMessage cm) {
    if (cm.getAction() == 2) {
      updateTree();
    }
  }
}
