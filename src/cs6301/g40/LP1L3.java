// Starter code for Level 3 driver for lp1

// Change following line to your group number
package cs6301.g40;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LP1L3 {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		LP1L3 x = new LP1L3();
		Map<Character, Num> varAssignTable = new LinkedHashMap<>();

		while (in.hasNext()) {
			String word = in.next();
			// print the last variable representation and terminate
			if (word.equals(";")) {
				// show the internal representation of the last assigned
				// variable
				Num lastValue = varAssignTable.get(varAssignTable.size() - 1);
				if (lastValue != null) {
					lastValue.printList();
				}
				System.out.println("Program Terminated.");
				break;
			}
			// map for storing variable assignment values
			Pattern p = Pattern.compile("[-+*/]");
			Matcher m = p.matcher(word);
			int startIndex = word.indexOf('=');
			int endIndex = word.indexOf(';');
			String varString = word.substring(0, startIndex).trim();
			char variable = varString.charAt(0);
			String expression = word.substring(startIndex, endIndex).trim();

			// contains assignment to a variable
			if (!m.find()) {
				varAssignTable.put(variable, new Num(expression));
				// print the variable
				System.out.println(expression);
			}
			// evaluate postfix expression first
			else {
				Num valueOfExp = evaluateExpression(expression, varAssignTable);
				varAssignTable.put(variable, valueOfExp);
				// print the value of the postfix expression after calculation
				System.out.println(valueOfExp.toString());
			}
		}
	}

	private static Num evaluateExpression(String expression, Map<Character, Num> varAssignTable) {
		// TODO Auto-generated method stub
		// remove spaces from expression
		expression = expression.replaceAll("\\s+", "");
		Stack<Num> tempRes = new Stack<Num>();
		char[] chars = expression.toCharArray();

		for (int i = 0; i < chars.length; i++) {
			char ch = chars[i];

			if (isAnOperator(ch)) {
				Num num2 = tempRes.pop();
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
				}
			} else if (Character.isDigit(ch)) {
				// Number, push to the stack
				StringBuilder sb = new StringBuilder();
				while (Character.isDigit(chars[i]))
					sb.append(chars[i++]);
				tempRes.push(new Num(new String(sb)));
			} else if (Character.isLetter(ch)) {
				Num value = varAssignTable.get(String.valueOf(ch));
				if (value != null) {
					tempRes.push(value);
				}
			}
		}

		if (!tempRes.isEmpty())
			return tempRes.pop();
		else
			return new Num(0);
	}

	static boolean isAnOperator(char ch) {
		return ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '%' || ch == '^' || ch == '|';
	}
}
