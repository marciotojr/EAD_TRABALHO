/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imdb.database.structures.common;

/**
 * Classe que representa um registro, o campo a que cada posição do registro
 * referecnia só pode ser sabido em conjunto com um objeto da classe Table
 * @author Marcio Júnior
 */
public class Entry {

    /**
     * Lista de valores de cada campo do registro
     */
    private String[] data;

    /**
     * Salva os valores de cada campo no registro
     * @param entry Vetor com cada valor de cada campo
     */
    public Entry(String[] entry) {
        data = entry;
    }

    /**
     * Retorna valores do registro
     * @return valores do registro
     */
    public String[] getData() {
        return data;
    }

    /**
     * Retorna uma String com todos os valores do registro concatenados
     * @return String com todos os valores do registro concatenados
     */
    @Override
    public String toString() {
        String returnValue = "";
        for (String s : data) {
            returnValue += "\"" + s + "\" ";
        }
        return returnValue;
    }
}
