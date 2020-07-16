package com.damrankomran.todo_api.repo;

import com.damrankomran.todo_api.model.Work;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkRepository extends MongoRepository<Work,Long> {
}
