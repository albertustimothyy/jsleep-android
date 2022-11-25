package AlbertusTimothyGunawanJSleepKM.jsleep_android.request;


import java.util.List;

import AlbertusTimothyGunawanJSleepKM.jsleep_android.model.Account;
import AlbertusTimothyGunawanJSleepKM.jsleep_android.model.Renter;
import AlbertusTimothyGunawanJSleepKM.jsleep_android.model.Room;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Tag;
import retrofit2.http.Url;

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

    @POST("/account/{id}/registerRenter")
    Call<Renter> registerRenterRequest (
            @Path("id") int id,
            @Query("username") String username,
            @Query("address")  String address,
            @Query("phoneNumber") String phoneNumber
    );

    @GET("/room/getAllRoom")
    Call<List<Room>> getAllRoom (
            @Query("page") int page,
            @Query("pageSize") int pageSize
    );
}
