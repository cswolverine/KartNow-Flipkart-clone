package com.marwaeltayeb.souq.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.marwaeltayeb.souq.model.CartApiResponse;
import com.marwaeltayeb.souq.model.OrderProductApiResponse;
import com.marwaeltayeb.souq.repository.CartRepository;
import com.marwaeltayeb.souq.repository.OrderProductRepository;
import com.marwaeltayeb.souq.repository.OrderProductStatusRepository;

public class OrderedProductViewModel extends ViewModel {

    private final OrderProductStatusRepository cartRepository;

    public OrderedProductViewModel() {
        cartRepository = new OrderProductStatusRepository();
    }

    public LiveData<OrderProductApiResponse> getProductsInOrder(int userId) {
        return cartRepository.getProductsInOrder(userId);
    }
}
