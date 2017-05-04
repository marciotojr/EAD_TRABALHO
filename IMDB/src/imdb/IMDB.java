/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imdb;

import analise.Analysis;
import imdb.database.Database;
import imdb.fileReader.FileImporter;
import imdb.database.*;

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
        String[] keys = {"id"};
        String[] search = {"4"};
        Table table = new Table("pessoa",fields);
        table.setKeys(keys);
        keys = table.getKeys();
        Database db = new Database();
        db.addTable(table);
        
        for(String[] s:input){
            db.put("pessoa", s);
        }
        String[] s = db.search("pessoa",search);
        return;
 
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
        String[] key = {"44061" ,"208"};
        db.search("nut_data", key);/*
        Analysis analysis = new Analysis(db, "nut_data");
        //int[] nodePerLevel = analysis.nodesPerLevel();
        //double[] percentagePerLevel = analysis.percentagePerLevel();
        /*String x = "";
        String y = "";
        for(int i = 10; i<analysis.getSize();i+=2000){
            long v = analysis.insertionTime(i);
            x+=i+",";
            y+=v+",";
        }
        System.out.println(x);
        System.out.println(y);*/
        /*
        String x = "";
        String y = "";
        for(int i = 100; i<analysis.getSize();i+=2500){
            long sum =0;
            for(int k=0;k<20;k++){
                sum+= analysis.completeInsertionTime(i);
            }
            x+=i+",";
            y+=(double)sum/(20000000)+",";
            System.out.println(i+"\t"+(double)sum/(20000000));
        }
        System.out.println(x);
        System.out.println(y);*/
        /*
        String x = "";
        String y = "";
        for(int i=500;i<analysis.getSize();i+=2500){
            long sum =0;
            for(int k=0;k<20;k++){
                sum+= analysis.insertionTimePerItem(i);
            }
            x+=i+",";
            y+=(double)sum/(20)+",";
            System.out.println(i+"\t"+(double)sum/(20000000));
        }
        System.out.println(x);
        System.out.println(y);*/
        
        //analysis.printSearchTimePerItem();
        
        
        return;
    }

}
