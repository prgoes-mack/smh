/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.mack.fci.cc.smh.ejb;

import com.mongodb.DBObject;

/**
 *
 * @author prgoes
 */
public class Evento {
    private String _dataDoEvento;
    private String _sensor;
    private String _tipo;
    private String _valor;
    
    public Evento(){        
        _dataDoEvento = "";
        _sensor = "";
        _tipo = "";
        _valor = "";
    }
    
    public Evento(DBObject doc) {                
        _sensor = doc.get("Sensor").toString();
        _tipo = doc.get("Tipo").toString();
        _valor = doc.get("Valor").toString();
        _dataDoEvento = doc.get("DataDoEvento").toString();
    }
        
    public String getDataDoEvento() { return _dataDoEvento; }
    public String getSensor() { return _sensor; }
    public String getTipo() { return _tipo; }
    public String getValor() { return _valor; }
}
