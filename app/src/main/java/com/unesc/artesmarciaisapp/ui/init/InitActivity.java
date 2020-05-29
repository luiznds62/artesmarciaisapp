package com.unesc.artesmarciaisapp.ui.init;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.jama.carouselview.CarouselScrollListener;
import com.jama.carouselview.CarouselView;
import com.jama.carouselview.CarouselViewListener;
import com.jama.carouselview.enums.IndicatorAnimationType;
import com.jama.carouselview.enums.OffsetType;
import com.unesc.artesmarciaisapp.MainActivity;
import com.unesc.artesmarciaisapp.R;
import com.unesc.artesmarciaisapp.ui.login.LoginActivity;

public class InitActivity extends AppCompatActivity {
    private int[] images = {R.drawable.undraw_imagination_ok71,R.drawable.undraw_statistic_chart_38b6};

    TextView txvInitTitle;
    TextView txvInitSubtitle;
    Button btnInitStart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        String[] titles = new String[]{"Bem-vindo","Primeiros passos"};
        String[] subTitles = new String[]{
                "Tenha todos seus indicadores na palma da sua mão, quando e onde quiser!",
                "Faça o login ou crie uma nova conta para acessar todos os módulos do app!"
        };

        // Se estiver no Shared que está logado, ou no Google já direciona para a Main
        // Todo verificar no shared
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            Intent intent = new Intent(InitActivity.this, MainActivity.class);
            startActivity(intent);
        }

        CarouselView carouselView = findViewById(R.id.carouselView);

        carouselView.setSize(images.length);
        carouselView.setResource(R.layout.image_carousel_item);
        carouselView.setAutoPlay(false);
        carouselView.enableSnapping(false);
        carouselView.setIndicatorAnimationType(IndicatorAnimationType.SWAP);
        carouselView.setCarouselOffset(OffsetType.CENTER);
        carouselView.setCarouselViewListener(new CarouselViewListener() {
            @Override
            public void onBindView(View view, int position) {
                // Example here is setting up a full image carousel
                ImageView imageView = view.findViewById(R.id.imageCarousel);
                imageView.setImageDrawable(getResources().getDrawable(images[position]));

                txvInitTitle = view.findViewById(R.id.txvInitTitle);
                txvInitSubtitle = view.findViewById(R.id.txvInitSubtitle);
                btnInitStart = view.findViewById(R.id.btnInitStart);

                btnInitStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(InitActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                });

                btnInitStart.setVisibility(View.INVISIBLE);
                txvInitTitle.setText(titles[position]);
                txvInitSubtitle.setText(subTitles[position]);

                carouselView.setCarouselScrollListener(new CarouselScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState, int position) {
                        txvInitTitle.setText(titles[position]);
                        txvInitSubtitle.setText(subTitles[position]);
                        if(position == 1){
                            btnInitStart.setVisibility(View.VISIBLE);
                        }else{
                            btnInitStart.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

                    }
                });
            }
        });
        // After you finish setting up, show the CarouselView
        carouselView.show();
    }
}
