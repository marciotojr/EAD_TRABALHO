/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imdb.database;

import imdb.database.structures.Structure;
import imdb.database.structures.common.Entry;
import imdb.database.structures.skiplist.SkipList;

/**
 *
 * @author Marcio Júnior
 */
public class Table{

    String[] fields;
    boolean[] keys;
    Structure structure;
    String name;
    private static final char separador = (char)13;

    public Table(String name, String[] newFields) {
        this.fields = newFields;
        this.keys = new boolean[this.fields.length];
        for (int i = 0; i < this.fields.length; i++) {
            keys[i] = false;
        }
        keys[0] = true;
        structure = new SkipList();
        this.name = name;
        structure.setTable(this);
    }

    public void setKeys(String[] keys) {
        for (int i = 0; i < this.fields.length; i++) {
            this.keys[i] = false;
        }
        for (int i = 0; i < this.fields.length; i++) {
            for (String key : keys) {
                if (this.fields[i].equals(key)) {
                    this.keys[i] = true;
                }
            }
        }
    }

    public String[] getKeys() {
        int numChaves = 0;
        for (int i = 0; i < this.keys.length; i++) {
            if (this.keys[i]) {
                numChaves++;
            }
        }
        String[] keys = new String[numChaves];
        int pos = 0;
        for (int i = 0; i < this.fields.length; i++) {
            if (this.keys[i]) {
                keys[pos] = this.fields[i];
                pos++;
            }
        }
        return keys;
    }

    public String getEntryKey(String[] entry) {
        String key = "";
        for (int i = 0; i < this.fields.length; i++) {
            if (this.keys[i]) {
                key += entry[i] + separador;
            }
        }
        return key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Entry search(String[] keys) {
        String key = "";
        for (String s : keys) {
            key += s + separador;
        }
        return structure.search(key);
    }
    
    public boolean put(String[] entry){
        return structure.put(entry);
    }
    
    public String toString(){
        return this.name + "  " + this.structure.getSize();
    }

    public Structure getStructure() {
        return structure;
    }
    
    
}
