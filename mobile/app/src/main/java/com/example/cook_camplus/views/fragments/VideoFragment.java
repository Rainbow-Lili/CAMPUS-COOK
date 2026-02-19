package com.example.cook_camplus.views.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cook_camplus.R;
import com.example.cook_camplus.datas.models.Recette;
import com.example.cook_camplus.datas.connection.Connection;

import coil.Coil;
import coil.request.ImageRequest;

public class VideoFragment extends Fragment {
    
    private Recette recette;
    private ImageView ivVideoThumbnail;
    private View btnPlayVideo;
    
    // Vidéo placeholder pour toutes les recettes
    private static final String VIDEO_PLACEHOLDER = "attieke_video.mp4";
    
    public static VideoFragment newInstance(Recette recette) {
        VideoFragment fragment = new VideoFragment();
        fragment.recette = recette;
        return fragment;
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        
        ivVideoThumbnail = view.findViewById(R.id.iv_video_thumbnail);
        btnPlayVideo = view.findViewById(R.id.btn_play_video);
        
        setupVideo();
        
        return view;
    }
    
    private void setupVideo() {
        // Charger la miniature (utiliser l'image de la recette)
        if (recette != null && recette.getImageUrl() != null) {
            String imageUrl = Connection.getImageUrl(recette.getImageUrl());
            
            ImageRequest request = new ImageRequest.Builder(requireContext())
                .data(imageUrl)
                .target(ivVideoThumbnail)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .build();
            
            Coil.imageLoader(requireContext()).enqueue(request);
        }
        
        // Bouton play pour lancer la vidéo
        btnPlayVideo.setOnClickListener(v -> {
            playVideo();
        });
        
        ivVideoThumbnail.setOnClickListener(v -> {
            playVideo();
        });
    }
    
    private void playVideo() {
        // URL de la vidéo placeholder
        String videoUrl = Connection.getVideoUrl(VIDEO_PLACEHOLDER);
        
        try {
            // Ouvrir avec l'app vidéo externe
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(videoUrl), "video/mp4");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(getContext(), 
                "Impossible de lire la vidéo. Vérifiez que le serveur est accessible.", 
                Toast.LENGTH_LONG).show();
        }
    }
    
    public void updateRecette(Recette recette) {
        this.recette = recette;
        if (getView() != null) {
            setupVideo();
        }
    }
}
