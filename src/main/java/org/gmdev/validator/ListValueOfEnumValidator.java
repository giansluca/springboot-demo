package org.gmdev.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListValueOfEnumValidator implements ConstraintValidator<ListValueOfEnum, List<String>> {

    private List<String> acceptedValues;

    @Override
    public void initialize(ListValueOfEnum annotation) {
        acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(List<String> stringList, ConstraintValidatorContext constraintValidatorContext) {
        if (stringList == null) return true;

        for (String item : stringList)
            if (!acceptedValues.contains(item)) return false;

        return true;
    }

}
