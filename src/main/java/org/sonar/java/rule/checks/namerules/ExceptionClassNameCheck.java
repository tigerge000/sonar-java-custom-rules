package org.sonar.java.rule.checks.namerules;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.ClassTree;

/**
 * 异常类命名检查
 * 异常类命名使用 Exception 结尾
 * Created by huqingen on 2017/3/17.
 */
@Rule(key = "ExceptionClassNameCheck")
public class ExceptionClassNameCheck extends BaseTreeVisitor implements JavaFileScanner{
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionClassNameCheck.class);

    private JavaFileScannerContext context;

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        scan(context.getTree());
    }

    @Override
    public void visitClass(ClassTree tree) {
        String className = tree.simpleName().name();
        String checkName = "Exception";
        if(tree.superClass() != null) {
            String superClassName = tree.superClass().symbolType().name();

            if(checkName.equals(superClassName)){
                if(checkName.length() > className.length()){
                    context.reportIssue(this,tree,"Class name must end with Exception");
                }else {
                    if(!className.contains(checkName)){
                        context.reportIssue(this,tree,"Class name must end with Exception");
                    }else {
                        if(!checkName.equals(className.substring(className.length()-checkName.length()))){
                            context.reportIssue(this,tree,"Class name must end with Exception");
                        }
                    }
                }
            }
        }
        super.visitClass(tree);
    }
}
