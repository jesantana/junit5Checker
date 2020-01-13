package com.plugin;


import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Goal which touches a timestamp file.
 */
@Mojo( name = "junit5Checker", defaultPhase = LifecyclePhase.PROCESS_SOURCES )
public class Junit5Checker extends AbstractMojo
{
    /**
     * Location of the file.
     */
    @Parameter( defaultValue = "${project.build.testSourceDirectory}", property = "testDirectory", required = true )
    private File testDirectory;

    public void execute()
        throws MojoExecutionException
    {
        try {
            analyzeProject(testDirectory.getPath());
        } catch (IOException ioe) {
            getLog().error(ioe);
        }
    }

    public void analyzeProject(String path) throws IOException {
        Files.walk(Paths.get(path))
                .filter(this::needToCheckFile)
                .forEach(this::analyzeFile);
    }


    public void analyzeFile(Path path) {
        boolean is4 = false;
        boolean is5 = false;

        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                is4 = isJunit4(line) ? true : is4 ;

                is5 = isJunit5(line) ? true : is5 ;

                if(is4 && is5 ){
                    break;
                }
            }

        }
        catch(Exception ex){

        }

        if(is4 && is5) {
            getLog().info("JUNIT 4 AND 5 " + path);
        } else if(is4){
            getLog().info("JUNIT 4 " + path);
        } else if(is5) {
            getLog().info("JUNIT 5 " + path);
        } else {
            getLog().info("Weird File " + path);
        }

    }

    public boolean needToCheckFile(Path path){
        boolean shouldCheck = Files.isRegularFile(path) && path.toString().endsWith("Test.java");

        if(!shouldCheck) {
            getLog().info("Skipping checking of file " + path.getFileName());
        }

        return shouldCheck;
    }


    public boolean isJunit4(String line){
        return line.contains("import org.junit.Test");
    }

    public boolean isJunit5(String line){
        return line.contains("import org.junit.jupiter");
    }
}
