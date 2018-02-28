package org.sireum.awas.example;

import org.sireum.awas.awasfacade.AwasGraph;
import org.sireum.awas.awasfacade.AwasGraphBuilder;
import org.sireum.util.jvm.FileUtil;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


public class QueryEvaluation {
    public static void main(String[] args) throws Exception {
        //sample arguments :
        //This program accepts 3 arguments:
        //1st : Awas Model file *.awas
        //2nd : Awas query file *.aq
        //3rd : Output file

        if (args.length == 3) {
            Optional<AwasGraph> graph = AwasGraphBuilder.build(args[0]);

            if (graph.isPresent()) {
                Map<String, String> res = graph.get().queryEvaluator(
                        FileUtil.readFile(FileUtil.toUri(args[1]))._1());
                String result = res.entrySet()
                        .stream()
                        .map(entry -> "\n" + entry.getKey() + ":\n  " + entry.getValue() + "\n")
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