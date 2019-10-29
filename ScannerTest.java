/**
 * CSC 453: Program Assignment 1
 * Group: Alexis Tinoco, Mayank Gandhi
 */
public class ScannerTest{

    /* Quick test of token extraction*/
    public static void testTokenExtraction(){
      System.out.println("*******************************************");
      System.out.println("Testing Token Extraction");
      Scanner test = new Scanner();

      System.out.print(test.extractTokens("23 || 33"));
      
      System.out.println("Congrats: preliminary token extraction tests passed! Now make your own test cases "+
                         "(this is only a subset of what we will test your code on)");
      System.out.println("*******************************************");
      System.out.println();
    }
  
    public static void main(String[] args){
      testTokenExtraction();
    }
  
  }
