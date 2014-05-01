/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.mack.fci.cc.smh.ejb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Andrea
 */
public class Consulta {
    private List <Evento> dados = new ArrayList<>();
    private List <String> formula = new ArrayList<>();
    
    
     public void CalculaFormula (ArrayList<Evento> da, ArrayList<String> formu)
    {
        int x;
        this.dados=da;
        this.formula=formu;
       // String sensor;
        ArrayList <Evento> sensores = new ArrayList<>();
        //Percorre dados para encontrar os eventos mais recentes
        ArrayList <Integer> resultados = new ArrayList<>();
        
        for (x=0; x<dados.size(); x++)
        {
            //verifica se já tenho esse sensor
            if (sensores.contains(dados.get(x).getSensor()))
            {
                System.out.println((dados.get(x).getSensor()+" Sensor já foi avaliado\n"));
            }
            else
            {
                sensores.add(dados.get(x));//pois esse que vou gravar o banco
            } 
        }
        
        x=0;
        while (x<formula.size())
        {
            int resultado=0;
            String aux;
            
            aux = formula.get(x);
            switch (aux) {
                case ("+"): {
                    for (int t = 0; t < resultados.size(); t++) {
                        resultado = resultados.get(t) + resultado;
                    }
                    resultados.clear();
                    resultados.add(resultado);
                }
                break;
                case ("-"): {
                    for (int t = 0; t < resultados.size(); t++) {
                        resultado = resultados.get(t) - resultado;
                    }
                    resultados.clear();
                    resultados.add(resultado);
                }
                break;
                case ("*"): {

                    for (int t = 0; t < resultados.size(); t++) {
                        resultado = resultados.get(t) * resultado;
                    }
                    resultados.clear();
                    resultados.add(resultado);

                }
                break;
                case ("/"): {

                    for (int t = 0; t < resultados.size(); t++) {
                        resultado = +resultados.get(t) / resultado;
                    }
                    resultados.clear();
                    resultados.add(resultado);
                }
                break;
                default: {
                    for (int k = 0; k < sensores.size(); k++) {
                        if (sensores.get(k).getSensor().equals(aux)) {
                            int result = Integer.parseInt(sensores.get(k).getValor());
                            resultados.add(result);
                        }
                    }
                }
            }
            x++;
        }
        System.out.println("Resultado: "+resultados.get(0));
    }
    
}
