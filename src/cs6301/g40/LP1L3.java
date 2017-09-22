// Starter code for Level 3 driver for lp1

// Change following line to your group number
package cs6301.g40;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LP1L3 {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
//		LP1L3 x = new LP1L3();
		//a linked hashmap to maintain all the value assignments
		Map<Character, Num> varAssignTable = new LinkedHashMap<>();

		while (in.hasNext()) {
			String word = in.nextLine();
			// print the last variable representation and terminate
			if (word.equals(";")) {
				// show the internal representation of the last assigned
				// variable
				//get the last values from the map
				List<Entry<Character, Num>> entryList =
					    new ArrayList<Map.Entry<Character ,Num>>(varAssignTable.entrySet());
					Entry<Character, Num> lastValue =
					    entryList.get(entryList.size()-1);
				if (lastValue != null) {
					lastValue.getValue().printList();
				}
				System.out.println("Program Terminated.");
				break;
			}
			// map for storing variable assignment values
			Pattern p = Pattern.compile("[-+*/%^|]");
			Matcher m = p.matcher(word);
			int startIndex = word.indexOf('=');
			int endIndex = word.indexOf(';');
			String varString = word.substring(0, startIndex).trim();
			char variable = varString.charAt(0);
			String expression = word.substring(startIndex + 1, endIndex).trim();

			// contains assignment to a variable
			if (!m.find()) {
				varAssignTable.put(variable, new Num(expression));
				// print the variable
				System.out.println(expression);
			}
			// evaluate postfix expression first
			else {
				Num valueOfExp = evaluateExpression(expression, varAssignTable);
				if(valueOfExp != null) {
					varAssignTable.put(variable, valueOfExp);					
					// print the value of the postfix expression after calculation
					System.out.println(valueOfExp.toString());
				}
				else {
					System.out.println("Program terminated due to wrong input format");
					break;
				}
			}
		}
		
		in.close();
	}

	public static Num evaluateExpression(String expression, Map<Character, Num> varAssignTable) {
		// TODO Auto-generated method stub
		// remove spaces from expression
		expression = expression.replaceAll("\\s+", "");
		Stack<Num> tempRes = new Stack<Num>();
		char[] chars = expression.toCharArray();

		for (int i = 0; i < chars.length; i++) {
			char ch = chars[i];

			if (isAnOperator(ch)) {
				if(tempRes.isEmpty()) {
					System.out.println("Wrong postfix expression.");
					break;
				}
				Num num2 = tempRes.pop();
				if(tempRes.isEmpty()) {
					System.out.println("Wrong postfix expression.");
					break;
				}
				Num num1 = tempRes.pop();

				switch (ch) {
				case '+':
					tempRes.push(Num.add(num1, num2));
					break;
				case '*':
					tempRes.push(Num.product(num1, num2));
					break;
				case '-':
					tempRes.push(Num.subtract(num1, num2));
					break;
				case '/':
					tempRes.push(Num.divide(num1, num2));
					break;
				case '%':
					tempRes.push(Num.mod(num1, num2));
					break;
				case '^':
					tempRes.push(Num.power(num1, num2));
					break;
				case '|':
					//since square root is a unary operator we push the num1 back
					tempRes.push(num1);
					tempRes.push(Num.squareRoot(num2));
					break;
				}
			} else if (Character.isDigit(ch)) {
				// Number, push to the stack
				StringBuilder sb = new StringBuilder();
				int digitIndex = i;
				while (Character.isDigit(chars[digitIndex]))
					sb.append(chars[digitIndex++]);
				tempRes.push(new Num(new String(sb)));
			} else if (Character.isLetter(ch)) {
				Num value = varAssignTable.get(ch);
				if (value != null) {
					tempRes.push(value);
				}
			}
		}

		if (!tempRes.isEmpty())
			return tempRes.pop();
		else
			return null;
	}

	static boolean isAnOperator(char ch) {
		return ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '%' || ch == '^' || ch == '|';
	}
}
