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
public class Entry {

    String key;
    String[] data;

    public Entry(String[] entry) {
        key = entry[0];
        data = new String[entry.length - 1];
        for (int i = 1; i < entry.length; i++) {
            data[i-1]=entry[i];
        }
    }

    public String getKey() {
        return key;
    }

    public String[] getData() {
        return data;
    }
    
    public String toString(){
        String returnValue = "\""+this.key+"\" ";
        for(String s:data){
            returnValue+="\""+s+"\" ";
        }
        return returnValue;
    }
}
