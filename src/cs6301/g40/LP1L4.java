// Starter code for Level 4 driver for lp1
package cs6301.g40;

/*
 * Group members:
Mukesh Kumar(mxk170430)
Shikhar Pandya (sdp170030)
Arijeet Roy (axr165030)*/

// Change following line to your group number
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Collection;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Iterator;
import java.util.Set;

import java.util.Scanner;

public class LP1L4 {
	static StringBuilder strbuilder = new StringBuilder();
	// This function is called when GOTO statement i.e (?) is encounted in the user input
	// It takes the two HashMaps , line number of expression, and arithmetic expression as parameters
	// It finds out the line number to go to i.e "whereToGo"
	// Iterates through the HashMap (which maps line number to arithmetic expression)
	// starts evaluating the arithmetic expression from line number "whereToGo"
	
	
	public static void gotoFn(Map<Character, Num> value,Map<String, String> expression ,String word, String nextWord,String whereToGo,char var){
	
		Num val = value.get(var);
		Num numWhrTo = new Num (whereToGo);
		Num present = new Num (word);
		Set set = expression.entrySet();
		
		Num zero = new Num("0");
		
		Iterator it = set.iterator();
		Map.Entry me = (Map.Entry)it.next();
		
		
			while(!me.getKey().equals(whereToGo))
				me = (Map.Entry)it.next();
			
	
			parse(it,value,expression,me);
			return;
			
		
	}
	
	// This function takes a arithmetic expression, two HashMaps and Line number as parameter,
	// It separates the variable and postfix expression in the String
	// evaluate the postfix expression in the string and put the value in the value HashMap with variable as key.
	// It also stores output in the StringBuilder
	
	public static void storeAssigment(String word, Map<Character, Num> value,Map<String, String> expression, String lineNum){
		
		
		int startIndex = word.indexOf("=");
		int endIndex = word.indexOf(";");
		String varString = word.substring(0,startIndex).trim();
		
		String express = word.substring(startIndex+1,endIndex);
		Character variable = varString.charAt(0);
		
		Num valueOfExp = LP1L3.evaluateExpression(express,value);
		Pattern p = Pattern.compile("[a-zA-Z]");
		Matcher m = p.matcher(lineNum);
		if(m.find()){
			strbuilder.append(valueOfExp);
			strbuilder.append('\n');}
	
		value.put(variable,valueOfExp);
	}
	
	public static void main(String[] args) {
		Scanner in;
		if (args.length > 0) {
			int base = Integer.parseInt(args[0]);
			Num.defaultBase = base;
			// Use above base for all numbers (except I/O, which is in base 10)
		}
		Map<String, String> expression = new java.util.LinkedHashMap<>();
		Map<Character, Num> value = new java.util.LinkedHashMap<>();
		
		in = new Scanner(System.in);
		LP1L4 x = new LP1L4();
		int i=0;
		
			while(in.hasNext()) {
				String word = in.next();
				if(word.equals(";")){
					StringBuilder sb = new StringBuilder();
					sb.append(i++);
					String lineno = "A".concat(new String(sb));
					expression.put(lineno, word);
					break;
				
				}
				else{
					Pattern p = Pattern.compile("\\d");
					Pattern oper = Pattern.compile("[-+*/%|^]");
					Pattern goTo  = Pattern.compile("[?]");
					Matcher m = p.matcher(word);
					
					
					if(m.find()){
						String nextWord = in.nextLine();
						Matcher m1 = goTo.matcher(nextWord);
						Matcher m2 = oper.matcher(nextWord);
						if(m1.find()){
							expression.put(word,nextWord);
						}
						
						else if(m2.find())
							{
								int startIndex = nextWord.indexOf('=');
								int endIndex = nextWord.indexOf(';');
								String varString = nextWord.substring(0, startIndex).trim();
								char variable = varString.charAt(0);
								String e = nextWord.substring(startIndex + 1, endIndex).trim();
								if(e.length()>1)
								{
							   Calculator calc = new Calculator();
							   nextWord = calc.infixToPostfix(e);
								StringBuilder sb = new StringBuilder();
									sb.append(variable);
									sb.append('=');
									sb.append(nextWord);
									sb.append(';');
									nextWord = new String (sb);
								}
							expression.put(word,nextWord);
						
						}
						else{
							
							int startIndex = nextWord.indexOf('=');
							int endIndex = nextWord.indexOf(';');
							String varString = nextWord.substring(0, startIndex).trim();
							char variable = varString.charAt(0);
							String e = nextWord.substring(startIndex + 1, endIndex).trim();
							Num num = new Num (e);
							
							expression.put(word,nextWord);
							
							
						}
					}
					else{
						String nextWord = in.nextLine();
						String temp  = nextWord.trim();
						String comma = ";";
						if(temp.compareTo(comma)==0){
						char key = word.charAt(0);
						
					       word = word.concat(temp);
					   }
						else {
							word = word.concat(nextWord);
												}
						
						StringBuilder sb = new StringBuilder();
						sb.append(i++);
						String lineno = "A".concat(new String(sb));
						expression.put(lineno, word);
					}
				}
			
		}
		
		Set set = expression.entrySet();
	
		Iterator it = set.iterator();
		Map.Entry me = (Map.Entry)it.next();
	    parse(it,value,expression,me );
    
	}
	
