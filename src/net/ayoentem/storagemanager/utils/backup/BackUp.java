package net.ayoentem.storagemanager.utils.backup;

import javafx.scene.control.ChoiceBox;
import lombok.Getter;
import lombok.Setter;
import net.ayoentem.storagemanager.utils.database.MySQLConnection;

import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Getter
@Setter
public class BackUp {

    private String pathData;
    private Date date;
    private ChoiceBox<String> listBackups;

    private MySQLConnection connection;

    public BackUp(MySQLConnection connection, ChoiceBox<String> listBackups) {
        this.connection = connection;
        this.listBackups = listBackups;
    }

    public void init() {
        File file = new File(System.getProperty("user.home") + "//.storageManager//backups//");
        if (!file.exists()) {
            file.mkdirs();
            return;
        }

        String[] fileArray = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return new File(dir, name).isDirectory();
            }
        });

        for (String backup : fileArray) {
            getListBackups().getItems().add(backup);
        }
    }

    public void createBackup(String pathData) {
        this.pathData = pathData;
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");

        if (!pathData.isEmpty()) {
            try {
                String curDate = sdf.format(new Date());
                Path existingPath = FileSystems.getDefault().getPath(this.pathData);
                File backupDir = new File(System.getProperty("user.home") + "//.storageManager//backups//" + "StorageManager-" + curDate);
                backupDir.mkdirs();

                connection.update("UPDATE backup SET lastBackup=CURDATE();");

                FileOutputStream fos = new FileOutputStream(System.getProperty("user.home") + "//.storageManager//backups//" + "StorageManager-" + curDate + "/BackUp.zip");
                ZipOutputStream zipOut = new ZipOutputStream(fos);

                zipFile(existingPath.toFile(), existingPath.toFile().getName(), zipOut);
                zipOut.close();
                fos.close();

                //copyFolder(existingPath, backupDir.toPath());

            } catch (Exception io) {
                io.printStackTrace();
            }
        }
    }

    private void copyFolder(Path src, Path dest) throws IOException {
        try (Stream<Path> stream = Files.walk(src)) {
            stream.forEach(source -> copy(source, dest.resolve(src.relativize(source))));
        }
    }

    private void copy(Path source, Path dest) {
        try {
            Files.copy(source, dest, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }


}




