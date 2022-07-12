package com.koa.instana.trace;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TraceUtils {
    public static HashMap<String, HashMap<String, Integer>> parseGraph() throws IOException {
        HashMap<String, HashMap<String, Integer>> graph = new HashMap<>();
        File file = new File("./input/graph.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String data;

        data = br.readLine();
        if (data != null) {
            // format of input category|params|traces
            String[] splitGraph = data.split(",");
            Arrays.stream(splitGraph).forEach(v -> {
                String startNode = String.valueOf(v.charAt(0));
                String endNode = String.valueOf(v.charAt(1));
                Integer latency = Integer.parseInt(String.valueOf(v.charAt(2)));
                HashMap<String, Integer> endNodeMap = new HashMap<>();
                HashMap<String, Integer> temp = graph.get(startNode);
                if (temp == null) {
                    endNodeMap.put(endNode, latency);
                    graph.put(startNode, endNodeMap);
                } else {
                    temp.put(endNode, latency);
                    graph.put(startNode, temp);
                }
            });
        }

        return graph;
    }

    public static List<TraceParams> parseTraceParams() throws IOException {
        File file = new File("./input/data.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String data;
        List<TraceParams> inputParams = new ArrayList<>();

        while ((data = br.readLine()) != null) {
            // format of input category|params|traces
            String[] splitData = data.split("\\|");
            String[] params = splitData[1].split(",");
            String[] traces = splitData[2].split("-");
            inputParams.add(new TraceParams(TraceCategories.valueOf(splitData[0]), List.of(params), List.of(traces)));
        }
        return inputParams;
    }
}
