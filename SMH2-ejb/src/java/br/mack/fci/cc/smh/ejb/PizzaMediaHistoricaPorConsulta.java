/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.mack.fci.cc.smh.ejb;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author prgoes
 */
@Stateless
public class PizzaMediaHistoricaPorConsulta {
    @EJB
    private ConsultaDAO consultas;
    
    public PizzaDTO executarPorConsulta(String nomeConsulta) {
        PizzaDTO resultado = new PizzaDTO();
        
        //List<Consulta> resultados = consultas.lerPorNome(nomeConsulta);
        
        //for(Consulta c : resultados) {        
            //resultado.adicionar(c);        
        //}
        
        return resultado;
    }
}
