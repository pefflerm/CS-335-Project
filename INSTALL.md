# Installation
This program is desinged to work on Eclipse IDE.

## Download Links
- [Maven](https://maven.apache.org/download.cgi)
- [Apache Commons Lang](https://commons.apache.org/lang/download_lang.cgi)
- [Opencsv](https://sourceforge.net/projects/opencsv/)

## Instructions
Download the ZIP and extract. Import the folder as a project in Eclipse.

To install Maven, open Eclipse and cick `Help`>>`Install New Software`>>`Add` and then type `M2Eclipse` in the Name field and `http://download.eclipse.org/technology/m2e/releases` in the Location field. Installation should begin after clicking ok.

Using the link above, download Apache Commons Lang by clicking `commons-lang3-3.17.0-bin.zip`. Unzip and paste a copy of the entire folder in `CS-335-Project-main\src\main\java\cs335_package`. Copy the file `commons-lang3-3.17.0.jar` into `CS-335-Project-main\src\main\java\cs335_package`.

Using the link above, download Opencsv and paste a copy of the .jar file in `CS-335-Project-main\src\main\java\cs335_package`.

In Eclipse, click `Window`>>`Show view`>>`Terminal`. Open a local terminal and navigate to the directory containing the project. Run the command `mvn install`. If everything was installed properly, you should see the following output.
```
[INFO] Scanning for projects...
[INFO] 
[INFO] -----------------------< com.example:yelp-test >------------------------
[INFO] Building yelp-test 1.0-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- resources:3.3.1:resources (default-resources) @ yelp-test ---
[WARNING] Using platform encoding (UTF-8 actually) to copy filtered resources, i.e. build is platform dependent!
[INFO] skip non existing resourceDirectory C:\Users\peffl\OneDrive\Documents\Simmons\SPRING 25\Software\eclipse-workspace\CS-335-Project-main\src\main\res
ources
[INFO]
[INFO] --- compiler:3.13.0:compile (default-compile) @ yelp-test ---
[INFO] Recompiling the module because of added or removed source files.
[WARNING] File encoding has not been set, using platform encoding UTF-8, i.e. build is platform dependent!
[INFO] Compiling 26 source files with javac [debug target 11] to target\classes
[WARNING] system modules path not set in conjunction with -source 11
[INFO] /C:/Users/peffl/OneDrive/Documents/Simmons/SPRING 25/Software/eclipse-workspace/CS-335-Project-main/src/main/java/cs335_package/SearchYelp.java: C:
\Users\peffl\OneDrive\Documents\Simmons\SPRING 25\Software\eclipse-workspace\CS-335-Project-main\src\main\java\cs335_package\SearchYelp.java uses or overr
ides a deprecated API.
[INFO] /C:/Users/peffl/OneDrive/Documents/Simmons/SPRING 25/Software/eclipse-workspace/CS-335-Project-main/src/main/java/cs335_package/SearchYelp.java: Re
compile with -Xlint:deprecation for details.
[INFO]
[INFO] --- resources:3.3.1:testResources (default-testResources) @ yelp-test ---
[WARNING] Using platform encoding (UTF-8 actually) to copy filtered resources, i.e. build is platform dependent!
[INFO] skip non existing resourceDirectory C:\Users\peffl\OneDrive\Documents\Simmons\SPRING 25\Software\eclipse-workspace\CS-335-Project-main\src\test\res
ources
C:\Users\peffl\.m2\repository\com\example\yelp-test\1.0-SNAPSHOT\yelp-test-1.0-SNAPSHOT.jar
[INFO] Installing C:\Users\peffl\OneDrive\Documents\Simmons\SPRING 25\Software\eclipse-workspace\CS-335-Project-main\target\yelp-test-1.0-SNAPSHOT-jar-wit
h-dependencies.jar to C:\Users\peffl\.m2\repository\com\example\yelp-test\1.0-SNAPSHOT\yelp-test-1.0-SNAPSHOT-jar-with-dependencies.jar
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  54.708 s
[INFO] Finished at: 2025-05-07T12:06:10-04:00
[INFO] ------------------------------------------------------------------------
```

## Troubleshooting
### Refresh
After adding the .jar files, refresh the project by right clicking on the project in Project Explorer and clicking Refresh.

### Check pom.xml
The file pom.xml needs to be formatted exactly as follows.

```
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.example</groupId>
    <artifactId>yelp-test</artifactId>
    <version>1.0-SNAPSHOT</version>
    <properties>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.apache.httpcomponents.client5</groupId>
            <artifactId>httpclient5</artifactId>
            <version>5.4.2</version>
        </dependency>
        <dependency>
            <groupId>org.apache.httpcomponents.core5</groupId>
            <artifactId>httpcore5</artifactId>
            <version>5.3.3</version>
        </dependency>
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.18.3</version>
        </dependency>
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-classic</artifactId>
            <version>1.2.3</version>
        </dependency>
        <dependency>
            <groupId>commons-cli</groupId>
            <artifactId>commons-cli</artifactId>
            <version>1.9.0</version>
        </dependency>
        <dependency>
            <groupId>io.reactivex.rxjava2</groupId>
            <artifactId>rxjava</artifactId>
            <version>2.2.21</version>
        </dependency>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>1.7.36</version>
        </dependency>
        <dependency>
            <groupId>commons-logging</groupId>
            <artifactId>commons-logging</artifactId>
            <version>1.2</version>
        </dependency>
        <dependency>
			<groupId>com.opencsv</groupId>
			<artifactId>opencsv</artifactId>
			<version>5.10</version>
		</dependency>
		<dependency>
			<groupId>com.toedter</groupId>
			<artifactId>jcalendar</artifactId>
			<version>1.4</version>
			<!--  Use the latest appropriate version  -->
</dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-jar-plugin</artifactId>
                <version>3.2.0</version>
                <configuration>
                    <archive>
                        <manifest>
                            <mainClass>cs335_package.Main</mainClass> <!-- Updated to Main class -->
                        </manifest>
                    </archive>
                </configuration>
            </plugin>
            <plugin>
                <artifactId>maven-assembly-plugin</artifactId>
                <version>3.3.0</version>
                <configuration>
                    <descriptorRefs>
                        <descriptorRef>jar-with-dependencies</descriptorRef>
                    </descriptorRefs>
                    <archive>
                        <manifest>
                            <mainClass>cs335_package.Main</mainClass> <!-- Updated to Main class -->
                        </manifest>
                    </archive>
                </configuration>
                <executions>
                    <execution>
                        <id>make-assembly</id>
                        <phase>package</phase>
                        <goals>
                            <goal>single</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

### Configure build path
If the Referenced Libraries folder is empty, add the .jar files to the build path.

In the src folder, right click on `opencsv-5.10.jar` and select `Build Path`>>`Add to Build Path`. Repeat for `commons-lang3-3.17.0.jar`.
