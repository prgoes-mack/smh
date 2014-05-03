/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.mack.fci.cc.smh.web;

import br.mack.fci.cc.smh.ejb.SerieConsulta;
import br.mack.fci.cc.smh.ejb.SerieDTO;
import com.keypoint.PngEncoder;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author Gisele
 */
@WebServlet(name = "GraficoSerie", urlPatterns = {"/GraficoSerie"})
public class GraficoSerie extends HttpServlet {
    
    @EJB
    private SerieConsulta ejb;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String formula = request.getParameter("nome");
        SerieDTO dto = ejb.executarSerieConsulta(formula);
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
      
        for(Double key : dto.getResultados().keySet()) {
                dataset.addValue(dto.getResultados().get(key), "Serie 1", key);
            }
        JFreeChart grafico = ChartFactory.createLineChart("Gráfico Série", "Dia", "Valor", dataset, PlotOrientation.VERTICAL, true, true, false);
        
        BufferedImage buf = null;
         buf = grafico.createBufferedImage(400, 250);
       // Dataset plot = (CategoryDataset) grafico.getPlot(); 
        //plot.setLabelFont(new Font("Tahoma", Font.PLAIN, 11)); 
        ChartRenderingInfo info = null;
        BufferedImage chartImage = grafico.createBufferedImage(640, 400, info);
        response.setContentType("imageSerie.png");
        // send the picture
        PngEncoder encoder = new PngEncoder(chartImage, false, 0, 9);
        response.getOutputStream().write(encoder.pngEncode());
        
        
       // try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
        /*    out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>GraficoSerie</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Formula: " + request.getParameter("nome") + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }*/
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    
}
