/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imdb.database;

import imdb.database.structures.common.Entry;
import java.util.Collection;

/**
 *
 * @author Marcio Júnior
 */
public class Database {
    private Table[] tables;
    
    public Database(){
        tables = new Table[0];
    }
    
    public boolean addTable(Table table){
        Table[] newTableArray = new Table[tables.length+1];
        for(int i=0;i<tables.length;i++){
            newTableArray[i]=tables[i];
            if(tables[i].getName().equals(table))return false;
        }
        newTableArray[tables.length]=table;
        tables=newTableArray;
        return true;
    }
    
    public boolean put(String tableName, String[] entry){
        for(Table table:tables){
            if(table.getName().equals(tableName)) return table.put(entry);
        }
        return false;
    }
    
    public String[] search(String tableName, String[] keys){
        for(Table table:tables){
            if(table.getName().equals(tableName)) {
                Entry entry = table.search(keys);
                if(entry!=null){
                    return  entry.getData();
                }
            }
        }
        return null;
    }
    
    public void setKeys(String tableName, String[] keys){
        for(Table table:tables){
            if(table.getName().equals(tableName)) table.setKeys(keys);
        }
    }
}
