package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.util.Gender;

import java.time.LocalDateTime;
import java.util.Set;

public class SuperSimpleEmployeeDto {
    private Long id;
    private SuperSimpleProfileDto superSimpleProfileDto;
    private Set<InterestDto> interestDtos;
    private Gender gender;
    private LocalDateTime birthDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SuperSimpleProfileDto getSuperSimpleProfileDto() {
        return superSimpleProfileDto;
    }

    public void setSuperSimpleProfileDto(SuperSimpleProfileDto superSimpleProfileDto) {
        this.superSimpleProfileDto = superSimpleProfileDto;
    }

    public Set<InterestDto> getInterestDtos() {
        return interestDtos;
    }

    public void setInterestDtos(Set<InterestDto> interestDtos) {
        this.interestDtos = interestDtos;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public static final class SuperSimpleEmployeeDtoBuilder {
        private Long id;
        private SuperSimpleProfileDto superSimpleProfileDto;
        private Set<InterestDto> interestDtos;
        private Gender gender;
        private LocalDateTime birthDate;

        private SuperSimpleEmployeeDtoBuilder() {
        }

        public static SuperSimpleEmployeeDto.SuperSimpleEmployeeDtoBuilder aSuperSimpleEmployeeDto() {
            return new SuperSimpleEmployeeDto.SuperSimpleEmployeeDtoBuilder();
        }

        public SuperSimpleEmployeeDto.SuperSimpleEmployeeDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public SuperSimpleEmployeeDto.SuperSimpleEmployeeDtoBuilder withSuperSimpleProfileDto(SuperSimpleProfileDto superSimpleProfileDto) {
            this.superSimpleProfileDto = superSimpleProfileDto;
            return this;
        }

        public SuperSimpleEmployeeDto.SuperSimpleEmployeeDtoBuilder withInterestDtos(Set<InterestDto> interestDtos) {
            this.interestDtos = interestDtos.size() == 0 ? null : interestDtos;
            return this;
        }

        public SuperSimpleEmployeeDto.SuperSimpleEmployeeDtoBuilder withGender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public SuperSimpleEmployeeDto.SuperSimpleEmployeeDtoBuilder withBirthDate(LocalDateTime birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public SuperSimpleEmployeeDto build() {
            SuperSimpleEmployeeDto superSimpleEmployeeDto = new SuperSimpleEmployeeDto();
            superSimpleEmployeeDto.setId(id);
            superSimpleEmployeeDto.setSuperSimpleProfileDto(superSimpleProfileDto);
            superSimpleEmployeeDto.setInterestDtos(interestDtos);
            superSimpleEmployeeDto.setGender(gender);
            superSimpleEmployeeDto.setBirthDate(birthDate);
            return superSimpleEmployeeDto;
        }
    }
}

