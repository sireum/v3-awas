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

package org.sireum.awas.awasfacade;


import org.sireum.awas.ast.Builder;
import org.sireum.awas.ast.Model;
import org.sireum.awas.example.ReachabilityExample;
import org.sireum.awas.fptc.FlowGraph;
import org.sireum.awas.fptc.FlowGraph$;
import org.sireum.awas.fptc.FlowNode;
import org.sireum.awas.symbol.SymbolTable;
import org.sireum.awas.symbol.SymbolTable$;
import org.sireum.awas.util.JavaConverters;
import org.sireum.util.ConsoleTagReporter;
import org.sireum.util.jvm.FileUtil;
import scala.Some;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class AwasGraphBuilder {

    public static Optional<AwasGraph> build(String path) throws Exception {
        Path basePath = Paths.get(FileUtil.fileUri(ReachabilityExample.class, ".."));
        Path relativeUri = basePath.relativize(Paths.get(FileUtil.toUri(path)));
        Some<String> optUri = Some.apply(relativeUri.toString());

        Optional<Model> modelOpt = JavaConverters.toJavaOptional(Builder.apply(optUri,
                FileUtil.readFile(FileUtil.toUri(path))._1(),
                Builder.apply$default$3(),
                Builder.apply$default$4()));
        if (modelOpt.isPresent()) {
            SymbolTable st = SymbolTable$.MODULE$.apply(modelOpt.get(),
                    new ConsoleTagReporter());
            //build graph
            FlowGraph<FlowNode> graph = FlowGraph$.MODULE$.apply(modelOpt.get(), st);

            final AwasGraph ag = new AwasGraphImpl(graph, st);
            return Optional.of(ag);

        } else {
            return Optional.empty();
        }
    }
}
