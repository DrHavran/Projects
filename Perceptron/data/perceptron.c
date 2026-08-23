#include "perceptron.h"

#include "node.h"
#include "data.h"
#include "raylib.h"

void initLinearPerceptron(LinearPerceptron *perceptron) {
    perceptron->w1 = 0;
    perceptron->w2 = 0;
    perceptron->b = 0;
}
void initQuadraticPerceptron(QuadraticPerceptron *perceptron) {
    perceptron->w1 = 0;
    perceptron->w2 = 0;
    perceptron->b = 0;
}

void updateLinearPerceptron(LinearPerceptron *perceptron, const Node *n) {
    perceptron->w1 = perceptron->w1 + n->xLogic;
    perceptron->w2 = perceptron->w2 + n->yLogic;
    perceptron->b = perceptron->b + n->b;
}

// GPT code to convert line points into vectors, too lazy to write it myself :)
Vector2Pair createLineFromPerceptron(const LinearPerceptron *perceptron) {
    Vector2Pair result;

    // Check if w2 is very close to zero (vertical line)
    if (perceptron->w2 > -1e-8 && perceptron->w2 < 1e-8) {
        double x_const = -perceptron->b / perceptron->w1;
        if (x_const < xMin) x_const = xMin;
        if (x_const > xMax) x_const = xMax;
        result.start.x = x_const * GetScreenWidth();
        result.start.y = (1.0 - yMin) * GetScreenHeight();
        result.end.x   = x_const * GetScreenWidth();
        result.end.y   = (1.0 - yMax) * GetScreenHeight();
        return result;
    }

    double y0 = -(perceptron->w1 * xMin + perceptron->b) / perceptron->w2;
    double y1 = -(perceptron->w1 * xMax + perceptron->b) / perceptron->w2;

    if (y0 < yMin) y0 = yMin;
    if (y0 > yMax) y0 = yMax;
    if (y1 < yMin) y1 = yMin;
    if (y1 > yMax) y1 = yMax;

    result.start.x = xMin * GetScreenWidth();
    result.start.y = (1.0 - y0) * GetScreenHeight();
    result.end.x   = xMax * GetScreenWidth();
    result.end.y   = (1.0 - y1) * GetScreenHeight();

    return result;
}