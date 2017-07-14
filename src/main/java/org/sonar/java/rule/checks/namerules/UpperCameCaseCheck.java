package org.sonar.java.rule.checks.namerules;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.ClassTree;

import java.util.ArrayList;
import java.util.List;

/**
 * 检查: 类名是否以驼峰形式命名
 * Created by huqingen on 2017/3/17.
 */
@Rule(key="UpperCameCaseCheck")
public class UpperCameCaseCheck extends BaseTreeVisitor implements JavaFileScanner{
    private static final Logger LOGGER = LoggerFactory.getLogger(UpperCameCaseCheck.class);


    private JavaFileScannerContext context;

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        scan(context.getTree());
    }


    /**
     * 判断类名是否以驼峰形式命名
     * @param tree
     */
    @Override
    public void visitClass(ClassTree tree) {

        String className = tree.simpleName().name();

        //例外情况(领域模型 的相关命名)DO / BO / DTO / VO

        List<String> otherList = new ArrayList<>();
        otherList.add("DO");
        otherList.add("BO");
        otherList.add("DTO");
        otherList.add("VO");

        for(String otherName: otherList){
            if(otherName.equalsIgnoreCase(className.substring(className.length()-otherName.length()))){
                if(!otherName.equals(className.substring(className.length()-otherName.length()))){
                    context.reportIssue(this,tree,"Can't Name this");
                }
            }
        }

        //判断第一个字符是否为大写
        char name = className.charAt(0);
        if(Character.isLowerCase(name)){
            context.reportIssue(this,tree,"Can't Name this");
        }
        super.visitClass(tree);
    }

}
