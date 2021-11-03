package com.test.buycrypto;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;

import com.blankj.utilcode.util.ToastUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

/**
 * Created time : 2021/11/3 16:08.
 *
 * @author 10585
 */
public class EnterAmountActivity extends AppCompatActivity {


    private AppCompatEditText mEdtAmount;
    private AppCompatButton mBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_amount);
        mEdtAmount = findViewById(R.id.edt_amount);
        mBtn = findViewById(R.id.btn);
        Intent intent = getIntent();
        String symbol = intent.getStringExtra("symbol");
        String address = intent.getStringExtra("address");

        mBtn.setOnClickListener(v -> {
            Editable amountText = mEdtAmount.getText();
            if (TextUtils.isEmpty(amountText)) {
                ToastUtils.showShort("请输入金额");
                return;
            }
            startActivity(new Intent(this, SupplierActivity.class)
                    .putExtra("address", address)
                    .putExtra("symbol", symbol)
                    .putExtra("amount", amountText.toString()));
        });
    }
}