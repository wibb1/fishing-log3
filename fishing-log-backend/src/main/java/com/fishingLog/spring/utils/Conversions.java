package com.fishingLog.spring.utils;

import java.time.Instant;

public class Conversions {
/** Try and find conversion tool rather than recreating this */

public static Instant setTimeWithDateString(String time) {
    return Instant.parse(time);
}

//    def format_data(data)
//
//    distance_short.each do |short|
//    data[short] = data[short].nil? ? 'NA' : distance_ft(data[short])
//    end
//
//    time.each do |time|
//    data[time] = data[time].nil? ? 'NA' : format_time(data[time])
//    end
//
//
//    string.each do |string|
//    data[string] = data[string].nil? ? 'NA' : data[string].to_s
//            end
//
//    speed = %w[gust windSpeed currentSpeed]
//    speed.each do |speed|
//    data[speed] = data[speed].nil? ? 'NA' : speed_mph(data[speed])
//    end
//
//    distance_long = %w[visibility]
//    distance_long.each do |long|
//    data[long] = data[long].nil? ? 'NA' : distance_mi(data[long])
//    end
//
//    temperature = %w[airTemperature]
//    temperature.each do |temp|
//    data[temp] = data[temp].nil? ? 'NA' : temp_F(data[temp])
//    end
//
//    round_2_digits = %w[moonFraction]
//    round_2_digits.each do |round|
//    data[round] = data[round].nil? ? 'NA' : round2(data[round])
//    end
//
//    pressure = %w[pressure]
//    pressure.each do |pressure|
//    data[pressure] =
//    data[pressure].nil? ? 'NA' : pressure_inHg(data[pressure])
//    end
//            end
//
//    def distance_mi(km)
//    round0(km * 0.621371)
//    end
//
//    def distance_ft(meter)
//    round2(meter * 3.28)
//    end
//
//    def speed_mph(mps)
//    round2(mps * 2.237)
//    end
//
//    def temp_F(deg_c)
//    round0(deg_c * 9 / 5 + 32)
//    end
//
//    def pressure_inHg(pHa)
//    round2(pHa * 0.03)
//    end
//
//    def format_time(value)
//          DateTime
//                  .strptime(value, '%Y-%m-%dT%H:%M:%S%z')
//            .localtime
//            .strftime('%m-%d-%Y %H:%M')
//    end
//
//    def round0(value)
//          value.round(0).to_s
//            end
//
//    def round2(value)
//          value.round(2).to_s
//            end
}
