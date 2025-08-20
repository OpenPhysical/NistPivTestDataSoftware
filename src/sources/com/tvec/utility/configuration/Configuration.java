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

import com.tvec.utility.JARUtils;
import com.tvec.utility.XMLUtils;
import com.tvec.utility.configuration.editor.edit_panel.cell_handlers.DialogMultiSelectListEditor;
import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeSet;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/configuration/Configuration.class */
public class Configuration implements ConfigurationMessageListener, ConfigurationMessageSender {
  public static final String ELEMENT_NAME = "configuration";
  private static final String ATTRIBUTE_FORMAT_NAME = "format_name";
  private boolean loadingConfiguration;
  private final HashMap<ConfigurationMessageListener, ConfigurationMessageListener>
      configurationMessageListeners;
  private final HashMap<String, ConfigurationGroup> groupLookup;
  private String formatName;

  public static void main(String[] args)
      throws TransformerException, ParserConfigurationException, DOMException {
    try {
      Configuration config =
          new Configuration(
              "S:\\jbproject\\piv_card\\piv_files\\java_files\\tvec\\TestRunnerFiles\\config\\PIV_APDU_TestConfig.xml");
      ConfigurationEntry entry = config.getEntry("connectivity:PIV_APDU_CLASS");
      System.out.println(entry.getValueString());
      config.toFile(
          "S:\\jbproject\\piv_card\\piv_files\\java_files\\tvec\\TestRunnerFiles\\config\\test.xml");
    } catch (IOException e) {
      e.printStackTrace();
    } catch (FactoryConfigurationError e2) {
      e2.printStackTrace();
    }
  }

  private static Document openDocument(InputStream input)
      throws ParserConfigurationException, SAXException, IOException {
    Document document = null;
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      document = builder.parse(input);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (FactoryConfigurationError e2) {
      e2.printStackTrace();
    } catch (ParserConfigurationException e3) {
      e3.printStackTrace();
    } catch (SAXException e4) {
      e4.printStackTrace();
    }
    return document;
  }

  public Configuration() {
    this.loadingConfiguration = false;
    this.configurationMessageListeners = new HashMap<>();
    this.groupLookup = new HashMap<>();
    this.formatName = "";
  }

  public Configuration(Format format) {
    this();
    this.formatName = format.getName();
    setDefaultValues(format);
  }

  public Configuration(String xmlFile) throws FileNotFoundException {
    this(new FileInputStream(xmlFile));
  }

  public Configuration(File xmlFile) throws FileNotFoundException {
    this(new FileInputStream(xmlFile));
  }

