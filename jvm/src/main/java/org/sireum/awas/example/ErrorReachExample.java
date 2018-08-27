/*
 * // #Sireum
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
 *
 */

package org.sireum.awas.example;


import org.sireum.awas.awasfacade.AwasGraph;
import org.sireum.awas.awasfacade.AwasGraphBuilder;

import java.io.File;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ErrorReachExample {
    public static void main(String[] args) {
        String path = "awas/jvm/src/test/resources/org/sireum/awas/test/example/Query/abcEF.awas";
        Boolean isForward = true;


        Optional<AwasGraph> graph = AwasGraphBuilder.
                build(path);
        if (graph.isPresent()) {

            Map<String, Set<String>> result;
            if (isForward) {
                result = graph.get().forwardErrorReachUsingNames("A.aIn", "Error.High");
            } else {
                result = graph.get().backwardErrorReachUsingNames("A.aIn", "Error.High");
            }

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

}
