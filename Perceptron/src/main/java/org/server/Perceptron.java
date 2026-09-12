package org.server;

import org.server.Models.Node;

import java.util.ArrayList;
import java.util.Collections;

public class Perceptron {
    protected ArrayList<Double> weights;

    public void run(ArrayList<Node> nodes) {
        int size = nodes.getFirst().getValues().size();
        this.weights = new ArrayList<>(Collections.nCopies(size, 0.0));

        double learningRate = GlobalSettings.learningRate;
        boolean allCorrect;
        int epoch = 0;

        do {
            allCorrect = true;

            for (Node node : nodes) {
                double value = 0;
                for (int i = 0; i < size; i++) {
                    value += node.getValues().get(i) * weights.get(i);
                }

                if (value <= 0) {
                    for (int i = 0; i < size; i++) {
                        weights.set(i, weights.get(i) + learningRate * node.getValues().get(i));
                    }
                    allCorrect = false;
                }
            }
            epoch++;
        } while (!allCorrect && epoch < GlobalSettings.epochs);

        System.out.println("loop result: " + allCorrect + ", epochs: " + epoch);
        System.out.println("weights:   " + weights);
    }

    public ArrayList<Double> getWeights() {
        return weights;
    }
}
































