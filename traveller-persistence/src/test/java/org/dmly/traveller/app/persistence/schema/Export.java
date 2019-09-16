package org.dmly.traveller.app.persistence.schema;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.reflections.Reflections;

import javax.persistence.Entity;
import java.util.EnumSet;
import java.util.Set;

public class Export {
    public static void main(String[] args) {
        exportDatabase("sql/", MySQL5Dialect.class);
    }

    public static void exportDatabase(String folder, Class<? extends Dialect> dialect) {
        MetadataSources metadata = new MetadataSources(new StandardServiceRegistryBuilder().
                applySetting("hibernate.dialect", dialect.getName()).build());

        Reflections reflections = new Reflections("org.dmly.traveller.app.model.entity");

        Set<Class<?>> entityClasses = reflections.getTypesAnnotatedWith(Entity.class);
        entityClasses.forEach(metadata::addAnnotatedClass);

        SchemaExport schemaExport = new SchemaExport();
        schemaExport.setDelimiter(";");
        schemaExport.setOutputFile(folder + "schema_" + dialect.getSimpleName() + ".sql");

        schemaExport.create(EnumSet.of(TargetType.SCRIPT), metadata.buildMetadata());
    }
}
