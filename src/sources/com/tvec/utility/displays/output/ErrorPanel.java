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
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/displays/output/ErrorPanel.class */
public class ErrorPanel extends JScrollPane implements ErrorMessageListener {
  private static final long serialVersionUID = 1;
  private final JTextArea output = new JTextArea(5, 30);

  public ErrorPanel() {
    this.output.addMouseListener(new MyMouseListener(this, null));
    setViewportView(this.output);
  }

  public void addText(String text) {
    if (text != null && text.length() > 0) {
      this.output.setText(this.output.getText() + "\n" + text);
      JScrollBar scrollBar = getVerticalScrollBar();
      scrollBar.setValue(scrollBar.getMaximum());
    }
  }

  @Override // com.tvec.utility.displays.output.ErrorMessageListener
  public void handleMessage(ErrorMessageSender sender, ErrorMessage em) {
    String errorText = em.getErrorText();
    if (errorText != null && errorText.length() > 0) {
      addText(errorText);
    }
  }

  /* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/displays/output/ErrorPanel$MyMouseListener.class */
  private class MyMouseListener extends MouseAdapter implements ActionListener {
    JPopupMenu popupMenu;
    public static final String COMMAND_CLEAR = "Clear Error Log";

    /* synthetic */ MyMouseListener(ErrorPanel errorPanel, MyMouseListener myMouseListener) {
      this();
    }

    private MyMouseListener() {
      this.popupMenu = new JPopupMenu("Operations");
      JMenuItem mi = new JMenuItem(COMMAND_CLEAR);
      mi.addActionListener(this);
      this.popupMenu.add(mi);
    }

    public void mousePressed(MouseEvent e) {
      showPopup(e);
    }

    public void mouseReleased(MouseEvent e) {
      showPopup(e);
    }

    private void showPopup(MouseEvent e) {
      if (e.isPopupTrigger() && this.popupMenu != null) {
        this.popupMenu.show(e.getComponent(), e.getX(), e.getY());
      }
    }

    public void actionPerformed(ActionEvent e) {
      String command = e.getActionCommand();
      if (command.equals(COMMAND_CLEAR)) {
        ErrorPanel.this.output.setText("");
      }
    }
  }
}
