/**
 * @author Helena Gray
 * @version 10.4.2018
 * 
 *          This class creates a Node object and comes with associated operations
 *          to change values of Node
 */
class Node<T> {
	private T value;
	private Node<T> next;
	private Node<T> prev;

	/**
	 * @param value
	 *            the input value for the Node
	 */
	public Node(T value) {
		this.value = value;
	}

	/**
	 * @return value of Node
	 */
	public T getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the input value for the Node
	 */
	public void setValue(T value) {
		this.value = value;
	}

	/**
	 * @return this.next returns the next node
	 */
	public Node<T> getNext() {
		return this.next;
	}

	/**
	 * @param next
	 *            the Node to be set as the next Node for the current Node
	 */
	public void setNext(Node<T> next) {
		this.next = next;
	}

	/**
	 * @return this.prev returns the previous node
	 */
	public Node<T> getPrev() {
		return this.prev;
	}

	/**
	 * @param prev
	 *            the Node to be set as the previous Node for the current Node
	 */
	public void setPrev(Node<T> prev) {
		this.prev = prev;
	}

	/**
	 * @param head
	 *            the head Node of a LinkedList
	 * @return the linked list as a string
	 */
	public static String listToString(Node<?> head) {
		StringBuilder ret = new StringBuilder();
		Node<?> current = head;
		while (current != null) {
			ret.append(current.value);
			ret.append(" ");
			current = current.getNext();
		}
		return ret.toString().trim();
	}

	/**
	 * @param head
	 *            the head Node of a LinkedList
	 * @return the linked list as a string but BACKWARDS
	 */
	public static String listToStringBackward(Node<?> head) {
		Node<?> current = head;

		while (current.getNext() != null) {
			current = current.getNext();

		}

		StringBuilder ret = new StringBuilder();
		while (current != null) {
			ret.append(current.value);
			ret.append(" ");
			current = current.getPrev();
		}
		return ret.toString().trim();
	}

	public static void main(String[] args) {
		// main method for testing, edit as much as you want

		// make nodes
		Node<String> n1 = new Node<>("A");
		Node<String> n2 = new Node<>("B");
		Node<String> n3 = new Node<>("C");

		// connect forward references
		n1.setNext(n2);
		n2.setNext(n3);

		// connect backward references
		n3.setPrev(n2);
		n2.setPrev(n1);

		// print forward and backward
		System.out.println(Node.listToString(n1));
		System.out.println(Node.listToStringBackward(n1));
	}
}