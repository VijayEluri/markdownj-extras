
Markdown Extras
================

Markdown Extras contains basic code implementations of common use cases of Markdown (a text-to-html conversion tool written by John Gruber), using the MarkdownJ library.

MarkdownJ is the pure Java port of Markdown.


Features
--------

*   MarkdownService

    A class which uses MarkdownJ library to transform markdown files, adding header and footer from classpath or filesystem or as text string.

*   MarkdownApp

    A basic application which uses MarkdownService to process recursively the markdown files in a given directory.


Building
--------

Git clone or download of the last version Jar at <http://github.com/downloads/enr/markdownj-extras/markdownj-extras-0.3.2.jar>.

Markdown App
------------

Usage (after mvn clean install):

    mvn exec:java -Dexec.mainClass="com.github.enr.markdownj.extras.MarkdownApp" \
    -Dexec.args="--header src/test/resources/site/templates/header.html          \
    --footer src/test/resources/site/templates/footer.html                       \
    --source src/test/resources/site/markdown                                    \
    --destination target/markdownj-extras"

Now you can see results in target/markdownj-extras directory.

Licensing
---------

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.

You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.

See the License for the specific language governing permissions and limitations under the License.



