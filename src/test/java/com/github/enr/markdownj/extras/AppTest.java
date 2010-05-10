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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.enr.markdownj.extras.FileUtils;
import com.github.enr.markdownj.extras.MarkdownApp;

/**
 * Integration test for MarkdownApp
 */
public class AppTest {

    private String headerPath;
    private String footerPath;
    private String sourcePath;

    private String baseTestDestination;

    @BeforeClass
    public void initData() {
        headerPath = resourceToPath("/site/templates/header.html");
        footerPath = resourceToPath("/site/templates/footer.html");
        sourcePath = resourceToPath("/site/markdown");
        baseTestDestination = "target/markdownj";
    }

    @Test
    public void testMainMethodCall() {
        String destination = buildDestinationDir("testMainMethodCall");
        String[] args = { "--source", sourcePath, "--destination", destination, "--header", headerPath, "--footer", footerPath };
        MarkdownApp.main(args);
        File destinationFile = new File(destination + "/sub/file.html");
        assertTrue(destinationFile.exists());
        assertEquals(FileUtils.readFileFromPath(destinationFile.getPath()), "<html>\n<h1>This is an H1</h1>\n\n<p>file.markdown</p>\n\n</html>\n");
    }

    @Test
    public void testMainMethodCallWithExtensionsArgument() {
        String destination = buildDestinationDir("testMainMethodCallWithExtensionsArgument");
        String extensions = "markdown,text";
        String[] args = { "--source", sourcePath, "--destination", destination, "--header", headerPath, "--footer", footerPath, "--extensions",
                extensions };
        MarkdownApp.main(args);
        File destinationFile = new File(destination + "/sub/file.html");
        assertTrue(destinationFile.exists());
        assertEquals(FileUtils.readFileFromPath(destinationFile.getPath()), "<html>\n<h1>This is an H1</h1>\n\n<p>file.markdown</p>\n\n</html>\n");
        File mdExtDestinationFile = new File(destination + "/sub/md-ext.html");
        assertTrue(!mdExtDestinationFile.exists(), String.format("File with extension 'md' processed, but processable extensions list is: '%s'",
                extensions));
    }

    @Test
    public void testProgrammaticCall() {
        String destination = buildDestinationDir("testProgrammaticCall");
        MarkdownApp app = new MarkdownApp();
        app.setSource(sourcePath);
        app.setDestination(destination);
        app.setHeader(headerPath);
        app.setFooter(footerPath);
        app.process();
        File destinationFile = new File(destination + "/sub/file.html");
        assertTrue(destinationFile.exists());
        assertEquals(FileUtils.readFileFromPath(destinationFile.getPath()), "<html>\n<h1>This is an H1</h1>\n\n<p>file.markdown</p>\n\n</html>\n");
        File mdExtDestinationFile = new File(destination + "/sub/md-ext.html");
        assertTrue(mdExtDestinationFile.exists());
    }

    @Test
    public void testNoProcessableExtensions() {
        String destination = buildDestinationDir("testNoProcessableExtensions");
        File mdExtDestinationFile = new File(destination + "/sub/md-ext.html");
        MarkdownApp app = new MarkdownApp();
        app.setSource(sourcePath);
        app.setDestination(destination);
        app.setHeader(headerPath);
        app.setFooter(footerPath);
        app.process();
        assertTrue(mdExtDestinationFile.exists(), "File with extension 'md' not processed");
    }

    @Test
    public void testFileWithNoProcessableExtension() {
        String destination = buildDestinationDir("testFileWithNoProcessableExtension");
        File mdExtDestinationFile = new File(destination + "/sub/md-ext.html");
        MarkdownApp app = new MarkdownApp();
        app.setSource(sourcePath);
        app.setDestination(destination);
        app.setHeader(headerPath);
        app.setFooter(footerPath);
        List<String> exts = new ArrayList<String>();
        exts.add("markdown");
        app.setProcessableExtensions(exts);
        app.process();
        assertTrue(!mdExtDestinationFile.exists(), String.format("File with extension 'md' processed, but processable extensions are: %s", app
                .getProcessableExtensions()));
    }

    @Test
    public void testFileWithProcessableExtension() {
        String destination = buildDestinationDir("testFileWithProcessableExtension");
        File mdExtDestinationFile = new File(destination + "/sub/md-ext.html");
        MarkdownApp app = new MarkdownApp();
        app.setSource(sourcePath);
        app.setDestination(destination);
        app.setHeader(headerPath);
        app.setFooter(footerPath);
        app.addProcessableExtension("md");
        app.process();
        assertTrue(mdExtDestinationFile.exists(), "File with extension 'md' not processed");
    }

    @Test
    public void testCodeBlockTemplate() {
        String destination = buildDestinationDir("testCodeBlockTemplate");
        String template = "<pre lang=\"%s\">%s</pre>";
        String s = sourcePath+"/code";
        String[] args = { "--source", s, "--destination", destination, "--code-template", template };
        MarkdownApp.main(args);
        File destinationFile = new File(destination + "/java.html");
        assertTrue(destinationFile.exists());
        assertEquals(FileUtils.readFileFromPath(destinationFile.getPath()), "<p>code:<pre lang=\"java\">import org.markdownj.*;</pre></p>\n");
    }

    /**
     * Utility method to resolve path to resources.
     * 
     * @param resource
     * @return
     */
    private String resourceToPath(String resource) {
        URL url = this.getClass().getResource(resource);
        File uf = new File(url.getFile());
        return FileUtils.normalizedPath(uf.getAbsolutePath());
    }

    /**
     * Utility method to create destination directories.
     * 
     * @param id
     * @return
     */
    private String buildDestinationDir(String id) {
        String destination = baseTestDestination + "/" + id;
        return destination;
    }

}
