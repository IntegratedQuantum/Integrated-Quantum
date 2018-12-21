package quantum.integratedquantum.app.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import quantum.integratedquantum.implementation.App;

public class SimpleFileReader {
    public static String readFile(String file, App app) {
        StringBuilder txt = new StringBuilder();
        try {
            String line;
            InputStream i = app.getAsset().open(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(i));
            while ((line = reader.readLine()) != null) {
                txt.append(line);
            }
            i.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return txt.toString();
    }
}
