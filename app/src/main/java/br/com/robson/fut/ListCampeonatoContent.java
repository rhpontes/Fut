package br.com.robson.fut;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
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

/**
 * Provides UI for the view with List.
 */
public class ListCampeonatoContent extends Fragment implements ICallback {

    public static final String EXTRA_POSITION = "position";
    private ContentAdapter mContentAdapter = null;
    private List<Campeonato> mCampeonatos =  null;



    void listarCampeonatos() {
        FutebolService.getInstance(getContext()).getCampeonatos(this);
    }

    @Override
    public void notifyDataRefreshed(Object resultado) {

        MessageResponse result = (MessageResponse) resultado;
        List<Campeonato> l = new ArrayList<>();
        for(Object obj : result.getList())
            l.add((Campeonato) obj);

        updateCampeonatosView(l);
    }

    void updateCampeonatosView(List<Campeonato> l) {
        //txt.setText(String.format("Retornou %s registros!", l.size()));
        //this.mCampeonatos = Stream.of(l).sortBy(Campeonato::getNome).collect(Collectors.toList());
        this.mCampeonatos = l;
        //if (mRecyclerView != null)
            //mRecyclerView.removeAllViews();
        if (mContentAdapter != null)
           mContentAdapter.updateData(this.mCampeonatos);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        listarCampeonatos();
        mContentAdapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(mContentAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView CampeonatoImg;
        public TextView CampeonatoNome;
        public TextView CampeonatoDesc;
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_list_campeonatos_content, parent, false));
            CampeonatoImg = (ImageView) itemView.findViewById(R.id.list_avatar);
            CampeonatoNome = (TextView) itemView.findViewById(R.id.list_title);
            CampeonatoDesc = (TextView) itemView.findViewById(R.id.list_desc);
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

        private List<Campeonato> mCampeonatos =  new ArrayList<>();
        //private Context mContext = null;

        public ContentAdapter(Context context) {
            Resources resources = context.getResources();
            //mContext = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        public void updateData(List<Campeonato> items) {
            mCampeonatos.clear();
            mCampeonatos.addAll(items);
            notifyDataSetChanged();
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            if (mCampeonatos != null && mCampeonatos.size() > 0 && mCampeonatos.size() > position) {
                Glide.with(holder.itemView.getContext()).load(R.drawable.ic_brasileiro).centerCrop().into(holder.CampeonatoImg);
                holder.CampeonatoNome.setText(mCampeonatos.get(position).getNome());
            }
        }

        @Override
        public int getItemCount() {
            return mCampeonatos.size();
        }
    }
}
