package sim.ssn.com.todo.fragment.list;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import sim.ssn.com.todo.R;
import sim.ssn.com.todo.listener.CustomListener;

public class TodoListFragment extends Fragment{
    private CustomListener clickListener;
    private ImageView ivAdd;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            clickListener = (CustomListener) activity;
            ivAdd = (ImageView) activity.findViewById(R.id.fList_ivAdd);
            //ivAdd.setOnClickListener(clickListener.handleAddKind());
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_todo_list, container, false);

                RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.fList_recyclerview);
                mRecyclerView.setHasFixedSize(true);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

                RecyclerView.Adapter mAdapter = new AdapterTodoList(R.layout.cardview_todo_list, getActivity());
                mRecyclerView.setAdapter(mAdapter);
            return rootView;
        }
}
