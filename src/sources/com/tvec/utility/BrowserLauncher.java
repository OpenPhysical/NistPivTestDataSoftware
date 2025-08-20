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
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/BrowserLauncher.class */
public enum BrowserLauncher {
  ;
  private static int jvm;
  private static Object browser;
  private static boolean loadedWithoutErrors;
  private static Class mrjFileUtilsClass;
  private static Class mrjOSTypeClass;
  private static Class aeDescClass;
  private static Constructor aeTargetConstructor;
  private static Constructor appleEventConstructor;
  private static Constructor aeDescConstructor;
  private static Method findFolder;
  private static Method getFileCreator;
  private static Method getFileType;
  private static Method openURL;
  private static Method makeOSType;
  private static Method putParameter;
  private static Method sendNoReply;
  private static Object kSystemFolderType;
  private static Integer keyDirectObject;
  private static Integer kAutoGenerateReturnID;
  private static Integer kAnyTransactionID;
  private static Object linkage;
  private static final String JDirect_MacOSX =
      "/System/Library/Frameworks/Carbon.framework/Frameworks/HIToolbox.framework/HIToolbox";
  private static final int MRJ_2_0 = 0;
  private static final int MRJ_2_1 = 1;
  private static final int MRJ_3_0 = 3;
  private static final int MRJ_3_1 = 4;
  private static final int WINDOWS_NT = 5;
  private static final int WINDOWS_9x = 6;
  private static final int OTHER = -1;
  private static final String FINDER_TYPE = "FNDR";
  private static final String FINDER_CREATOR = "MACS";
  private static final String GURL_EVENT = "GURL";
  private static final String FIRST_WINDOWS_PARAMETER = "/c";
  private static final String SECOND_WINDOWS_PARAMETER = "start";
  private static final String THIRD_WINDOWS_PARAMETER = "\"\"";
  private static final String NETSCAPE_REMOTE_PARAMETER = "-remote";
  private static final String NETSCAPE_OPEN_PARAMETER_START = "'openURL(";
  private static final String NETSCAPE_OPEN_PARAMETER_END = ")'";
  private static String errorMessage;

  private static native int ICStart(int[] iArr, int i);

  private static native int ICStop(int[] iArr);

  private static native int ICLaunchURL(
      int i, byte[] bArr, byte[] bArr2, int i2, int[] iArr, int[] iArr2);

