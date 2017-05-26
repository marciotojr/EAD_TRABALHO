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
public class Queue<E> {

    DoubleLinkedNode<E> head, tail;
    int size;

    public Queue() {
        head = null;
        tail = null;
        size = 0;
    }

    public void push(E value) {
        if (value != null) {
            DoubleLinkedNode<E> node = new DoubleLinkedNode<>(value);
            if (head == null && tail == null) {
                head = tail = node;
            } else {
                tail.setNext(node);
                tail.getNext().setPrevious(tail);
                tail = tail.getNext();
            }
            size++;
        }
    }

    public E pop() {
        if (head == null) {
            return null;
        }
        E value = head.value;
        head = head.getNext();
        if (head != null) {
            head.setPrevious(null);
        } else {
            tail = null;
        }
        size--;
        return value;
    }

    public int getSize() {
        return size;
    }
}
