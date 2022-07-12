package com.koa.instana.trace;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class TraceNode {
    private String curNode;
    private Set<String> moves;
    private Set<String> visited;

    public TraceNode(String curNode, Set<String> moves) {
        this.curNode = curNode;
        this.moves = moves;
        this.visited = new HashSet<>();
    }
}
