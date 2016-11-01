package br.com.robson.fut;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robson on 02/10/2016.
 */

public class CampeonatoDAO implements ICallback {

    private Context context;
    private ICallback callback;
    private List<Campeonato> listCampeonatos;

    public CampeonatoDAO(Context context, ICallback callback)
    {
        this.context = context;
        this.callback = callback;
    }


    public void listAll()
    {
        String url = "http://www.futebits.com.br/ws/api/getCampeonatos";
        MessageRequest request = new MessageRequest(url, MessageCodeEnum.List);
        Connection conn = new Connection(context, this);
        try {
            conn.doRequest(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Integer> parseEdicaoToArray(JSONArray jsonArray) throws JSONException
    {
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i ++)
            list.add(Integer.parseInt(jsonArray.get(i).toString()));

        return list;
    }

    private List<Divisao> parseDivisaoToArray(JSONArray arrayJSON) throws JSONException
    {

        List<Divisao> list = new ArrayList<>();

        for (int d = 0; d < arrayJSON.length(); d++) {
            JSONObject jsonObject = (JSONObject) arrayJSON.get(d);
            Divisao divisao = new Divisao();
            if (jsonObject.has("nome"))
                divisao.setNome(jsonObject.getString("nome"));
            if (jsonObject.has("edicoes"))
                divisao.setEdicoes(parseEdicaoToArray(jsonObject.getJSONArray("edicoes")));

            list.add(divisao);
        }

        return list;
    }

    private List<Object> parseResult(String data)
    {
        List<Object> list = new ArrayList<Object>();
        Campeonato campeonato;
        try {
            JSONObject jsonMain = new JSONObject(data);
            if (!jsonMain.has("campeonato"))
                return new ArrayList<Object>();

            JSONArray jsonArray = jsonMain.getJSONArray("campeonato");
            // Fazer o parse para objeto aqui...
            for (int i = 0; i < jsonArray.length(); i ++) {
                JSONObject jsonObj = (JSONObject) jsonArray.get(i);

                campeonato = new Campeonato();
                if (jsonObj.has("nome"))
                    campeonato.setNome(jsonObj.getString("nome"));
                if (jsonObj.has("cidade"))
                    campeonato.setCidade(jsonObj.getString("cidade"));
                if (jsonObj.has("estado"))
                    campeonato.setEstado(jsonObj.getString("estado"));
                if (jsonObj.has("pais"))
                    campeonato.setPais(jsonObj.getString("pais"));
                if (jsonObj.has("divisoes"))
                    campeonato.setDivisoes(parseDivisaoToArray(jsonObj.getJSONArray("divisoes")));

                // Add List
                list.add(campeonato);
            }


        } catch (Exception e)
        {
            e.printStackTrace();
        }

        // Retorna
        return list;

    }


    @Override
    public void notifyDataRefreshed(Object resultado) {
        MessageResponse response = (MessageResponse) resultado;
        if (response.getMessageCodeEnum() == MessageCodeEnum.List) {
            List<Object> list = parseResult(response.getResult());
            response.setList(list);
            callback.notifyDataRefreshed(response);
        }
    }
}
