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

import com.tvec.utility.configuration.Configuration;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPopupMenu;
import javax.swing.tree.TreePath;

/* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/configuration/editor/tree/RootTreeNode.class */
public class RootTreeNode extends BaseTreeNode {
  public static final String TREE_TEXT = "ROOT";

  public RootTreeNode(ConfigurationTree configurationTree, Configuration configuration) {
    super(configurationTree, 0, configuration, null, null);
  }

  @Override // com.tvec.utility.configuration.editor.tree.BaseTreeNode
  public String getTreeString() {
    return TREE_TEXT;
  }

  @Override // com.tvec.utility.configuration.editor.tree.BaseTreeNode
  public boolean allowsChildren() {
    return true;
  }

  @Override // com.tvec.utility.configuration.editor.tree.BaseTreeNode
  public void processTreeMenuCommand(ActionEvent e) {}

  @Override // com.tvec.utility.configuration.editor.tree.BaseTreeNode
  public JPopupMenu getTreeMenu(ActionListener actionListener, TreePath theTreePath) {
    super.getTreeMenu(actionListener, theTreePath);
    return null;
  }
}
