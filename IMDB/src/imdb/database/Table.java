/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imdb.database;

import imdb.database.structures.DatabaseStructure;
import imdb.database.structures.auxiliarTree.JoinTree;
import imdb.database.structures.chainedList.Stack;
import imdb.database.structures.common.Entry;
import imdb.database.structures.common.Key;
import imdb.database.structures.common.Set;
import imdb.database.structures.binarySearchTree.avlTree.AVLTree;
import imdb.database.structures.skiplist.SkipList;

/**
 * Classe que define métodos e atributos da tabela
 *
 * @author Marcio Júnior
 */
public class Table {

    /**
     * Lista de campos da tabela
     */
    private final String[] fields;
    private String[] foreignKeyTable;
    private String[] foreignKeyField;
    /**
     * Lista que indica se o campo em fields[i] faz parte da chave primária ou
     * não
     */
    private final boolean[] keys;
    /**
     * Estrutura onde serão armazenados os registros
     */
    private final DatabaseStructure<Entry> structure;
    /**
     * Nome da tabela
     */
    private final String name;
    /**
     * Símbolo separador das chaves na concatenação
     */
    private static final char SEPARATOR = (char) 13;

    /**
     * Inicializador da tabela e da estrutura da tabela
     *
     * @param name nome da tabela
     * @param newFields lista de campos da tabela
     */
    public Table(String name, String[] newFields) {
        this.fields = newFields;
        this.foreignKeyField = new String[this.fields.length];
        this.foreignKeyTable = new String[this.fields.length];
        this.keys = new boolean[this.fields.length];
        for (int i = 0; i < this.fields.length; i++) {
            keys[i] = false;
            foreignKeyTable[i] = "";
            foreignKeyField[i] = "";
        }
        keys[0] = true;
        structure = new SkipList(this);
        this.name = name;
    }

    /**
     * Identifica campos que formam a chave primária da tabela
     *
     * @param keys lista que indica se o campo em fields[i] faz parte da chave
     * primária ou não
     */
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

    /**
     * Retorna um vetor de Strings que fazem parte da chave primária
     *
     * @return vetor de Strings que fazem parte da chave primária
     */
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

    /**
     * Retorna a chave de um registro na tabela atual
     *
     * @param entry registro a ter sua chave calculada
     * @return chave de um registro na tabela atual
     */
    public Comparable getEntryKey(Object value) {
        Comparable key = "";
        if (value instanceof Entry) {
            for (int i = 0; i < this.fields.length; i++) {
                if (this.keys[i]) {
                    key += ((Entry) value).getData()[i] + SEPARATOR;
                }
            }
            key = new Key((String) key);
        } else if (value instanceof String[]) {
            if (((String[]) value).length < this.fields.length) {
                for (String s : (String[]) value) {
                    key += s + SEPARATOR;
                }
            } else {
                for (int i = 0; i < this.fields.length; i++) {
                    if (this.keys[i]) {
                        key += ((Entry) value).getData()[i] + SEPARATOR;
                    }
                }
            }
            key = new Key((String) key);
        } else if (value instanceof Set) {
            return ((Set) value).getKey();
        }
        return key;
    }

    public void setEntryKey(String[] entry, Set node) {
        /*String key = "";
        for (int i = 0; i < this.fields.length; i++) {
            if (this.keys[i]) {
                key += entry[i] + SEPARATOR;
            }
        }*/
        node.setKey(this.getEntryKey(entry));
    }

    /**
     * Retona o nome da tabela
     *
     * @return nome da tabela
     */
    public String getName() {
        return name;
    }

    /**
     * Busca um registro com chave igual à chave gerada pelo conjunto de valores
     * passado como parametro
     *
     * @param keys valores da chave do nó
     * @return registro que corresponde às chaves passadas por parâmetro
     */
    public Entry search(String[] keys) {
        String concatKey = "";
        for (String s : keys) {
            concatKey += s + SEPARATOR;
        }
        Key key = new Key(concatKey);
        return (Entry) structure.search(key);
    }

    public void remove(String[] keys) {
        String concatKey = "";
        for (String s : keys) {
            concatKey += s + SEPARATOR;
        }
        Key key = new Key(concatKey);
        System.err.println("Removendo: " + key);
        structure.remove(key);
    }

    /**
     * Insere o registro na tabela
     *
     * @param entry registro a ser inserido
     * @return verdadeiro se o registro foi inserido, falso caso contrário
     */
    public boolean put(String[] entry) {
        return structure.put(new Entry(entry));
    }

    /**
     * Retorna informações basicas da tabela
     *
     * @return nome e numero de registro da tabela
     */
    @Override
    public String toString() {
        return this.name + "  " + this.structure.getSize();
    }

    /**
     * Retorna a estrutura usada pela tabela
     *
     * @return estrutura usada pela tabela
     */
    public DatabaseStructure getStructure() {
        return structure;
    }

