/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.mack.fci.cc.smh.ejb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Andrea
 */
public class Consulta {
    private List <Evento> dados = new ArrayList<>();
    private List <String> formula = new ArrayList<>();
    private List <Evento> sensores = new ArrayList<>();
    private List <Integer> resultados = new ArrayList<>();
    private Date dataConsulta;
    
    public List<Evento> getEventos() { return dados; }
    public List<String> getFormula() { return formula; }  
    public List<Evento> getSensores() {return sensores; }
    public List<Integer> getResultados() {return resultados; }
    public Date getDataConsulta(){ return dataConsulta; }
    
    public void CalculaFormula (ArrayList<Evento> da, ArrayList<String> formu)
    {
        int x;
        this.dados=da;
        this.formula=formu;
        dataConsulta=new Date();
        
        //Percorre dados para encontrar os eventos mais recentes
        //assume-se que a lista de eventos esteja ordenada do evento 
        //mais recente ao mais antigo
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
                    //verifica se é numero
                    if (Character.isDigit(aux.charAt(0))==true) 
                    {
                        int num = Integer.parseInt(aux);
                        resultados.add(num);
                    }
                    //se nao for é uma posicao de sensor
                    else
                    {
                        for (int k = 0; k < sensores.size(); k++) {
                            if (sensores.get(k).getSensor().equals(aux)) {
                                int result = Integer.parseInt(sensores.get(k).getValor());
                                resultados.add(result);
                            }
                        }
                    }
                }break;
            }
            x++;
        }
        System.out.println("Resultado: "+resultados.get(0));
    }
    
}
