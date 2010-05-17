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

import java.net.URL;
import java.util.Map;

import com.petebevin.markdown.MarkdownProcessor;

/**
 * Basic implementation of MarkdownService.
 * 
 * 
 */
public class MarkdownServiceImpl implements MarkdownService {

    /**
     * The string containing the html used as header.
     */
    private String header;

    /**
     * The string containing the html used as footer.
     */
    private String footer;

    /**
     * The string containing the markdown to transform.
     */
    private String content;

    /**
     * The template (format string) used in code block rendering.
     * It is a 'Format String' with two format specifiers for String.
     * The first is used for 'lang' and the second for the actual code block.
     *  
     * @see: http://java.sun.com/j2se/1.5.0/docs/api/java/util/Formatter.html#syntax
     *
     */
    private String codeBlockTemplate;
    
    /**
     * @see com.petebevin.markdown.Entities
     */
    private Map<Character, String> htmlEntities;
    
    /**
     * The encoding to use.
     * null means platform default.
     * 
     */
    private String encoding;

    public MarkdownServiceImpl() {
    }

    public String process() {
        MarkdownProcessor processor = new MarkdownProcessor();
        if (codeBlockTemplate != null) {
            processor.setCodeBlockTemplate(codeBlockTemplate);
        }
        if (htmlEntities != null) {
            processor.setHtmlEntities(htmlEntities);
        }
        String hs = (header == null) ? "" : normalizeEol(header);
        String fs = (footer == null) ? "" : normalizeEol(footer);
        return String.format("%s%s%s", hs, processor.markdown(content), fs);
    }

    private String normalizeEol(String text) {
        if (text == null) {
            return "";
        }
        String r = text.replaceAll("\\r\\n", "\n");
        r = r.replaceAll("\\r", "\n");
        return r;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setContentPath(String path) {
        content = FileUtils.readFileFromPath(path, encoding);
    }

    public void setContentUrl(URL url) {
        content = FileUtils.readFileFromUrl(url);
    }

    public void setFooterPath(String path) {
        footer = FileUtils.readFileFromPath(path, encoding);
    }

    public void setFooterUrl(URL url) {
        footer = FileUtils.readFileFromUrl(url);
    }

    public void setHeaderPath(String path) {
        header = FileUtils.readFileFromPath(path, encoding);
    }

    public void setHeaderUrl(URL url) {
        header = FileUtils.readFileFromUrl(url);
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getCodeBlockTemplate() {
        return codeBlockTemplate;
    }

    public void setCodeBlockTemplate(String codeBlockTemplate) {
        this.codeBlockTemplate = codeBlockTemplate;
    }

    public void setHtmlEntities(Map<Character, String> htmlEntities) {
        this.htmlEntities = htmlEntities;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

}
