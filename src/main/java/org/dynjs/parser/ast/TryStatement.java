package org.dynjs.parser.ast;

import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.tree.Tree;
import org.dynjs.parser.CodeVisitor;
import org.dynjs.parser.Statement;
import org.dynjs.runtime.ExecutionContext;

public class TryStatement extends AbstractStatement {
    private final Statement tryBlock;
    private final CatchClause catchClause;
    private final Statement finallyBlock;

    public TryStatement(Tree tree, Statement tryBlock, CatchClause catchClause, Statement finallyBlock) {
        super(tree);
        this.tryBlock = tryBlock;
        this.catchClause = catchClause;
        this.finallyBlock = finallyBlock;
    }

    public Statement getTryBlock() {
        return this.tryBlock;
    }

    public CatchClause getCatchClause() {
        return this.catchClause;
    }

    public Statement getFinallyBlock() {
        return this.finallyBlock;
    }
    
    public List<FunctionDeclaration> getFunctionDeclarations() {
        return this.tryBlock.getFunctionDeclarations();
    }

    public List<VariableDeclaration> getVariableDeclarations() {
        List<VariableDeclaration> decls = new ArrayList<>();
        decls.addAll(this.tryBlock.getVariableDeclarations());
        if (this.catchClause != null) {
            decls.addAll(this.catchClause.getVariableDeclarations());
        }
        if (this.finallyBlock != null) {
            decls.addAll(this.finallyBlock.getVariableDeclarations());
        }
        return decls;
    }

    public String toIndentedString(String indent) {
        StringBuffer buf = new StringBuffer();
        buf.append(indent).append("try {\n");
        buf.append(this.tryBlock.toIndentedString(indent + "  "));
        buf.append(indent).append("}\n");
        if (this.catchClause != null) {
            buf.append(this.catchClause.toIndentedString(indent + "  "));
        }
        if (this.finallyBlock != null) {
            buf.append(indent).append("finally {\n");
            buf.append(this.finallyBlock.toIndentedString(indent + "  "));
            buf.append(indent).append("}");
        }
        return buf.toString();
    }

    @Override
    public void accept(ExecutionContext context, CodeVisitor visitor, boolean strict) {
        visitor.visit(context, this, strict);
    }

}