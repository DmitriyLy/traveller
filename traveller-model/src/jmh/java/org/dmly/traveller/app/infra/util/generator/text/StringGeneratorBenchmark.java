package org.dmly.traveller.app.infra.util.generator.text;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@Warmup(iterations = 10)
@Measurement(iterations = 10)
@Fork(value = 2, jvmArgsAppend = "-server")
@BenchmarkMode(Mode.AverageTime)
@State(Scope.Benchmark)
public class StringGeneratorBenchmark {

    private static final int STRING_SIZE = 32;

    private StringGenerator digitGenerator;

    private StringGenerator uuidGenerator;

    @Setup
    public void setup() {
        digitGenerator = new RandomDigitStringGenerator(STRING_SIZE);
        uuidGenerator = new UUIDStringGenerator();
    }

    @Benchmark
    public String generate_digitGenerator() {
        return digitGenerator.generate();
    }

    @Benchmark
    public String generate_uuidGenerator() {
        return uuidGenerator.generate();
    }

    public static void main(String[] args) throws Exception {
        Options opts = new OptionsBuilder().include(".*").mode(Mode.AverageTime).timeUnit(TimeUnit.NANOSECONDS)
                .resultFormat(ResultFormatType.TEXT).build();

        new Runner(opts).run();
    }
}