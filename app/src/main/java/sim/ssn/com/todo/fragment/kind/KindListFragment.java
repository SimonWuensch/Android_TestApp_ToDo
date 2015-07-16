package sim.ssn.com.todo.fragment.kind;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import sim.ssn.com.todo.R;
import sim.ssn.com.todo.listener.CustomListener;

public class KindListFragment extends Fragment{
    private CustomListener clickListener;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            clickListener = (CustomListener) activity;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_kind_list, container, false);

                ImageView ivAdd = (ImageView) rootView.findViewById(R.id.fKind_ivAdd);
                ivAdd.setOnClickListener(clickListener.handleAddKind());

                RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.fKind_recyclerview);
                mRecyclerView.setHasFixedSize(true);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

                RecyclerView.Adapter mAdapter = new AdapterKindList(R.layout.cardview_kind, getActivity());
                mRecyclerView.setAdapter(mAdapter);
            return rootView;
        }
}
