/*
 *
 * Copyright (c) 2019, Robby, Kansas State University
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.sireum.awas.example;

import org.sireum.awas.ast.Model;
import org.sireum.awas.awasfacade.AwasGraph;
import org.sireum.awas.awasfacade.AwasGraphImpl;
import org.sireum.awas.flow.*;
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

        String modelPath = "/Users/hariharan/Documents/workspace/sireum-v3/awas/jvm/src/test/resources/org/sireum/awas/test/example/aadl-json/simple_case_infoflow.json";
        Model awasModel = Aadl2Awas$.MODULE$.apply(FileUtil.readFile(FileUtil.toUri(modelPath))._1).get();
        SymbolTable st = SymbolTable$.MODULE$.apply(awasModel, new ConsoleTagReporter()); //reporter is slightly changed in my version
        FlowGraph<FlowNode, FlowEdge<FlowNode>> graph = FlowGraph$.MODULE$.apply(awasModel, st, false); //building all the graphs and returning only the top level graph
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
