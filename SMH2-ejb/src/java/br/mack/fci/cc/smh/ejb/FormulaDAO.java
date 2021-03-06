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
                append("Formula", f.getFormula()).
                append("Original", f.getFormulaOriginal());

        return doc;
    }

    public Formula gerarFormula(DBObject doc) {
        String nomeFormula = doc.get("NomeFormula").toString();
        List<String> formula = (List<String>) doc.get("Formula");
        String original = doc.get("Original").toString();

        return new Formula(nomeFormula, formula, original);
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

    public Formula encontrarFormula(String nome) {
        List<Formula> lista = new ArrayList<>();
        MongoClient mongoClient = null;
        DBCollection coll = null;
        try {
            mongoClient = new MongoClient();
            DB db = mongoClient.getDB("Parking");
            coll = db.getCollection("Formulas");

            BasicDBObject query = new BasicDBObject("NomeFormula", nome);

            DBCursor cursor = coll.find(query);
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
            Logger.getLogger(FormulaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            mongoClient.close();
        }
        
        if(lista.size()>0) return lista.get(0);
        return new Formula();
    }

    public void removerFormula(String nome) {
        MongoClient mongoClient = null;
        DBCollection coll = null;
        try {
            mongoClient = new MongoClient();
            DB db = mongoClient.getDB("Parking");
            coll = db.getCollection("Formulas");

            BasicDBObject query = new BasicDBObject("NomeFormula", nome);
            coll.remove(query);

        } catch (UnknownHostException ex) {
            Logger.getLogger(FormulaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            mongoClient.close();
        }
    }

    public void alterarFormula(Formula _formula) {
        MongoClient mongoClient = null;
        DBCollection coll = null;
        try {
            mongoClient = new MongoClient();
            BasicDBObject doc = gerarDocument(_formula);
            DB db = mongoClient.getDB("Parking");
            coll = db.getCollection("Formulas");
            
            BasicDBObject query = new BasicDBObject("NomeFormula", _formula.getNomeFormula());
            coll.findAndModify(query,doc);

        } catch (UnknownHostException ex) {
            Logger.getLogger(FormulaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            mongoClient.close();
        }
    }

}
