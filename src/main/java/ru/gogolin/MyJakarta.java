package ru.gogolin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Data
public class MyJakarta {
    @JsonProperty
    private String version;

    @JsonProperty
    private String description;

    @JsonProperty
    private List<Technology>  technologies;

    @JsonIgnore
    private UUID uuid = UUID.randomUUID();

    @JsonIgnore
    private final String fileName = "MyJakarta_" + uuid + ".json";

    @JsonIgnore
    private final Path path;

    public MyJakarta() {
        path = null;
    }

    public MyJakarta(Path path) {
        this.path = path;
    }

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
        Path path = Paths.get(this.path.toString(), this.fileName);
        writeToJson(path.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyJakarta myJakarta = (MyJakarta) o;
        return Objects.equals(version, myJakarta.version) && Objects.equals(description, myJakarta.description) && Objects.equals(technologies, myJakarta.technologies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, description, technologies);
    }
}
