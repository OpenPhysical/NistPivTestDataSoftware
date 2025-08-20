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

package com.tvec.utility.configuration;

import java.util.Collection;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/configuration/FormatEntryOptions.class */
public class FormatEntryOptions {
  public static final String ELEMENT_NAME = "options";
  private final HashMap<String, FormatEntryOption> optionLookup = new HashMap<>();

  public FormatEntryOptions(Node node) {
    if (node == null || !node.getNodeName().equals(ELEMENT_NAME)) {
      throw new RuntimeException(
          "FormatEntryOptions: FormatEntryOptions not created with valid node");
    }
    Node firstChild = node.getFirstChild();
    while (true) {
      Node child = firstChild;
      if (child != null) {
        String type = child.getNodeName();
        if (type.equals(FormatEntryOption.ELEMENT_NAME)) {
          FormatEntryOption optionNode = new FormatEntryOption(child);
          this.optionLookup.put(optionNode.getValue(), optionNode);
        }
        firstChild = child.getNextSibling();
      } else {
        return;
      }
    }
  }

  public FormatEntryOption[] getOptionArray() {
    return this.optionLookup.values().toArray(new FormatEntryOption[0]);
  }

  public Element getElement() throws ParserConfigurationException {
    return getElement(0);
  }

  public Element getElement(int level) throws ParserConfigurationException, DOMException {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    String baseIndent = "";
    for (int i = 0; i < level; i++) {
      baseIndent = baseIndent + "\t";
    }
    Element root = null;
    try {
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document document = builder.newDocument();
      root = document.createElement(ELEMENT_NAME);
      document.appendChild(root);
      root.appendChild(document.createTextNode("\n"));
      FormatEntryOption[] optionArray = getOptionArray();
      for (FormatEntryOption formatEntryOption : optionArray) {
        root.appendChild(document.createTextNode(baseIndent));
        root.appendChild(formatEntryOption.getElement(level + 1));
        root.appendChild(document.createTextNode("\n"));
      }
    } catch (ParserConfigurationException ex) {
      ex.printStackTrace();
    }
    return root;
  }

  public String toString() {
    StringBuffer out = new StringBuffer(128);
    Collection<FormatEntryOption> c = this.optionLookup.values();
    for (FormatEntryOption cfNode : c) {
      out.append(cfNode.toString());
    }
    return out.toString();
  }
}
