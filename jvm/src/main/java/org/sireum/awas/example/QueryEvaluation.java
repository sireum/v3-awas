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

import org.sireum.awas.awasfacade.AwasGraph;
import org.sireum.awas.awasfacade.AwasGraphBuilder;
import org.sireum.awas.awasfacade.Collector;
import org.sireum.util.jvm.FileUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


public class QueryEvaluation {
    public static void main(String[] args) {
        //sample arguments :
        //This program accepts 3 arguments:
        //1st : Awas Model file *.awas
        //2nd : Awas query file *.aq
        //3rd : Output file

        if (args.length == 3) {
            Optional<AwasGraph> graph = AwasGraphBuilder.build(args[0]);

            if (graph.isPresent()) {
                Map<String, Collector> res = new HashMap<>();
                try {
                    res = graph.get().queryEvaluator(
                            FileUtil.readFile(FileUtil.toUri(args[1]))._1());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                String result = res.entrySet()
                        .stream()
                        .map(entry -> "\n" + entry.getKey() + ":\n  " + entry.getValue().getGraph() + "\n")
                        .collect(Collectors.joining("\n "));
                FileUtil.writeFile(FileUtil.toUri(args[2]), result);
                System.out.println("Results in file :" + FileUtil.toUri(args[2]));
            }
        } else {
            System.out.println("ReachExampleUsingFacade Arg1 Arg2 Arg3");
            System.out.println("Arg1: awas file: absolute path of awas model");
            System.out.println("Arg2: query file: absolute path of awas query");
            System.out.println("Arg3: Result file: absolute path of the output file");
        }
    }
}