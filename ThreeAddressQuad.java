
public class ThreeAddressQuad   {
    
    public enum TokenType{
	    NUM, PLUS, MINUS, MUL, DIV, LT, LTE, GT, GTE, OP, CP, NOEQ,
	    EQEQ, EQ, ID, INT, SECOL, OBRA, CBRA, IF, WHILE, VOID, ANDAND, OROR,
	    IF_LT, IF_GT, IF_LTE, IF_GTE, IF_NE, IF_EQ;
    }
    TokenType op;
    String src1, src2, dest;

    public ThreeAddressQuad(TokenType op, String src1, String src2, String dest)
    {
        this.op = op;
        this.src1 = src1;
        this.src2 = src2;
        this.dest = dest;
    }

    public String toString()
    {
        if(op.equals(TokenType.EQ))
            return this.dest+ " = " + this.src1 + "\n";
        return "";
    }
    
    public static void main(String args[])
    {
        ThreeAddressQuad x = new ThreeAddressQuad(TokenType.EQ, "921", null, "temp1");
        System.out.print(x.toString());
    }
}