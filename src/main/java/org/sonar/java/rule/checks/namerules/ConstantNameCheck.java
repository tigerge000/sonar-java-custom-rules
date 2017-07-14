package org.sonar.java.rule.checks.namerules;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.*;

/**
 * 常量名称检查
 * 常量命名全部大写，单词间用下划线隔开，力求语义表达完整清楚
 * Created by huqingen on 2017/3/17.
 */
@Rule(key = "ConstantNameCheck")
public class ConstantNameCheck extends BaseTreeVisitor implements JavaFileScanner{

    private static final Logger LOGGER = LoggerFactory.getLogger(ConstantNameCheck.class);


    private JavaFileScannerContext context;

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        scan(context.getTree());
    }

    /**
     * 1.检查是否有常量
     * 2.检查常量的命名是否符合规范(变量名称和是否有static final)
     *
     * @param tree
     */
    @Override
    public void visitVariable(VariableTree tree){

        String varibaleName = tree.simpleName().name();//变量名称


        if(tree.symbol().isFinal()){
            //如果字符串大于10时，且没有按照_进行分割，报错
            if(varibaleName.length() > 10 && !varibaleName.contains("_")){
                LOGGER.info("长度过长BUG:" + varibaleName);
                context.reportIssue(this,tree,"different name should spilt as _");
            }

            //变量字符串中有数字，报错
            int count = 0;
            for(char s : varibaleName.toCharArray()){
                if(Character.isDigit(s)){
                    count ++;
                }
            }
            if(count > 0){
                LOGGER.info("变量字符串中有数字:" + varibaleName);
                context.reportIssue(this,tree,"The Name should not contain digit");
            }

            //把变量的所有英文字符串提取出来
            String sVaribaleName = varibaleName.replaceAll("[^a-z^A-Z]","");
            int i = 0;//计数器
            for(char s : sVaribaleName.toCharArray()){
                if(Character.isLowerCase(s)){
                    i++;
                }
            }
            if(i > 0){
                LOGGER.info("没有大写的BUG" + i + ":" + varibaleName);
                context.reportIssue(this,tree,"The Name should be As UpperCase");
            }
        }
        super.visitVariable(tree);
    }

}
