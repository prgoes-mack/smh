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
import javax.ejb.Stateless;
import org.w3c.dom.Document;

/**
 *
 * @author prgoes
 */
@Stateless
public class EventoDAO {
    
    public String teste(String nome) {
        return "Ola " + nome;
    }
    
    public BasicDBObject gerarDocument(Evento e) {
        BasicDBObject doc = new BasicDBObject("DataDoEvento", e.getDataDoEvento()).
                              append("Sensor", e.getSensor()).
                              append("Tipo", e.getTipo()).
                              append("Valor", e.getValor());
        
        return doc;
    }
    
    public Evento gerarEvento(DBObject doc) {
        String sensor = doc.get("Sensor").toString();
        String tipo = doc.get("Tipo").toString();
        String valor = doc.get("Valor").toString();
        String dataDoEvento = doc.get("DataDoEvento").toString();
        
        Evento e = new Evento(valor, sensor, tipo, valor);
        
        return e;
    }
    
    public void gravarEvento() {
        
        MongoClient mongoClient = null;
        DBCollection coll = null;
        try {
            mongoClient = new MongoClient();
            DB db = mongoClient.getDB( "Parking" );
            coll = db.getCollection("Eventos");

            BasicDBObject doc = new BasicDBObject("DataDoEvento", new Date()).
                              append("Sensor", "Sensor3").
                              append("Tipo", "Ocupado").
                              append("Valor", "true");

            coll.insert(doc);
        } catch (UnknownHostException ex) {
        Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            mongoClient.close();
        }
    }
    
    public void gravarEvento(Evento e) {
        MongoClient mongoClient = null;
        DBCollection coll = null;
        try {
            mongoClient = new MongoClient();
            DB db = mongoClient.getDB( "Parking" );
            coll = db.getCollection("Eventos");

            BasicDBObject doc = gerarDocument(e);

            coll.insert(doc);
        } catch (UnknownHostException ex) {
        Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            mongoClient.close();
        }
    }
    
    public List<Evento> lerTodosOsEventos() {
        List<Evento> lista = new ArrayList<>();
        
        MongoClient mongoClient = null;
        DBCollection coll = null;
        try {
            mongoClient = new MongoClient();
            DB db = mongoClient.getDB( "Parking" );
            coll = db.getCollection("Eventos");
        
            DBCursor cursor = coll.find();
            try {
               while(cursor.hasNext()) {
                   lista.add(gerarEvento(cursor.next()));
               }            
            } catch(Exception ex) {
                    Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
               cursor.close();
            }
        
        } catch (UnknownHostException ex) {
            Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            mongoClient.close();
        }
        
        return lista;
    }
    
    public List<Evento> lerTodosAposData(Date data) {
        List<Evento> lista = new ArrayList<>();
        
        MongoClient mongoClient = null;
        DBCollection coll = null;
        try {
            mongoClient = new MongoClient();
            DB db = mongoClient.getDB( "Parking" );
            coll = db.getCollection("Eventos");
        
            BasicDBObject query = new BasicDBObject("DataDoEvento", new BasicDBObject("$gt", data));
            DBCursor cursor = coll.find(query);
            try {
               while(cursor.hasNext()) {
                   lista.add(gerarEvento(cursor.next()));
               }            
            } catch(Exception ex) {
                    Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
               cursor.close();
            }
        
        } catch (UnknownHostException ex) {
            Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            mongoClient.close();
        }
        
        return lista;
    }
}
