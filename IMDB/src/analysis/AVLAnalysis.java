/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysis;

import imdb.database.Database;
import imdb.database.Table;
import imdb.database.structures.binarySearchTree.avlTree.AVLTree;
import imdb.database.structures.common.Entry;
import java.util.Random;

/**
 *
 * @author Marcio Júnior
 */
public class AVLAnalysis extends Analysis{

    Entry[] entries1;
    AVLTree avl;

    public AVLAnalysis(Database db, String tableName1, String tableName2) {
        database = db;
        table1 = TableFactory.getInstance().cloneTable(db.getTable(tableName1), tableName1+" ");
        table2 = TableFactory.getInstance().cloneTable(db.getTable(tableName2), tableName2+" ");
        super.addTables();

        avl = (AVLTree) table1.getStructure();
        entries1 = (Entry[])table1.getValuesArray();
    }

    public long completeInsertionTime(int limit) {
        
        Table newTable = new Table("temporary", table1.getKeys());
        AVLTree sl = new AVLTree(newTable);
        sl.setTable(newTable);
        long insertionTime = System.nanoTime();
        for (int i = 0; i < entries1.length && i < limit; i++) {
            sl.put(entries1[i].getData());
        }
        return System.nanoTime() - insertionTime;
    }

    public void printCompleteInsertionTime() {
        String x = "";
        String y = "";
        for (int i = 100; i < this.getSize(); i += 2500) {
            long sum = 0;
            for (int k = 0; k < 20; k++) {
                sum += this.completeInsertionTime(i);
            }
            x += i + ",";
            y += (double) sum / (20000000) + ",";
            System.out.println(i + "\t" + (double) sum / (20000000));
        }
        System.out.println(x);
        System.out.println(y);
    }

    public long insertionTimePerItem(int limit) {
        Random r = new Random();
        Table newTable = new Table("temporary", table1.getKeys());
        AVLTree sl = new AVLTree(newTable);
        sl.setTable(newTable);
        int item = r.nextInt(limit);
        for (int i = 0; i < item; i++) {
            sl.put(entries1[i].getData());
        }
        for (int i = item + 1; i < entries1.length && i < limit; i++) {
            sl.put(entries1[i].getData());
        }
        long insertionTime = System.nanoTime();
        sl.put(entries1[item].getData());
        return System.nanoTime() - insertionTime;
    }

    public int getSize() {
        return avl.getSize();
    }

    public void printInsertionTimePerItem() {
        String x = "";
        String y = "";
        for (int i = 500; i < this.getSize(); i += 2500) {
            long sum = 0;
            for (int k = 0; k < 20; k++) {
                sum += this.insertionTimePerItem(i);
            }
            x += i + ",";
            y += (double) sum / (20) + ",";
            System.out.println(i + "\t" + (double) sum / (20000000));
        }
        System.out.println(x);
        System.out.println(y);
    }


    public long searchTimePerItem(int limit, AVLTree sl) {
        Random r = new Random();
        int item = r.nextInt(limit);
        long insertionTime = System.nanoTime();
        sl.search(table1.getEntryKey(entries1[item].getData()));
        return System.nanoTime() - insertionTime;
    }
    
    public long removeTimePerItem(int limit, AVLTree sl) {
        Random r = new Random();
        int item = r.nextInt(limit);
        long insertionTime = System.nanoTime();
        sl.remove(table1.getEntryKey(entries1[item].getData()));
        return System.nanoTime() - insertionTime;
    }

    public void printSearchTimePerItem() {
        String x = "";
        String y = "";

        for (int i = 500; i < this.getSize(); i += 2500) {
            Table newTable = new Table("temporary", table1.getKeys());
            AVLTree sl = new AVLTree(newTable);
            sl.setTable(newTable);
            for (int k = 0; k < entries1.length && k < i; k++) {
                sl.put(entries1[k].getData());
            }
            double sum = 0;
            for (int k = 0; k < 20; k++) {
                sum += this.searchTimePerItem(i, sl);
            }
            x += i + ",";
            y += (double) sum / (20000000) + ",";
            System.out.println(i + "\t" + (double) sum / (20000000));
        }
        System.out.println(x);
        System.out.println(y);
    }
}
