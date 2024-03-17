package com.indivie.cms.entity;

import com.indivie.cms.constant.UserRole;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "m_role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;
}
