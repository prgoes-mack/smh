/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.mack.fci.cc.smh.ejb;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Andrea
 */
public class SerieDTO implements Serializable {
    
    private Map<Date,Double> resultados;
    private String titulo;
    
    public String getTitulo() { return titulo; }
    public Map<Date,Double> getResultados() { return resultados; }
    
  
    
    public SerieDTO() {
        resultados = new HashMap<Date,Double>();
        titulo = "";
    }
    
    public void adicionar(Consulta c) {
        if(titulo.equals("")) {
            titulo = c.getNome();
        }
        
        if(titulo.equals(c.getNome())) {
            resultados.put(c.getDataConsulta(),c.getResultadoFinal());
        }
    }   
    
}
