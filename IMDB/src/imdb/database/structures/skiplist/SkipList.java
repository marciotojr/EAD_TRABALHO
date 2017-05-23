/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imdb.database.structures.skiplist;

import imdb.database.Table;
import imdb.database.structures.Structure;

/**
 * Classe que define os atributos da skiplist
 * @author Marcio Júnior
 */
public class SkipList<E> extends Structure {

    /**
     * Primeiro nó da skiplist
     */
    private SkipListNode head;

    /**
     * Altura máxima para nós da skiplist
     */
    private static final int MAXHEIGHT = 50;

    /**
     * Inicializador da skiplist
     * @param table Tabela cujas entradas a skiplist guarda
     */
    public SkipList(Table table) {
        super(table);
        this.size = 0;
        this.head = null;
        this.table = table;
    }

    /**
     * Insere um registro na posição refernciada pela chave
     * @param value Valores do registro a ser inserido
     * @return verdadeiro se o novo valor foi inserido, falso caso contrário
     */
    @Override
    public boolean put(Object value) {
        SkipListNode newNode = null;
        Comparable key = table.getEntryKey(value);
        if (size == 0) {//cria o primeiro nó da lista
            this.head = new SkipListNode(key, value, this.table);
            size++;
            return true;
        } else if (head.getKey().compareTo(key) > 0) { //se nova entrada fica no inicio, substitui-se o iniício, e reinsere o antigo início
            Object reinsertEntry = head.getValue();
            head.setValue(value);
            head.setKey(key);
            newNode = new SkipListNode(table.getEntryKey(reinsertEntry), reinsertEntry, this.table);//reinsere o antigo inicio como se fosse o novo nó
        }
        if (newNode == null) {
            newNode = new SkipListNode(key, value, this.table);
        }
        SkipListNode[] substitutes = new SkipListNode[newNode.getNodes().length];//lista de nós que referenciarão para o novo nó
        if (newNode.getNodes().length > head.getNodes().length) {
            head.addLevels(newNode.getNodes().length);
        }
        SkipListNode currentNode = head;
        for (int i = head.getNodes().length - 1; i >= 0; i--) {//caminha na skiplist
            while (currentNode.getNodes()[i] != null && currentNode.getNodes()[i].getKey().compareTo(key) <= 0) {
                currentNode = currentNode.getNodes()[i];
            }
            if (i < substitutes.length) {
                substitutes[i] = currentNode;
            }
        }
        if (substitutes[0].compareTo(newNode) == 0) {
            return false;
        }
        for (int i = 0; i < substitutes.length; i++) {//rearranjo de referências
            newNode.getNodes()[i] = substitutes[i].getNodes()[i];
            substitutes[i].getNodes()[i] = newNode;
        }
        size++;
        return true;
    }
    
    @Override
    public boolean remove(Comparable key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Procura um registro com chave igual à chave passada por parâmetro
     * @param key chave de busca
     * @return registro com chave igual à chave passada por parâmetro
     */
    @Override
    public Object search(Comparable key) {

        SkipListNode currentNode = head;
        int i = currentNode.getNodes().length - 1;
        while (currentNode != null && i >= 0) {//percorre a skiplist
            if (currentNode.getNodes()[i] != null && currentNode.getNodes()[i].getKey().compareTo(key) <= 0) {
                currentNode = currentNode.getNodes()[i];
            } else if (currentNode.getKey().equals(key)) {
                return currentNode.getValue();
            } else {
                i--;
            }
        }
        if (currentNode.getKey().compareTo(key) == 0) {//se chave do registro encontrado igual à de busca
            return currentNode.getValue();
        }
        return null;
    }
    
    /**
     * Retorna o limite superior do número de referências que um nó faz a outros
     * @return o limite superior do número de referências que um nó faz a outros
     */
    public static int getLimitHeight(){
        return MAXHEIGHT;
    }

    /**
     * Retorna o primeiro nó da skiplist
     * @return primeiro nó da skiplist
     */
    public SkipListNode getHead() {
        return head;
    }

    @Override
    public boolean remove(Object key) {
        return this.remove((Comparable)key);
    }
    
    
}
