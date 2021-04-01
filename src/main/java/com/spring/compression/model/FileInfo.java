
package com.spring.compression.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileInfo {

    String id;
    String name;
    String type;
    Long size;

    @JsonIgnore
    @ToString.Exclude
    byte[] data;
}