package com.arrayprolc.shaderspatcher.actions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;

import com.arrayprolc.shaderspatcher.packet.ModifiedPacket;
import com.arrayprolc.shaderspatcher.utils.file.FileWalker;

public class ActionCreate extends Action {

    @Override
    public String getActionName() {
        return "create";
    }

    @Override
    public void runAction() {
        File file = new File("./data");
        File modifiedFolder = new File("./data/modified/shaders");
        File originalFolder = new File("./data/original/shaders");
        File newFolder = new File("./data/new/shaders");
        File header = new File("./data/modified/header.txt");
        modifiedFolder.mkdirs();
        originalFolder.mkdirs();
        newFolder.mkdirs();
        file.mkdirs();
        header.mkdirs();
        FileWalker w = new FileWalker();
        ArrayList<File> files = w.walk(modifiedFolder.getAbsolutePath());
        ArrayList<File> filesOrg = w.walk(originalFolder.getAbsolutePath());
        if (files == null || filesOrg == null) {
            //System.out.println("Can't find files.");
            System.exit(0);
            return;
        }
        HashMap<String, String> savePacketLists = new HashMap<String, String>();
        // TODO clean this up
        for (File f : files) {
            boolean cont = true;
            ArrayList<String> lines = new ArrayList<String>();
            ArrayList<String> lines2 = new ArrayList<String>();
            //System.out.println("Beginning to analyze " + f.getName() + " and its counterpart.");
            try {
                lines.addAll(FileUtils.readLines(f));
                File f2 = getFileFromName(f.getName(), filesOrg);
                if (f2 == null) {
                   // System.out.println(f.getName() + " is not in original and will be added later.");
                    cont = true;
                }
                lines2.addAll(FileUtils.readLines(f2));
            } catch (Exception ex) {
                //System.out.println(f.getName() + " cannot be read. Continuing.");
                cont = false;
            }
            if (cont) {
                int max = lines.size();
                if (lines2.size() > max) {
                    max = lines2.size();

                }
                ArrayList<ModifiedPacket> packets = new ArrayList<ModifiedPacket>();
                for (int id = 0; id < max; id++) {
                    try {
                        int i = id + 1;
                        String modified = "";
                        String original = "";
                        if (lines.size() < (i)) {
                            modified = "";
                        } else {
                            modified = lines.get(i);
                        }
                        if (lines2.size() < (i)) {
                            original = "";
                        } else {
                            original = lines2.get(i);
                        }
                        if (!original.equals(modified)) {
                            ModifiedPacket packet = new ModifiedPacket(f.getName(), i + 1, modified);
                            packets.add(packet);
                        }
                    } catch (Exception ex) {

                    }
                }
                if (packets.size() > 0) {
                    savePackets(f.getName(), packets);
                    savePacketLists.put(f.getName(), getSaveString(packets, "&nl").replace(";", "[semicolon]"));
                }
            }
        }
        String header2 = "";
        try {
            for(String s : FileUtils.readLines(header)){
                header2 = header2 + s + "&nl";
            }
        } catch (IOException e) {
            header2 = "NO_HEADER";
        }
        savePacketList(header2, savePacketLists);
        System.out.println("Done!");
        System.exit(0);
        return;
    }

    public File getFileFromName(String name, ArrayList<File> list) {
        for (File f : list) {
            if (f.getName().equals(name)) {
                return f;
            }
        }
        return null;
    }

    public void savePackets(String fileName, ArrayList<ModifiedPacket> packets) {
        String builder = getSaveString(packets, "\n");
        try {
            FileUtils.writeStringToFile(new File("./data/new/shaders/" + fileName), builder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void savePacketList(String header, HashMap<String, String> savePacketList) {
        String builder = "";
        String[] title = new String[] { "STOP! Don't know what this is?", "You must use the Shaders Patcher by ArrayPro! http://www.arrayprolc.com/shaderspatcher",
                "For instructions on how to use, visit ArrayPro's website, listed above.",
                "To find the original file, contact the modified shaderpack author or visit the official download page of the modified shaderpack." };
        savePacketList.put("header", header);
        for (String s : title) {
            builder = builder + "//" + s + "\n";
        }
        for (String s : savePacketList.keySet()) {
            String s2 = savePacketList.get(s);
            builder = builder + s + ";" + s2 + "\n";
        }
        //System.out.println("----------------------------------------------");
        //System.out.println(builder);
        try {
            FileUtils.writeStringToFile(new File("./data/new/" + "distribute.txt"), builder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getSaveString(ArrayList<ModifiedPacket> packets, String seperator) {
        String builder = "";
        for (ModifiedPacket packet : packets) {
            builder = builder + packet.toString() + seperator;
        }
        return builder;
    }

}
