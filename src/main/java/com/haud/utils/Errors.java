package com.haud.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author webwerks
 * <p>
 * This class for Error object.In case of any exception in Application, This
 * Error object will be populated and returned.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Errors {

    @Singular
    private List<Error> errors = new ArrayList<>();

    public void addError(Error e) {
        errors.add(e);
    }

    public List<Error> getErrors() {
        return errors;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class Error {

        private String title;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String detail;
    }
}


