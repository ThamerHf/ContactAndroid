package com.uca.contact;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.uca.contact.databinding.FragmentFirstBinding;
import com.uca.contact.model.Tuple;

import java.sql.Blob;
import java.util.ArrayList;

public class FirstFragment extends Fragment {


    private FragmentFirstBinding binding;



    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        View binding = inflater.inflate(R.layout.fragment_first, container, false);

        ListView list = binding.findViewById(R.id.contactList);
        ArrayList<Tuple<String, Integer>> postArray = new ArrayList<>();
        postArray.add(new Tuple<>("p1", R.drawable.ic_call));
        postArray.add(new Tuple<>("p2", R.drawable.ic_delete));
        postArray.add(new Tuple<>("p2", R.drawable.ic_delete));
        postArray.add(new Tuple<>("p2", R.drawable.ic_delete));
        postArray.add(new Tuple<>("p2", R.drawable.ic_delete));
        postArray.add(new Tuple<>("p2", R.drawable.ic_delete));
        postArray.add(new Tuple<>("p2", R.drawable.ic_delete));
        postArray.add(new Tuple<>("p2", R.drawable.ic_delete));
        postArray.add(new Tuple<>("p2", R.drawable.ic_delete));
        postArray.add(new Tuple<>("p2", R.drawable.ic_delete));
        postArray.add(new Tuple<>("p2", R.drawable.ic_delete));
        postArray.add(new Tuple<>("p2", R.drawable.ic_delete));
        postArray.add(new Tuple<>("p2", R.drawable.ic_delete));
        postArray.add(new Tuple<>("p2", R.drawable.ic_delete));
        postArray.add(new Tuple<>("p2", R.drawable.ic_delete));

        // Ajoutez autant d'éléments que nécessaire

        ContactsAdapter adapter = new ContactsAdapter(requireContext(), R.layout.item_list, postArray);
        list.setAdapter(adapter);


        return binding;

    }
/*
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
*/
}