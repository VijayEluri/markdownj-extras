/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.enr.markdownj.extras;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;

import com.google.common.io.Files;

/**
 * Provides utility methods for working with files.
 * 
 */
public class FileUtils {
    
    /**
     * Only static methods.
     */
    private FileUtils() {}

    /**
     * Returns file content as string, reading from a path. 
     * Throws runtime exception in case of FileNotFoundException or IOException.
     * 
     * @param filename the path to the file to read.
     * @return File content as string.
     */
    public static String readFileFromPath(String filename) {
        return readFileFromPath(filename, null);
    }

    /**
     * 
     * @param filename
     * @param encoding the encoding to use, null means platform default
     * @return the given file content
     */
    public static String readFileFromPath(String filename, String encoding) {
        try {
        	Charset charset = charsetForNameOrDefault(encoding);
        	return Files.toString(new File(filename), charset);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static File fileFromUrl(URL url) {
        File f = null;
        try {
            f = new File(url.toURI());
        } catch (URISyntaxException e) {
            f = new File(url.getPath());
        }
        return f;
    }

    /**
     * Returns file content as string, reading from a url. Throws runtime
     * exception in case of FileNotFoundException or IOException.
     * 
     * @param fileurl the url of the file to read.
     * @return file content as string.
     */
    public static String readFileFromUrl(URL fileurl, String encoding) {
        String fileContent = "";
        try {
        	Charset charset = charsetForNameOrDefault(encoding);
            fileContent = Files.toString(fileFromUrl(fileurl), charset);
        } catch (IOException e) {
            throw new RuntimeException("Error reading " + fileurl, e);
        }
        return fileContent;
    }

    /**
     * Writes a string to the specified file using the default encoding.
     * 
     * If the file path doesn't exist, it's created.
     * If the file exists, it is overwritten.
     * 
     * @param filePath the path to the file.
     * @param text the string to write.
     * @throws IOException
     */
    public static void writeFile(String filePath, String text) throws IOException {
        writeFile(filePath, text, null);
    }

    /**
     * Writes a string to the specified file using the specified encoding.
     * 
     * If the file path doesn't exist, it's created.
     * If the file exists, it is overwritten.
     * 
     * @param filePath the path to the file.
     * @param text the string to write.
     * @throws IOException
     */
    public static void writeFile(String filePath, String text, String encoding) throws IOException {
        File file = new File(filePath);
        File parent = file.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        Charset charset = charsetForNameOrDefault(encoding);
        Files.write(text, file, charset);
    }

    private static Charset charsetForNameOrDefault(String encoding) {
    	Charset charset = (encoding == null) ? Charset.defaultCharset() : Charset.forName(encoding);
		return charset;
	}

	/**
     * Replaces all backslashes with slash char. 
     * Throws NPE if the original path is null.
     * 
     * @param original the path to normalize.
     * @return the normalized path
     */
    public static String normalizedPath(String original) {
        return original.replaceAll("\\\\", "/");
    }

    /**
     * Changes file extension.
     * 
     * @param originalName
     * @param newExtension
     */
    public static String changeExtension(String originalName, String newExtension) {
        int lastDot = originalName.lastIndexOf(".");
        if (lastDot != -1) {
            return originalName.substring(0, lastDot) + newExtension;
        }
        return originalName + newExtension;
    }

}
