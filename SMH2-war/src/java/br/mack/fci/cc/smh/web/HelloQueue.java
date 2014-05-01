/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.mack.fci.cc.smh.web;

import br.mack.fci.cc.smh.ejb.Evento;
import br.mack.fci.cc.smh.ejb.EventoDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author prgoes
 */
@WebServlet(name = "HelloQueue", urlPatterns = {"/HelloQueue"})
public class HelloQueue extends HttpServlet {
    
    @Resource(mappedName = "jms/SensorQueueFactory")
    private QueueConnectionFactory myFactory;
    @Resource(mappedName = "jms/SensorQueue")
    private Queue myQueue;
    @EJB
    private EventoDAO eventos;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String mensagem1 = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><dados><data>"+new Date()+"</data><grandeza>Unidade</grandeza><unidadegrandeza>Sensor1</unidadegrandeza><valor>1</valor></dados>";
        String mensagem2 = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><dados><data>"+new Date()+"</data><grandeza>Unidade</grandeza><unidadegrandeza>Sensor2</unidadegrandeza><valor>1</valor></dados>";
        String mensagem3 = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><dados><data>"+new Date()+"</data><grandeza>Unidade</grandeza><unidadegrandeza>Sensor3</unidadegrandeza><valor>1</valor></dados>";
        try {
            Connection conn = myFactory.createConnection();
            Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = (MessageProducer) session.createProducer(myQueue);
            TextMessage msg = session.createTextMessage();
            msg.setText(mensagem1);
            producer.send(msg);
            
            msg = session.createTextMessage();
            msg.setText(mensagem2);
            producer.send(msg);
            
            msg = session.createTextMessage();
            msg.setText(mensagem3);
            producer.send(msg);
            Logger.getLogger(HelloQueue.class.getName()).log(Level.INFO,
                    "OK, mensagem enviada", "");
        }
        catch (JMSException ex) {
            Logger.getLogger(HelloQueue.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(HelloQueue.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        List<Evento> todosOsEventos = eventos.lerTodosOsEventos();
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet HelloQueue</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet HelloWorld at " + eventos.teste("Bob") + "</h1>");
            
            for(Evento e : todosOsEventos) {
                out.println(e.getDataDoEvento() + " | " + e.getSensor() + " | " + e.getValor() + "<br />");
            }
            out.println("</body>");
            out.println("</html>");
        }
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
