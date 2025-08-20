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

package gov.nist.piv.auto_create;

import java.io.StringWriter;
import java.util.HashMap;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/* loaded from: JPIV Test Data Generator.jar:gov/nist/piv/auto_create/XMLUtils.class */
public enum XMLUtils {
  ;

  public static final HashMap<String, String> getAttributes(Node node) throws DOMException {
    HashMap<String, String> hm = new HashMap<>();
    NamedNodeMap nm = node.getAttributes();
    int length = nm.getLength();
    for (int i = 0; i < length; i++) {
      String name = nm.item(i).getNodeName();
      String value = nm.item(i).getNodeValue();
      hm.put(name, value);
    }
    return hm;
  }

  public static String xmlToString(Node node)
      throws TransformerException, TransformerFactoryConfigurationError {
    try {
      Source source = new DOMSource(node);
      StringWriter stringWriter = new StringWriter();
      Result result = new StreamResult(stringWriter);
      TransformerFactory factory = TransformerFactory.newInstance();
      Transformer transformer = factory.newTransformer();
      transformer.transform(source, result);
      return stringWriter.getBuffer().toString();
    } catch (TransformerConfigurationException e) {
      e.printStackTrace();
      return null;
    } catch (TransformerException e2) {
      e2.printStackTrace();
      return null;
    }
  }
}
