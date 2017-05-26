package imdb.database.structures.skiplist;

import imdb.database.Table;
import imdb.database.structures.DatabaseStructure;
import imdb.database.structures.auxiliarTree.JoinTree;
import imdb.database.structures.chainedList.Queue;
import imdb.database.structures.chainedList.Stack;
import imdb.database.structures.common.Entry;
import imdb.database.structures.common.Set;

/**
 * Classe que define os atributos da skiplist
 *
 * @author Marcio Júnior
 * @param <E>
 */
public class SkipList<E> extends DatabaseStructure {

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
     *
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
     *
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
            head.setKey(table.getEntryKey(value));
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
        if (size == 0) {//cria o primeiro nó da lista
            return false;
        } else if ((head.getKey().compareTo(key) > 0)) {
            return false;
        } else if (head.getKey().compareTo(key) == 0) { //se nova entrada fica no inicio, substitui-se o iniício, e reinsere o antigo início
            head.setKey(head.getNodes()[0].getKey());
            head.setValue((E)head.getNodes()[0].getValue());
            for (int i = head.getNodes()[0].getNodes().length-1; i>=0; i--) {
                head.getNodes()[i] = head.getNodes()[0].getNodes()[i];
            }
            size--;
            return true;
        }
        SkipListNode[] substitutes = new SkipListNode[head.getNodes().length];//lista de nós que referenciarão para o novo nó
        SkipListNode currentNode = head;
        for (int i = head.getNodes().length - 1; i >= 0; i--) {//caminha na skiplist
            while (currentNode.getNodes()[i] != null && currentNode.getNodes()[i].getKey().compareTo(key) <= 0) {
                currentNode = currentNode.getNodes()[i];
            }
            if (i < substitutes.length) {
                substitutes[i] = currentNode;
            }
        }
        currentNode=currentNode.getNodes()[0];
        if (substitutes[0].compareTo(key) == 0) {
            for (int i = substitutes[0].getNodes().length-1; i >=0 ; i--) {//rearranjo de referências
                substitutes[i].getNodes()[i]=substitutes[i].getNodes()[i].getNodes()[i];
            }
        } else {
            return false;
        }

        return false;
    }

    /**
     * Procura um registro com chave igual à chave passada por parâmetro
     *
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
     *
     * @return o limite superior do número de referências que um nó faz a outros
     */
    public static int getLimitHeight() {
        return MAXHEIGHT;
    }

    /**
     * Retorna o primeiro nó da skiplist
     *
     * @return primeiro nó da skiplist
     */
    public SkipListNode getHead() {
        return head;
    }

    @Override
    public boolean remove(Object key) {
        return this.remove((Comparable) key);
    }

    @Override
    public Object[] getValuesArray() {
        Object[] list = new Object[size];
        int i = 0;
        SkipListNode node = head;
        while (node != null) {
            list[i] = node.getValue();
            i++;
            node = node.getNodes()[0];
        }
        return list;
    }

    @Override
    public Stack getValuesStack() {
        Stack<E> list = new Stack();
        int i = 0;
        SkipListNode node = head;
        while (node != null) {
            list.push((E) node.getValue());
            i++;
            node = node.getNodes()[0];
        }
        return list;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public JoinTree buildJoinTree(int leftSize) {
        JoinTree<Set> tree = new JoinTree(table, leftSize);
        for (int i = this.head.getNodes().length - 1; i > 0; i--) {
            Queue<SkipListNode> queue = new Queue();
            queue.push(this.head);
            SkipListNode node;
            while ((node = queue.pop()) != null) {
                Set set = (Set) node;
                tree.insert(set);
                queue.push(node.getNodes()[i]);
            }
        }
        return tree;
    }

    @Override
    public int count(int field, String value) {
        int count = 0;
        SkipListNode node = this.head;
        while (node != null) {
            if (((Entry) node.getValue()).getData()[field].equals(node)) {
                count++;
            }
            node = node.getNodes()[0];
        }
        return count;
    }
}
