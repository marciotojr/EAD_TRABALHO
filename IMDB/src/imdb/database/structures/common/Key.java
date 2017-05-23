/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imdb.database.structures.common;

/**
 *
 * @author Marcio Júnior
 */
public class Key implements Comparable<Object> {

    short hash;
    String key;

    public Key(String key) {
        this.key = key;
        this.hash=(short)key.hashCode();
    }
    
    
    
    @Override
    public int compareTo(Object o) {
        if(o instanceof Key){
            Key auxKey = (Key)o;
            if(this.hash!=auxKey.hash){
                return (this.hash<auxKey.hash) ? -1 : ((this.hash==auxKey.hash) ? 0 : 1);
            }else{
                return this.key.compareTo(auxKey.key);
            }
        }return ((Comparable)this).compareTo(o);
    }
    
    @Override
    public String toString(){
        return hash+"||"+key;
    }
    
}
