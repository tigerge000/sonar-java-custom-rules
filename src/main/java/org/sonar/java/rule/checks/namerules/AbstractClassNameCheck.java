package org.sonar.java.rule.checks.namerules;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.*;

/**
 * 抽象类命名检查
 * 抽象类命名使用 Abstract 或 Base 开头
 * Created by 古月随笔 on 2017/3/17.
 */
@Rule(key = "AbstractClassNameCheck")
public class AbstractClassNameCheck extends BaseTreeVisitor implements JavaFileScanner{

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractClassNameCheck.class);

    private JavaFileScannerContext context;

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        scan(context.getTree());
    }

    @Override
    public void visitClass(ClassTree tree) {
        String className = tree.simpleName().name();

        LOGGER.info(className + "<<>>" + tree.symbol().isAbstract());


        if(tree.symbol().isAbstract()){
            //判断名称是否以Abstract 或 Base 开头

            String abName = "Abstract";
            String bsName = "Base";
            //判断类名如果小于Abstract 或 Base
            if (className.length() < abName.length() || className.length() < bsName.length()) {
                context.reportIssue(this, tree, "The Name Of Abstract Class should use Abstract or Base first");
            } else {
                //判断是否存在 Abstract 或 Base
                if (!className.contains(abName)) {
                    if (!className.contains(bsName)) {
                        context.reportIssue(this, tree, "The Name Of Abstract Class should use Abstract or Base first");
                    } else {
                        if (className.indexOf(bsName) != 0) {
                            context.reportIssue(this, tree, "The Name Of Abstract Class should use Abstract or Base first");
                        }
                    }
                } else {
                    if (className.indexOf(abName) != 0) {
                        context.reportIssue(this, tree, "The Name Of Abstract Class should use Abstract or Base first");
                    }
                }
            }
        }

//        for(ModifierTree modifiersTree : tree.modifiers()){
//            if(modifiersTree instanceof ModifierKeywordTree) {
//                ModifierKeywordTree modifierKeywordTree = (ModifierKeywordTree) modifiersTree;
//                String name = modifierKeywordTree.modifier().name();
//                //判断是否为抽象类
//                if ("ABSTRACT".equals(name)) {
//                    //判断名称是否以Abstract 或 Base 开头
//
//                    String abName = "Abstract";
//                    String bsName = "Base";
//                    //判断类名如果小于Abstract 或 Base
//                    if (className.length() < abName.length() || className.length() < bsName.length()) {
//                        context.reportIssue(this, tree, "The Name Of Abstract Class should use Abstract or Base first");
//                    } else {
//                        //判断是否存在 Abstract 或 Base
//                        if (!className.contains(abName)) {
//                            if (!className.contains(bsName)) {
//                                context.reportIssue(this, tree, "The Name Of Abstract Class should use Abstract or Base first");
//                            } else {
//                                if (className.indexOf(bsName) != 0) {
//                                    context.reportIssue(this, tree, "The Name Of Abstract Class should use Abstract or Base first");
//                                }
//                            }
//                        } else {
//                            if (className.indexOf(abName) != 0) {
//                                context.reportIssue(this, tree, "The Name Of Abstract Class should use Abstract or Base first");
//                            }
//                        }
//                    }
//
//                }
//            }
//        }
        super.visitClass(tree);
    }

}
