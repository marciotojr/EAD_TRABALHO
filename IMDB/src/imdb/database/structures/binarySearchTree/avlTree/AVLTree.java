/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *
 */
package imdb.database.structures.binarySearchTree.avlTree;

import imdb.database.Table;
import imdb.database.structures.DatabaseStructure;
import imdb.database.structures.auxiliarTree.JoinTree;
import imdb.database.structures.binarySearchTree.BinarySearchTreeNode;
import imdb.database.structures.chainedList.Queue;
import imdb.database.structures.chainedList.Stack;
import imdb.database.structures.common.Entry;
import imdb.database.structures.common.Key;
import imdb.database.structures.common.Set;
import java.util.ArrayList;

public class AVLTree<E> extends DatabaseStructure {

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
        removeNode(key, root);
        int a = verify(root);
        return true;
    }

    private static int verify(BinarySearchTreeNode node) {
        if (node == null) {
            return 0;
        }
        int left = verify(node.getLeft());
        int right = verify(node.getRight());
        if (Math.abs(left - right) >= 2) {
            return Integer.MIN_VALUE;
        }
        return Math.max(left, right) + 1;
    }

    private AVLNode removeNode(Comparable key, AVLNode node) {
        if (node == null) {
            return null;
        }
        int compare = key.compareTo(node.getKey());
        if (compare == 0) {
            if (node.getLeft() == null && node.getRight() == null) {
                node = null;
                size--;
                return node;
            } else {
                BinarySearchTreeNode leafNode = node;
                boolean balance = height((AVLNode) node.getLeft()) < height((AVLNode) node.getRight());
                if (balance) {
                    leafNode = leafNode.getLeft();
                    while (leafNode.getRight() != null) {
                        leafNode = leafNode.getRight();
                    }
                } else {
                    leafNode = leafNode.getRight();
                    while (leafNode.getLeft() != null) {
                        leafNode = leafNode.getLeft();
                    }
                }
                BinarySearchTreeNode auxNode = new AVLNode(leafNode.getKey(), leafNode.getValue());
                leafNode.setKey(node.getKey());
                leafNode.setValue(node.getValue());
                node.setKey(auxNode.getKey());
                node.setValue(auxNode.getValue());
                if (balance) {
                    node.setLeft(removeNode(key, (AVLNode) node.getLeft()));
                } else {
                    node.setRight(removeNode(key, (AVLNode) node.getRight()));
                }

            }

        } else if (compare < 0) {
            node.setLeft(removeNode(key, (AVLNode) node.getLeft()));
            if (Math.abs(height((AVLNode) node.getLeft()) - height((AVLNode) node.getRight())) == 2) {
                if (node.getLeft() == null) {
                    if (height(((AVLNode) ((AVLNode) node.getRight()).getLeft())) < height(((AVLNode) ((AVLNode) node.getRight()).getRight()))) {
                        node = rotateWithRightChild(node);
                    } else {
                        node = doubleWithRightChild(node);
                    }
                }
            }
        } else if (compare > 0) {
            node.setRight(removeNode(key, (AVLNode) node.getRight()));
            if (Math.abs(height((AVLNode) node.getRight()) - height((AVLNode) node.getLeft())) == 2) {
                if (node.getRight() == null) {
                    if (height(((AVLNode) ((AVLNode) node.getLeft()).getLeft())) > height(((AVLNode) ((AVLNode) node.getLeft()).getRight()))) {
                        node = rotateWithLeftChild(node);
                    } else {
                        node = doubleWithLeftChild(node);
                    }
                }
            }
        }
        node.setHeight(Math.max(height((AVLNode) node.getLeft()), height((AVLNode) node.getRight())) + 1);
        return node;
    }

    public String printTree(AVLNode node) {
        String s = "";
        if (node != null) {
            s += ((Key) node.getKey()).getKey().replace("\r", "");
            s += "(";
            s += printTree((AVLNode) node.getLeft());
            s += ") (";
            s += printTree((AVLNode) node.getRight());
            s += ")";
        }
        if (node == root) {
            return s;
        }
        return s;
    }

    public void printTree() {
        System.out.println(printTree(root));
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

    /*private void printTree(AVLNode t) {
        if (t != null) {
            printTree((AVLNode) t.getLeft());
            System.out.println(t.getKey() + "||" + t.getValue());
            printTree((AVLNode) t.getRight());
        }
    }*/
    private static int height(AVLNode t) {
        return t == null ? -1 : t.getHeight();
    }

    private static AVLNode rotateWithLeftChild(AVLNode k2) {
        AVLNode k1 = (AVLNode) k2.getLeft();
        k2.setLeft(k1.getRight());
        k1.setRight(k2);
        k2.setHeight(Math.max(height((AVLNode) k2.getLeft()), height((AVLNode) k2.getRight())) + 1);
        k1.setHeight(Math.max(height((AVLNode) k1.getLeft()), k2.getHeight()) + 1);
        return k1;
    }

    private static AVLNode rotateWithRightChild(AVLNode k1) {
        AVLNode k2 = (AVLNode) k1.getRight();
        k1.setRight(k2.getLeft());
        k2.setLeft(k1);
        k1.setHeight(Math.max(height((AVLNode) k1.getLeft()), height((AVLNode) k1.getRight())) + 1);
        k2.setHeight(Math.max(height((AVLNode) k2.getRight()), k1.getHeight()) + 1);
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

    @Override
    public Object[] getValuesArray() {
        return getAllValuesArray(this.root);
    }

    @Override
    public Stack getValuesStack() {
        return getAllValuesStack(this.root);
    }

    private static Object[] getAllValuesArray(AVLNode root) {
        ArrayList list = new ArrayList();
        Stack<AVLNode> stack = new Stack();
        stack.push(root);
        AVLNode current;
        while ((current = stack.pop()) != null) {
            list.add(current.getValue());
            stack.push((AVLNode) root.getLeft());
            stack.push((AVLNode) root.getRight());
        }
        return list.toArray();
    }

    private static Stack getAllValuesStack(AVLNode root) {
        Stack list = new Stack();
        Stack<AVLNode> stack = new Stack();
        stack.push(root);
        AVLNode current;
        while ((current = stack.pop()) != null) {
            list.push(current.getValue());
            stack.push((AVLNode) current.getLeft());
            stack.push((AVLNode) current.getRight());
        }
        return list;
    }

    @Override
    public JoinTree buildJoinTree(int leftSize) {
        JoinTree<Set> tree = new JoinTree(table, leftSize);
        Queue<AVLNode> queue = new Queue();
        queue.push(this.root);
        AVLNode node;
        while ((node = queue.pop()) != null) {
            tree.insert(node);
            queue.push((AVLNode) node.getLeft());
            queue.push((AVLNode) node.getRight());
        }
        return tree;
    }

    @Override
    public int count(int field, String value) {
        Stack<AVLNode> queue = new Stack();
        AVLNode node = root;
        queue.push(root);
        int count = 0;
        while ((node = queue.pop()) != null) {
            if (((Entry) (node.getValue())).getData()[field].equals(value)) {
                count++;
            }
            queue.push((AVLNode) node.getLeft());
            queue.push((AVLNode) node.getRight());
        }
        return count;
    }

}
