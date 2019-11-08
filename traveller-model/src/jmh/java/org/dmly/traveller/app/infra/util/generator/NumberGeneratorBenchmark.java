package org.dmly.traveller.app.infra.util.generator;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
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
public class NumberGeneratorBenchmark {

    private static final int GENERATION_LIMIT = 100;

    private Map<String, NumberGenerator> generators;

    @Param({ "Seq", "Random", "Secured" })
    private String generator;

    @Setup
    public void setup() {
        generators = new HashMap<>(3);
        generators.put("Seq", new SequentialNumberGenerator(GENERATION_LIMIT));
        generators.put("Random", new RandomNumberGenerator(GENERATION_LIMIT));
        generators.put("Secured", new SecureRandomNumberGenerator(GENERATION_LIMIT));
    }

    @Benchmark
    public int generate_singleOperation() {
        return generators.get(generator).generate();
    }

    public static void main(String[] args) throws Exception {
        Options opts = new OptionsBuilder().include(".*").mode(Mode.AverageTime).timeUnit(TimeUnit.NANOSECONDS)
                .resultFormat(ResultFormatType.TEXT).build();

        new Runner(opts).run();
    }

}
