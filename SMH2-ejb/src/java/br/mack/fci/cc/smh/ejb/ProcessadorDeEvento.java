/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.mack.fci.cc.smh.ejb;

import java.util.Date;
import org.w3c.dom.Document;

/**
 *
 * @author prgoes
 */
public class ProcessadorDeEvento {
    
    public static Evento tratarMensagem(Document xmlEvento) {
        
        String tipo = "Ocupado";
        String sensor = xmlEvento.getElementsByTagName("unidadegrandeza").item(0).getTextContent();
        String valor = xmlEvento.getElementsByTagName("valor").item(0).getTextContent();
        String data = xmlEvento.getElementsByTagName("data").item(0).getTextContent();
        
        Evento e = new Evento(new Date(), sensor, tipo, valor);
        
        return e;
    }
    
}
