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

package com.tvec.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/Logger.class */
public class Logger {
  private static final String LABEL = "Logger";
  private static PrintWriter resultFile;
  private static PrintWriter logFile;

  private Logger() {
    resultFile = new PrintWriter(System.out);
    logFile = new PrintWriter(System.out);
  }

  public static final void setLogFile(String fileName) {
    try {
      File tFile = new File(fileName);
      File path = tFile.getParentFile();
      path.mkdirs();
      OutputStream tStream = new FileOutputStream(tFile);
      logFile = new PrintWriter(tStream, true);
    } catch (Exception e) {
      e.printStackTrace(System.out);
      throw new RuntimeException("Unable to initialize logger");
    }
  }

  public static final void setResultsFile(String fileName) {
    try {
      File tFile = new File(fileName);
      File path = tFile.getParentFile();
      path.mkdirs();
      OutputStream tStream = new FileOutputStream(tFile);
      resultFile = new PrintWriter(tStream, true);
    } catch (Exception e) {
      e.printStackTrace(System.out);
      throw new RuntimeException("Logger: Error occurred setting result file");
    }
  }

  public static void logException(String name, Exception e) {
    logFile.println("Exception in: " + name);
    e.printStackTrace(logFile);
  }

  public static void log(String name, String text) {
    logFile.println(name + ": " + text);
  }

  public static void logResult(String s) {
    resultFile.println(s);
  }
}
