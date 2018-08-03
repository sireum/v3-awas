package org.sireum.awas.awasfacade;

import org.sireum.awas.fptc.FlowEdge;
import org.sireum.awas.fptc.FlowGraph;
import org.sireum.awas.fptc.FlowNode;
import org.sireum.awas.symbol.SymbolTable;
import org.sireum.util.Tag;
import scala.Enumeration;

import java.util.*;
import java.util.stream.Collectors;

import static org.sireum.awas.util.JavaConverters.*;

public class CollectorImpl implements Collector {

    private org.sireum.awas.collector.Collector collector;

    public CollectorImpl(org.sireum.awas.collector.Collector collector) {
        this.collector = collector;
    }

    @Override
    public Set<FlowNode> getNodes() {
        return toJavaSet(collector.getNodes());
    }

    @Override
    public Set<FlowEdge<FlowNode>> getEdges() {
        return toJavaSet(collector.getEdges());
    }

    @Override
    public SymbolTable getSymbolTable() {
        return collector.getSymbolTable();
    }

    @Override
    public Set<FlowGraph<FlowNode, FlowEdge<FlowNode>>> getGraph() {
        return toJavaSet(collector.getGraphs());
    }

    @Override
    public Optional<Enumeration.Value> getResultType() {
        return toJavaOptional(collector.getResultType());
    }

    @Override
    public Optional<Enumeration.Value> getOperator() {
        return toJavaOptional(collector.getOperator());
    }

    @Override
    public Set<String> getCriteria() {
        return toJavaSet(collector.getCriteria());
    }

    @Override
    public Set<String> getPorts() {
        return toJavaSet(collector.getPorts());
    }

    @Override
    public Map<String, Set<String>> getPortErrors() {
        Map<String, Set<String>> temp = new HashMap<>();
        toJavaMap(collector.getPortErrors()).forEach((k, v) -> {
            Set<String> setVal = toJavaSet(v);
            temp.put(k, setVal);
        });
        return temp;
    }

    @Override
    public Set<String> getFlows() {
        return toJavaSet(collector.getFlows());
    }

    @Override
    public Set<String> getBehavior() {
        return toJavaSet(collector.getBehavior());
    }

    @Override
    public Set<String> getModes() {
        return toJavaSet(collector.getModes());
    }

    @Override
    public Set<String> getEvents() {
        return toJavaSet(collector.getEvents());
    }

    @Override
    public Set<Tag> getErrors() {
        return toJavaSet(collector.getErrors());
    }

    @Override
    public Set<Tag> getWarnings() {
        return toJavaSet(collector.getWarnings());
    }

    @Override
    public Boolean hasErrors() {
        return collector.hasErrors();
    }

    @Override
    public List<Collector> getPaths() {
        return toJavaList(collector.getPaths().toSeq()).stream().map(c ->
                (Collector) new CollectorImpl(c)).collect(Collectors.toList());
    }

}
