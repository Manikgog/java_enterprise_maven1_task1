package ru.gogolin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class App 
{
    public static final String JAKARTA_FILES = "jakarta-files";

    public static void main( String[] args ) throws IOException {
        prepare(JAKARTA_FILES);
        Technology t = new Technology();
        t.setName("Maven");
        t.setDescription("Apache Maven is a software project management and comprehension tool.");

        Technology t1 = new Technology();
        t1.setName("Java 17");
        t1.setDescription("The JDK is a development environment for building applications using the Java programming language.");

        MyJakarta myJakarta = new MyJakarta(Paths.get(String.valueOf(new File(System.getProperty("user.dir"), JAKARTA_FILES))));
        myJakarta.setDescription("My Jakarta");
        myJakarta.setVersion("1.0");
        List<Technology> listOfTechnologies = new ArrayList<>();
        listOfTechnologies.add(t);
        listOfTechnologies.add(t1);
        myJakarta.setTechnologies(listOfTechnologies);
        File dir = new File(System.getProperty("user.dir"), JAKARTA_FILES);
        Path path = Paths.get(String.valueOf(dir), myJakarta.getFileName());
        myJakarta.writeToJson(path.toString());
        System.out.println(myJakarta.readFromJson(String.valueOf(path)));

        t1.setName("Java 21");
        t1.setDescription("The JDK is a development environment for building applications using the Java programming language.");

        myJakarta.updateTechnology(t1);
    }

    public static void prepare(String folderName) throws IOException {
        File dir = new File(System.getProperty("user.dir"), JAKARTA_FILES);
        Path path = Paths.get(String.valueOf(dir));
        if (Files.notExists(path)) {
            Files.createDirectories(path);
        }
    }
}
