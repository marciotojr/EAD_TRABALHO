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
class Node<E>{

    E value;
    Node next;
    
    public Node(E value) {
        this.value = value;
        this.next = null;
    }

    public E getValue() {
        return value;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public void setValue(E value) {
        this.value = value;
    }
}
