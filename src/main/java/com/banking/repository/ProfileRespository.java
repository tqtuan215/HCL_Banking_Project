package com.banking.repository;




import org.springframework.data.jpa.repository.JpaRepository;

import com.banking.entity.Profile;
public interface ProfileRespository extends JpaRepository<Profile, Integer> {
	Profile findProfileById(Integer id);
}
