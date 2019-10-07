package org.dmly.traveller.app.persistence.classpath;

import org.dmly.traveller.app.persistence.repository.CityRepository;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.reflections.Reflections;

import javax.persistence.Entity;

@Warmup(iterations = 10)
@Measurement(iterations = 10)
@Fork(value = 2, jvmArgsAppend = "-server")
@BenchmarkMode(Mode.Throughput)
public class ReflectionsBenchmark {

    @Benchmark
    public void findClassesByAnnotationEntity(Blackhole blackhole) {
        Reflections reflections = new Reflections("org.dmly.traveller.app.model.entity");

        blackhole.consume(reflections.getTypesAnnotatedWith(Entity.class));
    }

    @Benchmark
    public void findClassesImplementingCityRepositoryInterface(Blackhole blackhole) {
        Reflections reflections = new Reflections("org.dmly.traveller.app");

        blackhole.consume(reflections.getSubTypesOf(CityRepository.class));
    }

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder().include(".*").resultFormat(ResultFormatType.TEXT).build();

        new Runner(options).run();
    }

}
