/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imdb.database.structures.binarySearchTree;

import imdb.database.Table;
import imdb.database.structures.DatabaseStructure;
import imdb.database.structures.auxiliarTree.JoinTree;
import imdb.database.structures.chainedList.Queue;
import imdb.database.structures.chainedList.Stack;
import imdb.database.structures.common.Entry;
import imdb.database.structures.common.Set;
import java.util.ArrayList;

/**
 *
 * @author Marcio Júnior
 */
public class BinarySearchTree<E> extends DatabaseStructure {

    protected BinarySearchTreeNode root = null;

    public BinarySearchTree(Table table) {
        super(table);
    }

    @Override
    public boolean put(Object entry) {
        if (root == null) {
            root = new BinarySearchTreeNode(table.getEntryKey(entry), entry);
            return true;
        } else {
            BinarySearchTreeNode newNode = new BinarySearchTreeNode(table.getEntryKey(entry), entry);
            return root.put(newNode);
        }
    }

    @Override
    public boolean remove(Comparable key) {
        return root.remove(key);
    }

    @Override
    public boolean remove(Object entry) {
        return root.remove(table.getEntryKey(entry));
    }

    @Override
    public Object search(Comparable key) {
        return root.search(key);
    }

    @Override
    public Object[] getValuesArray() {
        return getAllValuesArray(this.root);
    }

    @Override
    public Stack getValuesStack() {
        return getAllValuesStack(this.root);
    }

    private static Object[] getAllValuesArray(BinarySearchTreeNode root) {
        ArrayList list = new ArrayList();
        Stack<BinarySearchTreeNode> stack = new Stack();
        stack.push(root);
        BinarySearchTreeNode current;
        while ((current = stack.pop()) != null) {
            list.add(current.getValue());
            stack.push(root.getLeft());
            stack.push(root.getRight());
        }
        return list.toArray();
    }

    private static Stack getAllValuesStack(BinarySearchTreeNode root) {
        Stack list = new Stack();
        Stack<BinarySearchTreeNode> stack = new Stack();
        stack.push(root);
        BinarySearchTreeNode current;
        while ((current = stack.pop()) != null) {
            list.push(current.getValue());
            stack.push(root.getLeft());
            stack.push(root.getRight());
        }
        return list;
    }

    @Override
    public JoinTree buildJoinTree(int leftSize) {
        JoinTree<Set> tree = new JoinTree(table,leftSize);
        Queue<BinarySearchTreeNode> queue = new Queue();
        queue.push(this.root);
        BinarySearchTreeNode node;
        while ((node = queue.pop()) != null) {
            queue.push(node.getLeft());
            queue.push(node.getRight());
            tree.insert(node);
        }
        return tree;
    }
    
     @Override
    public int count(int field, String value) {
        Queue<BinarySearchTreeNode> queue = new Queue();
        BinarySearchTreeNode node = root;
        int count = 0;
        while ((node = queue.pop()) != null) {
            if (((Entry) (node.getValue())).getData()[field].equals(value)) {
                count++;
            }
            queue.push( node.getLeft());
            queue.push( node.getRight());
        }
        return count;
    }

}
