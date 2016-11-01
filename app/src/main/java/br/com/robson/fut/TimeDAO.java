package br.com.robson.fut;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robson on 03/10/2016.
 */

public class TimeDAO implements ICallback {


    private Context context;
    private ICallback callback;
    private List<Time> listTimes;

    public TimeDAO(Context context, ICallback callback)
    {
        this.context = context;
        this.callback = callback;
    }

    public void listAll()
    {
        this.callback = callback;
        MessageResponse response = new MessageResponse("", MessageCodeEnum.List);
        notifyDataRefreshed(response);
    }

    private List<Object> parseResult(String data){
        List<Object> list = new ArrayList<>();
        Time time = new Time();
        time.setNome("Nome teste");
        list.add(time);
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
