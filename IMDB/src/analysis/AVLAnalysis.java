/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysis;

import imdb.database.Database;
import imdb.database.Table;
import imdb.database.structures.binarySearchTree.BinarySearchTreeNode;
import imdb.database.structures.binarySearchTree.avlTree.AVLTree;
import imdb.database.structures.common.Entry;
import java.util.Random;

/**
 *
 * @author Marcio Júnior
 */
public class AVLAnalysis extends Analysis {

    Object[] entries1;
    AVLTree avl;

    public AVLAnalysis(Database db, String tableName1, String tableName2) {
        database = db;
        table1 = TableFactory.getInstance().cloneTable(db.getTable(tableName1), tableName1 + " ");
        table2 = TableFactory.getInstance().cloneTable(db.getTable(tableName2), tableName2 + " ");
        super.addTables();

        avl = (AVLTree) table1.getStructure();
        entries1 = table1.getValuesArray();
    }

    public long completeInsertionTime(int limit) {

        Table newTable = TableFactory.getInstance().cloneTableWOValues(table1, table1.getName() + " ");
        AVLTree sl = (AVLTree) newTable.getStructure();
        sl.setTable(newTable);
        long insertionTime = System.nanoTime();
        for (int i = 0; i < entries1.length && i < limit; i++) {
            sl.put(((Entry) entries1[i]).getData());
        }
        return System.nanoTime() - insertionTime;
    }

    public void printCompleteInsertionTime() {
        String x = "";
        String y = "";
        for (int i = 100; i < this.getSize(); i += this.getSize() / 30) {
            long sum = 0;
            int iteration = 20;
            for (int k = 0; k < iteration; k++) {
                sum += this.completeInsertionTime(i);
            }
            System.out.println(i + "\t" + (double) sum / (iteration * 1000000));
        }
        System.out.println(x);
        System.out.println(y);
    }

    public long insertionTimePerItem(int limit) {
        Random r = new Random();
        Table newTable = TableFactory.getInstance().cloneTableWOValues(table1, table1.getName() + " ");
        AVLTree sl = (AVLTree) newTable.getStructure();
        sl.setTable(newTable);
        int item = r.nextInt(limit);
        for (int i = 0; i < item; i++) {
            sl.put(((Entry) entries1[i]).getData());
        }
        for (int i = item + 1; i < entries1.length && i < limit; i++) {
            sl.put(((Entry) entries1[i]).getData());
        }
        long insertionTime = System.nanoTime();
        sl.put(((Entry) entries1[item]).getData());
        return System.nanoTime() - insertionTime;
    }

    public int getSize() {
        return avl.getSize();
    }

    public void printInsertionTimePerItem() {
        String x = "";
        String y = "";
        for (int i = 500; i < this.getSize(); i += this.getSize() / 30) {
            long sum = 0;
            int iterations = 20;
            for (int k = 0; k < iterations; k++) {
                sum += this.insertionTimePerItem(i);
            }
            x += i + ",";
            y += (double) sum / (iterations) + ",";
            System.out.println(i + "\t" + (double) sum / (iterations * 1000000));
        }
        System.out.println(x);
        System.out.println(y);
    }

    public long searchTimePerItem(int limit, AVLTree sl) {
        Random r = new Random();
        int item = r.nextInt(limit);
        long insertionTime = System.nanoTime();
        sl.search(table1.getEntryKey(((Entry) entries1[item]).getData()));
        return System.nanoTime() - insertionTime;
    }

    public long removeTimePerItem(int limit, AVLTree sl) {
        Random r = new Random();
        int item = r.nextInt(limit);
        long insertionTime = System.nanoTime();
        sl.remove(table1.getEntryKey(((Entry) entries1[item]).getData()));
        return System.nanoTime() - insertionTime;
    }

    public void printSize() {
        for (int i = 1; i < entry1.length; i += entry1.length / 30) {
            aux1 = TableFactory.getInstance().cloneTableWOValues(table1, table1.getName() + " ");
            for (int j = 0; j < i; j++) {
                aux1.put(((Entry) entry1[j]).getData());
            }
            int iterations = 5;
            int size = 0;
            for (int j = 0; j < iterations; j++) {
                size += size(((AVLTree) aux1.getStructure()).getRoot());
            }
            System.out.println(i + "\t" + (size / iterations));
        }
        return;
    }

    public int size() {
        return size(avl.getRoot());
    }

    private static int size(BinarySearchTreeNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + size(node.getLeft()) + size(node.getRight());
    }

    public void printSearchTimePerItem() {
        String x = "";
        String y = "";

        for (int i = 1; i < this.getSize(); i += this.getSize() / 30) {
            Table newTable = TableFactory.getInstance().cloneTableWOValues(table1, table1.getName() + " ");
            AVLTree sl = (AVLTree) newTable.getStructure();
            sl.setTable(newTable);
            for (int k = 0; k < entries1.length && k < i; k++) {
                sl.put(((Entry) entries1[k]).getData());
            }
            double sum = 0;
            int iterations = 20;
            for (int k = 0; k < iterations; k++) {
                sum += this.searchTimePerItem(i, sl);
            }
            System.out.println(i + "\t" + (double) sum / (iterations * 1000000));
        }
        System.out.println(x);
        System.out.println(y);
    }

   public void printRemoveTime() {
        String x = "";
        String y = "";

        for (int i = 1; i < this.getSize(); i += this.getSize() / 30) {
            Table newTable = TableFactory.getInstance().cloneTableWOValues(table1, table1.getName() + " ");
            AVLTree sl = (AVLTree) newTable.getStructure();
            sl.setTable(newTable);
            for (int k = 0; k < entries1.length && k < i; k++) {
                sl.put(((Entry) entries1[k]).getData());
            }
            long sum = 0;
            int iterations = 20;
            Random rand = new Random();
            for (int k = 0; k < iterations; k++) {
                int pos = rand.nextInt(i);
                long begin = System.nanoTime();
                sl.remove(entry1[pos]);
                sum += System.nanoTime()-begin;
            }
            System.out.println(i + "\t" + (double) sum / (iterations));
        }
        System.out.println(x);
        System.out.println(y);
    }

}
