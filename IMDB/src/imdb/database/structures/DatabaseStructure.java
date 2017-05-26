/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imdb.database.structures;

import imdb.database.Table;
import imdb.database.structures.auxiliarTree.JoinTree;
import imdb.database.structures.chainedList.Stack;

/**
 *
 * @author Marcio Júnior
 */
public abstract class DatabaseStructure <E> {
    protected Table table;
    protected int size;

    public DatabaseStructure(Table table) {
        this.table = table;
        this.size = 0;
    }
    
    
    
    public abstract boolean put(E entry);
    public abstract boolean remove(Comparable key);
    public abstract boolean remove(E entry);
    public abstract E search(Comparable key);
    public void setTable(Table table){
        this.table=table;
    }
    public int getSize(){
        return size;
    }
    public abstract int count(int field,String value);
    
    public abstract E[] getValuesArray();
    public abstract Stack<E> getValuesStack();
    public abstract JoinTree<E> buildJoinTree(int leftSize);
    
}
