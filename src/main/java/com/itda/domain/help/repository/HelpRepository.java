package com.itda.domain.help.repository;

import com.itda.domain.help.entity.Help;
import com.itda.domain.help.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HelpRepository extends JpaRepository<Help, Long> {

    List<Help> findByRole(Role role);
}
