package com.example.todolist.utils;

import java.util.List;

import javax.inject.Inject;

public class ValidateHelper {

    private final int MAX_TITLE_LENGTH = 30;
    private final int MAX_DESCRIPTION_LENGTH = 60;
    private final String[] priorityItems = {
            "Low", "Medium", "High","None"
    };
    private final int MAX_CATEGORY_TAG = 3;
    @Inject
    public ValidateHelper() {
    }

    public boolean isValidateTitle(String text) {
        return text.length() <= MAX_TITLE_LENGTH;
    }
    public boolean isValidateDescription(String text) {
        return text.length() <= MAX_DESCRIPTION_LENGTH;
    }
    public boolean isValidatePriority(String priority) {
        for (String item : priorityItems) {
            if (item.equals(priority)) {
                return true;
            }
        }
        return false;
    }
    public boolean isValidateCategories(List<String> categories) {
        return categories.size() <= MAX_CATEGORY_TAG;
    }
}
