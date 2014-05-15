/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.mack.fci.cc.smh.ejb;

import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

/**
 *
 * @author prgoes
 */
@Stateless
public class ConsultaEJB {
    
    @EJB
    private ConsultaDAO consultas;
    
    @EJB
    private EventoDAO eventos;
    
    @EJB
    private FormulaDAO formulas;
    
    //@Schedule(minute="*/1",hour="*", persistent=false)
    public void gerarConsultas() {
        
        System.out.println("Executando gerador de consultas!");
        
        Consulta consultaMaisRecente = consultas.lerConsultaMaisRecente();
        List<Evento> eventosParaAnalisar = eventos.lerTodosAposData(consultaMaisRecente.getDataConsulta());
        List<Formula> formulasParaConsolidar = formulas.lerTodosAsFormulas();
        
        if(eventosParaAnalisar.size() > 0)
        {
            for(Formula formula : formulasParaConsolidar) {
                Consulta novaConsulta = new Consulta(eventosParaAnalisar, formula, consultaMaisRecente);
                consultas.gravarConsulta(novaConsulta);
            }
        }
    }
}
