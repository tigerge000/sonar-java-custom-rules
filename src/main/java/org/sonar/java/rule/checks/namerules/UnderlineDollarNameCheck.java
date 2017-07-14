package org.sonar.java.rule.checks.namerules;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.*;

/**
 * 命名规约
 * 代码中的命名均不能以下划线或美元符号开始，也不能以下划线或美元符号结束
 * Created by huqingen on 2017/3/16.
 */
//@Rule(
//        key = "UnderlineDollarNameCheck",
//        name = "Can't use underline or Dollar first or end As Name",
//        description = "Can't use underline or Dollar first or end As Name",
//        priority = Priority.CRITICAL,
//        tags = {"bug"})
@Rule(key = "UnderlineDollarNameCheck")
public class UnderlineDollarNameCheck extends BaseTreeVisitor implements JavaFileScanner {

    private static final Logger LOGGER = LoggerFactory.getLogger(UnderlineDollarNameCheck.class);


    private JavaFileScannerContext context;

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        scan(context.getTree());
    }

    /**
     * 判断类名是否以_或$开始或结尾
     * @param tree
     */
    @Override
    public void visitClass(ClassTree tree) {
        LOGGER.info("ClassName:" + tree.simpleName().name());
        String className = tree.simpleName().name();
        if("_".equals(className.substring(0,1))){
            context.reportIssue(this,tree,"Can't Name this");
        }
        super.visitClass(tree);
    }

    /**
     * 判断方法名称是否以_或$开始或结尾
     * 判断方法的参数是否以_或$开始或结尾
     */
    @Override
    public void visitMethod(MethodTree tree){

        String methodName = tree.simpleName().name();
        if("CLASS".equals(tree.parent().kind().name())) {
            //判断方法局部变量名称是否以_或$开始或结尾
            for (StatementTree statementTree : tree.block().body()) {
                if(statementTree instanceof VariableTree) {
                    VariableTree sTree = (VariableTree) statementTree;
                    String sName = sTree.simpleName().name();
                    if ("_".equals(sName.substring(0, 1)) || "_".equals(sName.substring(sName.length() - 1))
                            || "$".equals(sName.substring(0, 1)) || "$".equals(sName.substring(sName.length() - 1))) {
                        context.reportIssue(this, tree, "Can't Name As This");
                    }
                }
            }
        }

        //判断方法名称是否以_或$开始或结尾
        if("_".equals(methodName.substring(0,1)) || "_".equals(methodName.substring(methodName.length()-1))
    || "$".equals(methodName.substring(0,1)) || "$".equals(methodName.substring(methodName.length()-1))){
            context.reportIssue(this,tree,"Can't Name As This");
        }

        super.visitMethod(tree);
    }

    /**
     * 判断成员变量命名是否符合规范
     * @param tree
     */
    @Override
    public void visitVariable(VariableTree tree){
        String variableName = tree.simpleName().name();
        if("_".equals(variableName.substring(0,1)) || "_".equals(variableName.substring(variableName.length()-1))
                || "$".equals(variableName.substring(0,1)) || "$".equals(variableName.substring(variableName.length()-1))){
            context.reportIssue(this,tree,"Can't Name As This");
        }
        super.visitVariable(tree);
    }
}
