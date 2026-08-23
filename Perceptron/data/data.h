#ifndef DATA_H
#define DATA_H

#include "arrayList.h"

extern double xMin, xMax, yMin, yMax;

ArrayList loadData(const char *fileName);
void updateMinMax(double x, double y);

#endif