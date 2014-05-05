/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smh2desktop;

import br.mack.fci.cc.smh.ejb.SerieConsulta;
import br.mack.fci.cc.smh.ejb.SerieDTO;
import java.util.Date;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author desktop02
 */
public class GraficoPizza {
    private SerieConsulta ejb, ejb2;
    private JFreeChart grafico;
    
    public DefaultPieDataset construiGraficoPizza (String formula)   
    {
        SerieDTO dto = ejb.executarSerieConsulta(formula);
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Date key : dto.getResultados().keySet()) {
            dataset.setValue(key, dto.getResultados().get(key));
        }
        return dataset;
    }
}
