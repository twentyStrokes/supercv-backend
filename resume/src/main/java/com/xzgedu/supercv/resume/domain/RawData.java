package com.xzgedu.supercv.resume.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RawData {
    private Profile profile;
    private List<Module> modules = new ArrayList<>();
}
