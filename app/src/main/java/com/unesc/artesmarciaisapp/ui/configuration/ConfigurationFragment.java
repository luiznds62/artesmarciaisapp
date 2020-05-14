package com.unesc.artesmarciaisapp.ui.configuration;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.unesc.artesmarciaisapp.ui.login.LoginActivity;
import com.unesc.artesmarciaisapp.R;
import com.unesc.artesmarciaisapp.services.CicleImageLoadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class ConfigurationFragment extends Fragment {

    TextView txvConfigurationsUsername;
    TextView txvConfigurationsEmail;
    CircleImageView imvConfigurationsUserImg;
    Button btnLogout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_configuration, container, false);

        txvConfigurationsUsername = root.findViewById(R.id.txvConfigurationsUsername);
        txvConfigurationsEmail = root.findViewById(R.id.txvConfigurationsEmail);
        imvConfigurationsUserImg = root.findViewById(R.id.imvConfigurationsUserImg);

        try {
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(root.getContext());
            txvConfigurationsUsername.setText("Olá, " + account.getDisplayName() + "!");
            txvConfigurationsEmail.setText(account.getEmail());
            new CicleImageLoadTask(account.getPhotoUrl().toString(), imvConfigurationsUserImg).execute();
        }catch(Exception e){

        }

        btnLogout = root.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                new AlertDialog.Builder(v.getContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Dúvida:")
                        .setMessage("Deseja realmente fazer o logout?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                        .requestEmail()
                                        .build();

                                final GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(v.getContext(), gso);

                                mGoogleSignInClient.signOut().addOnCompleteListener((Activity) v.getContext(), new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Intent intent = new Intent(v.getContext(), LoginActivity.class);
                                        startActivity(intent);
                                    }
                                });
                            }

                        })
                        .setNegativeButton("Não", null)
                        .show();
            }
        });

        return root;
    }

}
