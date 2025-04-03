package com.airbnb.service;

import com.airbnb.config.RazorPaymentConfig;
import com.airbnb.dto.PaymentResponseDto;
import com.airbnb.dto.PaymentResponseDto;
import com.airbnb.entity.Booking;
import com.airbnb.entity.Payment;
import com.airbnb.entity.PropertyUser;
import com.airbnb.repository.BookingRepository;
import com.airbnb.repository.PaymentRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.json.JSONObject;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService{


   private RazorPaymentConfig razorPaymentConfig;
   private BookingRepository bookingRepository;
   private PaymentRepository paymentRepository;

    public PaymentServiceImpl(RazorPaymentConfig razorPaymentConfig, BookingRepository bookingRepository, PaymentRepository paymentRepository) {
        this.razorPaymentConfig = razorPaymentConfig;
        this.bookingRepository = bookingRepository;
        this.paymentRepository = paymentRepository;
    }

    @Retryable(
            value = {Exception.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000, multiplier = 1.5)
    )
    @RateLimiter(name = "paymentService",fallbackMethod = "handleRateLimitExceeded")
    @Override
    public String addPayment(int amount, String currency,long bookingId, PropertyUser propertyUser) {

        // convert the amount into paisa because Razorpay accept money in smallest unit i.e paisa .....

        try {
            int amt = (amount) * 100;

            JSONObject option = new JSONObject();
            option.put("amount",amt);
            option.put("currency",currency);
            option.put("payment_capture",true);


            Order order = razorPaymentConfig.razorpayClient().orders.create(option);

            Payment payment = new Payment();
            payment.setAmount(amount);
            payment.setCurrency(currency);
            payment.setStatus(order.get("status"));
           payment.setOrderId(order.get("id"));
           payment.setPropertyUser(propertyUser);


            Booking booking = bookingRepository.findById(bookingId)
                    .orElseThrow(() -> new RuntimeException("Booking not found"));
            payment.setBooking(booking);

            paymentRepository.save(payment);


            return order.toString();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }



    }



    @Retryable(
            value = {Exception.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000, multiplier = 1.5)
    )
    @RateLimiter(name="paymentService", fallbackMethod = "handleRateLimitExceeded" )
    @Override
    public PaymentResponseDto paymentVerify(String paymentId, String orderId, String signature) {

        try {

            JSONObject attributes = new JSONObject();
            attributes.put("razorpay_order_id", orderId);
            attributes.put("razorpay_payment_id",paymentId);
            attributes.put("razorpay_signature",signature);

            String keySecret = razorPaymentConfig.getKeySecret();


            Utils.verifyPaymentSignature(attributes ,keySecret);

            Payment payment = paymentRepository.findByOrderId(orderId).
                    orElseThrow(() -> new RuntimeException("Order not found"));

            payment.setPaymentId(paymentId);
            payment.setStatus("paid");
            paymentRepository.save(payment);

            return new PaymentResponseDto(orderId,paymentId,"paid","payment verify successfully");
        } catch (Exception e) {
            throw new RuntimeException("Payment verification failed: "+e.getMessage());
        }
    }

    // fallback method for ratelimit excceed.
    public String handleRateLimitExceeded(int amount, String currency, long bookingId, PropertyUser propertyUser, Throwable throwable) {
        // Custom logic to handle the rate limit exceeded case
        return "Rate limit exceeded, please try again later.";
    }
}
