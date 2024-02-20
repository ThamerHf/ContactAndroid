package com.uca.contact;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.uca.contact.databinding.ContactsListFragmentBinding;
import com.uca.contact.model.Contact;
import com.uca.contact.model.ContactsDatabase;

import java.util.List;

public class ContactsListFragment extends Fragment {


    private ContactsListFragmentBinding binding;

    private ContactsDatabase contactsDatabase;



    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        View binding = inflater.inflate(R.layout.contacts_list_fragment, container, false);

        contactsDatabase = new ContactsDatabase(getActivity());
        //TODO remove default data after finishing the add contact functionality
        contactsDatabase.addContact(new Contact("123", "Test", "Test address"));
        List<Contact> contacts = contactsDatabase.getAllContacts();

        ListView list = binding.findViewById(R.id.contactList);

        // Ajoutez autant d'éléments que nécessaire

        ContactsAdapter adapter = new ContactsAdapter(requireContext(), R.layout.item_list, contacts);
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