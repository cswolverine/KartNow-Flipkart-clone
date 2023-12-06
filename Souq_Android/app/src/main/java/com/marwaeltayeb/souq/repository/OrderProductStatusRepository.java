package com.marwaeltayeb.souq.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.marwaeltayeb.souq.model.CartApiResponse;
import com.marwaeltayeb.souq.model.OrderProductApiResponse;
import com.marwaeltayeb.souq.net.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderProductStatusRepository {

    private static final String TAG = OrderProductStatusRepository.class.getSimpleName();

    public LiveData<OrderProductApiResponse> getProductsInOrder(int userId) {
        final MutableLiveData<OrderProductApiResponse> mutableLiveData = new MutableLiveData<>();

        RetrofitClient.getInstance().getApi().getProductsInOrder(userId).enqueue(new Callback<OrderProductApiResponse>() {
            @Override
            public void onResponse(Call<OrderProductApiResponse> call, Response<OrderProductApiResponse> response) {
                Log.d(TAG, "onResponse: Succeeded");

                OrderProductApiResponse orderProductApiResponse = response.body();

                if (response.body() != null) {
                    mutableLiveData.setValue(orderProductApiResponse);
                    Log.d(TAG, String.valueOf(response.body().getProductsInOrder()));
                }
            }

            @Override
            public void onFailure(Call<OrderProductApiResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
        return mutableLiveData;
    }
}
