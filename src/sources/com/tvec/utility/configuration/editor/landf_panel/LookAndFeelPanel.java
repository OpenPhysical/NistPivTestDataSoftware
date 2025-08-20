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

package com.tvec.utility.configuration.editor.landf_panel;

import com.tvec.utility.configuration.Configuration;
import com.tvec.utility.configuration.ConfigurationEntry;
import com.tvec.utility.configuration.ConfigurationGroup;
import com.tvec.utility.configuration.FormatGroup;
import com.tvec.utility.configuration.editor.EditPanelInterface;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/configuration/editor/landf_panel/LookAndFeelPanel.class */
public class LookAndFeelPanel extends JPanel implements EditPanelInterface {
  private static final long serialVersionUID = 1;
  public Configuration configuration;
  public ConfigurationGroup configurationGroup;
  public FormatGroup formatGroup;
  public ConfigurationEntry landfEntry;
  JComboBox<String> jComboBoxLandF = new JComboBox<>();
  JLabel jLabelSelectLandF = new JLabel();

  public LookAndFeelPanel() {
    try {
      jbInit();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  @Override // com.tvec.utility.configuration.editor.EditPanelInterface
  public void focusLost() {}

  @Override // com.tvec.utility.configuration.editor.EditPanelInterface
  public void init(
      Configuration config, FormatGroup formatGroup, ConfigurationGroup configurationGroup) {
    this.configuration = config;
    this.configurationGroup = configurationGroup;
    this.formatGroup = formatGroup;
    ConfigurationEntry[] entryArray = configurationGroup.getEntryArray();
    if (entryArray != null && entryArray.length > 0) {
      this.landfEntry = entryArray[0];
      String className = this.landfEntry.getValueString();
      UIManager.LookAndFeelInfo[] info = UIManager.getInstalledLookAndFeels();
      for (int i = 0; i < info.length; i++) {
        if (info[i].getClassName() == className) {
          this.jComboBoxLandF.setSelectedItem(info[i].getName());
          return;
        }
      }
    }
  }

  private void jbInit() throws Exception {
    UIManager.LookAndFeelInfo[] info = UIManager.getInstalledLookAndFeels();
    for (UIManager.LookAndFeelInfo lookAndFeelInfo : info) {
      this.jComboBoxLandF.addItem(lookAndFeelInfo.getName());
    }
    this.jComboBoxLandF.setSelectedItem(UIManager.getLookAndFeel().getName());
    this.jComboBoxLandF.addActionListener(new LookAndFeelPanel_jComboBoxLandF_actionAdapter(this));
    this.jLabelSelectLandF.setToolTipText("");
    this.jLabelSelectLandF.setText("Select Look And Feel:");
    add(this.jLabelSelectLandF);
    add(this.jComboBoxLandF);
  }

  public void jComboBoxLandF_actionPerformed(ActionEvent e) {
    String selectedItem = (String) this.jComboBoxLandF.getSelectedItem();
    try {
      Component[] frames = Frame.getFrames();
      Component component = null;
      if (0 < frames.length) {
        component = frames[0];
      }
      UIManager.LookAndFeelInfo[] info = UIManager.getInstalledLookAndFeels();
      for (int i = 0; i < info.length; i++) {
        if (info[i].getName() == selectedItem) {
          String className = info[i].getClassName();
          UIManager.setLookAndFeel(className);
          SwingUtilities.updateComponentTreeUI(component);
          this.landfEntry.setValue(className);
          return;
        }
      }
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
  }
}
