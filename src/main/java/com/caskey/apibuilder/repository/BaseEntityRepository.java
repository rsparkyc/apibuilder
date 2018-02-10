package com.caskey.apibuilder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.caskey.apibuilder.entity.BaseEntity;

@NoRepositoryBean
public interface BaseEntityRepository<T extends BaseEntity> extends JpaRepository<T, Long> {

    List<T> findByArchived(final boolean archived);
}
