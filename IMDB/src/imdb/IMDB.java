/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imdb;

import imdb.database.Database;
import imdb.database.Table;
import imdb.database.structures.common.Entry;
import imdb.database.structures.skiplist.SkipList;
import imdb.fileReader.FileImporter;
import javafx.scene.control.TableCell;

/**
 *
 * @author Marcio Júnior
 */
public class IMDB {

    public static final int iterations = 16000000;
    public static final int items = 2;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*
        int max = 0;
        int sum = 0;
        SkipList sl = new SkipList();
        String[] s1 ={"b","b"};
        sl.put(s1);
        String[] s2 ={"d","d"};
        sl.put(s2);
        String[] s3 ={"c","c"};
        sl.put(s3);
        String[] s4 ={"a","a"};
        sl.put(s4);
        String[] s5 ={"g","g"};
        sl.put(s5);
        String[] s6 ={"h","h"};
        sl.put(s6);
        String[] s7 ={"f","f"};
        sl.put(s7);
        
        int x = "fdfgdfg".compareTo("b");
        System.out.println(sum / iterations + " " + max);
         */

 /*

        String[] fields = {"id","nome","telefone"};
        String[][] input = {{"4","lice","132456789"},{"2","Mariana","132456789"},{"3","Tania","132456789"},{"4","Alice","132456789"}};
        String[] keys = {"id","nome"};
        String[] search = {"4"};
        Table table = new Table("pessoa",fields);
        table.setKeys(keys);
        keys = table.getKeys();
        for(String[] s:input){
            table.put(s);
        }
        Entry entry = table.search(search);
        return;*/
 
 /*
        Database b = new Database();
        String[] camposT1 = {"datasrc_id", "authors", "title", "year", "journal"};
        Table t1 = new Table("data_src", camposT1);
        b.addTable(t1);
        String[] registroT1 = {"D1066", "G.V. Mann", "The Health and Nutritional", "1962", "American Journal of Clinical Nutrition"};
        b.put("data_src", registroT1);
        String[] keys = {"D1066"};
        String[] s = b.search("data_src", keys);
        return;*/
        Database db = new Database();
        FileImporter fi = new FileImporter(db);
        fi.importFile("usda.sql");
        return;
    }

}
