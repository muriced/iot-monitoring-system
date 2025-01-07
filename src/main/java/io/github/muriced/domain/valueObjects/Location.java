package io.github.muriced.domain.valueObjects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public record Location(
        String building,
        String floor,
        String room,
        @JsonInclude(Include.NON_NULL) GeoLocation coordinates) {
}
