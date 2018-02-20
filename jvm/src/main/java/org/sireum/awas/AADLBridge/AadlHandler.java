package org.sireum.awas.AADLBridge;

import org.sireum.aadl.skema.ast.Aadl;
import org.sireum.awas.ast.Model;
import org.sireum.awas.slang.Aadl2Awas;

public class AadlHandler {

    public static Model buildAwasModel(Aadl aadlModel) {
        return Aadl2Awas.apply(aadlModel);
    }

    public static void generateWitness(String awasFile,
                                       String queryFile,
                                       String output) {
        Aadl2Awas.generateVisualizer(awasFile, queryFile, output);
    }

    public static void generateWitness(String awasFile,
                                       String queryFile,
                                       String output,
                                       String sireumJarLoc) {
        Aadl2Awas.generateVisualizer(awasFile, queryFile, output, sireumJarLoc);
    }
}
