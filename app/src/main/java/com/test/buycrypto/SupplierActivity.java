package com.test.buycrypto;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created time : 2021/11/3 16:18.
 *
 * @author 10585
 */
public class SupplierActivity extends AppCompatActivity {

    private LinearLayout mLytSupplier1;
    private LinearLayout mLytSupplier2;
    private LinearLayout mLytSupplier3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier);
        mLytSupplier1 = findViewById(R.id.lyt_supplier1);
        mLytSupplier2 = findViewById(R.id.lyt_supplier2);
        mLytSupplier3 = findViewById(R.id.lyt_supplier3);
        Intent intent = getIntent();
        String symbol = intent.getStringExtra("symbol");
        String address = intent.getStringExtra("address");
        String amount = intent.getStringExtra("amount");

        mLytSupplier1.setOnClickListener(v -> {
            String url = GenerateUrlUtils.generateUrl("Moonpay", symbol, amount, "USD", address);
            startActivity(new Intent(this, WebActivity.class)
                    .putExtra("url", url));
        });
        mLytSupplier2.setOnClickListener(v -> {
            String url = GenerateUrlUtils.generateUrl("Mercuryo", symbol, amount, "USD", address);
            startActivity(new Intent(this, WebActivity.class)
                    .putExtra("url", url));
        });
        mLytSupplier3.setOnClickListener(v -> {
            String url = GenerateUrlUtils.generateUrl("Paxful", symbol, amount, "USD", address);
            startActivity(new Intent(this, WebActivity.class)
                    .putExtra("url", url));
        });

    }
}