package com.dev.bhoopnarayan

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dev.bhoopnarayan.Model.CartModel
import com.dev.bhoopnarayan.Shared.CartRepository // Shared object to hold the cart items
import com.dev.bhoopnarayan.databinding.ActivityMmtcCoinDetailBinding

class MmtcCoinDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMmtcCoinDetailBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMmtcCoinDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Receive the data passed from the previous activity
        val coinName = intent.getStringExtra("coinName") ?: "Unknown"
        val coinPrice = intent.getStringExtra("coinPrice") ?: "Unknown"
        val description = intent.getStringExtra("description") ?: "No Description"
        val img1Path = intent.getStringExtra("img1Path") ?: ""

        // Set the received data in the views
        with(binding) {
            coinDetailName.text = coinName
            coinDetailPrice.text = coinPrice
            coinDetailDescription.text = description

            // Load image using Glide
            if (img1Path.isNotEmpty()) {
                Glide.with(this@MmtcCoinDetailActivity)
                    .load(img1Path)
                    .placeholder(R.drawable.mmtc_gold_preview) // Placeholder image
                    .into(coinDetailImage)
            } else {
                coinDetailImage.setImageResource(R.drawable.mmtc_gold_preview) // Default placeholder
            }
        }

        // Set up the button click listeners
        setOnClickListeners(coinName, img1Path, coinPrice)
    }

    private fun setOnClickListeners(coinName: String, img1Path: String, coinPrice: String) {
        with(binding) {
            // Add to Cart button click listener
            addtocartbutton.setOnClickListener {
                // Add the item to the cart
                addToCart(coinName, img1Path, coinPrice.toDouble())

                // Navigate to CartActivity
                val intent = Intent(this@MmtcCoinDetailActivity, CartActivity::class.java)
                startActivity(intent)
            }

            // Basket button click listener
            basket.setOnClickListener {
                // Navigate directly to CartActivity
                val intent = Intent(this@MmtcCoinDetailActivity, CartActivity::class.java)
                startActivity(intent)
            }
        }
    }

    // Function to add item to the cart
    private fun addToCart(coinName: String, imgPath: String, price: Double) {
        val cartItem = CartModel(coinName, imgPath, price, 1)
        CartRepository.addToCart(cartItem) // Add the item to the cart
        // Optionally, you can show a message to indicate that the item has been added to the cart
    }
}
