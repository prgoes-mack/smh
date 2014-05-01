/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.mack.fci.cc.smh.ejb;

import com.mongodb.BasicDBObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Douglas
 */
@Stateless
public class FormulaEJB {

    @EJB
    private FormulaDAO formulas;

    public void InsertFormula(String nome, String formula) {
        try {
            Formula _formula = new Formula(nome, formula);
            formulas.gravarFormula(_formula);
        } catch (Exception ex) {
            Logger.getLogger(FormulaEJB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Formula> listarTodas() {
        List<Formula> listFormula = new ArrayList<Formula>();
        try {
           listFormula = formulas.lerTodosAsFormulas();
        } catch (Exception ex) {
            Logger.getLogger(FormulaEJB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listFormula;
    }

    public void removerFormula(String nome) {
        try {
            formulas.removerFormula(nome);
        } catch (Exception ex) {
            Logger.getLogger(FormulaEJB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
