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
import java.util.Collection;
import java.util.HashMap;
import java.util.TreeSet;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/configuration/ConfigurationGroup.class */
public class ConfigurationGroup
    implements ConfigurationMessageSender, ConfigurationMessageListener {
  public static final String ELEMENT_NAME = "group";
  private static final String ATTRIBUTE_NAME = "name";
  public HashMap<ConfigurationMessageListener, ConfigurationMessageListener>
      configurationMessageListeners;
  private final HashMap<String, ConfigurationEntry> entryLookup;
  private final HashMap<String, ConfigurationGroup> groupLookup;
  private String name;

  public ConfigurationGroup() {
    this.configurationMessageListeners = new HashMap<>();
    this.entryLookup = new HashMap<>();
    this.groupLookup = new HashMap<>();
  }

  public ConfigurationGroup(String name) {
    this();
    this.name = name;
  }

  public ConfigurationGroup(Node node) throws DOMException {
    this();
    addNodes(node);
  }

  public ConfigurationMessageListener[] getConfigurationMessageListeners() {
    return this.configurationMessageListeners.values().toArray(new ConfigurationMessageListener[0]);
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

  public void clear() {
    this.entryLookup.clear();
    this.groupLookup.clear();
  }

  public void addNodes(Node node) throws DOMException {
    if (node == null || !node.getNodeName().equals("group")) {
      throw new RuntimeException(
          "ConfigurationGroup: ConfigurationGroup not created with valid node");
    }
    HashMap hm = XMLUtils.getAttributes(node);
    this.name = (String) hm.get(ATTRIBUTE_NAME);
    Node firstChild = node.getFirstChild();
    while (true) {
      Node child = firstChild;
      if (child != null) {
        String type = child.getNodeName();
        if (type.equals("entry")) {
          ConfigurationEntry ce = new ConfigurationEntry(child);
          addEntry(ce);
        } else if (type.equals("group")) {
          ConfigurationGroup cg = new ConfigurationGroup(child);
          addGroup(cg);
        }
        firstChild = child.getNextSibling();
      } else {
        return;
      }
    }
  }

  public void addEntry(ConfigurationEntry configurationEntry) {
    this.entryLookup.put(configurationEntry.getName(), configurationEntry);
    configurationEntry.addConfigurationMessageListener(this);
  }

  public void addGroup(ConfigurationGroup configurationGroup) {
    this.groupLookup.put(configurationGroup.getName(), configurationGroup);
    configurationGroup.addConfigurationMessageListener(this);
  }

  @Override // com.tvec.utility.configuration.ConfigurationMessageListener
  public void handleMessage(ConfigurationMessageSender sender, ConfigurationMessage cm) {
    String newKey = getName() + ":" + cm.getKey();
    cm.setKey(newKey);
    sendConfigurationMessage(cm);
  }

  public String getName() {
    return this.name;
  }

  public String[] getEntryNames() {
    return new TreeSet<>(this.entryLookup.keySet()).toArray(new String[0]);
  }

  public int getEntryCount() {
    return this.entryLookup.size();
  }

  public String[] getGroupNames() {
    return new TreeSet<>(this.groupLookup.keySet()).toArray(new String[0]);
  }

  public int getGroupCount() {
    return this.groupLookup.size();
  }

  public ConfigurationEntry[] getEntryArray() {
    return this.entryLookup.values().toArray(new ConfigurationEntry[0]);
  }

  public ConfigurationGroup[] getGroupArray() {
    return this.groupLookup.values().toArray(new ConfigurationGroup[0]);
  }

  public ConfigurationGroup getLocalGroup(String groupName) {
    return this.groupLookup.get(groupName);
  }

  public ConfigurationEntry getLocalEntry(String entryName) {
    return this.entryLookup.get(entryName);
  }

  public ConfigurationGroup getGroup(String path) {
    ConfigurationGroup ret = null;
    String[] pathBits = path.split(":");
    if (pathBits.length > 1) {
      ConfigurationGroup subGroup = getLocalGroup(pathBits[0]);
      if (subGroup != null) {
        ret = subGroup.getGroup(path.substring(pathBits[0].length() + 1));
      }
    } else if (pathBits.length == 1) {
      ret = getLocalGroup(pathBits[0]);
    }
    return ret;
  }

  public ConfigurationEntry getEntry(String path) {
    ConfigurationEntry ret = null;
    String[] pathBits = path.split(":");
    if (pathBits.length > 1) {
      ConfigurationGroup subGroup = getLocalGroup(pathBits[0]);
      if (subGroup != null) {
        ret = subGroup.getEntry(path.substring(pathBits[0].length() + 1));
      }
    } else if (pathBits.length == 1) {
      ret = getLocalEntry(pathBits[0]);
    }
    return ret;
  }

  public String toString() {
    StringBuffer out = new StringBuffer(128);
    Collection<ConfigurationEntry> c = this.entryLookup.values();
    for (ConfigurationEntry cfNode : c) {
      out.append(cfNode.toString());
    }
    return out.toString();
  }

  public void addElement(Document document, Element parent, int level) {
    String baseIndent = "";
    for (int i = 0; i < level; i++) {
      baseIndent = baseIndent + "\t";
    }
    Element root = document.createElement("group");
    parent.appendChild(root);
    root.setAttribute(ATTRIBUTE_NAME, this.name);
    root.appendChild(document.createTextNode("\n"));
    ConfigurationEntry[] entryArray = getEntryArray();
    for (ConfigurationEntry configurationEntry : entryArray) {
      root.appendChild(document.createTextNode(baseIndent));
      configurationEntry.addElement(document, root, level + 1);
      root.appendChild(document.createTextNode("\n"));
    }
    root.appendChild(document.createTextNode(baseIndent));
    ConfigurationGroup[] groupArray = getGroupArray();
    for (ConfigurationGroup configurationGroup : groupArray) {
      root.appendChild(document.createTextNode(baseIndent));
      configurationGroup.addElement(document, root, level + 1);
      root.appendChild(document.createTextNode("\n"));
    }
  }
}
