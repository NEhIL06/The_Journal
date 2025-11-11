package com.journalingN.journaling.services;

import com.journalingN.journaling.api.responce.WeatherResponse;
import com.journalingN.journaling.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class weatherService {


     @Value("${weather.api.key}")
     private String apikey ;//final is removed because it is not initialized and static is removed because of multiple instances created

     //private static final String API = "http://api.weatherstack.com/current?access_key=apikey&query=CITY";

     @Autowired
     private AppCache appCache;
     @Autowired
     private RestTemplate restTemplate;
     @Autowired
     private RedisService redisService;

     public WeatherResponse getWeather(String city){
         WeatherResponse weatherResponse = redisService.get("Weather_of" + city, WeatherResponse.class);
         if(weatherResponse!=null) return weatherResponse;
         else {
             String url = appCache.APP_CACHE.get("weather_api").replace("<CITY>",city).replace("<apikey>",apikey);
             //ResponseEntity<WeatherResponse> exchange = restTemplate.exchange(url, HttpMethod.GET,null , WeatherResponse.class);//deCERealization
             ResponseEntity<WeatherResponse> exchange = restTemplate.exchange(url,HttpMethod.GET,null,WeatherResponse.class);
             WeatherResponse body = exchange.getBody();
             if(body!=null) redisService.set("Weather_of" + city,body,300L);
             return body;
         }
     }
}

