package com.rbc.yelp;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.fail;

public class TestUtils {

    private static final String RESOURCES_BASE_PATH = "src/test/java/com/rbc/yelp/json";

    public static Object getResponsesFromJsonFile(String filePath, final Class responseClass) {
        String json = null;
        try {
            InputStream is = responseClass.getClassLoader().getResourceAsStream(filePath);
            if (is == null) {
                File file = new File(RESOURCES_BASE_PATH + filePath);
                is = new FileInputStream(file);
            }
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            fail("Error to read Json file");
        }

        Object response;
        try {
            response = new Gson().fromJson(json, responseClass);
        } catch (Exception e) {
            response = null;
            fail("Error to parse Json file");
        }
        return response;
    }
}
