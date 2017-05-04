/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imdb.database;

import imdb.database.structures.common.Entry;

/**
 * Classe que define todo o banco de dados e suas tabelas
 * @author Marcio Júnior
 */
public class Database {

    /**
     * Lista de tabelas do banco de dados
     */
    private Table[] tables;

    /**
     * Inicilizador do banco de dados
     */
    public Database() {
        tables = new Table[0];
    }

    /**
     * Insere uma tabela no banco de dados
     * @param table tabela a ser inserida
     * @return verdadeiro se a não há tabela com o mesmo nome e foi inserida, falsa caso contrário
     */
    public boolean addTable(Table table) {
        Table[] newTableArray = new Table[tables.length + 1];
        for (int i = 0; i < tables.length; i++) {
            newTableArray[i] = tables[i];
            if (tables[i].getName().equals(table)) {
                return false;
            }
        }
        newTableArray[tables.length] = table;
        tables = newTableArray;
        return true;
    }

    /**
     * Adiciona um resgitro a uma tabela
     * @param tableName tabela na qual vai se inserir o registro
     * @param entry registro a ser inserido
     * @return verdadeiro se o registro foi inserido, falso caso contrário
     */
    public boolean put(String tableName, String[] entry) {
        for (Table table : tables) {
            if (table.getName().equals(tableName)) {
                return table.put(entry);
            }
        }
        return false;
    }

    /**
     * Retorna os valores do registro procurado que tenha chave igual à chave de busca
     * @param tableName tabela onde o registro será procurado
     * @param keys chave a ser procurada
     * @return os valores do registro procurado que tenha chave igual à chave de busca
     */
    public String[] search(String tableName, String[] keys) {
        for (Table table : tables) {
            if (table.getName().equals(tableName)) {
                Entry entry = table.search(keys);
                if (entry != null) {
                    return entry.getData();
                }
            }
        }
        return null;
    }

    /**
     * Determina as chaves de uma tabela
     * @param tableName tabela a ser modificada
     * @param keys lista de campos que são chave primária
     */
    public void setKeys(String tableName, String[] keys) {
        for (Table table : tables) {
            if (table.getName().equals(tableName)) {
                table.setKeys(keys);
            }
        }
    }

    /**
     * Retorna uma tabela com o nome passado por parâmetro
     * @param name nome da tabela procurada
     * @return tabela com o nome passado por parâmetro
     */
    public Table getTable(String name) {
        for (Table table : tables) {
            if (table.getName().equals(name)) {
                return table;
            }
        }
        return null;
    }
}
