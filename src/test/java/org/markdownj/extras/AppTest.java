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

package org.markdownj.extras;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

/**
 * Integration test for MarkdownApp
 */
public class AppTest 
{

	private String headerPath;
	private String footerPath;
	private String sourcePath;
	
	private String destination;

	@Before
	public void initData() {
		
		headerPath = resourceToPath("/site/templates/header.html");
		footerPath = resourceToPath("/site/templates/footer.html");
		sourcePath = resourceToPath("/site/markdown");
		
		destination = "target/markdownj";
	}
	
	@Test
	public void testMainMethodCall() throws IOException {
		clearDirectory(destination);
		String[] args = {"--source", sourcePath, "--destination", destination, "--header", headerPath, "--footer", footerPath};
		MarkdownApp.main(args);
		File destinationFile = new File(destination+"/sub/file.html");
		assertTrue(destinationFile.exists());
		assertEquals("<html>\n<h1>This is an H1</h1>\n\n<p>file.markdown</p>\n\n</html>\n", FileUtils.readFileFromPath(destinationFile.getPath()));
	}

	@Test
	public void testProgrammaticCall() throws IOException {
		clearDirectory(destination);
		MarkdownApp app = new MarkdownApp();
		app.setSource(sourcePath);
		app.setDestination(destination);
		app.setHeader(headerPath);
		app.setFooter(footerPath);
		app.process();
		File destinationFile = new File(destination+"/sub/file.html");
		assertTrue(destinationFile.exists());
		assertEquals("<html>\n<h1>This is an H1</h1>\n\n<p>file.markdown</p>\n\n</html>\n", FileUtils.readFileFromPath(destinationFile.getPath()));
	}
	
	/**
	 * Utility method to resolve path to resources.
	 * @param resource
	 * @return
	 */
	private String resourceToPath(String resource)
	{
		URL url = this.getClass().getResource(resource);
		File uf = new File(url.getFile());
		return FileUtils.normalizedPath(uf.getAbsolutePath());
	}
	
	/**
	 * Utility method to delete all content of a directory, if it exists.
	 * 
	 * @param path
	 * @throws IOException 
	 */
	private void clearDirectory(String path) throws IOException
	{
		File dir = new File(path);
		if (dir.exists()) {
			FileUtils.deleteDirectoryContents(dir);
		}
	}
}
