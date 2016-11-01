package br.com.robson.fut;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


public class ListJogosContent extends Fragment implements ICallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String EXTRA_POSITION = "position";
    private ContentAdapter mContentAdapter = null;
    private List<Jogo> mJogos =  null;

    void listarTimes() {
        FutebolService.getInstance(getContext()).getJogos(this);
    }

    @Override
    public void notifyDataRefreshed(Object resultado) {

        MessageResponse result = (MessageResponse) resultado;
        List<Jogo> l = new ArrayList<>();
        for(Object obj : result.getList())
            l.add((Jogo) obj);

        updateTimesView(l);
    }

    void updateTimesView(List<Jogo> l) {
        //txt.setText(String.format("Retornou %s registros!", l.size()));
        //this.mCampeonatos = Stream.of(l).sortBy(Campeonato::getNome).collect(Collectors.toList());
        this.mJogos = l;
        //if (mRecyclerView != null)
        //mRecyclerView.removeAllViews();
        if (mContentAdapter != null)
            mContentAdapter.updateData(this.mJogos);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        listarTimes();
        mContentAdapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(mContentAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView JogoImg;
        public TextView JogoNome;
        public TextView JogoDesc;
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_list_jogos_content, parent, false));
            JogoImg = (ImageView) itemView.findViewById(R.id.list_jogo_avatar);
            JogoNome = (TextView) itemView.findViewById(R.id.list_jogo_title);
            JogoDesc = (TextView) itemView.findViewById(R.id.list_jogo_desc);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra(DetailActivity.EXTRA_POSITION, getAdapterPosition());
                    context.startActivity(intent);
                }
            });
        }
    }

    /**
     * Adapter to display recycler view.
     */
    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of List in RecyclerView.
        private static final int LENGTH = 18;

        private List<Jogo> mJogos =  new ArrayList<>();
        //private Context mContext = null;

        public ContentAdapter(Context context) {
            Resources resources = context.getResources();
            //mContext = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        public void updateData(List<Jogo> items) {
            mJogos.clear();
            mJogos.addAll(items);
            notifyDataSetChanged();
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            if (mJogos != null && mJogos.size() > 0 && mJogos.size() > position) {
                Glide.with(holder.itemView.getContext()).load(R.drawable.ic_brasileiro).centerCrop().into(holder.JogoImg);
                holder.JogoNome.setText(mJogos.get(position).getNome());
            }
        }

        @Override
        public int getItemCount() {
            return mJogos.size();
        }
    }
}
