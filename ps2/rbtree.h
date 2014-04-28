#include <stddef.h>
#include <stdlib.h>
#include <stdbool.h>
#include <stdint.h>
/**************************************************************************
 * File: rbtree.h
 *
 * A header file defining a red/black tree type and exporting several
 * useful functions for red/black trees.
 */
#ifndef Rbtree_Included
#define Rbtree_Included

/**
 * Type: struct rbtree
 *
 * A type representing a node in a red/black tree whose keys are integers.
 * In order to save space, the color of the node has been packed into the
 * low-order bit of the left child pointer. If this bit is 1, the node is
 * red. Otherwise, it is back. This means that to access the bit that
 * encodes whether the node is red or black, you can write
 *
 *    ((uintptr_t)node->left) & 1
 *
 * The uintptr_t type, defined in <stdint.h>, is an unsigned integer type
 * that is guaranteed to be at least as large as a pointer.
 *
 * To follow the left pointer, you will need to first mask out this bit.
 * Otherwise, you will end up at the wrong address.
 */
struct node {
  struct node* left;
  struct node* right;
  int key;
};

// Returns if a node is red
bool red(struct node* someNode); 

// Masks away the color bit
struct node* left(struct node* leftPointer);

int blackHeight(struct node *node, bool parentRed);

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
 * For full credit, this function must run in time O(n).
 *
 * (This method returns a value of type _Bool. This is the built-in name
 * for the C bool type. If you include the <stdbool.h> header in your
 * source files, then you can use the name bool along with the values
 * true and false rather than _True and _False.)
 */
_Bool is_red_black_tree(struct node* root);

/**
 * Function: to_red_black_tree(int elems[], unsigned length);
 * -------------------------------------------------------------------------
 * Given as input a sorted array of elements, returns a new red/black tree
 * containing the elements of that array.
 *
 * For full credit, this function must run in time O(n).
 */
struct node* to_red_black_tree(int elems[], unsigned length);

struct node *medianTree(int elems[], int start, int end);

struct node *newNode(int key, struct node *left, struct node *right, bool isBlack);

#endif
