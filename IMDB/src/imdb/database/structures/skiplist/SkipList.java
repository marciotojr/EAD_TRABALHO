/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imdb.database.structures.skiplist;

import imdb.database.structures.Structure;
import imdb.database.structures.common.Entry;

/**
 * Classe que define os atributos da skiplist
 * @author Marcio Júnior
 */
public class SkipList extends Structure {

    /**
     * Primeiro nó da skiplist
     */
    private Node head;
    /**
     * Número de elementos da skiplist
     */
    private int size;
    /**
     * Altura máxima para nós da skiplist
     */
    private static final int maxHeight = 50;

    /**
     * Inicializador da skiplist
     */
    public SkipList() {
        size = 0;
        head = null;
        table = null;
    }

    /**
     * Insere um registro na posição refernciada pela chave
     * @param newEntry Valores do registro a ser inserido
     * @param key Chave única do registro
     * @return verdadeiro se o novo valor foi inserido, falso caso contrário
     */
    private boolean put(Entry newEntry, String key) {
        Node newNode = null;
        if (size == 0) {//cria o primeiro nó da lista
            newNode = new Node(newEntry, this.table);
            this.head = newNode;
            size++;
            return true;
        } else if (head.getKey().compareTo(key) > 0) { //se nova entrada fica no inicio, substitui-se o iniício, e reinsere o antigo início
            Entry reinsertEntry = head.getEntry();
            head.setEntry(newEntry);
            head.setKey(key);
            newNode = new Node(reinsertEntry, this.table);//reinsere o antigo inicio como se fosse o novo nó
        }
        if (newNode == null) {
            newNode = new Node(newEntry, this.table);
        }
        Node[] substitutes = new Node[newNode.getNodes().length];//lista de nós que referenciarão para o novo nó
        if (newNode.getNodes().length > head.getNodes().length) {
            head.addLevels(newNode.getNodes().length);
        }
        Node currentNode = head;
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

    /**
     * Retorna o número de elementos na skiplist
     * @return o número de elementos na skiplist
     */
    @Override
    public int getSize() {
        return size;
    }
    
    /**
     * Insere um registro na posição refernciada pela chave
     * @param entry Valores do registro a ser inserido
     * @return verdadeiro se o novo valor foi inserido, falso caso contrário
     */
    @Override
    public boolean put(String[] entry) {
        String key = table.getEntryKey(entry);
        return put(new Entry(entry), key);
    }

    
    @Override
    public boolean remove(String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Procura um registro com chave igual à chave passada por parâmetro
     * @param key chave de busca
     * @return registro com chave igual à chave passada por parâmetro
     */
    @Override
    public Entry search(String key) {

        Node currentNode = head;
        int i = currentNode.getNodes().length - 1;
        while (currentNode != null && i >= 0) {//percorre a skiplist
            if (currentNode.getNodes()[i] != null && currentNode.getNodes()[i].getKey().compareTo(key) <= 0) {
                currentNode = currentNode.getNodes()[i];
            } else if (currentNode.getKey().equals(key)) {
                return currentNode.getEntry();
            } else {
                i--;
            }
        }
        if (currentNode.getKey().compareTo(key) == 0) {//se chave do registro encontrado igual à de busca
            return currentNode.getEntry();
        }
        return null;
    }
    
    /**
     * Retorna o limite superior do número de referências que um nó faz a outros
     * @return o limite superior do número de referências que um nó faz a outros
     */
    public static int getLimitHeight(){
        return maxHeight;
    }

    /**
     * Retorna o primeiro nó da skiplist
     * @return primeiro nó da skiplist
     */
    public Node getHead() {
        return head;
    }
    
    
}
