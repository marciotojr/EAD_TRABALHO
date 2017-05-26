/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imdb.database.structures.chainedList;

/**
 *
 * @author Marcio Júnior
 */
public class SingleLinkedNode<E>{

    E value;
    SingleLinkedNode next;
    
    public SingleLinkedNode(E value) {
        this.value = value;
        this.next = null;
    }

    public E getValue() {
        return value;
    }

    public SingleLinkedNode getNext() {
        return next;
    }

    public void setNext(SingleLinkedNode next) {
        this.next = next;
    }

    public void setValue(E value) {
        this.value = value;
    }
}
