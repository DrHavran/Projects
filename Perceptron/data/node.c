#include "node.h"

void setupLogicVariables(Node *node) {
    if (node->category == 1) {
        node->xLogic = node->x;
        node->yLogic = node->y;
        node->b = 1;
    }else {
        node->xLogic = -node->x;
        node->yLogic = -node->y;
        node->b = -1;
    }
}