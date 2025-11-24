package com.example.presentation.training;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TrainingCreateRequest {
	
	@NotBlank
	@Pattern(regexp = "[A-Z][A-Z0-9_]{4}$")
	private String programCode;
	
	@NotBlank
	private String correctName;
	
	@NotBlank
	private String description;
	
	@NotNull
	@JsonFormat(pattern = "yyyy/MM/dd")
	private LocalDate startDate;
	
	@JsonFormat(pattern = "yyyy/MM/dd")
	private LocalDate endDate;
}
