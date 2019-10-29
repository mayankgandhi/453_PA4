public class SimpleJavaTest{

    public static void TestThreeAddrGen(){
      System.out.println("*******************************************");
      System.out.println("Testing Three Address Generation");
      SimpleJava parser = new SimpleJava();
  
      String eval = "void blarg() {}";
      String result = "";
      assert(parser.getThreeAddr(eval).equals(result));
  
      eval = "void main() {int xx = 3;}";
      result = "temp0 = 3\nxx = temp0\n";
      assert(parser.getThreeAddr(eval).equals(result));
  
      eval = "void main() { int xx = 12; if(3 < 6) { int yy = 3; } }";
      result = "temp0 = 12\n"+
               "xx = temp0\n"+
               "temp0 = 3\n"+
               "temp1 = 6\n"+
               "IF_LT: temp0, temp1, trueLabel0\n"+
               "GOTO: falseLabel0\n"+
               "trueLabel0\n"+
               "temp0 = 3\n"+
               "yy = temp0\n"+
               "falseLabel0\n";
      
      assert(parser.getThreeAddr(eval).equals(result));
  
      eval = "void main() { int xx = 3; while(3 < 2) { int xx = 4;} }";
      result = "temp0 = 3\n"+
               "xx = temp0\n"+
               "repeatLabel0\n"+
               "temp0 = 3\n"+
               "temp1 = 2\n"+
               "IF_LT: temp0, temp1, trueLabel0\n"+
               "GOTO: falseLabel0\n"+
               "trueLabel0\n"+
               "temp0 = 4\n"+
               "xx = temp0\n"+
               "GOTO: repeatLabel0\n"+
               "falseLabel0\n";
      assert(parser.getThreeAddr(eval).equals(result));

      System.out.println("Congrats: three address generation tests passed! Now make your own test cases "+
                         "(this is only a subset of what we will test your code on)");
      System.out.println("*******************************************");
    }
  
    public static void main(String[] args){
      TestThreeAddrGen();
    }
  
  }
  