/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imdb;

import imdb.database.structures.common.Entry;
import imdb.database.structures.skiplist.Node;

/**
 *
 * @author Marcio Júnior
 */
public class IMDB {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String[] s = new String[2];
        s[0]="a";
        s[1]="b";
        Node node = new Node(new Entry(s), 130);
        int x ="fdfgdfg".compareTo("b");
        node.getKey();
    }
    
}
