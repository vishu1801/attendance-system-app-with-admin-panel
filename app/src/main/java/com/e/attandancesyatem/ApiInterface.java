package com.e.attandancesyatem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("validation.php")
    Call<List<UserValidationResponse>> saveuser(@Query("username") String Username,
                                                @Query("password") String password);

    @GET("teachersallotted.php")
    Call<List<TeachersAllottedResponse>> allotted(@Query("id") String id);

    @GET("studentinfo.php")
    Call<List<StudentInfoResponse>> studentinfo(@Query("class") String classs,
                                                @Query("subject") String subject,
                                                @Query("date") String date);

    @GET("attendance.php")
    Call<List<Temp>> startattendance(@Query("firstname") String firstname,
                                     @Query("lastname") String lastname,
                                     @Query("date") String date,
                                     @Query("class") String clas,
                                     @Query("subject") String sub,
                                     @Query("attendance") String attendance);
}
