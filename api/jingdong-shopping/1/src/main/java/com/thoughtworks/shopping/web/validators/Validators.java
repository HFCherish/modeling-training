package com.thoughtworks.shopping.web.validators;

import com.thoughtworks.shopping.Uniqueness;

import javax.ws.rs.BadRequestException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

/**
 * Created by pzzheng on 11/22/16.
 */
public class Validators {
    public static void validate(Map<String, Object> info, Validator validator) {
        Optional<String> errorMessage = validator.validate(info);
        if (errorMessage.isPresent()) {
            throw new BadRequestException(errorMessage.get());
        }
    }

    public interface Validator {
        Optional<String> validate(Map<String, Object> info);
    }

    public static Validator fieldNotEmpty(String field, String message) {
        return info -> info.getOrDefault(field, "").toString().isEmpty() ? Optional.of(message) : Optional.empty();
    }

    public static Validator collectionFieldNotEmpty(String field, String message) {
        return info -> ((Collection)info.getOrDefault(field, Collections.EMPTY_LIST)).isEmpty() ? Optional.of(message) : Optional.empty();
    }

    public static Validator all(Validator... validators) {
        return info -> {
            List<String> messages = asList(validators).stream().map(v -> v.validate(info)).filter(m -> m.isPresent()).map(message -> message.get()).collect(Collectors.toList());
            return messages.size() != 0 ? Optional.of(String.join("\n", messages)) : Optional.empty();
        };
    }

    public static Validator unique(String field, String message, Uniqueness repo) {
        return info -> repo.findBy(info.get(field)).map(e -> message);
    }


}
