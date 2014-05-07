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
		// TODO: Implement this!
		// Can we just make it be a linked list with one end as the root, or is that fail?
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
		a.parent = grandparent;

		// make b the parent of the middle subtree
		b.left = a.right;
		if (b.left != null) {
			b.left.parent = b;
		}

		// make a the parent of b
		a.left = b;
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
	
	private boolean isZigZig(SplayTreeNode cur) {
		SplayTreeNode parent = cur.parent;
		if (parent == null) {
			return false;
		}
		SplayTreeNode grandparent = parent.parent;
		if (grandparent == null) {
			return false;
		}
		if (((cur == parent.left) && (parent == grandparent.left)) ||
			((cur == parent.right) && (parent == grandparent.right))) {
			return true;
		}
		return false;
	}

	private boolean isZigZag(SplayTreeNode cur) {
		SplayTreeNode parent = cur.parent;
		if (parent == null) {
			return false;
		}
		SplayTreeNode grandparent = parent.parent;
		if (grandparent == null) {
			return false;
		}
		if (((cur == parent.left) && (parent == grandparent.right)) ||
			((cur == parent.right) && (parent == grandparent.left))) {
			return true;
		}
		return false;
	}

	private boolean doZig(SplayTreeNode cur) {
		//return (cur.parent == root);
		if (cur.parent != root) {
			return false;
		}
		// do zig
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
		// Search for the node
		SplayTreeNode cur = root;
		while (cur != null) {
			if (cur.key == key) {
				break;
			}
			if (cur.key > key) {
				cur = cur.left;
			}
			else {
				cur = cur.right;
			}
		}

		// We did not find the key
		if (cur == null) {
			return false;
		}

		// splay up

		return false;
	}
}
