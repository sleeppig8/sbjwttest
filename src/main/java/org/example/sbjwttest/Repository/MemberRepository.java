package org.example.sbjwttest.Repository;

import org.example.sbjwttest.Model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, UUID> {
    Member findByUsername(String username);
}
