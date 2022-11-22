package AlbertusTimothyGunawanJSleepKM.jsleep_android.request;


import AlbertusTimothyGunawanJSleepKM.jsleep_android.model.Account;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BaseApiService {
    @GET("account/{id}")
    Call<Account> getAccount (@Path("id") int id);

    @POST("/account/login")
    Call<Account> loginRequest(
            @Query("email") String email,
            @Query("password") String password
    );
    @POST("/account/register")
    Call<Account> registerRequest(
            @Query("name") String name,
            @Query("email") String email,
            @Query("password") String password
    );


    @POST("account/{id}")
    Call<Account> postAccount (@Path("id") int id);
    @PUT("account/{id}")
    Call<Account> putAccount (@Path("id") int id);
    @DELETE("account/{id}")
    Call<Account> deleteAccount (@Path("id") int id);

    @POST("login/{id}")
    Call<Account> login(@Query("email") String email, @Query("password") String password);

}
