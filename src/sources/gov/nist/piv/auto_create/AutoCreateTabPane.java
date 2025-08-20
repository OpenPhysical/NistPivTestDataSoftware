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
import java.io.File;
import java.util.Vector;
import javax.swing.JTabbedPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/* loaded from: JPIV Test Data Generator.jar:gov/nist/piv/auto_create/AutoCreateTabPane.class */
public class AutoCreateTabPane extends JTabbedPane
    implements OutputMessageSender, OutputMessageListener {
  private static final long serialVersionUID = 1;
  private static final Logger logger = LogManager.getLogger(AutoCreateTabPane.class);
  private final Vector<OutputMessageListener> outputListeners = new Vector<>();

  public AutoCreateTabPane() {
    MainPane mainPane = new MainPane();
    add(mainPane, "Main");
    mainPane.addOutputMessageListener(this);
    DataPane dataPane = new KeyStorePane();
    dataPane.addOutputMessageListener(this);
    add(dataPane, "KeyStore");
    DataPane dataPane2 = new CHUIDPane();
    dataPane2.addOutputMessageListener(this);
    add(dataPane2, "CHUID");
    DataPane dataPane3 = new CCCPane();
    dataPane3.addOutputMessageListener(this);
    add(dataPane3, "CCC");
    DataPane dataPane4 = new CertificatesPane();
    dataPane4.addOutputMessageListener(this);
    add(dataPane4, "Certificates");
    DataPane dataPane5 = new FingerprintPane();
    dataPane5.addOutputMessageListener(this);
    add(dataPane5, "Fingerprint");
    DataPane dataPane6 = new FacialImagePane();
    dataPane6.addOutputMessageListener(this);
    add(dataPane6, "Facial Image");
    DataPane dataPane7 = new PrintedInformationPane();
    dataPane7.addOutputMessageListener(this);
    add(dataPane7, "Printed Information");

    // Auto-load default configuration if it exists
    loadDefaultConfiguration(mainPane);
  }

  private void loadDefaultConfiguration(MainPane mainPane) {
    try {
      // Try multiple possible locations for the default configuration
      String[] configPaths = {
        "./extra_files/auto_create_options.xml",
        "./auto_create_options.xml",
        "extra_files/auto_create_options.xml",
        "auto_create_options.xml"
      };

      File configFile = null;
      for (String path : configPaths) {
        File candidate = new File(path);
        if (candidate.exists() && candidate.canRead()) {
          configFile = candidate;
          sendOutputMessage("Loading default configuration from: " + path);
          logger.info("Loading default configuration from: {}", path);
          break;
        }
      }

      if (configFile != null) {
        mainPane.fromFile(configFile);
        sendOutputMessage("Default configuration loaded successfully");
        logger.info("Default configuration loaded successfully");
      } else {
        sendOutputMessage(
            "No default configuration file found. Please use 'Open Data File...' to load configuration.");
        logger.warn("No default configuration file found");
      }
    } catch (Exception e) {
      sendOutputMessage("Error loading default configuration: " + e.getMessage());
      logger.error("Error loading default configuration", e);
    }
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
}
