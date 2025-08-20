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

/* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/configuration/FormatEntry.class */
public class FormatEntry {
  public static final String ELEMENT_NAME = "entry";
  public static final String TYPE_STRING_HEX = "hexstring";
  public static final String TYPE_STRING_INT = "intstring";
  public static final String TYPE_STRING_STRING = "string";
  public static final String TYPE_STRING_DIRECTORY = "directory";
  public static final String TYPE_STRING_FILE = "file";
  public static final String TYPE_MULTI_SELECT_STRING = "multiselectstring";
  public static final String TYPE_PIV_KEY_ALGORITHM_LIST = "pivkeyalgorithmlist";
  public static final String TYPE_PIV_KEY_CRYPTOGRAPHIC_MECHANISM_LIST =
      "pivkeycryptographicmechanismlist";
  public static final String TYPE_TEST_MANAGER_MODIFICATIONS = "testmangermodifications";
  public static final String TYPE_X509_CERTIFICATE = "x509certificate";
  private static final String ATTRIBUTE_MIN = "min";
  private static final String ATTRIBUTE_MAX = "max";
  private static final String ATTRIBUTE_NAME = "name";
  private static final String ATTRIBUTE_TYPE = "type";
  private static final String CHILD_ELEMENT_DISPLAY_NAME = "display_name";
  private static final String CHILD_ELEMENT_HELP = "help";
  public static final Integer DATA_TYPE_STRING = Integer.valueOf(0);
  public static final Integer DATA_TYPE_FRAGMENT = Integer.valueOf(1);
  private static final HashMap<String, Integer> typeToDataType = new HashMap<>();
  private FormatEntryOptions options;
  private FormatEntryDefault defaultEntry;
  private final String name;
  private String displayName;
  private final String type;
  private String help;
  private Integer min;
  private Integer max;
  private String value;

  static {
    typeToDataType.put(TYPE_STRING_HEX, DATA_TYPE_STRING);
    typeToDataType.put(TYPE_STRING_INT, DATA_TYPE_STRING);
    typeToDataType.put("string", DATA_TYPE_STRING);
    typeToDataType.put(TYPE_STRING_DIRECTORY, DATA_TYPE_STRING);
    typeToDataType.put(TYPE_STRING_FILE, DATA_TYPE_STRING);
    typeToDataType.put(TYPE_MULTI_SELECT_STRING, DATA_TYPE_STRING);
    typeToDataType.put(TYPE_PIV_KEY_ALGORITHM_LIST, DATA_TYPE_STRING);
    typeToDataType.put(TYPE_PIV_KEY_CRYPTOGRAPHIC_MECHANISM_LIST, DATA_TYPE_STRING);
    typeToDataType.put(TYPE_TEST_MANAGER_MODIFICATIONS, DATA_TYPE_FRAGMENT);
    typeToDataType.put(TYPE_X509_CERTIFICATE, DATA_TYPE_STRING);
  }

  protected static Integer getDataType(String type) {
    return typeToDataType.get(type);
  }

  public FormatEntry(Node node) throws DOMException {
    this.options = null;
    this.defaultEntry = new FormatEntryDefault();
    this.displayName = "";
    this.help = "";
    this.value = "";
    if (node == null || !node.getNodeName().equals("entry")) {
      throw new RuntimeException("FormatEntry: FormatEntry not created with valid node");
    }
    HashMap hm = XMLUtils.getAttributes(node);
    this.name = (String) hm.get(ATTRIBUTE_NAME);
    this.type = (String) hm.get(ATTRIBUTE_TYPE);
    String maxString = (String) hm.get(ATTRIBUTE_MAX);
    if (maxString != null) {
      this.max = Integer.valueOf(maxString);
    }
    String minString = (String) hm.get(ATTRIBUTE_MIN);
    if (minString != null) {
      this.min = Integer.valueOf(minString);
    }
    Node firstChild = node.getFirstChild();
    while (true) {
      Node child = firstChild;
      if (child != null) {
        String type = child.getNodeName();
        if (type.equals(CHILD_ELEMENT_HELP)) {
          Node helpChild = child.getFirstChild();
          this.help = helpChild.getNodeValue().trim();
        } else if (type.equals(FormatEntryDefault.ELEMENT_NAME)) {
          FormatEntryDefault defaultNode = new FormatEntryDefault(child);
          this.defaultEntry = defaultNode;
        } else if (type.equals(CHILD_ELEMENT_DISPLAY_NAME)) {
          Node defaultChild = child.getFirstChild();
          this.displayName = defaultChild.getNodeValue().trim();
        } else if (type.equals(FormatEntryOptions.ELEMENT_NAME)) {
          FormatEntryOptions optionsNode = new FormatEntryOptions(child);
          this.options = optionsNode;
        } else if (type.equals("#text")) {
          this.value = child.getNodeValue().trim();
        }
        firstChild = child.getNextSibling();
      } else {
        return;
      }
    }
  }

  public FormatEntryOptions getOptions() {
    return this.options;
  }

  public String getName() {
    return this.name;
  }

  public String getDisplayName() {
    return this.displayName;
  }

  public String getType() {
    return this.type;
  }

  public String getHelp() {
    return this.help;
  }

  public String getValue() {
    return this.value;
  }

  public FormatEntryDefault getDefaultEntry() {
    return this.defaultEntry;
  }

  public int getMin() {
    int ret = 0;
    if (this.min != null) {
      ret = this.min.intValue();
    }
    return ret;
  }

  public int getMax() {
    int ret = 0;
    if (this.max != null) {
      ret = this.max.intValue();
    }
    return ret;
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
      root = document.createElement("entry");
      document.appendChild(root);
      root.setAttribute(ATTRIBUTE_NAME, this.name);
      root.setAttribute(ATTRIBUTE_TYPE, this.type);
      if (this.min != null) {
        root.setAttribute(ATTRIBUTE_MIN, this.min.toString());
      }
      if (this.max != null) {
        root.setAttribute(ATTRIBUTE_MAX, this.max.toString());
      }
      root.appendChild(document.createTextNode("\n"));
      if (!this.displayName.trim().equals("")) {
        root.appendChild(document.createTextNode(baseIndent));
        Element child = document.createElement(CHILD_ELEMENT_DISPLAY_NAME);
        child.appendChild(document.createTextNode(this.displayName));
        root.appendChild(document.createTextNode("\n"));
      }
      if (this.defaultEntry != null) {
        root.appendChild(this.defaultEntry.getElement(level + 1));
        root.appendChild(document.createTextNode("\n"));
      }
      if (!this.help.trim().equals("")) {
        root.appendChild(document.createTextNode(baseIndent));
        Element child2 = document.createElement(CHILD_ELEMENT_HELP);
        child2.appendChild(document.createTextNode(this.help));
        root.appendChild(document.createTextNode("\n"));
      }
    } catch (ParserConfigurationException ex) {
      ex.printStackTrace();
    }
    return root;
  }

  public String toString() {
    String out =
        this.name
            + ":"
            + this.type
            + ":"
            + this.min
            + ":"
            + this.max
            + ":"
            + this.value
            + ":"
            + this.help;
    return out;
  }
}
