#include "rbtree.h"
#include <stddef.h>
#include <stdlib.h>
#include <stdbool.h>
#include <stdint.h>

// Returns if a node is red
bool red(struct node* someNode) {
    return (((uintptr_t)someNode->left) & 1);
}

// Masks away the color bit
struct node* left(struct node* leftPointer) {
    return (struct node*)(((uintptr_t)leftPointer) & (~1));
}


/**
 * Function: is_red_black_tree(struct node* root);
 * --------------------------------------------------------------------------
 * Given a pointer to the root of a tree, returns whether that tree is a 
 * red/black tree. This function can assume the following:
 *
 * 1. The pointer provided either points to a valid address or to NULL.
 * 2. The tree structure pointed at is indeed a tree; there aren't any
 *    directed or undirected cycles and all internal pointers are valid
 *    (though the left pointers may need to be tweaked a bit to get to
 *    valid data).
 *
 * This function runs in time O(n) since it performs one check for the root 
 * is black, which is O(1). Then it does depth first search to check that each 
 * root leaf path has the same number of black nodes and that red nodes don't
 * have red children. The Depth first search only processes each of the n nodes 
 * once, doing constant work each time. Thus, the runtime is O(n). 
 */
bool is_red_black_tree(struct node* root) {
    if (red(root)) return false;
    return (blackHeight(root, false) != -1);
}

// Returns the black height of a red black tree if it is valid
// Returns -1 if root leaf path has differend number of black nodes or if a red
// node has red children.
int blackHeight(struct node* node, bool parentRed) {
    if (node == NULL) return 0;
    bool isRed = red(node);
    if (isRed && parentRed) {
        return -1;
    }
    int currBlack = (isRed) ? 0 : 1;
    int lbHeight = blackHeight(left(node->left), isRed);
    int rbHeight = blackHeight(node->right, isRed);
    if ((lbHeight == -1) || (rbHeight == -1) || (lbHeight != rbHeight)) {
        return -1;
    }
    return lbHeight + currBlack;
}

/**
 * Function: to_red_black_tree(int elems[], unsigned length);
 * -------------------------------------------------------------------------
 * Given as input a sorted array of elements, returns a new red/black tree
 * containing the elements of that array.
 *
 * TODO: Edit this comment to describe why this function runs in time O(n).
 */
struct node* to_red_black_tree(int elems[], unsigned length) {
	/* TODO: Implement this! */
	return NULL;
}
