#include "../data/data.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "node.h"

double xMin, xMax, yMin, yMax;

ArrayList loadData(const char *fileName) {
    ArrayList list;
    al_init(&list);

    char path[256];
    snprintf(path, sizeof(path), "../import_data/%s", fileName);
    FILE *f = fopen(path, "r");

    char line[1024];
    fgets(line, sizeof(line), f);

    while (fgets(line, sizeof(line), f)) {
        const char *token = strtok(line, ",");
        const double x = atof(token);

        token = strtok(NULL, ",");
        const double y = atof(token);

        token = strtok(NULL, ",");
        const int category = atoi(token);

        Node *n = malloc(sizeof(Node));

        n->category = category;
        n->x = x;
        n->y = y;

        setupLogicVariables(n);

        updateMinMax(x, y);
        al_add(&list, n);
    }
    fclose(f);
    return list;
}

void updateMinMax(const double x, const double y) {
    if (x < xMin) xMin = x;
    if (x > xMax) xMax = x;
    if (y < yMin) yMin = y;
    if (y > yMax) yMax = y;
}