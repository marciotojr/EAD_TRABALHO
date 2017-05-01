/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imdb.database.structures.common;

import imdb.database.Table;

/**
 *
 * @author Marcio Júnior
 */
public class Entry {

    private String[] data;

    public Entry(String[] entry) {
        data = new String[entry.length];
        for (int i = 0; i < entry.length; i++) {
            data[i] = entry[i];
        }
    }

    public String[] getData() {
        return data;
    }

    @Override
    public String toString() {
        String returnValue = "";
        for (String s : data) {
            returnValue += "\"" + s + "\" ";
        }
        return returnValue;
    }
}
