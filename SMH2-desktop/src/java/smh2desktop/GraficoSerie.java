/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smh2desktop;

import br.mack.fci.cc.smh.ejb.SerieConsulta;
import br.mack.fci.cc.smh.ejb.SerieDTO;
import java.util.Date;
import javax.ejb.EJB;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Andrea
 */
public class GraficoSerie {
    
    @EJB
    private SerieConsulta ejb;
    private JFreeChart grafico;
    
    public DefaultCategoryDataset construiGraficoSerie (String formula)   
    {
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
