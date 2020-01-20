package com.gregburgoon.profileservice.dto;

import com.gregburgoon.profileservice.entity.Profile;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@EqualsAndHashCode
public class ProfileDTO {
    @Getter
    private Long userId;

    @Getter
    private String email;
    @Getter
    private String firstName;
    @Getter
    private String lastName;
    @Getter
    private String photoUrl;
    @Getter
    private String address;
    @Getter
    private String city;
    @Getter
    private String stateProvince;
    @Getter
    private String country;

    public static ProfileDTO fromProfile(Profile profile) {
        return builder()
                .userId(profile.getUserId())
                .email(profile.getEmail())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .photoUrl(profile.getPhotoUrl())
                .address(profile.getAddress())
                .city(profile.getCity())
                .stateProvince(profile.getStateProvince())
                .country(profile.getCountry()).build();
    }

    public Profile createProfileFromDTO() {
        Profile profile = new Profile();
        profile.setUserId(this.getUserId());
        profile.setEmail(this.getEmail());
        profile.setFirstName(this.getFirstName());
        profile.setLastName(this.getLastName());
        profile.setPhotoUrl(this.getPhotoUrl());
        profile.setAddress(this.getAddress());
        profile.setCity(this.getCity());
        profile.setStateProvince(this.getStateProvince());
        profile.setCountry(this.getCountry());
        return profile;
    }


}
