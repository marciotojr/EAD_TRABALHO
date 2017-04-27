/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imdb.database.structures.skiplist;

import imdb.database.structures.common.Entry;
import java.util.Random;

/**
 *
 * @author Marcio Júnior
 */
public class Node {

    Entry entry;
    Node[] nodes;

    public Node(Entry newEntry, int size) {
        this.entry = newEntry;
        double t = new Random().nextDouble();
        int i = 1;
        double j = 1;
        while (i < Math.floor(Math.log(size) / 0.6931471805599453) && t <= 1 / j) { //0.6931471805599453 é log de 2 na base 10, essa operação dá o resultado de log de size na base 2
            j *= 2;
            i++;
        }
        this.nodes = new Node[i];
        for (int k = 0; k < nodes.length; k++) {
            this.nodes[k] = this;
        }
    }
    
    public void addMaximumReferences(int size){
        Node[] newNodes = new Node[(int) Math.floor(Math.log(size) / 0.6931471805599453)];
        for (int k = 0; k < nodes.length; k++) {
            if (k < this.getNodes().length) {
                newNodes[k] = this.getNodes()[k];
            } else {
                newNodes[k] = this;
            }
        }
        this.nodes=newNodes;
    }

    public String getKey() {
        return entry.getKey();
    }

    public int size() {
        return this.nodes.length;
    }

    public Entry getEntry() {
        return entry;
    }

    public Node[] getNodes() {
        return nodes;
    }

}
