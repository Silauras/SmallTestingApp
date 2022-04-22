package service.validation;

import java.util.regex.Pattern;

public class EmailValidator implements StringValidator {
    String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    Pattern pattern = Pattern.compile(regex);

    @Override
    public boolean isValid(String s) {
        return pattern.matcher(s).matches();
    }
}
