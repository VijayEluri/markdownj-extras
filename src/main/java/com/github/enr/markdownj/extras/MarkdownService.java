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

public interface MarkdownService {

    /**
     * The standard line ending for Markdownj
     */
    public static final String EOL = "\n";

    void setHeaderPath(String path);

    void setContentPath(String path);

    void setFooterPath(String path);

    void setHeaderUrl(URL url);

    void setContentUrl(URL url);

    void setFooterUrl(URL url);

    void setHeader(String header);

    void setContent(String content);

    void setFooter(String footer);
    
    void setCodeBlockTemplate(String codeBlockTemplate);

    /**
     * Transforms the markdown content in html, adding header and footer.
     * 
     * @return the html string.
     */
    String process();
}
