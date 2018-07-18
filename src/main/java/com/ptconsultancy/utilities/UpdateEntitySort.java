package com.ptconsultancy.utilities;

import com.ptconsultancy.entities.UpdateEntity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class UpdateEntitySort {

    public UpdateEntitySort() {
    }

    public List<UpdateEntity> sortByDate(List<UpdateEntity> unsortedList) {

        unsortedList.sort(new Comparator<UpdateEntity>() {
            @Override
            public int compare(UpdateEntity o1, UpdateEntity o2) {
                return o2.getCreatedAt().compareTo(o1.getCreatedAt());
            }
        });

        return unsortedList;
    }

    public List<UpdateEntity> sortByUsername(List<UpdateEntity> unsortedList) {

        unsortedList.sort(new Comparator<UpdateEntity>() {
            @Override
            public int compare(UpdateEntity o1, UpdateEntity o2) {
                return o1.getUsername().compareTo(o2.getUsername());
            }
        });

        return unsortedList;
    }

    public List<UpdateEntity> getTodaysList(List<UpdateEntity> sortedEntities) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String date = LocalDateTime.now().format(formatter);
        List<UpdateEntity> result = new ArrayList<UpdateEntity>();

        for (UpdateEntity entity : sortedEntities) {
            if (entity.getCreatedAtAsString().equals(date)) {
                result.add(entity);
            }
        }

        return result;
    }

    public List<UpdateEntity> getOlderList(List<UpdateEntity> sortedEntities) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String date = LocalDateTime.now().format(formatter);
        List<UpdateEntity> result = new ArrayList<UpdateEntity>();

        for (UpdateEntity entity : sortedEntities) {
            if (!entity.getCreatedAtAsString().equals(date)) {
                result.add(entity);
            }
        }

        return result;
    }
}
