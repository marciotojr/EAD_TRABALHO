/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imdb.database;

import imdb.database.structures.Structure;
import imdb.database.structures.binarySearchTree.avlTree.AVLTree;
import imdb.database.structures.chainedList.EntryChainedList;
import imdb.database.structures.common.Entry;
import imdb.database.structures.common.Key;
import imdb.database.structures.common.Set;
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
    private final String[] foreignKeyTable;
    private final String[] foreignKeyField;
    /**
     * Lista que indica se o campo em fields[i] faz parte da chave primária ou
     * não
     */
    private final boolean[] keys;
    /**
     * Estrutura onde serão armazenados os registros
     */
    private final Structure<Entry> structure;
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
        structure = new SkipList<>(this);
        this.name = name;
    }

    /**
     * Identifica campos que formam a chave primária da tabela
     *
     * @param keys lista que indica se o campo em fields[i] faz parte da chave
     * primária ou não
     */
    void setKeys(String[] keys) {
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
            key = new Key((String)key);
        }
        return key;
    }

    public void setEntryKey(String[] entry, Set node) {
        String key = "";
        for (int i = 0; i < this.fields.length; i++) {
            if (this.keys[i]) {
                key += entry[i] + SEPARATOR;
            }
        }
        node.setKey(key);
    }

    /**
     * Retona o nome da tabela
     *
     * @return nome da tabela
     */
    String getName() {
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
    public Structure getStructure() {
        return structure;
    }

}
