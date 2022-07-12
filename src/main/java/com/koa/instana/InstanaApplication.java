package com.koa.instana;

import com.koa.instana.trace.TraceCalculator;
import com.koa.instana.trace.TraceProcessor;
import com.koa.instana.trace.TraceUtils;

import java.util.HashMap;

public class InstanaApplication {
    public static void main(String[] args) throws Exception {
        HashMap<String, HashMap<String, Integer>> graph = TraceUtils.parseGraph();
        TraceCalculator calculator = new TraceCalculator(graph);
        TraceProcessor processor = new TraceProcessor(calculator);
        processor.process();
    }
}
