/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.mack.fci.cc.smh.ejb;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.w3c.dom.Document;

/**
 *
 * @author prgoes
 */
@Stateless
public class ConsultaDAO {
    
    @EJB 
    EventoDAO eventos;
    
    public BasicDBObject gerarDocument(Consulta c) {
        ArrayList x = new ArrayList();
            for(Evento e : c.getSensores()) {
                BasicDBObject eventoDocument = eventos.gerarDocument(e);
                x.add(eventoDocument);
            }

            BasicDBObject doc = new BasicDBObject("DataDaConsulta", c.getDataConsulta()).
                            append("Nome", c.getNome()).
                            append("Formula", c.getFormula()).
                            append ("DadosFormula", x).
                            append ("Resultado",c.getResultadoFinal());
            
        return doc;
    }
    
    public Consulta gerarConsulta(DBObject doc) {
        Date data = (Date)doc.get("DataDaConsulta");
        double resultado = (double)doc.get("Resultado");
        //List<Evento> dados = (List<Evento>)doc.get("DadosFormula");
        List<Evento> dados = new ArrayList<Evento>();
        for(DBObject evento : (List<DBObject>)doc.get("DadosFormula")) {
            dados.add(eventos.gerarEvento(evento));
        }
        List<String> formula = (List<String>)doc.get("Formula");
        String nome = doc.get("Nome").toString();
        
        return new Consulta(nome, data, resultado, dados, formula);
    }
    
    public void gravarConsulta(Consulta c) {
        MongoClient mongoClient = null;
        DBCollection coll = null;
        try {
            mongoClient = new MongoClient();
            DB db = mongoClient.getDB("Parking");
            coll = db.getCollection("Consultas");
            
            BasicDBObject doc = gerarDocument(c);

            coll.insert(doc);
        } catch (UnknownHostException ex) {
        Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            mongoClient.close();
        }
    }
    
    public Consulta lerConsultaMaisRecente() {
        MongoClient mongoClient = null;
        DBCollection coll = null;
        try {
            mongoClient = new MongoClient();
            DB db = mongoClient.getDB("Parking");
            coll = db.getCollection("Consultas");
            
            DBCursor cursor = coll.find().sort(new BasicDBObject("DataDaConsulta",-1));
            
            if(cursor.hasNext()) {
                return gerarConsulta(cursor.next());
            }
            else {
                return new Consulta();
            }
        } catch (UnknownHostException ex) {
        Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            mongoClient.close();
        }
        
        return new Consulta();
    }
    
    public List<Consulta> lerConsultasPorNome(String nomeConsulta) {
        MongoClient mongoClient = null;
        DBCollection coll = null;
        List<Consulta> resultado = new ArrayList<Consulta>();
        try {
            mongoClient = new MongoClient();
            DB db = mongoClient.getDB("Parking");
            coll = db.getCollection("Consultas");
            
            BasicDBObject query = new BasicDBObject("Nome",nomeConsulta);
            DBCursor cursor = coll.find(query);
            
            while(cursor.hasNext()) {
                resultado.add(gerarConsulta(cursor.next()));
            }
        } catch (UnknownHostException ex) {
        Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            mongoClient.close();
        }
        
        return resultado;
    }
    
}
