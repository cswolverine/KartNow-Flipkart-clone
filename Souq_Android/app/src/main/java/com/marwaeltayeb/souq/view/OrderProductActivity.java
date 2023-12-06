package com.marwaeltayeb.souq.view;

import static com.marwaeltayeb.souq.utils.Constant.PRODUCTID;
import static com.marwaeltayeb.souq.utils.Constant.PRODUCT_ID;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.databinding.ActivityOrderProductBinding;
import com.marwaeltayeb.souq.model.Cart;
import com.marwaeltayeb.souq.model.OrderedProducts;
import com.marwaeltayeb.souq.model.Ordering;
import com.marwaeltayeb.souq.model.Product;
import com.marwaeltayeb.souq.model.Shipping;
import com.marwaeltayeb.souq.storage.LoginUtils;
import com.marwaeltayeb.souq.utils.RequestCallback;
import com.marwaeltayeb.souq.viewmodel.OrderingViewModel;
import com.marwaeltayeb.souq.viewmodel.ShippingViewModel;
import com.marwaeltayeb.souq.viewmodel.ToOrderingProductViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class OrderProductActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivityOrderProductBinding binding;
    private OrderingViewModel orderingViewModel;
    private ToOrderingProductViewModel toOrderingProductViewModel;

    private ShippingViewModel shippingViewModel;

    private ArrayList<Integer> productIdList;
    private Product product;

    private String orderId="";

    private int orderingId;

    private int totalPrice;
    private String address="";
    private String city="";
    private String country="";
    private String zip="";
    private String phone="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_product);

        orderingViewModel = ViewModelProviders.of(this).get(OrderingViewModel.class);
        toOrderingProductViewModel = ViewModelProviders.of(this).get(ToOrderingProductViewModel.class);
        shippingViewModel = ViewModelProviders.of(this).get(ShippingViewModel.class);
        product = new Product();
        binding.addCard.setOnClickListener(this);
        Intent intent = getIntent();
        productIdList = intent.getIntegerArrayListExtra(PRODUCTID);
        //orderingId = intent.getIntExtra("orderingId",0);
        totalPrice = intent.getIntExtra("totalPrice",20000);
        address = intent.getStringExtra("address");
        city = intent.getStringExtra("city");
        country = intent.getStringExtra("country");
        zip = intent.getStringExtra("zip");
        phone = intent.getStringExtra("phone");
        Random rnd = new Random();
        int number = rnd.nextInt(999999)+100000;
        orderingId = number;
        populateSpinner();
    }

    private void orderProduct() {
        String nameOnCard = binding.nameOnCard.getText().toString().trim();
        String cardNumber = binding.cardNumber.getText().toString().trim();

        String year = binding.spinnerYearMenu.getEditableText().toString().toLowerCase();
        String month = binding.spinnerMonthMenu.getEditableText().toString().toLowerCase();
        String fullDate = year + "-" + month + "-00";

        int userId = LoginUtils.getInstance(this).getUserInfo().getId();
        Log.d("orderProduct",productIdList.size()+"");

        if(productIdList.size() == 1) {
            Ordering ordering = new Ordering(nameOnCard, cardNumber, fullDate, userId, totalPrice,orderingId);

            orderingViewModel.orderProduct(ordering).observe(this, responseBody -> {
                try {
                    //Log.d("response", responseBody.string());
                    orderId=responseBody.string();
                    Log.d("data",orderId);
                    for(int productId: productIdList) {
                        Shipping shipping = new Shipping(address, city, country, zip, phone, userId, productId, Integer.parseInt(orderId));
                        shippingViewModel.addShippingAddress(shipping).observe(this, responseBody2 -> {
                            try {
                                Toast.makeText(this, responseBody2.string() + "", Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                    insertToOrdering(() -> product.setIsOrdered(true),productIdList.get(0),orderId);
                    finish();
                    Intent homeIntent = new Intent(OrderProductActivity.this, ProductActivity.class);
                    startActivity(homeIntent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        else{

            Ordering ordering = new Ordering(nameOnCard, cardNumber, fullDate, userId, totalPrice,orderingId);
            orderingViewModel.orderProduct(ordering).observe(this, responseBody -> {
                try {
                    //Log.d("response", responseBody.string());
                    orderId= responseBody.string();
                    Log.d("data",orderId);
                    for(int productId: productIdList) {
                        Shipping shipping = new Shipping(address, city, country, zip, phone, userId, productId, Integer.parseInt(orderId));
                        shippingViewModel.addShippingAddress(shipping).observe(this, responseBody2 -> {
                            try {
                                Toast.makeText(this, responseBody2.string() + "", Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                    for(int i=0;i<productIdList.size();i++) {
                        insertToOrdering(() -> product.setIsOrdered(true),productIdList.get(i),orderId);
                    }
                    finish();
                    Intent homeIntent = new Intent(OrderProductActivity.this, ProductActivity.class);
                    startActivity(homeIntent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });



        }

    }

    private void insertToOrdering(RequestCallback callback, int i, String orderId) {
        OrderedProducts orderedProducts = new OrderedProducts(Integer.parseInt(orderId), i);
        toOrderingProductViewModel.addToOrderingProduct(orderedProducts, callback);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.addCard) {
            orderProduct();
        }
    }

    private void populateSpinner() {
        String[] years = {"2023","2024","2025","2026","2027","2028","2029","2030"};
        ArrayAdapter<CharSequence> yearsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, years );
        binding.spinnerYearMenu.setAdapter(yearsAdapter);

        String[] months = {"01","02","03","04","05","06","07","08","09","10","11","12"};
        ArrayAdapter<CharSequence> monthsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, months );
        binding.spinnerMonthMenu.setAdapter(monthsAdapter);
    }
}
