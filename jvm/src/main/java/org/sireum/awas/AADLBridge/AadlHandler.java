package org.sireum.awas.AADLBridge;

import org.sireum.aadl.skema.ast.Aadl;
import org.sireum.awas.ast.Model;
import org.sireum.awas.ast.PrettyPrinter;
import org.sireum.awas.slang.Aadl2Awas;

public class AadlHandler {

    public static Model buildAwasModel(Aadl aadlModel) {
        return Aadl2Awas.apply(aadlModel);
    }

    public static String buildAwasString(Aadl aadlModel) {
        return PrettyPrinter.apply(Aadl2Awas.apply(aadlModel));
    }

    public static void generateWitness(String awasFile,
                                       String queryFile,
                                       String output) {
        Aadl2Awas.generateVisualizer(awasFile, queryFile, output);
    }
}
