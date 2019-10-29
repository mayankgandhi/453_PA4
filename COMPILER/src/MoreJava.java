import java.util.*;

import java.lang.String;

public class MoreJava {

	Scanner scan = new Scanner();
	int tempID = 0;
	int tlabelID = 0; // Label id for true
	int flabelID = 0; // Label id for false
	int rlabelID = 0; // Label id for loops

	// My global
	String threeAddressResult = "";
	LinkedList<ThreeAddressQuad> threeAddressResultList = new LinkedList<ThreeAddressQuad>();
	
	ThreeAddressQuad e;
	String prevSymbol = "";
	String lookahead = null; // keeps track of current token
	StringBuilder stream; // our stream of tokens

	void program() {
		if (lookahead.equals("void")) {
			match('v');
			String id = lookahead;
			
			match('i');
			
			if (!lookahead.equals("("))
				exit_program ("Missing open parenthesis");
			match('p');
			
			if (!lookahead.equals(")"))
				exit_program ("Missing closing parenthesis");
			match('p');
			
			if (!lookahead.equals("{"))
				exit_program ("Missing opening bracket");
			match('b');
			
			stmt_list();
			
			if (!lookahead.equals("}"))
				exit_program ("Missing closing bracket");
			match('b');
		}
		else {
			exit_program("void not found");
		}
	}
	
	void exit_program(String errorMessage) {
//		System.out.println(errorMessage);
//		System.exit(1);
	}
	
	void stmt_list() {
		while(lookahead.equals("int") ||
				lookahead.equals("while") ||
				lookahead.equals("if")) {
			smt();
		}
	}
	
	void smt() {
		if (lookahead.equals("int")) {
			assignment();
		}
		else if (lookahead.equals("if") || lookahead.equals("while")){
			control_flow();
		}
		
	}
	
