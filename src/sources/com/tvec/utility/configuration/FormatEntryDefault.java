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
import javax.xml.transform.TransformerException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/configuration/FormatEntryDefault.class */
public class FormatEntryDefault {
  public static final String ELEMENT_NAME = "default";
  private static final String ATTRIBUTE_TYPE = "type";
  public static final String ATTRIBUTE_TYPE_FRAGMENT = "fragment";
  public static final String ATTRIBUTE_TYPE_STRING = "string";
  private Node defaultValueNode;
  private String defaultValueString;
  private String type;

  public FormatEntryDefault() {
    this.defaultValueString = "";
    this.type = "string";
  }

  public FormatEntryDefault(Node node) throws DOMException {
    this.defaultValueString = "";
    this.type = "string";
    if (node == null || !node.getNodeName().equals(ELEMENT_NAME)) {
      throw new RuntimeException(
          "FormatEntryDefault: FormatEntryDefault not created with valid node");
    }
    HashMap hm = XMLUtils.getAttributes(node);
    if (hm.get(ATTRIBUTE_TYPE) != null) {
      this.type = (String) hm.get(ATTRIBUTE_TYPE);
      this.type.toLowerCase();
    }
    Node child = node.getFirstChild();
    if (this.type.equals("fragment")) {
      while (child != null) {
        String type = child.getNodeName();
        if (!type.equals("#text")) {
          this.defaultValueNode = child.cloneNode(true);
          return;
        }
        child = child.getNextSibling();
      }
      return;
    }
    while (child != null) {
      String type2 = child.getNodeName();
      if (type2.equals("#text")) {
        this.defaultValueString = this.defaultValueString + child.getNodeValue().trim();
      }
      child = child.getNextSibling();
    }
  }

  public String getType() {
    return this.type;
  }

  public String getDefaultString() throws TransformerException {
    if (getType().equals("fragment")) {
      return XMLUtils.xmlToString(this.defaultValueNode);
    }
    return this.defaultValueString;
  }

  public Node getDefaultNode() {
    return this.defaultValueNode;
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
      root.setAttribute(ATTRIBUTE_TYPE, getType());
      if (getType().equals("fragment")) {
        root.appendChild(getDefaultNode());
      } else {
        root.appendChild(document.createTextNode(getDefaultString()));
      }
    } catch (ParserConfigurationException ex) {
      ex.printStackTrace();
    } catch (TransformerException ex) {
      ex.printStackTrace();
    }
    return root;
  }

  public String toString() {
    StringBuffer out = new StringBuffer(128);
    out.append(getType());
    out.append(":");
    try {
      out.append(getDefaultString());
    } catch (TransformerException ex) {
      out.append("[Error: " + ex.getMessage() + "]");
    }
    return out.toString();
  }
}
