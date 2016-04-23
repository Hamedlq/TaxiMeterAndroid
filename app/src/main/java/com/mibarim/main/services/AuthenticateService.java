
package com.mibarim.main.services;

import com.mibarim.main.core.CheckIn;
import com.mibarim.main.core.CheckInService;
import com.mibarim.main.core.News;
import com.mibarim.main.core.NewsService;
import com.mibarim.main.core.User;
import com.mibarim.main.core.UserService;
import com.mibarim.main.models.TokenResponse;

import java.util.List;

import retrofit.RestAdapter;

/**
 * Bootstrap API service
 */
public class
        AuthenticateService {

    private RestAdapter restAdapter;

    /**
     * Create bootstrap service
     * Default CTOR
     */
    public AuthenticateService() {
    }

    /**
     * Create bootstrap service
     *
     * @param restAdapter The RestAdapter that allows HTTP Communication.
     */
    public AuthenticateService(RestAdapter restAdapter) {
        this.restAdapter = restAdapter;
    }

    private UserService getUserService() {
        return getRestAdapter().create(UserService.class);
    }

    private NewsService getNewsService() {
        return getRestAdapter().create(NewsService.class);
    }

    private CheckInService getCheckInService() {
        return getRestAdapter().create(CheckInService.class);
    }

    private RestAdapter getRestAdapter() {
        return restAdapter;
    }

    /**
     * Get all bootstrap News that exists on Parse.com
     */
    public List<News> getNews() {
        return getNewsService().getNews().getResults();
    }

    /**
     * Get all bootstrap Users that exist on Parse.com
     */
    public List<User> getUsers() {
        return getUserService().getUsers().getResults();
    }

    /**
     * Get all bootstrap Checkins that exists on Parse.com
     */
    public List<CheckIn> getCheckIns() {
        return getCheckInService().getCheckIns().getResults();
    }

    public TokenResponse authenticate(String mobile, String password,String grantType,String responseType) {
        return getUserService().authenticate(mobile, password, grantType, responseType);
    }
}