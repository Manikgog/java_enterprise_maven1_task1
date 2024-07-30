package ru.gogolin;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Data
public class MyJakarta {

    private String version;

    private String description;

    private List<Technology>  technologies;
    private UUID uuid = UUID.randomUUID();
    private final String fileName = "MyJakarta_" + uuid + ".json";

    public List<Technology> getTechnologies(){
        return Collections.unmodifiableList(technologies);
    }

    public void writeToJson(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(path), this);
    }

    public MyJakarta readFromJson(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(path), MyJakarta.class);
    }

    public void updateTechnology(Technology technology) throws IOException {
        for(Technology t : technologies){
            if(t.equals(technology)){
                t.setName(technology.getName());
                t.setDescription(technology.getDescription());
            }
        }
        writeToJson(fileName);
    }

}
