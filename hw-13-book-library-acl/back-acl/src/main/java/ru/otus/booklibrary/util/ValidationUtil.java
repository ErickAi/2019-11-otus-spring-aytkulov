package ru.otus.booklibrary.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.otus.booklibrary.exception.NotFoundException;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings({"WeakerAccess"})
public class ValidationUtil {

    public static <T> T checkFoundOptional(Optional<T> optional, String errorMsg) {
        checkFound(optional.isPresent(), errorMsg);
        return optional.get();
    }

    public static <T> T checkFound(T object, String errorMsg) {
        checkFound(object != null, errorMsg);
        return object;
    }

    public static void checkFound(boolean found, String errorMsg) {
        if (!found) {
            throw new NotFoundException(errorMsg);
        }
    }
}
