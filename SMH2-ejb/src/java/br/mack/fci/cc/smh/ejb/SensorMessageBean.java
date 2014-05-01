/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.mack.fci.cc.smh.ejb;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import org.w3c.dom.Document;

/**
 *
 * @author prgoes
 */
@MessageDriven (mappedName="jms/SensorQueue", 
activationConfig = {  
    @ActivationConfigProperty(propertyName="acknowledgeMode",propertyValue="Auto-acknowledge"),  
    @ActivationConfigProperty(propertyName="destinationType",propertyValue="javax.jms.Queue") 
}) 
public class SensorMessageBean implements MessageListener {
    
    @EJB
    private EventoDAO eventos;
    
    public SensorMessageBean() {
    }
    
    @Override
    public void onMessage(Message message) {
        try {
            TextMessage tm = (TextMessage) message;
            Document doc = XMLParser.convertStringToDocument(tm.getText());
            Evento e = ProcessadorDeEvento.tratarMensagem(doc);
            eventos.gravarEvento(e);  
            Logger.getLogger("Log de MrMessageBean").log(Level.INFO, "Mensagem recebida e processada. Conteudo: " + tm.getText(), "");
        } catch (JMSException ex) {
            Logger.getLogger(SensorMessageBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
