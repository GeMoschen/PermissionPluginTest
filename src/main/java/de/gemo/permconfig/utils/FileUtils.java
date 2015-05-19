package de.gemo.permconfig.utils;

import java.io.*;

/**
 * Created by GeMo on 17.05.2015.
 */
public class FileUtils {

    public static boolean saveString(String string, File file) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(string);
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String readFile(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = null;
        StringBuilder stringBuilder = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }

        reader.close();
        return stringBuilder.toString().trim();
    }
}
