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
import com.tvec.utility.configuration.editor.EditPanelInterface;
import com.tvec.utility.configuration.editor.edit_panel.DefaultEditPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.tree.TreePath;

/* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/configuration/editor/tree/ConfigurationGroupTreeNode.class */
public class ConfigurationGroupTreeNode extends BaseTreeNode {
  public ConfigurationGroupTreeNode(
      ConfigurationTree configurationTree,
      Configuration configuration,
      FormatGroup formatGroup,
      ConfigurationGroup configurationGroup) {
    super(configurationTree, 1, configuration, formatGroup, configurationGroup);
  }

  @Override // com.tvec.utility.configuration.editor.tree.BaseTreeNode
  public String getTreeString() {
    String displayName = this.formatGroup.getDisplayName();
    if (displayName == null || displayName.equals("")) {
      displayName = this.formatGroup.getName();
    }
    return displayName;
  }

  @Override // com.tvec.utility.configuration.editor.tree.BaseTreeNode
  public boolean allowsChildren() {
    return true;
  }

  public JComponent getDisplay() throws ClassNotFoundException {
    JComponent defaultEditPanel = null;
    String className = this.formatGroup.getEditPanelClassName();
    if (className != null) {
      try {
        Class<?> displayClass = Class.forName(className);
        defaultEditPanel = (JComponent) displayClass.getDeclaredConstructor().newInstance();
      } catch (ClassNotFoundException e) {
      } catch (IllegalAccessException e2) {
      } catch (InstantiationException e3) {
      } catch (NoSuchMethodException e4) {
      } catch (java.lang.reflect.InvocationTargetException e5) {
      }
    }
    if (defaultEditPanel == null) {
      defaultEditPanel = new DefaultEditPanel();
    }
    ((EditPanelInterface) defaultEditPanel)
        .init(this.configuration, this.formatGroup, this.configurationGroup);
    return defaultEditPanel;
  }

  @Override // com.tvec.utility.configuration.editor.tree.BaseTreeNode
  public void processTreeMenuCommand(ActionEvent e) {}

  @Override // com.tvec.utility.configuration.editor.tree.BaseTreeNode
  public JPopupMenu getTreeMenu(ActionListener actionListener, TreePath theTreePath) {
    super.getTreeMenu(actionListener, theTreePath);
    return null;
  }
}
