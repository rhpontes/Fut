package br.com.robson.fut;

import android.content.Context;

/**
 * Created by Robson on 02/10/2016.
 */

public class FutebolService {
    private static FutebolService instance;
    private static Context context;

    public FutebolService(Context context)
    {
        this.context = context;
    }

    public static FutebolService getInstance(Context context)
    {
        if (instance == null)
            instance = new FutebolService(context);
        else
        {
            FutebolService.context = context;
        }

        return instance;
    }

    public void getCampeonatos(ICallback callback)
    {
        CampeonatoDAO dao = new CampeonatoDAO(context, callback);
        dao.listAll();
    }

    public void getTimes(ICallback callback)
    {
        TimeDAO dao = new TimeDAO(context, callback);
        dao.listAll();
    }

    public void getJogos(ICallback callback)
    {
        JogoDAO dao = new JogoDAO(context, callback);
        dao.listAll();
    }
}
