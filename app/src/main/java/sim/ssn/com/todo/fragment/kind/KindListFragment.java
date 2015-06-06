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

import java.util.ArrayList;
import java.util.List;

import sim.ssn.com.todo.R;
import sim.ssn.com.todo.activity.MainActivity;
import sim.ssn.com.todo.fragment.list.AdapterTodoList;
import sim.ssn.com.todo.listener.CustomListener;
import sim.ssn.com.todo.resource.Kind;
import sim.ssn.com.todo.resource.Todo;

public class KindListFragment extends Fragment {
    private static String TODOKIND = "todoKind";
    private static String TODOKINDID = "todoKindID";

    private CustomListener clickListener;

    private ImageView ivAdd;

    private List<Todo> todoList;
    private Kind kind;

    public static KindListFragment newInstance(Kind kind) {
        KindListFragment fragment = new KindListFragment();
        Bundle projectMap = new Bundle();
        projectMap.putLong(TODOKINDID, kind.getId());
        projectMap.putString(TODOKIND, kind.getName());
        fragment.setArguments(projectMap);
        return fragment;
    }

    private void loadArguments(){
        if (getArguments() != null) {
            String kindName = getArguments().getString(TODOKIND);
            long id = getArguments().getLong(TODOKINDID);
            this.kind = new Kind(id, kindName);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        loadArguments();
        clickListener = (CustomListener) activity;
        this.todoList = ((MainActivity)activity).getDataBase().getTodoListByKind(kind.getId());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_kind_list, container, false);

        ivAdd = (ImageView) rootView.findViewById(R.id.fKind_ivAdd);
        ivAdd.setOnClickListener(clickListener.handleAddTodo());

        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.fKind_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        RecyclerView.Adapter mAdapter = new AdapterKindList(R.layout.cardview_kind_list, getActivity(), todoList);
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }
}
