/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysis;

import imdb.database.Database;
import imdb.database.Table;
import imdb.database.structures.common.Entry;
import java.util.ArrayList;

/**
 *
 * @author Marcio Júnior
 */
public class Analysis {

    protected Table table1;
    protected Table table2;
    protected Database database;
    protected Object[] entry1;
    protected Object[] entry2;
    protected Table aux1;
    protected Table aux2;

    public void addTables() {
        database.addTable(table1);
        database.addTable(table2);
        entry1 = table1.getValuesArray();
        entry2 = table2.getValuesArray();
    }

    public void joinSetup() {
        aux1 = TableFactory.getInstance().cloneTableWOValues(table1, table1.getName() + " ");
        aux2 = TableFactory.getInstance().cloneTableWOValues(table2, table2.getName() + " ");
    }

    public void leftJoin() {
        for (int i = 1; i < entry1.length; i += entry1.length / 30) {
            aux1 = TableFactory.getInstance().cloneTableWOValues(table1, table1.getName() + " ");
            for (int j = 0; j < i; j++) {
                aux1.put(((Entry) entry1[j]).getData());
            }
            long total = 0;
            int iterations=10;
            for (int j = 0; j < iterations; j++) {
                long insertionTime = System.nanoTime();
                aux1.leftJoin(table2);
                total += System.nanoTime() - insertionTime;
            }
            System.out.println(i + "\t" + (total/iterations));
        }
        return;
    }

    public void rightJoin() {
        for (int i = 1; i < entry1.length; i += entry1.length / 30) {
            aux1 = TableFactory.getInstance().cloneTableWOValues(table1, table1.getName() + " ");
            for (int j = 0; j < i; j++) {
                aux1.put(((Entry) entry1[j]).getData());
            }
            long total = 0;
            int iterations=5;
            for (int j = 0; j < iterations; j++) {
                long insertionTime = System.nanoTime();
                aux1.rightJoin(table2);
                total += System.nanoTime() - insertionTime;
            }
            System.out.println(i + "\t" + (total/iterations));
        }
        return;
    }
    
    public void innerJoin() {
        for (int i = 1; i < entry1.length; i += entry1.length / 30) {
            aux1 = TableFactory.getInstance().cloneTableWOValues(table1, table1.getName() + " ");
            for (int j = 0; j < i; j++) {
                aux1.put(((Entry) entry1[j]).getData());
            }
            long total = 0;
            int iterations=10;
            for (int j = 0; j < iterations; j++) {
                long insertionTime = System.nanoTime();
                aux1.innerJoin(table2);
                total += System.nanoTime() - insertionTime;
            }
            System.out.println(i + "\t" + (total/iterations));
        }
        return;
    }
    
    
}
