package com.stargazer.demos.java.grammar;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public class TryWithResource {



    public void normalClose() throws IOException {
        BufferedInputStream bin = null;
        BufferedOutputStream bout = null;
        try {
            bin = new BufferedInputStream(new FileInputStream(new File("d:/test.txt")));
            bout = new BufferedOutputStream(new FileOutputStream(new File("d:/out.txt")));
            int b;
            while ((b = bin.read()) != -1) {
                bout.write(b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bin != null) {
                try {
                    bin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (bout != null) {
                        try {
                            bout.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public void tryWithResource() throws IOException {
        try (BufferedInputStream bin = new BufferedInputStream(new FileInputStream(new File("d:/test.txt")));
            BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(new File("d:/out.txt")))) {
            int b;
            while ((b = bin.read()) != -1) {
                bout.write(b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        TryWithResource twr = new TryWithResource();
        twr.normalClose();
        twr.tryWithResource();
    }
}
