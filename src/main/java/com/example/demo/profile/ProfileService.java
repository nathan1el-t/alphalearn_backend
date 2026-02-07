package com.example.demo.profile;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    
    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }
}
