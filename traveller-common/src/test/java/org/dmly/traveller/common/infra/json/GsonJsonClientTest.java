package org.dmly.traveller.common.infra.json;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import lombok.Getter;
import lombok.Setter;
import org.dmly.traveller.common.infra.exception.flow.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.PersistenceException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.*;

public class GsonJsonClientTest {

    private JsonClient jsonClient;

    @BeforeEach
    void setup() {
        jsonClient = new GsonJsonClient();
    }

    @Test
    void toJson_validObject_returnsJson() {
        Person person = new Person();
        person.setAge(20);
        person.setName("John");
        person.setSkills(new String[] { "Java", "Js" });

        String actual = jsonClient.toJson(person);
        String expected = loadJson("json/person.json");
        assertEquals(expected, actual);
    }

    @Test
    void fromJson_validJson_returnsPerson() {
        String json = loadJson("json/person.json");
        Person actual = jsonClient.fromJson(json, Person.class);

        assertNotNull(actual);
        assertEquals(actual.getAge(), 20);
        assertEquals(actual.getName(), "John");
        assertEquals(actual.getSkills().length, 2);
    }

    @Test
    void fromJson_invalidJson_throwsException() {
        String invalidJson = "invalid-json";
        assertThrows(ValidationException.class, () -> jsonClient.fromJson(invalidJson, Person.class));
    }

    private String loadJson(String fileName) {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(fileName)) {
            return CharStreams.toString(new InputStreamReader(in, Charsets.UTF_8));
        } catch (IOException e) {
            throw new PersistenceException(e);
        }
    }

    @Getter
    @Setter
    public static class Person {
        private int age;

        private String name;

        private String[] skills;
    }

}