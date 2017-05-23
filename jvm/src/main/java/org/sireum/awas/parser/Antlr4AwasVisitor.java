// Generated from /Users/Hariharan/Documents/workspace-slang/sireum-v3/awas/jvm/src/main/resources/org/sireum/awas/parser/Antlr4Awas.g4 by ANTLR 4.7
package org.sireum.awas.parser;

// @formatter:off

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link Antlr4AwasParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface Antlr4AwasVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link Antlr4AwasParser#modelFile}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModelFile(Antlr4AwasParser.ModelFileContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4AwasParser#model}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModel(Antlr4AwasParser.ModelContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AliasTypeDecl}
	 * labeled alternative in {@link Antlr4AwasParser#typeDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAliasTypeDecl(Antlr4AwasParser.AliasTypeDeclContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EnumTypeDecl}
	 * labeled alternative in {@link Antlr4AwasParser#typeDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumTypeDecl(Antlr4AwasParser.EnumTypeDeclContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LatticeTypeDecl}
	 * labeled alternative in {@link Antlr4AwasParser#typeDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLatticeTypeDecl(Antlr4AwasParser.LatticeTypeDeclContext ctx);
	/**
	 * Visit a parse tree produced by the {@code RecordTypeDecl}
	 * labeled alternative in {@link Antlr4AwasParser#typeDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecordTypeDecl(Antlr4AwasParser.RecordTypeDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4AwasParser#behaviorDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBehaviorDecl(Antlr4AwasParser.BehaviorDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4AwasParser#componentDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComponentDecl(Antlr4AwasParser.ComponentDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4AwasParser#connectionDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConnectionDecl(Antlr4AwasParser.ConnectionDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4AwasParser#typeAliasDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeAliasDecl(Antlr4AwasParser.TypeAliasDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4AwasParser#enumDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumDecl(Antlr4AwasParser.EnumDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4AwasParser#latticeDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLatticeDecl(Antlr4AwasParser.LatticeDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4AwasParser#recordDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecordDecl(Antlr4AwasParser.RecordDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4AwasParser#field}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitField(Antlr4AwasParser.FieldContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4AwasParser#port}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPort(Antlr4AwasParser.PortContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4AwasParser#propagation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPropagation(Antlr4AwasParser.PropagationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4AwasParser#flow}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFlow(Antlr4AwasParser.FlowContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4AwasParser#property}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProperty(Antlr4AwasParser.PropertyContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4AwasParser#constantDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstantDecl(Antlr4AwasParser.ConstantDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4AwasParser#transition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransition(Antlr4AwasParser.TransitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4AwasParser#transExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransExpr(Antlr4AwasParser.TransExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4AwasParser#behaviour}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBehaviour(Antlr4AwasParser.BehaviourContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4AwasParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(Antlr4AwasParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4AwasParser#idGroup}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdGroup(Antlr4AwasParser.IdGroupContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4AwasParser#tuple}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTuple(Antlr4AwasParser.TupleContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4AwasParser#faultPort}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFaultPort(Antlr4AwasParser.FaultPortContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FaultRef}
	 * labeled alternative in {@link Antlr4AwasParser#one}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFaultRef(Antlr4AwasParser.FaultRefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FaultSet}
	 * labeled alternative in {@link Antlr4AwasParser#one}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFaultSet(Antlr4AwasParser.FaultSetContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4AwasParser#fault}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFault(Antlr4AwasParser.FaultContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BaseType}
	 * labeled alternative in {@link Antlr4AwasParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBaseType(Antlr4AwasParser.BaseTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OptionType}
	 * labeled alternative in {@link Antlr4AwasParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOptionType(Antlr4AwasParser.OptionTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SetType}
	 * labeled alternative in {@link Antlr4AwasParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetType(Antlr4AwasParser.SetTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SeqType}
	 * labeled alternative in {@link Antlr4AwasParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSeqType(Antlr4AwasParser.SeqTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MapType}
	 * labeled alternative in {@link Antlr4AwasParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMapType(Antlr4AwasParser.MapTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BooleanType}
	 * labeled alternative in {@link Antlr4AwasParser#basicType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanType(Antlr4AwasParser.BooleanTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IntegerType}
	 * labeled alternative in {@link Antlr4AwasParser#basicType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntegerType(Antlr4AwasParser.IntegerTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code RealType}
	 * labeled alternative in {@link Antlr4AwasParser#basicType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRealType(Antlr4AwasParser.RealTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StringType}
	 * labeled alternative in {@link Antlr4AwasParser#basicType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringType(Antlr4AwasParser.StringTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ComponentType}
	 * labeled alternative in {@link Antlr4AwasParser#basicType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComponentType(Antlr4AwasParser.ComponentTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PortType}
	 * labeled alternative in {@link Antlr4AwasParser#basicType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPortType(Antlr4AwasParser.PortTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NamedType}
	 * labeled alternative in {@link Antlr4AwasParser#basicType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNamedType(Antlr4AwasParser.NamedTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LitIntConstant}
	 * labeled alternative in {@link Antlr4AwasParser#intConstant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLitIntConstant(Antlr4AwasParser.LitIntConstantContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NamedIntConstant}
	 * labeled alternative in {@link Antlr4AwasParser#intConstant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNamedIntConstant(Antlr4AwasParser.NamedIntConstantContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArbitraryIntConstant}
	 * labeled alternative in {@link Antlr4AwasParser#intConstant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArbitraryIntConstant(Antlr4AwasParser.ArbitraryIntConstantContext ctx);
	/**
	 * Visit a parse tree produced by the {@code True}
	 * labeled alternative in {@link Antlr4AwasParser#init}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrue(Antlr4AwasParser.TrueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code False}
	 * labeled alternative in {@link Antlr4AwasParser#init}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFalse(Antlr4AwasParser.FalseContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Integer}
	 * labeled alternative in {@link Antlr4AwasParser#init}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInteger(Antlr4AwasParser.IntegerContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Real}
	 * labeled alternative in {@link Antlr4AwasParser#init}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReal(Antlr4AwasParser.RealContext ctx);
	/**
	 * Visit a parse tree produced by the {@code String}
	 * labeled alternative in {@link Antlr4AwasParser#init}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString(Antlr4AwasParser.StringContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Record}
	 * labeled alternative in {@link Antlr4AwasParser#init}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecord(Antlr4AwasParser.RecordContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NameRef}
	 * labeled alternative in {@link Antlr4AwasParser#init}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNameRef(Antlr4AwasParser.NameRefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code None}
	 * labeled alternative in {@link Antlr4AwasParser#init}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNone(Antlr4AwasParser.NoneContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Some}
	 * labeled alternative in {@link Antlr4AwasParser#init}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSome(Antlr4AwasParser.SomeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Set}
	 * labeled alternative in {@link Antlr4AwasParser#init}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSet(Antlr4AwasParser.SetContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Seq}
	 * labeled alternative in {@link Antlr4AwasParser#init}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSeq(Antlr4AwasParser.SeqContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Map}
	 * labeled alternative in {@link Antlr4AwasParser#init}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMap(Antlr4AwasParser.MapContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4AwasParser#mapEntry}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMapEntry(Antlr4AwasParser.MapEntryContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4AwasParser#name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitName(Antlr4AwasParser.NameContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4AwasParser#states}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStates(Antlr4AwasParser.StatesContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4AwasParser#events}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEvents(Antlr4AwasParser.EventsContext ctx);
}