package com.example.kauppalista;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductListHolder extends RecyclerView.ViewHolder {

    ImageView removeImage, editImage;
    TextView productName;
    EditText editProductName;
    public ProductListHolder(@NonNull View itemView) {
        super(itemView);
        removeImage = itemView.findViewById(R.id.imgDelete);
        editImage = itemView.findViewById(R.id.imgEdit);
        productName = itemView.findViewById(R.id.txtProductName);
        editProductName = itemView.findViewById(R.id.editProductNameText);
    }
}
