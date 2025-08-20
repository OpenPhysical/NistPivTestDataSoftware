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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

/* loaded from: JPIV Test Data Generator.jar:gov/nist/piv/auto_create/MainPane.class */
public class MainPane extends JScrollPane
    implements SampleGeneratorEventListener, OutputMessageSender, OutputMessageListener {
  private static final long serialVersionUID = 1;
  public static String ELEMENT_NAME = "auto_create_options";
  public static String ATTRIBUTE_NAME = "name";
  public static String TAG_MAIN_PANE = "MainPane";
  public static String ATTRIBUTE_OUTPUT_DIRECTORY = "OutputDirectory";
  public static String ATTRIBUTE_SAMPLES = "SampleCount";
  private final Vector<OutputMessageListener> outputListeners = new Vector<>();
  private final SampleGenerator sampleGenerator = new SampleGenerator();
  private JPanel jPanelMain = null;
  private JButton jButtonSave = null;
  private JButton jButtonOpen = null;
  private JLabel jLabelOutputDirectory = null;
  private JPanel jPanelOutputDirectory = null;
  private JButton jButtonSelectOutputDirectory = null;
  private JTextField jTextFieldOutputDirectory = null;
  private JLabel jLabelConfiguration = null;
  private JPanel jPanelConfiguration = null;
  private JLabel jLabelSampleSize = null;
  private JPanel jPanelSampleSize = null;
  private JTextField jTextFieldSampleSize = null;
  private JButton jButtonCreateSamples = null;
  private JPanel jPanelCreateSamples = null;
  private JButton jButtonCreateSamplesCancel = null;

  public MainPane() {
    initialize();
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

  private void initialize() {
    setViewportView(getJPanelMain());
    // Fixed: Don't override viewport layout - let JScrollPane handle it
    // FlowLayout fl = new FlowLayout();
    // fl.setAlignment(0);
    // getViewport().setLayout(fl);
  }

  private JPanel getJPanelMain() {
    if (this.jPanelMain == null) {
      GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
      gridBagConstraints21.gridx = 1;
      gridBagConstraints21.anchor = 17;
      gridBagConstraints21.gridy = 2;
      GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
      gridBagConstraints7.gridx = 1;
      gridBagConstraints7.anchor = 17;
      gridBagConstraints7.insets = new Insets(5, 5, 5, 5);
      gridBagConstraints7.gridy = 1;
      GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
      gridBagConstraints6.gridx = 0;
      gridBagConstraints6.gridy = 1;
      this.jLabelSampleSize = new JLabel();
      this.jLabelSampleSize.setText("Samples to Create:");
      GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
      gridBagConstraints4.gridx = 1;
      gridBagConstraints4.anchor = 17;
      gridBagConstraints4.gridy = 3;
      GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
      gridBagConstraints5.gridx = 0;
      gridBagConstraints5.gridy = 3;
      this.jLabelConfiguration = new JLabel();
      this.jLabelConfiguration.setText("Configuration:");
      GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
      gridBagConstraints3.gridx = 1;
      gridBagConstraints3.fill = 2;
      gridBagConstraints3.gridwidth = 1;
      gridBagConstraints3.insets = new Insets(0, 0, 0, 0);
      gridBagConstraints3.gridy = 0;
      GridBagConstraints gridBagConstraints = new GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.gridy = 0;
      this.jLabelOutputDirectory = new JLabel();
      this.jLabelOutputDirectory.setText("Output Directory:");
      this.jPanelMain = new JPanel();
      this.jPanelMain.setLayout(new GridBagLayout());
      this.jPanelMain.add(this.jLabelOutputDirectory, gridBagConstraints);
      this.jPanelMain.add(getJPanelOutputDirectory(), gridBagConstraints3);
      this.jPanelMain.add(this.jLabelConfiguration, gridBagConstraints5);
      this.jPanelMain.add(getJPanelConfiguration(), gridBagConstraints4);
      this.jPanelMain.add(this.jLabelSampleSize, gridBagConstraints6);
      this.jPanelMain.add(getJPanelSampleSize(), gridBagConstraints7);
      this.jPanelMain.add(getJPanelCreateSamples(), gridBagConstraints21);
    }
    return this.jPanelMain;
  }

  private JButton getJButtonSave() {
    if (this.jButtonSave == null) {
      this.jButtonSave = new JButton();
      this.jButtonSave.setText("Save to Data File...");
      this.jButtonSave.setToolTipText("Save configuration data to file");
      this.jButtonSave.addActionListener(
          new ActionListener() { // from class: gov.nist.piv.auto_create.MainPane.1
            public void actionPerformed(ActionEvent e) {
              JFileChooser fileChooser = new JFileChooser();
              String curDir = System.getProperty("user.dir");
              fileChooser.setSelectedFile(
                  new File(curDir + File.separator + "auto_create_options.xml"));
              if (fileChooser.showSaveDialog(MainPane.this) == 0) {
                try {
                  MainPane.this.toFile(fileChooser.getSelectedFile());
                } catch (IOException e1) {
                  e1.printStackTrace();
                  JOptionPane.showMessageDialog(
                      MainPane.this, "Unable to save auto create options.", "Save Error", 64);
                } catch (Exception ex) {
                  ex.printStackTrace();
                  JOptionPane.showMessageDialog(
                      MainPane.this, "Error saving: " + ex.getMessage(), "Save Error", 64);
                }
              }
            }
          });
    }
    return this.jButtonSave;
  }

  public void toFile(File outFile)
      throws XPathExpressionException,
          TransformerException,
          ParserConfigurationException,
          DOMException,
          IOException {
    Document document = toDocument(true);
    if (document != null) {
      try {
        Source source = new DOMSource(document);
        if (!outFile.exists()) {
          File parentPath = outFile.getParentFile();
          parentPath.mkdirs();
        }
        Result result = new StreamResult(outFile);
        Transformer xformer = TransformerFactory.newInstance().newTransformer();
        xformer.transform(source, result);
        return;
      } catch (TransformerConfigurationException ex) {
        ex.printStackTrace();
        return;
      } catch (TransformerException ex2) {
        ex2.printStackTrace();
        return;
      }
    }
    sendOutputMessage("Unable to save configuration.");
  }

  public Document toDocument(boolean removePasswords)
      throws XPathExpressionException, ParserConfigurationException, DOMException {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    Document document = null;
    try {
      DocumentBuilder builder = factory.newDocumentBuilder();
      document = builder.newDocument();
      Element root = document.createElement(ELEMENT_NAME);
      document.appendChild(root);
      root.setAttribute(ATTRIBUTE_NAME, "document");
      root.appendChild(document.createTextNode("\n"));
      root.appendChild(document.createTextNode("\t"));
      addElement(document, root, 1);
      root.appendChild(document.createTextNode("\n"));
      JTabbedPane tabbedPane = (JTabbedPane) getParent();
      int tabCount = tabbedPane.getTabCount();
      for (int tab = 0; tab < tabCount; tab++) {
        Component componentAt = tabbedPane.getComponentAt(tab);
        if (componentAt instanceof DataPane) {
          DataPane dataPane = (DataPane) componentAt;
          root.appendChild(document.createTextNode("\t"));
          dataPane.addElement(document, root, 1);
          root.appendChild(document.createTextNode("\n"));
        }
      }
      if (removePasswords) {
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xpath = xPathFactory.newXPath();
        try {
          XPathExpression expr = xpath.compile("//*[@isPassword='true']");
          NodeList nodes = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
          if (nodes != null && nodes.getLength() > 0) {
            for (int i = 0; i < nodes.getLength(); i++) {
              Node parentNode = nodes.item(i).getParentNode();
              parentNode.removeChild(nodes.item(i));
            }
          }
        } catch (XPathExpressionException e) {
          sendOutputMessage("Unable to remove passwords from document.");
          e.printStackTrace();
        }
      }
    } catch (ParserConfigurationException ex) {
      ex.printStackTrace();
    }
    return document;
  }

  public void addElement(Document document, Element parent, int level) throws DOMException {
    String baseIndent = "";
    for (int i = 0; i < level; i++) {
      baseIndent = baseIndent + "\t";
    }
    Element root = document.createElement(TAG_MAIN_PANE);
    parent.appendChild(root);
    root.setAttribute(ATTRIBUTE_SAMPLES, getJTextFieldSampleSize().getText());
    root.setAttribute(ATTRIBUTE_OUTPUT_DIRECTORY, getJTextFieldOutputDirectory().getText());
    root.appendChild(document.createTextNode("\n"));
    root.appendChild(document.createTextNode(baseIndent));
  }

  public int getSampleCount() {
    return Integer.parseInt(getJTextFieldSampleSize().getText());
  }

  private JButton getJButtonOpen() {
    if (this.jButtonOpen == null) {
      this.jButtonOpen = new JButton();
      this.jButtonOpen.setText("Open Data File...");
      this.jButtonOpen.setToolTipText("Open configuration data file");
      this.jButtonOpen.addActionListener(
          new ActionListener() { // from class: gov.nist.piv.auto_create.MainPane.2
            public void actionPerformed(ActionEvent e) {
              String curDir = System.getProperty("user.dir");
              JFileChooser fileChooser = new JFileChooser(curDir);
              if (fileChooser.showOpenDialog(MainPane.this) != 0) {
                return;
              }
              try {
                MainPane.this.fromFile(fileChooser.getSelectedFile());
              } catch (FileNotFoundException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(
                    MainPane.this,
                    "Unable to open auto configuration options file.",
                    "Open Error",
                    64);
              } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(
                    MainPane.this, "Error opening file: " + ex.getMessage(), "Open Error", 64);
              }
            }
          });
    }
    return this.jButtonOpen;
  }

  public void fromFile(File inFile)
      throws DOMException, ClassNotFoundException, FileNotFoundException {
    try {
      loadData(openDocument(new FileInputStream(inFile)).getFirstChild());
    } catch (ParserConfigurationException | SAXException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void loadData(Node node) throws DOMException, ClassNotFoundException {
    if (node == null || !node.getNodeName().equals(ELEMENT_NAME)) {
      throw new RuntimeException("MainPane: MainPane not created with valid node");
    }
    AutoCreateTabPane tabbedPane = (AutoCreateTabPane) getParent();
    int tabCount = tabbedPane.getTabCount();
    for (int tab = tabCount - 1; tab >= 0; tab--) {
      Component component = tabbedPane.getComponentAt(tab);
      if (component instanceof DataPane) {
        tabbedPane.remove(tab);
      }
    }
    XMLUtils.getAttributes(node);
    Node firstChild = node.getFirstChild();
    while (true) {
      Node child = firstChild;
      if (child != null) {
        String type = child.getNodeName();
        if (type.equals(DataPane.TAG_DATA_PANE)) {
          String dpClassName = XMLUtils.getAttributes(child).get(DataPane.ATTRIBUTE_TYPE);
          try {
            Class<?> dpClass = Class.forName(dpClassName);
            DataPane dataPane = (DataPane) dpClass.getDeclaredConstructor().newInstance();
            dataPane.loadData(child);
            dataPane.addOutputMessageListener(tabbedPane);
            tabbedPane.add(dataPane.getPaneName(), dataPane);
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
        } else if (type.equals(TAG_MAIN_PANE)) {
          HashMap<String, String> hm = XMLUtils.getAttributes(child);
          getJTextFieldSampleSize().setText(hm.get(ATTRIBUTE_SAMPLES));
          getJTextFieldOutputDirectory().setText(hm.get(ATTRIBUTE_OUTPUT_DIRECTORY));
        }
        firstChild = child.getNextSibling();
      } else {
        return;
      }
    }
  }

  private JPanel getJPanelOutputDirectory() {
    if (this.jPanelOutputDirectory == null) {
      this.jPanelOutputDirectory = new JPanel();
      this.jPanelOutputDirectory.setLayout(new FlowLayout());
      this.jPanelOutputDirectory.add(getJTextFieldOutputDirectory(), null);
      this.jPanelOutputDirectory.add(getJButtonSelectOutputDirectory(), null);
    }
    return this.jPanelOutputDirectory;
  }

  private JButton getJButtonSelectOutputDirectory() {
    if (this.jButtonSelectOutputDirectory == null) {
      this.jButtonSelectOutputDirectory = new JButton();
      this.jButtonSelectOutputDirectory.setText("Choose...");
      this.jButtonSelectOutputDirectory.addActionListener(
          new ActionListener() { // from class: gov.nist.piv.auto_create.MainPane.3
            public void actionPerformed(ActionEvent e) {
              String curDir = System.getProperty("user.dir");
              JFileChooser fileChooser = new JFileChooser(curDir);
              fileChooser.setFileSelectionMode(1);
              fileChooser.setSelectedFile(
                  new File(MainPane.this.getJTextFieldOutputDirectory().getText()));
              if (fileChooser.showDialog(MainPane.this, "Select") == 0) {
                MainPane.this
                    .getJTextFieldOutputDirectory()
                    .setText(fileChooser.getSelectedFile().getAbsolutePath());
              }
            }
          });
    }
    return this.jButtonSelectOutputDirectory;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public JTextField getJTextFieldOutputDirectory() {
    if (this.jTextFieldOutputDirectory == null) {
      this.jTextFieldOutputDirectory = new JTextField();
      this.jTextFieldOutputDirectory.setText("./generated_data");
      this.jTextFieldOutputDirectory.setColumns(20);
    }
    return this.jTextFieldOutputDirectory;
  }

  private JPanel getJPanelConfiguration() {
    if (this.jPanelConfiguration == null) {
      GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
      gridBagConstraints1.insets = new Insets(5, 5, 5, 5);
      gridBagConstraints1.gridy = -1;
      gridBagConstraints1.gridx = -1;
      GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
      gridBagConstraints2.insets = new Insets(5, 5, 5, 5);
      gridBagConstraints2.gridy = -1;
      gridBagConstraints2.gridx = -1;
      this.jPanelConfiguration = new JPanel();
      this.jPanelConfiguration.setLayout(new GridBagLayout());
      this.jPanelConfiguration.add(getJButtonOpen(), gridBagConstraints1);
      this.jPanelConfiguration.add(getJButtonSave(), gridBagConstraints2);
    }
    return this.jPanelConfiguration;
  }

  private JPanel getJPanelSampleSize() {
    if (this.jPanelSampleSize == null) {
      this.jPanelSampleSize = new JPanel();
      this.jPanelSampleSize.setLayout(new BorderLayout());
      this.jPanelSampleSize.add(getJTextFieldSampleSize(), "West");
    }
    return this.jPanelSampleSize;
  }

  private JTextField getJTextFieldSampleSize() {
    if (this.jTextFieldSampleSize == null) {
      this.jTextFieldSampleSize = new JTextField("1");
      this.jTextFieldSampleSize.setColumns(4);
      this.jTextFieldSampleSize.setInputVerifier(
          new InputVerifier() { // from class: gov.nist.piv.auto_create.MainPane.4
            public boolean verify(JComponent comp) throws NumberFormatException {
              boolean returnValue = true;
              JTextField textField = (JTextField) comp;
              String content = textField.getText();
              if (content.length() != 0) {
                try {
                  Integer.parseInt(textField.getText());
                } catch (NumberFormatException e) {
                  returnValue = false;
                }
              }
              return returnValue;
            }
          });
    }
    return this.jTextFieldSampleSize;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public JButton getJButtonCreateSamples() {
    if (this.jButtonCreateSamples == null) {
      this.jButtonCreateSamples = new JButton();
      this.jButtonCreateSamples.setText("Create Samples");
      this.jButtonCreateSamples.addActionListener(
          new ActionListener() { // from class: gov.nist.piv.auto_create.MainPane.5
            public void actionPerformed(ActionEvent e) {
              MainPane.this.getJButtonCreateSamples().setEnabled(false);
              MainPane.this.createSamples();
            }
          });
    }
    return this.jButtonCreateSamples;
  }

  public synchronized void createSamples() {
    try {
      String outDirString = getJTextFieldOutputDirectory().getText();
      String curDir = System.getProperty("user.dir");
      File outDir = new File(curDir, outDirString);
      outDir.mkdirs();
      this.sampleGenerator.addOutputMessageListener(this);
      this.sampleGenerator.setInput(toDocument(false));
      this.sampleGenerator.addEventListener(this);
      this.sampleGenerator.setOutputDirectory(outDir);
      this.sampleGenerator.setSamplesToCreate(getSampleCount());
      Thread thread = new Thread(this.sampleGenerator);
      thread.start();
      getJButtonCreateSamplesCancel().setEnabled(true);
    } catch (Exception e) {
      e.printStackTrace();
      sendOutputMessage("Error creating samples: " + e.getMessage());
    }
  }

  @Override // gov.nist.piv.auto_create.SampleGeneratorEventListener
  public void processSampleGeneratorEvent(int eventCode) {
    switch (eventCode) {
      case 0:
        getJButtonCreateSamplesCancel().setEnabled(false);
        getJButtonCreateSamples().setEnabled(true);
        break;
    }
  }

  private JPanel getJPanelCreateSamples() {
    if (this.jPanelCreateSamples == null) {
      GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
      gridBagConstraints8.anchor = 17;
      gridBagConstraints8.gridx = -1;
      gridBagConstraints8.gridy = -1;
      gridBagConstraints8.insets = new Insets(5, 5, 5, 5);
      this.jPanelCreateSamples = new JPanel();
      this.jPanelCreateSamples.setLayout(new FlowLayout());
      this.jPanelCreateSamples.add(getJButtonCreateSamples(), null);
      this.jPanelCreateSamples.add(getJButtonCreateSamplesCancel(), null);
    }
    return this.jPanelCreateSamples;
  }

  private JButton getJButtonCreateSamplesCancel() {
    if (this.jButtonCreateSamplesCancel == null) {
      this.jButtonCreateSamplesCancel = new JButton();
      this.jButtonCreateSamplesCancel.setText("Cancel");
      this.jButtonCreateSamplesCancel.setEnabled(false);
      this.jButtonCreateSamplesCancel.addActionListener(
          new ActionListener() { // from class: gov.nist.piv.auto_create.MainPane.6
            public void actionPerformed(ActionEvent e) {
              MainPane.this.sampleGenerator.setAbort();
              MainPane.this.jButtonCreateSamplesCancel.setEnabled(false);
            }
          });
    }
    return this.jButtonCreateSamplesCancel;
  }
}
