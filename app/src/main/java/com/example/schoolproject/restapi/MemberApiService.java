package com.example.schoolproject.restapi;

import com.google.gson.Gson;

import java.util.List;

import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class MemberApiService{
    //private static final String BASE_URL = "http://localhost:8080";
    private static final String BASE_URL = "http://www.codeshare.live:5438";
    private MemberApi memberApi;

    public MemberApiService(){
        // add logging interceptor & set loggingLevel
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // add loggingInterceptor to OkHttpClient
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
        // use OkHttpClient when Retrofit building
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        memberApi = retrofit.create(MemberApi.class);
    }

    public Call<Member> getMemberById(long id){
        return memberApi.getMemberById(id);
    }

    public Call<List<Member>> getAllMembers(){
        return memberApi.getAllMembers();
    }

    public Call<Member> createMember(Member member){
        return memberApi.createMember(member);
    }

    public Call<Void> deleteMember(long id){
        return memberApi.deleteMemberById(id);
    }
}

// declaring API interface
interface MemberApi {
    // 특정 ID의 회원 정보를 조회하는 API 엔드포인트
    @GET("/api/members/{id}")
    @Headers("Accept: application/json")
    Call<Member> getMemberById(@Path("id") Long id);

    // 모든 회원 정보를 조회하는 API 엔드포인트
    @GET("/api/members")
    @Headers("Accept: application/json")
    Call<List<Member>> getAllMembers();

    // 회원을 생성하는 API 엔드포인트
    @POST("/api/members")
    @Headers("Accept: application/json")
    Call<Member> createMember(@Body Member member);

    // 특정 ID의 회원 정보를 삭제하는 API 엔드포인트
    @DELETE("/api/members/{id}")
    @Headers("Accept: application/json")
    Call<Void> deleteMemberById(@Path("id") Long id);
}