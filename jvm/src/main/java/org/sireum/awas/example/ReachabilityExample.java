/*
 *
 *  Copyright (c) 2017, Hariharan Thiagarajan, Kansas State University
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice, this
 *     list of conditions and the following disclaimer.
 *  2. Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package org.sireum.awas.example;

import org.sireum.awas.ast.Builder;
import org.sireum.awas.ast.Model;
import org.sireum.awas.fptc.FlowEdge;
import org.sireum.awas.fptc.FlowGraph;
import org.sireum.awas.fptc.FlowGraph$;
import org.sireum.awas.fptc.FlowNode;
import org.sireum.awas.reachability.PortReachability;
import org.sireum.awas.reachability.PortReachability$;
import org.sireum.awas.symbol.SymbolTable;
import org.sireum.awas.symbol.SymbolTable$;
import org.sireum.awas.symbol.SymbolTableHelper;
import org.sireum.awas.util.JavaConverters;
import org.sireum.util.ConsoleTagReporter;
import org.sireum.util.jvm.FileUtil;
import scala.Some;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;

public class ReachabilityExample {
    public static void main(String[] args) {

        if (args.length > 2 && args.length < 4) {

            //construct the AST from the AWAS file
            Path basePath = Paths.get(FileUtil.fileUri(ReachabilityExample.class, ".."));
            Path relativeUri = basePath.relativize(Paths.get(FileUtil.toUri(args[0])));
            Some<String> optUri = Some.apply(relativeUri.toString());

            Optional<Model> modelOpt = JavaConverters.toJavaOptional(Builder.apply(optUri,
                    FileUtil.readFile(FileUtil.toUri(args[0]))._1(),
                    Builder.apply$default$3(),
                    Builder.apply$default$4()));

            //if AST is constructed, build symbol table
            if (modelOpt.isPresent()) {
                SymbolTable st = SymbolTable$.MODULE$.apply(modelOpt.get(),
                        new ConsoleTagReporter());
                //build graph
                FlowGraph<FlowNode, FlowEdge<FlowNode>> graph = FlowGraph$.MODULE$.apply(modelOpt.get(), st, false);
                System.out.println("Constructed Graph: ");
                System.out.println(graph.getDot());

                //query the graph step 1: get the uri of the criterion
                String criterion = null;
                if (args.length == 3) {
                    Optional<String> uri = JavaConverters.toJavaOptional(
                            SymbolTableHelper.getUriFromString(st, args[2]));
                    criterion = uri.orElse(null);
                }

                // step 2: Construct the reachability object
                PortReachability<FlowNode> pr = PortReachability$.MODULE$.apply(st);

                // step 3: invoke the right method based on the direction
                if (criterion != null) {
                    if (Objects.equals(args[1].toLowerCase(), "forward")) {
                        System.out.println("Forward reachability for the criterion: " + args[2]);
                        JavaConverters.toJavaSet(
                                pr.forwardReach(criterion).getPorts()).forEach(System.out::println);
                    } else {
                        System.out.println("Backward reachability for the criterion: " + args[2]);
                        JavaConverters.toJavaSet(
                                pr.backwardReach(criterion).getPorts()).forEach(System.out::println);
                    }
                } else {
                    if (args.length == 3)
                        System.out.println("Incorrect criterion :" + args[2]);
                    else
                        System.out.println("Provide Criterion");
                }
            } else {
                System.out.println("Error in parsing AWAS file");
            }
        } else {
            System.out.println("Arg1: Input AWAS file");
            System.out.println("Arg2: Direction ('forward' or 'backward')");
            System.out.println("Arg3: Component_Name.Optional_Port_Name (eg: Comp1.port1)");
        }
    }
}