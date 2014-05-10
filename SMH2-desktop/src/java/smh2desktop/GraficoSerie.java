/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smh2desktop;

import br.mack.fci.cc.smh.ejb.ISerieConsulta;
import br.mack.fci.cc.smh.ejb.SerieConsulta;
import br.mack.fci.cc.smh.ejb.SerieDTO;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.spi.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author Andrea
 */
public class GraficoSerie {
    
    public static DefaultCategoryDataset construiGraficoSerie (String formula)   
    {
       
      //  InitialContext context = null;
        try {
            //context =  new InitialContext();
            InitialContext context = new InitialContext();
            ISerieConsulta ejb = (ISerieConsulta)context.lookup ("java:global/SMH2/SMH2-ejb/SerieConsulta");
            SerieDTO dto = ejb.executarSerieConsulta(formula);
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            for (Date key : dto.getResultados().keySet()) {

                dataset.addValue(dto.getResultados().get(key), "Resultados para " + dto.getTitulo(), key);
            }
        return dataset;
        } 
            catch (NamingException ex) {
                Logger.getLogger(GraficoSerie.class.getName()).log(Level.SEVERE, null, ex);
            }
     
         return new DefaultCategoryDataset();      
    }
   
    }
