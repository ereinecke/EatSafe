package com.ereinecke.eatsafe.ui;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.ereinecke.eatsafe.R;
import com.ereinecke.eatsafe.services.OpenFoodService;
import com.ereinecke.eatsafe.util.Constants;
import com.ereinecke.eatsafe.util.Utility;

/**
 * SearchFragment allows the user to search the database by UPC code.  The code can be scanned
 * or entered manually.
 */

public class SearchFragment extends Fragment {

    private static final String LOG_TAG = SearchFragment.class.getSimpleName();
    private static final String BARCODE_CONTENT = "barcodeContent";
    private EditText barcodeView;
    private static View rootView;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_search, container, false);

        barcodeView = (EditText) rootView.findViewById(R.id.barcode);

        // TODO: requires a press on search button.  Add a button handler to search icon.
        barcodeView.setOnEditorActionListener(
            new EditText.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_ACTION_DONE ||
                        event.getAction() == KeyEvent.ACTION_DOWN &&
                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                        String barcodeStr = v.getText().toString();

                        if (!Utility.validateBarcode(barcodeStr)) {

                            // TODO: This Snackbar should probably be LENGTH_INDEFINITE
                            Snackbar.make(rootView, getString(R.string.barcode_validation_failed, barcodeStr),
                                    Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).setDuration(5000).show();
                        } else if (checkConnectivity()) {
                            // Have a (potentially) valid barcode, fetch product info
                            Intent bookIntent = new Intent(getActivity(), OpenFoodService.class);
                            bookIntent.putExtra(Constants.BARCODE, barcodeStr);
                            bookIntent.setAction(Constants.FETCH_PRODUCT);
                            getActivity().startService(bookIntent);
                        }
                        return true; // consume.
                    }
                    return false; // pass on to other listeners.
                }
            });

        rootView.findViewById(R.id.scan_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ZXing is called here
                Context context = getActivity();

                // TODO: IntentIntegrator part of ZXing, currently not being recognized.
                /*
                if (checkConnectivity()) {
                    try {
                        IntentIntegrator integrator = new IntentIntegrator(getActivity());
                        integrator.initiateScan();
                    } catch (Exception e) {
                        Snackbar.make(view, getActivity().getString(R.string.result_failed),
                                Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                } */
            }
        });

        rootView.findViewById(R.id.scan_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.launchScanner(view);
            }
        });

        if (savedInstanceState != null) {
            barcodeView.setText(savedInstanceState.getString(BARCODE_CONTENT));
        }
        return rootView;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (barcodeView != null) {
            outState.putString(BARCODE_CONTENT, barcodeView.getText().toString());
        }
    }

    /* Returns a boolean representing internet connectivity */
    private boolean checkConnectivity() {
        // Check to see if internet connection available
        Context context = getActivity();
        CharSequence text;
        boolean isConnected;

        ConnectivityManager cm = (ConnectivityManager) context.
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        text = getString(R.string.no_internet);
        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            isConnected = true;
        } else {
            isConnected = false;
            Snackbar.make(rootView, text, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

        return isConnected;
    }
}
