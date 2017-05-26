/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imdb.database.structures.chainedList;

import imdb.database.Table;
import imdb.database.structures.DatabaseStructure;
import imdb.database.structures.auxiliarTree.JoinTree;
import imdb.database.structures.common.Entry;
import imdb.database.structures.common.Set;

/**
 *
 * @author Marcio Júnior
 */
public class ChainedList<E> extends DatabaseStructure {

    SingleLinkedNode<Set<E>> head;

    public ChainedList(Table table) {
        super(table);
    }

    @Override
    public boolean remove(Comparable key) {
        SingleLinkedNode<Set<E>> current = head;
        SingleLinkedNode<Set> predecessor = null;
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
            predecessor = (SingleLinkedNode)current;
            current = current.getNext();
        }
        return false;
    }

    @Override
    public E search(Comparable key) {
        SingleLinkedNode<Set<E>> current = head;
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
        SingleLinkedNode<Set<E>> newNode = new SingleLinkedNode(new Set(table.getEntryKey(entry), entry));
        if (head == null) {
            head = (SingleLinkedNode)newNode;
            size++;
            return true;
        }/*
        Node<Set<E>> last = head;
        for (last = head; null != last.getNext(); last = last.getNext());
        last.setNext(newNode);
        */
        newNode.setNext(head);
        head = newNode;
        size++;
        return true;
    }

    @Override
    public boolean remove(Object value) {
        SingleLinkedNode<Set<E>> current = head;
        SingleLinkedNode<Set> predecessor = null;
        while (current != null) {
            if (value.equals(current.getValue())) {
                if (current == head) {
                    head = current.getNext();
                } else {
                    predecessor.setNext(current.getNext());
                }
                current.setNext(null);
                return true;
            }
            predecessor = (SingleLinkedNode)current;
            current = current.getNext();
        }
        return false;
    }

    @Override
    public Object[] getValuesArray() {
        Object[] list = new Object[size];
        int i = 0;
        SingleLinkedNode<Set<E>> node = head;
        while(node!=null){
            list[i]=node.getValue();
            i++;
            node=node.getNext();
        }
        return list;
    }
    
    @Override
    public Stack getValuesStack() {
        Stack list = new Stack();
        SingleLinkedNode<Set<E>> node = head;
        while(node!=null){
            list.push(node.getValue());
            node=node.getNext();
        }
        return list;
    }

    @Override
    public JoinTree buildJoinTree(int leftSize) {
        JoinTree<Set> tree = new JoinTree(table,leftSize);
        SingleLinkedNode<Set<E>> node = head;
        while(node!=null){
            tree.insert(node.getValue());
            node=node.getNext();
        }
        return tree;
    }

    @Override
    public int count(int field, String value) {
        SingleLinkedNode<Set<E>> node = head;
        int count=0;
        while(node!=null){
            if(((Entry)(node.getValue().getValue())).getData()[field].equals(value))count++;
            node=node.getNext();
        }
        return count;
    }

}
