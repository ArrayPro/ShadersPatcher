package com.arrayprolc.shaderspatcher.actions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
        modifiedFolder.mkdirs();
        originalFolder.mkdirs();
        newFolder.mkdirs();
        file.mkdirs();
        FileWalker w = new FileWalker();
        ArrayList<File> files = w.walk(modifiedFolder.getAbsolutePath());
        ArrayList<File> filesOrg = w.walk(originalFolder.getAbsolutePath());
        if (files == null || filesOrg == null) {
            System.out.println("Can't find files.");
            System.exit(0);
            return;
        }
        // TODO clean this up
        for (File f : files) {
            boolean cont = true;
            ArrayList<String> lines = new ArrayList<String>();
            ArrayList<String> lines2 = new ArrayList<String>();
            System.out.println("Beginning to analyze " + f.getName() + " and its counterpart.");
            try {
                lines.addAll(FileUtils.readLines(f));
                File f2 = getFileFromName(f.getName(), filesOrg);
                if (f2 == null) {
                    System.out.println(f.getName() + " is not in original and will be added later.");
                    cont = false;
                }
                lines2.addAll(FileUtils.readLines(f2));
            } catch (Exception ex) {
                System.out.println(f.getName() + " cannot be read. Continuing.");
                cont = false;
            }
            if (cont) {
                int max = lines.size();
                if (lines2.size() > max) {
                    max = lines2.size();
                    
                }
                ArrayList<ModifiedPacket> packets = new ArrayList<ModifiedPacket>();
                for (int id = 0; id < max; id++) {
                    try{
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
                    // System.out.println(modified + " | " + original);
                    if (!original.equals(modified)) {
                       // System.out.println("Line " + i + " is different!");
                        ModifiedPacket packet = new ModifiedPacket(f.getName(), i+1, modified);
                        packets.add(packet);
                    }
                    }catch(Exception ex){
                        
                    }
                }
                savePackets(f.getName(), packets);
            }
        }
        System.out.println("Done!");

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
        String builder = "";
        for(ModifiedPacket packet : packets){
            builder = builder + packet.toString() + "\n";
        }
        System.out.println(builder);
        try {
            FileUtils.writeStringToFile(new File("./data/new/shaders/" + fileName), builder);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
