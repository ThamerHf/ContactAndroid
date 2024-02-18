package com.uca.contact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.uca.contact.model.Contact;

import java.util.List;

public class ContactsAdapter extends ArrayAdapter<Contact> {

    private LayoutInflater inflater;

    public ContactsAdapter(@NonNull Context context, int resource, @NonNull List<Contact> objects) {
        super(context, resource, objects);
        inflater = LayoutInflater.from(context);
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

        ImageButton totoButton = convertView.findViewById(R.id.edit);
        totoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer une instance du fragment SecondFragment
                EditContactFragment editContactFragment = new EditContactFragment();

                editContactFragment.setContactTel("123");
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

