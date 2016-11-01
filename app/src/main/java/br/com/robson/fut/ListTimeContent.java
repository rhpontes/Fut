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
 * A simple {@link Fragment} subclass.
 */
public class ListTimeContent extends Fragment implements ICallback {

    public static final String EXTRA_POSITION = "position";
    private ContentAdapter mContentAdapter = null;
    private List<Time> mTimes =  null;


    void listarTimes() {
        FutebolService.getInstance(getContext()).getTimes(this);
    }

    @Override
    public void notifyDataRefreshed(Object resultado) {

        MessageResponse result = (MessageResponse) resultado;
        List<Time> l = new ArrayList<>();
        for(Object obj : result.getList())
            l.add((Time) obj);

        updateTimesView(l);
    }

    void updateTimesView(List<Time> l) {
        //txt.setText(String.format("Retornou %s registros!", l.size()));
        //this.mCampeonatos = Stream.of(l).sortBy(Campeonato::getNome).collect(Collectors.toList());
        this.mTimes = l;
        //if (mRecyclerView != null)
        //mRecyclerView.removeAllViews();
        if (mContentAdapter != null)
            mContentAdapter.updateData(this.mTimes);
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
        public ImageView TimeImg;
        public TextView TimeNome;
        public TextView TimeDesc;
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_list_time_content, parent, false));
            TimeImg = (ImageView) itemView.findViewById(R.id.list_time_avatar);
            TimeNome = (TextView) itemView.findViewById(R.id.list_time_title);
            TimeDesc = (TextView) itemView.findViewById(R.id.list_time_desc);
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

        private List<Time> mTimes =  new ArrayList<>();
        //private Context mContext = null;

        public ContentAdapter(Context context) {
            Resources resources = context.getResources();
            //mContext = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        public void updateData(List<Time> items) {
            mTimes.clear();
            mTimes.addAll(items);
            notifyDataSetChanged();
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            if (mTimes != null && mTimes.size() > 0 && mTimes.size() > position) {
                Glide.with(holder.itemView.getContext()).load(R.drawable.ic_brasileiro).centerCrop().into(holder.TimeImg);
                holder.TimeNome.setText(mTimes.get(position).getNome());
            }
        }

        @Override
        public int getItemCount() {
            return mTimes.size();
        }
    }

}
