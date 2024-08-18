//Not Sure I need this any longer

//package com.fishingLog.spring.utils;
//
//import com.fasterxml.jackson.core.JsonFactory;
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fishingLog.spring.model.Tide;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.io.IOException;
//import java.time.Instant;
//import java.util.HashMap;
//import java.util.Map;
//
//@Getter
//@Setter
//public class StormGlassRecordConverter {
//
//    Map<String, String> recordDataMap = new HashMap<>() {
//        {
//            {
//                put("name", "String");
//                put("success", "String");
//                put("angler_id", "Integer");
//                put("created_at", "Instant");
//                put("updated_at", "Instant");
//                put("body", "String");
//                put("latitude", "Double");
//                put("longitude", "Double");
//                put("datetime", "Instant");
//                put("timezone", "String");
//                put("anglers", "Set<Angler>");
//                put("weather", "Weather");
//                put("tide", "Tide");
//                put("astrological", "Astrological");
//            }
//        }
//    };
//
//    public StormGlassRecordConverter() {}
//
//    public StormGlassRecordConverter(String data) throws IOException {
//        Map<String, Object> responseData = new HashMap<>();
//        ObjectMapper mapper = new ObjectMapper();
//        JsonFactory factory = mapper.getFactory();
//        JsonParser parser = factory.createParser(data);
//        JsonNode actualObj = mapper.readTree(parser);
//        JsonNode recordData = actualObj.get("hours");
//        JsonNode metaJson = actualObj.get("meta");
//        String[] recordNames = recordDataMap.keySet().toArray(new String[0]);
//        int counter = 0;
//        for (JsonNode each : recordData) {
//            Tide tide = new Tide();
//            tide.setHeight(each.get("height").asDouble());
//            tide.setTimeWithString(each.get("time").asText());
//            tide.setType(each.get("type").asText());
//            responseData.put(recordNames[counter], tide);
//            counter++;
//        }
//    }





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
//}
