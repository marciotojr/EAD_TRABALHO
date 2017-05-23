/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imdb.database.structures.binarySearchTree;

import imdb.database.structures.common.Set;
import java.util.Random;

/**
 *
 * @author Marcio Júnior
 */
public class BinarySearchTreeNode extends Set {

    protected BinarySearchTreeNode left, right;

    public BinarySearchTreeNode(Comparable key, Object value) {
        super(key, value);
        this.left = null;
        this.right = null;
    }

    public boolean put(BinarySearchTreeNode node) {
        int compare = this.key.compareTo(node.getKey());
        if (compare == 0) {
            return false;
        } else if (compare > 0) {
            if (left == null) {
                left = node;
                return true;
            } else {
                return left.put(node);
            }
        } else if (compare < 0) {
            if (right == null) {
                right = node;
                return true;
            } else {
                return right.put(node);
            }
        }
        return false;
    }

    public boolean remove(Comparable key) {
        return remove(this, null, key);
    }

    private static boolean remove(BinarySearchTreeNode current, BinarySearchTreeNode parent, Comparable key) {
        int compare = current.key.compareTo(key);
        if (compare == 0) {
            if (current.left == null && current.right == null) {
                if (parent.getKey().compareTo(key) < 0) {
                    parent.right = null;
                } else {
                    parent.left = null;
                }
                return true;
            } else if (current.left == null || current.right == null) {
                if (parent != null) {
                    if (parent.getKey().compareTo(key) > 0) {
                        if (current.left != null) {
                            parent.left = current.left;
                            return true;
                        } else {
                            parent.left = current.right;
                            return true;
                        }
                    } else {
                        if (current.left != null) {
                            parent.right = current.left;
                            return true;
                        } else {
                            parent.right = current.right;
                            return true;
                        }
                    }
                } else {
                    if (current.left != null) {
                        current = current.left;
                        return true;
                    } else {
                        current = current.right;
                        return true;
                    }
                }
            } else {
                BinarySearchTreeNode minMaxChild = null;
                BinarySearchTreeNode minMaxParent = null;
                Random random = new Random();
                if (random.nextBoolean()) {
                    minMaxChild = current.left;
                    while (minMaxChild.right != null) {
                        minMaxParent = minMaxChild;
                        minMaxChild = minMaxChild.right;
                    }
                    current.key = minMaxChild.key;
                    current.value = minMaxChild.value;
                    if (minMaxParent != null) {
                        minMaxParent.right = null;
                    }
                    return current.left.remove(current.left.getKey());
                } else {
                    minMaxChild = current.right;
                    while (minMaxChild.left != null) {
                        minMaxParent = minMaxChild;
                        minMaxChild = minMaxChild.left;
                    }
                    current.key = minMaxChild.key;
                    current.value = minMaxChild.value;
                    if (minMaxParent != null) {
                        minMaxParent.left = null;
                    }
                    return current.right.remove(current.right.getKey());
                }
            }
        } else if (compare > 0) {
            if (current.left == null) {
                return false;
            } else {
                return remove(current.left, current, key);
            }
        } else if (compare < 0) {
            if (current.right == null) {
                return false;
            } else {
                return remove(current.right, current, key);
            }
        }
        return false;
    }

    public Object search(Comparable key) {
        return search(key, this);
    }

    public static Object search(Comparable key, BinarySearchTreeNode root) {
        if (root != null) {
            int compare = root.key.compareTo(key);
            if (compare == 0) {
                return root;
            } else if (compare > 0) {
                return search(key, root.left);
            } else if (compare < 0) {
                return search(key, root.right);
            }
        }
        return null;
    }

    public BinarySearchTreeNode getLeft() {
        return left;
    }

    public void setLeft(BinarySearchTreeNode left) {
        this.left = left;
    }

    public BinarySearchTreeNode getRight() {
        return right;
    }

    public void setRight(BinarySearchTreeNode right) {
        this.right = right;
    }

    public String toString() {
        return key.toString();
    }
}
