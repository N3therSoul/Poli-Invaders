package me.nether.polinvaders.utils;

import me.nether.polinvaders.Main;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    public static InputStream getFile(String string) {
        InputStream in = Main.DISPLAY.getClass().getResourceAsStream(string);
        return in;
    }

    public static List<String> readFileLines(File file) throws Exception {
        List<String> lines = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();
        return lines;
    }

    public static List<String> readUrlResponse(URL url) {
        try {
            List<String> lines = new ArrayList<String>();
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            for (String s : reader.readLine().split(":")) {
                lines.add(s);
            }
            reader.close();
            return lines;
        } catch (Exception e) {
        }
        return null;
    }

    public static List<String> readUrlResponseFull(URL url) {
        try {
            List<String> lines = new ArrayList<String>();
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (!inputLine.isEmpty()) {
                    lines.add(inputLine);
                }
            }
            in.close();
            return lines;
        } catch (Exception e) {
        }
        return null;
    }

    public static void printFile(File dir, List<String> list) throws Exception {
        PrintWriter writer = new PrintWriter(dir);
        for (Object o : list) {
            String string = o.toString();
            writer.println(string);
        }
        writer.close();
    }

    public static void printFile(File dir, String... list) throws Exception {
        PrintWriter writer = new PrintWriter(dir);
        for (Object o : list) {
            String string = o.toString();
            writer.println(string);
        }
        writer.close();
    }

    public static void checkDirectory(File dir) {
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

}
