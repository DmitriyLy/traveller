package org.dmly.traveller.app.service.benchmark;

import org.dmly.traveller.app.model.entity.base.AbstractEntity;
import org.dmly.traveller.app.model.entity.geography.City;
import org.dmly.traveller.app.model.entity.geography.Station;
import org.dmly.traveller.app.service.transform.impl.CachedFieldProvider;
import org.dmly.traveller.app.service.transform.impl.FieldProvider;
import org.dmly.traveller.app.service.transform.impl.GuavaCachedFieldProvider;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Warmup(iterations = 10)
@Measurement(iterations = 10)
@Fork(value = 2, jvmArgsAppend = "-server")
@BenchmarkMode(Mode.AverageTime)
@State(Scope.Benchmark)
public class FieldProviderBenchmark {

    private FieldProvider fieldProvider;
    private FieldProvider cachedProvider;
    private FieldProvider guavaCachedProvider;

    @Setup
    public void setup() {
        fieldProvider = new FieldProvider();
        cachedProvider = new CachedFieldProvider();
        guavaCachedProvider = new GuavaCachedFieldProvider();
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(".*")
                .mode(Mode.AverageTime)
                .timeUnit(TimeUnit.NANOSECONDS)
                .resultFormat(ResultFormatType.TEXT)
                .build();

        new Runner(options).run();
    }

    @Benchmark
    public void getFieldNames_targetCityCopy() {
        fieldProvider.getFieldNames(City.class, CityCopy.class);
    }

    @Benchmark
    public void getFieldNames_targetObject() {
        fieldProvider.getFieldNames(City.class, Object.class);
    }

    @Benchmark
    public void getFieldNames_cached_targetCityCopy() {
        cachedProvider.getFieldNames(City.class, CityCopy.class);
    }

    @Benchmark
    public void getFieldNames_guava_targetObject() {
        cachedProvider.getFieldNames(City.class, Object.class);
    }

    @Benchmark
    public void getFieldNames_guava_targetCityCopy() {
        guavaCachedProvider.getFieldNames(City.class, CityCopy.class);
    }

    @Benchmark
    public void getFieldNames_cached_targetObject() {
        guavaCachedProvider.getFieldNames(City.class, Object.class);
    }

    public static class CityCopy extends AbstractEntity {
        private String name;

        private String district;

        private String region;

        private Set<Station> stations;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public Set<Station> getStations() {
            return stations;
        }

        public void setStations(Set<Station> stations) {
            this.stations = stations;
        }
    }

}
