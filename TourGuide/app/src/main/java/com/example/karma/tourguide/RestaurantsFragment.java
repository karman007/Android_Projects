package com.example.karma.tourguide;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantsFragment extends Fragment {


    public RestaurantsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_view, container, false);

        final ArrayList<Information> informations = new ArrayList<>();
        informations.add(new Information(getString(R.string.bicicletta),
                getString(R.string.bicicletta_des),
                getString(R.string.bicicletta_loc),
                R.drawable.bicicletta));
        informations.add(new Information(getString(R.string.teatro),
                getString(R.string.teatro_des),
                getString(R.string.teatro_loc),
                R.drawable.teatro_vivaldi));
        informations.add(new Information(getString(R.string.pappaRich),
                getString(R.string.pappaRich_des),
                getString(R.string.pappaRich_loc),
                R.drawable.papparich7));
        informations.add(new Information(getString(R.string.bAKER),
                getString(R.string.bAKER_Des),
                getString(R.string.bAKER_loc),
                R.drawable.baker));
        informations.add(new Information(getString(R.string.parlour),
                getString(R.string.parlour_des),
                getString(R.string.parlour_loc),
                R.drawable.parlour));
        final MyInformationAdapter adapter = new MyInformationAdapter(getActivity(), informations);

        ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Information info = informations.get(position);

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(info.getLocation()));
                startActivity(intent);
            }
        });
        return rootView;
    }

}
