/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ayoentem.storagemanager.utils;

import java.io.File;
import java.util.Comparator;

/**
 *
 * @author ayoentem
 */
public class SortByFileSize implements Comparator<File>{
    
    public int compare(File a, File b) 
    { 
        return (int) (a.length()- b.length());
    } 
}
