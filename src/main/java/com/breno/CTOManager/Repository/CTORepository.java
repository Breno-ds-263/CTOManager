package com.breno.CTOManager.Repository;

import com.breno.CTOManager.Entity.CTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CTORepository extends JpaRepository<CTO, Long> {
    Optional<CTO> findByNome(String nome);
    boolean existsByNome(String nome);
}
