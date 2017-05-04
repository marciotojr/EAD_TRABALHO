/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imdb.fileReader;

import imdb.database.Database;
import imdb.database.Table;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import static javax.script.ScriptEngine.FILENAME;

/**
 *
 * @author Marcio Júnior
 */
public class FileImporter {

    Database dataBase;

    public FileImporter(Database db) {
        this.dataBase = db;
    }

    private void importTables(String fileName) {
        BufferedReader br = null;
        FileReader fr = null;

        try {

            fr = new FileReader(fileName);

            String currentLine;

            br = new BufferedReader(new FileReader(fileName));

            while ((currentLine = br.readLine()) != null) {
                if (currentLine.contains("CREATE TABLE")) {
                    String tableName = currentLine.trim().split(" ")[2];
                    ArrayList<String> fields = new ArrayList<>();
                    while (((currentLine = br.readLine()) != null) && !currentLine.contains(");")) {
                        fields.add(currentLine.trim().split(" ")[0].replace("\"", ""));
                    }
                    String[] fieldsArray = new String[fields.size()];
                    for (int i = 0; i < fields.size(); i++) {
                        fieldsArray[i] = fields.get(i);
                    }
                    dataBase.addTable(new Table(tableName, fieldsArray));
                } else if (currentLine.contains("ALTER TABLE ONLY")) {
                    String tableName = currentLine.trim().split(" ")[3];
                    ArrayList<String> fields = new ArrayList<>();
                    if ((currentLine = br.readLine()) != null && currentLine.contains("PRIMARY KEY")) {
                        currentLine = currentLine.trim();
                        currentLine = currentLine.substring(currentLine.lastIndexOf('(') + 1, currentLine.lastIndexOf(')'));
                        String[] keys = currentLine.split(",");
                        for (int i = 0; i < keys.length; i++) {
                            keys[i] = keys[i].trim();
                        }
                        dataBase.setKeys(tableName, keys);
                    }
                }
            }

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (br != null) {
                    br.close();
                }

                if (fr != null) {
                    fr.close();
                }

            } catch (IOException ex) {

                ex.printStackTrace();

            }

        }

    }

    private void importValues(String fileName) {
        BufferedReader br = null;
        FileReader fr = null;

        try {

            fr = new FileReader(fileName);

            String currentLine;

            br = new BufferedReader(new FileReader(fileName));

            while ((currentLine = br.readLine()) != null) {
                if (currentLine.contains("COPY")) {
                    String tableName = currentLine.trim().split(" ")[1];
                    while (((currentLine = br.readLine()) != null) && !currentLine.contains("\\.")) {
                        //System.out.println(currentLine);
                        String[] entry = currentLine.split("\t");
                        dataBase.put(tableName, entry);
                    }                   
                }
            }

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (br != null) {
                    br.close();
                }

                if (fr != null) {
                    fr.close();
                }

            } catch (IOException ex) {

                ex.printStackTrace();

            }

        }

    }

    public void importFile(String fileName){
        this.importTables(fileName);
        this.importValues(fileName);
    }
}
