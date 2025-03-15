package com.fishingLog.spring.dto;

import com.fishingLog.spring.model.Angler;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AnglerDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private List<String> roles;
    private Instant createdAt;
    private Instant updatedAt;
    private List<RecordDTO> records;

    public AnglerDTO(Angler angler, boolean includeRecords) {
        this.id = angler.getId();
        this.firstName = angler.getFirstName();
        this.lastName = angler.getLastName();
        this.username = angler.getUsername();
        this.email = angler.getEmail();
        this.createdAt = angler.getCreatedAt();
        this.updatedAt = angler.getUpdatedAt();
        if (includeRecords) {
            this.records = RecordDTO.listFromRecords(angler.getRecords());
        }
    }
}
