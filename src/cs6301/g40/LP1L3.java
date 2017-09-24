// Starter code for Level 3 driver for lp1

// Change following line to your group number
package cs6301.g40;
/*
 * Group members:
Mukesh Kumar(mxk170430)
Shikhar Pandya (sdp170030)
Arijeet Roy (axr165030)*/
import java.util.ArrayList;
import java.util.Iterator;
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
		// LP1L3 x = new LP1L3();
		// a linked hashmap to maintain all the value assignments
		Map<Character, Num> varAssignTable = new LinkedHashMap<>();
		//List of Strings containing the results
		List<String> resultList = new ArrayList<>();

		while (in.hasNext()) {
			String word = in.nextLine();
			// print the last variable representation and terminate
			if (word.equals(";")) {
				// show the internal representation of the last assigned
				// variable
				// get the last values from the map
				for (Iterator<String> iterator = resultList.iterator(); iterator.hasNext();) {
					String result = (String) iterator.next();
					System.out.println(result);
					
				}
				List<Entry<Character, Num>> entryList = new ArrayList<Map.Entry<Character, Num>>(
						varAssignTable.entrySet());
				Entry<Character, Num> lastValue = entryList.get(entryList.size() - 1);
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
				// add to the result list
				resultList.add(expression);
			}
			// evaluate postfix expression first
			else {
				Num valueOfExp = evaluateExpression(expression, varAssignTable);
				if (valueOfExp != null) {
					varAssignTable.put(variable, valueOfExp);
					// print the value of the postfix expression after
					// calculation
					resultList.add(valueOfExp.toString());
				} else {
					System.out.println("Program terminated due to wrong input format");
					break;
				}
			}
		}

		in.close();
	}

	// Evaluates the given postFix expression and returns the calculated value.
	// expression: postfix expression
	// varAssignTable: Hashmap referred by function to calculate expression
	public static Num evaluateExpression(String expression, Map<Character, Num> varAssignTable) {
		// TODO Auto-generated method stub
		// remove spaces from expression
		expression = expression.replaceAll("\\s+", "");
		expression = expression.replaceAll(",", "");
		expression = expression.replaceAll("\\[", "");
		expression = expression.replaceAll("\\]", "");
		Stack<Num> tempRes = new Stack<Num>();
		char[] chars = expression.toCharArray();

		for (int i = 0; i < chars.length; i++) {
			char ch = chars[i];

			if (isAnOperator(ch)) {

				switch (ch) {
				case '+':
					Num num2 = tempRes.pop();
					Num num1 = tempRes.pop();
					tempRes.push(Num.add(num1, num2));
					break;

				case '^':
					num2 = tempRes.pop();
					num1 = tempRes.pop();
					tempRes.push(Num.power(num1, num2));
					break;

				case '%':
					num2 = tempRes.pop();
					num1 = tempRes.pop();
					tempRes.push(Num.mod(num1, num2));
					break;

				case '|':
					num1 = tempRes.pop();
					tempRes.push(Num.squareRoot(num1));
					break;

				case '*':
					num2 = tempRes.pop();
					num1 = tempRes.pop();
					tempRes.push(Num.product(num1, num2));
					break;

				case '-':
					num2 = tempRes.pop();
					num1 = tempRes.pop();
					tempRes.push(Num.subtract(num1, num2));
					break;

				case '/':
					num2 = tempRes.pop();
					num1 = tempRes.pop();
					tempRes.push(Num.divide(num1, num2));
					break;
				}
			} else if (Character.isDigit(ch)) {
				// Number, push to the stack
				StringBuilder sb = new StringBuilder();
				Boolean check = true;
				while (Character.isDigit(chars[i]) && check) {
					sb.append(chars[i]);
					if (i + 1 < chars.length && Character.isDigit(chars[i + 1]))
						i++;
					else
						check = false;
				}
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
			return new Num(0, true);
	}

	static boolean isAnOperator(char ch) {
		return ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '%' || ch == '^' || ch == '|';
	}
}
