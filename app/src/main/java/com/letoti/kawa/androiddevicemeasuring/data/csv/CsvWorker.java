package com.letoti.kawa.androiddevicemeasuring.data.csv;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import com.letoti.kawa.androiddevicemeasuring.R;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CsvWorker {

    private String fileName;
    private String baseDir;
    private CSVWriter writer;

    public CsvWorker(String name) {
        this.fileName = name.concat(".csv");
        this.baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    public CsvWorker() {
        this.fileName = System.currentTimeMillis() + ".csv";
        this.baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * Creates new file if not exists or opens and works with existing file
     *
     * @throws IOException
     */
    public void createOrOpen() throws IOException {
        String filePath = baseDir + File.separator + fileName;
        File f = new File(filePath);

        FileWriter mFileWriter;
        if (!f.exists()) {
            f.createNewFile();
        }
        mFileWriter = new FileWriter(filePath, true);
        writer = new CSVWriter(mFileWriter);
    }

    /**
     * Writes data line
     *
     * @param data
     */
    public void writeLine(String[] data) {
        writer.writeNext(data);
    }

    /**
     * Closing file stream
     */
    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


