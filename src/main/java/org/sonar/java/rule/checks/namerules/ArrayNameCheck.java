package org.sonar.java.rule.checks.namerules;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.check.Rule;
import org.sonar.java.model.JavaTree;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.*;

/**
 * 数组名称检查
 * 中括号是数组类型的一部分，数组定义如下:String[] args;
 * Created by huqingen on 2017/3/18.
 */
@Rule(key = "ArrayNameCheck")
public class ArrayNameCheck extends BaseTreeVisitor implements JavaFileScanner{

    private static final Logger LOGGER = LoggerFactory.getLogger(ArrayNameCheck.class);

    private JavaFileScannerContext context;

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        scan(context.getTree());
    }

    /**
     * 通过获取String 所在到列值，以及[所在到列值，组合判断是否符合规范
     * @param tree
     */
    @Override
    public void visitVariable(VariableTree tree) {

        String variableName = tree.simpleName().name();

        String typeName = tree.symbol().type().name();

        if("Array".equals(typeName)) {
            if (tree.type() instanceof JavaTree) {
                //获取数据类型
                JavaTree sTree = (JavaTree) tree.type();
                //获取基础数组类对象所在列
                int firstColumn = sTree.firstToken().column();
                //获取基础数组类对象到字符长度
                int arrayClassLenth = sTree.firstToken().text().length();
                //获取基础数组类对象 ] 所在列
                int lastColumn = sTree.lastToken().column();
                //根据对象所在列 + 对象长度 + 1,预期应等于类对象 ] 所在列
                if((firstColumn + arrayClassLenth + 1) != lastColumn){
                    context.reportIssue(this,tree,"[] should follow the array object");
                }
            }
        }

        super.visitVariable(tree);
    }

}
