#ifndef PERCEPTRON_PERCEPTRON_H
#define PERCEPTRON_PERCEPTRON_H

#include "node.h"
#include "raylib.h"

typedef struct LinearPerceptron {
    double w1, w2, b;
} LinearPerceptron;

typedef struct QuadraticPerceptron {
    double w1, w2, b;
} QuadraticPerceptron;

typedef struct Vector2Pair {
    Vector2 start;
    Vector2 end;
} Vector2Pair;

void initLinearPerceptron(LinearPerceptron *perceptron);
void initQuadraticPerceptron(QuadraticPerceptron *perceptron);
void updateLinearPerceptron(LinearPerceptron *perceptron, const Node *n);
Vector2Pair createLineFromPerceptron(const LinearPerceptron *perceptron);

#endif //PERCEPTRON_PERCEPTRON_H