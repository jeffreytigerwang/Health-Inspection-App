package com.example.restaurantlist.Model;

import android.os.Build;
import android.os.Environment;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

//Takes a CSV file and downloads it into files.
public class downloadCSV {
    private String url;
    private String fileName;

    public downloadCSV(String url, String fileName) {
        this.url = url;
        this.fileName = fileName;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void download() {
        try {
            InputStream in = new URL(this.url).openStream();
            File file = new File(Environment.getDataDirectory(),fileName);
            file.createNewFile();
            Files.copy(in, file.toPath(), StandardCopyOption.REPLACE_EXISTING);

        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}
