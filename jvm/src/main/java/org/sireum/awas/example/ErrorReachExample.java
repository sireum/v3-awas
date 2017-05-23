package org.sireum.awas.example;


import org.sireum.awas.awasfacade.AwasGraph;
import org.sireum.awas.awasfacade.AwasGraphBuilder;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ErrorReachExample {
    public static void main(String[] args) throws Exception {
        String path = "/workspace-v3/sireum-v3/awas/jvm/src/test/resources/org/sireum/awas/test/example/Query/abcEF.awas";
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

            result.forEach((k, v) ->
                    System.out.println(k +
                            "-> {" +
                            v.stream().map(Object::toString).collect(Collectors.joining(", "))
                            + "}"));

        }
    }

}
