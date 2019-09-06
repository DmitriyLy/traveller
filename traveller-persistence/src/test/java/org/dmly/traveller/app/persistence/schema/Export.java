package org.dmly.traveller.app.persistence.schema;

import com.google.common.collect.Sets;
import org.dmly.traveller.app.model.entity.geography.Address;
import org.dmly.traveller.app.model.entity.geography.Coordinate;
import org.dmly.traveller.app.model.entity.geography.Station;
import org.dmly.traveller.app.model.entity.person.Account;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.dialect.Dialect;
import org.dmly.traveller.app.model.entity.geography.City;
import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;

import java.util.EnumSet;
import java.util.Set;

public class Export {
    public static void main(String[] args) {
        exportDatabase("sql/", MySQL5Dialect.class);
    }

    public static void exportDatabase(String folder, Class<? extends Dialect> dialect) {
        MetadataSources metadata = new MetadataSources(new StandardServiceRegistryBuilder().
                applySetting("hibernate.dialect", dialect.getName()).build());

        Set<Class<?>> entityClasses = Sets.newHashSet(City.class, Address.class, Station.class, Coordinate.class,
                Account.class);
        entityClasses.forEach(metadata::addAnnotatedClass);

        SchemaExport schemaExport = new SchemaExport();
        schemaExport.setDelimiter(";");
        schemaExport.setOutputFile(folder + "schema_" + dialect.getSimpleName() + ".sql");

        schemaExport.create(EnumSet.of(TargetType.SCRIPT), metadata.buildMetadata());
    }
}
