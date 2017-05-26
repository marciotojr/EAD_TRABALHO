package imdb.database.structures.auxiliarTree;

import imdb.database.Table;
import imdb.database.structures.DatabaseStructure;
import imdb.database.structures.chainedList.Stack;
import imdb.database.structures.common.Entry;
import imdb.database.structures.common.Key;
import imdb.database.structures.common.Set;
import java.util.ArrayList;

/**
 * Classe que define atributos e métodos da árvore que guarda os elementos da
 * tabela da direita em um right join, sendo esta uma AVL
 *
 * @author Marcio Júnior
 * @param <E>
 */
public class JoinTree<E> extends DatabaseStructure {

    private int leftSize;

    /**
     * Raiz da árvore de Join
     */
    private JoinTreeNode root;

    /**
     * Inicializador da árvore de Join
     *
     * @param table Tabela da direita de um Right Join
     */
    public JoinTree(Table table) {
        super(table);
        root = null;
    }

    public JoinTree(Table table, int leftSize) {
        super(table);
        this.leftSize = leftSize;
        root = null;
    }

    /**
     * Insere um valor na árvore de Join
     *
     * @param value Valor a ser inserido na AVL
     */
    public void insert(E value) {
        root = insert(value, root);
    }

    /**
     * Insere o elemento da tabela da esquerda do Right Join, buscando seu
     * elemento na tabela da direita
     *
     * @param key Chave do elemento da tabela da esquerda do Right Join
     * @param entry Valor do elemento da tabela da esquerda do Right Join
     */
    public void matchEntry(Key key, Entry entry) {
        matchEntry(key, entry, root);
    }

