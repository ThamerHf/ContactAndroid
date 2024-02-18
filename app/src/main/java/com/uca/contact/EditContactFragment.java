package com.uca.contact;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.uca.contact.model.Contact;

public class EditContactFragment extends Fragment {

    private String contactTel;
    private String mode;

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    private ContactsDatabase contactsDatabase;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        View binding = inflater.inflate(R.layout.edit_contact_fragment, container, false);
        contactsDatabase = new ContactsDatabase(getActivity());

        Contact contact = contactsDatabase.getContactByTel(this.getContactTel());

        ((TextView)binding.findViewById(R.id.tel)).setText(contact.getTel());
        ((TextView)binding.findViewById(R.id.name)).setText(contact.getName());
        ((TextView)binding.findViewById(R.id.address)).setText(contact.getAddress());
        ((ImageView)binding.findViewById(R.id.photo)).setImageResource(R.drawable.ic_user);

        Button totoButton = binding.findViewById(R.id.save);

        if (this.getMode().equals("edit")) {
            totoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Contact contactToSave = new Contact(
                            ((TextView)v.findViewById(R.id.tel)).getText().toString(),
                            ((TextView)v.findViewById(R.id.name)).getText().toString(),
                            ((TextView)v.findViewById(R.id.address)).getText().toString()
                    );

                    contactsDatabase.updateContact(contactToSave);

                    ContactSavedDialogFragment dialogFragment = new ContactSavedDialogFragment();
                    dialogFragment.show(requireActivity().getSupportFragmentManager(), "ContactSavedDialogFragment");
                }
            });
        }
        else {
            totoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Contact contactToSave = new Contact(
                            ((TextView)v.findViewById(R.id.tel)).getText().toString(),
                            ((TextView)v.findViewById(R.id.name)).getText().toString(),
                            ((TextView)v.findViewById(R.id.address)).getText().toString()
                    );

                    contactsDatabase.addContact(contactToSave);

                    ContactSavedDialogFragment dialogFragment = new ContactSavedDialogFragment();
                    dialogFragment.show(requireActivity().getSupportFragmentManager(), "ContactSavedDialogFragment");
                }
            });
        }

        return binding;

    }


    /*
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }*/

}