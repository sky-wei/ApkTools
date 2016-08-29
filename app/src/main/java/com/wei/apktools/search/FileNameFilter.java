package com.wei.apktools.search;

import java.io.File;
import java.util.Locale;

/**
 * Created by jingcai.wei on 3/18/14.
 */
public class FileNameFilter implements FileFilter {

    private final String[] fileNames;
    private final String[] lowerCasefileNames;

    public FileNameFilter(String... fileNames) {

        this.fileNames = new String[fileNames.length];
        this.lowerCasefileNames = new String[fileNames.length];

        for (int i = 0; i < fileNames.length; i++) {

            if (fileNames[i] == null || fileNames[i].length() == 0) {

                throw new IllegalArgumentException(
                        "Each extension must be non-null and not empty");
            }

            this.fileNames[i] = fileNames[i];
            lowerCasefileNames[i] = fileNames[i].toLowerCase(Locale.ENGLISH);
        }
    }

    @Override
    public boolean accept(File file) {

        if (file != null) {

            if (file.isDirectory()) return false;

            String desiredFileName = file.getName().toLowerCase(Locale.ENGLISH);

            for (String fileName : lowerCasefileNames) {

                if (desiredFileName.equals(fileName)) {

                    return true;
                }
            }
        }

        return false;
    }
}
