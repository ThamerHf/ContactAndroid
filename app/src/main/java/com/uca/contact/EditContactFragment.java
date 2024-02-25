package com.uca.contact;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.uca.contact.model.Contact;
import com.uca.contact.model.ContactsDatabase;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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

    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia;

    private Contact contactToSave;

    private byte[] imageBeforeUpload;

    private Boolean isModifiedImage;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        contactToSave = new Contact();
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
        if (contact.getPhoto() == null) {
            ((ImageView)binding.findViewById(R.id.photo)).setImageResource(R.drawable.ic_user);
        } else {
            Bitmap bitmap = BitmapFactory
                    .decodeByteArray(contact.getPhoto(), 0,
                            contact.getPhoto().length);
            ((ImageView)binding.findViewById(R.id.photo)).setImageBitmap(bitmap);
            imageBeforeUpload = contact.getPhoto();
        }

        Button saveButton = binding.findViewById(R.id.save);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactToSave.setTel(telText.getText().toString());
                contactToSave.setName(nameText.getText().toString());
                contactToSave.setAddress(addressText.getText().toString());
                if (contactToSave.getPhoto() == null) {
                    contactToSave.setPhoto(imageBeforeUpload);
                }
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

                    List<Contact> c = contactsDatabase.getAllContacts();

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

        ImageView imageView = binding.findViewById(R.id.photo); // Assuming you have an ImageView in your layout
        Button addButton = binding.findViewById(R.id.addButton);
        Button deleteButton = binding.findViewById(R.id.deleteButton);
        Button cancelImageUpload = binding.findViewById(R.id.cancelUploadImage);

        pickMedia = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            if (uri != null) {
                imageView.setImageURI(uri);
                try {
                    InputStream iStream = requireContext().getContentResolver().openInputStream(uri);
                    contactToSave.setPhoto(getBytes(iStream));
                    cancelImageUpload.setVisibility(View.VISIBLE);
                } catch (FileNotFoundException e) {
                    Log.e("YourTag", "File not found", e);
                    // Handle the exception or show an error to the user
                } catch (IOException e) {
                    Log.e("YourTag", "IOException", e);
                    // Handle the exception or show an error to the user
                }
            } else {
                Log.d("PhotoPicker", "No media selected");
            }
        });

        boolean isButtonsVisible = addButton.getVisibility() == View.VISIBLE;
        addButton.setVisibility(isButtonsVisible ? View.GONE : View.VISIBLE);
        if (imageBeforeUpload != null) {
            isButtonsVisible = deleteButton.getVisibility() == View.VISIBLE;
            deleteButton.setVisibility(isButtonsVisible ? View.GONE : View.VISIBLE);
        }

        if (contactToSave.getPhoto() != null) {
            isButtonsVisible = cancelImageUpload.getVisibility() == View.VISIBLE;
            cancelImageUpload.setVisibility(isButtonsVisible ? View.GONE : View.VISIBLE);
        }

        addButton.setOnClickListener(v -> {
            pickMedia.launch(new PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageAndVideo.INSTANCE)
                    .build());
        });

        deleteButton.setOnClickListener(v -> {
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat
                    .app.AlertDialog.Builder(requireContext());
            builder.setTitle("Image deletion")
                    .setMessage("Are you sure you want to delete the image?")
                    .setPositiveButton("yes", (dialog, which) -> {
                        contactToSave.setPhoto(null);
                        ((ImageView)binding.findViewById(R.id.photo))
                                .setImageResource(R.drawable.ic_user);
                        imageBeforeUpload = null;
                        deleteButton.setVisibility(View.GONE);
                        cancelImageUpload.setVisibility(View.GONE);
                    })
                    .setNegativeButton("no", null) // No action for cancel button
                    .show();
        });

        cancelImageUpload.setOnClickListener(v -> {
            if (imageBeforeUpload != null) {
                Bitmap bitmap = BitmapFactory
                        .decodeByteArray(imageBeforeUpload, 0,
                                imageBeforeUpload.length);
                ((ImageView)binding.findViewById(R.id.photo)).setImageBitmap(bitmap);
            } else {
                ((ImageView)binding.findViewById(R.id.photo)).setImageResource(R.drawable.ic_user);
            }

            contactToSave.setPhoto(null);
            cancelImageUpload.setVisibility(View.GONE);
        });

        return binding;

    }


    public static byte[] getBytes(InputStream inputStream) throws IOException {

        byte[] bytesResult = null;
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        try {
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }
            bytesResult = byteBuffer.toByteArray();
        } finally {
            // close the stream
            try{ byteBuffer.close(); } catch (IOException ignored){ /* do nothing */ }
        }
        return bytesResult;
    }


}