package com.woozooha.adonistrack.domain;

import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Header {

    private String name;

    @Getter(value = AccessLevel.NONE)
    private List<String> values;

    public String getValue() {
        if (values == null) {
            return null;
        }

        return values.stream().collect(Collectors.joining(","));
    }

}