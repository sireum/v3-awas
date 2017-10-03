package org.sireum.awas.awasfacade;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.sireum.awas.collector.Operator;
import org.sireum.awas.collector.ResultType;
import org.sireum.awas.fptc.FlowEdge;
import org.sireum.awas.fptc.FlowGraph;
import org.sireum.awas.fptc.FlowNode;
import org.sireum.awas.graph.AwasEdge;
import org.sireum.awas.symbol.SymbolTable;
import org.sireum.util.Tag;
import scala.Enumeration;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface Collector {


    Set<FlowNode> getNodes();

    Set<FlowEdge<FlowNode>> getEdges();

    SymbolTable getSymbolTable();

    FlowGraph<FlowNode> getGraph();

    Optional<Enumeration.Value> getResultType();

    Optional<Enumeration.Value> getOperator();

    Set<String> getCriteria();

    Set<String> getPorts();

    Map<String, Set<String>> getPortErrors();

    Set<String> getFlows();

    Set<String> getBehavior();

    Set<String> getModes();

    Set<String> getEvents();

    Set<Tag> getErrors();

    Set<Tag> getWarnings();

    Boolean hasErrors();

    List<org.sireum.awas.collector.Collector> getPaths();
}
