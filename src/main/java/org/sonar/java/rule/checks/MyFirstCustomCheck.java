//package org.finger.java.rule.checks;
//
//import com.google.common.collect.ImmutableList;
//import org.sonar.check.Priority;
//import org.sonar.check.Rule;
//import org.sonar.java.ast.visitors.SubscriptionVisitor;
//import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
//import org.sonar.plugins.java.api.semantic.Symbol;
//import org.sonar.plugins.java.api.semantic.Type;
//import org.sonar.plugins.java.api.tree.MethodTree;
//import org.sonar.plugins.java.api.tree.Tree;
//import org.sonar.plugins.java.api.tree.Tree.Kind;
//
//import java.util.List;
//
///**
// * Created by huqingen on 2017/3/16.
// */
//@Rule(
//        key = "MyFirstCustomCheck",
//        name = "Return type and parameter of a method should not be the same",
//        description = "For a method having a single parameter, the types of its return value and its parameter should never be the same.",
//        priority = Priority.CRITICAL,
//        tags = {"bug"})
//public class MyFirstCustomCheck extends IssuableSubscriptionVisitor{
//    @Override
//    public List<Kind> nodesToVisit() {
//        return ImmutableList.of(Kind.METHOD);
//    }
//
//
//
//    @Override
//    public void visitNode(Tree tree){
//        MethodTree method = (MethodTree) tree;
//        if(method.parameters().size() == 1){
//            Symbol.MethodSymbol symbol = method.symbol();
//            Type firstParameterType = symbol.parameterTypes().get(0);
//            Type returnType = symbol.returnType().type();
//
//            System.out.println(">>>" + firstParameterType + "<<<");
//            System.out.println(">>>" + returnType + "<<<");
//
//            if(returnType.equals(firstParameterType.fullyQualifiedName())){
//                reportIssue(method.simpleName(),"For a method having a single parameter, the types of its return value and its parameter should never be the same");
//            }
//
//        }
//    }
//}
