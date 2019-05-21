/**
 * @author Helena Gray
 * @version 10.4.2018
 * 
 *          This class creates a SymbolTable that acts similarly to a hash table
 *          It's much like a dictionary in that it stores keys and values, and
 *          can look up a key and return a value, and much more
 */

class SymbolTable<T> {
	private TableEntry<String, T>[] storage;
	private int size;

	/**
	 * @param size
	 *            capacity of the SymbolTable constructor for SymbolTable
	 */
	@SuppressWarnings("unchecked")
	public SymbolTable(int size) {
		// Create a hash table where the initial storage
		// is size and string keys can be mapped to T values.
		// You may assume size is >= 2
		if (size < 2) {
			size = 2;
		}
		storage = new TableEntry[size];
		this.size = 0;
	}

	/**
	 * @return storage.length returns the capacity of the SymbolTable
	 */
	public int getCapacity() {
		// return how big the storage is
		// O(1)
		return storage.length;
	}

	/**
	 * @return size returns the number of elements in the SymbolTable
	 */
	public int size() {
		// return the number of elements in the table
		// O(1)
		return size;
	}

	/**
	 * @param k
	 *            the key of object to be placed. It is used to look up values.
	 * @param v
	 *            the value of the object to be placed. It is returned when k is
	 *            found
	 */
	public void put(String k, T v) {
		// Place value v at the location of key k.
		// Use linear probing if that location is in use.
		// You may assume both k and v will not be null.
		// Worst case: O(n), Average case: O(1)

		// Use the Math.absolute value of k.hashCode() for
		// the probe start.
		int indexOriginal = Math.abs(k.hashCode()) % this.getCapacity();
		int index = indexOriginal;
		// If the key already exists in the table
		// replace the current value with v.
		if (this.get(k) != null) {
			while (storage[index] == null || !storage[index].getKey().equals(k)) {
				index = index + 1;
				if (index >= this.getCapacity()) {
					index = 0;
				} else if (index == indexOriginal) {
					break;
				}
			}
		} else {
			while (storage[index] != null && !storage[index].getKey().equals("RIP")) {
				index = index + 1;
				if (index >= this.getCapacity()) {
					index = 0;
				} else if (index == indexOriginal) {
					break;
				}
			}
			size++;
		}
		// Make a TableEntry to store in storage
		storage[index] = new TableEntry<String, T>(k, v);
		// If the key isn't in the table and the table
		// is >= 80% full, expand the table to twice
		// the size and rehash
		if ((this.size() >= (this.getCapacity() * .8))) {
			this.rehash(this.getCapacity() * 2);
		}
	}

	/**
	 * @param k
	 *            the key of object to be removed
	 * @return value of removed object
	 */
	public T remove(String k) {
		// Remove the given key (and associated value)
		// from the table. Return the value removed.
		// If the value is not in the table, return null.
		// Worst case: O(n), Average case: O(1)
		TableEntry<String, T> oldValue = new TableEntry<String, T>(k, this.get(k));
		if (oldValue.getValue().equals(null)) {
			return null;
		} else {
			size--;
			int index = Math.abs(k.hashCode()) % this.getCapacity();
			while ((!(storage[index].getKey().equals(k)))) {
				index = index + 1;
			}
			storage[index] = new TableEntry<String, T>("RIP", null);
			return oldValue.getValue();
		}
	}

	/**
	 * @param k
	 *            the key of object to be found
	 * @return value of found object
	 */
	public T get(String k) {
		// Given a key, return the value from the table.
		// If the value is not in the table, return null.
		// Worst case: O(n), Average case: O(1)
		int indexOriginal = Math.abs(k.hashCode()) % this.getCapacity();
		int index = indexOriginal;
		while (storage[index] != null && (!(storage[index].getKey().equals(k)))) {
			index = index + 1;
			if (index >= this.getCapacity()) {
				index = 0;
			} else if (index == indexOriginal) {
				return null;
			}
		}
		if (storage[index] == null) {
			return null;
		} else {
			return storage[index].getValue();
		}
	}

