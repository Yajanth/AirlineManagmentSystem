package com.airplane;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.*;

public class LocalizationLoader {

    public static Properties loadMessages(Locale locale) {
        // Dynamically load the appropriate resource file based on the user's locale
        String baseName = "MessagesBundle_" + locale.getLanguage() + ".properties";
        Path path = Paths.get("i18n", baseName);  // e.g. ./i18n/MessagesBundle_fr.properties
        Properties props = new Properties();

        try (InputStream in = Files.newInputStream(path)) {
            props.load(in);  // Load the properties file
        } catch (IOException e) {
            System.err.println("Localization file not found: " + path);
            // Fallback to default English if file is not found
            try (InputStream defaultIn = Files.newInputStream(Paths.get("i18n", "MessagesBundle_en.properties"))) {
                props.load(defaultIn);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        return props;
    }
}
