/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.mack.fci.cc.smh.ejb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private Integer resultFinal;
    
    public List<Evento> getEventos() { return dados; }
    public List<String> getFormula() { return formula; }  
    public List<Evento> getSensores() {return sensores; }
    public Integer getResultadoFinal() {return resultFinal; }
    public Date getDataConsulta(){ return dataConsulta; }
    
    public Consulta(List<Evento> da, List<String> formu, Consulta consultaAnterior) {
        CalculaFormula(da, formu, consultaAnterior);
    }
    
    public Consulta() {
        String string = "January 1, 1900";
        try {
            dataConsulta = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(string);
            dados = new ArrayList<Evento>();
            formula = new ArrayList<String>();
            sensores = new ArrayList<Evento>();
        } catch (ParseException ex) {
            Logger.getLogger(Consulta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Consulta(Date data, int resultado, List<Evento> dados, List<String> formula) {
        this.formula = formula;
        this.resultFinal = resultado;
        this.sensores = dados;
        this.dataConsulta = data;
    }
    
    public void CalculaFormula (List<Evento> da, List<String> formu, Consulta consultaAnterior)
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
                    resultado = 0;
                    List<Integer> novosResultados = new ArrayList<Integer>();
                    for (int t = 0; t < (resultados.size() - 2); t++) {
                        novosResultados.add(resultados.get(t));
                    }
                    
                    resultado = resultados.get(resultados.size() - 2) + resultados.get(resultados.size() - 1);
                    novosResultados.add(resultado);
                    resultados.clear();
                    resultados.addAll(novosResultados);
                }
                break;
                case ("-"): {
                    resultado = 0;
                    List<Integer> novosResultados = new ArrayList<Integer>();
                    for (int t = 0; t < (resultados.size() - 2); t++) {
                        novosResultados.add(resultados.get(t));
                    }
                    
                    resultado = resultados.get(resultados.size() - 2) - resultados.get(resultados.size() - 1);
                    novosResultados.add(resultado);
                    resultados.clear();
                    resultados.addAll(novosResultados);
                }
                break;
                case ("*"): {
                    resultado = 1;
                    List<Integer> novosResultados = new ArrayList<Integer>();
                    for (int t = 0; t < (resultados.size() - 2); t++) {
                        novosResultados.add(resultados.get(t));
                    }
                    
                    resultado = resultados.get(resultados.size() - 2) * resultados.get(resultados.size() - 1);
                    novosResultados.add(resultado);
                    resultados.clear();
                    resultados.addAll(novosResultados);

                }
                break;
                case ("/"): {
                    resultado = 0;
                    List<Integer> novosResultados = new ArrayList<Integer>();
                    for (int t = 0; t < (resultados.size() - 2); t++) {
                        novosResultados.add(resultados.get(t));
                    }
                    
                    resultado = resultados.get(resultados.size() - 2) / resultados.get(resultados.size() - 1);
                            
                    novosResultados.add(resultado);
                    resultados.clear();
                    resultados.addAll(novosResultados);
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
                        Boolean sensorEncontrado = false;
                        for (int k = 0; k < sensores.size(); k++) {
                            if (sensores.get(k).getSensor().equals(aux)) {
                                sensorEncontrado = true;
                                int result = sensores.get(k).getValor();
                                resultados.add(result);
                            }
                        }
                        
                        if(!sensorEncontrado) {
                            if(consultaAnterior != null) {
                                for(Evento e : consultaAnterior.getSensores()) {
                                    if(e.getSensor().equals(aux)) {
                                        sensorEncontrado = true;
                                        sensores.add(e);
                                        int result = e.getValor();
                                        resultados.add(result);
                                    }
                                }
                            }
                        }
                        
                        if(!sensorEncontrado) {
                            resultados.add(0);
                        }
                    }
                }break;
            }
            x++;
        }
        System.out.println("Resultado: "+resultados.get(0));
        resultFinal=resultados.get(0);
        resultados.clear();
    }
    
}
