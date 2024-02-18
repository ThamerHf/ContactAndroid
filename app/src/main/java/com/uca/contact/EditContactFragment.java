package com.uca.contact;

import android.content.Intent;
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

        TextView telText = binding.findViewById(R.id.tel);
        TextView nameText = binding.findViewById(R.id.name);
        TextView addressText = binding.findViewById(R.id.address);
        telText.setText(contact.getTel());
        nameText.setText(contact.getName());
        addressText.setText(contact.getAddress());
        ((ImageView)binding.findViewById(R.id.photo)).setImageResource(R.drawable.ic_user);

        Button saveButton = binding.findViewById(R.id.save);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contact contactToSave = new Contact(
                        telText.getText().toString(),
                        nameText.getText().toString(),
                        addressText.getText().toString()
                );

                if (getMode().equals("edit")) {
                    contactsDatabase.updateContact(contactToSave);
                } else {
                    contactsDatabase.addContact(contactToSave);
                }

                ContactSavedDialogFragment dialogFragment = new ContactSavedDialogFragment();
                dialogFragment.show(requireActivity().getSupportFragmentManager(), "ContactSavedDialogFragment");
            }
        });

        Button cancelButton = binding.findViewById(R.id.cancel);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

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