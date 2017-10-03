package org.sireum.awas.example;

import org.sireum.awas.witness.Visualizer;

public class AwasWitnessVisualizer {
    public static void main(String[] args) throws Exception {
        //sample arguments
        //"/workspace-v3/sireum-v3/awas/jvm/src/test/resources/org/sireum/awas/test/example/bindings/spiral4.awas"
        //"awas/jvm/src/test/resources/org/sireum/awas/test/example/bindings/spiral4.awas"
        //"awas/jvm/src/test/resources/org/sireum/awas/test/example/bindings/spiral4.aq"
        //"~/output/" --end with slash
        if (args.length == 3) {
            Visualizer.main(args);
        } else {
            System.out.println("AwasWitnessVisualizer Arg1 Arg2 Arg3");
            System.out.println("Arg1: file absolute path of awas model");
            System.out.println("Arg2: file absolute path of awas query");
            System.out.println("Arg3: absolute path of output folder");
        }
    }
}
