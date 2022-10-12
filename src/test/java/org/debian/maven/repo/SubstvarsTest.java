package org.debian.maven.repo;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.junit.Test;

import static org.junit.Assert.*;

public class SubstvarsTest {

    @Test
    public void testLoad() throws IOException {
        Properties substvars = Substvars.loadSubstvars(new File("src/test/resources/"), "libcommons-compress-java");
        assertNotNull(substvars);

        assertEquals("maven:OptionalDepends", "libxz-java (>= 1.8)", substvars.get("maven:OptionalDepends"));
        assertEquals("maven:TestDepends", "", substvars.get("maven:TestDepends"));
    }

    @Test
    public void testWrite() throws IOException {
        Properties substvars = Substvars.loadSubstvars(new File("target/test-classes"), "libcommons-compress-java");
        assertNotNull(substvars);
        
        Substvars.write(new File("target/test-classes"), "libcommons-compress2-java", substvars);
        
        Properties substvars2 = Substvars.loadSubstvars(new File("target/test-classes"), "libcommons-compress2-java");

        for (Object name : substvars.keySet()) {
            assertEquals("variable " + name, substvars.get(name), substvars2.get(name));
        }
    }
}
