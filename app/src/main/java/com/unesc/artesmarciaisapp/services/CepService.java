package com.unesc.artesmarciaisapp.services;

import com.unesc.artesmarciaisapp.models.CepModel;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class CepService {
    public String getStateByUf(String uf) {
        String state = "";
        switch (uf) {
            case "RO":
                state = "Rondônia";
                break;
            case "AC":
                state = "Acre";
                break;
            case "AM":
                state = "Amazonas";
                break;
            case "RR":
                state = "Roraima";
                break;
            case "PA":
                state = "Pará";
                break;
            case "AP":
                state = "Amapá";
                break;
            case "TO":
                state = "Tocantins";
                break;
            case "MA":
                state = "Maranhão";
                break;
            case "PI":
                state = "Piauí";
                break;
            case "CE":
                state = "Ceará";
                break;
            case "RN":
                state = "Rio Grande do Norte\t";
                break;
            case "PB":
                state = "Paraíba";
                break;
            case "PE":
                state = "Pernambuco";
                break;
            case "AL":
                state = "Alagoas";
                break;
            case "SE":
                state = "Sergipe";
                break;
            case "BA":
                state = "Bahia";
                break;
            case "MG":
                state = "Minas Gerais";
                break;
            case "ES":
                state = "Espírito Santo";
                break;
            case "RJ":
                state = "Rio de Janeiro";
                break;
            case "SP":
                state = "São Paulo";
                break;
            case "PR":
                state = "Paraná";
                break;
            case "SC":
                state = "Santa Catarina";
                break;
            case "RS":
                state = "Rio Grande do Sul";
                break;
            case "MS":
                state = "Mato Grosso do Sul";
                break;
            case "MT":
                state = "Mato Grosso";
                break;
            case "GO":
                state = "Goiás";
                break;
            case "DF":
                state = "Distrito Federal";
                break;
        }
        return state;
    }

    public CepModel getCep(String cep) {
        CepModel cepRet = new CepModel();
        try {
            URL url = new URL("https://viacep.com.br/ws/" + cep + "/json/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.connect();
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error code : "
                        + conn.getResponseCode());
            } else {
                String inline = "";
                Scanner sc = new Scanner(url.openStream());
                while (sc.hasNext()) {
                    inline += sc.nextLine();
                }
                sc.close();
                JSONObject jonb = new JSONObject(inline);
                cepRet.setCep(jonb.getString("cep"));
                cepRet.setLogradouro(jonb.getString("logradouro"));
                cepRet.setLocalidade(jonb.getString("localidade"));
                cepRet.setComplemento(jonb.getString("complemento"));
                cepRet.setBairro(jonb.getString("bairro"));
                cepRet.setUf(jonb.getString("uf"));
                cepRet.setUnidade(jonb.getString("unidade"));
                cepRet.setIbge(jonb.getString("ibge"));
                cepRet.setGia(jonb.getString("gia"));

            }

            conn.disconnect();
            return cepRet;
        } catch (Exception e) {
            return null;
        }
    }
}
