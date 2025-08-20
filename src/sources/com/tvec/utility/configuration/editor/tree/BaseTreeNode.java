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
import com.tvec.utility.configuration.ConfigurationGroup;
import com.tvec.utility.configuration.FormatGroup;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.JPopupMenu;
import javax.swing.tree.TreePath;

/* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/configuration/editor/tree/BaseTreeNode.class */
public abstract class BaseTreeNode {
  public static final int TOP_LEVEL_NODE = 0;
  public static final int CONFIGURATION_GROUP_NODE = 1;
  int nodeType;
  ConfigurationTree configurationTree;
  Configuration configuration;
  FormatGroup formatGroup;
  ConfigurationGroup configurationGroup;

  public abstract String getTreeString();

  public abstract boolean allowsChildren();

  public BaseTreeNode(
      ConfigurationTree configurationTree,
      int nodeType,
      Configuration configuration,
      FormatGroup formatGroup,
      ConfigurationGroup configurationGroup) {
    this.configurationTree = configurationTree;
    this.nodeType = nodeType;
    this.configuration = configuration;
    this.configurationGroup = configurationGroup;
    this.formatGroup = formatGroup;
  }

  public int getNodeType() {
    return this.nodeType;
  }

  public String toString() {
    return getTreeString();
  }

  public HashMap getChildMap() {
    return null;
  }

  public void processTreeMenuCommand(ActionEvent e) {}

  public JPopupMenu getTreeMenu(ActionListener actionListener, TreePath thePath) {
    return null;
  }
}
