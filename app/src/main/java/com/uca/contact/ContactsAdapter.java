package com.uca.contact;

import android.content.Context;
import android.os.Bundle;
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

import com.uca.contact.model.Tuple;

import java.util.List;

public class ContactsAdapter extends ArrayAdapter<Tuple<String, Integer>> {

    private LayoutInflater inflater;

    public ContactsAdapter(@NonNull Context context, int resource, @NonNull List<Tuple<String, Integer>> objects) {
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
        Tuple<String, Integer> contactItem = getItem(position);

        // Remplir la vue avec les données de l'élément
        viewHolder.contactNameTextView.setText(contactItem.first);
        // Modifiez l'image ici si nécessaire (par exemple, à partir de ressources, d'URL, etc.)
        viewHolder.contactImageView.setImageResource(contactItem.second);

        ImageButton totoButton = convertView.findViewById(R.id.edit);
        totoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer une instance du fragment SecondFragment
                SecondFragment secondFragment = new SecondFragment();

                /*// Passer des données au SecondFragment si nécessaire
                Bundle bundle = new Bundle();
                bundle.putString("key", "value");
                secondFragment.setArguments(bundle);
*/
                // Remplacer le fragment actuel par le SecondFragment
                FragmentTransaction transaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_view, secondFragment);
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

