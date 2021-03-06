package com.ereinecke.eatsafe.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.ereinecke.eatsafe.R;
import com.ereinecke.eatsafe.util.Constants;

/**
 * Creates a dialog asking the user if they want to upload new data.
 */

public class DeleteDialog  extends DialogFragment {

    private final static String LOG_TAG = DeleteDialog.class.getSimpleName();

    public DeleteDialog newInstance(String barcode) {

        DeleteDialog dialog = new DeleteDialog();
        Bundle args = new Bundle();
        args.putString(Constants.DIALOG_TYPE, Constants.DIALOG_DELETE );
        args.putString(Constants.BARCODE_KEY, barcode);
        dialog.setArguments(args);
        return dialog;
    }


    // Use this instance of the interface to deliver action events
    private NoticeDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.delete_dialog)
                .setPositiveButton(R.string.delete_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                        mListener.onDialogPositiveClick(DeleteDialog.this);
                    }
                })
                .setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogNegativeClick(DeleteDialog.this);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it.
     */
    @SuppressWarnings("EmptyMethod")
    public interface NoticeDialogListener {
        void onDialogPositiveClick(DialogFragment dialog);
        void onDialogNegativeClick(DialogFragment dialog);
    }

}