package com.cansoft.app.fragments;


import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cansoft.app.activity.MainActivity;
import com.cansoft.app.R;
import com.cansoft.app.model.About;
import com.cansoft.app.model.AboutD;
import com.cansoft.app.model.AppInfo;
import com.cansoft.app.model.InfoD;
import com.cansoft.app.network.RestClient2;
import com.cansoft.app.widget.IconTextView;
import com.marcoscg.licenser.Library;
import com.marcoscg.licenser.License;
import com.marcoscg.licenser.LicenserDialog;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {
    private TextView aboutDetails;
    private IconTextView appInfoBtn;
    private View viewC;


    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_about, container, false);

        Toolbar toolbar = (Toolbar)getActivity().findViewById(R.id.app_bar);
        toolbar.setTitle("");
        aboutDetails = (TextView) view.findViewById(R.id.about_details);
        //appInfoBtn = (IconTextView) view.findViewById(R.id.app_infoBtn);

        setHasOptionsMenu(true);

        RestClient2.getInstance().callRetrofit(view.getContext()).getAbout().enqueue(new Callback<AboutD>() {
            @Override
            public void onResponse(Call<AboutD> call, Response<AboutD> response) {
                List<About> about = response.body().getData();
                aboutDetails.setText(about.get(0).getDetails());
            }

            @Override
            public void onFailure(Call<AboutD> call, Throwable t) {

            }
        });
        showDrawerButton();
        /*appInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPopUpInfo(view);
            }
        });*/

        setViewC(view);
        return view;
    }

    private void showPopUpInfo(final View view) {
        final Dialog dialog = new Dialog(view.getContext());
        dialog.setContentView(R.layout.app_info);
        dialog.setCancelable(false);


        // set the custom dialog components - text, image and button
        final WebView projectInfoWeb = (WebView) dialog.findViewById(R.id.project_info_web);
      //  final TextView projectInfo = (TextView) dialog.findViewById(R.id.project_info);
        final TextView licencesInfo = (TextView) dialog.findViewById(R.id.license_text);

        final ImageButton closeBtn = (ImageButton) dialog.findViewById(R.id.info_closeBtn);


        RestClient2.getInstance().callRetrofit(view.getContext()).getAppInfo().enqueue(new Callback<InfoD>() {
            @Override
            public void onResponse(Call<InfoD> call, Response<InfoD> response) {
                if (response.body() == null){
                    Toast.makeText(view.getContext(), "Please connect to the internet", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }else {
                List<AppInfo> info = response.body().getData();
                    String html = info.get(0).getProjectInfo();
                    String mime = "text/html";
                    String encoding = "utf-8";
                    projectInfoWeb.getSettings().setJavaScriptEnabled(true);
                    projectInfoWeb.loadData(html,mime,encoding);
                   // projectInfo.setText(info.get(0).getProjectInfo());

                }
            }

            @Override
            public void onFailure(Call<InfoD> call, Throwable t) {

            }
        });

        // if button is clicked, close the custom dialog
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        licencesInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LicenserDialog(view.getContext())
                        .setTitle("Licenses")
                        .setCustomNoticeTitle("Notices for files:")
                        .setBackgroundColor(Color.WHITE) // Optional
                        .setLibrary(new Library("Licenser",
                                "https://github.com/marcoscgdev/Licenser",
                                License.MIT))
                        .setLibrary(new Library("Glide",
                                "https://github.com/bumptech/glide",
                                License.MIT))
                        .setLibrary(new Library("Material-sheet-fab",
                                "https://github.com/gowong/material-sheet-fab",
                                License.MIT))
                        .setLibrary(new Library("Retrofit",
                                "https://github.com/square/retrofit",
                                License.APACHE))
                        .setLibrary(new Library("Picasso",
                                "https://square.github.io/picasso/",
                                License.APACHE))
                        .setLibrary(new Library("Html-textview",
                                "https://github.com/SufficientlySecure/html-textview",
                                License.APACHE))
                        .setLibrary(new Library("Material-ripple",
                                "https://github.com/balysv/material-ripple",
                                License.APACHE))
                        .setLibrary(new Library("SmartCache",
                                "https://github.com/dimitrovskif/SmartCache",
                                License.APACHE))
                        .setLibrary(new Library("Shortbread",
                                "https://github.com/MatthiasRobbers/shortbread",
                                License.APACHE))
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // TODO: 11/02/2018
                            }
                        })
                        .show();
            }
        });


        dialog.show();

    }

    public void showDrawerButton() {

            ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.bar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bar_promotion :
                showPopUpInfo(getViewC());
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private View getViewC() {
        return viewC;
    }

    private void setViewC(View viewC) {
        this.viewC = viewC;
    }


}
