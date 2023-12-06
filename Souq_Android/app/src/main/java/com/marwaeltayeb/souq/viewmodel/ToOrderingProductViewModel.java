package com.marwaeltayeb.souq.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.marwaeltayeb.souq.model.OrderedProducts;
import com.marwaeltayeb.souq.repository.OrderProductRepository;
import com.marwaeltayeb.souq.utils.RequestCallback;

import okhttp3.ResponseBody;

public class ToOrderingProductViewModel extends ViewModel {
    private final OrderProductRepository orderProductRepository;

    public ToOrderingProductViewModel() {
        orderProductRepository = new OrderProductRepository();
    }

    public LiveData<ResponseBody> addToOrderingProduct(OrderedProducts orderedProducts, RequestCallback callback) {
        return orderProductRepository.addToOrderingProduct(orderedProducts, callback);
    }
}
