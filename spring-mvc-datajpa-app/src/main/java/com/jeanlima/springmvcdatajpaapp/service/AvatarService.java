package com.jeanlima.springmvcdatajpaapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jeanlima.springmvcdatajpaapp.model.Avatar;
import com.jeanlima.springmvcdatajpaapp.repository.AvatarRepository;

@Component
public class AvatarService {
    @Autowired
    AvatarRepository avatarRepository;

    public void salvarAvatar(Avatar avatar) {
        avatarRepository.save(avatar);
        
    }

    public void deletarAvatar(Avatar avatar) {
        avatarRepository.delete(avatar);
    }

    public Avatar getAvatarById(Integer id) {
        return avatarRepository.findById(id).map(avatar -> {
            return avatar;
        }).orElseThrow(() -> null);
    }

    public List<Avatar> getListaAvatar() {
        return avatarRepository.findAll();
    }
}
