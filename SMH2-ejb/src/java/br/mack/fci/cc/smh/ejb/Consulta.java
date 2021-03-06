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
    private List <Double> resultados = new ArrayList<>();
    private Date dataConsulta;
    private Double resultFinal;
    private String nome;
    
    public List<Evento> getEventos() { return dados; }
    public List<String> getFormula() { return formula; }  
    public List<Evento> getSensores() {return sensores; }
    public Double getResultadoFinal() {return resultFinal; }
    public Date getDataConsulta(){ return dataConsulta; }
    public String getNome() { return nome; }
    
    public Consulta(List<Evento> da, Formula formula, Consulta consultaAnterior) {
        nome = formula.getNomeFormula();
        CalculaFormula(da, formula.getFormula(), consultaAnterior);
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
    
    public Consulta(String nome, Date data, double resultado, List<Evento> dados, List<String> formula) {
        this.nome = nome;
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
        dataConsulta=(new Date());
        
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
            double resultado=0;
            String aux;
            
            aux = formula.get(x);
            switch (aux) {
                case ("+"): {
                    resultado = 0;
                    List<Double> novosResultados = new ArrayList<Double>();
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
                    List<Double> novosResultados = new ArrayList<Double>();
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
                    List<Double> novosResultados = new ArrayList<Double>();
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
                    List<Double> novosResultados = new ArrayList<Double>();
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
                        double num = Integer.parseInt(aux);
                        resultados.add(num);
                    }
                    //se nao for é uma posicao de sensor
                    else
                    {
                        Boolean sensorEncontrado = false;
                        for (int k = 0; k < sensores.size(); k++) {
                            if (sensores.get(k).getSensor().equals(aux)) {
                                sensorEncontrado = true;
                                double result = sensores.get(k).getValor();
                                resultados.add(result);
                            }
                        }
                        
                        if(!sensorEncontrado) {
                            if(consultaAnterior != null) {
                                for(Evento e : consultaAnterior.getSensores()) {
                                    if(e.getSensor().equals(aux)) {
                                        sensorEncontrado = true;
                                        sensores.add(e);
                                        double result = e.getValor();
                                        resultados.add(result);
                                    }
                                }
                            }
                        }
                        
                        if(!sensorEncontrado) {
                            resultados.add(0.0);
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
