package com.marwaeltayeb.souq.view;

import static com.marwaeltayeb.souq.utils.Constant.ORDER;
import static com.marwaeltayeb.souq.utils.Constant.PRODUCT;
import static com.marwaeltayeb.souq.utils.Constant.PRODUCTID;
import static com.marwaeltayeb.souq.utils.InternetUtils.isNetworkConnected;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.adapter.CartAdapter;
import com.marwaeltayeb.souq.adapter.OrderedProductAdapter;
import com.marwaeltayeb.souq.databinding.ActivityStatusBinding;
import com.marwaeltayeb.souq.model.Order;
import com.marwaeltayeb.souq.model.Product;
import com.marwaeltayeb.souq.storage.LoginUtils;
import com.marwaeltayeb.souq.viewmodel.CartViewModel;
import com.marwaeltayeb.souq.viewmodel.OrderedProductViewModel;

import java.util.List;

public class StatusActivity extends AppCompatActivity {

    private int productId;

    private List<Product> favoriteList;

    private OrderedProductAdapter cartAdapter;

    private OrderedProductViewModel orderedProductViewModel;

    ActivityStatusBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_status);
        // orderedProductViewModel = ViewModelProviders.of(this).get(OrderedProductViewModel.class);

        // Receive Order object
        Intent intent = getIntent();
        Order order = (Order) intent.getSerializableExtra(ORDER);

//        productId = order.getProductId();
        binding.orderDate.setText(order.getOrderDate());
        binding.orderNumber.setText(order.getOrderNumber());
        binding.userName.setText(order.getUserName());
        binding.userAddress.setText(order.getShippingAddress());
        binding.userPhone.setText(order.getShippingPhone());
//        binding.txtProductName.setText(order.getProductName());
//        binding.txtProductPrice.setText(String.valueOf(order.getProductPrice()));
        String status = getString(R.string.item, order.getOrderDateStatus());
        binding.orderStatus.setText(status);

        //binding.reOrder.setOnClickListener(this);
        setUpRecyclerView();
        getProductsInCart(order);
    }

    private void setUpRecyclerView() {
        binding.productsInOrder.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.productsInOrder.setHasFixedSize(true);
        orderedProductViewModel = ViewModelProviders.of(this).get(OrderedProductViewModel.class);
    }

    private void getProductsInCart(Order order) {
        if (isNetworkConnected(this)) {
            orderedProductViewModel.getProductsInOrder(Integer.parseInt(order.getOrderNumber())).observe(this, cartApiResponse -> {
                if (cartApiResponse != null) {
                    favoriteList = cartApiResponse.getProductsInOrder();
//                    if (favoriteList.size() == 0) {
//                        binding.noBookmarks.setVisibility(View.VISIBLE);
//                        binding.emptyCart.setVisibility(View.VISIBLE);
//                    } else {
//                        binding.recylerviewScrollview.setVisibility(View.VISIBLE);
//                    }
                    cartAdapter = new OrderedProductAdapter(getApplicationContext(), favoriteList, product -> {
                        Intent intent = new Intent(this, DetailsActivity.class);
                        // Pass an object of product class
                        intent.putExtra(PRODUCT, (product));
                        startActivity(intent);
                    }, this);
                    int totalPrice = 0;
                    for (Product pd : favoriteList) {
                        totalPrice += pd.getProductPrice();
                    }
                    //binding.totalPrice.setText(totalPrice+"");
                }

                //binding.loadingIndicator.setVisibility(View.GONE);
                binding.productsInOrder.setAdapter(cartAdapter);
            });
        } else {
//            binding.emptyCart.setVisibility(View.VISIBLE);
//            binding.loadingIndicator.setVisibility(View.GONE);
//            binding.emptyCart.setText(getString(R.string.no_internet_connection));
        }

//    @Override
//    public void onClick(View view) {
//        if (view.getId() == R.id.reOrder) {
//            Intent reOrderIntent = new Intent(this, OrderProductActivity.class);
//            reOrderIntent.putExtra(PRODUCTID, productId);
//            startActivity(reOrderIntent);
//        }
//    }
    }
}
