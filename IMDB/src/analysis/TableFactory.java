/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysis;

import imdb.database.Table;
import imdb.database.structures.chainedList.Stack;
import imdb.database.structures.common.Entry;

/**
 *
 * @author Marcio Júnior
 */
public class TableFactory {
    private static TableFactory instance=null;
    
    private TableFactory() {
    }
    
    public Table cloneTable(Table table, String tableName){
        Table newTable = new Table(tableName, table.getFields());
        newTable.setKeys(table.getKeys());
        newTable.setForeignKeyField(table.getForeignKeyField());
        newTable.setForeignKeyTable(table.getForeignKeyTable());
        Stack<Entry> stack = table.getValuesStack();
        Entry entry;
        while((entry=stack.pop())!=null){
            newTable.put(entry.getData());
        }
        
        return newTable;
    }
    
    public Table cloneTableWOValues(Table table, String tableName){
        Table newTable = new Table(tableName, table.getFields());
        newTable.setKeys(table.getKeys());
        newTable.setForeignKeyField(table.getForeignKeyField());
        newTable.setForeignKeyTable(table.getForeignKeyTable());
        return newTable;
    }
    
    public static TableFactory getInstance(){
        if(instance==null){
            instance = new TableFactory();
        }
        return instance;
    }
}
