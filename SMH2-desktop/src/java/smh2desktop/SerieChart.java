/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smh2desktop;

import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

/**
 *
 * @author Douglas
 */
public class SerieChart extends JFrame {

    private static final long serialVersionUID = 1L;

    public SerieChart(String applicationTitle, String chartTitle) {
        super(applicationTitle);
        // This will create the dataset 
        DefaultCategoryDataset dataset = GraficoSerie.construiGraficoSerie(chartTitle);
        // based on the dataset we create the chart
        JFreeChart chart = createChart(dataset);
        // we put the chart into a panel
        ChartPanel chartPanel = new ChartPanel(chart);
        // default size
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        // add it to our application
        setContentPane(chartPanel);

    }

    /**
     * Creates a chart
     */

    private JFreeChart createChart(DefaultCategoryDataset dataset) {

        JFreeChart chart = ChartFactory.createLineChart("Gráfico Série", "Consulta", "Valor", dataset, PlotOrientation.VERTICAL, true, true, false);
        return chart;

    }
}
