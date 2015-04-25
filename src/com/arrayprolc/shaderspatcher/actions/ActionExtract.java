package com.arrayprolc.shaderspatcher.actions;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;

import com.arrayprolc.shaderspatcher.core.ShadersPatcherCore;
import com.arrayprolc.shaderspatcher.utils.md5.UtilMD5;

public class ActionExtract extends Action {

    @Override
    public String getActionName() {
        return "extract";
    }

    @Override
    public void runAction() {

        HashMap<String, String> data = new HashMap<String, String>();
        HashMap<String, String> headerData = new HashMap<String, String>();

        ArrayList<String> fileData = new ArrayList<String>();
        try {
            fileData.addAll(FileUtils.readLines(new File(ShadersPatcherCore.distribute)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String s : fileData) {
            if (s.contains(";") && !s.startsWith("//")) {
                String key = s.split(";")[0];
                String value = s.split(";")[1].replace("&nl", "\n").replace("[semicolon]", ";");
                data.put(key, value);
            }
        }
        String header = data.get("header");
        for (String s : header.split("\n")) {
            if(s.contains(": ")){
                String key = s.split(": ")[0];
                String value = s.split(": ")[1];
                headerData.put(key, value);
            }
        }
        String url = headerData.get("original-download-link");
        String md5 = data.get("md5");
        if (!md5.equals("any")) {
            try {
                String md5New = UtilMD5.getMD5Checksum(ShadersPatcherCore.original);
                if (!md5.equals(md5New)) {
                    JOptionPane.showMessageDialog(null, "The original shaderpack, named " + ShadersPatcherCore.original.replace("./", "")
                            + " does not match the original. \n\nAfter you click OK, we will bring you to the website where you can download the original pack.", "Error", JOptionPane.ERROR_MESSAGE);
                    if (Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().browse(new URI(url));
                    } else {
                        JOptionPane.showMessageDialog(null, "Sorry, we can't open your browser.\n Please try navigating to the link on your own: \n\n" + url, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    System.exit(0);
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        data.remove("md5");
        data.remove("header");
        
        for(String s : data.keySet()){
            HashMap<Integer, String> lines = new HashMap<Integer, String>();
        }

        String fileName = "filename.zip";
        JOptionPane.showMessageDialog(null, "Success! We've placed the file, named \"" + fileName + "\" into the same folder as this program is running!", "Success!", JOptionPane.INFORMATION_MESSAGE);
    }

}
