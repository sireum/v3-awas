package org.sireum.awas.example;


import org.sireum.awas.awasfacade.AwasGraph;
import org.sireum.awas.awasfacade.AwasGraphBuilder;

import java.io.File;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ErrorReachExample {
    public static void main(String[] args) throws Exception {
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
