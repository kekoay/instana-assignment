package com.koa.instana;

import com.koa.instana.trace.TraceCalculator;
import com.koa.instana.trace.TraceProcessor;
import com.koa.instana.trace.TraceUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class InstanaApplicationTests {
    HashMap<String, HashMap<String, Integer>> graph;
    TraceCalculator traceCalculator;
    TraceProcessor processor;

    @BeforeAll
    public void beforeAll() throws Exception {
        this.graph = TraceUtils.parseGraph();
        this.traceCalculator = new TraceCalculator(graph);
        this.processor = new TraceProcessor(traceCalculator);
    }

    @Test
    public void testAverageLatencyValid() {
        String result = traceCalculator.findAverageLatency(new ArrayList<String>(List.of("A", "B")));
        assertEquals("5", result);
    }

    @Test
    public void testAverageLatencyInValid() {
        String result = traceCalculator.findAverageLatency(new ArrayList<String>(List.of("E", "A")));
        assertEquals("NO SUCH TRACE", result);
    }

    @Test
    public void testShortestLatency() {
        String result = traceCalculator.findShortest(new ArrayList<String>(List.of("A", "C")));
        assertEquals("9", result);
    }

    @Test
    public void testShortestLatencyBtoB() {
        String result = traceCalculator.findShortest(new ArrayList<String>(List.of("B", "B")));
        assertEquals("9", result);
    }
}
