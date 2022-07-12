package com.koa.instana.trace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class TraceCalculator {
    private final HashMap<String, HashMap<String, Integer>> graph;
    private final String errorMessage = "NO SUCH TRACE";

    public TraceCalculator(HashMap<String, HashMap<String, Integer>> graph) {
        this.graph = graph;
    }

    public String findAverageLatency(List<String> trace) {
        Integer averageLatency = 0;
        for (int i = 0; i < trace.size() - 1; i++) {
            String curNode = trace.get(i);
            String nextNode = trace.get(i + 1);
            try {
                Integer curLatency = graph.get(curNode).get(nextNode);
                if (curLatency == null) {
                    return errorMessage;
                }
                averageLatency += curLatency;
            } catch (Exception e) {
                return errorMessage;
            }
        }

        return String.valueOf(averageLatency);
    }

    public String findNumberOfOriginating(List<String> traces, Integer hops, String hopType) {
        Integer numberOfTraces = 0;
        switch (hopType) {
            case "MIN":
                break;
            case "MAX":
                break;
            case "EXACT":
                break;
        }
        return String.valueOf(numberOfTraces);
    }

    public String findShortest(List<String> traces) {
        // find all of the possible combinations of traces
        HashMap<String, Integer> combinations = new HashMap<>();
        boolean isMore = true;
        String rootNode = traces.get(0);
        String finalNode = traces.get(1);
        ArrayList<TraceNode> queue = new ArrayList<>();
        queue.add(new TraceNode(rootNode, graph.get(rootNode).keySet()));
        String prevNode = rootNode;

        Integer curCountNode = 0;
        while (isMore) {
            // traverse the tree
            // possible moves
            TraceNode tempNode = queue.get(curCountNode);

            // if it finds the final node
            if (Objects.equals(tempNode.getCurNode(), finalNode) && curCountNode > 0) {
                // count number of traces
                StringBuilder trace = new StringBuilder();
                int latency = 0;
                boolean isValidTrace = true;
                for (int i = 0; i < queue.size() - 1; i++) {
                    trace.append(queue.get(i).getCurNode());
                    Integer traceLatency = graph.get(queue.get(i).getCurNode()).get(queue.get(i + 1).getCurNode());
                    if (traceLatency == null) {
                        isValidTrace = false;
                        break;
                    }
                    latency += traceLatency;
                }
                if (isValidTrace) {
                    combinations.putIfAbsent(trace.toString(), latency);
                }
                // dequeue the node
                queue.remove(queue.size() - 1);
                curCountNode--;
                tempNode = queue.get(curCountNode);
                prevNode = getPrevNode(queue);
            }
            // if there is another possible option continue to the next node
            Set<String> nextMoves = new HashSet<>(tempNode.getMoves());
            nextMoves.removeAll(tempNode.getVisited());
            if (nextMoves.size() == 0) {
                int reduceBy = queue.size() - 1;
                if (reduceBy > 0) {
                    queue.remove(queue.size() - 1);
                    curCountNode--;
                    prevNode = getPrevNode(queue);
                }
                tempNode = queue.get(curCountNode);
                if (Objects.equals(tempNode.getCurNode(), rootNode) && queue.size() == 1) {
                    isMore = false;
                }
            } else {
                String nextMove = nextMoves.stream().findFirst().orElse(null);
                Integer traceLatency = graph.get(tempNode.getCurNode()).get(nextMove);
                // add to the visted list
                queue.get(queue.size() - 1).getVisited().add(nextMove);
                if (traceLatency != null && !Objects.equals(prevNode, nextMove)) {
                    // add new node to the queue
                    queue.add(new TraceNode(nextMove, graph.get(nextMove).keySet()));
                    curCountNode++;
                    prevNode = getPrevNode(queue);
                }
            }
        }
        // find the shortest latency trace
        Integer shortestTrace = 0;
        boolean isFirst = true;
        for (String key : combinations.keySet()) {
            if (isFirst) {
                shortestTrace = combinations.get(key);
                isFirst = false;
            }
            if (shortestTrace > combinations.get(key)) {
                shortestTrace = combinations.get(key);
            }
        }
        return String.valueOf(shortestTrace);
    }

    private String getPrevNode(ArrayList<TraceNode> queue) {
        if (queue.size() == 1) {
            return queue.get(0).getCurNode();
        } else {
            return queue.get(queue.size() - 2).getCurNode();
        }
    }
}
