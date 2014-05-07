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
import javax.ejb.EJB;
import javax.enterprise.context.spi.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Andrea
 */
public class GraficoSerie {
    private ISerieConsulta ejb;
    private JFreeChart grafico;
    
    public DefaultCategoryDataset construiGraficoSerie (String formula)   
    {
       
        InitialContext context = null;
        try {
            context =  new InitialContext();
             ejb = (ISerieConsulta)context.lookup ("java:global/SMH2/SMH2-ejb/SerieConsulta");
            } 
            catch (NamingException ex) {
                
            }
        
       // formula="Ocupacao";
        SerieDTO dto = ejb.executarSerieConsulta(formula);
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int x = 1;
        for (Date key : dto.getResultados().keySet()) {

            dataset.addValue(dto.getResultados().get(key), "Resultados para " + dto.getTitulo(), key);
            x++;
        }
      //  grafico = ChartFactory.createLineChart("Gráfico Série", "Consulta", "Valor", dataset, PlotOrientation.VERTICAL, true, true, false);

       /* BufferedImage buf = null;
        buf = grafico.createBufferedImage(400, 300);

        ChartRenderingInfo info = null;
        BufferedImage chartImage = grafico.createBufferedImage(800, 600, info);

        PngEncoder encoder = new PngEncoder(chartImage, false, 0, 9);
        
        return grafico;*/
        
        return dataset;
            
        
    }
   
    
   
    }
