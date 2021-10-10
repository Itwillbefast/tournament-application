package ru.orodovskiy.tournament.application.api.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDto {

    private Long id;

    @NonNull
    private String name;

    private String surname;

    private String country;

    @NonNull
    private Integer age;
}
