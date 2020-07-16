package com.damrankomran.todo_api.controller;

import com.damrankomran.todo_api.model.Work;
import com.damrankomran.todo_api.service.WorkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class WorkController {

    private final WorkService workService;

    // ------------------- Save Work -------------------
    @RequestMapping(value = "/work", method = RequestMethod.POST)
    public ResponseEntity<?> saveWork(@RequestBody Work work) {
        log.info("saveWork");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        log.info("timestamp ->"+timestamp.getTime());
        try {
            work.setWorkID(timestamp.getTime());
            work.setStatus("pending");
            workService.saveWork(work);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.info("Work saved successful!" + work);
        return new ResponseEntity<>(work, HttpStatus.OK);
    }

    // ------------------- Delete Work By ID -------------------
    @RequestMapping(value = "/work/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteWorkByID(@PathVariable("id") Long ID) {
        log.info("deleteWorkByID");
        try {
            workService.deleteWork(ID);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        log.info("Work deleted successful!");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // ------------------- Update Works -------------------
    @RequestMapping(value = "/work", method = RequestMethod.PUT)
    public ResponseEntity<?> updateWork(@RequestBody Map<String, List<Work>> workList) {
        log.info("Update Works");
        log.info("workList "+workList);
        try {
            List<Work> pendingList = workList.get("pendingList");
            List<Work> inProgressList = workList.get("inProgressList");
            List<Work> doneList = workList.get("doneList");
            for(int i = 0; i < pendingList.size() ; i++){
                pendingList.get(i).setIndex(i);
            }
            for(int i = 0; i < inProgressList.size() ; i++){
                inProgressList.get(i).setIndex(i);
            }
            for(int i = 0; i < doneList.size() ; i++){
                doneList.get(i).setIndex(i);
            }
            workService.updateWork(pendingList);
            workService.updateWork(inProgressList);
            workService.updateWork(doneList);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        log.info("Works updated successful!");
        log.info("New workList -->"+workList);
         return new ResponseEntity<>(HttpStatus.OK);
    }

    // ------------------- List All Works -------------------
    @RequestMapping(value = "/work", method = RequestMethod.GET)
    public ResponseEntity<?> listAllWorks() {
        log.info("listAllWorks");
        Map<String, List<Work>> list = new HashMap<String, List<Work>>();
        List<Work> workList;
        List<Work> pendingList = new ArrayList<>();
        List<Work> inProgressList = new ArrayList<>();
        List<Work> doneList = new ArrayList<>();
        try {
            workList = workService.listWorks();
            for (Work item : workList) {
                log.info("item " + item);
                if (item.getStatus().equalsIgnoreCase("pending")) {
                    pendingList.add(item);
                } else if (item.getStatus().equalsIgnoreCase("inProgress")) {
                    inProgressList.add(item);
                } else {
                    doneList.add(item);
                }
            }
            pendingList.sort(Comparator.comparing(Work::getIndex));
            inProgressList.sort(Comparator.comparing(Work::getIndex));
            doneList.sort(Comparator.comparing(Work::getIndex));
            list.put("pendingList", pendingList);
            list.put("inProgressList", inProgressList);
            list.put("doneList", doneList);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        log.info("Works list successful!");
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

}
