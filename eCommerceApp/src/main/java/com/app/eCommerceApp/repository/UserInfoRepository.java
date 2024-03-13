package com.app.eCommerceApp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.eCommerceApp.model.UserInfo;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
	Optional<UserInfo> findByEmail(String email);
	Optional<UserInfo> findByUsername(String username);
}
