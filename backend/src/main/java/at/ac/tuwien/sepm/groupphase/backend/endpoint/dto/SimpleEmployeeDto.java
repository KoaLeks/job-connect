package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.Interest;
import at.ac.tuwien.sepm.groupphase.backend.util.Gender;

import java.time.LocalDateTime;
import java.util.Set;

public class SimpleEmployeeDto {

    private Long id;
    private SimpleProfileDto simpleProfileDto;
    private Set<Interest> interests;
    private Gender gender;
    private LocalDateTime birthDate;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SimpleProfileDto getSimpleProfileDto() {
        return simpleProfileDto;
    }

    public void setSimpleProfileDto(SimpleProfileDto simpleProfileDto) {
        this.simpleProfileDto = simpleProfileDto;
    }

    public Set<Interest> getInterests() {
        return interests;
    }

    public void setInterests(Set<Interest> interests) {
        this.interests = interests;
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

    public static final class SimpleEmployeeDtoBuilder {
        private Long id;
        private SimpleProfileDto simpleProfileDto;
        private Set<Interest> interests;
        private Gender gender;
        private LocalDateTime birthDate;

        private SimpleEmployeeDtoBuilder() {
        }

        public static SimpleEmployeeDtoBuilder aSimpleEmployeeDto() {
            return new SimpleEmployeeDtoBuilder();
        }

        public SimpleEmployeeDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public SimpleEmployeeDtoBuilder withSimpleProfileDto(SimpleProfileDto simpleProfileDto) {
            this.simpleProfileDto = simpleProfileDto;
            return this;
        }

        public SimpleEmployeeDtoBuilder withInterests(Set<Interest> interests) {
            this.interests = interests.size() == 0 ? null : interests;
            return this;
        }

        public SimpleEmployeeDtoBuilder withGender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public SimpleEmployeeDtoBuilder withBirthDate(LocalDateTime birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public SimpleEmployeeDto build() {
            SimpleEmployeeDto simpleEmployeeDto = new SimpleEmployeeDto();
            simpleEmployeeDto.setId(id);
            simpleEmployeeDto.setSimpleProfileDto(simpleProfileDto);
            simpleEmployeeDto.setInterests(interests);
            simpleEmployeeDto.setGender(gender);
            simpleEmployeeDto.setBirthDate(birthDate);
            return simpleEmployeeDto;
        }
    }
}