    /**
     * Não implementado, a árvore de Join não tem motivos para seus elementos
     * serem apagados
     *
     * @param key
     * @return
     */
    @Override
    public boolean remove(Comparable key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Insere um elemento da tabela direita do Right Join
     *
     * @param value Valor do elemento da tabela direita do Right Join
     * @param node Nó da comparação atual
     * @return Raiz da árvore corrigida
     */
    private JoinTreeNode insert(E value, JoinTreeNode node) {
        Comparable key = table.getEntryKey(value);
        if (node == null) {
            node = new JoinTreeNode(key, value);
            size++;
        } else if (key.compareTo(node.getKey()) < 0) {
            node.setLeft(insert(value, (JoinTreeNode) node.getLeft()));
            size++;
            if (Math.abs(height((JoinTreeNode) node.getLeft()) - height((JoinTreeNode) node.getRight())) == 2) {
                if (key.compareTo(node.getLeft().getKey()) < 0) {
                    node = leftRotation(node);
                } else {
                    node = rightLeftRotation(node);
                }
            }
        } else if (key.compareTo(node.getKey()) > 0) {
            node.setRight(insert(value, (JoinTreeNode) node.getRight()));
            size++;
            if (Math.abs(height((JoinTreeNode) node.getRight()) - height((JoinTreeNode) node.getLeft())) == 2) {
                if (key.compareTo(node.getRight().getKey()) > 0) {
                    node = rightRotation(node);
                } else {
                    node = leftRightRotation(node);
                }
            }
        }
        node.setHeight(Math.max(height((JoinTreeNode) node.getLeft()), height((JoinTreeNode) node.getRight())) + 1);
        return node;
    }

    /**
     * Retorna a altura de um nó
     *
     * @param node Nó a ter sua altura obtida
     * @return Altura de um nó
     */
    private static int height(JoinTreeNode node) {
        return node == null ? -1 : node.height;
    }

    /**
     * Rotaciona pai com filho à esquerda
     *
     * @param node Nó pai
     * @return Novo pai
     */
    private static JoinTreeNode leftRotation(JoinTreeNode node) {
        JoinTreeNode leftChild = (JoinTreeNode) node.getLeft();
        node.setLeft(leftChild.getRight());
        leftChild.setRight(node);
        node.setHeight(Math.max(height((JoinTreeNode) node.getLeft()), height((JoinTreeNode) node.getRight())) + 1);
        leftChild.setHeight(Math.max(height((JoinTreeNode) leftChild.getLeft()), node.height) + 1);
        return leftChild;
    }

    /**
     * Rotaciona pai com filho à direita
     *
     * @param node Nó pai
     * @return Novo pai
     */
    private static JoinTreeNode rightRotation(JoinTreeNode node) {
        JoinTreeNode rightChild = (JoinTreeNode) node.getRight();
        node.setRight(rightChild.getLeft());
        rightChild.setLeft(node);
        node.setHeight(Math.max(height((JoinTreeNode) node.getLeft()), height((JoinTreeNode) node.getRight())) + 1);
        rightChild.setHeight(Math.max(height((JoinTreeNode) rightChild.getRight()), node.height) + 1);
        return rightChild;
    }

    /**
     * Rotaciona pai com filho à esquerda e neto à direita
     *
     * @param node Nó pai
     * @return Novo pai
     */
    private static JoinTreeNode rightLeftRotation(JoinTreeNode node) {
        node.setLeft(rightRotation((JoinTreeNode) node.getLeft()));
        return leftRotation(node);
    }

    /**
     * Rotaciona pai com filho à direita e neto à esquerda
     *
     * @param node Nó pai
     * @return Novo pai
     */
    private static JoinTreeNode leftRightRotation(JoinTreeNode node) {
        node.setRight(leftRotation((JoinTreeNode) node.getRight()));
        return rightRotation(node);
    }

    /**
     * Insere elemento da tabela da direita do Right Join na tabela
     *
     * @param entry ELemento da tabela da esquerda
     * @return Como toda inserção deve ser válida, já que a tabela da direita
     * não tem chaves iguais, retorna sempre verdadeiro
     */
    @Override
    public boolean put(Object entry) {
        root = insert((E) entry, root);
        size++;
        return true;
    }

    /**
     * Não implementado, a árvore de Join não tem motivos para seus elementos
     * serem apagados
     *
     * @param entry
     * @return
     */
    @Override
    public boolean remove(Object entry) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Busca elemento pela chave primaria da tabela da direita do Right Join
     *
     * @param key Chave de busca
     * @return
     */
    @Override
    public Object search(Comparable key) {
        return search(key, root);
    }

    /**
     * Busca elemento pela chave primaria da tabela da direita do Right Join
     *
     * @param key Chave de busca
     * @param root Raiz da árvore de Join
     * @return
     */
    public static Object search(Comparable key, JoinTreeNode root) {
        if (root != null) {
            int compare = root.getKey().compareTo(key);
            if (compare == 0) {
                return root.getValue();
            } else if (compare > 0) {
                return search(key, (JoinTreeNode) root.getLeft());
            } else if (compare < 0) {
                return search(key, (JoinTreeNode) root.getRight());
            }
        }
        return null;
    }

    /**
     * Retorna valores da árvore em um array
     *
     * @return Valores da árvore em um array
     */
    @Override
    public Object[] getValuesArray() {
        return getAllValuesArray(this.root);
    }

    /**
     * Retorna valores da árvore em uma pilha
     *
     * @return Valores da árvore em uma pilha
     */
    @Override
    public Stack getValuesStack() {
        return getAllValuesStack(this.root, this.leftSize);
    }

    /**
     * Constróia array com todos os valores a partir de uma raiz
     *
     * @param root Raiz
     * @return Array com todos os valores a partir de uma raiz
     */
    private static Object[] getAllValuesArray(JoinTreeNode root) {
        ArrayList list = new ArrayList();
        Stack<JoinTreeNode> stack = new Stack();
        stack.push(root);
        JoinTreeNode current;
        while ((current = stack.pop()) != null) {
            list.add(current.getValue());
            stack.push((JoinTreeNode) root.getLeft());
            stack.push((JoinTreeNode) root.getRight());
        }
        return list.toArray();
    }

    /**
     * Constróia pilha com todos os valores a partir de uma raiz
     *
     * @param root Raiz
     * @return Pilha com todos os valores a partir de uma raiz
     */
    private static Stack<String[]> getAllValuesStack(JoinTreeNode node, int leftSize) {
        Stack<String[]> list = new Stack();
        Stack<JoinTreeNode> stack = new Stack();
        stack.push(node);
        JoinTreeNode current;

        while ((current = stack.pop()) != null) {
            if (current.getLeftEntryStack().getSize() == 0) {
                String[] entry = ((Entry) ((Set) current.getValue()).getValue()).getData();
                String[] complete = new String[leftSize + entry.length];
                int i;
                for (i = 0; i < leftSize; i++) {
                    complete[i] = "";
                }
                for (int j = 0; j < entry.length; j++) {
                    complete[i + j] = entry[j];
                }
                list.push(complete);
            } else {
                Entry left;
                while ((left = current.getLeftEntryStack().pop()) != null) {
                    String[] entry = ((Entry) ((Set) current.getValue()).getValue()).getData();
                    String[] leftData = left.getData();
                    String[] complete = new String[leftData.length + entry.length];
                    int i;
                    for (i = 0; i < leftSize; i++) {
                        complete[i] = leftData[i];
                    }
                    for (int j = 0; j < entry.length; j++) {
                        complete[i + j] = entry[j];
                    }
                    list.push(complete);
                }
            }
            stack.push((JoinTreeNode) current.getLeft());
            stack.push((JoinTreeNode) current.getRight());
        }
        return list;
    }

    /**
     * Insere o elemento da tabela da esquerda do Right Join, buscando seu
     * elemento na tabela da direita
     *
     * @param key Chave do elemento da tabela da esquerda do Right Join
     * @param entry Valor do elemento da tabela da esquerda do Right Join
     * @param root Raiz da árvore de Join
     */
    private boolean matchEntry(Key key, Entry entry, JoinTreeNode root) {
        if (root != null) {
            Comparable comparable = table.getEntryKey(root);
            int compare = comparable.compareTo(key);
            if (compare == 0) {
                root.setLeftEntry(entry);
                return true;
            } else if (compare > 0) {
                return matchEntry(key, entry, (JoinTreeNode) root.getLeft());
            } else if (compare < 0) {
                return matchEntry(key, entry, (JoinTreeNode) root.getRight());
            }
        }
        return false;
    }

    /**
     * Constrói a árvore de Join de uma estrutura
     *
     * @return
     */
    @Override
    public JoinTree buildJoinTree(int leftSize) {
        this.leftSize=leftSize;
        return this;
    }

    /**
     * Retorna a String que identifica a estrutura
     *
     * @return String que identifica a estrutura
     */
    @Override
    public String toString() {
        return String.valueOf(this.size);
    }

    @Override
    public int count(int field, String value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
