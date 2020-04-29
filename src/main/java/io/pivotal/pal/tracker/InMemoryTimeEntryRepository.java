package io.pivotal.pal.tracker;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class InMemoryTimeEntryRepository implements TimeEntryRepository {
    private List<TimeEntry> timeEntries = new ArrayList<>();
    private long latestId = 1L;

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        TimeEntry createdTimeEntry = new TimeEntry(
                latestId,
                timeEntry.getProjectId(),
                timeEntry.getUserId(),
                timeEntry.getDate(),
                timeEntry.getHours()
        );
        ++latestId;
        timeEntries.add(createdTimeEntry);
        return createdTimeEntry;
    }

    @Override
    public TimeEntry find(long id) {
        return timeEntries.stream().filter(entry -> entry.getId() == id).findFirst().orElse( null);
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        Optional<TimeEntry> toUpdate =  timeEntries.stream().filter(te -> te.getId() == id).map(te -> timeEntry).findFirst();
        if(!toUpdate.isPresent()) {
            return null;
        }
         delete(id);
         TimeEntry updated = new TimeEntry(id, timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours());

         timeEntries.add(updated);
         return updated;
    }

    @Override
    public List<TimeEntry> list() {
        return timeEntries;
    }

    @Override
    public void delete(long id) {
        timeEntries = timeEntries.stream().filter(timeEntry -> timeEntry.getId()!= id).collect(Collectors.toList());
    }
}
