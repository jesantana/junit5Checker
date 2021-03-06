package com.plugin;


import org.apache.maven.plugin.testing.MojoRule;
import org.apache.maven.plugin.testing.WithoutMojo;

import org.junit.Rule;
import static org.junit.Assert.*;
import org.junit.Test;
import java.io.File;

public class Junit5CheckerTest
{
    @Rule
    public MojoRule rule = new MojoRule()
    {
        @Override
        protected void before() throws Throwable 
        {
        }

        @Override
        protected void after()
        {
        }
    };



    /** Do not need the MojoRule. */
    @WithoutMojo
    @Test
    public void testSomethingWhichDoesNotNeedTheMojoAndProbablyShouldBeExtractedIntoANewClassOfItsOwn()
    {
        assertTrue( true );
      
        System.out.println("import static org.junit.Assert.assertThat;".matches("import( static)? org.junit.[A-Z].*$"));
        
        System.out.println("import org.junit.jupiter.api.Test".matches("import( static)? org.junit.[A-Z].*$"));
        
        System.out.println("import org.junit.Test;".matches("import( static)? org.junit.[A-Z].*$"));
    }

}

