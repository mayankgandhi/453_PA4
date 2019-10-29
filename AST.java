import java.util.*;
public class AST
{
    public class ASTNode
    {
        public ASTNode left, right;
        public TokenType Ttype;

        public ASTNode(TokenType type)
        {
            Ttype = type;
            left = null;
            right = null;
        }
    }

    public AST() {
    	head = null;
    }
    
    ASTNode head;
    
    public ASTNode BuildAST(TokenType postfix)
    {
    	return new ASTNode(postfix);
    }

    public void setLeftNode(ASTNode newLeft) {
    	head.left = newLeft;
    }
    
    public void setRightNode(ASTNode newRight) {
    	head.right = newRight;
    }
    
    public ASTNode getLeft() {
    	return head.left;
    }
    
    public ASTNode getRight() {
    	return head.right;
    }
    
    public ASTNode getHead() {
    	return head;
    }
    
    public void setHead(TokenType newHead) {
    	head = new ASTNode(newHead);
    }
    
    public static void main(String[] args) {
        AST a = new AST();
        a.setHead(TokenType.MUL);
    }
}