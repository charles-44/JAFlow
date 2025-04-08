package org.scem.command.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

public class FileUtils {

    private FileUtils() {
        //private constructor for utility class
    }

    public static File getProjectDirectory() {
        return (new File(System.getProperty("user.dir")));
    }

    public static File getRootProjectDirectory() {
        return getProjectDirectory().getParentFile();
    }


    public static List<Path> findAllFiles(Path rootDir, String extension) throws IOException {

        if (!extension.startsWith(".")) {
            extension = "." + extension;
        }
        extension = extension.toLowerCase();

        try (Stream<Path> paths = Files.walk(rootDir)) {
            String finalExtension = extension;
            return paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().toLowerCase().endsWith(finalExtension))
                    .toList();
        }
    }


    public static String getPrivateProperties(String key) throws IOException {
        File projectDirectory = FileUtils.getProjectDirectory();
        File fileProperties = Paths.get(projectDirectory.getAbsolutePath(), "private.properties").toFile();
        Properties properties = new Properties();

        try (FileInputStream input = new FileInputStream(fileProperties)) {
            properties.load(input);
        }

        return properties.getProperty(key);
    }


}
