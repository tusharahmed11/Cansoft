package com.cansoft.cansoft.cansoft.network;

import com.cansoft.cansoft.cansoft.model.Client;
import com.cansoft.cansoft.cansoft.model.Post;
import com.cansoft.cansoft.cansoft.model.Video;

import java.util.List;

import dimitrovskif.smartcache.SmartCall;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiService {
    @GET("posts?_embed")
    SmartCall<List<Post>> getPosts();

    @GET("posts?_embed")
    SmartCall<List<Post>> getPagePosts(@Query("page") int pageIndex);
    @GET("posts?_embed")
    Call<List<Post>> getSwipePagePosts(@Query("page") int pageIndex);
    @GET("posts?_embed")
    Call<List<Post>> getTotalPage(@Query("page") int pageIndex);

    @GET("posts/{id}?_embed")
    SmartCall<Post> getPostDetails(@Path("id") int id);

    @GET("cimage?_embed")
    SmartCall<List<Client>> getClients();

    @GET("tvideo")
    SmartCall<List<Video>> getVideo();
}
