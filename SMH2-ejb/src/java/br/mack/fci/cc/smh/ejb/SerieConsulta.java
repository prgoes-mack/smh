/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.mack.fci.cc.smh.ejb;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.Remote;

/**
 *
 * @author Andrea
 */
//@Remote(ISerieConsulta.class)
@Stateless
public class SerieConsulta {
    @EJB
    private ConsultaDAO consultas;

    //@Override
    public SerieDTO  executarSerieConsulta(String nomeConsulta) {
        SerieDTO resultado = new SerieDTO();
        
        List<Consulta> resultados = consultas.lerConsultasPorNome(nomeConsulta);
        
        for(Consulta c : resultados) {        
            resultado.adicionar(c);        
        }
        return resultado;
    }
    
}
