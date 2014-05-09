/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.mack.fci.cc.smh.web;

import br.mack.fci.cc.smh.ejb.IPizzaMediaHistoricaPorConsulta;
import br.mack.fci.cc.smh.ejb.PizzaDTO;
import br.mack.fci.cc.smh.ejb.PizzaMediaHistoricaPorConsulta;
import com.keypoint.PngEncoder;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author prgoes
 */
@WebServlet(name = "GraficoPizza", urlPatterns = {"/GraficoPizza"})
public class GraficoPizza extends HttpServlet {
    
    @EJB
    private IPizzaMediaHistoricaPorConsulta ejb;

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
        //response.setContentType("text/html;charset=UTF-8");
        
        String nomeConsulta = request.getParameter("nome");
        
        PizzaDTO dto = ejb.executarPorConsulta(nomeConsulta);        
        //request.setAttribute("chartDTO", dto);        
        //request.getRequestDispatcher("/pizzagraph.jsp").forward(request, response);
        
        DefaultPieDataset pieDataset = new DefaultPieDataset(); 
        for(Double key : dto.getResultados().keySet()) {
                pieDataset.setValue(key, dto.getResultados().get(key));
            }
        JFreeChart chart = ChartFactory.createPieChart( 
            "Sample Pie-Chart created using JFreeChart!", pieDataset, true, 
            true, true);
        chart.setTitle(dto.getTitulo()); 
        chart.setAntiAlias(true); 
        chart.setTextAntiAlias(false);
        PiePlot plot = (PiePlot) chart.getPlot(); 
        plot.setLabelFont(new Font("Tahoma", Font.PLAIN, 11)); 
        ChartRenderingInfo info = null;
        BufferedImage chartImage = chart.createBufferedImage(640, 400, info);
        response.setContentType("image.png");
        // send the picture
        PngEncoder encoder = new PngEncoder(chartImage, false, 0, 9);
        response.getOutputStream().write(encoder.pngEncode());
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
