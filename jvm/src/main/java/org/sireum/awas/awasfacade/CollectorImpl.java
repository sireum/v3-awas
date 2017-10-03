package org.sireum.awas.awasfacade;

import org.sireum.awas.collector.Operator;
import org.sireum.awas.collector.ResultType;
import org.sireum.awas.fptc.FlowEdge;
import org.sireum.awas.fptc.FlowGraph;
import org.sireum.awas.fptc.FlowNode;
import org.sireum.awas.fptc.FlowNode$;
import org.sireum.awas.graph.AwasEdge;
import org.sireum.awas.symbol.SymbolTable;
import org.sireum.util.Tag;
import scala.Enumeration;
import scala.collection.JavaConverters$;
import scala.collection.JavaConverters.*;

import java.util.*;

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
    public FlowGraph<FlowNode> getGraph() {
        return collector.getGraph();
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
    public List<org.sireum.awas.collector.Collector> getPaths() {
        return toJavaList(collector.getPaths());
    }
}
