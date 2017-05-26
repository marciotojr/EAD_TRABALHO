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
class DoubleLinkedNode<E>{

    E value;
    DoubleLinkedNode next,previous;
    
    public DoubleLinkedNode(E value) {
        this.value = value;
        this.next = null;
        this.previous = null;
    }

    public E getValue() {
        return value;
    }

    public DoubleLinkedNode getNext() {
        return next;
    }

    public void setNext(DoubleLinkedNode next) {
        this.next = next;
    }

    public void setValue(E value) {
        this.value = value;
    }

    public DoubleLinkedNode getPrevious() {
        return previous;
    }

    public void setPrevious(DoubleLinkedNode previous) {
        this.previous = previous;
    }
    
    
}
