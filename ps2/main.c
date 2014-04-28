#include <stdio.h>
#include "rbtree.h"

#define MAX_TEST_SIZE 100
int elems[MAX_TEST_SIZE];

void test_red_black(int size) {
    printf("Making a tree of size %i...\n", size);
    struct node* rbroot = to_red_black_tree(elems, size);
    printf("Checking if the tree was well-formed\n");
    if (is_red_black_tree(rbroot)) {
        printf("PASS\n");
    }
    else {
        printf("\tFAIL\n");
    }
}

int main(int argc, char *argv[]) {
    printf("Red black trees\n");
    for (int i = 0; i < MAX_TEST_SIZE; i++) {
        elems[i] = i;
    }

    for (int i = 0; i < MAX_TEST_SIZE; i++) {
        test_red_black(i);
    }
    
    return 0;
}
