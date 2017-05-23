/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imdb.database.structures.binarySearchTree;

import imdb.database.Table;
import imdb.database.structures.Structure;

/**
 *
 * @author Marcio Júnior
 */
public class BinarySearchTree<E> extends Structure{

    protected BinarySearchTreeNode root = null;
    
    public BinarySearchTree(Table table) {
        super(table);
    }

    @Override
    public boolean put(Object entry) {
        if(root==null){
            root = new BinarySearchTreeNode(table.getEntryKey(entry), entry);
            return true;
        }else{
            BinarySearchTreeNode newNode = new BinarySearchTreeNode(table.getEntryKey(entry), entry);
            return root.put(newNode);
        }
    }

    @Override
    public boolean remove(Comparable key) {
        return root.remove(key);
    }

    @Override
    public boolean remove(Object entry) {
        return root.remove(table.getEntryKey(entry));
    }

    @Override
    public Object search(Comparable key) {
        return root.search(key);
    }

    
}
