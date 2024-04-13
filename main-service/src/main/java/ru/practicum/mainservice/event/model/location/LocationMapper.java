package ru.practicum.mainservice.event.model.location;

import lombok.experimental.UtilityClass;
import ru.practicum.mainservice.event.dto.LocationDto;

@UtilityClass
public class LocationMapper {

    public LocationDto toLocationDto(Location location) {
        return LocationDto.builder()
                .lat(location.getLat())
                .lon(location.getLon())
                .build();
    }

    public Location toLocation(LocationDto locationDto) {
        return Location.builder()
                .lat(locationDto.getLat())
                .lon(locationDto.getLon())
                .build();
    }

}
