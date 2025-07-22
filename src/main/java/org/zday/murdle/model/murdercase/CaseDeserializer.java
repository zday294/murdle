package org.zday.murdle.model.murdercase;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.zday.murdle.model.murdercase.clues.Clue;
import org.zday.murdle.model.murdercase.resolution.Resolution;
import org.zday.murdle.model.murdercase.suspect.Location;
import org.zday.murdle.model.murdercase.suspect.Person;
import org.zday.murdle.model.murdercase.suspect.Weapon;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CaseDeserializer extends StdDeserializer<Case> {

    public CaseDeserializer() {
        this(null);
    }

    public CaseDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Case deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        Case murderCase = new Case();

        ObjectMapper mapper = new ObjectMapper();

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        murderCase.setDescription(node.get("description").asText());

        List<String> personNameList = mapper.readValue(node.get("personList").toString(), List.class);
        List<Person> personList = deserializeSuspectList(personNameList, mapper, Person.class);
        murderCase.setPersonList(personList);

        List<String > locationNameList = mapper.readValue(node.get("locationList").toString(), List.class);
        List<Location> locationList = deserializeSuspectList(locationNameList, mapper, Location.class);
        murderCase.setLocationList(locationList);

        List<String> weaponNameList = mapper.readValue(node.get("weaponList").toString(), List.class);
        List<Weapon> weaponList = deserializeSuspectList(weaponNameList, mapper, Weapon.class);
        murderCase.setWeaponList(weaponList);

        List<Clue> clueList = mapper.readValue(node.get("cluesList").toString(), new TypeReference<>(){});
        murderCase.setCluesList(clueList);

        Resolution resolution = mapper.readValue(node.get("resolution").toString(), Resolution.class);
        murderCase.setResolution(resolution);

        return murderCase;
    }

    private <T> List<T> deserializeSuspectList(List<String> suspectNameList, ObjectMapper mapper, Class<T> suspectClass) throws IOException {
        List<T> suspects = new ArrayList<>();

        for (String name: suspectNameList) {
            // load the weapon from the resources
            try (InputStream stream = getClass().getResourceAsStream("data/"+ suspectClass.getName().toLowerCase() + "s/" + name + ".json")) {
                if (stream == null) {
                    throw new IOException("Suspect file not found: " + name);
                }
                T suspect = mapper.readValue(stream, suspectClass);
                suspects.add(suspect);
            } catch (IOException e) {
                throw new IOException("Error loading suspect: " + name, e);
            }

        }
        return suspects;
    }


}
