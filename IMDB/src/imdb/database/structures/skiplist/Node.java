/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imdb.database.structures.skiplist;

import imdb.database.Table;
import imdb.database.structures.common.Entry;
import java.util.Random;

/**
 * Classe dos nós da skiplist
 * @author Marcio Júnior
 */
public class Node implements Comparable {

    /**
     * Registro do nó
     */
    private Entry entry;
    /**
     * Lista de nós seguintes
     */
    private Node[] nodes;
    /**
     * Chave de identificação única do registro no nó e do nó
     */
    private String key;

    /**
     * Cria um nó para inserção com sua nova chave baseada na tabela onde será inserido
     * @param newEntry campos do registro
     * @param table tabela onde o nó deve ser inserido
     */
    Node(Entry newEntry, Table table) {
        this.entry = newEntry;
        double t = new Random().nextDouble();
        int i = 0;
        double j = 1;
        while (i <= Math.floor(Math.log(table.getStructure().getSize()) / 0.6931471805599453) && i < SkipList.getLimitHeight() && t < 1 / j) { //0.6931471805599453 é log de 2 na base 10, essa operação dá o resultado de log de size na base 2
            j *= 2;
            i++;
        }
        this.nodes = new Node[i];
        for (int k = 0; k < nodes.length; k++) {
            this.nodes[k] = null;
        }
        this.key = table.getEntryKey(entry.getData());
    }

    public void addLevels(int size) {
        Node[] newNode = new Node[size];
        for (int k = 0; k < nodes.length; k++) {
            if (k < this.getNodes().length) {
                newNode[k] = this.getNodes()[k];
            }
        }
        this.nodes = newNode;
    }

    void setEntry(Entry entry) {
        this.entry = entry;
    }
    
    public String getKey() {
        return key;
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

    @Override
    public int compareTo(Object o) {
        if (o == null) {
            return 0;
        }
        if (o instanceof Node) {
            String comparable = (String) ((Node) o).getKey();
            int compare= this.getKey().compareTo(comparable);
            return compare;
        }
        return 0;
    }
    
    @Override
    public String toString(){
        return this.getKey();
    }

    public void setKey(String key) {
        this.key = key;
    }

    
}
