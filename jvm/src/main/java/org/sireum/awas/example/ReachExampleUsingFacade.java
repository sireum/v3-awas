/*
 Copyright (c) 2017, Robby, Kansas State University
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions are met:

 1. Redistributions of source code must retain the above copyright notice, this
    list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright notice,
    this list of conditions and the following disclaimer in the documentation
    and/or other materials provided with the distribution.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.sireum.awas.example;

import org.sireum.awas.awasfacade.AwasGraph;
import org.sireum.awas.awasfacade.AwasGraphBuilder;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class ReachExampleUsingFacade {
    //sample arguments
    //"..org/sireum/awas/test/example/Query/PCAModelFlow.awas" "forward" "PulseOx"
    //"..org/sireum/awas/test/example/Query/PCAModelFlow.awas" "backward" "PulseOx.spo2"
    public static void main(String[] args) throws Exception {
        if (args.length == 3) {
            Optional<AwasGraph> graph = AwasGraphBuilder.build(args[0]);
            if (graph.isPresent()) {

                //Model in graph representation
                System.out.println(graph.get().getDotGraph());

                //computes reachability and returns the awas node represented as URI
                Set<String> result;
                if (Objects.equals(args[1].toLowerCase(), "forward")) {
                    result = graph.get().forwardReachUsingNames(args[2]);
                } else {
                    result = graph.get().backwardReachUsingNames(args[2]);
                }

                System.out.println(args[1] + " reachability for the criterion: " + args[2]);
                result.forEach(System.out::println);
            } else {
                System.out.println("Error in parsing AWAS file");
            }
        } else {
            System.out.println("ReachExampleUsingFacade Arg1 Arg2 Arg3");
            System.out.println("Arg1: file absolute path of awas model");
            System.out.println("Arg2: Direction ('forward' or 'backward')");
            System.out.println("Arg3: Component_Name.Optional_Port_Name (eg: Comp1.port1)");
        }
    }
}