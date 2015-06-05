package sim.ssn.com.todo.fragment.kind;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.renderscript.Script;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import sim.ssn.com.todo.R;
import sim.ssn.com.todo.fragment.list.AdapterTodoList;
import sim.ssn.com.todo.listener.CustomListener;

public class KindListFragment extends Fragment {
    private static String TODOKIND = "todoKind";
    private CustomListener clickListener;

    private ImageView ivAdd;

    private String kind;

    public static KindListFragment newInstance(String kind) {
        KindListFragment fragment = new KindListFragment();
        Bundle projectMap = new Bundle();
        projectMap.putString(TODOKIND, kind);
        fragment.setArguments(projectMap);
        return fragment;
    }

    private void loadArguments(){
        if (getArguments() != null) {
            this.kind = getArguments().getString(TODOKIND);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadArguments();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        clickListener = (CustomListener) activity;
        ivAdd = (ImageView) activity.findViewById(R.id.fKind_ivAdd);
       // ivAdd.setOnClickListener(clickListener.handleAddTodo());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_kind_list, container, false);

        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.fKind_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        RecyclerView.Adapter mAdapter = new AdapterKindList(R.layout.cardview_kind_list, getActivity(), kind);
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }
}
