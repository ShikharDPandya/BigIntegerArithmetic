// Starter code for Level 4 driver for lp1
package cs6301.g40;
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
	
	public static void gotoFn(Map<Character, Num> value,Map<String, String> expression ,String word, String nextWord){
		int midIndex = nextWord.indexOf('?');
		int endIndex = nextWord.indexOf(';');
		String temp = nextWord.substring(0,midIndex).trim();
		char var = temp.charAt(0);
		Num val = value.get(var);
		String whereToGo = nextWord.substring(midIndex + 1, endIndex).trim();
		Set set = expression.entrySet();
		
		Num zero = new Num("0");
		
		Iterator it = set.iterator();
		Map.Entry me = (Map.Entry)it.next();
		while(!me.getKey().equals(whereToGo)){
			me = (Map.Entry)it.next();
			
		}
		
		String str = (String) me.getValue();
		String key = (String) me.getKey();
		while(me.getKey()!=word){
			
			storeAssigment((String) me.getValue(), value);
			me = (Map.Entry)it.next();
		}
		 val = value.get(var);
		if(val.compareTo(zero)>0)
			gotoFn(value,expression,word,nextWord);
	}
	
	public static void storeAssigment(String word, Map<Character, Num> value){
		
		int startIndex = word.indexOf("=");
		int endIndex = word.indexOf(";");
		String varString = word.substring(0,startIndex).trim();
		
		String express = word.substring(startIndex+1,endIndex);
		Character variable = varString.charAt(0);
		
		Num valueOfExp = LP1L3.evaluateExpression(express,value);
		value.put(variable,valueOfExp);
	}
	
	public static void main(String[] args) {
		Scanner in;
		if (args.length > 0) {
			int base = Integer.parseInt(args[0]);
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
					Num lastValue = value.get(value.size() - 1);
					if(lastValue!=null)
						lastValue.printList();
					System.out.print("Program Terminated");
					break;
				}
				else{
					Pattern p = Pattern.compile("\\d");
					Pattern oper = Pattern.compile("[-+*/]");
					Pattern goTo  = Pattern.compile("[?]");
					Matcher m = p.matcher(word);
					
					
					if(m.find()){
						String nextWord = in.nextLine();
						Matcher m1 = goTo.matcher(nextWord);
						Matcher m2 = oper.matcher(nextWord);
						if(m1.find()){
							expression.put(word,nextWord);
							int midIndex = nextWord.indexOf('?');
							int endIndex = nextWord.indexOf(';');
							String temp = nextWord.substring(0,midIndex).trim();
							char var = temp.charAt(0);
							Num val = value.get(var);
							String whereToGo = nextWord.substring(midIndex + 1, endIndex).trim();
							Num zero = new Num("0");
							Set set = expression.entrySet();
							if(val.compareTo(zero)>0)
							gotoFn(value,expression,word,nextWord);
							
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
							storeAssigment(nextWord,value);
						}
						else{
							
							int startIndex = nextWord.indexOf('=');
							int endIndex = nextWord.indexOf(';');
							String varString = nextWord.substring(0, startIndex).trim();
							char variable = varString.charAt(0);
							String e = nextWord.substring(startIndex + 1, endIndex).trim();
							Num num = new Num (e);
							
							expression.put(word,nextWord);
							value.put(variable,num);
						}
					}
					else{
						String nextWord = in.nextLine();
						word = word.concat(nextWord);
						StringBuilder sb = new StringBuilder();
						sb.append(i++);
						String lineno = "A".concat(new String(sb));
						expression.put(lineno,word);
						storeAssigment(word,value);
					}
				}
			
		}
	}
}
