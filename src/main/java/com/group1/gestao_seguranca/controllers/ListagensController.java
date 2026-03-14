package com.group1.gestao_seguranca.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/lists/")
public class ListagensController {

    private <T extends Enum<T>> List<Map<String, String>> buildList(T[] values) {
        return Arrays.stream(values)
                .map(v -> {
                    Map<String, String> item = new LinkedHashMap<>();
                    item.put("valor", v.name());
                    item.put("label", ((LabeledEnum) v).getLabel());
                    return item;
                })
                .collect(Collectors.toList());
    }
}
