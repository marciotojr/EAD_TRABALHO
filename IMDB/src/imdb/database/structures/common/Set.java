/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imdb.database.structures.common;

/**
 *
 * @author Marcio Júnior
 */
public class Set<E> {

    protected Comparable key;
    protected E value;

    public Set(Comparable key, E value) {
        this.key = key;
        this.value = value;
    }
    
    public void setKey(Comparable key) {
        this.key = key;
    }

    public Comparable getKey() {
        return key;
    }

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }
    
    
}