	void control_flow() {
		if (lookahead.equals("if")) {
			tempID = 0;
			match('i'); // if
			
			if (!lookahead.equals("("))
				exit_program ("Missing open parenthesis");
			match('p'); //open parenthesis
			
			int left = E(); //left comparison
			
			String comparisonVal = lookahead;
			match('c'); // comparison val
			
			int right = E(); // right comparison
			int thisLabel = tlabelID;
			int thisFalse = flabelID;
			tlabelID++;
			flabelID++;
			if (comparisonVal.equals("<")) {
				this.threeAddressResult += "IF_LT: temp" + left + ", temp" + right + ", trueLabel"+thisLabel + "\n" ;
				e = new ThreeAddressQuad("IF_LT", "trueLabel"+Integer.toString(thisLabel), "temp" + Integer.toString(left), "temp" + Integer.toString(right));
				System.out.println("---------------"+ e.toString());				
			}
			else if (comparisonVal.equals(">")) {
				this.threeAddressResult += "IF_GT: temp" + left + ", temp" + right + ", trueLabel"+thisLabel + "\n" ;
				e = new ThreeAddressQuad("IF_GT", "trueLabel"+Integer.toString(thisLabel), "temp" + Integer.toString(left), "temp" + Integer.toString(right));
				System.out.println("---------------"+ e.toString());
			}
			else if (comparisonVal.equals("<=")) {
				this.threeAddressResult += "IF_LTE: temp" + left + ", temp" + right + ", trueLabel"+thisLabel + "\n" ;
				e = new ThreeAddressQuad("IF_LTE", "trueLabel"+Integer.toString(thisLabel), "temp" + Integer.toString(left), "temp" + Integer.toString(right));
				System.out.println("---------------"+ e.toString());
			}
			else if (comparisonVal.equals(">=")) {
				this.threeAddressResult += "IF_GTE: temp" + left + ", temp" + right + ", trueLabel"+thisLabel + "\n" ;
				e = new ThreeAddressQuad("IF_GTE", "trueLabel"+Integer.toString(thisLabel), "temp" + Integer.toString(left), "temp" + Integer.toString(right));
				System.out.println("---------------"+ e.toString());
			}
			else if (comparisonVal.equals("!=")) {
				this.threeAddressResult += "IF_NE: temp" + left + ", temp" + right + ", trueLabel"+thisLabel + "\n" ;
				e = new ThreeAddressQuad("IF_NE", "trueLabel"+Integer.toString(thisLabel), "temp" + Integer.toString(left), "temp" + Integer.toString(right));
				System.out.println("---------------"+ e.toString());
			}
			else if (comparisonVal.equals("==")) {
				this.threeAddressResult += "IF_EQ: temp" + left + ", temp" + right + ", trueLabel"+thisLabel + "\n" ;
				e = new ThreeAddressQuad("IF_EQ", "trueLabel"+Integer.toString(thisLabel), "temp" + Integer.toString(left), "temp" + Integer.toString(right));
				System.out.println("---------------"+ e.toString());
			}
			 else if (comparisonVal.equals("&&")) {
			 	this.threeAddressResult += "IF_EQ: temp" + left + ", temp" + right + ", trueLabel"+thisLabel + "\n" ;
			 }
			 else if (comparisonVal.equals("||")) {
			 	this.threeAddressResult += "IF_EQ: temp" + left + ", temp" + right + ", trueLabel"+thisLabel + "\n" ;
			 }
			
			this.threeAddressResult += "GOTO: falseLabel" + thisFalse + "\n";
			e = new ThreeAddressQuad("GOTO",null,null,"falseLabel"+Integer.toString(thisFalse));
			System.out.println("---------------"+ e.toString());
			
			this.threeAddressResult += "trueLabel" + thisLabel + "\n";
			e = new ThreeAddressQuad("GOTO",null,null,"trueLabel"+Integer.toString(thisLabel));
			System.out.println("---------------"+ e.toString());
			
			if (!lookahead.equals(")"))
				exit_program ("Missing closing parenthesis");
			match('p'); // closing parenthesis
			
			if (!lookahead.equals("{"))
				exit_program ("Missing openin bracket");
			match('b'); // opening bracket
			
			
			stmt_list();

			if (!lookahead.equals("}"))
				exit_program ("Missing closing bracket");
			match('b');
			this.threeAddressResult += "falseLabel" + thisFalse + "\n";
			e = new ThreeAddressQuad("GOTO",null,null,"falseLabel"+Integer.toString(thisFalse));
			System.out.println("---------------"+ e.toString());
			
		}
		else if (lookahead.equals("while")) {
			tempID = 0;
			match('w'); //while
			
			if (!lookahead.equals("("))
				exit_program ("Missing open parenthesis");
			match('p'); // opening parenthesis
			
			int thisRepeat = rlabelID;
			rlabelID++;
			this.threeAddressResult += "repeatLabel" + thisRepeat + "\n";
			
			int left = E();
			
			String comparisonVal = lookahead;
			match('c'); // comparison value
			
			int right = E();
			int thisLabel = tlabelID;
			int thisFalse = flabelID;
			tlabelID++;
			flabelID++;
			
			if (comparisonVal.equals("<")) {
				this.threeAddressResult += "IF_LT: temp" + left + ", temp" + right + ", trueLabel"+thisLabel + "\n" ;
				e = new ThreeAddressQuad("IF_LT", "trueLabel"+Integer.toString(thisLabel), "temp" + Integer.toString(left), "temp" + Integer.toString(right));
				System.out.println("---------------"+ e.toString());
			}
			else if (comparisonVal.equals(">")) {
				this.threeAddressResult += "IF_GT: temp" + left + ", temp" + right + ", trueLabel"+thisLabel + "\n" ;
				e = new ThreeAddressQuad("IF_GT", "trueLabel"+Integer.toString(thisLabel), "temp" + Integer.toString(left), "temp" + Integer.toString(right));
				System.out.println("---------------"+ e.toString());
			}
			else if (comparisonVal.equals("<=")) {
				this.threeAddressResult += "IF_LTE: temp" + left + ", temp" + right + ", trueLabel"+thisLabel + "\n" ;
				e = new ThreeAddressQuad("IF_LTE", "trueLabel"+Integer.toString(thisLabel), "temp" + Integer.toString(left), "temp" + Integer.toString(right));
				System.out.println("---------------"+ e.toString());
			}
			else if (comparisonVal.equals(">=")) {
				this.threeAddressResult += "IF_GTE: temp" + left + ", temp" + right + ", trueLabel"+thisLabel + "\n" ;
				e = new ThreeAddressQuad("IF_GTE", "trueLabel"+Integer.toString(thisLabel), "temp" + Integer.toString(left), "temp" + Integer.toString(right));
				System.out.println("---------------"+ e.toString());
			}
			else if (comparisonVal.equals("!=")) {
				this.threeAddressResult += "IF_NE: temp" + left + ", temp" + right + ", trueLabel"+thisLabel + "\n" ;
				e = new ThreeAddressQuad("IF_NE", "trueLabel"+Integer.toString(thisLabel), "temp" + Integer.toString(left), "temp" + Integer.toString(right));
				System.out.println("---------------"+ e.toString());
			}
			else if (comparisonVal.equals("==")) {
				this.threeAddressResult += "IF_EQ: temp" + left + ", temp" + right + ", trueLabel"+thisLabel + "\n" ;
				e = new ThreeAddressQuad("IF_EQ", "trueLabel"+Integer.toString(thisLabel), "temp" + Integer.toString(left), "temp" + Integer.toString(right));
				System.out.println("---------------"+ e.toString());
			}
			else if (comparisonVal.equals("&&")) {
			 	this.threeAddressResult += "IF_EQ: temp" + left + ", temp" + right + ", trueLabel"+thisLabel + "\n" ;
			 }
			 else if (comparisonVal.equals("||")) {
			 	this.threeAddressResult += "IF_EQ: temp" + left + ", temp" + right + ", trueLabel"+thisLabel + "\n" ;
			 }
			this.threeAddressResult += "GOTO: falseLabel" + thisFalse + "\n";
			e = new ThreeAddressQuad("GOTO",null,null,"falseLabel"+Integer.toString(thisFalse));
			System.out.println("---------------"+ e.toString());
			
			this.threeAddressResult += "trueLabel" + thisLabel + "\n";
			
			
			if (!lookahead.equals(")"))
				exit_program ("Missing closing parenthesis");
			match('p'); // closing parenthesis
			
			if (!lookahead.equals("{"))
				exit_program ("Missing open bracket");
			match('b'); // opening bracket
			
			stmt_list();
			
			this.threeAddressResult += "GOTO: repeatLabel" + thisRepeat + "\n";
			e = new ThreeAddressQuad("GOTO",null,null,"repeatLabel"+Integer.toString(thisRepeat));
			System.out.println("---------------"+ e.toString());
			
			if (!lookahead.equals("}"))
				exit_program ("Missing closing bracket");
			match('b'); //closing braket
			
			this.threeAddressResult += "falseLabel" + thisFalse + "\n";
			
		}
	}
	
