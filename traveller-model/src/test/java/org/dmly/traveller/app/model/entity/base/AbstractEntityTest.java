package org.dmly.traveller.app.model.entity.base;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

public class AbstractEntityTest {

    @Test
    public void equals_IdentifierIsEqual_ReturnsTrue() {
        SampleEntity entity1 = new SampleEntity();
        entity1.setId(1);
        entity1.setCreatedAt(LocalDateTime.now());

        SampleEntity entity2 = new SampleEntity();
        entity2.setId(1);
        entity2.setCreatedAt(LocalDateTime.now().minusDays(1));
        Assert.assertTrue(entity1.equals(entity2));
    }

    public static class SampleEntity extends AbstractEntity {

    }

}