package edu.pucmm.isc581.applogin.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.Navigation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import edu.pucmm.isc581.applogin.R;

public class ArticleListFragment extends Fragment {


    private static final String ARG_PARAM1 = "idCategoria";

    private Long idCategoria;

    public ArticleListFragment() {
    }

    public static ArticleListFragment newInstance(Long idCategoria) {
        ArticleListFragment fragment = new ArticleListFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_PARAM1, idCategoria);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idCategoria = getArguments().getLong(ARG_PARAM1, -1L);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article_list, container, false);
        FloatingActionButton createButton = view.findViewById(R.id.createArticleButton);
        createButton.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_nav_list_article_to_create_article);
        });
        return view;

    }
}