/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imdb.database.structures.skiplist;

import imdb.database.Table;
import imdb.database.structures.common.Set;
import java.util.Random;

/**
 * Classe dos nós da skiplist
 *
 * @author Marcio Júnior
 */
public class SkipListNode extends Set implements Comparable {

    /**
     * Lista de nós seguintes
     */
    private SkipListNode[] nodes;

    /**
     * Cria um nó para inserção com sua nova chave baseada na tabela onde será
     * inserido
     *
     * @param value campos do registro
     * @param table tabela onde o nó deve ser inserido
     */
    SkipListNode(Comparable key, Object value, Table table) {
        super(key,value);
        double t = new Random().nextDouble();
        int i = 1;
        double j = 1;
        int size = table.getStructure().getSize();
        int max = (int) Math.floor(Math.log(table.getStructure().getSize()) / 0.6931471805599453);
        while (i <= max && i < SkipList.getLimitHeight() && t < 1 / j) { //0.6931471805599453 é log de 2 na base 10, essa operação dá o resultado de log de size na base 2
            j *= 2;
            i++;
        }
        this.nodes = new SkipListNode[i];
        for (int k = 0; k < nodes.length; k++) {
            this.nodes[k] = null;
        }

    }

    public void addLevels(int size) {
        SkipListNode[] newNode = new SkipListNode[size];
        for (int k = 0; k < nodes.length; k++) {
            if (k < this.getNodes().length) {
                newNode[k] = this.getNodes()[k];
            }
        }
        this.nodes = newNode;
    }

    public Comparable getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }
 
    public int size() {
        return this.nodes.length;
    }

    public SkipListNode[] getNodes() {
        return nodes;
    }

    @Override
    public int compareTo(Object o) {
        if (o == null) {
            return 0;
        }
        if (o instanceof SkipListNode) {
            Comparable key = ((SkipListNode) o).getKey();
            int compare = this.getKey().compareTo(key);
            return compare;
        }
        return 0;
    }

    @Override
    public String toString() {
        return (String)this.getKey();
    }

    public void setKey(String key) {
        this.key = key;
    }

}