	// This function takes two hashmaps , iterator of Expression Hashmap as input
	// It goes through each entry in expression hashmap one by one and evaluate arithmetic expression and populate
	// value hashmap with values.
	
	public static void parse (Iterator it, Map<Character, Num> value,Map<String, String> expression,Map.Entry me ){
		
		while(me!=null){
			String lineNum = (String) me.getKey();
			String expr = (String) me.getValue();
			
			if(expr.equals(";")){
				//Num lastValue; // = value.get(value.size() - 1);
				Set set1 = value.entrySet();
				Iterator it1 = set1.iterator();
				Map.Entry me1 = null;
				Map.Entry me2 = null;
				while(it1.hasNext())
				{
				//	me2 = me1;
					
					me1 = (Map.Entry)it1.next();
				}
				Num lastValue = (Num) me1.getValue();
				String str = new String(strbuilder);
				System.out.println(str);
				if(lastValue!=null) {
				 System.out.println(lastValue.toString());
				}
				System.out.print("Program Terminated");
				return;
			}
			else{
				Pattern p = Pattern.compile("[a-zA-Z]");
				Pattern oper = Pattern.compile("[-+*/|^%]");
				Pattern goTo  = Pattern.compile("[?]");
				Pattern goTonext = Pattern.compile("[:]");
				Matcher m = p.matcher(lineNum);
				if(!m.find()){
					//	String nextWord = in.nextLine();
					Matcher m0 = goTonext.matcher(expr);
					Matcher m1 = goTo.matcher(expr);
					Matcher m2 = oper.matcher(expr);
					if(m1.find()){
						if(!m0.find()){
						int midIndex = expr.indexOf('?');
						int endIndex = expr.indexOf(';');
						String temp = expr.substring(0,midIndex).trim();
						char var = temp.charAt(0);
						Num val = value.get(var);
						String whereToGo = expr.substring(midIndex + 1, endIndex).trim();
						Num zero = new Num("0");
						//	Set set = expression.entrySet();
						if(val.compareTo(zero)!=0){
							gotoFn(value,expression,lineNum,expr,whereToGo,var);
						    return;}
						}
							
							else{
							
							int startIndex = expr.indexOf('?');
							int midIndex = expr.indexOf(':');
							int endIndex = expr.indexOf(';');
							String temp = expr.substring(0,midIndex).trim();
							char var = temp.charAt(0);
							String whereToGo1 = expr.substring(startIndex + 1, midIndex).trim();
							String whereToGo2 = expr.substring(midIndex + 1, endIndex).trim();
							Num zero = new Num("0");
							Num val = value.get(var);
							if(val.compareTo(zero)!=0){
								gotoFn(value,expression,lineNum,expr,whereToGo1,var);
							return;
							}
							else{
								gotoFn(value,expression,lineNum,expr,whereToGo2,var);
							return;
							}
						}
						
						
					}
					
					else if(m2.find())
					{
					
						storeAssigment(expr,value,expression,lineNum);
					}
					else {
						int startIndex = expr.indexOf('=');
						int endIndex = expr.indexOf(';');
						String varString = expr.substring(0, startIndex).trim();
						char variable = varString.charAt(0);
						String e = expr.substring(startIndex + 1, endIndex).trim();
						Num num;
						if(value.containsKey(e.charAt(0)))
						{
							num = value.get(e.charAt(0));
						}
						else{
							num = new Num (e);
						}
						value.put(variable,num);
					}
				}
				
				else{
					
					String comma = ";";
					if(expr.length()==1 && expr.compareTo(comma)==0)
					{
						Num lastValue = value.get(value.size() - 1);
						if(lastValue!=null)
							lastValue.printList();
						String str = new String(strbuilder);
						System.out.println(str);
						System.out.print("Program Terminated");
						break;
					}
					
					else if(expr.length()==2)
					{
						char key = expr.charAt(0);
						strbuilder.append(value.get(key).toString());
						strbuilder.append('\n');
					}
					else{
						storeAssigment(expr,value,expression,lineNum);
					}
					
					
				}
			}
			me = (Map.Entry)it.next();
		}
	}
}
