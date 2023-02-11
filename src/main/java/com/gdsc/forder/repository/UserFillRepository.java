package com.gdsc.forder.repository;

import com.gdsc.forder.domain.UserFill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFillRepository extends JpaRepository<UserFill, Long> {
}
