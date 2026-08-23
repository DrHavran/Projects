#include "arrayList.h"
#include <stdio.h>
#include <stdlib.h>

void al_init(ArrayList *list) {
    list->data = NULL;
    list->size = 0;
}

void al_add(ArrayList *list, void *data) {
    void **new_data = realloc(list->data, (list->size + 1) * sizeof(void*));
    list->data = new_data;
    list->data[list->size] = data;
    list->size++;
}