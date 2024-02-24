package com.uca.contact;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.uca.contact.model.Contact;
import com.uca.contact.model.ContactsDatabase;

import java.util.ArrayList;
import java.util.List;

public class ContactsAdapter extends ArrayAdapter<Contact> implements Filterable {

    private List<Contact> originalContacts;
    private List<Contact> filteredContacts;
    private LayoutInflater inflater;
    private Context context;

    public ContactsAdapter(@NonNull Context context, int resource,
                           @NonNull List<Contact> contacts) {
        super(context, resource, contacts);
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.originalContacts = new ArrayList<>(contacts);
        this.filteredContacts = new ArrayList<>(contacts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_list, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Récupérer l'élément à cette position
        Contact contactItem = getItem(position);
        System.out.println(contactItem.getContactId());

        // Remplir la vue avec les données de l'élément
        viewHolder.contactNameTextView.setText(contactItem.getName());
        // Modifiez l'image ici si nécessaire (par exemple, à partir de ressources, d'URL, etc.)
        if (contactItem.getPhoto() == null) {
            viewHolder.contactImageView.setImageResource(R.drawable.ic_user);
        } else {
            Bitmap bitmap = BitmapFactory
                    .decodeByteArray(contactItem.getPhoto(), 0,
                            contactItem.getPhoto().length);
            viewHolder.contactImageView.setImageBitmap(bitmap);
        }

        ImageButton callButton = convertView.findViewById(R.id.callButton);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View v) {


                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel: " + contactItem.getTel()));
                v.getContext().startActivity(intent);
            }

        });

        ImageButton editButton = convertView.findViewById(R.id.edit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer une instance du fragment SecondFragment
                EditContactFragment editContactFragment = new EditContactFragment();

                editContactFragment.setContactId(contactItem.getContactId());
                editContactFragment.setMode("edit");
                // Remplacer le fragment actuel par le SecondFragment
                FragmentTransaction transaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_view, editContactFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        ImageButton deleteButton = convertView.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Contact deletion")
                        .setMessage("Are you sure you want to delete the contact?")
                        .setPositiveButton("yes", (dialog, which) -> {
                            ContactsDatabase contactsDatabase = new ContactsDatabase(context);
                            contactsDatabase.deleteContact(contactItem.getContactId());
                            Intent intent = new Intent(context, MainActivity.class);
                            context.startActivity(intent);
                        })
                        .setNegativeButton("no", null) // No action for cancel button
                        .show();
            }
        });

        return convertView;
    }

    private static class ViewHolder {
        TextView contactNameTextView;
        ImageView contactImageView;

        ViewHolder(View view) {
            contactNameTextView = view.findViewById(R.id.contactNameTextView1);
            contactImageView = view.findViewById(R.id.initialTextView);
        }
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString().toLowerCase().trim();
                if (charString.isEmpty()) {
                    filteredContacts = new ArrayList<>(originalContacts);
                } else {
                    List<Contact> filteredList = new ArrayList<>();
                    for (Contact contact : originalContacts) {
                        if (contact.getName().toLowerCase().contains(charString)) {
                            filteredList.add(contact);
                        }
                    }
                    filteredContacts = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredContacts;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredContacts = (List<Contact>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getCount() {
        return filteredContacts.size();
    }

    @Override
    public Contact getItem(int position) {
        return filteredContacts.get(position);
    }
}

