package org.dmly.traveller.app.model.entity.geography;

import org.dmly.traveller.app.model.entity.transport.TransportType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CityTest {
    private City city;

    @BeforeEach
    void setup() {
        city = new City("Odessa");
    }

    @Nested
    @DisplayName("Checks addStation method")
    class AddStationTest {
        @Test
        void successIfValidStation() {
            Station station = city.addStation(TransportType.AUTO);

            assertTrue(containsStation(city, station));
            assertEquals(city, station.getCity());
        }

        @Test
        void throwsExceptionIfNullTransportType() {
            assertThrows(NullPointerException.class, () -> city.addStation(null));
        }
    }

    @Nested
    @DisplayName("Checks addStation method")
    class RemoveStationTest {
        @Test
        void success() {
            Station station = city.addStation(TransportType.AVIA);
            city.removeStation(station);

            assertTrue(city.getStations().isEmpty());
        }

        @Test
        void throwsExceptionIfStationNull() {
            assertThrows(NullPointerException.class, () -> city.removeStation(null));
        }
    }

    private boolean containsStation(City city, Station station) {
        return city.getStations().contains(station);
    }
}