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

import java.awt.Component;
import java.awt.Container;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/displays/output/OutputList.class */
public class OutputList extends JList<String> {
  private static final long serialVersionUID = 1;
  private static final Logger logger = LogManager.getLogger(OutputList.class);
  public static final int DEFAULT_BUFFER_SIZE = 100;
  private int scrollbackBufferSize = 100;

  public static void main(String[] args) {
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(3);
    frame.setSize(300, 400);
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.getViewport().add(new OutputList());
    frame.add(scrollPane);
    frame.setVisible(true);
  }

  public OutputList() {
    setModel(new DefaultListModel<String>());
  }

  public void setScrollbackBufferSize(int newSize) {
    if (newSize < 1) {
      newSize = 100;
    }
    this.scrollbackBufferSize = newSize;
    DefaultListModel<String> listModel = (DefaultListModel<String>) getModel();
    while (listModel.getSize() > this.scrollbackBufferSize) {
      listModel.remove(0);
    }
  }

  public int getScrollbackBufferSize() {
    return this.scrollbackBufferSize;
  }

  /* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/displays/output/OutputList$AddTextRunnable.class */
  private class AddTextRunnable implements Runnable {
    String theText;

    AddTextRunnable(String theText) {
      this.theText = "";
      this.theText = theText;
    }

    @Override // java.lang.Runnable
    public void run() {
      OutputList.this.doAddText(this.theText);
    }
  }

  public synchronized void addText(String text) {
    if (text != null && text.length() > 0) {
      SwingUtilities.invokeLater(new AddTextRunnable(text));
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public synchronized void doAddText(String theText) {
    // Log output at debug level with value
    logger.debug("Output: {}", theText);

    String[] lines = theText.split("\n");
    DefaultListModel<String> listModel = (DefaultListModel<String>) getModel();
    while (listModel.getSize() > 0
        && listModel.getSize() + lines.length >= this.scrollbackBufferSize) {
      listModel.remove(0);
    }
    for (String str : lines) {
      listModel.addElement(str);
    }
    // Fixed scroll to bottom logic for modern Java versions
    SwingUtilities.invokeLater(
        new Runnable() {
          public void run() {
            int lastIndex = getModel().getSize() - 1;
            if (lastIndex >= 0) {
              ensureIndexIsVisible(lastIndex);
              // Force the parent container to update its viewport
              Container parent = getParent();
              if (parent instanceof JViewport) {
                JViewport viewport = (JViewport) parent;
                Container scrollPane = viewport.getParent();
                if (scrollPane instanceof JScrollPane) {
                  JScrollPane sp = (JScrollPane) scrollPane;
                  JScrollBar verticalScrollBar = sp.getVerticalScrollBar();
                  if (verticalScrollBar != null) {
                    verticalScrollBar.setValue(verticalScrollBar.getMaximum());
                  }
                }
              }
            }
          }
        });
  }

  public void clear() {
    DefaultListModel<String> listModel = (DefaultListModel<String>) getModel();
    listModel.removeAllElements();
  }

  /* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/displays/output/OutputList$MyCellRenderer.class */
  protected class MyCellRenderer implements ListCellRenderer {
    DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

    public MyCellRenderer() {}

    public Component getListCellRendererComponent(
        JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
      Component listCellRendererComponent =
          this.defaultRenderer.getListCellRendererComponent(
              list, value, index, isSelected, cellHasFocus);
      String text = (String) value;
      if (text.lastIndexOf("\n") > 0) {
        JEditorPane editorPane = new JEditorPane();
        listCellRendererComponent = editorPane;
        editorPane.setText(text);
        editorPane.setOpaque(true);
        editorPane.setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
        editorPane.setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
        if (isSelected) {
          if (cellHasFocus) {
            editorPane.setBorder(UIManager.getBorder("List.focusCellHighlightBorder"));
          } else {
            editorPane.setBorder(BorderFactory.createLineBorder(list.getSelectionBackground(), 2));
          }
        } else {
          editorPane.setBorder(BorderFactory.createLineBorder(list.getBackground(), 2));
        }
      }
      return listCellRendererComponent;
    }
  }
}
