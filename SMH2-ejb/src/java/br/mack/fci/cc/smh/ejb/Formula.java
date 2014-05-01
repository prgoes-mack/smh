/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.mack.fci.cc.smh.ejb;
import static br.mack.fci.cc.smh.ejb.ShuntingYard.*;
import java.util.List;
/**
 *
 * @author Douglas
 */
public class Formula {
    private List<String> _Formula;
    private String _Nome;
    
    public Formula(String nome, String formula){
        _Formula = postfix(formula);
        _Nome = nome;
    }
    
    public List<String> getFormula(){
        return _Formula;
    }
    
    public String getNomeFormula()
    {
        return _Nome;
    }
}