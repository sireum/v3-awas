// Generated from /workspace-v3/sireum-v3/awas/jvm/src/main/resources/org/sireum/awas/parser/Antlr4Awas.g4 by ANTLR 4.7
package org.sireum.awas.parser;

// @formatter:off

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link Antlr4AwasParser}.
 */
public interface Antlr4AwasListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link Antlr4AwasParser#modelFile}.
	 * @param ctx the parse tree
	 */
	void enterModelFile(Antlr4AwasParser.ModelFileContext ctx);
	/**
	 * Exit a parse tree produced by {@link Antlr4AwasParser#modelFile}.
	 * @param ctx the parse tree
	 */
	void exitModelFile(Antlr4AwasParser.ModelFileContext ctx);
	/**
	 * Enter a parse tree produced by {@link Antlr4AwasParser#model}.
	 * @param ctx the parse tree
	 */
	void enterModel(Antlr4AwasParser.ModelContext ctx);
	/**
	 * Exit a parse tree produced by {@link Antlr4AwasParser#model}.
	 * @param ctx the parse tree
	 */
	void exitModel(Antlr4AwasParser.ModelContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AliasTypeDecl}
	 * labeled alternative in {@link Antlr4AwasParser#typeDecl}.
	 * @param ctx the parse tree
	 */
	void enterAliasTypeDecl(Antlr4AwasParser.AliasTypeDeclContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AliasTypeDecl}
	 * labeled alternative in {@link Antlr4AwasParser#typeDecl}.
	 * @param ctx the parse tree
	 */
	void exitAliasTypeDecl(Antlr4AwasParser.AliasTypeDeclContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EnumTypeDecl}
	 * labeled alternative in {@link Antlr4AwasParser#typeDecl}.
	 * @param ctx the parse tree
	 */
	void enterEnumTypeDecl(Antlr4AwasParser.EnumTypeDeclContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EnumTypeDecl}
	 * labeled alternative in {@link Antlr4AwasParser#typeDecl}.
	 * @param ctx the parse tree
	 */
	void exitEnumTypeDecl(Antlr4AwasParser.EnumTypeDeclContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LatticeTypeDecl}
	 * labeled alternative in {@link Antlr4AwasParser#typeDecl}.
	 * @param ctx the parse tree
	 */
	void enterLatticeTypeDecl(Antlr4AwasParser.LatticeTypeDeclContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LatticeTypeDecl}
	 * labeled alternative in {@link Antlr4AwasParser#typeDecl}.
	 * @param ctx the parse tree
	 */
	void exitLatticeTypeDecl(Antlr4AwasParser.LatticeTypeDeclContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RecordTypeDecl}
	 * labeled alternative in {@link Antlr4AwasParser#typeDecl}.
	 * @param ctx the parse tree
	 */
	void enterRecordTypeDecl(Antlr4AwasParser.RecordTypeDeclContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RecordTypeDecl}
	 * labeled alternative in {@link Antlr4AwasParser#typeDecl}.
	 * @param ctx the parse tree
	 */
	void exitRecordTypeDecl(Antlr4AwasParser.RecordTypeDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link Antlr4AwasParser#behaviorDecl}.
	 * @param ctx the parse tree
	 */
	void enterBehaviorDecl(Antlr4AwasParser.BehaviorDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link Antlr4AwasParser#behaviorDecl}.
	 * @param ctx the parse tree
	 */
	void exitBehaviorDecl(Antlr4AwasParser.BehaviorDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link Antlr4AwasParser#componentDecl}.
	 * @param ctx the parse tree
	 */
	void enterComponentDecl(Antlr4AwasParser.ComponentDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link Antlr4AwasParser#componentDecl}.
	 * @param ctx the parse tree
	 */
	void exitComponentDecl(Antlr4AwasParser.ComponentDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link Antlr4AwasParser#connectionDecl}.
	 * @param ctx the parse tree
	 */
	void enterConnectionDecl(Antlr4AwasParser.ConnectionDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link Antlr4AwasParser#connectionDecl}.
	 * @param ctx the parse tree
	 */
	void exitConnectionDecl(Antlr4AwasParser.ConnectionDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link Antlr4AwasParser#typeAliasDecl}.
	 * @param ctx the parse tree
	 */
	void enterTypeAliasDecl(Antlr4AwasParser.TypeAliasDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link Antlr4AwasParser#typeAliasDecl}.
	 * @param ctx the parse tree
	 */
	void exitTypeAliasDecl(Antlr4AwasParser.TypeAliasDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link Antlr4AwasParser#enumDecl}.
	 * @param ctx the parse tree
	 */
	void enterEnumDecl(Antlr4AwasParser.EnumDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link Antlr4AwasParser#enumDecl}.
	 * @param ctx the parse tree
	 */
	void exitEnumDecl(Antlr4AwasParser.EnumDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link Antlr4AwasParser#latticeDecl}.
	 * @param ctx the parse tree
	 */
	void enterLatticeDecl(Antlr4AwasParser.LatticeDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link Antlr4AwasParser#latticeDecl}.
	 * @param ctx the parse tree
	 */
	void exitLatticeDecl(Antlr4AwasParser.LatticeDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link Antlr4AwasParser#recordDecl}.
	 * @param ctx the parse tree
	 */
	void enterRecordDecl(Antlr4AwasParser.RecordDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link Antlr4AwasParser#recordDecl}.
	 * @param ctx the parse tree
	 */
	void exitRecordDecl(Antlr4AwasParser.RecordDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link Antlr4AwasParser#field}.
	 * @param ctx the parse tree
	 */
	void enterField(Antlr4AwasParser.FieldContext ctx);
	/**
	 * Exit a parse tree produced by {@link Antlr4AwasParser#field}.
	 * @param ctx the parse tree
	 */
	void exitField(Antlr4AwasParser.FieldContext ctx);
	/**
	 * Enter a parse tree produced by {@link Antlr4AwasParser#port}.
	 * @param ctx the parse tree
	 */
	void enterPort(Antlr4AwasParser.PortContext ctx);
	/**
	 * Exit a parse tree produced by {@link Antlr4AwasParser#port}.
	 * @param ctx the parse tree
	 */
	void exitPort(Antlr4AwasParser.PortContext ctx);
	/**
	 * Enter a parse tree produced by {@link Antlr4AwasParser#propagation}.
	 * @param ctx the parse tree
	 */
	void enterPropagation(Antlr4AwasParser.PropagationContext ctx);
	/**
	 * Exit a parse tree produced by {@link Antlr4AwasParser#propagation}.
	 * @param ctx the parse tree
	 */
	void exitPropagation(Antlr4AwasParser.PropagationContext ctx);
	/**
	 * Enter a parse tree produced by {@link Antlr4AwasParser#flow}.
	 * @param ctx the parse tree
	 */
	void enterFlow(Antlr4AwasParser.FlowContext ctx);
	/**
	 * Exit a parse tree produced by {@link Antlr4AwasParser#flow}.
	 * @param ctx the parse tree
	 */
	void exitFlow(Antlr4AwasParser.FlowContext ctx);
	/**
	 * Enter a parse tree produced by {@link Antlr4AwasParser#property}.
	 * @param ctx the parse tree
	 */
	void enterProperty(Antlr4AwasParser.PropertyContext ctx);
	/**
	 * Exit a parse tree produced by {@link Antlr4AwasParser#property}.
	 * @param ctx the parse tree
	 */
	void exitProperty(Antlr4AwasParser.PropertyContext ctx);
	/**
	 * Enter a parse tree produced by {@link Antlr4AwasParser#constantDecl}.
	 * @param ctx the parse tree
	 */
	void enterConstantDecl(Antlr4AwasParser.ConstantDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link Antlr4AwasParser#constantDecl}.
	 * @param ctx the parse tree
	 */
	void exitConstantDecl(Antlr4AwasParser.ConstantDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link Antlr4AwasParser#transition}.
	 * @param ctx the parse tree
	 */
	void enterTransition(Antlr4AwasParser.TransitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Antlr4AwasParser#transition}.
	 * @param ctx the parse tree
	 */
	void exitTransition(Antlr4AwasParser.TransitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Antlr4AwasParser#transExpr}.
	 * @param ctx the parse tree
	 */
	void enterTransExpr(Antlr4AwasParser.TransExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link Antlr4AwasParser#transExpr}.
	 * @param ctx the parse tree
	 */
	void exitTransExpr(Antlr4AwasParser.TransExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link Antlr4AwasParser#behaviour}.
	 * @param ctx the parse tree
	 */
	void enterBehaviour(Antlr4AwasParser.BehaviourContext ctx);
	/**
	 * Exit a parse tree produced by {@link Antlr4AwasParser#behaviour}.
	 * @param ctx the parse tree
	 */
	void exitBehaviour(Antlr4AwasParser.BehaviourContext ctx);
	/**
	 * Enter a parse tree produced by {@link Antlr4AwasParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(Antlr4AwasParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Antlr4AwasParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(Antlr4AwasParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Antlr4AwasParser#idGroup}.
	 * @param ctx the parse tree
	 */
	void enterIdGroup(Antlr4AwasParser.IdGroupContext ctx);
	/**
	 * Exit a parse tree produced by {@link Antlr4AwasParser#idGroup}.
	 * @param ctx the parse tree
	 */
	void exitIdGroup(Antlr4AwasParser.IdGroupContext ctx);
	/**
	 * Enter a parse tree produced by {@link Antlr4AwasParser#tuple}.
	 * @param ctx the parse tree
	 */
	void enterTuple(Antlr4AwasParser.TupleContext ctx);
	/**
	 * Exit a parse tree produced by {@link Antlr4AwasParser#tuple}.
	 * @param ctx the parse tree
	 */
	void exitTuple(Antlr4AwasParser.TupleContext ctx);
	/**
	 * Enter a parse tree produced by {@link Antlr4AwasParser#faultPort}.
	 * @param ctx the parse tree
	 */
	void enterFaultPort(Antlr4AwasParser.FaultPortContext ctx);
	/**
	 * Exit a parse tree produced by {@link Antlr4AwasParser#faultPort}.
	 * @param ctx the parse tree
	 */
	void exitFaultPort(Antlr4AwasParser.FaultPortContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FaultRef}
	 * labeled alternative in {@link Antlr4AwasParser#one}.
	 * @param ctx the parse tree
	 */
	void enterFaultRef(Antlr4AwasParser.FaultRefContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FaultRef}
	 * labeled alternative in {@link Antlr4AwasParser#one}.
	 * @param ctx the parse tree
	 */
	void exitFaultRef(Antlr4AwasParser.FaultRefContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FaultSet}
	 * labeled alternative in {@link Antlr4AwasParser#one}.
	 * @param ctx the parse tree
	 */
	void enterFaultSet(Antlr4AwasParser.FaultSetContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FaultSet}
	 * labeled alternative in {@link Antlr4AwasParser#one}.
	 * @param ctx the parse tree
	 */
	void exitFaultSet(Antlr4AwasParser.FaultSetContext ctx);
	/**
	 * Enter a parse tree produced by {@link Antlr4AwasParser#fault}.
	 * @param ctx the parse tree
	 */
	void enterFault(Antlr4AwasParser.FaultContext ctx);
	/**
	 * Exit a parse tree produced by {@link Antlr4AwasParser#fault}.
	 * @param ctx the parse tree
	 */
	void exitFault(Antlr4AwasParser.FaultContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BaseType}
	 * labeled alternative in {@link Antlr4AwasParser#type}.
	 * @param ctx the parse tree
	 */
	void enterBaseType(Antlr4AwasParser.BaseTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BaseType}
	 * labeled alternative in {@link Antlr4AwasParser#type}.
	 * @param ctx the parse tree
	 */
	void exitBaseType(Antlr4AwasParser.BaseTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code OptionType}
	 * labeled alternative in {@link Antlr4AwasParser#type}.
	 * @param ctx the parse tree
	 */
	void enterOptionType(Antlr4AwasParser.OptionTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code OptionType}
	 * labeled alternative in {@link Antlr4AwasParser#type}.
	 * @param ctx the parse tree
	 */
	void exitOptionType(Antlr4AwasParser.OptionTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SetType}
	 * labeled alternative in {@link Antlr4AwasParser#type}.
	 * @param ctx the parse tree
	 */
	void enterSetType(Antlr4AwasParser.SetTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SetType}
	 * labeled alternative in {@link Antlr4AwasParser#type}.
	 * @param ctx the parse tree
	 */
	void exitSetType(Antlr4AwasParser.SetTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SeqType}
	 * labeled alternative in {@link Antlr4AwasParser#type}.
	 * @param ctx the parse tree
	 */
	void enterSeqType(Antlr4AwasParser.SeqTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SeqType}
	 * labeled alternative in {@link Antlr4AwasParser#type}.
	 * @param ctx the parse tree
	 */
	void exitSeqType(Antlr4AwasParser.SeqTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MapType}
	 * labeled alternative in {@link Antlr4AwasParser#type}.
	 * @param ctx the parse tree
	 */
	void enterMapType(Antlr4AwasParser.MapTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MapType}
	 * labeled alternative in {@link Antlr4AwasParser#type}.
	 * @param ctx the parse tree
	 */
	void exitMapType(Antlr4AwasParser.MapTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BooleanType}
	 * labeled alternative in {@link Antlr4AwasParser#basicType}.
	 * @param ctx the parse tree
	 */
	void enterBooleanType(Antlr4AwasParser.BooleanTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BooleanType}
	 * labeled alternative in {@link Antlr4AwasParser#basicType}.
	 * @param ctx the parse tree
	 */
	void exitBooleanType(Antlr4AwasParser.BooleanTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IntegerType}
	 * labeled alternative in {@link Antlr4AwasParser#basicType}.
	 * @param ctx the parse tree
	 */
	void enterIntegerType(Antlr4AwasParser.IntegerTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IntegerType}
	 * labeled alternative in {@link Antlr4AwasParser#basicType}.
	 * @param ctx the parse tree
	 */
	void exitIntegerType(Antlr4AwasParser.IntegerTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RealType}
	 * labeled alternative in {@link Antlr4AwasParser#basicType}.
	 * @param ctx the parse tree
	 */
	void enterRealType(Antlr4AwasParser.RealTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RealType}
	 * labeled alternative in {@link Antlr4AwasParser#basicType}.
	 * @param ctx the parse tree
	 */
	void exitRealType(Antlr4AwasParser.RealTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StringType}
	 * labeled alternative in {@link Antlr4AwasParser#basicType}.
	 * @param ctx the parse tree
	 */
	void enterStringType(Antlr4AwasParser.StringTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StringType}
	 * labeled alternative in {@link Antlr4AwasParser#basicType}.
	 * @param ctx the parse tree
	 */
	void exitStringType(Antlr4AwasParser.StringTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ComponentType}
	 * labeled alternative in {@link Antlr4AwasParser#basicType}.
	 * @param ctx the parse tree
	 */
	void enterComponentType(Antlr4AwasParser.ComponentTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ComponentType}
	 * labeled alternative in {@link Antlr4AwasParser#basicType}.
	 * @param ctx the parse tree
	 */
	void exitComponentType(Antlr4AwasParser.ComponentTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PortType}
	 * labeled alternative in {@link Antlr4AwasParser#basicType}.
	 * @param ctx the parse tree
	 */
	void enterPortType(Antlr4AwasParser.PortTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PortType}
	 * labeled alternative in {@link Antlr4AwasParser#basicType}.
	 * @param ctx the parse tree
	 */
	void exitPortType(Antlr4AwasParser.PortTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NamedType}
	 * labeled alternative in {@link Antlr4AwasParser#basicType}.
	 * @param ctx the parse tree
	 */
	void enterNamedType(Antlr4AwasParser.NamedTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NamedType}
	 * labeled alternative in {@link Antlr4AwasParser#basicType}.
	 * @param ctx the parse tree
	 */
	void exitNamedType(Antlr4AwasParser.NamedTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LitIntConstant}
	 * labeled alternative in {@link Antlr4AwasParser#intConstant}.
	 * @param ctx the parse tree
	 */
	void enterLitIntConstant(Antlr4AwasParser.LitIntConstantContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LitIntConstant}
	 * labeled alternative in {@link Antlr4AwasParser#intConstant}.
	 * @param ctx the parse tree
	 */
	void exitLitIntConstant(Antlr4AwasParser.LitIntConstantContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NamedIntConstant}
	 * labeled alternative in {@link Antlr4AwasParser#intConstant}.
	 * @param ctx the parse tree
	 */
	void enterNamedIntConstant(Antlr4AwasParser.NamedIntConstantContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NamedIntConstant}
	 * labeled alternative in {@link Antlr4AwasParser#intConstant}.
	 * @param ctx the parse tree
	 */
	void exitNamedIntConstant(Antlr4AwasParser.NamedIntConstantContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ArbitraryIntConstant}
	 * labeled alternative in {@link Antlr4AwasParser#intConstant}.
	 * @param ctx the parse tree
	 */
	void enterArbitraryIntConstant(Antlr4AwasParser.ArbitraryIntConstantContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ArbitraryIntConstant}
	 * labeled alternative in {@link Antlr4AwasParser#intConstant}.
	 * @param ctx the parse tree
	 */
	void exitArbitraryIntConstant(Antlr4AwasParser.ArbitraryIntConstantContext ctx);
	/**
	 * Enter a parse tree produced by the {@code True}
	 * labeled alternative in {@link Antlr4AwasParser#init}.
	 * @param ctx the parse tree
	 */
	void enterTrue(Antlr4AwasParser.TrueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code True}
	 * labeled alternative in {@link Antlr4AwasParser#init}.
	 * @param ctx the parse tree
	 */
	void exitTrue(Antlr4AwasParser.TrueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code False}
	 * labeled alternative in {@link Antlr4AwasParser#init}.
	 * @param ctx the parse tree
	 */
	void enterFalse(Antlr4AwasParser.FalseContext ctx);
	/**
	 * Exit a parse tree produced by the {@code False}
	 * labeled alternative in {@link Antlr4AwasParser#init}.
	 * @param ctx the parse tree
	 */
	void exitFalse(Antlr4AwasParser.FalseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Integer}
	 * labeled alternative in {@link Antlr4AwasParser#init}.
	 * @param ctx the parse tree
	 */
	void enterInteger(Antlr4AwasParser.IntegerContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Integer}
	 * labeled alternative in {@link Antlr4AwasParser#init}.
	 * @param ctx the parse tree
	 */
	void exitInteger(Antlr4AwasParser.IntegerContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Real}
	 * labeled alternative in {@link Antlr4AwasParser#init}.
	 * @param ctx the parse tree
	 */
	void enterReal(Antlr4AwasParser.RealContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Real}
	 * labeled alternative in {@link Antlr4AwasParser#init}.
	 * @param ctx the parse tree
	 */
	void exitReal(Antlr4AwasParser.RealContext ctx);
	/**
	 * Enter a parse tree produced by the {@code String}
	 * labeled alternative in {@link Antlr4AwasParser#init}.
	 * @param ctx the parse tree
	 */
	void enterString(Antlr4AwasParser.StringContext ctx);
	/**
	 * Exit a parse tree produced by the {@code String}
	 * labeled alternative in {@link Antlr4AwasParser#init}.
	 * @param ctx the parse tree
	 */
	void exitString(Antlr4AwasParser.StringContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Record}
	 * labeled alternative in {@link Antlr4AwasParser#init}.
	 * @param ctx the parse tree
	 */
	void enterRecord(Antlr4AwasParser.RecordContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Record}
	 * labeled alternative in {@link Antlr4AwasParser#init}.
	 * @param ctx the parse tree
	 */
	void exitRecord(Antlr4AwasParser.RecordContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NameRef}
	 * labeled alternative in {@link Antlr4AwasParser#init}.
	 * @param ctx the parse tree
	 */
	void enterNameRef(Antlr4AwasParser.NameRefContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NameRef}
	 * labeled alternative in {@link Antlr4AwasParser#init}.
	 * @param ctx the parse tree
	 */
	void exitNameRef(Antlr4AwasParser.NameRefContext ctx);
	/**
	 * Enter a parse tree produced by the {@code None}
	 * labeled alternative in {@link Antlr4AwasParser#init}.
	 * @param ctx the parse tree
	 */
	void enterNone(Antlr4AwasParser.NoneContext ctx);
	/**
	 * Exit a parse tree produced by the {@code None}
	 * labeled alternative in {@link Antlr4AwasParser#init}.
	 * @param ctx the parse tree
	 */
	void exitNone(Antlr4AwasParser.NoneContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Some}
	 * labeled alternative in {@link Antlr4AwasParser#init}.
	 * @param ctx the parse tree
	 */
	void enterSome(Antlr4AwasParser.SomeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Some}
	 * labeled alternative in {@link Antlr4AwasParser#init}.
	 * @param ctx the parse tree
	 */
	void exitSome(Antlr4AwasParser.SomeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Set}
	 * labeled alternative in {@link Antlr4AwasParser#init}.
	 * @param ctx the parse tree
	 */
	void enterSet(Antlr4AwasParser.SetContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Set}
	 * labeled alternative in {@link Antlr4AwasParser#init}.
	 * @param ctx the parse tree
	 */
	void exitSet(Antlr4AwasParser.SetContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Seq}
	 * labeled alternative in {@link Antlr4AwasParser#init}.
	 * @param ctx the parse tree
	 */
	void enterSeq(Antlr4AwasParser.SeqContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Seq}
	 * labeled alternative in {@link Antlr4AwasParser#init}.
	 * @param ctx the parse tree
	 */
	void exitSeq(Antlr4AwasParser.SeqContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Map}
	 * labeled alternative in {@link Antlr4AwasParser#init}.
	 * @param ctx the parse tree
	 */
	void enterMap(Antlr4AwasParser.MapContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Map}
	 * labeled alternative in {@link Antlr4AwasParser#init}.
	 * @param ctx the parse tree
	 */
	void exitMap(Antlr4AwasParser.MapContext ctx);
	/**
	 * Enter a parse tree produced by {@link Antlr4AwasParser#mapEntry}.
	 * @param ctx the parse tree
	 */
	void enterMapEntry(Antlr4AwasParser.MapEntryContext ctx);
	/**
	 * Exit a parse tree produced by {@link Antlr4AwasParser#mapEntry}.
	 * @param ctx the parse tree
	 */
	void exitMapEntry(Antlr4AwasParser.MapEntryContext ctx);
	/**
	 * Enter a parse tree produced by {@link Antlr4AwasParser#name}.
	 * @param ctx the parse tree
	 */
	void enterName(Antlr4AwasParser.NameContext ctx);
	/**
	 * Exit a parse tree produced by {@link Antlr4AwasParser#name}.
	 * @param ctx the parse tree
	 */
	void exitName(Antlr4AwasParser.NameContext ctx);
	/**
	 * Enter a parse tree produced by {@link Antlr4AwasParser#states}.
	 * @param ctx the parse tree
	 */
	void enterStates(Antlr4AwasParser.StatesContext ctx);
	/**
	 * Exit a parse tree produced by {@link Antlr4AwasParser#states}.
	 * @param ctx the parse tree
	 */
	void exitStates(Antlr4AwasParser.StatesContext ctx);
	/**
	 * Enter a parse tree produced by {@link Antlr4AwasParser#events}.
	 * @param ctx the parse tree
	 */
	void enterEvents(Antlr4AwasParser.EventsContext ctx);
	/**
	 * Exit a parse tree produced by {@link Antlr4AwasParser#events}.
	 * @param ctx the parse tree
	 */
	void exitEvents(Antlr4AwasParser.EventsContext ctx);
}