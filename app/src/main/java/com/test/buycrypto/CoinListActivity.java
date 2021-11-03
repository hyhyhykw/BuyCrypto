package com.test.buycrypto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created time : 2021/11/3 15:59.
 *
 * @author 10585
 */
public class CoinListActivity extends AppCompatActivity {

    private LinearLayout mLytCoin1;
    private LinearLayout mLytCoin2;
    private LinearLayout mLytCoin3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_list);

        mLytCoin1 = findViewById(R.id.lyt_coin1);
        mLytCoin1.setOnClickListener(v->{
            startActivity(new Intent(this,EnterAmountActivity.class)
            .putExtra("address","bc1qxy2kgdygjrsqtzq2n0yrf2493p83kkfjhx0wlh")
            .putExtra("symbol","BTC"));
        });
        mLytCoin2 = findViewById(R.id.lyt_coin2);

        mLytCoin2.setOnClickListener(v->{
            startActivity(new Intent(this,EnterAmountActivity.class)
                    .putExtra("address","0x56473200cf43aa30380b8c86b96deaa1c67cad6f")
                    .putExtra("symbol","ETH"));
        });
        mLytCoin3 = findViewById(R.id.lyt_coin3);
        mLytCoin3.setOnClickListener(v->{
            startActivity(new Intent(this,EnterAmountActivity.class)
                    .putExtra("address","0x56473200cf43aa30380b8c86b96deaa1c67cad6f")
                    .putExtra("symbol","BNB"));
        });
    }

}