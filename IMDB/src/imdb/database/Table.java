/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imdb.database;

import imdb.database.structures.Structure;
/**
 *
 * @author Marcio Júnior
 */
abstract class Table {
    String[] fields;
    Structure structure;
    
    Table(String[] newFields){
        this.fields=newFields;
    }
}
