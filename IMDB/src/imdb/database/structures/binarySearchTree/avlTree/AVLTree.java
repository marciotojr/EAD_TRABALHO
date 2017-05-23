/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *
 */
package imdb.database.structures.binarySearchTree.avlTree;

import imdb.database.Table;
import imdb.database.structures.Structure;

public class AVLTree<E> extends Structure {

    private AVLNode root;

    public AVLTree(Table table) {
        super(table);
        root = null;
    }

    public void insert(E value) {
        root = insert(value, root);
    }

    @Override
    public boolean remove(Comparable key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void printTree() {
        if (root != null) {
            System.out.println("Empty tree");
        } else {
            printTree(root);
        }
    }

    private AVLNode insert(E value, AVLNode t) {
        Comparable x = table.getEntryKey(value);
        if (t == null) {
            t = new AVLNode(x, value);
        } else if (x.compareTo(t.getKey()) < 0) {
            t.setLeft(insert(value, (AVLNode) t.getLeft()));
            if (Math.abs(height((AVLNode) t.getLeft()) - height((AVLNode) t.getRight())) == 2) {
                if (x.compareTo(t.getLeft().getKey()) < 0) {
                    t = rotateWithLeftChild(t);
                } else {
                    t = doubleWithLeftChild(t);
                }
            }
        } else if (x.compareTo(t.getKey()) > 0) {
            t.setRight(insert(value, (AVLNode) t.getRight()));
            int balance = Math.abs(height((AVLNode) t.getRight()) - height((AVLNode) t.getLeft()));
            if (Math.abs(height((AVLNode) t.getRight()) - height((AVLNode) t.getLeft())) == 2) {
                if (x.compareTo(t.getRight().getKey()) > 0) {
                    t = rotateWithRightChild(t);
                } else {
                    t = doubleWithRightChild(t);
                }
            }
        }
        t.setHeight(Math.max(height((AVLNode) t.getLeft()), height((AVLNode) t.getRight())) + 1);
        return t;
    }

    private void printTree(AVLNode t) {
        if (t != null) {
            printTree((AVLNode) t.getLeft());
            System.out.println(t.getKey() + "||" + t.getValue());
            printTree((AVLNode) t.getRight());
        }
    }

    private static int height(AVLNode t) {
        return t == null ? -1 : t.height;
    }

    private static AVLNode rotateWithLeftChild(AVLNode k2) {
        AVLNode k1 = (AVLNode) k2.getLeft();
        k2.setLeft(k1.getRight());
        k1.setRight(k2);
        k2.setHeight(Math.max(height((AVLNode) k2.getLeft()), height((AVLNode) k2.getRight())) + 1);
        k1.setHeight(Math.max(height((AVLNode) k1.getLeft()), k2.height) + 1);
        return k1;
    }

    private static AVLNode rotateWithRightChild(AVLNode k1) {
        AVLNode k2 = (AVLNode) k1.getRight();
        k1.setRight(k2.getLeft());
        k2.setLeft(k1);
        k1.setHeight(Math.max(height((AVLNode) k1.getLeft()), height((AVLNode) k1.getRight())) + 1);
        k2.setHeight(Math.max(height((AVLNode) k2.getRight()), k1.height) + 1);
        return k2;
    }

    private static AVLNode doubleWithLeftChild(AVLNode k3) {
        k3.setLeft(rotateWithRightChild((AVLNode) k3.getLeft()));
        return rotateWithLeftChild(k3);
    }

    private static AVLNode doubleWithRightChild(AVLNode k1) {
        k1.setRight(rotateWithLeftChild((AVLNode) k1.getRight()));
        return rotateWithRightChild(k1);
    }

    @Override
    public boolean put(Object entry) {
        root = insert((E) entry, root);
        size++;
        return true;
    }

    @Override
    public boolean remove(Object entry) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object search(Comparable key) {
        return search(key, root);
    }

    public static Object search(Comparable key, AVLNode root) {
        if (root != null) {
            int compare = root.getKey().compareTo(key);
            if (compare == 0) {
                return root.getValue();
            } else if (compare > 0) {
                return search(key, (AVLNode) root.getLeft());
            } else if (compare < 0) {
                return search(key, (AVLNode) root.getRight());
            }
        }
        return null;
    }

}
