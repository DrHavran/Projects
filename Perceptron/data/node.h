#ifndef PERCEPTRON_NODE_H
#define PERCEPTRON_NODE_H

typedef struct node {
    //  Loaded data
    double x, y;
    int category;

    //  Data for computing
    double yLogic, xLogic;
    int b;
    int group;
} Node;

void setupLogicVariables(Node *node);

#endif //PERCEPTRON_NODE_H