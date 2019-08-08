package org.sireum.awas.example;

import org.sireum.aadl.ir.Aadl;
import org.sireum.awas.ast.Model;
import org.sireum.awas.awasfacade.AwasGraph;
import org.sireum.awas.awasfacade.AwasGraphBuilder;
import org.sireum.awas.awasfacade.AwasGraphImpl;
import org.sireum.awas.fptc.*;
import org.sireum.awas.slang.Aadl2Awas$;
import org.sireum.awas.symbol.SymbolTable;
import org.sireum.awas.symbol.SymbolTable$;
import org.sireum.awas.symbol.SymbolTableHelper$;
import org.sireum.util.ConsoleTagReporter;
import org.sireum.util.jvm.FileUtil;

import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.sireum.awas.util.JavaConverters.toJavaOptional;
import static org.sireum.awas.util.JavaConverters.toJavaSet;

public class NeighbourhoodAnalysis {
    public static void main(String[] args) {
        String modelPath = "/Users/hariharan/Documents/workspace/sireum-v3/awas/jvm/src/test/resources/org/sireum/awas/test/example/aadl-json/PulseOx_Forwarding.json";
        Model awasModel = Aadl2Awas$.MODULE$.apply(FileUtil.readFile(FileUtil.toUri(modelPath))._1).get();
        SymbolTable st = SymbolTable$.MODULE$.apply(awasModel, new ConsoleTagReporter()); //reporter is slightly changed in my version
        FlowGraph<FlowNode, FlowEdge<FlowNode>> graph = FlowGraph$.MODULE$.apply(awasModel, st); //building all the graphs and returning only the top level graph
        Optional<FlowNode> flowNode = toJavaOptional(FlowNode$.MODULE$.getNode(SymbolTableHelper$.MODULE$.getUriFromString(st, "patient").get()));
        Set<FlowNode> neighbourNodes = new HashSet<FlowNode>();

        if (flowNode.isPresent()) {
            FlowGraph<FlowNode, FlowEdge<FlowNode>> nodeGraph = flowNode.get().getOwner(); //getting the graph that contains the input component
            neighbourNodes.addAll(toJavaSet(nodeGraph.getPredecessorNodes(flowNode.get()))); //adding predecessor nodes
            neighbourNodes.addAll(toJavaSet(nodeGraph.getSuccessorNodes(flowNode.get()))); //adding successor nodes
        }

        neighbourNodes.forEach(node -> {
            System.out.println(SymbolTableHelper$.MODULE$.uri2CanonicalName(node.getUri()));
        });

        String path = "awas/jvm/src/test/resources/org/sireum/awas/test/example/aadl-json/PulseOx_Forwarding.json";

        AwasGraph awasgraph = new AwasGraphImpl(graph, st);


        Map<String, Set<String>> result;
        result = awasgraph.backwardErrorReachUsingNames("appDisplay.DerivedAlarm", "PulseOx_Forwarding_Errors.MissedAlarm");
        result.forEach((k, v) -> {
            String[] ports = k.split(File.separator);
            System.out.println(ports[0] +
                    ports[ports.length - 1] +
                    " -> {" +
                    v.stream().map(Object::toString).map(f -> f.substring(f.lastIndexOf("$")))
                            .collect(Collectors.joining(", ")) + "}");
        });


    }


}
