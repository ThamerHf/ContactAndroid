package com.uca.contact;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.uca.contact.model.Contact;
import com.uca.contact.model.ContactsDatabase;

import java.util.List;

public class ContactsAdapter extends ArrayAdapter<Contact> {

    private LayoutInflater inflater;

    private Context context;

    public ContactsAdapter(@NonNull Context context, int resource, @NonNull List<Contact> objects) {
        super(context, resource, objects);
        inflater = LayoutInflater.from(context);
        this.context = context;
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

        // Remplir la vue avec les données de l'élément
        viewHolder.contactNameTextView.setText(contactItem.getName());
        // Modifiez l'image ici si nécessaire (par exemple, à partir de ressources, d'URL, etc.)
        viewHolder.contactImageView.setImageResource(R.drawable.ic_user);
        //viewHolder.contactImageView.setImageBitmap(BitmapFactory.decodeByteArray(contactItem.getPhoto(), 0, contactItem.getPhoto().length));

        ImageButton editButton = convertView.findViewById(R.id.edit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer une instance du fragment SecondFragment
                EditContactFragment editContactFragment = new EditContactFragment();

                editContactFragment.setContactTel(contactItem.getTel());
                editContactFragment.setMode("edit");

                /*// Passer des données au SecondFragment si nécessaire
                Bundle bundle = new Bundle();
                bundle.putString("key", "value");
                secondFragment.setArguments(bundle);
*/
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
                        .setMessage("Ary you sure you want to delete the contact?")
                        .setPositiveButton("yes", (dialog, which) -> {
                            ContactsDatabase contactsDatabase = new ContactsDatabase(context);
                            contactsDatabase.deleteContact(contactItem.getTel());
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
}

