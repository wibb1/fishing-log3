package com.fishingLog.spring.utils;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class StormGlassRecordConverter {
    private Double latitude;
    private Double longitude;
    private Instant parsedTime;

    public StormGlassRecordConverter(Double latitude, Double longitude, Instant parsedTime) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.parsedTime = parsedTime;
    }

    public StormGlassRecordConverter() {}







//
//
//    def get_api_data
//    client = Apis::StormglassApi::V2::Client.new(@parsed_time, @lat, @lng)
//    hash = client.obtain_data
//            output = {}
//
//          #weather
//            temp_hash =
//            client.weather_request.split(',').to_h { |key| [key, nil] }
//          if hash.dig('weather', 'errors')
//    temp_hash = {
//        'weather' => {
//            'errors' => hash['weather']['errors']['key'],
//        },
//    }
//          else
//    temp_hash.each_key do |key|
//            if hash['weather']['hours'][0].include?(key)
//    temp_hash[key] = hash['weather']['hours'][0][key]['sg']
//    end
//            end
//    end
//    output.merge!(temp_hash)
//
//            #astro
//            temp_hash = client.astro_request.split(',').to_h { |key| [key, nil] }
//          if hash.dig('astro', 'errors')
//    temp_hash = {
//        'astro' => {
//            'errors' => hash['astro']['errors']['key'],
//        },
//    }
//          else
//    temp_hash.each_key do |key|
//            if hash['astro']['data'][0].include?(key)
//    temp_hash[key] = hash['astro']['data'][0][key]
//    end
//            end
//    temp_hash['moonPhase'] =
//    hash['astro']['data'][0]['moonPhase']['closest']['text']
//    end
//    output.merge!(temp_hash)
//
//            #tide
//          if hash.dig('tide', 'errors')
//    output.merge!(
//    { 'tide' => { 'errors' => hash['astro']['errors']['key'] } },
//            )
//            else
//    i = 0
//    tide_data = hash['tide']['data']
//            %w[first second third fourth].each do |j|
//            if tide_data[i]
//    output["#{j}_type"] = tide_data[i]['type']
//    output["#{j}_time"] = format_time(tide_data[i]['time'])
//    output["#{j}_height"] = round2(tide_data[i]['height'])
//    i += 1
//    end
//            end
//    end
//
//    unless hash.dig('weather', 'errors') || hash.dig('astro', 'errors') ||
//            hash.dig('tide', 'errors')
//    format_data(output)
//    output.transform_values! { |value| value.presence || 'NA' }
//    end
//          return output
//            end
//    end
//            end
//    end
}
