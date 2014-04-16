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
 * TODO: Edit this comment to describe why this function runs in time O(n).
 */
bool is_red_black_tree(struct node* root) {
	/* TODO: Implement this! */
	return false;
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
