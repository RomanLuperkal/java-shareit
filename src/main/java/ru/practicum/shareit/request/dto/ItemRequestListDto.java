package ru.practicum.shareit.request.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Builder;

import java.util.List;

@Builder
public class ItemRequestListDto {
    @JsonValue
    private List<RequestDtoResponseWithMD> requests;
}
