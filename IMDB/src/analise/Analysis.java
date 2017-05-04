/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analise;

import imdb.database.Database;
import imdb.database.Table;
import imdb.database.structures.common.Entry;
import imdb.database.structures.skiplist.Node;
import imdb.database.structures.skiplist.SkipList;
import java.util.Random;

/**
 *
 * @author Marcio Júnior
 */
public class Analysis {

    Entry[] entries;
    String[] keys;
    Table table;
    SkipList skiplist;
    Database database;

    public Analysis(Database db, String tableName) {
        database = db;
        table = database.getTable(tableName);
        skiplist = (SkipList) table.getStructure();
        Node head = skiplist.getHead().getNodes()[0];
        entries = new Entry[skiplist.getSize() - 1];
        keys = new String[skiplist.getSize() - 1];
        for (int i = 0; head != null; head = head.getNodes()[0]) {
            if (head.getEntry() != null) {
                entries[i] = head.getEntry();
                keys[i] = head.getKey();
                i++;
            }
        }
    }

    public int[] nodesPerLevel() {
        int[] returnValue = new int[skiplist.getHead().getNodes().length];
        for (int i = 0; i < returnValue.length; i++) {
            returnValue[i] = 0;
        }

        for (Node head = skiplist.getHead().getNodes()[0]; head != null; head = head.getNodes()[0]) {
            returnValue[head.getNodes().length - 1]++;
        }

        return returnValue;
    }

    public double[] percentagePerLevel() {
        double[] returnValue = new double[skiplist.getHead().getNodes().length];
        int[] nodePerLevel = this.nodesPerLevel();
        for (int i = 0; i < returnValue.length; i++) {
            returnValue[i] = ((double) nodePerLevel[i]) / skiplist.getSize();
        }

        return returnValue;
    }

    public long completeInsertionTime(int limit) {
        long insertionTime = System.nanoTime();
        SkipList sl = new SkipList();
        Table newTable = new Table("temporary", table.getKeys());
        sl.setTable(newTable);
        for (int i = 0; i < entries.length && i < limit; i++) {
            sl.put(entries[i].getData());
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
        SkipList sl = new SkipList();
        Table newTable = new Table("temporary", table.getKeys());
        sl.setTable(newTable);
        int item = r.nextInt(limit);
        for (int i = 0; i < item; i++) {
            sl.put(entries[i].getData());
        }
        for (int i = item + 1; i < entries.length && i < limit; i++) {
            sl.put(entries[i].getData());
        }
        long insertionTime = System.nanoTime();
        sl.put(entries[item].getData());
        return System.nanoTime() - insertionTime;
    }

    public int getSize() {
        return skiplist.getSize();
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

    public int getSpace(int limit) {
        int space = 0;
        SkipList sl = new SkipList();
        Table newTable = new Table("temporary", table.getKeys());
        sl.setTable(newTable);
        for (int i = 0; i < entries.length && i < limit; i++) {
            sl.put(entries[i].getData());
        }
        for (Node head = sl.getHead(); head != null; head = head.getNodes()[0]) {
            space += head.getNodes().length;
        }
        return space;
    }

    public void printSpace() {
        String x = "";
        String y = "";
        for (int i = 500; i < this.getSize(); i += 2500) {
            int space = 0;
            for (int k = 0; k < 20; k++) {
                space += getSpace(i);
            }
            space /= 20;
            x += i + ",";
            y += space + ",";
            System.out.println(i + "\t" + space);
        }
        System.out.println(x);
        System.out.println(y);
    }

    public long searchTimePerItem(int limit, SkipList sl) {
        Random r = new Random();
        int item = r.nextInt(limit);
        long insertionTime = System.nanoTime();
        sl.search(table.getEntryKey(entries[item].getData()));
        return System.nanoTime() - insertionTime;
    }

    public void printSearchTimePerItem() {
        String x = "";
        String y = "";

        for (int i = 500; i < this.getSize(); i += 2500) {
            SkipList sl = new SkipList();
            Table newTable = new Table("temporary", table.getKeys());
            sl.setTable(newTable);
            for (int k = 0; k < entries.length && k < i; k++) {
                sl.put(entries[k].getData());
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
