/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.mack.fci.cc.smh.ejb;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author prgoes
 */
public class PizzaDTO implements Serializable {
    
    private Map<Double, Integer> resultados;
    private String titulo;
    
    public String getTitulo() { return titulo; }
    public Map<Double, Integer> getResultados() { return resultados; }
    
    public PizzaDTO() {
        resultados = new HashMap<Double, Integer>();
        titulo = "";
    }
    
    public void adicionar(Consulta c) {
        if(titulo.equals("")) {
            titulo = c.getNome();
        }
        
        if(titulo.equals(c.getNome())) {
            resultados.put(c.getResultadoFinal(), resultados.getOrDefault(c.getResultadoFinal(), 0) + 1);            
        }
    }    
}
