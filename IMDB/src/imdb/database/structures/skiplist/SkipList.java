/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imdb.database.structures.skiplist;

import imdb.database.structures.Structure;
import imdb.database.structures.common.Entry;

/**
 *
 * @author Marcio Júnior
 */
public class SkipList extends Structure {

    private Node head;
    private int size;
    private static final int maxHeight = 50;

    public SkipList() {
        size = 0;
        head = null;
        table = null;
    }

    private void put(Entry newEntry, String key) {
        Node newNode = null;
        if (size == 0) {
            newNode = new Node(newEntry, size, this.table);
            this.head = newNode;
            size++;
            return;
        } else if (head.getKey().compareTo(key) > 0) { //se nova entrada fica no inicio, substitui-se o iniício, e reinsere o antigo início, size não é atualizado, pois a segunda chamada do método atualiza size
            Entry reinsertEntry = head.getEntry();
            head.setEntry(newEntry);
            head.setKey(key);
            newNode = new Node(reinsertEntry, this.getSize(), this.table);
        }
        if (newNode == null) {
            newNode = new Node(newEntry, this.getSize(), this.table);
        }
        Node[] substitutes = new Node[newNode.getNodes().length];
        if (newNode.getNodes().length > head.getNodes().length) {
            head.addLevels(newNode.getNodes().length);
        }
        Node currentNode = head;
        for (int i = head.getNodes().length - 1; i >= 0; i--) {
            while (currentNode.getNodes()[i] != null && currentNode.getNodes()[i].getKey().compareTo(key) <= 0) {
                currentNode = currentNode.getNodes()[i];
            }
            if (i < substitutes.length) {
                substitutes[i] = currentNode;
            }
        }
        if (substitutes[0].compareTo(newNode) == 0) {
            return;
        }
        for (int i = 0; i < substitutes.length; i++) {
            newNode.getNodes()[i] = substitutes[i].getNodes()[i];
            substitutes[i].getNodes()[i] = newNode;
        }
        size++;

    }

    public Node getHead() {
        return head;
    }

    public int getSize() {
        return size;
    }

    public int getMaxHeight() {
        return (int) Math.floor(Math.log(size) / 0.6931471805599453);
    }

    @Override
    public boolean put(String[] entry) {
        String key = table.getEntryKey(entry);
        put(new Entry(entry), key);
        return true;
    }

    @Override
    public boolean remove(String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Entry search(String key) {

        Node currentNode = head;
        int i = currentNode.getNodes().length - 1;
        while (currentNode != null && i >= 0) {
            if (currentNode.getNodes()[i] != null && currentNode.getNodes()[i].getKey().compareTo(key) <= 0) {
                currentNode = currentNode.getNodes()[i];
            } else if (currentNode.getKey().equals(key)) {
                return currentNode.getEntry();
            } else {
                i--;
            }
        }
        if (currentNode.getKey().compareTo(key) == 0) {
            return currentNode.getEntry();
        }
        return null;
    }
    
    public static int getLimitHeight(){
        return maxHeight;
    }
}
