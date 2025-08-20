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

package com.tvec.utility.configuration.editor.edit_panel.cell_handlers;

import com.tvec.utility.configuration.editor.edit_panel.BaseRowData;
import java.awt.Toolkit;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/configuration/editor/edit_panel/cell_handlers/HexStringEditor.class */
public class HexStringEditor extends JTextField implements BaseHandler {
  private static final long serialVersionUID = 1;
  protected BaseRowData baseValueType;
  private final IntegerDocument integerDocument1 = new IntegerDocument();

  public HexStringEditor() {
    setDocument(this.integerDocument1);
  }

  @Override // com.tvec.utility.configuration.editor.edit_panel.cell_handlers.BaseHandler
  public void setRowData(BaseRowData baseRowData) {
    this.baseValueType = baseRowData;
    setText(baseRowData.getText());
  }

  @Override // com.tvec.utility.configuration.editor.edit_panel.cell_handlers.BaseHandler
  public String getValue() {
    return getText();
  }

  /* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/configuration/editor/edit_panel/cell_handlers/HexStringEditor$IntegerDocument.class */
  static class IntegerDocument extends PlainDocument {
    private static final long serialVersionUID = 1;

    IntegerDocument() {}

    public void insertString(int offset, String string, AttributeSet attributes)
        throws BadLocationException {
      String newValue;
      if (string == null) {
        return;
      }
      int length = getLength();
      if (length == 0) {
        newValue = string;
      } else {
        String currentContent = getText(0, length);
        StringBuffer currentBuffer = new StringBuffer(currentContent);
        currentBuffer.insert(offset, string);
        newValue = currentBuffer.toString();
      }
      try {
        byte[] testArray = newValue.toUpperCase().getBytes();
        for (int i = 0; i < testArray.length; i++) {
          if ((testArray[i] < 65 || testArray[i] > 70)
              && (testArray[i] < 48 || testArray[i] > 57)) {
            throw new NumberFormatException();
          }
        }
        super.insertString(offset, string, attributes);
      } catch (NumberFormatException e) {
        Toolkit.getDefaultToolkit().beep();
      }
    }
  }
}
