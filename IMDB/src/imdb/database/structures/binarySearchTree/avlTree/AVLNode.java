/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imdb.database.structures.binarySearchTree.avlTree;

import imdb.database.structures.binarySearchTree.BinarySearchTreeNode;

/**
 *
 * @author Marcio Júnior
 */
public class AVLNode extends BinarySearchTreeNode {

    int height;

    public AVLNode(Comparable key, Object value) {
        super(key, value);
        height = 0;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
    

}
