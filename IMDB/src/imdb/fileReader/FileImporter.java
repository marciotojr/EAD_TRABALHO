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

/**
 * Classe responsável pela leitura e importação das tabelas do arquivo para a
 * memória
 *
 * @author Marcio Júnior
 */
public class FileImporter {

    /**
     * Importa informações do arquivo descrito por fileName para o banco de
     * dados
     *
     * @param db banco de dados de destino
     * @param fileName arquivo de origem
     */
    public static void importDatabase(Database db, String fileName) {
        System.out.println("Importando arquivo: "+fileName);
        importTables(db, fileName);
        importValues(db, fileName);
        System.gc();
        System.out.println("Arquivo Importado");
    }

    /**
     * Importa informações da tabela do arquivo descrito por fileName para o
     * banco de dados
     *
     * @param db banco de dados de destino
     * @param fileName arquivo de origem
     */
    private static void importTables(Database dataBase, String fileName) {
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
                    } else if (currentLine.contains("FOREIGN KEY")) {
                        String[] fk = currentLine.substring(currentLine.indexOf('(')+1, currentLine.indexOf(')')).split(",");
                        for(int i=0;i<fk.length;i++){
                            fk[i] = fk[i].trim();
                        }
                        String[] pk = currentLine.substring(currentLine.lastIndexOf('(')+1, currentLine.lastIndexOf(')')).split(",");
                        for(int i=0;i<fk.length;i++){
                            pk[i] = pk[i].trim();
                        }
                        int begin=currentLine.indexOf("REFERENCES");
                        int end=currentLine.lastIndexOf('(');
                        String table = currentLine.substring(currentLine.indexOf(' ', begin), end);
                        table = table.trim();
                        dataBase.getTable(tableName).insertForeignKeys(table, fk);
                        /*ALTER TABLE ONLY nut_data
                        ADD CONSTRAINT nut_data_src_cd_fkey FOREIGN KEY (src_cd) REFERENCES src_cd(src_cd);*/
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

    /**
     * Importa registros do arquivo descrito por fileName para o banco de dados
     *
     * @param db banco de dados de destino
     * @param fileName arquivo de origem
     */
    private static void importValues(Database dataBase, String fileName) {
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

}
