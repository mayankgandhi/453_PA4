import java.io.*;

public class ThreeAddressQuad   {
    
    TokenType op;
    String OP;
    String src1, src2, dest;

    public ThreeAddressQuad(String OP, String src1, String src2, String dest)
    {
        this.OP = OP;
        this.src1 = src1;
        this.src2 = src2;
        this.dest = dest;
    }

	public String toString()
    {
        if(OP.equals("EQ"))
            return this.dest+ " = " + this.src1;
        else if( this.src1!=null && this.src2!=null )
        	return this.OP+": "+this.dest+ ", " + this.src1 + ", " + this.src2;
        else
        	return this.OP+": "+this.dest;
    }
    
    public static void main(String args[])
    {
        ThreeAddressQuad x = new ThreeAddressQuad("EQ", "921", null, "temp1");
        System.out.print(x.toString());
        
        ThreeAddressQuad e = new ThreeAddressQuad("IF_LT", "trueLabel"+Integer.toString(10), "temp" + Integer.toString(1), "temp" + Integer.toString(2));
		System.out.println(e.toString());
    }
}