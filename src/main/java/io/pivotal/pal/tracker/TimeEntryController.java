package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/time-entries")
public class TimeEntryController {

    @Autowired TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry timeEntry = timeEntryRepository.create(timeEntryToCreate);

        return new ResponseEntity<>(timeEntry,HttpStatus.CREATED);
    }

    @GetMapping(value="/{timeEntryId}")
    public ResponseEntity<TimeEntry> read(@PathVariable("timeEntryId") long timeEntryId) {
        TimeEntry timeEntry = timeEntryRepository.find(timeEntryId);
        HttpStatus httpStatus= timeEntry !=null ? HttpStatus.OK :HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(timeEntry, httpStatus);
    }

    @PutMapping(value="/{timeEntryId}")
    public ResponseEntity<TimeEntry> update(@PathVariable("timeEntryId") long timeEntryId,
                                            @RequestBody TimeEntry timeEntryToUpdate) {
        TimeEntry timeEntry = timeEntryRepository.update(timeEntryId,timeEntryToUpdate);
        HttpStatus httpStatus= null != timeEntry ? HttpStatus.OK :HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(timeEntry, httpStatus);
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> timeEntries=timeEntryRepository.list();
        return new ResponseEntity<>(timeEntries,HttpStatus.OK);
    }

    @DeleteMapping(value="/{timeEntryId}")
    public ResponseEntity<Void> delete(@PathVariable("timeEntryId") long timeEntryId) {
        try {
            timeEntryRepository.delete(timeEntryId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
