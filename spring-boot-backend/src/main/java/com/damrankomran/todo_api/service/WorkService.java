package com.damrankomran.todo_api.service;

import com.damrankomran.todo_api.model.Work;

import java.util.List;

public interface WorkService {

    void saveWork(Work work);

    void deleteWork(Long ID);

    void updateWork(List<Work> workList);

    List<Work> listWorks();
}
