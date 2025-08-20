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
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.TreeSet;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/configuration/FormatGroup.class */
public class FormatGroup {
  public static final String ELEMENT_NAME = "group";
  private static final String ATTRIBUTE_NAME = "name";
  private static final String ATTRIBUTE_EDITABLE = "editable";
  private static final String ATTRIBUTE_VISIBLE = "visible";
  private static final String CHILD_ELEMENT_DISPLAY_NAME = "display_name";
  private static final String CHILD_ELEMENT_EDIT_PANEL = "edit_panel";
  private String name;
  private String editPanel;
  private final HashMap<String, FormatEntry> entryLookup = new HashMap<>();
  private final HashMap<String, FormatGroup> groupLookup = new HashMap<>();
  private String displayName = "";
  private boolean editable = true;
  private boolean visible = true;

  public FormatGroup() {}

  public FormatGroup(Node node) throws DOMException {
    addNodes(node);
  }

  public void clear() {
    this.entryLookup.clear();
    this.groupLookup.clear();
  }

  public void addNodes(Node node) throws DOMException {
    if (node == null || !node.getNodeName().equals("group")) {
      throw new RuntimeException("FormatGroup: FormatGroup not created with valid node");
    }
    HashMap hm = XMLUtils.getAttributes(node);
    this.name = (String) hm.get(ATTRIBUTE_NAME);
    String editable = (String) hm.get(ATTRIBUTE_EDITABLE);
    if (editable != null && editable.equalsIgnoreCase("false")) {
      this.editable = false;
    }
    String visible = (String) hm.get(ATTRIBUTE_VISIBLE);
    if (visible != null && visible.equalsIgnoreCase("false")) {
      this.visible = false;
    }
    Node firstChild = node.getFirstChild();
    while (true) {
      Node child = firstChild;
      if (child != null) {
        String type = child.getNodeName();
        if (type.equals("entry")) {
          FormatEntry cfNode = new FormatEntry(child);
          this.entryLookup.put(cfNode.getName(), cfNode);
        } else if (type.equals(CHILD_ELEMENT_DISPLAY_NAME)) {
          Node defaultChild = child.getFirstChild();
          this.displayName = defaultChild.getNodeValue().trim();
        } else if (type.equals(CHILD_ELEMENT_EDIT_PANEL)) {
          Node defaultChild2 = child.getFirstChild();
          this.editPanel = defaultChild2.getNodeValue().trim();
        } else if (type.equals("group")) {
          FormatGroup cfg = new FormatGroup(child);
          this.groupLookup.put(cfg.getName(), cfg);
        }
        firstChild = child.getNextSibling();
      } else {
        return;
      }
    }
  }

  public String getName() {
    return this.name;
  }

  public boolean isEditable() {
    return this.editable;
  }

  public boolean isVisible() {
    return this.visible;
  }

  public String getDisplayName() {
    return this.displayName;
  }

  public String getEditPanelClassName() {
    return this.editPanel;
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

  public FormatEntry[] getEntryArray() {
    String[] entryNames = this.entryLookup.keySet().toArray(new String[0]);
    Arrays.sort(entryNames, String.CASE_INSENSITIVE_ORDER);
    FormatEntry[] out = new FormatEntry[entryNames.length];
    for (int i = 0; i < entryNames.length; i++) {
      out[i] = this.entryLookup.get(entryNames[i]);
    }
    return out;
  }

  public FormatEntry getEntry(String name) {
    return this.entryLookup.get(name);
  }

  public FormatGroup[] getGroupArray() {
    return this.groupLookup.values().toArray(new FormatGroup[0]);
  }

  public FormatGroup getGroup(String name) {
    return this.groupLookup.get(name);
  }

  public FormatGroup findGroup(String path) {
    FormatGroup ret = null;
    String[] pathBits = path.split(":");
    if (pathBits.length > 1) {
      FormatGroup subGroup = getGroup(pathBits[0]);
      if (subGroup != null) {
        ret = subGroup.findGroup(path.substring(pathBits[0].length() + 1));
      }
    } else if (pathBits.length == 1) {
      ret = getGroup(pathBits[0]);
    }
    return ret;
  }

  public FormatEntry findEntry(String path) {
    FormatEntry ret = null;
    String[] pathBits = path.split(":");
    if (pathBits.length > 1) {
      FormatGroup subGroup = getGroup(pathBits[0]);
      if (subGroup != null) {
        ret = subGroup.findEntry(path.substring(pathBits[0].length() + 1));
      }
    } else if (pathBits.length == 1) {
      ret = getEntry(pathBits[0]);
    }
    return ret;
  }

  public String toString() {
    StringBuffer out = new StringBuffer(128);
    Collection<FormatEntry> c = this.entryLookup.values();
    for (FormatEntry cfNode : c) {
      out.append(cfNode.toString());
    }
    return out.toString();
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
      root = document.createElement("group");
      document.appendChild(root);
      root.setAttribute(ATTRIBUTE_NAME, this.name);
      root.appendChild(document.createTextNode("\n"));
      if (!this.displayName.trim().equals("")) {
        root.appendChild(document.createTextNode(baseIndent));
        Element child = document.createElement(CHILD_ELEMENT_DISPLAY_NAME);
        child.appendChild(document.createTextNode(this.displayName));
        root.appendChild(document.createTextNode("\n"));
      }
      FormatEntry[] entryArray = getEntryArray();
      for (FormatEntry formatEntry : entryArray) {
        root.appendChild(document.createTextNode(baseIndent));
        root.appendChild(formatEntry.getElement(level + 1));
        root.appendChild(document.createTextNode("\n"));
      }
      FormatGroup[] groupArray = getGroupArray();
      for (FormatGroup formatGroup : groupArray) {
        root.appendChild(document.createTextNode(baseIndent));
        root.appendChild(formatGroup.getElement(level + 1));
        root.appendChild(document.createTextNode("\n"));
      }
    } catch (ParserConfigurationException ex) {
      ex.printStackTrace();
    }
    return root;
  }
}
