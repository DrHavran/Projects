#include "draw.h"
#include "raylib.h"
#include "../data/data.h"

void drawWindow() {
    const int screenWidth = 800;
    const int screenHeight = 450;

    InitWindow(screenWidth, screenHeight, "Perceptron");

    const Image icon = LoadImage("icon.png");
    SetWindowIcon(icon);
    UnloadImage(icon);

    SetTargetFPS(60);
}
void drawAll(const ArrayList list, const LinearPerceptron perceptron) {
    ClearBackground(RAYWHITE);
    BeginDrawing();
    for (int i = 0; i < list.size; i++) {
        const struct node *n = list.data[i];
        drawNode(*n);
    }
    drawLinearPerceptron(perceptron);
    EndDrawing();
}
void drawNode(const Node node) {
    Color color;
    if (node.category == 1) {
        color = RED;
    }else {
        color = BLUE;
    }
    DrawCircle(
        node.x * GetScreenWidth(),
        (1 - node.y) * GetScreenHeight(),
        3, color);
}
void drawLinearPerceptron(const LinearPerceptron perceptron) {
    const Vector2Pair vector = createLineFromPerceptron(&perceptron);
    DrawLineV(vector.start, vector.end, BLACK);
}