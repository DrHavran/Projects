#ifndef PERCEPTOR_DRAW_H
#define PERCEPTOR_DRAW_H

#include "../data/arrayList.h"
#include "../data/perceptron.h"
#include "../data/node.h"

void drawWindow();
void drawAll(ArrayList list, LinearPerceptron perceptron);
void drawNode(Node node);
void drawLinearPerceptron(LinearPerceptron perceptron);

#endif //PERCEPTOR_DRAW_H