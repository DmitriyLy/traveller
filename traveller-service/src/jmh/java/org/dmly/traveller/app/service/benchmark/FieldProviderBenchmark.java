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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Warmup(iterations = 10)
@Measurement(iterations = 10)
@Fork(value = 2, jvmArgsAppend = "-server")
@BenchmarkMode(Mode.AverageTime)
@State(Scope.Benchmark)
public class FieldProviderBenchmark {

    private Map<String, FieldProvider> providers;

    @Param({"Basic", "Cached", "Guava"})
    private String provider;

    @Setup
    public void setup() {
        providers = new HashMap<>();
        providers.put("Basic", new FieldProvider());
        providers.put("Cached", new CachedFieldProvider());
        providers.put("Guava", new GuavaCachedFieldProvider());
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
        providers.get(provider).getFieldNames(City.class, CityCopy.class);
    }

    @Benchmark
    public void getFieldNames_targetObject() {
        providers.get(provider).getFieldNames(City.class, Object.class);
    }
}
