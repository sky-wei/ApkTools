package com.wei.apktools.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * Created by starrysky on 15-3-25.
 */
public class ZipUtils {

    public static void copyFile(File souce, File target, ZipEntryFilter filter) throws IOException {

        if (souce == null || target == null) return ;

        ZipFile zipFile = null;
        ZipOutputStream zos = null;

        try {
            // 需要解压的文件
            zipFile = new ZipFile(souce);
            zos = new ZipOutputStream(new FileOutputStream(target));

            ZipEntry entry;
            int length;
            byte[] buffer = new byte[4096];

            Enumeration<ZipEntry> enumeration = (Enumeration<ZipEntry>) zipFile.entries();

            while (enumeration.hasMoreElements()) {

                InputStream is = null;

                try {
                    entry = enumeration.nextElement();

                    if (!accept(filter, entry)) continue;

                    is = zipFile.getInputStream(entry);

                    ZipEntry zipEntry = new ZipEntry(entry.getName());
                    zipEntry.setMethod(entry.getMethod());
                    zipEntry.setCrc(entry.getCrc());
                    zipEntry.setSize(entry.getSize());
                    zos.putNextEntry(zipEntry);

                    while ((length = is.read(buffer)) != -1) {
                        zos.write(buffer, 0, length);
                    }
                } finally {
                    if (is != null) is.close();
                }
            }
        } finally {
            if (zos != null) zos.close();
            if (zipFile != null) zipFile.close();
        }
    }

    private static boolean accept(ZipEntryFilter filter, ZipEntry entry) {
        return filter == null ? true : filter.accept(entry);
    }

    public interface ZipEntryFilter {

        boolean accept(ZipEntry entry);
    }
}