  static {
    loadedWithoutErrors = true;
    String osName = System.getProperty("os.name");
    if (osName.startsWith("Mac OS")) {
      String mrjVersion = System.getProperty("mrj.version");
      String majorMRJVersion = mrjVersion.substring(0, 3);
      try {
        double version = Double.valueOf(majorMRJVersion).doubleValue();
        if (version == 2.0d) {
          jvm = 0;
        } else if (version >= 2.1d && version < 3.0d) {
          jvm = 1;
        } else if (version == 3.0d) {
          jvm = 3;
        } else if (version >= 3.1d) {
          jvm = 4;
        } else {
          loadedWithoutErrors = false;
          errorMessage = "Unsupported MRJ version: " + version;
        }
      } catch (NumberFormatException e) {
        loadedWithoutErrors = false;
        errorMessage = "Invalid MRJ version: " + mrjVersion;
      }
    } else if (osName.startsWith("Windows")) {
      if (osName.indexOf("9") != -1) {
        jvm = 6;
      } else {
        jvm = 5;
      }
    } else {
      jvm = -1;
    }
    if (loadedWithoutErrors) {
      try {
        loadedWithoutErrors = loadClasses();
      } catch (Exception e) {
        loadedWithoutErrors = false;
        errorMessage = e.getMessage();
      }
    }
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  private static boolean loadClasses()
      throws NoSuchFieldException,
          NoSuchMethodException,
          ClassNotFoundException,
          SecurityException {
    switch (jvm) {
      case 0:
        try {
          Class aeTargetClass = Class.forName("com.apple.MacOS.AETarget");
          Class osUtilsClass = Class.forName("com.apple.MacOS.OSUtils");
          Class appleEventClass = Class.forName("com.apple.MacOS.AppleEvent");
          Class aeClass = Class.forName("com.apple.MacOS.ae");
          aeDescClass = Class.forName("com.apple.MacOS.AEDesc");
          aeTargetConstructor = aeTargetClass.getDeclaredConstructor(Integer.TYPE);
          appleEventConstructor =
              appleEventClass.getDeclaredConstructor(
                  Integer.TYPE, Integer.TYPE, aeTargetClass, Integer.TYPE, Integer.TYPE);
          aeDescConstructor = aeDescClass.getDeclaredConstructor(String.class);
          makeOSType = osUtilsClass.getDeclaredMethod("makeOSType", String.class);
          putParameter =
              appleEventClass.getDeclaredMethod("putParameter", Integer.TYPE, aeDescClass);
          sendNoReply = appleEventClass.getDeclaredMethod("sendNoReply");
          Field keyDirectObjectField = aeClass.getDeclaredField("keyDirectObject");
          keyDirectObject = (Integer) keyDirectObjectField.get(null);
          Field autoGenerateReturnIDField =
              appleEventClass.getDeclaredField("kAutoGenerateReturnID");
          kAutoGenerateReturnID = (Integer) autoGenerateReturnIDField.get(null);
          Field anyTransactionIDField = appleEventClass.getDeclaredField("kAnyTransactionID");
          kAnyTransactionID = (Integer) anyTransactionIDField.get(null);
          return true;
        } catch (ClassNotFoundException cnfe) {
          errorMessage = cnfe.getMessage();
          return false;
        } catch (IllegalAccessException iae) {
          errorMessage = iae.getMessage();
          return false;
        } catch (NoSuchFieldException nsfe) {
          errorMessage = nsfe.getMessage();
          return false;
        } catch (NoSuchMethodException nsme) {
          errorMessage = nsme.getMessage();
          return false;
        }
      case 1:
        try {
          mrjFileUtilsClass = Class.forName("com.apple.mrj.MRJFileUtils");
          mrjOSTypeClass = Class.forName("com.apple.mrj.MRJOSType");
          Field systemFolderField = mrjFileUtilsClass.getDeclaredField("kSystemFolderType");
          kSystemFolderType = systemFolderField.get(null);
          findFolder = mrjFileUtilsClass.getDeclaredMethod("findFolder", mrjOSTypeClass);
          getFileCreator = mrjFileUtilsClass.getDeclaredMethod("getFileCreator", File.class);
          getFileType = mrjFileUtilsClass.getDeclaredMethod("getFileType", File.class);
          return true;
        } catch (ClassNotFoundException cnfe2) {
          errorMessage = cnfe2.getMessage();
          return false;
        } catch (IllegalAccessException iae2) {
          errorMessage = iae2.getMessage();
          return false;
        } catch (NoSuchFieldException nsfe2) {
          errorMessage = nsfe2.getMessage();
          return false;
        } catch (NoSuchMethodException nsme2) {
          errorMessage = nsme2.getMessage();
          return false;
        } catch (SecurityException se) {
          errorMessage = se.getMessage();
          return false;
        }
      case 2:
      default:
        return true;
      case 3:
        try {
          Class linker = Class.forName("com.apple.mrj.jdirect.Linker");
          Constructor constructor = linker.getConstructor(Class.class);
          linkage = constructor.newInstance(BrowserLauncher.class);
          return true;
        } catch (ClassNotFoundException cnfe3) {
          errorMessage = cnfe3.getMessage();
          return false;
        } catch (IllegalAccessException iae3) {
          errorMessage = iae3.getMessage();
          return false;
        } catch (InstantiationException ie) {
          errorMessage = ie.getMessage();
          return false;
        } catch (NoSuchMethodException nsme3) {
          errorMessage = nsme3.getMessage();
          return false;
        } catch (InvocationTargetException ite) {
          errorMessage = ite.getMessage();
          return false;
        }
      case 4:
        try {
          mrjFileUtilsClass = Class.forName("com.apple.mrj.MRJFileUtils");
          openURL = mrjFileUtilsClass.getDeclaredMethod("openURL", String.class);
          return true;
        } catch (ClassNotFoundException cnfe4) {
          errorMessage = cnfe4.getMessage();
          return false;
        } catch (NoSuchMethodException nsme4) {
          errorMessage = nsme4.getMessage();
          return false;
        }
    }
  }

  private static Object locateBrowser()
      throws IllegalAccessException,
          InstantiationException,
          IllegalArgumentException,
          InvocationTargetException {
    if (browser != null) {
      return browser;
    }
    switch (jvm) {
      case -1:
      case 2:
      default:
        browser = "netscape";
        break;
      case 0:
        try {
          Integer finderCreatorCode = (Integer) makeOSType.invoke(null, FINDER_CREATOR);
          Object aeTarget = aeTargetConstructor.newInstance(finderCreatorCode);
          Integer gurlType = (Integer) makeOSType.invoke(null, GURL_EVENT);
          Object appleEvent =
              appleEventConstructor.newInstance(
                  gurlType, gurlType, aeTarget, kAutoGenerateReturnID, kAnyTransactionID);
          return appleEvent;
        } catch (IllegalAccessException iae) {
          browser = null;
          errorMessage = iae.getMessage();
          return browser;
        } catch (InstantiationException ie) {
          browser = null;
          errorMessage = ie.getMessage();
          return browser;
        } catch (InvocationTargetException ite) {
          browser = null;
          errorMessage = ite.getMessage();
          return browser;
        }
      case 1:
        try {
          File systemFolder = (File) findFolder.invoke(null, kSystemFolderType);
          String[] systemFolderFiles = systemFolder.list();
          for (String str : systemFolderFiles) {
            try {
              File file = new File(systemFolder, str);
              if (file.isFile()) {
                Object fileType = getFileType.invoke(null, file);
                if (FINDER_TYPE.equals(fileType.toString())) {
                  Object fileCreator = getFileCreator.invoke(null, file);
                  if (FINDER_CREATOR.equals(fileCreator.toString())) {
                    browser = file.toString();
                    return browser;
                  }
                } else {
                  continue;
                }
              }
            } catch (IllegalAccessException iae2) {
              browser = null;
              errorMessage = iae2.getMessage();
              return browser;
            } catch (IllegalArgumentException iare) {
              browser = null;
              errorMessage = iare.getMessage();
              return null;
            } catch (InvocationTargetException ite2) {
              browser = null;
              errorMessage =
                  ite2.getTargetException().getClass()
                      + ": "
                      + ite2.getTargetException().getMessage();
              return browser;
            }
          }
          browser = null;
          break;
        } catch (IllegalAccessException iae3) {
          browser = null;
          errorMessage = iae3.getMessage();
          return browser;
        } catch (IllegalArgumentException iare2) {
          browser = null;
          errorMessage = iare2.getMessage();
          return browser;
        } catch (InvocationTargetException ite3) {
          browser = null;
          errorMessage =
              ite3.getTargetException().getClass() + ": " + ite3.getTargetException().getMessage();
          return browser;
        }
      case 3:
      case 4:
        browser = "";
        break;
      case 5:
        browser = "cmd.exe";
        break;
      case 6:
        browser = "command.com";
        break;
    }
    return browser;
  }

  public static void openURL(String url)
      throws IllegalAccessException,
          InterruptedException,
          InstantiationException,
          IOException,
          IllegalArgumentException,
          InvocationTargetException {
    if (!loadedWithoutErrors) {
      throw new IOException("Exception in finding browser: " + errorMessage);
    }
    Object browser2 = locateBrowser();
    if (browser2 == null) {
      throw new IOException("Unable to locate browser: " + errorMessage);
    }
    switch (jvm) {
      case -1:
        try {
          int exitCode =
              Runtime.getRuntime()
                  .exec(
                      new String[] {
                        (String) browser2,
                        NETSCAPE_REMOTE_PARAMETER,
                        NETSCAPE_OPEN_PARAMETER_START + url + NETSCAPE_OPEN_PARAMETER_END
                      })
                  .waitFor();
          if (exitCode != 0) {
            Runtime.getRuntime().exec(new String[] {(String) browser2, url});
            return;
          }
          return;
        } catch (InterruptedException ie) {
          throw new IOException("InterruptedException while launching browser: " + ie.getMessage());
        }
      case 0:
        try {
          try {
            try {
              Object aeDesc = aeDescConstructor.newInstance(url);
              putParameter.invoke(browser2, keyDirectObject, aeDesc);
              sendNoReply.invoke(browser2);
              return;
            } catch (InstantiationException ie2) {
              throw new IOException(
                  "InstantiationException while creating AEDesc: " + ie2.getMessage());
            }
          } catch (IllegalAccessException iae) {
            throw new IOException(
                "IllegalAccessException while building AppleEvent: " + iae.getMessage());
          }
        } catch (InvocationTargetException ite) {
          throw new IOException(
              "InvocationTargetException while creating AEDesc: " + ite.getMessage());
        }
      case 1:
        Runtime.getRuntime().exec(new String[] {(String) browser2, url});
        return;
      case 2:
      default:
        Runtime.getRuntime().exec(new String[] {(String) browser2, url});
        return;
      case 3:
        int[] instance = new int[1];
        int result = ICStart(instance, 0);
        if (result == 0) {
          int[] selectionStart = new int[1];
          byte[] urlBytes = url.getBytes();
          int[] selectionEnd = {urlBytes.length};
          int result2 =
              ICLaunchURL(
                  instance[0],
                  new byte[1],
                  urlBytes,
                  urlBytes.length,
                  selectionStart,
                  selectionEnd);
          if (result2 == 0) {
            ICStop(instance);
            return;
          }
          throw new IOException("Unable to launch URL: " + result2);
        }
        throw new IOException("Unable to create an Internet Config instance: " + result);
      case 4:
        try {
          openURL.invoke(null, url);
          return;
        } catch (IllegalAccessException iae2) {
          throw new IOException(
              "IllegalAccessException while calling openURL: " + iae2.getMessage());
        } catch (InvocationTargetException ite2) {
          throw new IOException(
              "InvocationTargetException while calling openURL: " + ite2.getMessage());
        }
      case 5:
      case 6:
        Process process =
            Runtime.getRuntime()
                .exec(
                    new String[] {
                      (String) browser2,
                      FIRST_WINDOWS_PARAMETER,
                      SECOND_WINDOWS_PARAMETER,
                      THIRD_WINDOWS_PARAMETER,
                      '\"' + url + '\"'
                    });
        try {
          process.waitFor();
          process.exitValue();
        } catch (InterruptedException ie3) {
          throw new IOException(
              "InterruptedException while launching browser: " + ie3.getMessage());
        }
    }
  }
}
