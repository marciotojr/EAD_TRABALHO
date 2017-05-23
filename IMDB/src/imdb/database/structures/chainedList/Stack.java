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
public class Stack<E> {

    Node head;
    int size;

    public Stack() {
        head = null;
        size = 0;
    }

    public void push(E entry) {
        Node newNode = new Node(entry);
        newNode.setNext(head);
        head = newNode;
        size++;
    }

    public E pop() {
        if(head==null)return null;
        Node returnValue = head;
        head = head.getNext();
        size--;
        return (E)returnValue.getValue();
    }

    public int getSize() {
        return size;
    }
}
