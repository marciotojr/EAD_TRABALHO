/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imdb;

import imdb.database.Database;
import imdb.fileReader.FileImporter;

/**
 * Classe principal
 * @author Marcio Júnior
 */
public class IMDB {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) { 
        Database db = new Database();
        FileImporter.importDatabase(db,"usda.sql");
        String[] key = {"44061" ,"208"};
        db.search("nut_data", key);      
        return;
    }

}
