package com.example.eider.navigation_drawer.Fragment;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.eider.navigation_drawer.R;


public class MoviesFragment extends Fragment {
    ImageButton fab;
    public MoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View Rootview = inflater.inflate(R.layout.fragment_movies, container, false);
        try {

        }
        catch (Exception e){
            Toast.makeText(getContext(), e.getMessage()+" error: "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
        fab = (ImageButton) Rootview.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.setSelected(!fab.isSelected());
                fab.setImageResource(fab.isSelected() ? R.drawable.animate_plus :R.drawable.animate_minus);
                Drawable drawable = fab.getDrawable();
                if (drawable instanceof Animatable){
                    ((Animatable)drawable).start();
                }
            }
        });
        return Rootview;
    }
}