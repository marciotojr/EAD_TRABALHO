/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysis;

import imdb.database.Database;
import imdb.database.Table;
import imdb.database.structures.common.Entry;
import imdb.database.structures.common.Key;
import imdb.database.structures.skiplist.SkipListNode;
import imdb.database.structures.skiplist.SkipList;
import java.util.Random;


/**
 *
 * @author Marcio Júnior
 */
@Deprecated
public class SkipListAnalysis extends Analysis{

    Entry[] entries1;
    String[] keys1;
    SkipList skiplist;

    public SkipListAnalysis(Database db, String tableName1, String tableName2) {
        database = db;
        table1 = TableFactory.getInstance().cloneTable(db.getTable(tableName1), tableName1+" ");
        table2 = TableFactory.getInstance().cloneTable(db.getTable(tableName2), tableName2+" ");
        
        
        super.addTables();
        skiplist = (SkipList) table1.getStructure();
        SkipListNode head = skiplist.getHead().getNodes()[0];
        entries1 = new Entry[skiplist.getSize() - 1];
        keys1 = new String[skiplist.getSize() - 1];
        for (int i = 0; head != null; head = head.getNodes()[0]) {
            if (head.getValue()!= null) {
                entries1[i] = (Entry)head.getValue();
                keys1[i] = ((Key)head.getKey()).getKey();
                i++;
            }
        }
    }

    public int[] nodesPerLevel() {
        int[] returnValue = new int[skiplist.getHead().getNodes().length];
        for (int i = 0; i < returnValue.length; i++) {
            returnValue[i] = 0;
        }

        for (SkipListNode head = skiplist.getHead().getNodes()[0]; head != null; head = head.getNodes()[0]) {
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
        Table newTable = TableFactory.getInstance().cloneTableWOValues(table1, table1.getName()+" ");
        SkipList sl = (SkipList)newTable.getStructure();
        sl.setTable(newTable);
        for (int i = 0; i < entries1.length && i < limit; i++) {
            sl.put(entries1[i].getData());
        }
        return System.nanoTime() - insertionTime;
    }

    public void printCompleteInsertionTime() {
        String x = "";
        String y = "";
        for (int i = 100; i < this.getSize(); i += this.getSize()/30) {
            long sum = 0;
            int iterations = 10;
            for (int k = 0; k < iterations; k++) {
                sum += this.completeInsertionTime(i);
            }
            System.out.println(i + "\t" + (double) sum / (iterations*1000000));
        }
    }

    public long insertionTimePerItem(int limit) {
        Random r = new Random();
        Table newTable = TableFactory.getInstance().cloneTableWOValues(table1, table1.getName()+" ");
        SkipList sl = (SkipList)newTable.getStructure();
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
        return skiplist.getSize();
    }

    public void printInsertionTimePerItem() {
        String x = "";
        String y = "";
        for (int i = 500; i < this.getSize(); i += this.getSize()/30) {
            long sum = 0;
            int iterations=3;
            for (int k = 0; k < iterations; k++) {
                sum += this.insertionTimePerItem(i);
            }
            System.out.println(i + "\t" + (double) sum / (iterations));
        }
        System.out.println(x);
        System.out.println(y);
    }

    public int getSpace(int limit) {
        int space = 0;
        Table newTable = TableFactory.getInstance().cloneTableWOValues(table1, table1.getName()+" ");
        SkipList sl = (SkipList)newTable.getStructure();
        sl.setTable(newTable);
        for (int i = 0; i < entries1.length && i < limit; i++) {
            sl.put(entries1[i].getData());
        }
        for (SkipListNode head = sl.getHead(); head != null; head = head.getNodes()[0]) {
            space += head.getNodes().length;
        }
        return space;
    }

    public void printSpace() {
        String x = "";
        String y = "";
        for (int i = 500; i < this.getSize(); i += 2500) {
            int space = 0;
            //for (int k = 0; k < 20; k++) {
                space += getSpace(i);
            //}
            //space /= 20;
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
        sl.search(table1.getEntryKey(entries1[item].getData()));
        return System.nanoTime() - insertionTime;
    }
    
    public long removeTimePerItem(int limit, SkipList sl) {
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
            SkipList sl = new SkipList(newTable);
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

    public void printRemoveTime() {
       String x = "";
        String y = "";
        for (int i = 500; i < this.getSize(); i += this.getSize()/30) {
            long sum = 0;
            int iterations=3;
            for (int k = 0; k < iterations; k++) {
                sum += this.removeTimePerItem(i,this.skiplist);
            }
            System.out.println(i + "\t" + (double) sum / (iterations));
        }
        System.out.println(x);
        System.out.println(y);
    }
}
