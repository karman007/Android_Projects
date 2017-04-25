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
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CafesFragment extends Fragment {


    public CafesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_view, container, false);

        final ArrayList<Information> informations = new ArrayList<>();
        informations.add(new Information(getString(R.string.mocan),
                getString(R.string.mocan_des),
                getString(R.string.mocan_loc),
                R.drawable.mocan_green_grout));
        informations.add(new Information(getString(R.string.cupping_room),
                getString(R.string.cupping_room_des),
                getString(R.string.cupping_room_loc),
                R.drawable.the_cupping_room));
        informations.add(new Information(getString(R.string.fellows),
                getString(R.string.fellows_des),
                getString(R.string.fellows_loc),
                R.drawable.the_fellows));
        informations.add(new Information(getString(R.string.cream),
                getString(R.string.cream_des),
                getString(R.string.cream_loc),
                R.drawable.creamcafe_caff_full));
        informations.add(new Information(getString(R.string.ivy_fox),
                getString(R.string.ivy_fox_des),
                getString(R.string.ivy_fox_loc),
                R.drawable.ivy_and_the_fox));
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
