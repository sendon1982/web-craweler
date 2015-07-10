package com.zunix.craweler.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Reference article URL: http://www.drdobbs.com/jvm/java-se-7-new-file-io/231600403
 * 
 * @author Ming
 * 
 */
public class FileUtil {
    private static final String FILE_PATTERN_ALL = "^*.*$";

    private static final Logger log = LoggerFactory.getLogger(FileUtil.class);

    public static Set<String> readFileAsSet(File file) {
        Set<String> resultSet = new LinkedHashSet<String>();

        log.debug("Read file from location [{}]", file.getParent());

        try {
            Path path = FileSystems.getDefault().getPath(file.getPath());
            BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset());
            String line = null;
            while ((line = reader.readLine()) != null) {
                resultSet.add(line);
            }

        } catch (IOException e) {
            log.error("Failed to read file from location [{}]", file.getParent(), e);
        }

        log.debug("Total lines from file [{}] is [{}]", file.getPath(), resultSet.size());

        return resultSet;
    }

    public static Set<String> readFileContentAsSet(Collection<File> fileList) {
        Set<String> resultSet = new LinkedHashSet<String>();
        for (File file : fileList) {
            log.debug("Fetch content from file [{}]", file.getAbsoluteFile());
            resultSet.addAll(readFileAsSet(file));
        }

        return resultSet;
    }

    public static Set<String> readFileContentAsSet(String dir) {
        Collection<File> fileList = getFileList(dir);
        return readFileContentAsSet(fileList);
    }

    /**
     * Fetch a list of files from a folder and its sub-folder via Apache Commons-IO
     * 
     * @param dir
     * @return
     */
    public static Collection<File> getFileList(String dir) {
        log.debug("Read files from location [{}]", dir);

        File fileDir = new File(dir);
        Collection<File> files =
                        FileUtils.listFiles(fileDir, new RegexFileFilter(FILE_PATTERN_ALL),
                                        DirectoryFileFilter.DIRECTORY);

        log.debug("Total number of files from location [{}] is [{}]", dir, files.size());

        return files;
    }

    /**
     * Recursively fetch a list of files from a folder and its sub-folder.
     * 
     * @param dir
     * @return
     */
    public static Collection<File> getFileListManually(String dir) {
        log.debug("Read files from location [{}]", dir);

        Collection<File> files = new ArrayList<File>();
        listf(dir, files);

        log.debug("Total number of files from location [{}] is [{}]", dir, files.size());

        return files;
    }
    
    private static void listf(String dir, Collection<File> files) {
        File directory = new File(dir);

        // get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList) {
            if (file.isFile()) {
                files.add(file);
            } else if (file.isDirectory()) {
                listf(file.getAbsolutePath(), files);
            }
        }
    }

}