	void assignment() {
		tempID = 0; // reset tempID
		if (lookahead.equals("int")) {
			match('i');
			String id = lookahead;
			match('d');
			
			if (!lookahead.equals("="))
				exit_program ("Missing equals");
			match('e');
			
			prevSymbol = "";
			int returnVal = E();
			
			if (!lookahead.equals(";"))
				exit_program ("Missing semicolon");
			match(';');
			
			this.threeAddressResult += id + " = temp" + returnVal + "\n";
			e = new ThreeAddressQuad("EQ", "temp"+Integer.toString(returnVal) ,null,id);
			System.out.println("---------------"+ e.toString());
		}
	}
	
	int E() {
		int x = T();
		int thisTempID = x;

		while (true) {
			// if current token is a plus
			if (lookahead.equals("+")) {
				match('+');

				int prev = x;
				x = T();
				thisTempID = tempID;
				tempID++;

				if (prevSymbol.equals("+"))
					prev++;
				else
					prevSymbol = "+";

				this.threeAddressResult += "temp" + thisTempID + " = temp" + prev + " + temp" + x + "\n";

				continue;
			}
			// if current token is a minus
			else if (lookahead.equals("-")) {
				match('-');

				int prev = x;
				x = T();
				thisTempID = tempID;
				tempID++;

				if (prevSymbol.equals("-"))
					prev++;
				else
					prevSymbol = "-";
				this.threeAddressResult += "temp" + thisTempID + " = temp" + prev + " - temp" + x + "\n";

				continue;
			}
			return thisTempID;
		}
	}

	// T/T'
	int T() {
		int x = F();
		int thisTempID = x;

		while (true) {
			// if current token is a multiplier
			if (lookahead.equals("*")) {
				match('*');

				int prev = x;
				x = F();

				thisTempID = tempID;
				tempID++;

				if (prevSymbol.equals("*"))
					prev++;
				else
					prevSymbol = "*";

				this.threeAddressResult += "temp" + thisTempID + " = temp" + prev + " * temp" + x + "\n";

				continue;
			}
			// if current token is a divisor
			else if (lookahead.equals("/")) {
				match('/');

				int prev = x;
				x = T();
				thisTempID = tempID;
				tempID++;

				if (prevSymbol.equals("/"))
					prev++;
				else
					prevSymbol = "/";

				this.threeAddressResult += "temp" + thisTempID + " = temp" + prev + " / temp" + x + "\n";

				continue;
			}
			return thisTempID;
		}
	}
	
	// F
	int F() {
		int toReturn = 0;
		int thisTempID = tempID;
		// if current token is a parenthesis
		if (lookahead.equals("(")) {
			match('(');
			toReturn = E();
			thisTempID = toReturn;
			match(')');
		}
		// if current token is a num
		else {
			try {
				toReturn = Integer.parseInt(lookahead);
				match((char) (toReturn + '0'));
				thisTempID = tempID;
				tempID++;
				this.threeAddressResult += "temp" + thisTempID + " = " + toReturn + "\n";
				e = new ThreeAddressQuad("EQ",Integer.toString(toReturn),"null","temp" + thisTempID);
				System.out.println("---------------"+ e.toString());
				return thisTempID;
			} catch (Exception NumberFormatException) {
				// nothing
			}
		}

		return thisTempID;
	}

	private void match(char c) {
		// needed since for some reason I cannot import the class, so I cannot check if
		// the token is null before getting string
		// so the solution (bad, but only I think) was a try/catch and check if there is
		// an exception of null pointer
		try {
			lookahead = scan.extractToken(stream).tokenVal;
		} catch (Exception NullPointerException) {

		}
	}

	public String getThreeAddr(String eval) {
		tempID = 0;
		tlabelID = 0;
		flabelID = 0;
		rlabelID = 0;
		prevSymbol = "";
		threeAddressResult = "";
		lookahead = null;
		// convert to stringbuilder to ease of use
		stream = new StringBuilder(eval);
		lookahead = scan.extractToken(stream).tokenVal;
		program();
		return this.threeAddressResult;
	}

	public static void main(String[] args) {
		MoreJava parser = new MoreJava();
		//String eval = "void main() { int xx = 3; while(3 < 2) { int xx = 4;} }";
		String eval = "void main() { int xx = 3; if( 2 < 3 && 5 < 4 ){ xx = 42; }} ";
		
		System.out.println(parser.getThreeAddr(eval));
	}
}