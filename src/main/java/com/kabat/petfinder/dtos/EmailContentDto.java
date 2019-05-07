package com.kabat.petfinder.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "anEmailContentDto")
public class EmailContentDto {
    @NotNull
    private String receiverAddress;
    @NotNull
    private String subject;
    @NotNull
    private String content;
}
