/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.mack.fci.cc.smh.ejb;
import static br.mack.fci.cc.smh.ejb.ShuntingYard.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Douglas
 */
public class Formula {
    private List<String> _Formula;
    private String _Nome;
    private String _Original;
    
    public Formula()
    {
        _Original = "";
        _Formula = new ArrayList<String>();
        _Nome = "";
    }
    
    public Formula(String nome, String formula){
        _Original = formula;
        _Formula = postfix(formula);
        _Nome = nome;
    }
    
    public Formula(String nome, List<String> formula, String original){
        _Original = original;
        _Formula = formula;
        _Nome = nome;
    }
    
    public List<String> getFormula(){
        return _Formula;
    }
    
    public String getNomeFormula()
    {
        return _Nome;
    }
    
    public String getFormulaOriginal(){
        return _Original;
    }
}