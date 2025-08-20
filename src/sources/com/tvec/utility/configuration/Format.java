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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.TreeSet;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/configuration/Format.class */
public class Format {
  public static final String ELEMENT_NAME = "format";
  private static final String ATTRIBUTE_NAME = "name";
  private static final String CHILD_ELEMENT_DISPLAY_NAME = "display_name";
  private final HashMap<String, FormatGroup> groupLookup;
  private String name;
  private String displayName;

  public static void main(String[] args)
      throws ParserConfigurationException, SAXException, IOException {
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document document =
          builder.parse("C:/data/projects/smart card/GSCIS_files/java_files/GSCISTestConfig.xml");
      Format cfNodes = new Format(document.getFirstChild());
      String[] names = cfNodes.getGroupNames();
      for (String str : names) {
        FormatGroup cfNode = cfNodes.getGroup(str);
        System.out.println(cfNode.toString());
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (FactoryConfigurationError e2) {
      e2.printStackTrace();
    } catch (ParserConfigurationException e3) {
      e3.printStackTrace();
    } catch (SAXException e4) {
      e4.printStackTrace();
    }
  }

  private static Document openDocument(InputStream input)
      throws ParserConfigurationException, SAXException, IOException {
    Document document = null;
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setCoalescing(true);
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

  public Format() {
    this.groupLookup = new HashMap<>();
  }

  public Format(String xmlFile) throws ParserConfigurationException, SAXException, IOException {
    this(new FileInputStream(xmlFile));
  }

  public Format(File xmlFile) throws ParserConfigurationException, SAXException, IOException {
    this(new FileInputStream(xmlFile));
  }

  public Format(InputStream input) throws ParserConfigurationException, SAXException, IOException {
    this(openDocument(input).getFirstChild());
  }

  public Format(Node node) throws DOMException {
    this.groupLookup = new HashMap<>();
    addNodes(node);
  }

  public void clear() {
    this.groupLookup.clear();
  }

  public void addNodes(String xmlFile)
      throws DOMException, ParserConfigurationException, SAXException, IOException {
    addNodes(new FileInputStream(xmlFile));
  }

  public void addNodes(InputStream input)
      throws DOMException, ParserConfigurationException, SAXException, IOException {
    addNodes(openDocument(input).getFirstChild());
  }

  public void addNodes(Node node) throws DOMException {
    if (node == null || !node.getNodeName().equals(ELEMENT_NAME)) {
      throw new RuntimeException("Format: Format not created with valid node");
    }
    HashMap hm = XMLUtils.getAttributes(node);
    this.name = (String) hm.get(ATTRIBUTE_NAME);
    Node firstChild = node.getFirstChild();
    while (true) {
      Node child = firstChild;
      if (child != null) {
        String type = child.getNodeName();
        if (type.equals("group")) {
          FormatGroup fg = new FormatGroup(child);
          this.groupLookup.put(fg.getName(), fg);
        } else if (type.equals(CHILD_ELEMENT_DISPLAY_NAME)) {
          Node defaultChild = child.getFirstChild();
          this.displayName = defaultChild.getNodeValue().trim();
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

  public String getDisplayName() {
    return this.displayName;
  }

  public String[] getGroupNames() {
    return new TreeSet<>(this.groupLookup.keySet()).toArray(new String[0]);
  }

  public FormatGroup[] getGroupArray() {
    return this.groupLookup.values().toArray(new FormatGroup[0]);
  }

  public FormatGroup getGroup(String name) {
    return this.groupLookup.get(name);
  }

  public int getGroupCount() {
    return this.groupLookup.size();
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
    } else {
      int length = pathBits.length;
    }
    return ret;
  }

  public String toString() {
    StringBuffer out = new StringBuffer(128);
    Collection<FormatGroup> c = this.groupLookup.values();
    for (FormatGroup cfNode : c) {
      out.append(cfNode.toString());
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
      root.setAttribute(ATTRIBUTE_NAME, this.name);
      root.appendChild(document.createTextNode("\n"));
      Element child = document.createElement(CHILD_ELEMENT_DISPLAY_NAME);
      child.appendChild(document.createTextNode(this.displayName));
      root.appendChild(document.createTextNode("\n"));
      FormatGroup[] valueNodeArray = getGroupArray();
      for (FormatGroup formatGroup : valueNodeArray) {
        root.appendChild(document.createTextNode("\t"));
        root.appendChild(formatGroup.getElement(1));
        root.appendChild(document.createTextNode("\n"));
      }
      try {
        Source source = new DOMSource(document);
        Result result = new StreamResult(fileName);
        Transformer xformer = TransformerFactory.newInstance().newTransformer();
        xformer.transform(source, result);
      } catch (TransformerException ex) {
        ex.printStackTrace();
      }
    } catch (ParserConfigurationException ex3) {
      ex3.printStackTrace();
    }
  }
}
