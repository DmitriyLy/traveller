package org.dmly.traveller.common.model.transform.impl;

import org.dmly.traveller.app.model.transform.annotation.DomainProperty;
import org.dmly.traveller.common.model.entity.base.AbstractEntity;
import org.dmly.traveller.common.model.entity.loader.EntityLoader;
import org.dmly.traveller.common.model.transform.Transformable;
import org.dmly.traveller.common.model.transform.Transformer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EntityReferenceTransformerTest {

    private Transformer entityReferenceTransformer;

    @Mock
    private EntityLoader entityLoader;

    @BeforeEach
    void setup() {
        entityReferenceTransformer = new EntityReferenceTransformer(entityLoader,
                new CachedFieldProvider());
    }

    @Test
    void transform_validEntity_referenceFieldsCopied() {
        ParentEntity parent = new ParentEntity();
        parent.setId(1);
        SourceEntity source = new SourceEntity();
        source.parent = parent;

        SourceDTO sourceDTO = entityReferenceTransformer.transform(source, SourceDTO.class);
        assertNotNull(sourceDTO);
        assertEquals(parent.getId(), sourceDTO.parentId);
    }

    @Test
    void untransform_validDTO_referenceFieldsCopied() {
        SourceDTO sourceDTO = new SourceDTO();
        sourceDTO.parentId = 1;

        ParentEntity parent = new ParentEntity();
        parent.setId(1);
        when(entityLoader.load(ParentEntity.class, 1)).thenReturn(parent);

        SourceEntity source = entityReferenceTransformer.untransform(sourceDTO, SourceEntity.class);
        assertNotNull(source);
        assertEquals(parent, source.parent);
    }

    public static class SourceEntity extends AbstractEntity {
        ParentEntity parent;
    }

    public static class ParentEntity extends AbstractEntity {
    }

    public static class SourceDTO implements Transformable<SourceEntity> {
        @DomainProperty("parent")
        int parentId;

        @Override
        public void transform(SourceEntity p) {

        }

        @Override
        public SourceEntity untransform(SourceEntity p) {
            return p;
        }

    }


}