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

package org.sireum.awas.AADLBridge;

import org.sireum.None;
import org.sireum.hamr.ir.Aadl;
import org.sireum.awas.analysis.FaultImpactAnalysis;
import org.sireum.awas.ast.AwasSerializer$;
import org.sireum.awas.ast.Builder$;
import org.sireum.awas.ast.Model;
import org.sireum.awas.ast.PrettyPrinter;
import org.sireum.awas.awasfacade.AwasSerializer;
import org.sireum.awas.slang.Aadl2Awas;
import org.sireum.awas.util.JavaConverters;
import org.sireum.awas.witness.Visualizer;
import org.sireum.awas.witness.Visualizer$;
import scala.Array;
import scala.Array$;
import scala.Option;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

public class AadlHandler {

    public static Model buildAwasModel(Aadl aadlModel) {
        return Aadl2Awas.apply(aadlModel);
    }

    public static String buildAwasText(Aadl aadlModel) {
        return PrettyPrinter.apply(Aadl2Awas.apply(aadlModel));
    }

    public static void generateWitness(File awasFile,
                                       String output) throws Exception {
        generateWitness(awasFile, output, null);
    }

    public static void generateWitness(File awasFile,
                                       String output,
                                       File queryFile) throws Exception {
        Optional<Model> modelOpt = JavaConverters.toJavaOptional(Builder$.MODULE$.apply(Option.empty(),
                new String(Files.readAllBytes(awasFile.toPath())),
                Builder$.MODULE$.apply$default$3(),
                Builder$.MODULE$.apply$default$4()));
        if (modelOpt.isPresent()) {
            generateWitness(modelOpt.get(), output, queryFile);
        }
    }

    public static void generateWitness(Model awasModel,
                                       String output,
                                       File queryFile) throws IOException {
        String queries = (queryFile != null) ? new String(Files.readAllBytes(queryFile.toPath())) : "";
        generateWitness(AwasSerializer.serialize(awasModel),
                output, queries);
    }

    public static void generateWitness(Model awasModel,
                                       String output) throws IOException {
        generateWitness(awasModel, output, null);
    }


    public static void generateWitness(String awasJson,
                                       String outputPath,
                                       String queryFile) throws IOException {
        Visualizer$.MODULE$.apply(awasJson,
                outputPath,
                queryFile);
    }


    public static void generateWitness(String awasJson,
                                       String outputPath) throws IOException {
        generateWitness(awasJson, outputPath, "");
    }

    public static String generateFIA(Model awasModel) {
        return new FaultImpactAnalysis().computeFIA(awasModel);
    }
}
