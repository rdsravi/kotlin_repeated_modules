package com.dev.bhoopnarayan

import ShippingDetails
import ShippingResponse
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dev.bhoopnarayan.databinding.ActivityShippingDetailsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShippingDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShippingDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShippingDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        setOnClickListeners()
    }

    private fun initView() {
        // Any initialization logic goes here
    }

    private fun setOnClickListeners() {
        // Pay button click
        binding.paytoproceed.setOnClickListener {
            if (checkAllFields()) {
                // Prepare data to send
                val shippingDetails = ShippingDetails(
                    Name = binding.Name.text.toString(),
                    CNumber = binding.contactNo.text.toString(),
                    Email = binding.email.text.toString(),
                    Address = binding.address.text.toString(),
                    PinCode = binding.pincode.text.toString(),
                    landmarks = binding.landmark.text.toString(),
                    State = binding.state.text.toString(),
                    City = binding.city.text.toString(),
                    Scode = binding.shortcode.text.toString() // Assuming Scode refers to Country code
                )

                // Make API call to post shipping details
                postShippingDetails(shippingDetails)
            }
        }
    }

    private fun postShippingDetails(shippingDetails: ShippingDetails) {
        val call = RetrofitInstance.api.postShippingDetails(shippingDetails) // Make sure RetrofitInstance is defined

        call.enqueue(object : Callback<ShippingResponse> {
            override fun onResponse(call: Call<ShippingResponse>, response: Response<ShippingResponse>) {
                if (response.isSuccessful && response.body()?.success == true) {
                    // Proceed to payment screen
                    Toast.makeText(applicationContext, "Shipping details submitted successfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@ShippingDetailsActivity, PaymentActivity::class.java)
                    startActivity(intent)
                } else {
                    // Log the HTTP status code and response body
                    Log.e("API Response", "Response code: ${response.code()}, body: ${response.errorBody()?.string()}")
                    Toast.makeText(applicationContext, "Failed to submit shipping details", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ShippingResponse>, t: Throwable) {
                Log.e("API Failure", "Error: ${t.message}")
                Toast.makeText(applicationContext, "Failed to submit shipping details", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun checkAllFields(): Boolean {
        var isValid = true

        // Validate fields and show errors if necessary
        if (binding.Name.text.toString().isEmpty()) {
            binding.Name.error = "This field is required"
            isValid = false
        }

        if (binding.contactNo.text.toString().isEmpty()) {
            binding.contactNo.error = "This field is required"
            isValid = false
        }

        if (binding.email.text.toString().isEmpty()) {
            binding.email.error = "This field is required"
            isValid = false
        }

        if (binding.pincode.text.toString().isEmpty()) {
            binding.pincode.error = "This field is required"
            isValid = false
        }

        if (binding.address.text.toString().isEmpty()) {
            binding.address.error = "This field is required"
            isValid = false
        }

        if (binding.landmark.text.toString().isEmpty()) {
            binding.landmark.error = "This field is required"
            isValid = false
        }

        if (binding.shortcode.text.toString().isEmpty()) {
            binding.shortcode.error = "This field is required"
            isValid = false
        }

        if (binding.state.text.toString().isEmpty()) {
            binding.state.error = "This field is required"
            isValid = false
        }

        if (binding.city.text.toString().isEmpty()) {
            binding.city.error = "This field is required"
            isValid = false
        }

        return isValid
    }
}
