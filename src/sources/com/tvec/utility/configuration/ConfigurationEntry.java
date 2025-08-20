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
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/configuration/ConfigurationEntry.class */
public class ConfigurationEntry implements ConfigurationMessageSender {
  public static final String ELEMENT_NAME = "entry";
  private static final String ATTRIBUTE_NAME = "name";
  private static final String ATTRIBUTE_TYPE = "type";
  public static final String ATTRIBUTE_TYPE_FRAGMENT = "fragment";
  public static final String ATTRIBUTE_TYPE_STRING = "string";
  public HashMap<ConfigurationMessageListener, ConfigurationMessageListener>
      configurationMessageListeners;
  private String type;
  private final String name;
  private String valueString;
  private Node valueNode;

  public ConfigurationEntry(Node node) throws DOMException {
    this.configurationMessageListeners = new HashMap<>();
    this.type = "string";
    this.valueString = "";
    if (node == null || !node.getNodeName().equals("entry")) {
      throw new RuntimeException(
          "ConfigurationEntry: ConfigurationEntry not created with valid node");
    }
    HashMap hm = XMLUtils.getAttributes(node);
    this.name = (String) hm.get(ATTRIBUTE_NAME);
    if (hm.get(ATTRIBUTE_TYPE) != null) {
      this.type = ((String) hm.get(ATTRIBUTE_TYPE)).toLowerCase();
    }
    Node child = node.getFirstChild();
    if (this.type.equals("fragment")) {
      while (child != null) {
        String type = child.getNodeName();
        if (!type.equals("#text")) {
          this.valueNode = child.cloneNode(true);
          return;
        }
        child = child.getNextSibling();
      }
      return;
    }
    while (child != null) {
      String type2 = child.getNodeName();
      if (type2.equals("#text")) {
        this.valueString = this.valueString + child.getNodeValue().trim();
      }
      child = child.getNextSibling();
    }
  }

  public ConfigurationEntry(String name, FormatEntryDefault value) {
    this.configurationMessageListeners = new HashMap<>();
    this.type = "string";
    this.valueString = "";
    this.name = name;
    if (value.getType().equals("string")) {
      this.type = "string";
      try {
        this.valueString = value.getDefaultString();
      } catch (javax.xml.transform.TransformerException e) {
        e.printStackTrace();
        this.valueString = "";
      }
    } else {
      this.type = "fragment";
      this.valueNode = value.getDefaultNode();
    }
  }

  public ConfigurationEntry(String name, String value) {
    this.configurationMessageListeners = new HashMap<>();
    this.type = "string";
    this.valueString = "";
    this.name = name;
    this.valueString = value;
  }

  public ConfigurationEntry(String name, Node value) {
    this.configurationMessageListeners = new HashMap<>();
    this.type = "string";
    this.valueString = "";
    this.type = "fragment";
    this.name = name;
    this.valueNode = value;
  }

  @Override // com.tvec.utility.configuration.ConfigurationMessageSender
  public void addConfigurationMessageListener(ConfigurationMessageListener cml) {
    this.configurationMessageListeners.put(cml, cml);
  }

  @Override // com.tvec.utility.configuration.ConfigurationMessageSender
  public void deleteConfigurationMessageListener(ConfigurationMessageListener cml) {
    this.configurationMessageListeners.remove(cml);
  }

  @Override // com.tvec.utility.configuration.ConfigurationMessageSender
  public void sendConfigurationMessage(ConfigurationMessage cm) {
    for (ConfigurationMessageListener cml : this.configurationMessageListeners.values()) {
      cml.handleMessage(this, cm);
    }
  }

  public String getName() {
    return this.name;
  }

  public String getType() {
    return this.type;
  }

  public String getValueString() {
    if (getType().equals("fragment")) {
      try {
        return XMLUtils.xmlToString(this.valueNode);
      } catch (javax.xml.transform.TransformerException e) {
        e.printStackTrace();
        return "";
      }
    }
    return this.valueString;
  }

  public Node getValueNode() {
    return this.valueNode;
  }

  public void setValue(String value) {
    this.valueString = value;
    sendConfigurationMessage(new ConfigurationMessage(getName(), 1));
  }

  public void setValue(Node value) {
    this.valueNode = value;
    sendConfigurationMessage(new ConfigurationMessage(getName(), 1));
  }

  public void addElement(Document document, Element parent, int level) throws DOMException {
    String baseIndent = "";
    for (int i = 0; i < level; i++) {
      baseIndent = baseIndent + "\t";
    }
    parent.appendChild(document.createTextNode(baseIndent));
    Element root = document.createElement("entry");
    root.setAttribute(ATTRIBUTE_NAME, this.name);
    root.setAttribute(ATTRIBUTE_TYPE, getType());
    if (getType().equals("fragment")) {
      root.appendChild(document.importNode(getValueNode(), true));
    } else {
      root.appendChild(document.createTextNode(getValueString()));
    }
    parent.appendChild(root);
  }

  public String toString() {
    String out = this.name + ":" + getValueString();
    return out;
  }
}
