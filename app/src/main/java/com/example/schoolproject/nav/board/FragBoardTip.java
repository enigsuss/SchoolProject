package com.example.schoolproject.nav.board;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.schoolproject.model.Board;
import com.example.schoolproject.model.BoardKind;
import com.example.schoolproject.model.retrofit.BoardApiService;
import com.example.schoolproject.model.retrofit.BoardCallback;
import com.example.schoolproject.post.PostPreviewRecyclerViewAdapter;
import com.example.schoolproject.post.PostWriteActivity;
import com.example.schoolproject.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class FragBoardTip extends Fragment {

    private View view;
    private List<Object> dataFragBoardTips;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private FloatingActionButton fab;

    public static FragBoardTip newInstance(){
        FragBoardTip fragBoardTip = new FragBoardTip();
        return fragBoardTip;
    }

    @Override
    public void onResume() {
        super.onResume();
        // get updated board
        BoardApiService apiService = new BoardApiService();
        Call<List<Board>> call = apiService.getBoardsByBoardKind(BoardKind.TIP);
        call.enqueue(new BoardCallback.BoardListCallBack(getActivity().getApplicationContext(), adapter));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_board_tip, container, false);
        // connecting resources
        fab = view.findViewById(R.id.fab_write);
        // setting RecylerView
        recyclerView = view.findViewById(R.id.recycler_view_board_tip);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        dataFragBoardTips = new ArrayList<>();
        adapter = new PostPreviewRecyclerViewAdapter(getContext(), dataFragBoardTips);
        recyclerView.setAdapter(adapter);

        // set data
        // get posts matching boardKind
        BoardApiService apiService = new BoardApiService();
        Call<List<Board>> call = apiService.getBoardsByBoardKind(BoardKind.TIP);
        call.enqueue(new BoardCallback.BoardListCallBack(getActivity().getApplicationContext(), adapter));

        // setting listeners
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), PostWriteActivity.class);
                intent.putExtra("boardKind", "TIP");
                startActivity(intent);
            }
        });

        return view;
    }
}