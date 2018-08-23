package com.example.pingsunyi.wallpaper.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.pingsunyi.wallpaper.Adapters.PhotosAdapter;
import com.example.pingsunyi.wallpaper.Models.Photo;
import com.example.pingsunyi.wallpaper.R;
import com.example.pingsunyi.wallpaper.Webservices.ApiInterface;
import com.example.pingsunyi.wallpaper.Webservices.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotosFragment extends Fragment {
    private final String TAG = PhotosFragment.class.getSimpleName();
    @BindView(R.id.fragment_photos_progressBar)
    ProgressBar progressBar;
    @BindView(R.id.fragment_photos_recycleviews)
    RecyclerView recyclerView;

    private PhotosAdapter photosAdapter;
    private List<Photo> photos = new ArrayList<>();

    private Unbinder unbinder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photos, container, false);
        unbinder = ButterKnife.bind(this, view);
        // RecyclerView
        LinearLayoutManager linerLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linerLayoutManager);
        photosAdapter = new PhotosAdapter(getActivity(), photos);
        recyclerView.setAdapter(photosAdapter);

        showProgressBar(true);
        getPhotos();
        return view;
    }

    private void getPhotos() {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        retrofit2.Call<List<Photo>> call = apiInterface.getPhotos();
        call.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Photo>> call, Response<List<Photo>> response) {
                if (response.isSuccessful()) {
                    photos.addAll(response.body());
                    photosAdapter.notifyDataSetChanged();
                } else {
                    Log.e(TAG, "fail " + response.message());
                }
                showProgressBar(false);
            }

            @Override
            public void onFailure(retrofit2.Call<List<Photo>> call, Throwable t) {
                Log.e(TAG, "fail " + t.getMessage());
                showProgressBar(false);
            }
        });
    }

    private void showProgressBar(boolean isShow) {
        if (isShow) {
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
        unbinder.unbind();
    }
}
