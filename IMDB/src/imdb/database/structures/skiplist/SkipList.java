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
public class SkipList extends Structure{

    Node head;
    int size;
    int currentHeight;

    public SkipList() {
        size=0;
        currentHeight=0;
        head=null;
    }
    
    private void addEntry(Entry entry){
        size++;
        if(size == 1){
            this.head = new Node(entry, size);
        }else{
            Node[] substitutes =  new Node[head.nodes.length];
            Node newNode = new Node(entry, size);
            Node currentNode = head;
            for(int i = head.getNodes().length-1; i > 0; i--){
                if(currentNode.getKey().compareTo(newNode.getKey())<0){
                    
                }
            }
        }
    }

    private void setHead(Node node) {
        node.addMaximumReferences(size);
    }

    public Node getHead() {
        return head;
    }

    public int getSize() {
        return size;
    }

    public int getCurrentHeight() {
        return currentHeight;
    }

    public int getMaxHeight() {
        return (int) Math.floor(Math.log(size) / 0.6931471805599453);
    }

    @Override
    public boolean put(String[] entry) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean remove(String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String[] search(String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
