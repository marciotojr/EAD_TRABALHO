package imdb.database.structures.auxiliarTree;

import imdb.database.structures.binarySearchTree.BinarySearchTreeNode;
import imdb.database.structures.chainedList.Stack;
import imdb.database.structures.common.Entry;
import imdb.database.structures.common.Set;

/**
 * Classe que define os nós de uma árvore de Join
 *
 * @author Marcio Júnior
 */
public class JoinTreeNode extends BinarySearchTreeNode {

    /**
     * Altura de um nó
     */
    int height;

    /**
     *
     */
    private Stack<Entry> leftEntryStack;

    public JoinTreeNode(Comparable key, Object value) {
        super(key, value);
        leftEntryStack = new Stack<>();
        height = 0;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Stack<Entry> getLeftEntry() {
        return leftEntryStack;
    }

    public void setLeftEntry(Entry leftEntry) {
        this.leftEntryStack.push(leftEntry);
    }

    public String[][] joinedValues(int leftSize) {
        String[] arr[] = new String[leftEntryStack.getSize()][];
        Entry entry;
        int k = 0;
        while ((entry = leftEntryStack.pop()) != null) {
            String[] leftValues = entry.getData();
            String[] rightValues = ((Entry) ((Set) this.getValue()).getValue()).getData();
            String[] merged = new String[leftValues.length + rightValues.length];
            int i;
            for (i = 0; i < leftValues.length; i++) {
                merged[i] = leftValues[i];
            }
            for (int j = 0; j < rightValues.length; j++) {
                merged[i + j] = rightValues[j];
            }
            arr[k]=merged;
            k++;
        }

        return arr;
    }

    public Stack<Entry> getLeftEntryStack() {
        return leftEntryStack;
    }
    
    

}
