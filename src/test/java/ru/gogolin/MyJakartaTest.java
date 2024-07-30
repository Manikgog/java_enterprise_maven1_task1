package ru.gogolin;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class MyJakartaTest {
    private final String fileFolder = "test-files";

    @Before
    public void before() throws IOException {
        File dir = new File(System.getProperty("user.dir"), fileFolder);
        Path path = Paths.get(String.valueOf(dir));
        if (Files.notExists(path)) {
            Files.createDirectories(path);
        }
    }

    @After
    public void after() throws IOException {
        Path path = Paths.get(System.getProperty("user.dir"), fileFolder);
        try(Stream<Path> p = Files.walk(path)){
            p.filter(Files::isRegularFile).forEach(f -> {
                try {
                    Files.delete(f);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            });
        }
    }

    @Test
    public void writeToJson_Test(){
        MyJakarta myJakarta = new MyJakarta(Paths.get(String.valueOf(new File(System.getProperty("user.dir"), fileFolder))));
        myJakarta.setVersion("1.0");
        myJakarta.setDescription("My Jakarta");

        Technology t = new Technology();
        t.setName("Maven");
        t.setDescription("Apache Maven is a software project management and comprehension tool.");

        Technology t1 = new Technology();
        t1.setName("Java 17");
        t1.setDescription("The JDK is a development environment for building applications using the Java programming language.");

        myJakarta.setTechnologies(List.of(t, t1));

        try {
            File dir = new File(System.getProperty("user.dir"), fileFolder);
            Path path = Paths.get(String.valueOf(dir), myJakarta.getFileName());
            myJakarta.writeToJson(path.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        File dir = new File(System.getProperty("user.dir"), fileFolder);
        assertEquals(Paths.get(String.valueOf(dir), myJakarta.getFileName()).getFileName().toString(), myJakarta.getFileName());
    }

    @Test
    public void readFromJson_Test(){
        MyJakarta myJakarta = new MyJakarta(Paths.get(String.valueOf(new File(System.getProperty("user.dir"), fileFolder))));
        myJakarta.setVersion("1.0");
        myJakarta.setDescription("My Jakarta");

        Technology t = new Technology();
        t.setName("Maven");
        t.setDescription("Apache Maven is a software project management and comprehension tool.");

        Technology t1 = new Technology();
        t1.setName("Java 17");
        t1.setDescription("The JDK is a development environment for building applications using the Java programming language.");

        myJakarta.setTechnologies(List.of(t, t1));

        try {
            File dir = new File(System.getProperty("user.dir"), fileFolder);
            Path path = Paths.get(String.valueOf(dir), myJakarta.getFileName());
            myJakarta.writeToJson(path.toString());

            assertEquals(myJakarta.readFromJson(path.toString()), myJakarta);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void updateTechnology_Test() throws IOException, InterruptedException {
        MyJakarta myJakarta = new MyJakarta(Paths.get(String.valueOf(new File(System.getProperty("user.dir"), fileFolder))));
        myJakarta.setVersion("1.0");
        myJakarta.setDescription("My Jakarta");

        Technology t = new Technology();
        t.setName("Maven");
        t.setDescription("Apache Maven is a software project management and comprehension tool.");

        Technology t1 = new Technology();
        t1.setName("Java 17");
        t1.setDescription("The JDK is a development environment for building applications using the Java programming language.");

        myJakarta.setTechnologies(List.of(t, t1));
        Path path = null;
        try {
            File dir = new File(System.getProperty("user.dir"), fileFolder);
            path = Paths.get(String.valueOf(dir), myJakarta.getFileName());
            myJakarta.writeToJson(path.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Thread.sleep(1000);
        t1.setName("Java 21");
        myJakarta.updateTechnology(t1);

        MyJakarta myJakartaUpdated = myJakarta.readFromJson(path.toString());

        assertEquals(myJakartaUpdated, myJakarta);
    }
}