  public Configuration(InputStream input) {
    this();
    try {
      loadConfiguration(openDocument(input).getFirstChild());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public Configuration(Node node) {
    this();
    loadConfiguration(node);
  }

  public void loadConfiguration(File xmlFile) throws FileNotFoundException {
    loadConfiguration(xmlFile, false);
  }

  public void loadConfiguration(File xmlFile, boolean loadDefaults) throws FileNotFoundException {
    loadConfiguration(new FileInputStream(xmlFile), loadDefaults);
  }

  public void loadConfiguration(InputStream input) {
    loadConfiguration(input, false);
  }

  public void loadConfiguration(InputStream input, boolean loadDefaults) {
    try {
      loadConfiguration(openDocument(input).getFirstChild(), loadDefaults);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public void loadConfiguration(Node node) {
    loadConfiguration(node, false);
  }

  public synchronized void loadConfiguration(Node node, boolean loadDefaults) {
    boolean z = false;
    this.loadingConfiguration = true;
    if (node != null) {
      try {
        if (node.getNodeName().equals(ELEMENT_NAME)) {
          clear();
          HashMap hm = XMLUtils.getAttributes(node);
          this.formatName = (String) hm.get(ATTRIBUTE_FORMAT_NAME);
          for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
            String type = child.getNodeName();
            if (type.equals("group")) {
              ConfigurationGroup cg = new ConfigurationGroup(child);
              addGroup(cg);
            }
          }
          if (loadDefaults) {
            try {
              setDefaultValues();
            } catch (ParserConfigurationException e) {
              e.printStackTrace();
            }
          }
          if (z) {
            return;
          } else {
            return;
          }
        }
      } finally {
        if (this.loadingConfiguration) {
          this.loadingConfiguration = false;
          sendConfigurationMessage(new ConfigurationMessage(null, 2));
        }
      }
    }
    throw new RuntimeException("Configuration: configuration file not recognized.");
  }

  public synchronized void loadDefaults(Format format) {
    this.formatName = format.getName();
    loadDefaults();
  }

  public synchronized void loadDefaults() {
    clear();
    try {
      setDefaultValues();
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    }
    sendConfigurationMessage(new ConfigurationMessage(null, 2));
  }

  @Override // com.tvec.utility.configuration.ConfigurationMessageSender
  public synchronized void addConfigurationMessageListener(ConfigurationMessageListener cml) {
    this.configurationMessageListeners.put(cml, cml);
  }

  @Override // com.tvec.utility.configuration.ConfigurationMessageSender
  public synchronized void deleteConfigurationMessageListener(ConfigurationMessageListener cml) {
    this.configurationMessageListeners.remove(cml);
  }

  @Override // com.tvec.utility.configuration.ConfigurationMessageSender
  public synchronized void sendConfigurationMessage(ConfigurationMessage cm) {
    if (!this.loadingConfiguration) {
      HashMap<ConfigurationMessageListener, ConfigurationMessageListener> temp =
          new HashMap<>(this.configurationMessageListeners);
      for (ConfigurationMessageListener cml : temp.values()) {
        cml.handleMessage(this, cm);
      }
    }
  }

  @Override // com.tvec.utility.configuration.ConfigurationMessageListener
  public void handleMessage(ConfigurationMessageSender sender, ConfigurationMessage cm) {
    if (!this.loadingConfiguration) {
      switch (cm.getAction()) {
        case 1:
        case 2:
          sendConfigurationMessage(cm);
          break;
        case 3:
          editEntry(cm.getKey(), cm.getParent());
          break;
      }
    }
  }

  public boolean isInitialized() {
    return !this.formatName.equals("");
  }

  public void addGroup(ConfigurationGroup configurationGroup) {
    this.groupLookup.put(configurationGroup.getName(), configurationGroup);
    configurationGroup.addConfigurationMessageListener(this);
  }

  public void editEntry(String key, Component parent) {
    Format format = getFormat();
    FormatEntry formatEntry = format.findEntry(key);
    if (formatEntry.getType().equalsIgnoreCase(FormatEntry.TYPE_MULTI_SELECT_STRING)) {
      FormatEntryOptions options = formatEntry.getOptions();
      FormatEntryOption[] optionArray = options.getOptionArray();
      ConfigurationEntry entry = getEntry(key);
      String[] currentSelections = getStringArray(key);
      DialogMultiSelectListEditor dialog =
          new DialogMultiSelectListEditor(optionArray, currentSelections);
      if (dialog.showDialog(parent) == 0) {
        entry.setValue(dialog.getStringList());
        return;
      }
      return;
    }
    System.out.println(
        "Configuration: editEntry - no edit support has been added for types of :"
            + formatEntry.getType());
  }

  public String getFormatName() {
    return this.formatName;
  }

  public Format getFormat() {
    try {
      InputStream in = JARUtils.getInputStream(this.formatName);
      return new Format(in);
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  public void setDefaultValues() throws ParserConfigurationException {
    setDefaultValues(getFormat());
  }

  public void setDefaultValues(Format format) {
    this.formatName = format.getName();
    FormatGroup[] formatGroupArray = format.getGroupArray();
    for (FormatGroup formatGroup : formatGroupArray) {
      ConfigurationGroup configGroup = getGroup(formatGroup.getName());
      if (configGroup == null) {
        configGroup = new ConfigurationGroup(formatGroup.getName());
        addGroup(configGroup);
      }
      setDefaultValues(formatGroup, configGroup);
    }
  }

  public void setDefaultValues(FormatGroup formatGroup, ConfigurationGroup configGroup) {
    FormatEntry[] formatEntryArray = formatGroup.getEntryArray();
    for (FormatEntry formatEntry : formatEntryArray) {
      ConfigurationEntry configEntry = configGroup.getLocalEntry(formatEntry.getName());
      if (configEntry == null) {
        configGroup.addEntry(
            new ConfigurationEntry(formatEntry.getName(), formatEntry.getDefaultEntry()));
      }
    }
    FormatGroup[] formatGroupArray = formatGroup.getGroupArray();
    for (FormatGroup formatSubGroup : formatGroupArray) {
      ConfigurationGroup configSubGroup = configGroup.getLocalGroup(formatSubGroup.getName());
      if (configSubGroup == null) {
        configSubGroup = new ConfigurationGroup(formatSubGroup.getName());
        configGroup.addGroup(configSubGroup);
      }
      setDefaultValues(formatSubGroup, configSubGroup);
    }
  }

  public String[] getGroupNames() {
    return new TreeSet<>(this.groupLookup.keySet()).toArray(new String[0]);
  }

  public ConfigurationGroup[] getGroupArray() {
    return this.groupLookup.values().toArray(new ConfigurationGroup[0]);
  }

  public int getGroupCount() {
    return this.groupLookup.size();
  }

  public String getDirectory(String key, String defaultDirectory) {
    String directory = getValue(key);
    if (directory == null) {
      directory = defaultDirectory;
    }
    if (directory != null && !directory.equals("")) {
      directory = directory.trim();
      if (!directory.endsWith("/") || !directory.endsWith("\\")) {
        directory = directory + File.separatorChar;
      }
    }
    return directory;
  }

  public String getDirectory(String key) {
    return getDirectory(key, null);
  }

  public TreeSet getStringTreeSet(String key) {
    return getStringTreeSet(key, ";");
  }

  public TreeSet getStringTreeSet(String key, String seperator) {
    String value = getString(key, "");
    String[] values = value.split(seperator);
    TreeSet<String> ret = new TreeSet<>();
    Collections.addAll(ret, values);
    return ret;
  }

  public String[] getStringArray(String key) {
    return getStringArray(key, ";");
  }

  public String[] getStringArray(String key, String seperator) {
    String value = getString(key, "");
    return value.split(seperator);
  }

  public String getString(String key) {
    return getString(key, null);
  }

  public String getString(String key, String defaultString) {
    String value = getValue(key);
    return value != null ? value : defaultString;
  }

  public int getInt(String key) {
    String value = getString(key);
    if (value == null) {
      throw new NumberFormatException("null value found for key:" + key);
    }
    return Integer.parseInt(value);
  }

  public int getInt(String key, int defaultInt) throws NumberFormatException {
    int ret = defaultInt;
    try {
      String value = getString(key);
      ret = Integer.parseInt(value);
    } catch (Exception e) {
    }
    return ret;
  }

  public synchronized void clear() {
    for (ConfigurationGroup cg : this.groupLookup.values()) {
      cg.deleteConfigurationMessageListener(this);
    }
    this.groupLookup.clear();
  }

  public String[] getNames() {
    return new TreeSet<>(this.groupLookup.keySet()).toArray(new String[0]);
  }

  public int size() {
    return this.groupLookup.size();
  }

  private String getValue(String name) {
    String out = null;
    ConfigurationEntry entry = getEntry(name);
    if (entry != null) {
      out = entry.getValueString();
    }
    return out;
  }

  public ConfigurationGroup getLocalGroup(String groupName) {
    return this.groupLookup.get(groupName);
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
      ConfigurationGroup subGroup = getGroup(pathBits[0]);
      if (subGroup != null) {
        ret = subGroup.getEntry(path.substring(pathBits[0].length() + 1));
      }
    } else {
      int length = pathBits.length;
    }
    return ret;
  }

  public String toString() {
    StringBuffer out = new StringBuffer(128);
    Collection<ConfigurationGroup> c = this.groupLookup.values();
    for (ConfigurationGroup vNode : c) {
      out.append(vNode.toString());
    }
    return out.toString();
  }

  public void toFile(String fileName)
      throws TransformerException, ParserConfigurationException, DOMException, IOException {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    try {
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document document = builder.newDocument();
      Element root = document.createElement(ELEMENT_NAME);
      document.appendChild(root);
      root.setAttribute(ATTRIBUTE_FORMAT_NAME, this.formatName);
      root.appendChild(document.createTextNode("\n"));
      ConfigurationGroup[] valueNodeArray = getGroupArray();
      for (ConfigurationGroup configurationGroup : valueNodeArray) {
        root.appendChild(document.createTextNode("\t"));
        configurationGroup.addElement(document, root, 1);
        root.appendChild(document.createTextNode("\n"));
      }
      try {
        Source source = new DOMSource(document);
        File outFile = new File(fileName);
        if (!outFile.exists()) {
          File parentPath = outFile.getParentFile();
          parentPath.mkdirs();
        }
        Result result = new StreamResult(fileName);
        Transformer xformer = TransformerFactory.newInstance().newTransformer();
        xformer.transform(source, result);
      } catch (TransformerConfigurationException ex) {
        ex.printStackTrace();
      } catch (TransformerException ex2) {
        ex2.printStackTrace();
      }
    } catch (ParserConfigurationException ex3) {
      ex3.printStackTrace();
    }
  }
}
