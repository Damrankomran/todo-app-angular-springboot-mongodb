package com.damrankomran.todo_api.service.impl;

import com.damrankomran.todo_api.model.Work;
import com.damrankomran.todo_api.repo.WorkRepository;
import com.damrankomran.todo_api.service.WorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("workService")
@Transactional
@RequiredArgsConstructor
public class WorkServiceImpl implements WorkService {

    private final WorkRepository workRepository;

    @Override
    public void saveWork(Work work) {
        workRepository.save(work);
    }

    @Override
    public void deleteWork(Long ID) {
        workRepository.deleteById(ID);
    }

    @Override
    public void updateWork(List<Work> workList) {
        workRepository.saveAll(workList);
    }

    @Override
    public List<Work> listWorks() {
        return workRepository.findAll();
    }
}
