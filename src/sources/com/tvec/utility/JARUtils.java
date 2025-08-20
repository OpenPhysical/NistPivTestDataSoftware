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

import gov.nist.piv.data.SecurityObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: JPIV Test Data Generator.jar:com/tvec/utility/JARUtils.class */
public enum JARUtils {
  ;
  public static final String FILE_LIST_FILE_NAME = "FILE_LIST.txt";

  public static final void copyFile(String source, String destination) throws IOException {
    InputStream in = null;
    OutputStream out = null;
    try {
      InputStream in2 = getInputStream(source);
      if (in2 == null) {
        throw new FileNotFoundException(source);
      }
      File outFile = new File(destination);
      File directory = outFile.getParentFile();
      if (directory != null) {
        directory.mkdirs();
      }
      OutputStream out2 = new FileOutputStream(outFile);
      byte[] buf = new byte[SecurityObject.DEFAULT_KEY_SIZE];
      while (true) {
        int len = in2.read(buf);
        if (len <= 0) {
          break;
        } else {
          out2.write(buf, 0, len);
        }
      }
      if (in2 != null) {
        try {
          in2.close();
        } catch (IOException ex) {
          ex.printStackTrace();
        }
      }
      if (out2 != null) {
        try {
          out2.close();
        } catch (IOException ex2) {
          ex2.printStackTrace();
        }
      }
    } catch (Throwable th) {
      if (0 != 0) {
        try {
          in.close();
        } catch (IOException ex3) {
          ex3.printStackTrace();
        }
      }
      if (0 != 0) {
        try {
          out.close();
        } catch (IOException ex4) {
          ex4.printStackTrace();
        }
      }
      throw th;
    }
  }

  public static final InputStream getInputStream(String path) throws FileNotFoundException {
    ClassLoader cl = JARUtils.class.getClassLoader();
    InputStream in = cl.getResourceAsStream(path);
    if (in == null) {
      throw new FileNotFoundException("Unable to find file in JAR: " + path);
    }
    return in;
  }

  public static final String[] getFileList(String path) throws IOException {
    return getFileList(path, null);
  }

  /* JADX WARN: Removed duplicated region for block: B:43:0x00ed A[EXC_TOP_SPLITTER, SYNTHETIC] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
      To view partially-correct add '--show-bad-code' argument
  */
  public static final java.lang.String[] getFileList(java.lang.String r6, java.io.FilenameFilter r7)
      throws java.io.IOException {
    /*
        Method dump skipped, instructions count: 262
        To view this dump add '--comments-level debug' option
    */
    throw new UnsupportedOperationException(
        "Method not decompiled: com.tvec.utility.JARUtils.getFileList(java.lang.String, java.io.FilenameFilter):java.lang.String[]");
  }
}
