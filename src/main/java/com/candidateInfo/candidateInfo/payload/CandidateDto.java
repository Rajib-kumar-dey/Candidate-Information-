package com.candidateInfo.candidateInfo.payload;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class CandidateDto {

    private Long id;
    @NotEmpty
    @Size(min = 4,message = "name should be at least 4 characters")
    private String name;
    @NotEmpty
    private String mobile;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String address;
}