    public Object[] getValuesArray() {
        Object[] o = structure.getValuesArray();
        return  o;
    }

    public Stack<Entry> getValuesStack() {
        return (Stack<Entry>) structure.getValuesStack();
    }

    public String[] getFields() {
        return fields;
    }

    public void setForeignKeyTable(String[] foreignKeyTable) {
        this.foreignKeyTable = foreignKeyTable;
    }

    public void setForeignKeyField(String[] foreignKeyField) {
        this.foreignKeyField = foreignKeyField;
    }

    public String[] getForeignKeyTable() {
        return foreignKeyTable;
    }

    public String[] getForeignKeyField() {
        return foreignKeyField;
    }

    public void insertForeignKeys(String table, String[] fk) {
        for (int i = 0; i < this.fields.length; i++) {
            for (int j = 0; j < fk.length; j++) {
                if (this.fields[i].equals(fk[j])) {
                    foreignKeyField[i] = fk[j];
                    foreignKeyTable[i] = table;
                }
            }
        }
    }

    public static char getSEPARATOR() {
        return SEPARATOR;
    }

    public int count() {
        return this.structure.getSize();
    }

    public int count(String field, String value) {
        for (int i = 0; i < this.fields.length; i++) {
            if (this.fields[i].equals(field)) {
                return structure.count(i, value);
            }
        }
        return -1;
    }

    private static int[] getPrimaryKeyIndexesFromForeignKey(Table table1, Table table2) {
        int pos[] = new int[table2.getKeys().length];
        int p = 0;
        String[] fk = table2.getKeys();
        for (int i = 0; i < fk.length; i++) {
            for (int j = 0; j < table1.getFields().length; j++) {
                if (table1.getForeignKeyTable()[j].equals(table2.getName()) && table1.getForeignKeyField()[j].equals(fk[i])) {
                    pos[p] = j;
                    p++;
                }
            }
        }
        return pos;
    }

    public Stack<String[]> innerJoin(Table table2) {
        Table table1 = this;
        Stack<Entry> stack = table1.getValuesStack();
        Entry entry;
        int[] pos = getPrimaryKeyIndexesFromForeignKey(table1, table2);
        Stack<String[]> list = new Stack<>();
        while ((entry = stack.pop()) != null) {
            String[] key = new String[pos.length];
            for (int i = 0; i < pos.length; i++) {
                key[i] = entry.getData()[pos[i]];
            }
            Entry searched = table2.search(key);
            if (searched != null) {
                String[] joinedEntry = new String[table1.getFields().length + table2.getFields().length];
                int i;
                for (i = 0; i < entry.getData().length; i++) {
                    if (i < entry.getData().length) {
                        joinedEntry[i] = entry.getData()[i];
                    }
                }
                for (int j = 0; j < searched.getData().length; j++) {
                    if (j < searched.getData().length) {
                        joinedEntry[i + j] = searched.getData()[j];
                    }
                }
                list.push(joinedEntry);
            }
        }
        return list;
    }

    public Stack<String[]> leftJoin(Table table2) {
        Table table1 = this;
        Stack<Entry> stack = table1.getValuesStack();
        Entry entry;
        int[] pos = getPrimaryKeyIndexesFromForeignKey(table1, table2);
        Stack<String[]> list = new Stack<>();
        while ((entry = stack.pop()) != null) {
            String[] key = new String[pos.length];
            for (int i = 0; i < pos.length; i++) {
                key[i] = entry.getData()[pos[i]];
            }
            Entry searched = table2.search(key);

            String[] joinedEntry = new String[table1.getFields().length + table2.getFields().length];
            int i;
            for (i = 0; i < entry.getData().length; i++) {
                if (i < entry.getData().length) {
                    joinedEntry[i] = entry.getData()[i];
                }
            }
            if (searched != null) {
                for (int j = 0; j < searched.getData().length; j++) {
                    if (j < searched.getData().length) {
                        joinedEntry[i + j] = searched.getData()[j];
                    }
                }
            }
            list.push(joinedEntry);
        }
        return list;
    }

    public Stack<String[]> rightJoin(Table table2) {
        Table table1 = this;
        JoinTree joinTree = table2.getStructure().buildJoinTree(table1.getFields().length);
        Stack<Entry> stack = table1.getValuesStack();
        Entry entry;
        int[] pos = getPrimaryKeyIndexesFromForeignKey(table1, table2);
        Stack<String[]> list;
        while ((entry = stack.pop()) != null) {
            String[] key = new String[pos.length];
            for (int i = 0; i < pos.length; i++) {
                key[i] = entry.getData()[pos[i]];
            }
            String concatKey = "";
            for (String s : key) {
                concatKey += s + Table.getSEPARATOR();
            }

            Key searchKey = new Key(concatKey);

            joinTree.matchEntry(searchKey, entry);
        }
        list = joinTree.getValuesStack();
        joinTree = null;
        System.gc();
        return list;
    }
}
