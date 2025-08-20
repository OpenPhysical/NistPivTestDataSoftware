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

package com.tvec.utility.displays.output;

import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/displays/output/Output.class */
public class Output extends JPanel implements OutputMessageListener, ErrorMessageListener {
  private static final long serialVersionUID = 1;
  private final JTabbedPane tabbedPane;
  private final OutputPanel outputPanel;
  private final ErrorPanel errorPanel;

  public Output() {
    super(new GridLayout(0, 1));
    this.tabbedPane = new JTabbedPane(3);
    this.outputPanel = new OutputPanel();
    this.tabbedPane.add("Output", this.outputPanel);
    this.errorPanel = new ErrorPanel();
    this.tabbedPane.add("Error", this.errorPanel);
    add(this.tabbedPane);
  }

  @Override // com.tvec.utility.displays.output.OutputMessageListener
  public void handleMessage(OutputMessageSender sender, OutputMessage om) {
    this.outputPanel.handleMessage(sender, om);
  }

  @Override // com.tvec.utility.displays.output.ErrorMessageListener
  public void handleMessage(ErrorMessageSender sender, ErrorMessage em) {
    this.errorPanel.handleMessage(sender, em);
  }
}
