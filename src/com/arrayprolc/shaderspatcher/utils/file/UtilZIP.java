package com.arrayprolc.shaderspatcher.utils.file;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

/**
 * 
 * @author Joe Michael from StackOverflow with modifications by Justin (Source:
 *         http
 *         ://stackoverflow.com/questions/9324933/what-is-a-good-java-library
 *         -to-zip-unzip-files)
 *
 */
public class UtilZIP {

    public static void extractFolder(String source, String destination, String password) {
        try {
            ZipFile zipFile = new ZipFile(source);
            if (zipFile.isEncrypted()) {
                zipFile.setPassword(password);
            }
            zipFile.extractAll(destination);
        } catch (ZipException e) {
            e.printStackTrace();
        }

    }

}
