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

package com.tvec.utility.displays.output;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

/* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/displays/output/OutputPanel.class */
public class OutputPanel extends JScrollPane implements OutputMessageListener {
  private static final long serialVersionUID = 1;
  private final OutputList outputList = new OutputList();

  public OutputPanel() {
    this.outputList.addMouseListener(new MyMouseListener(this, this.outputList, null));
    setViewportView(this.outputList);
  }

  public void addText(String text) {
    this.outputList.addText(text);
  }

  @Override // com.tvec.utility.displays.output.OutputMessageListener
  public void handleMessage(OutputMessageSender sender, OutputMessage om) {
    String outputText = om.getOutputText();
    if (outputText != null && outputText.length() > 0) {
      addText(outputText);
    }
  }

  /* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/displays/output/OutputPanel$MyMouseListener.class */
  private class MyMouseListener extends MouseAdapter implements ActionListener {
    public static final String MENU_ITEM_CLEAR = "Clear";
    public static final String MENU_ITEM_PROPERTIES = "Properties...";
    private final JPopupMenu popupMenu;
    private final OutputList outputList;

    /* synthetic */ MyMouseListener(
        OutputPanel outputPanel, OutputList outputList, MyMouseListener myMouseListener) {
      this(outputList);
    }

    private MyMouseListener(OutputList outputList) {
      this.popupMenu = new JPopupMenu();
      this.outputList = outputList;
      JMenuItem menuItem = new JMenuItem(MENU_ITEM_CLEAR);
      menuItem.addActionListener(this);
      this.popupMenu.add(menuItem);
      JMenuItem menuItem2 = new JMenuItem(MENU_ITEM_PROPERTIES);
      menuItem2.addActionListener(this);
      this.popupMenu.add(menuItem2);
    }

    public void mousePressed(MouseEvent e) {
      showPopup(e);
    }

    public void mouseReleased(MouseEvent e) {
      showPopup(e);
    }

    private void showPopup(MouseEvent e) {
      OutputList theList = (OutputList) e.getSource();
      if (e.isPopupTrigger()) {
        this.popupMenu.show(theList, e.getX(), e.getY());
      }
    }

    public void actionPerformed(ActionEvent e) {
      if (e.getActionCommand().equals(MENU_ITEM_CLEAR)) {
        this.outputList.clear();
        return;
      }
      if (e.getActionCommand().equals(MENU_ITEM_PROPERTIES)) {
        OutputPropertiesDialog dialog = new OutputPropertiesDialog(this.outputList);
        dialog.setScrollbackBufferSize(this.outputList.getScrollbackBufferSize());
        dialog.setVisible(true);
        if (dialog.getSelection() == 0) {
          this.outputList.setScrollbackBufferSize(dialog.getScrollbackBufferSize());
        }
      }
    }
  }
}
