/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imdb.database.structures.chainedList;

import imdb.database.Table;
import imdb.database.structures.Structure;
import imdb.database.structures.chainedList.Node;
import imdb.database.structures.common.Set;

/**
 *
 * @author Marcio Júnior
 */
public class EntryChainedList<E> extends Structure {

    Node<Set> head;

    public EntryChainedList(Table table) {
        super(table);
    }

    @Override
    public boolean remove(Comparable key) {
        Node<Set> current = head;
        Node<Set> predecessor = null;
        while (current != null) {
            if (key.compareTo(current.getValue().getKey()) == 0) {
                if (current == head) {
                    head = current.getNext();
                } else {
                    predecessor.setNext(current.getNext());
                }
                current.setNext(null);
                size--;
                return true;
            }
            predecessor = current;
            current = current.getNext();
        }
        return false;
    }

    @Override
    public E search(Comparable key) {
        Node<Set> current = head;
        while (current != null) {
            if (key.compareTo(current.getValue().getKey()) == 0) {
                return (E) current.getValue();
            }
            current = current.getNext();
        }
        return null;
    }

    @Override
    public boolean put(Object entry) {
        Node<Set> newNode = new Node(new Set(table.getEntryKey(entry), entry));
        if (head == null) {
            head = newNode;
            size++;
            return true;
        }
        Node<Set> last = head;
        for (last = head; null != last.getNext(); last = last.getNext());
        last.setNext(newNode);
        /*
        newNode.setNext(head);
        head = newNode;*/
        size++;
        return true;
    }

    @Override
    public boolean remove(Object entry) {
        Node<Set> current = head;
        Node<Set> predecessor = null;
        while (current != null) {
            if (entry.equals(current.getValue())) {
                if (current == head) {
                    head = current.getNext();
                } else {
                    predecessor.setNext(current.getNext());
                }
                current.setNext(null);
                return true;
            }
            predecessor = current;
            current = current.getNext();
        }
        return false;
    }

}
