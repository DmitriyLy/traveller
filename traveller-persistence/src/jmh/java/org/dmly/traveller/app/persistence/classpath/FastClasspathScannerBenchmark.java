package org.dmly.traveller.app.persistence.classpath;

import org.dmly.traveller.app.persistence.repository.CityRepository;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.lukehutch.fastclasspathscanner.scanner.ScanResult;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import javax.persistence.Entity;
import java.util.List;

@Warmup(iterations = 10)
@Measurement(iterations = 10)
@Fork(value = 2, jvmArgsAppend = "-server")
@BenchmarkMode(Mode.Throughput)
@State(Scope.Benchmark)
public class FastClasspathScannerBenchmark {

    @Benchmark
    public void findClassesAsStringsByAnnotationEntity(Blackhole blackhole) {
        ScanResult scanResult = new
                FastClasspathScanner("org.dmly.traveller.app.model.entity").scan();

        List<String> classes = scanResult.getNamesOfClassesWithAnnotation(Entity.class);

        blackhole.consume(classes);
    }

    @Benchmark
    public void findClassesByAnnotationEntity(Blackhole blackhole) {
        ScanResult scanResult = new
                FastClasspathScanner("org.dmly.traveller.app.model.entity").scan();

        List<String> classes = scanResult.getNamesOfClassesWithAnnotation(Entity.class);

        blackhole.consume(scanResult.classNamesToClassRefs(classes));
    }

    @Benchmark
    public void findClassesAsStringsThatImplementingCityRepositoryInterface(Blackhole blackhole) {
        ScanResult scanResult = new
                FastClasspathScanner("oorg.dmly.traveller.app").scan();

        List<String> classes = scanResult.getNamesOfClassesImplementing(CityRepository.class);

        blackhole.consume(classes);
    }

    @Benchmark
    public void findClassesThatImplementingCityRepositoryInterface(Blackhole blackhole) {
        ScanResult scanResult = new
                FastClasspathScanner("org.dmly.traveller.app").scan();

        List<String> classes = scanResult.getNamesOfClassesImplementing(CityRepository.class);

        blackhole.consume(scanResult.classNamesToClassRefs(classes));
    }

    public static void main(String[] args) throws Exception {
        Options opts = new OptionsBuilder()
                .include("FastClasspathScannerBenchmark")
                .resultFormat(ResultFormatType.TEXT).build();
        new Runner(opts).run();
    }

}
