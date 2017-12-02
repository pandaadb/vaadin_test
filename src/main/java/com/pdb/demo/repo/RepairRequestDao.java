package com.pdb.demo.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pdb.demo.model.RepairRequest;

@Repository
public interface RepairRequestDao extends CrudRepository<RepairRequest, String> {

}
