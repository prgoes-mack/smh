/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.mack.fci.cc.smh.ejb;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 *
 * @author prgoes
 */
//@Remote(IPizzaMediaHistoricaPorConsulta.class)
@Stateless
public class PizzaMediaHistoricaPorConsulta {
    @EJB
    private ConsultaDAO consultas;
    
    //@Override
    public PizzaDTO executarPorConsulta(String nomeConsulta) {
        PizzaDTO resultado = new PizzaDTO();
        
        List<Consulta> resultados = consultas.lerConsultasPorNome(nomeConsulta);
        
        for(Consulta c : resultados) {        
            resultado.adicionar(c);        
        }
        
        return resultado;
    }
}
