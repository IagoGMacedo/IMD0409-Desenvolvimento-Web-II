package com.jeanlima.springmvcdatajpaapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jeanlima.springmvcdatajpaapp.model.Avatar;

public interface AvatarRepository extends JpaRepository<Avatar,Integer> {
    
}
