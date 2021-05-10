/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ayoentem.storagemanager.main;

import java.io.File;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ayoentem
 */
public class Volume {

    private String name;
    private int fiDiContain;
    private boolean isHidden;
    private String lastModified;
    private String directory;

    public static ObservableList<Volume> volumeList = FXCollections.observableArrayList();

    public Volume() {
    }

    public Volume(String name, int fiDiContain, boolean isHidden, String lastModified, String directory) {
        this.name = name;
        this.fiDiContain = fiDiContain;
        this.isHidden = isHidden;
        this.lastModified = lastModified;
        this.directory = directory;
    }

    public String getName() {
        return name;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFiDiContain() {
        return fiDiContain;
    }

    public void setFiDiContain(int fiDiContain) {
        this.fiDiContain = fiDiContain;
    }

    public boolean isIsHidden() {
        return isHidden;
    }

    public void setIsHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public static ObservableList<Volume> getVolumeList() {
        return volumeList;
    }

    public static void setVolumeList(ObservableList<Volume> volumeList) {
        Volume.volumeList = volumeList;
    }

    public static long getFileFolderSize(File dir) {
        long size = 0;
        if (dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                if (file.isFile()) {
                    size += file.length();
                } else {
                    size += getFileFolderSize(file);
                }
            }
        } else if (dir.isFile()) {
            size += dir.length();
        }
        return size;
    }

}
