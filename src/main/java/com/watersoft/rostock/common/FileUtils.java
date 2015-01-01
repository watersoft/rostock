package com.watersoft.rostock.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Wouter on 1/1/2015.
 */
public class FileUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);

    public static boolean getResourceExists(String name) throws IOException {
        try (InputStream stream = FileUtils.class.getResourceAsStream(name)) {
            return stream != null;
        } catch (IOException e) {
            LOGGER.error(String.format("Failed to read resource file '%s'", name), e);
            throw e;
        }
    }

    public static String getResourceAsString(String name) throws IOException {
        try (InputStream stream = FileUtils.class.getResourceAsStream(name);
             InputStreamReader reader = new InputStreamReader(stream);
             BufferedReader buffer = new BufferedReader(reader)) {
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = buffer.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }
            return builder.toString();
        } catch (IOException e) {
            LOGGER.error(String.format("Failed to read resource file '%s'", name), e);
            throw e;
        }
    }
}
