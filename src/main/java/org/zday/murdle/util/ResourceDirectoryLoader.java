package org.zday.murdle.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceDirectoryLoader {

    private final String BASE = "/org/zday/murdle/";
    private String prefix;
    private List<String> resourceList;

    public List<String> load() {
        return resourceList.stream().map(resource -> getClass().getResource(BASE + prefix + resource).toExternalForm()).toList();
    }
}
