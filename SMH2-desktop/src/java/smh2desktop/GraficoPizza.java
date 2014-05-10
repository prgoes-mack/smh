/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smh2desktop;

import br.mack.fci.cc.smh.ejb.IPizzaMediaHistoricaPorConsulta;
import br.mack.fci.cc.smh.ejb.PizzaDTO;
import br.mack.fci.cc.smh.ejb.SerieConsulta;
import br.mack.fci.cc.smh.ejb.SerieDTO;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author desktop02
 */
public class GraficoPizza {
    private JFreeChart grafico;
    
    public static DefaultPieDataset construiGraficoPizza (String formula)   
    {
        try {
            InitialContext ctx = new InitialContext();
            IPizzaMediaHistoricaPorConsulta ejb = (IPizzaMediaHistoricaPorConsulta)ctx.lookup("java:global/SMH2/SMH2-ejb/PizzaMediaHistoricaPorConsulta");
            DefaultPieDataset dataset = new DefaultPieDataset();
            PizzaDTO dto = ejb.executarPorConsulta(formula);
            for (Double key : dto.getResultados().keySet()) {
                dataset.setValue(key, dto.getResultados().get(key));
            }
            return dataset;
        } catch (NamingException ex) {
            Logger.getLogger(GraficoPizza.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        //SerieDTO dto = ejb.executarSerieConsulta(formula);
        
        //for (Date key : dto.getResultados().keySet()) {
        //    dataset.setValue(key, dto.getResultados().get(key));
        //}
        return new DefaultPieDataset();
    }
}
