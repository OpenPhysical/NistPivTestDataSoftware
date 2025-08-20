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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.swing.*;
import java.util.Vector;

/**
 * Abstract class that serves as a data management component, extending {@link JPanel}, and
 * providing functionality to handle output messages through the {@link OutputMessageSender}
 * interface. This class is designed to be extended by specific implementations to manage various
 * types of data.
 *
 * <p>Key features: - Supports the management of output message listeners and message distribution.
 * - Provides abstract methods to be implemented for specific data handling needs.
 */
/* loaded from: JPIV Test Data Generator.jar:gov/nist/piv/auto_create/DataManager.class */
public abstract class DataManager extends JPanel implements OutputMessageSender {
  public static String TAG_DATA_MANAGER = "DataManager";
  public static String ATTRIBUTE_TYPE = "type";
  public static String ATTRIBUTE_NAME = "name";
  private final Vector<OutputMessageListener> outputListeners = new Vector<>();
  private static final long serialVersionUID = 1;

  public abstract void loadValues(Node node);

  public abstract void addElement(Document document, Element element, int i);

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
}
