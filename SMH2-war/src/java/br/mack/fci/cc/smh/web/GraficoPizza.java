/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.mack.fci.cc.smh.web;

import br.mack.fci.cc.smh.ejb.PizzaDTO;
import br.mack.fci.cc.smh.ejb.PizzaMediaHistoricaPorConsulta;
import java.awt.Font;
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
    private PizzaMediaHistoricaPorConsulta ejb;

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
        
        PizzaDTO dto = ejb.executarPorConsulta("Ocupacao");        
        request.setAttribute("chartDTO", dto);        
        request.getRequestDispatcher("/pizzagraph.jsp").forward(request, response);
        /*
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet GraficoPizza</title>");            
            out.println("</head>");
            out.println("<body>");
            criarGrafico(dto);
            out.println("<h1>" + dto.getTitulo() + "</h1>");
            for(Double key : dto.getResultados().keySet()) {
                out.println(key.toString() + " - " + dto.getResultados().get(key).toString() + "<br />" );
            }
            out.println("</body>");
            out.println("</html>");
        }*/
    }
    
    private PiePlot criarGrafico(PizzaDTO dto) {
        //Creating the dataset 
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
        return plot;
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
