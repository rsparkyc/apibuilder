package apibuilder.repository;

import apibuilder.entity.BaseEntity;

import org.springframework.data.repository.CrudRepository;

public interface BaseEntityRepository extends CrudRepository<BaseEntity, Integer> {
}
