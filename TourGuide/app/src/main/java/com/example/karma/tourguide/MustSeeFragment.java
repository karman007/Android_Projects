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
public class MustSeeFragment extends Fragment {


    public MustSeeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_view, container, false);

        final ArrayList<Information> informations = new ArrayList<>();
        informations.add(new Information(getString(R.string.great_barrier_reef),
                getString(R.string.great_barrier_reef_des),
                getString(R.string.great_barrier_reef_loc),
                R.drawable.greatbarrierreef));
        informations.add(new Information(getString(R.string.pyramids_of_giza),
                getString(R.string.pyramids_of_giza_des),
                getString(R.string.pyramids_of_giza_loc),
                R.drawable.all_gizah_pyramids));
        informations.add(new Information(getString(R.string.stonehenge),
                getString(R.string.stonehenge_des),
                getString(R.string.stonehenge_loc),
                R.drawable.stonehenge_circle_pink_sky));
        informations.add(new Information(getString(R.string.grand_canyon),
                getString(R.string.grand_canyon_des),
                getString(R.string.grand_canyon_loc),
                R.drawable.grandcanyon));
        informations.add(new Information(getString(R.string.easter_island),
                getString(R.string.easter_island_des),
                getString(R.string.easter_island_loc),
                R.drawable.easter_island));
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
