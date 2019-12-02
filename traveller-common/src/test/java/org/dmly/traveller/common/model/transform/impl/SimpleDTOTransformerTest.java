package org.dmly.traveller.common.model.transform.impl;

import org.dmly.traveller.common.infra.exception.flow.InvalidParameterException;
import org.dmly.traveller.common.model.entity.base.AbstractEntity;
import org.dmly.traveller.common.model.transform.Transformable;
import org.dmly.traveller.common.model.transform.Transformer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SimpleDTOTransformerTest {
    private Transformer transformer;

    @BeforeEach
    public void setup() {
        transformer = new SimpleDTOTransformer(new CachedFieldProvider());
    }

    @Test
    void transform_fromSourceToDestination_success() {
        Source source = new Source();
        source.value = 10;
        source.text = "ab";

        Destination destination = transformer.transform(source, Destination.class);
        assertNotNull(destination);
        assertEquals(source.value, destination.value);
        assertEquals(source.text, destination.text);
    }

    @Test
    void transform_nullCity_exception() {
        assertThrows(InvalidParameterException.class, () -> transformer.transform(null, Destination.class));
    }

    @Test
    void testTransformNullDTOClassFailure() {
        assertThrows(InvalidParameterException.class,
                () -> transformer.transform(new Source(), (Class<Destination>) null));
    }

    @Test
    void untransform_fromSourceToDestination_success() {
        Destination destination = new Destination();
        destination.value = 10;
        destination.text = "ab";

        Source source = transformer.untransform(destination, Source.class);
        assertNotNull(source);
        assertEquals(destination.value, source.value);
        assertEquals(destination.text, source.text);
    }

    @Test
    void untransform_nullDestination_exception() {
        assertThrows(InvalidParameterException.class, () -> transformer.untransform(null, Source.class));
    }

    @Test
    public void untransform_nullEntityClass_exception() {
        assertThrows(InvalidParameterException.class,
                () -> transformer.untransform(new Destination(), (Class<Source>) null));
    }

    public static class Source extends AbstractEntity {
        int value;

        String text;
    }

    public static class Destination implements Transformable<Source> {
        int value;

        String text;

        @Override
        public void transform(Source p) {

        }

        @Override
        public Source untransform(Source p) {
            return p;
        }
    }

}