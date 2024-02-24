package com.uca.contact;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.uca.contact.model.Contact;
import com.uca.contact.model.ContactsDatabase;

public class EditContactFragment extends Fragment {

    private String contactId;
    private String mode;

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
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

        Contact contact = null;
        if (this.getContactId() != null) {
            contact = contactsDatabase.getContactByContactId(this.getContactId());
        } else {
            contact = new Contact();
        }

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

                contactToSave.setContactId(getContactId());
                if (contactToSave.getName().isEmpty()
                        || contactToSave.getName().trim().isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                    String alertTitle;
                    if (getMode().equals("edit")) {
                        alertTitle = "Update contact";
                    } else {
                        alertTitle = "Add new contact";
                    }
                    builder.setTitle(alertTitle)
                            .setMessage("Contact's name is required")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Handle positive button click
                                    dialog.dismiss(); // Close the dialog
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Handle negative button click or simply close the dialog
                                    dialog.dismiss();
                                }
                            })
                            .show();
                } else {
                    if (getMode().equals("edit")) {
                        contactsDatabase.updateContact(contactToSave);
                    } else {
                        contactsDatabase.addContact(contactToSave);
                    }

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
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


}