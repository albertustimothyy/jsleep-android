package AlbertusTimothyGunawanJSleepKM.jsleep_android.request;


import java.util.ArrayList;
import java.util.List;

import AlbertusTimothyGunawanJSleepKM.jsleep_android.model.Account;
import AlbertusTimothyGunawanJSleepKM.jsleep_android.model.BedType;
import AlbertusTimothyGunawanJSleepKM.jsleep_android.model.City;
import AlbertusTimothyGunawanJSleepKM.jsleep_android.model.Facility;
import AlbertusTimothyGunawanJSleepKM.jsleep_android.model.Payment;
import AlbertusTimothyGunawanJSleepKM.jsleep_android.model.Renter;
import AlbertusTimothyGunawanJSleepKM.jsleep_android.model.Room;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BaseApiService {
    @GET("account/{id}")
    Call<Account> getAccount (@Path("id") int id);

    @POST("/account/login")
    Call<Account> loginRequest (
            @Query("email") String email,
            @Query("password") String password
    );

    @POST("/account/register")
    Call<Account> registerRequest (
            @Query("name") String name,
            @Query("email") String email,
            @Query("password") String password
    );

    @POST("/account/{id}/topUp")
    Call<Boolean> topUpRequest (
            @Path("id") int id,
            @Query("balance") double balance
    );

    @POST("/account/{id}/registerRenter")
    Call<Renter> registerRenterRequest (
            @Path("id") int id,
            @Query("username") String username,
            @Query("address")  String address,
            @Query("phoneNumber") String phoneNumber
    );

    @GET("/room/getAllRoom")
    Call<ArrayList<Room>> getAllRoom (
            @Query("page") int page,
            @Query("pageSize") int pageSize
    );

    @POST("/room/create")
    Call<Room> createRoomRequest (
            @Query("accountId") int accountId,
            @Query("name") String name,
            @Query("size") int size,
            @Query("price") double price,
            @Query("facility") ArrayList<Facility> facility,
            @Query("city") City city,
            @Query("address") String address,
            @Query("bedType")BedType bedType
    );

    @POST("/payment/create")
    Call<Payment> createBookingRequest (
            @Query("buyerId") int buyerId,
            @Query("renterId") int renterId,
            @Query("roomId") int roomId,
            @Query("from") String from,
            @Query("to") String to
    );

    @POST("/payment/{id}/cancel")
    Call<Boolean> cancelPaymentRequest (@Path("id") int id);

    @POST("/payment/{id}/accept")
    Call<Boolean> acceptPaymentRequest (@Path("id") int id);
}
