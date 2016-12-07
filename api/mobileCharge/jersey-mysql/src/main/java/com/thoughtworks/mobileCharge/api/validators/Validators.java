package com.thoughtworks.mobileCharge.api.validators;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

/**
 * Created by pzzheng on 11/29/16.
 */
public class Validators {

    public static void validate(Validator validator, Map<String, Object> info) {
        Optional<String> messages = validator.validate(info);
        if (messages.isPresent()) {
            throw new IllegalArgumentException(messages.get());
        }
    }

    public interface Validator {
        Optional<String> validate(Map<String, Object> info);
    }

    public static Validator fieldNotEmpty(String field, String message) {
        return info -> info.getOrDefault(field, "").toString().isEmpty() ? Optional.of(message) : Optional.empty();
    }

    public static Validator all(Validator... validators) {
        return info -> {
            List<String> messages = asList(validators).stream().map(v -> v.validate(info)).filter(m -> m.isPresent()).map(m -> m.get()).collect(Collectors.toList());
            return messages.size() > 0 ? Optional.of(String.join("\n", messages)) : Optional.empty();
        };
    }
}
