package com.marwaeltayeb.souq.view;

import static com.marwaeltayeb.souq.utils.Constant.PRODUCTID;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.databinding.ActivityShippingAddressBinding;
import com.marwaeltayeb.souq.model.Shipping;
import com.marwaeltayeb.souq.storage.LoginUtils;
import com.marwaeltayeb.souq.viewmodel.ShippingViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ShippingAddressActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivityShippingAddressBinding binding;

    private ShippingViewModel shippingViewModel;
    private ArrayList<Integer> productidList;

    private int totalPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shipping_address);

        shippingViewModel = ViewModelProviders.of(this).get(ShippingViewModel.class);

        binding.proceed.setOnClickListener(this);

        binding.txtName.setText(LoginUtils.getInstance(this).getUserInfo().getName());
        Intent intent = getIntent();
        productidList = intent.getIntegerArrayListExtra(PRODUCTID);
        totalPrice = intent.getIntExtra("totalPrice",20000);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.proceed) {
            addShippingAddress();
        }
    }

    private void addShippingAddress() {
        String address = binding.address.getText().toString().trim();
        String city = binding.city.getText().toString().trim();
        String country = binding.country.getText().toString().trim();
        String zip = binding.zip.getText().toString().trim();
        String phone = binding.phone.getText().toString().trim();
        int userId = LoginUtils.getInstance(this).getUserInfo().getId();
        Log.d("shipping",productidList.size()+"");

//        Random rnd = new Random(0);
//        int number = rnd.nextInt(999999);

        Intent orderProductIntent = new Intent(ShippingAddressActivity.this, OrderProductActivity.class);
        orderProductIntent.putExtra(PRODUCTID,productidList);
        //orderProductIntent.putExtra("orderingId",number);
        orderProductIntent.putExtra("totalPrice",totalPrice);

        orderProductIntent.putExtra("address",address);
        orderProductIntent.putExtra("city",city);
        orderProductIntent.putExtra("country",country);
        orderProductIntent.putExtra("zip",zip);
        orderProductIntent.putExtra("phone",phone);
        orderProductIntent.putExtra("userId",userId);


        startActivity(orderProductIntent);
    }
}
