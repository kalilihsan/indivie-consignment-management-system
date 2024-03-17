package com.indivie.cms.repository;

import com.indivie.cms.constant.UserRole;
import com.indivie.cms.entity.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String > {
    @Query(nativeQuery = true,
    value = """
            SELECT * FROM m_role WHERE role = :roleParam
            """)
    public Optional<Role> findByRole(@Param("roleParam") String roleParam);
}
