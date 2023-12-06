package com.marwaeltayeb.souq.adapter;


import static com.marwaeltayeb.souq.utils.Constant.LOCALHOST;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.databinding.CartListItemBinding;
import com.marwaeltayeb.souq.databinding.OrderedProductListItemBinding;
import com.marwaeltayeb.souq.model.Product;
import com.marwaeltayeb.souq.viewmodel.AddFavoriteViewModel;
import com.marwaeltayeb.souq.viewmodel.FromCartViewModel;
import com.marwaeltayeb.souq.viewmodel.RemoveFavoriteViewModel;

import java.text.DecimalFormat;
import java.util.List;

public class OrderedProductAdapter extends RecyclerView.Adapter<OrderedProductAdapter.OrderedProductViewHolder> {

    private final Context mContext;

    private final List<Product> productsInCart;

    private Product currentProduct;


    private final OrderedProductAdapter.OrderedProductAdapterOnClickHandler clickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface OrderedProductAdapterOnClickHandler {
        void onClick(Product product);
    }

    public OrderedProductAdapter(Context mContext, List<Product> productInCart, OrderedProductAdapter.OrderedProductAdapterOnClickHandler clickHandler, FragmentActivity activity) {
        this.mContext = mContext;
        this.productsInCart = productInCart;
        this.clickHandler = clickHandler;
    }

    @NonNull
    @Override
    public OrderedProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        OrderedProductListItemBinding cartListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.ordered_product_list_item, parent, false);
        return new OrderedProductAdapter.OrderedProductViewHolder(cartListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderedProductViewHolder holder, int position) {
        currentProduct = productsInCart.get(position);
        holder.binding.txtProductName.setText(currentProduct.getProductName());

        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String formattedPrice = formatter.format(currentProduct.getProductPrice());
        holder.binding.txtProductPrice.setText("Rs. " + formattedPrice);

        // Load the Product image into ImageView
        String imageUrl = LOCALHOST + currentProduct.getProductImage().replaceAll("\\\\", "/");
        Glide.with(mContext)
                .load(imageUrl)
                .into(holder.binding.imgProductImage);

        // If product is inserted
//        if (currentProduct.isFavourite() == 1) {
//            holder.binding.imgFavourite.setImageResource(R.drawable.ic_favorite_pink);
//        }
    }

    @Override
    public int getItemCount() {
        if (productsInCart == null) {
            return 0;
        }
        return productsInCart.size();
    }

    class OrderedProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Create view instances
        private final OrderedProductListItemBinding binding;

        private OrderedProductViewHolder(OrderedProductListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            // Register a callback to be invoked when this view is clicked.
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getBindingAdapterPosition();
            // Get position of the product
            currentProduct = productsInCart.get(position);

            switch (view.getId()) {
                case R.id.card_view:
                    // Send product through click
                    clickHandler.onClick(currentProduct);
                    break;
                default: // Should not get here
            }
        }
    }
}