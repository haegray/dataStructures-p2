
/**
 * @author Helena Gray
 * @version 10.4.2018
 * 
 *  This class creates a post-fix calculator and facilitates calculations*/
//These are all the imports you are allowed, don't add any more!
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

class Computer {

	/**
	 * @param filename
	 *            the name of the file to read the instructions
	 * @throws IOException
	 *             if the file does not exist
	 * @return the head node of the node queue or null if file doesn't exist
	 */
	public static Node<String> fileToNodeQueue(String filename) throws IOException {
		// given a file name, open that file in a scanner and create a queue
		// of nodes the head of the queue of nodes should be the start of the
		// queue the values in the nodes should be the strings read in each time
		// you call next() on the scanner
		File program;
		Scanner scan;
		try {
			program = new File(filename + ".txt");
			scan = new Scanner(program);
			Node head = new Node(scan.next());
			Node current = head;
			while (scan.hasNext()) {
				Node next = new Node(scan.next());
				current.setNext(next);
				current = current.getNext();
			}
			Node tail = current;
			scan.close();
			return head;
		} catch (IOException e) {
			System.out.println("File does not exist!");
			return null;
		}
	}

	/**
	 * @param input
	 *            the node queue of instructions
	 * @param numSymbols
	 *            the number of symbols to process
	 * @return values of nodes remaining in input
	 */
	public Node<String> process(Node<String> input, int numSymbols) {
		// Given an input queue of symbols process the number of symbols
		// specified (numSymbols) and update the progStack and symbols
		// variables appropriately to reflect the state of the "computer"
		// (see below the "do not edit" line for these variables).

		for (int i = 0; i < numSymbols; i++) {
			int output = 0;
			String operation = input.getValue();

			boolean arithmetic = false;
			for (String s : INT_OPS) {
				if (s.equals(operation)) {
					arithmetic = true;
				}
			}

			if (!arithmetic) {
				for (String s : ASSIGN_OPS) {
					if (s.equals(operation)) {
						arithmetic = true;
					}
				}
			}

			if (operation.equals("print")) {
				String result = (String) progStack.pop();
				if (symbols.get(result) != null) {
					System.out.println(symbols.get(result));
				} else {
					System.out.println(result);
				}
			} else if (arithmetic) {

				String first = (String) progStack.pop();
				String second = (String) progStack.pop();

				int firstInt = 0;
				int secondInt = 0;

				if (symbols.get(first) != null) {
					firstInt = symbols.get(first);
				} else {
					firstInt = Integer.parseInt(first);
				}

				if (operation.equals("=")) {
					symbols.put(second, firstInt);
				} else {
					if (symbols.get(second) != null) {
						secondInt = symbols.get(second);
					} else {
						secondInt = Integer.parseInt(second);
					}
					switch (operation) {
					case "*":
						output = secondInt * firstInt;
						break;
					case "+":
						output = secondInt + firstInt;
						break;
					case "-":
						output = secondInt - firstInt;
						break;
					case "/":
						output = secondInt / firstInt;
						break;
					case "*=":
						output = secondInt * firstInt;
						symbols.put(second, output);
						break;
					case "+=":
						output = secondInt + firstInt;
						symbols.put(second, output);
						break;
					case "-=":
						output = secondInt - firstInt;
						symbols.put(second, output);
						break;
					case "/=":
						output = secondInt / firstInt;
						symbols.put(second, output);
						break;
					}
					String outputString = Integer.toString(output);
					if (operation.length() < 2) {
						progStack.push(outputString);
					}
				}
			} else {
				progStack.push(input.getValue());
			}

			if (input.getNext() == null) {
				return null;
			} else {
				input = input.getNext();
			}
		}
		// Return the remaining queue items.
		return input;
	}

	/**
	 * This is to test the main method
	 */
	public static void testMain() {
		// edit this as much as you want, if you use main without any arguments,
		// this is the method that will be run instead of the program
		System.out.println("You need to put test code in testMain() to run Computer with no parameters.");
	}

	// --------------------DON'T EDIT BELOW THIS LINE--------------------
	// ----------------------EXCEPT TO ADD JAVADOCS----------------------

	// don't edit these...
	public static final String[] INT_OPS = { "+", "-", "*", "/" };
	public static final String[] ASSIGN_OPS = { "=", "+=", "-=", "*=", "/=" };

	// or these...
	public ProgramStack<Object> progStack = new ProgramStack<>();
	public SymbolTable<Integer> symbols = new SymbolTable<>(5);

	public static void main(String[] args) {
		// this is not a testing main method, so don't edit this
		// edit testMain() instead!

		if (args.length == 0) {
			testMain();
			return;
		}

		if (args.length != 2 || !(args[1].equals("false") || args[1].equals("true"))) {
			System.out.println("Usage: java Computer [filename] [true|false]");
			System.exit(0);
		}

		try {
			(new Computer()).runProgram(args[0], args[1].equals("true"));
		} catch (IOException e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}

	// provided, don't change this
	/**
	 * @param filename
	 *            filename of file that contains program to execute
	 * @param debug
	 *            indicates if program will be run in debug mode or not
	 * @throws IOException
	 *             I don't see where this throws an exception but apparently it
	 *             does if something happens
	 */
	public void runProgram(String filename, boolean debug) throws IOException {
		Node<String> input = fileToNodeQueue(filename);
		System.out.println("\nProgram: " + Node.listToString(input));

		if (!debug) {
			while (input != null) {
				input = process(input, 10);
			}
		} else {
			Scanner s = new Scanner(System.in);
			for (int i = 1; input != null; i++) {
				System.out.println("\n######### Step " + i + " ###############\n");
				System.out.println("----------Step Output----------");
				input = process(input, 1);
				System.out.println("----------Symbol Table---------");
				System.out.println(symbols);
				System.out.println("----------Program Stack--------");
				System.out.println(progStack);
				if (input != null) {
					System.out.println("----------Program Remaining----");
					System.out.println(Node.listToString(input));
				}
				System.out.println("\nPress Enter to Continue");
				s.nextLine();
			}
		}
	}
}