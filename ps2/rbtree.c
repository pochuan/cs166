#include "rbtree.h"
#include <stddef.h>
#include <stdlib.h>
#include <stdio.h>
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
    if (root == NULL) return true;
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
        //printf("Red node has child\n");
        return -1;
    }
    int currBlack = (isRed) ? 0 : 1;
    int lbHeight = blackHeight(left(node->left), isRed);
    int rbHeight = blackHeight(node->right, isRed);
    if ((lbHeight == -1) || (rbHeight == -1) || (lbHeight != rbHeight)) {
        //printf("Unequal black height\n");
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
 * This algorithm buids a red black tree by making a node from the
 * median elem in the list, and then recursively building the rest of 
 * the tree by calling the same function on the elems left and right of 
 * the median. The base cases are when the list of elems passed in are 
 * length 0, 1, or 2. Most of the nodes are black, except for when we reach
 * the base case where the list length is 2, and we set it to have one red 
 * child. This algorithm adds each elem in the list to the tree without 
 * needing do any tree rotations or fix the colors since it structurally
 * creates a balanced tree that satisfies the red black tree constraints.
 * Thus it runs in O(n) time.
 */

struct node* to_red_black_tree(int elems[], unsigned length) {
	return medianTree(elems, 0, length);
}

struct node *medianTree(int elems[], int start, int end) {
    //printf("median tree start: %d, end: %d\n", start, end);
    if (end-start == 0) return NULL;
    if (end-start == 1) return newNode(elems[start], NULL, NULL, true);
    if (end-start == 2) return newNode(elems[start+1], newNode(elems[start], NULL, NULL, false), NULL, true);
    if (end-start == 3) {
        struct node *newL = newNode(elems[start], NULL, NULL, false);
        struct node *newR = newNode(elems[start+2], NULL, NULL, false);
        return newNode(elems[start+1], newL, newR, true);
    }
    // median
    int m = start + (end-start)/2;
    //printf("median: %d\n", m);
    struct node* leftTree = medianTree(elems, start, m);
    struct node* rightTree = medianTree(elems, m+1, end);

    return(newNode(elems[m], leftTree, rightTree, true));
}

struct node *newNode(int key, struct node *left, struct node *right, bool isBlack) {
    struct node *newNode = (struct node*) malloc(sizeof(struct node));
    if (!isBlack) {
        left = (struct node*)(((uintptr_t)left) | 1);
    }
    newNode -> left = left;
    newNode -> right = right;
    return newNode;
}
