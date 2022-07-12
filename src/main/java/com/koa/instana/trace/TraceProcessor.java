package com.koa.instana.trace;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

public class TraceProcessor {
    private final TraceCalculator traceCalculator;

    public TraceProcessor(TraceCalculator traceCalculator) throws Exception {
        this.traceCalculator = traceCalculator;
    }

    public void process() throws Exception {
        // read in file
        File file = new File("./input/data.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String data;
        while ((data = br.readLine()) != null) {
            // format of input category|params|traces
            String[] splitData = data.split("\\|");
            String[] params = splitData[1].split(",");
            String[] traces = splitData[2].split("-");
            String result = "";
            switch (TraceCategories.valueOf(splitData[0])) {
                case AVERAGE:
                    result = traceCalculator.findAverageLatency(Arrays.stream(traces).toList());
                    break;
                case SHORTEST:
                    result = traceCalculator.findShortest(Arrays.stream(traces).toList());
            }
            System.out.println(result);
        }
    }
}
