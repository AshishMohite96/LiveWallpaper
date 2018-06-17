package com.example.admin.livewallpaper.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.livewallpaper.R;
import com.example.admin.livewallpaper.adapter.MyRecentAdapter;
import com.example.admin.livewallpaper.database.RecentsImages;
import com.example.admin.livewallpaper.database.datasource.RecentsRepository;
import com.example.admin.livewallpaper.database.localdatabase.LocalDatabase;
import com.example.admin.livewallpaper.database.localdatabase.RecentsDataSource;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class RecentsFragment extends Fragment {

    private static RecentsFragment INSTANCE = null;

    Context context;

    RecyclerView recyclerView;
    List<RecentsImages> recentsList;
    MyRecentAdapter adapter;

    //room dB
    CompositeDisposable compositeDisposable;
    RecentsRepository recentsRepository;
    public RecentsFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public RecentsFragment(Context context) {
        this.context = context;

        //        init room dB
        compositeDisposable = new CompositeDisposable();
        LocalDatabase database = LocalDatabase.getInstance(context);
        recentsRepository = RecentsRepository.getInstance(RecentsDataSource.getInstance(database.recentsDAO()));
    }

    public static RecentsFragment getInstance(Context context){
        if (INSTANCE == null)
            INSTANCE = new RecentsFragment(context);

        return INSTANCE;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recents, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_recents);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recentsList = new ArrayList<>();
        adapter = new MyRecentAdapter(context, recentsList);
        recyclerView.setAdapter(adapter);
        
        loadRecents();
        return view;
    }

    private void loadRecents() {
        Disposable disposable = recentsRepository.getAllRecents()
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribeOn(Schedulers.io())
                                    .subscribe(new Consumer<List<RecentsImages>>() {
                                        @Override
                                        public void accept(List<RecentsImages> recentsImages) throws Exception {
                                            onGetAllRecentsSuccess(recentsImages);
                                        }
                                    }, new Consumer<Throwable>() {
                                        @Override
                                        public void accept(Throwable throwable) throws Exception {
                                            Log.d("ERROR", throwable.getMessage());
                                        }
                                    });

        compositeDisposable.add(disposable);
    }

    private void onGetAllRecentsSuccess(List<RecentsImages> recentsImages) {
        recentsList.clear();
        recentsList.addAll(recentsImages);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {

        compositeDisposable.clear();
        super.onDestroy();
    }
}
