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

import org.sireum.awas.fptc.FptcGraph;
import org.sireum.awas.fptc.FptcNode;
import org.sireum.awas.reachability.PortReachability;
import org.sireum.awas.reachability.PortReachability$;
import org.sireum.awas.symbol.SymbolTable;
import org.sireum.awas.symbol.SymbolTableHelper;

import java.util.Set;

import static org.sireum.awas.util.JavaConverters.toJavaOptional;
import static org.sireum.awas.util.JavaConverters.toJavaSet;


public class AwasGraphImpl implements AwasGraph {
    private FptcGraph<FptcNode> graph;

    private SymbolTable st;

    private PortReachability<FptcNode> pr;

    public AwasGraphImpl(FptcGraph<FptcNode> graph, SymbolTable st) {
        this.graph = graph;
        this.st = st;
        this.pr = PortReachability$.MODULE$.apply(graph);
    }

    /**
     * Returns the forward reachability/slice of the criterion
     *
     * @param criterion string representation of AWAS node URI
     * @return the URIs that are reachable
     */
    @Override
    public Set<String> forwardReach(String criterion) {
        return toJavaSet((pr.forwardReach(criterion)));
    }

    /**
     * Returns the backward reachability/slice of the criterion
     *
     * @param criterion string representation of AWAS node URI
     * @return the Uris that are reachable
     */
    @Override
    public Set<String> backwardReach(String criterion) {
        return toJavaSet((pr.backwardReach(criterion)));
    }

    @Override
    public Set<String> forwardPortReach(String criterion) {
        return toJavaSet((pr.forwardPortReach(criterion)));
    }

    @Override
    public Set<String> backwardPortReach(String criterion) {
        return toJavaSet((pr.backwardPortReach(criterion)));
    }

    /**
     * Returns the forward reachability/slice of the criterion
     *
     * @param criterion dot separated component name or port name
     *                  eg: Foo - a component named 'Foo'
     *                  Foo.bar - a port 'bar' belonging to component 'Foo'
     * @return reachable URIs
     */
    @Override
    public Set<String> forwardReachUsingNames(String criterion) {
        return toJavaSet(pr.forwardReach(
                toJavaOptional(SymbolTableHelper
                        .getUriFromString(st, criterion)).orElse(""))
        );
    }

    /**
     * Returns the backward reachability/slice of the criterion
     *
     * @param criterion dot separated component name or port name
     *                  eg: Foo - a component named 'Foo'
     *                  Foo.bar - a port 'bar' belonging to component 'Foo'
     * @return reachable URIs
     */
    @Override
    public Set<String> backwardReachUsingNames(String criterion) {
        return toJavaSet(pr.backwardReach(
                toJavaOptional(SymbolTableHelper
                        .getUriFromString(st, criterion)).orElse(""))
        );
    }

    @Override
    public FptcGraph<FptcNode> getGraph() {
        return graph;
    }

    @Override
    public String getDotGraph() {
        return graph.toDot();
    }
}
