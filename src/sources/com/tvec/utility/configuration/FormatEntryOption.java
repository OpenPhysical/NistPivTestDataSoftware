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

import com.tvec.utility.XMLUtils;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/configuration/FormatEntryOption.class */
public class FormatEntryOption {
  public static final String ELEMENT_NAME = "option";
  private static final String ATTRIBUTE_VALUE = "value";
  private final String value;
  private String displayName;

  public FormatEntryOption(Node node) throws DOMException {
    this.displayName = "";
    if (node == null || !node.getNodeName().equals(ELEMENT_NAME)) {
      throw new RuntimeException(
          "FormatEntryOption: FormatEntryOption not created with valid node");
    }
    HashMap hm = XMLUtils.getAttributes(node);
    this.value = (String) hm.get(ATTRIBUTE_VALUE);
    this.displayName = this.value;
    Node firstChild = node.getFirstChild();
    while (true) {
      Node child = firstChild;
      if (child != null) {
        String type = child.getNodeName();
        if (type.equals("#text")) {
          this.displayName = child.getNodeValue().trim();
        }
        firstChild = child.getNextSibling();
      } else {
        return;
      }
    }
  }

  public String getValue() {
    return this.value;
  }

  public String getDisplayName() {
    return this.displayName;
  }

  public Element getElement() throws ParserConfigurationException {
    return getElement(0);
  }

  public Element getElement(int level) throws ParserConfigurationException, DOMException {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    Element root = null;
    String baseIndent = "";
    for (int i = 0; i < level; i++) {
      baseIndent = baseIndent + "\t";
    }
    try {
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document document = builder.newDocument();
      root = document.createElement(ELEMENT_NAME);
      document.appendChild(root);
      root.setAttribute(ATTRIBUTE_VALUE, getValue());
      root.appendChild(document.createTextNode(getDisplayName()));
    } catch (ParserConfigurationException ex) {
      ex.printStackTrace();
    }
    return root;
  }

  public String toString() {
    String out = getValue() + ":" + getDisplayName();
    return out;
  }
}
