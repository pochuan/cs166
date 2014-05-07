/**
 * An implementation of a BST backed by a splay tree.
 */
public class SplayTree implements BST {
	SplayTreeNode root;

	/**
	 * Constructs a new tree from the specified array of weights. Since splay
	 * trees don't care about access probabilities, you should only need
	 * to read the length of the weights array and not the weights themselves.
	 * This tree should store the values 0, 1, 2, ..., n - 1, where n is the length
	 * of the input array.
	 *
	 * @param The weights on the elements.
	 */
	public SplayTree(double[] keys) {
		if (keys.length == 0) {
			root = null;
			return;
		}
		root = new SplayTreeNode(0);
		SplayTreeNode prev = root;
		for (int i = 1; i < keys.length; i++) {
			SplayTreeNode cur = new SplayTreeNode(i);
			prev.right = cur;
			cur.parent = prev;
			prev = cur;
		}
	}
	
	private SplayTreeNode rotateRight(SplayTreeNode a, SplayTreeNode b) {
		assert (a != null && b != null) : "Invalid rotate right: null";
		SplayTreeNode grandparent = b.parent;
		// Preserve the link to the rest of the tree
		if (grandparent != null) {
			if (grandparent.left == b) {
				grandparent.left = a;
			}
			else if (grandparent.right == b) {
				grandparent.right = a;
			}
			else {
				//error!  Assert?
			}
		}
		else {
			root = a;
		}
		a.parent = grandparent;

		// make b the parent of the middle subtree
		b.left = a.right;
		if (b.left != null) {
			b.left.parent = b;
		}

		// make a the parent of b
		a.right = b;
		b.parent = a;
		return grandparent;
	}

	private SplayTreeNode rotateLeft(SplayTreeNode a, SplayTreeNode b) {
		assert (a != null && b != null) : "Invalid rotate left: null";
		SplayTreeNode grandparent = a.parent;
		// Preserve the link to the rest of the tree
		if (grandparent != null) {
			if (grandparent.left == a) {
				grandparent.left = b;
			}
			else if (grandparent.right == a) {
				grandparent.right = b;
			}
			else {
				//error!  Assert?
			}
		}
		else {
			root = b;
		}
		b.parent = grandparent;

		// make a the parent of the middle subtree
		a.right = b.left;
		if (a.right != null) {
			a.right.parent = a;
		}

		// make b the parent of a
		b.left = a;
		a.parent = b;
		return grandparent;
	}

	// Returns true if this was actually a zigzig case, and does the zig zig.
	// False otherwise
	private boolean doZigZig(SplayTreeNode cur) {
		//System.out.println("Doing Zig Zig");
		SplayTreeNode parent = cur.parent;
		if (parent == null) {
			return false;
		}
		SplayTreeNode grandparent = parent.parent;
		if (grandparent == null) {
			return false;
		}
		if ((cur == parent.left) && (parent == grandparent.left)) {
			//System.out.println("zig zig right");
			//System.out.println("parent was " + parent.key + "; grandparent was " + grandparent.key + "; cur was " + cur.key);
			rotateRight(parent, grandparent);
			rotateRight(cur, parent);
			return true;
		} 
		if ((cur == parent.right) && (parent == grandparent.right)) {
			//System.out.println("zig zig left");
			rotateLeft(grandparent, parent);
			rotateLeft(parent, cur);
			return true;
		}
		return false;
	}

	private boolean doZigZag(SplayTreeNode cur) {
		//System.out.println("Doing Zig Zag");
		SplayTreeNode parent = cur.parent;
		if (parent == null) {
			return false;
		}
		SplayTreeNode grandparent = parent.parent;
		if (grandparent == null) {
			return false;
		}
		if ((cur == parent.left) && (parent == grandparent.right)) {
			rotateRight(cur, parent);
			rotateLeft(grandparent, cur);
			return true;
		}
		if ((cur == parent.right) && (parent == grandparent.left)) {
			rotateLeft(parent, cur);
			rotateRight(cur, grandparent);
			return true;
		}
		return false;
	}

	private boolean doZig(SplayTreeNode cur) {
		//System.out.println("Doing Zig");
		//return (cur.parent == root);
		if (cur.parent != root) {
			return false;
		}
		/*// do zig
		if (cur == root.left) {
			cur.parent = null;
			root.parent = cur;
			root.left = cur.right;
			cur.right = root;
			root = cur;
		}
		if (cur == root.right) {
			cur.parent = null;
			root.parent = cur;
			root.right = cur.left;
			cur.left = root;
			root = cur;
		}*/
		if (root.left == cur) {
			rotateRight(cur, root);
		}
		else if (root.right == cur) {
			rotateLeft(root, cur);
		}


		return true;
	}
	/**
	 * Returns whether the specified key is in the BST.
	 *
	 * @param key The key to test.
	 * @return Whether it's in the BST.
	 */
	public boolean contains(int key) {
		//System.out.println("Searching for " + key);
		// Search for the node
		SplayTreeNode cur = root;
		while (cur != null) {
			if (cur.key == key) {
				//System.out.println("Found key " + key);
				break;
			}
			if (cur.key > key) {
				//System.out.println("Current node's key " + cur.key  + " is bigger than target key " + key);
				cur = cur.left;
			}
			else {
				//System.out.println("Current node's key " + cur.key  + " is smaller than target key " + key);
				cur = cur.right;
			}
		}

		// We did not find the key
		if (cur == null) {
			return false;
		}

		//System.out.println("Found node with key " + key);
		// splay up
		while (root != cur) {
			if (doZig(cur)) {
				//System.out.println("Zig was successful");
			}
			else if (doZigZag(cur)) {
				//System.out.println("ZigZag was successful");
			}
			else if (doZigZig(cur)) {
				//System.out.println("ZigZig was successful");
			}
			else {
				//System.out.println("Nothing worked on this round");
				return false;
			}
		}

		//System.out.println("Tree now looks like:\n\t" + root.key);
		/*if (root.left != null && root.right != null) {
			System.out.println("" + root.left.key + "\t" + root.right.key);
		}
		if (root.left == null && root.right != null) {
			System.out.println("Tree goes only to the right");
		}
		if (root.right == null && root.left != null) {
			System.out.println("Tree goes only to the left");
		}*/
		return true;
	}
}
