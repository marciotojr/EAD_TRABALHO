/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imdb.database.structures;

import imdb.database.Table;

/**
 *
 * @author Marcio Júnior
 */
public abstract class Structure <E> {
    protected Table table;
    protected int size;

    public Structure(Table table) {
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
    
}
