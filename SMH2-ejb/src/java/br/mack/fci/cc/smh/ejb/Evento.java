/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.mack.fci.cc.smh.ejb;

import java.util.Date;

/**
 *
 * @author prgoes
 */
public class Evento {
    private Date _dataDoEvento;
    private String _sensor;
    private String _tipo;
    private double _valor;
    
    public Evento(){        
        _dataDoEvento = new Date(1900, 01, 01);
        _sensor = "";
        _tipo = "";
        _valor = 0;
    }
       
    public Evento(Date data, String sensor, String tipo, double valor) {
        _dataDoEvento = data;
        _tipo = tipo;
        _sensor = sensor;
        _valor = valor;
    }
        
    public Date getDataDoEvento() { return _dataDoEvento; }
    public String getSensor() { return _sensor; }
    public String getTipo() { return _tipo; }
    public double getValor() { return _valor; }
}
