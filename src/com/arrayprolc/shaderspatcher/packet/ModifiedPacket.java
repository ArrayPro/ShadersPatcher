package com.arrayprolc.shaderspatcher.packet;

public class ModifiedPacket {

    private String fileName;
    private int lineNumber;
    private String data;

    public ModifiedPacket(String fileName, int lineNumber, String data) {
        super();
        this.fileName = fileName;
        this.lineNumber = lineNumber;
        this.data = data;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String toString() {
        String temporaryData = "";
        temporaryData = data.replace(" ", "%20");
        return lineNumber + " " + temporaryData;
    }

    public static ModifiedPacket fromString(String fileName, String packet) {
        String tempInt = packet.split(" ")[0];
        String tempData = packet.split(" ")[1];
        tempData = tempData.replace("%20", " ");
        int i = Integer.parseInt(tempInt);
        return new ModifiedPacket(fileName, i, tempData);
    }

}
