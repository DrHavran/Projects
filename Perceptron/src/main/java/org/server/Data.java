package org.server;

import org.server.Models.Entry;
import org.server.Models.Node;
import org.server.Models.Shape;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Data {
    private final ArrayList<Entry> entries;
    private final ArrayList<Node> nodes;

    public Data() {
        this.entries = new ArrayList<>();
        this.nodes = new ArrayList<>();
    }

    public void loadData(String file, Shape shape){
        try {
            Scanner sc = new Scanner(new File(GlobalSettings.importFolder + file + ".csv"));
            // Skip header
            sc.nextLine();

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] parts = line.split(",");

                double x = Double.parseDouble(parts[0]);
                double y = Double.parseDouble(parts[1]);
                int classification = Integer.parseInt(parts[2]);

                Entry entry = new Entry(x, y, classification);
                entries.add(entry);

                double b = (entry.classification() == 0) ? -1 : 1;
                nodes.add(
                    new Node(
                            entry.x(), entry.y(), b, shape
                    )
                );
            }
            sc.close();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Entry> getEntries() {
        return entries;
    }
    public ArrayList<Node> getNodes() {
        return nodes;
    }
}