	/**
	 * @param index
	 *            the index of the object to be checked
	 * @return true or false depending on if value is a tombstone
	 */
	public boolean isTombstone(int index) {
		// this is a helper method needed for printing
		// return whether or not there is a tombstone at the
		// given index
		// O(1)
		TableEntry testValue = storage[index];
		if ((testValue == null) || !(storage[index].getKey().equals("RIP"))) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * @param size
	 *            is the size of the new SymbolTable
	 * @return true or false depending on if the SymbolTable was able to be
	 *         rehashed
	 */
	@SuppressWarnings("unchecked")
	public boolean rehash(int size) {
		// Increase or decrease the size of the storage,
		// rehashing all values.
		// If the new size won't fit all the elements,
		// return false and do not rehash. Return true
		// if you were able to rehash.
		if (size < this.size()) {
			return false;
		} else {
			int index = 0;
			TableEntry<String, T>[] tempStorage = new TableEntry[size];
			for (TableEntry t : storage) {
				if (t != null && !t.getKey().equals("RIP")) {
					index = Math.abs(t.getKey().hashCode()) % tempStorage.length;
					while (index < tempStorage.length && (tempStorage[index] != null)
							&& !(tempStorage[index].getKey().equals(t.getKey()))) {
						// what to do with index for linear probe
						index = index + 1;
					}
					tempStorage[index] = t;
				}
			}
			storage = tempStorage;
			return true;
		}
	}

	public static void main(String[] args) {
		// main method for testing, edit as much as you want
		SymbolTable<String> st1 = new SymbolTable<>(10);
		SymbolTable<Integer> st2 = new SymbolTable<>(5);
		if (st1.getCapacity() == 10 && st2.getCapacity() == 5 && st1.size() == 0 && st2.size() == 0) {
			System.out.println("Yay 1");
		}
		st1.put("a", "apple");
		st1.put("b", "banana");
		st1.put("banana", "b");
		st1.put("b", "butter");
		if (st1.toString().equals("a:apple\nb:butter\nbanana:b") && st1.toStringDebug().equals(
				"[0]: null\n[1]: null\n[2]: null\n[3]: null\n[4]: null\n[5]: null\n[6]: null\n[7]: a:apple\n[8]: b:butter\n[9]: banana:b")) {
			System.out.println("Yay 2");
		}
		if (st1.getCapacity() == 10 && st1.size() == 3 && st1.get("a").equals("apple") && st1.get("b").equals("butter")
				&& st1.get("banana").equals("b")) {
			System.out.println("Yay 3");
		}
		st2.put("a", 1);
		st2.put("b", 2);
		st2.put("e", 3);
		st2.put("y", 4);

		if (st2.toString().equals("e:3\ny:4\na:1\nb:2") && st2.toStringDebug().equals(
				"[0]: null\n[1]: e:3\n[2]: y:4\n[3]: null\n[4]: null\n[5]: null\n[6]: null\n[7]: a:1\n[8]: b:2\n[9]: null")) {
			System.out.println("Yay 4");
		}
		if (st2.getCapacity() == 10 && st2.size() == 4 && st2.get("a").equals(1) && st2.get("b").equals(2)
				&& st2.get("e").equals(3) && st2.get("y").equals(4)) {
			System.out.println("Yay 5");
		}
		if (st2.remove("e").equals(3) && st2.getCapacity() == 10 && st2.size() == 3 && st2.get("e") == null
				&& st2.get("y").equals(4)) {
			System.out.println("Yay 6");
		}
		if (st2.toString().equals("y:4\na:1\nb:2") && st2.toStringDebug().equals(
				"[0]: null\n[1]: tombstone\n[2]: y:4\n[3]: null\n[4]: null\n[5]: null\n[6]: null\n[7]: a:1\n[8]: b:2\n[9]: null")) {
			System.out.println("Yay 7");
		}
		if (st2.rehash(2) == false && st2.size() == 3 && st2.getCapacity() == 10) {
			System.out.println("Yay 8");
		}
		if (st2.rehash(4) == true && st2.size() == 3 && st2.getCapacity() == 4) {
			System.out.println("Yay 9");
		}

		if (st2.toString().equals("y:4\na:1\nb:2")
				&& st2.toStringDebug().equals("[0]: null\n[1]: y:4\n[2]: a:1\n[3]: b:2")) {
			System.out.println("Yay 10");
		}
		SymbolTable<String> st3 = new SymbolTable<>(2);
		st3.put("a", "a");
		st3.remove("a");

		if (st3.toString().equals("") && st3.toStringDebug().equals("[0]: null\n[1]: tombstone")) {
			st3.put("a", "a");

			if (st3.toString().equals("a:a") && st3.toStringDebug().equals("[0]: null\n[1]: a:a")
					&& st3.toStringDebug().equals("[0]: null\n[1]: a:a")) {
				System.out.println("Yay 11");
			}
		}
	}

	// --------------Provided methods below this line--------------
	// Add JavaDocs, but do not change the methods.

	/**
	 * @return string value of SymbolTable
	 */
	public String toString() {
		// THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < storage.length; i++) {
			if (storage[i] != null && !isTombstone(i)) {
				s.append(storage[i] + "\n");
			}
		}
		return s.toString().trim();
	}

	/**
	 * @return string value of SymbolTable with indication of tombstone
	 */
	public String toStringDebug() {
		// THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < storage.length; i++) {
			if (!isTombstone(i)) {
				s.append("[" + i + "]: " + storage[i] + "\n");
			} else {
				s.append("[" + i + "]: tombstone\n");
			}

		}
		return s.toString().trim();
	}
}