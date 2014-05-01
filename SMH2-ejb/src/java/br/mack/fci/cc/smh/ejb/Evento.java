/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.mack.fci.cc.smh.ejb;

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
       
    public Evento(String data, String sensor, String tipo, String valor) {
        _dataDoEvento = data;
        _tipo = tipo;
        _sensor = sensor;
        _valor = valor;
    }
        
    public String getDataDoEvento() { return _dataDoEvento; }
    public String getSensor() { return _sensor; }
    public String getTipo() { return _tipo; }
    public String getValor() { return _valor; }
}
