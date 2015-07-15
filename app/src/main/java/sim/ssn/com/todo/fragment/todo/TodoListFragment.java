package sim.ssn.com.todo.fragment.todo;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import sim.ssn.com.todo.R;
import sim.ssn.com.todo.activity.MainActivity;
import sim.ssn.com.todo.listener.CustomListener;
import sim.ssn.com.todo.resource.Todo;

public class TodoListFragment extends Fragment {
    private static String TODOKINDID = "todoKindID";

    private CustomListener clickListener;

    private ImageView ivAdd;
    private List<Todo> todoList;
    private long kindId;

    public static TodoListFragment newInstance(long kindId) {
        TodoListFragment fragment = new TodoListFragment();
        Bundle projectMap = new Bundle();
        projectMap.putLong(TODOKINDID, kindId);
        fragment.setArguments(projectMap);
        return fragment;
    }

    private void loadArguments(){
        if (getArguments() != null) {
           kindId = getArguments().getLong(TODOKINDID);
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
        this.todoList = ((MainActivity)activity).getDataBase().getTodoListByKind(kindId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_todo_list, container, false);

        ivAdd = (ImageView) rootView.findViewById(R.id.fTodo_ivAdd);
        ivAdd.setOnClickListener(clickListener.handleAddTodo(kindId));

        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.fTodo_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        RecyclerView.Adapter mAdapter = new AdapterTodoList(R.layout.cardview_todo, getActivity(), todoList);
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }
}
