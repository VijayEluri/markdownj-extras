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

import java.net.URL;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.markdownj.extras.MarkdownService;
import org.markdownj.extras.MarkdownServiceImpl;



import static org.testng.Assert.*;

/**
 * 
 */
public class MarkdownServiceTest {

    MarkdownService service;

    URL headerUrl;

    URL footerUrl;

    URL winEncodingHeaderUrl;

    String markdownContent = "# This is an H1";
    String htmlContent = "<h1>This is an H1</h1>";

    String expected;

    @BeforeClass
    public void initData() {
        service = new MarkdownServiceImpl();
        headerUrl = this.getClass().getResource("/site/templates/header.html");
        winEncodingHeaderUrl = this.getClass().getResource("/site/templates/header-win.html");
        footerUrl = this.getClass().getResource("/site/templates/footer.html");
        expected = String.format("<html>%s%s%s%s</html>%s", MarkdownService.EOL, htmlContent, MarkdownService.EOL, MarkdownService.EOL,
                MarkdownService.EOL);
        service.setContent(markdownContent);
    }

    @Test
    public void testUsingPaths() {
        service.setHeaderPath(headerUrl.getPath());
        service.setFooterPath(footerUrl.getPath());
        assertEquals(service.process(), expected);
    }

    @Test
    public void testUsingUrls() {
        service.setHeaderUrl(headerUrl);
        service.setFooterUrl(footerUrl);
        assertEquals(service.process(), expected);
    }

    @Test
    public void testUsingStrings() {
        service.setHeader(String.format("<html>%s", MarkdownService.EOL));
        service.setFooter(String.format("%s</html>%s", MarkdownService.EOL, MarkdownService.EOL));
        assertEquals(service.process(), expected);
    }

    @Test
    public void testOnlyContent() {
        String expectedForContentOnly = htmlContent + MarkdownService.EOL;
        assertEquals(service.process(), expectedForContentOnly);
    }

    @Test
    public void testWinEncoding() {
        service.setHeaderUrl(winEncodingHeaderUrl);
        service.setFooterUrl(footerUrl);
        assertEquals(service.process(), expected);
    }
}