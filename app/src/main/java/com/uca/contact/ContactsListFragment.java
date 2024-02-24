package com.uca.contact;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.uca.contact.databinding.ContactsListFragmentBinding;
import com.uca.contact.model.Contact;
import com.uca.contact.model.ContactsDatabase;

import java.util.List;

public class ContactsListFragment extends Fragment {


    private ContactsListFragmentBinding binding;

    private ContactsDatabase contactsDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = ContactsListFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        FloatingActionButton fab = view.findViewById(R.id.addContact);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Remplacez le fragment actuel par un nouveau fragment

                FragmentTransaction transaction = requireActivity()
                        .getSupportFragmentManager().beginTransaction();
                EditContactFragment fragment = new EditContactFragment();
                fragment.setMode("SAVE");
                transaction.replace(R.id.fragment_container_view, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        contactsDatabase = new ContactsDatabase(getActivity());
        List<Contact> contacts = contactsDatabase.getAllContacts();

        ListView list = view.findViewById(R.id.contactList);

        // Ajoutez autant d'éléments que nécessaire

        ContactsAdapter adapter = new ContactsAdapter(requireContext(), R.layout.item_list,
                contacts);
        list.setAdapter(adapter);

        EditText editText = view.findViewById(R.id.search);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;

    }
}