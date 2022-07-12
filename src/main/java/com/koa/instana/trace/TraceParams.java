package com.koa.instana.trace;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class TraceParams {
    private TraceCategories traceCategories;
    private List<String> params;
    private List<String> traces;
}
