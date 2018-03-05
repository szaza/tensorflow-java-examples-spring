package edu.ml.tensorflow.util;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

/**
 * Util class to read image, graphDef and label files.
 */
public final class IOUtil {
    private final static Logger LOGGER = LoggerFactory.getLogger(IOUtil.class);
    private IOUtil() {}

    public static byte[] readAllBytesOrExit(final String fileName) {
        try {
            return IOUtils.toByteArray(new FileInputStream(new File(fileName)));
        } catch (IOException | NullPointerException ex) {
            LOGGER.error("Failed to read!");
            throw new ServiceException("Failed to read!", ex);
        }
    }

    public static List<String> readAllLinesOrExit(final String filename) {
        try {
            File file = new File(filename);
            return Files.readAllLines(file.toPath(), Charset.forName("UTF-8"));
        } catch (IOException ex) {
            LOGGER.error("Failed to read [{}]!", filename, ex.getMessage());
            throw new ServiceException("Failed to read [" + filename + "]!", ex);
        }
    }

    public static void createDirIfNotExists(final File directory) {
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    public static String getFileName(final String path) {
        return path.substring(path.lastIndexOf("/") + 1, path.length());
    }
}
