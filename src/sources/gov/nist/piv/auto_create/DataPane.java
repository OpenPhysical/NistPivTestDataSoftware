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

import com.tvec.utility.displays.output.OutputMessage;
import com.tvec.utility.displays.output.OutputMessageListener;
import com.tvec.utility.displays.output.OutputMessageSender;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/* loaded from: JPIV Test Data Generator.jar:gov/nist/piv/auto_create/DataPane.class */
public class DataPane extends JScrollPane implements OutputMessageSender, OutputMessageListener {
  private final Vector<DataManager> dataManagers;
  private String paneName;
  public static String ATTRIBUTE_NAME = "name";
  public static String ATTRIBUTE_TYPE = "type";
  public static String TAG_DATA_PANE = "DataPane";
  private final Vector<OutputMessageListener> outputListeners;
  private GridBagConstraints gridBagConstraints;
  private static final long serialVersionUID = 1;

  public DataPane(String xmlName) {
    this();
    setPaneName(xmlName);
  }

  public DataPane() {
    this.dataManagers = new Vector<>();
    this.outputListeners = new Vector<>();
    this.gridBagConstraints = null;
    JPanel mainPanel = new JPanel();
    this.gridBagConstraints = new GridBagConstraints();
    this.gridBagConstraints.anchor = 21;
    this.gridBagConstraints.gridx = 0;
    this.gridBagConstraints.gridy = 0;
    mainPanel.setLayout(new GridBagLayout());
    setViewportView(mainPanel);
    // Fixed: Don't override viewport layout - let JScrollPane handle it
    // FlowLayout fl = new FlowLayout();
    // fl.setAlignment(0);
    // getViewport().setLayout(fl);
  }

  @Override // com.tvec.utility.displays.output.OutputMessageSender
  public void addOutputMessageListener(OutputMessageListener listener) {
    this.outputListeners.add(listener);
  }

  @Override // com.tvec.utility.displays.output.OutputMessageSender
  public void deleteOutputMessageListener(OutputMessageListener listener) {
    this.outputListeners.remove(listener);
  }

  public void sendOutputMessage(String message) {
    sendOutputMessage(new OutputMessage(message));
  }

  @Override // com.tvec.utility.displays.output.OutputMessageSender
  public void sendOutputMessage(OutputMessage om) {
    for (int i = 0; i < this.outputListeners.size(); i++) {
      this.outputListeners.elementAt(i).handleMessage(this, om);
    }
  }

  @Override // com.tvec.utility.displays.output.OutputMessageListener
  public void handleMessage(OutputMessageSender oms, OutputMessage om) {
    sendOutputMessage(om);
  }

  public void setPaneName(String xmlName) {
    this.paneName = xmlName;
  }

  public String getPaneName() {
    return this.paneName;
  }

  public void add(DataManager dataManager) {
    this.dataManagers.add(dataManager);
    dataManager.addOutputMessageListener(this);
    JPanel mainPanel = (JPanel) getViewport().getView();
    mainPanel.add(dataManager, this.gridBagConstraints);
    this.gridBagConstraints.gridy++;
  }

  public DataManager[] getDataManagers() {
    return this.dataManagers.toArray(new DataManager[0]);
  }

  public void loadData(Node node) throws DOMException, ClassNotFoundException {
    if (node == null || !node.getNodeName().equals(TAG_DATA_PANE)) {
      throw new RuntimeException("DataPane: DataPane not created with valid node");
    }
    JPanel mainPanel = (JPanel) getViewport().getView();
    mainPanel.removeAll();
    this.gridBagConstraints.gridy = 0;
    for (int i = 0; i < this.dataManagers.size(); i++) {
      this.dataManagers.elementAt(i).deleteOutputMessageListener(this);
    }
    this.dataManagers.clear();
    HashMap<String, String> hm = XMLUtils.getAttributes(node);
    setPaneName(hm.get(ATTRIBUTE_NAME));
    Node firstChild = node.getFirstChild();
    while (true) {
      Node child = firstChild;
      if (child != null) {
        String type = child.getNodeName();
        if (type.equals(DataManager.TAG_DATA_MANAGER)) {
          HashMap<String, String> hm2 = XMLUtils.getAttributes(child);
          String dmClassName = hm2.get(DataManager.ATTRIBUTE_TYPE);
          try {
            Class<?> dmClass = Class.forName(dmClassName);
            DataManager dataManager = (DataManager) dmClass.getDeclaredConstructor().newInstance();
            add(dataManager);
            dataManager.loadValues(child);
          } catch (ClassNotFoundException e) {
            e.printStackTrace();
          } catch (IllegalAccessException e2) {
            e2.printStackTrace();
          } catch (InstantiationException e3) {
            e3.printStackTrace();
          } catch (NoSuchMethodException e4) {
            e4.printStackTrace();
          } catch (java.lang.reflect.InvocationTargetException e5) {
            e5.printStackTrace();
          }
        }
        firstChild = child.getNextSibling();
      } else {
        return;
      }
    }
  }

  public void addElement(Document document, Element parent, int level) throws DOMException {
    String baseIndent = "";
    for (int i = 0; i < level; i++) {
      baseIndent = baseIndent + "\t";
    }
    Element root = document.createElement(TAG_DATA_PANE);
    parent.appendChild(root);
    root.setAttribute(ATTRIBUTE_NAME, getPaneName());
    root.setAttribute(ATTRIBUTE_TYPE, getClass().getName());
    DataManager[] dataManagers = getDataManagers();
    for (DataManager dataManager : dataManagers) {
      root.appendChild(document.createTextNode("\n"));
      root.appendChild(document.createTextNode(baseIndent + "\t"));
      dataManager.addElement(document, root, level + 1);
    }
    root.appendChild(document.createTextNode("\n"));
    root.appendChild(document.createTextNode(baseIndent));
  }
}
