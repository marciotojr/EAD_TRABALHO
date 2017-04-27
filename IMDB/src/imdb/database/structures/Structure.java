/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imdb.database.structures;

/**
 *
 * @author Marcio Júnior
 */
public abstract class Structure {
    public abstract boolean put(String[] entry);
    public abstract boolean remove(String key);
    public boolean remove(String[] entry){
        return this.remove(entry[0]);
    }
    public abstract String[] search(String key);
    
}
