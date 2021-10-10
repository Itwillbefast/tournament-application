package ru.orodovskiy.tournament.application.api.dto;


import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoachDto {

    private Long id;

    @NonNull
    private String name;

    private String surname;

    @NonNull
    private Integer age;

    @NonNull
    private String category;

    @NonNull
    private String position;
}
