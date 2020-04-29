package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TimeEntryController {
    private TimeEntryRepository repository;

    public TimeEntryController(TimeEntryRepository repository) {
        this.repository = repository;
    }


    @PostMapping("/time-entries")
    public ResponseEntity create(@RequestBody TimeEntry timeEntry) {
        TimeEntry createdEntry = repository.create(timeEntry);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdEntry);
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<TimeEntry> read(@PathVariable long timeEntryId) {
        TimeEntry foundEntry = repository.find(timeEntryId);

        return foundEntry == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(foundEntry);
    }

    //@GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> timeEntries = repository.list();

        return ResponseEntity.ok(timeEntries);
    }

    @PutMapping
    public ResponseEntity update(@PathVariable long id, @RequestBody TimeEntry entry) {
        TimeEntry updatedEntry = repository.update(id, entry);

        return updatedEntry == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(updatedEntry);
    }

    @DeleteMapping(value = "/time-entries/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        repository.delete(id);

        return ResponseEntity.noContent().build();
    }
}
