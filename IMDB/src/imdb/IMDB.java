/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imdb;

import analysis.AVLAnalysis;
import analysis.Analysis;
import analysis.SkipListAnalysis;
import imdb.database.Database;
import imdb.database.Table;
import imdb.database.structures.binarySearchTree.avlTree.AVLNode;
import imdb.database.structures.chainedList.Stack;
import imdb.fileReader.FileImporter;
import java.util.Scanner;

/**
 * Classe principal
 *
 * @author Marcio Júnior
 */
public class IMDB {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Database db = new Database();
        FileImporter.importDatabase(db, "usda.sql");
        Table t1 = db.getTable("footnote");
        Table t2 = db.getTable("nutr_def");
        boolean repeat = true;
        while (repeat) {
            System.out.println("\nSelecione qual operação deseja realizar:");
            System.out.println("1. Buscar registro");
            System.out.println("2. Inserir registro");
            System.out.println("3. Remover registro");
            System.out.println("4. Realizar INNER JOIN");
            System.out.println("5. Realizar LEFT OUTER JOIN");
            System.out.println("6. Realizar RIGHT OUTER JOIN");
            System.out.println("7. Contagem");
            System.out.println("8. Contagem por campo");
            System.out.println("0. Sair");
            Scanner scanner = new Scanner(System.in);
            String op = scanner.nextLine();
            switch (op) {
                case "1":
                    searchEntry(db);
                    break;
                case "2":
                    insertEntry(db);
                    break;
                case "3":
                    removeEntry(db);
                    break;
                case "4":
                    innerJoin(db);
                    break;
                case "5":
                    leftJoin(db);
                    break;
                case "6":
                    rightJoin(db);
                    break;
                case "7":
                    count(db);
                    break;
                case "8":
                    countField(db);
                    break;
                case "0":
                    repeat = false;
                    break;
                default:
                    repeat = true;
            }
        }
        return;
    }

    private static void insertEntry(Database db) {
        boolean repeat = true;
        while (repeat) {
            System.out.println("\nDigite o nome da tabela que deseja buscar:");
            Scanner scanner = new Scanner(System.in);
            String tableName = scanner.nextLine();
            Table table = db.getTable(tableName);
            if (table == null) {
                System.out.println("Tabela não encontrada. Deseja continuar? S/n");
                String answer = scanner.nextLine();
                if (answer != null && answer.equals("n")) {
                    break;
                } else {
                    continue;
                }
            }
            String[] fields = new String[table.getFields().length];
            for (int i = 0; i < fields.length; i++) {
                System.out.println("Insira um valor para o campo " + table.getFields()[i]);
                fields[i] = scanner.nextLine();
            }
            if (db.put(tableName, fields)) {
                System.out.println("Registro inserido com sucesso!");
            } else {
                System.out.println("Registro não encontrado!");
            }
            System.out.println("Deseja continuar realizando mais operações de inserção? s/N");
            if (!scanner.nextLine().equals("s")) {
                break;
            }
        }
    }

    private static void removeEntry(Database db) {
        while (true) {
            System.out.println("\nDigite o nome da tabela que deseja buscar:");
            Scanner scanner = new Scanner(System.in);
            String tableName = scanner.nextLine();
            Table table = db.getTable(tableName);
            if (table == null) {
                System.out.println("Tabela não encontrada. Deseja continuar? S/n");
                String answer = scanner.nextLine();
                if (answer != null && answer.equals("n")) {
                    break;
                } else {
                    continue;
                }
            }
            String[] fields = new String[table.getKeys().length];
            for (int i = 0; i < fields.length; i++) {
                System.out.println("Insira um valor para o campo " + table.getFields()[i]);
                fields[i] = scanner.nextLine();
            }
            if (db.search(tableName, fields) != null) {
                db.remove(tableName, fields);
                System.out.println("Registro removido com sucesso!");
            } else {
                System.out.println("Registro não encontrado!");
            }

            System.out.println("Deseja continuar realizando mais operações de remoção? s/N");
            if (!scanner.nextLine().equals("s")) {
                break;
            }
        }
    }

    private static void innerJoin(Database db) {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("\nDigite o nome da tabela contendo as chaves estrangeiras");
            String tableName = scanner.nextLine();
            Table table1 = db.getTable(tableName);
            if (table1 == null) {
                System.out.println("Tabela não encontrada. Deseja continuar? S/n");
                String answer = scanner.nextLine();
                if (answer != null && answer.equals("n")) {
                    break;
                } else {
                    continue;
                }
            }
            System.out.println("\nDigite o nome da tabela contendo as chaves primárias");
            tableName = scanner.nextLine();
            Table table2 = db.getTable(tableName);
            if (table2 == null) {
                System.out.println("Tabela não encontrada. Deseja continuar? S/n");
                String answer = scanner.nextLine();
                if (answer != null && answer.equals("n")) {
                    break;
                } else {
                    continue;
                }
            }
            Stack<String[]> result = db.innerJoin(table1, table2);
            System.err.println("Imprimindo INNER JOIN de " + table1.getName() + " e " + table2.getName());
            String[] s;
            int size = result.getSize();
            while ((s = result.pop()) != null) {
                String line = "";
                for (String a : s) {
                    line += a + "\t";
                }
                System.out.println(line);
            }
            System.out.println("\nA operação retornou " + size + " valores");
            System.out.println("Deseja continuar realizando mais operações INNER JOIN? s/N");
            if (!scanner.nextLine().equals("s")) {
                break;
            }
        }
    }

    private static void leftJoin(Database db) {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("\nDigite o nome da tabela contendo as chaves estrangeiras");
            String tableName = scanner.nextLine();
            Table table1 = db.getTable(tableName);
            if (table1 == null) {
                System.out.println("Tabela não encontrada. Deseja continuar? S/n");
                String answer = scanner.nextLine();
                if (answer != null && answer.equals("n")) {
                    break;
                } else {
                    continue;
                }
            }
            System.out.println("\nDigite o nome da tabela contendo as chaves primárias");
            tableName = scanner.nextLine();
            Table table2 = db.getTable(tableName);
            if (table2 == null) {
                System.out.println("Tabela não encontrada. Deseja continuar? S/n");
                String answer = scanner.nextLine();
                if (answer != null && answer.equals("n")) {
                    break;
                } else {
                    continue;
                }
            }
            Stack<String[]> result = db.leftJoin(table1, table2);
            System.err.println("Imprimindo LEFT OUTER JOIN de " + table1.getName() + " e " + table2.getName());
            String[] s;
            int size = result.getSize();
            while ((s = result.pop()) != null) {
                String line = "";
                for (String a : s) {
                    line += a + "\t";
                }
                System.out.println(line);
            }
            System.out.println("\nA operação retornou " + size + " valores");
            System.out.println("Deseja continuar realizando mais operações de OUTER LEFT JOIN? s/N");
            if (!scanner.nextLine().equals("s")) {
                break;
            }
        }
    }

    private static void rightJoin(Database db) {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("\nDigite o nome da tabela contendo as chaves estrangeiras");
            String tableName = scanner.nextLine();
            Table table1 = db.getTable(tableName);
            if (table1 == null) {
                System.out.println("Tabela não encontrada. Deseja continuar? S/n");
                String answer = scanner.nextLine();
                if (answer != null && answer.equals("n")) {
                    break;
                } else {
                    continue;
                }
            }
            System.out.println("\nDigite o nome da tabela contendo as chaves primárias");
            tableName = scanner.nextLine();
            Table table2 = db.getTable(tableName);
            if (table2 == null) {
                System.out.println("Tabela não encontrada. Deseja continuar? S/n");
                String answer = scanner.nextLine();
                if (answer != null && answer.equals("n")) {
                    break;
                } else {
                    continue;
                }
            }
            Stack<String[]> result = db.rightJoin(table1, table2);
            System.err.println("Imprimindo RIGHT OUTER JOIN de " + table1.getName() + " e " + table2.getName());
            String[] s;
            int size = result.getSize();
            while ((s = result.pop()) != null) {
                String line = "";
                for (String a : s) {
                    line += a + "\t";
                }
                System.out.println(line);
            }
            System.out.println("\nA operação retornou " + size + " valores");
            System.out.println("Deseja continuar realizando mais operações de RIGHT OUTER JOIN? s/N");
            if (!scanner.nextLine().equals("s")) {
                break;
            }
        }
    }

    private static void searchEntry(Database db) {
        while (true) {
            System.out.println("\nDigite o nome da tabela que contém o item que deseja buscar:");
            Scanner scanner = new Scanner(System.in);
            String tableName = scanner.nextLine();
            Table table = db.getTable(tableName);
            if (table == null) {
                System.out.println("Tabela não encontrada. Deseja continuar? S/n");
                String answer = scanner.nextLine();
                if (answer != null && answer.equals("n")) {
                    break;
                } else {
                    continue;
                }
            }
            String[] fields = new String[table.getKeys().length];
            for (int i = 0; i < fields.length; i++) {
                System.out.println("Insira um valor para o campo " + table.getFields()[i]);
                fields[i] = scanner.nextLine();
            }
            String[] entry = db.search(tableName, fields);
            if (entry != null) {
                String line = "";
                for (String s : entry) {
                    line += s + "\t";
                }
                System.out.println(line);
            } else {
                System.out.println("Nenhum registro encontrado");
            }
            System.out.println("Deseja continuar realizando mais operações de busca? s/N");
            if (!scanner.nextLine().equals("s")) {
                break;
            }
        }
    }

    private static void count(Database db) {
        boolean repeat = true;
        while (repeat) {
            System.out.println("\nDigite o nome da tabela que deseja buscar:");
            Scanner scanner = new Scanner(System.in);
            String tableName = scanner.nextLine();
            Table table = db.getTable(tableName);
            if (table == null) {
                System.out.println("Tabela não encontrada. Deseja continuar? S/n");
                String answer = scanner.nextLine();
                if (answer != null && answer.equals("n")) {
                    break;
                } else {
                    continue;
                }
            }
            System.out.println("A tabela " + tableName + " tem " + table.count() + " registros");
            System.out.println("Deseja continuar realizando mais operações de contagem? s/N");
            if (!scanner.nextLine().equals("s")) {
                break;
            }
        }
    }

    private static void countField(Database db) {
        boolean repeat = true;
        while (repeat) {
            System.out.println("\nDigite o nome da tabela que deseja buscar:");
            Scanner scanner = new Scanner(System.in);
            String tableName = scanner.nextLine();
            Table table = db.getTable(tableName);
            if (table == null) {
                System.out.println("Tabela não encontrada. Deseja continuar? S/n");
                String answer = scanner.nextLine();
                if (answer != null && answer.equals("n")) {
                    break;
                } else {
                    continue;
                }
            }

            boolean find = false;
            do {
                System.out.println("Digite o campo");
                String field = scanner.nextLine();
                for (int i = 0; i < table.getFields().length; i++) {
                    if (table.getFields()[i].equals(field)) {
                        find = true;
                        System.out.println("Digite o valor");
                        String value = scanner.nextLine();
                        System.out.println("Foram encontrados " + table.count(field, value) + " campos " + field + " com valor " + value);
                    }
                }
                if (!find) {
                    System.out.println("Campo não encontrado\nDeseja tentar com outro campo? S/n");
                    if (!scanner.nextLine().equals("n")) {
                        break;
                    }
                }
            } while (!find);

            System.out.println("Deseja continuar realizando mais operações de contagem? s/N");
            if (!scanner.nextLine().equals("s")) {
                break;
            }
        }
    }

}
