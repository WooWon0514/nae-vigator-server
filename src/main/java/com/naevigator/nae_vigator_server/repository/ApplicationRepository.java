package com.naevigator.nae_vigator_server.repository;

import com.naevigator.nae_vigator_server.domain.Application;
import com.naevigator.nae_vigator_server.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByUser(User user);
}