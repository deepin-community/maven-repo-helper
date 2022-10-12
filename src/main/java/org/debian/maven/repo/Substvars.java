package org.debian.maven.repo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Substvars {

    private static final Logger log = Logger.getLogger(Substvars.class.getName());

    private static File substvarsFile(File outputDirectory, String packageName) {
        return new File(outputDirectory, packageName + ".substvars");
    }

    public static Properties loadSubstvars(File outputDirectory, String packageName) {
        File substvarsFile = Substvars.substvarsFile(outputDirectory, packageName);
        Properties depVars = new Properties();
        if (substvarsFile.exists()) {
            try (BufferedReader in = new BufferedReader(new FileReader(substvarsFile))) {
                String line;
                while ((line = in.readLine()) != null) {
                    if (!line.startsWith("#")) {
                        int pos = line.indexOf("=");
                        String key = line.substring(0, pos).trim();
                        String value = line.substring(pos + 1).trim();
                        depVars.setProperty(key, value);
                    }
                }
            } catch (IOException ex) {
                log.log(Level.SEVERE, "Error while reading file " + substvarsFile, ex);
            }
        }
        return depVars;
    }

    public static void write(File outputDirectory, String packageName, Properties substvars) {
        File substvarsFile = substvarsFile(outputDirectory, packageName);
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(substvarsFile));
            out.write("#List of dependencies for " + packageName + ", generated for use by debian/control\n");

            for (String propName : substvars.stringPropertyNames()) {
                out.write(propName + "=" +  substvars.get(propName) + "\n");
            }
            out.close();
        } catch (IOException ex) {
            log.log(Level.SEVERE, "Error while saving file " + substvarsFile, ex);
        }
    }
}
