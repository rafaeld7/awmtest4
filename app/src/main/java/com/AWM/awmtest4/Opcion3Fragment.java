package com.AWM.awmtest4;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class Opcion3Fragment extends Fragment {


    public Opcion3Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /**/
        View view = inflater.inflate(R.layout.fragment_opcion3, container, false);


        ImageButton insta = view.findViewById(R.id.btnInsta);
        ImageButton twit = view.findViewById(R.id.btnTwit);
        ImageButton face = view.findViewById(R.id.btnFace);
        ImageButton awm = view.findViewById(R.id.btnAWM);
        ImageButton trackruptela = view.findViewById(R.id.btnruptela);
        ImageButton iftt = view.findViewById(R.id.btnIFTTT);
        ImageButton tracksolid = view.findViewById(R.id.btntracksolid);

       /*
       Codigo que ejecuta el localizador web de tasmota en el navegador. Se comenta ya que se implemento una solucion similar en EncontrarDisp

       btnLocator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse("http://tasmota.simplethings.work/?"));
                startActivity(i);
            }
        });
*/

       /*Codigo que ejecuta la pagina de la empresa en las redes sociales instagram, twitter y facebook en sus respectivas aplicacion si
       *estan instaladas en el movil de lo contrario se abre en el navegador*/

        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://instagram.com/awmsolutions"));
                startActivity(intent);

            }
        });

        twit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/awmsolutionsrd"));
                startActivity(intent);
            }
        });

        face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Uri uri = Uri.parse("fb://page/1174100919273674/");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.facebook.katana");
                startActivity(likeIng);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.facebook.com/1174100919273674")));
            }

            }
        });

        awm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://awm.do"));
                startActivity(intent);
            }
        });

        trackruptela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://track2.ruptela.com/login"));
                startActivity(intent);
            }
        });

        iftt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ifttt.com/login"));
                startActivity(intent);
            }
        });

        tracksolid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.tracksolid.com/mainFrame"));
                startActivity(intent);
            }
        });



        // Inflate the layout for this fragment
        return view;
    }



}
