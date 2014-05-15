/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.mack.fci.cc.smh.service;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 *
 * @author prgoes
 */
@WebService
public class QueueWS {
    
    @Resource(mappedName = "jms/SensorQueueFactory")
    private QueueConnectionFactory myFactory;
    @Resource(mappedName = "jms/SensorQueue")
    private Queue myQueue;
    
    @WebMethod(operationName = "enviaLeitura") 
    public void sendMessage(String nomeSensor, Double valor){ 
        String mensagem = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><dados><data>"+new Date()+"</data><grandeza>Unidade</grandeza><unidadegrandeza>"+nomeSensor+"</unidadegrandeza><valor>"+valor.toString()+"</valor></dados>";
        try {
            Connection conn = myFactory.createConnection();
            Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = (MessageProducer) session.createProducer(myQueue);
            TextMessage msg = session.createTextMessage();
            msg.setText(mensagem);
            producer.send(msg);
            Logger.getLogger(QueueWS.class.getName()).log(Level.INFO,
                    "OK, mensagem enviada", "");
        }
        catch (JMSException ex) {
            Logger.getLogger(QueueWS.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    } 

    
}
