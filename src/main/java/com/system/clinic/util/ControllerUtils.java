package com.system.clinic.util;

import org.springframework.validation.BindingResult;
import com.system.clinic.exception.ValidationErrorDTO;

public final class ControllerUtils {

  private ControllerUtils() {
  }

  public static ValidationErrorDTO createValidationErrorResponse(BindingResult bindingResult) {
    var errors = new ValidationErrorDTO();
    if (bindingResult.hasErrors()) {
      bindingResult
          .getFieldErrors()
          .forEach(
              e -> errors
                  .getErrors()
                  .add(new ValidationErrorDTO.FieldError(e.getField(), e.getDefaultMessage())));
    }
    return errors;
  }

}
