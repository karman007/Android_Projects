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
public class EntertainmentFragment extends Fragment {


    public EntertainmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_view, container, false);

        final ArrayList<Information> informations = new ArrayList<>();
        informations.add(new Information(getString(R.string.siam),
                getString(R.string.siam_des),
                getString(R.string.siam_loc),
                R.drawable.siam_paragon));
        informations.add(new Information(getString(R.string.berjaya),
                getString(R.string.berjaya_des),
                getString(R.string.berjaya_loc),
                R.drawable.special_offer_right));
        informations.add(new Information(getString(R.string.istanbul),
                getString(R.string.istanbul_des),
                getString(R.string.istanbul_loc),
                R.drawable.cevahir_avm_foto));
        informations.add(new Information(getString(R.string.sm_megamall),
                getString(R.string.sm_megamall_des),
                getString(R.string.sm_megamall_loc),
                R.drawable.mega));
        informations.add(new Information(getString(R.string.new_south_china_mall),
                getString(R.string.new_south_china_mall_des),
                getString(R.string.new_south_china_mall_loc),
                R.drawable.china_mall));
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
