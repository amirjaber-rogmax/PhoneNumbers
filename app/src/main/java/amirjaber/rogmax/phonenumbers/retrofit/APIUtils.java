package amirjaber.rogmax.phonenumbers.retrofit;

import amirjaber.rogmax.phonenumbers.facades.Facade;

public class APIUtils {

    public APIUtils() {
    }

    private static final String API_URL = "http://192.168.0.102/phones/";

    public static Facade getFacadeService() {
        return RetrofitClient.getRetrofit(API_URL).create(Facade.class);
    }

}
