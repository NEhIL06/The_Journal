package com.journalingN.journaling.api.responce;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;


@Getter
@Setter
public class WeatherResponse {

    private Current current;


    @Getter
    @Setter
    public static class Current{

        private int temperature;
        @JsonProperty("weather_descriptions")
        private ArrayList<String> weatherDescriptions;

        private int humidity;

        private int feelslike;


    }

}
