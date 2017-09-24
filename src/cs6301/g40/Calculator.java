package cs6301.g40;
/*
 * Group members:
Mukesh Kumar(mxk170430)
Shikhar Pandya (sdp170030)
Arijeet Roy (axr165030)*/
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

public class Calculator {
	
	// Used for label jumping via goto statements
	private HashMap<Integer, String> labels;
	
	// Used for holding variables
	// This is similar to Level III except it can now take infix as well as postfix expressions
	private HashMap<String, String> variables;
	
	// Used to store the precedence of operators
	private HashMap<String, Integer> precedence;
	
	
	public Calculator() {
		labels = new HashMap<>();
		variables = new HashMap<>();
		precedence = new HashMap<>();
	}
	
	// Sets the precedence of arithmetic operators
	private void setupPrecedence() {
		precedence.put("|", 4);
		precedence.put("^", 4);
		precedence.put("%", 4);
		precedence.put("/", 3);
		precedence.put("*", 3);
		precedence.put("-", 2);
		precedence.put("+", 2);
		precedence.put("(", 1);
	}
	
	// This function transforms infix expression to postfix expression
	// infixExpression: String which contains the infix Expression
	
	public String infixToPostfix(String infixExpression) {
		ArrayDeque<String> outputQueue = new ArrayDeque<>();
		Stack<String> operatorStack = new Stack<>();
		Tokenizer.Token currentToken;
		
		
		String[] tokens = infixExpression.split(" ");
		
		for (String token : tokens) {
			try {
				currentToken = Tokenizer.tokenize(token);
				
				switch (currentToken) {
					// If the current token is a number or variable
					case NUM:
						outputQueue.add(token);
						break;
					case VAR:
						outputQueue.add(token);
						break;
					case OP:
						while ((!operatorStack.empty()) &&
								(precedence.get(operatorStack.peek()) >= precedence.get(token))) {
							outputQueue.add(operatorStack.pop());
						}
						operatorStack.push(token);
						break;
					case OPEN:
						operatorStack.push(token);
						break;
					case CLOSE:
						// TODO : Instead of this
						/*
						String topToken = operatorStack.pop();
						
						while (topToken != "(") {
							outputQueue.add(topToken);
							
							if (operatorStack.empty()) {
								// TODO : This must throw an exception and current solution is temporary
								System.out.println("ERROR : Mismatched parenthesis");
							}
							topToken = operatorStack.pop();
						}
						*/
						
						// TODO : Try this
						// TODO : Both works but this doesn't need to allocate memory for String
						while (operatorStack.peek().equals("(")) {
							if (operatorStack.empty()) {
								// TODO : This must throw an exception and current solution is temporary
								System.out.println("ERROR : Mismatched parenthesis");
							}
							
							outputQueue.add(operatorStack.pop());
						}
						
						operatorStack.pop();    // Pop the OPEN bracket
						break;
				}
				
			} catch (Exception e) {
				// TODO : This must throw an exception and current solution is temporary
				System.out.println("Could not tokenize token : " + token);
			}
		}
		
		while (!operatorStack.empty()) {
			outputQueue.add(operatorStack.pop());
		}
		
//		System.out.println(outputQueue.toString());
		return outputQueue.toString();
	}
	
	public static void main(String[] args) throws Exception {
		Scanner in = new Scanner(System.in);
		while (in.hasNext()) {
			String s = in.nextLine();
			Calculator calculator = new Calculator();
			calculator.setupPrecedence();
			calculator.infixToPostfix(s);
		}
	}
}
