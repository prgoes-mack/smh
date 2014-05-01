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
 * @author Douglas
 */
@Stateless
public class FormulaDAO {

    public BasicDBObject gerarDocument(Formula f) {
        BasicDBObject doc = new BasicDBObject("NomeFormula", f.getNomeFormula()).
                append("Formula", f.getFormula());

        return doc;
    }

    public Formula gerarFormula(DBObject doc) {
        String nomeFormula = doc.get("NomeFormula").toString();
        List<String> formula = (List<String>) doc.get("Formula");

        return new Formula(nomeFormula, formula);
    }

    public void gravarFormula(Formula f) {
        MongoClient mongoClient = null;
        DBCollection coll = null;
        try {
            mongoClient = new MongoClient();
            DB db = mongoClient.getDB("Parking");
            coll = db.getCollection("Formulas");

            BasicDBObject doc = gerarDocument(f);

            coll.insert(doc);
        } catch (UnknownHostException ex) {
            Logger.getLogger(FormulaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            mongoClient.close();
        }
    }

    public List<Formula> lerTodosAsFormulas() {
        List<Formula> lista = new ArrayList<>();

        MongoClient mongoClient = null;
        DBCollection coll = null;
        try {
            mongoClient = new MongoClient();
            DB db = mongoClient.getDB("Parking");
            coll = db.getCollection("Formulas");

            DBCursor cursor = coll.find();
            try {
                while (cursor.hasNext()) {
                    lista.add(gerarFormula(cursor.next()));
                }
            } catch (Exception ex) {
                Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                cursor.close();
            }

        } catch (UnknownHostException ex) {
            Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            mongoClient.close();
        }

        return lista;
    }
    
    public void removerFormula(String nome){
        MongoClient mongoClient = null;
        DBCollection coll = null;
        try {
            mongoClient = new MongoClient();
            DB db = mongoClient.getDB("Parking");
            coll = db.getCollection("Formulas");

            //coll.remove()
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(FormulaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            mongoClient.close();
        }
    }

}
