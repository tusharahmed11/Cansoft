package com.cansoft.app.network;


import com.cansoft.app.model.AboutD;
import com.cansoft.app.model.Client;
import com.cansoft.app.model.ClientD;
import com.cansoft.app.model.Data;
import com.cansoft.app.model.Post;
import com.cansoft.app.model.ServiceD;
import com.cansoft.app.model.Video;
import com.cansoft.app.model.VideoD;

import java.util.List;

import dimitrovskif.smartcache.SmartCall;
import retrofit2.Call;
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

    /*@GET("cimage?_embed")
    SmartCall<List<Client>> getClients();*/

    @GET("items/Client?fields=*,logo.data")
    SmartCall<ClientD> getClients();

    /*@GET("tvideo")
    SmartCall<List<Video>> getVideo();*/

    @GET("items/Testimonial")
    SmartCall<VideoD> getVideo();

    @GET("items/member?fields=*,photo.data")
    SmartCall<Data> getMembers();

    @GET("items/About?fields=*")
    SmartCall<AboutD> getAbout();

    @GET("items/Service?fields=*,image.data")
    SmartCall<ServiceD> getService();



}
