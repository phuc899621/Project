package com.example.todolist.ui.viewmodel;

import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.todolist.domain.usecase.ValidateTaskInfoUseCase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AddTaskViewModel extends ViewModel {

    private final ValidateTaskInfoUseCase validateTaskInfoUseCase;

    //
    private final MutableLiveData<List<String>> priorityItems = new MutableLiveData<>();
    private final MutableLiveData<List<String>> remindOptions = new MutableLiveData<>();
    private final MutableLiveData<List<String>> repeatOptions=new MutableLiveData<>();

    //

    private final MutableLiveData<String> date = new MutableLiveData<>();
    private final MutableLiveData<String> deadlineDate = new MutableLiveData<>();
    private final MutableLiveData<String> time = new MutableLiveData<>();
    private final MutableLiveData<String> deadlineTime = new MutableLiveData<>();

    //

    private final MutableLiveData<Boolean> isEnableSetTime=new MutableLiveData<>();
    private final MutableLiveData<Boolean> isEnableSetDeadlineTime=new MutableLiveData<>();

    //
    private final MutableLiveData<String> taskTitle = new MutableLiveData<>();
    private final MutableLiveData<String> taskDescription = new MutableLiveData<>();
    private final MutableLiveData<String> taskPriority = new MutableLiveData<>();
    private final MutableLiveData<List<String>> taskCategories = new MutableLiveData<>();
    private final MutableLiveData<String> taskRepeat = new MutableLiveData<>();
    private final MutableLiveData<Pair<Integer,String>> taskReminder = new MutableLiveData<>();
    private final MutableLiveData<List<String>> subTasks = new MutableLiveData<>();

    @Inject
    public AddTaskViewModel(ValidateTaskInfoUseCase validateTaskInfoUseCase){
        this.validateTaskInfoUseCase=validateTaskInfoUseCase;
        setDefaultTimeOption();
        setDefaultRepeatOption();
        setDefaultRemindOption();
        setPriorityItems();
        setSubTasks(new ArrayList<>());
    }
    //----------------------get data-----------------------


    public LiveData<String> getDate(){
        return date;
    }
    public LiveData<String> getDeadlineDate() {
        return deadlineDate;
    }
    public LiveData<String> getTime() {
        return time;
    }
    public LiveData<String> getDeadlineTime() {
        return deadlineTime;
    }


    public LiveData<List<String>> getRemindOptions() {
        return remindOptions;
    }
    public LiveData<Boolean> getIsEnableSetTime(){
        return isEnableSetTime;
    }
    public LiveData<Boolean> getIsEnableSetDeadlineTime() {
        return isEnableSetDeadlineTime;
    }
    public LiveData<String> getTaskTitle(){
        return taskTitle;
    }
    public LiveData<String> getTaskDescription() {
        return taskDescription;
    }
    public LiveData<String> getTaskPriority() {
        return taskPriority;
    }
    public LiveData<List<String>> getTaskCategories() {
        return taskCategories;
    }
    public LiveData<String> getTaskRepeat() {
        return taskRepeat;
    }
    public LiveData<Pair<Integer,String>> getTaskReminder() {
        return taskReminder;
    }
    public LiveData<List<String>> getPriorityItems(){
        return priorityItems;
    }
    public LiveData<List<String>> getRepeatOptions(){
        return repeatOptions;
    }
    public LiveData<List<String>> getSubTasks(){
        return subTasks;
    }

    //------------------default setting--------------

    public void setDefaultTimeOption(){
        setDate("None");
        setDeadlineTime("None");
        setDeadlineDate("None");
        setTime("None");
    }
    public void setPriorityItems(){
        List<String> priorityItems=new ArrayList<>();
        priorityItems.add("High");
        priorityItems.add("Medium");
        priorityItems.add("Low");
        priorityItems.add("None");
        setPriorityItems(priorityItems);
    }
    public void setDefaultRepeatOption(){
        List<String> repeatOptions=new ArrayList<>();
        repeatOptions.add("None");
        repeatOptions.add("Daily");
        repeatOptions.add("Weekly");
        repeatOptions.add("Monthly");
        setRepeatOptions(repeatOptions);
    }
    public void setDefaultRemindOption(){
        List<String> remindOptions=new ArrayList<>();
        remindOptions.add("None");
        remindOptions.add("Custom");
        remindOptions.add("5 minutes before");
        remindOptions.add("30 minutes before");
        remindOptions.add("1 hour before");
        remindOptions.add("12 hours before");
        remindOptions.add("1 day before");
        remindOptions.add("1 week before");
        setRemindOptions(remindOptions);
    }



    //----------------set data-----------------------

    public void addTaskCategory(String item){
        List<String> currentTaskCategory;
        if(taskCategories.getValue()!=null){
            currentTaskCategory=taskCategories.getValue();
            if(!currentTaskCategory.contains(item)){
                currentTaskCategory.add(item);
                taskCategories.setValue(currentTaskCategory);
            }
        }
        else {
            currentTaskCategory=new ArrayList<>();
            currentTaskCategory.add(item);
            taskCategories.setValue(currentTaskCategory);
        }
    }
    public void deleteTaskCategory(String categoryItem){
        List<String> currentTaskCategory=taskCategories.getValue();
        currentTaskCategory.removeIf(item->item.equals(categoryItem));
        taskCategories.setValue(currentTaskCategory);
    }
    public void setTaskTitle(String title){
        taskTitle.setValue(title);
    }
    public void setTaskDescription(String description) {
        taskDescription.setValue(description);
    }
    public void setTaskPriority(String priority) {
        taskPriority.setValue(priority);
    }
    public void setTaskCategories(List<String> categories) {
        taskCategories.setValue(categories);
    }
    public void setTaskRepeat(String recurring) {
        taskRepeat.setValue(recurring);
    }
    public void setTaskReminder(Pair<Integer,String> reminder) {
        taskReminder.setValue(reminder);
    }
    public void setEnableSetTime(boolean isEnable){
        isEnableSetTime.setValue(isEnable);
    }


    public void setEnableSetDeadlineTime(boolean isEnable) {
        isEnableSetDeadlineTime.setValue(isEnable);
    }
    public void setDate(String date){
        if (date.equals("None")){
            setEnableSetTime(false);
        }else {
            setEnableSetTime(true);
        }
        this.date.setValue(date);
    }
    public void setDeadlineDate(String date){
        if(date.equals("None")){
            setEnableSetDeadlineTime(false);
        }else {
            setEnableSetDeadlineTime(true);
        }
        this.deadlineDate.setValue(date);
    }
    public void setTime(String time){
        this.time.setValue(time);
    }
    public void setDeadlineTime(String time) {
        this.deadlineTime.setValue(time);
    }
    public void setPriorityItems(List<String> priorityItems){
        this.priorityItems.setValue(priorityItems);
    }
    public void setRemindOptions(List<String> remindOptions){
        this.remindOptions.setValue(remindOptions);
    }
    public void setRepeatOptions(List<String> repeatOptions){
        this.repeatOptions.setValue(repeatOptions);
    }
    public void setSubTasks(List<String> subTasks){
        this.subTasks.setValue(subTasks);
    }
    public void removeSubTask(String subTask){
        List<String> currentSubTasks=subTasks.getValue();
        currentSubTasks.removeIf(item->item.equals(subTask));
        subTasks.setValue(currentSubTasks);
    }
    public void removeSubTaskAt(int position){
        if(position>subTasks.getValue().size()-1){
            return;
        }
        List<String> currentSubTasks=subTasks.getValue();
        currentSubTasks.remove(position);
        subTasks.setValue(currentSubTasks);
    }
    public void addSubTask(String subTask){
        List<String> currentSubTasks=subTasks.getValue();
        currentSubTasks.add(subTask);
        subTasks.setValue(currentSubTasks);
    }
    public void editSubTask(String s,int position){
        if(position>subTasks.getValue().size()-1){
            return;
        }
        subTasks.getValue().set(position,s);

    }
    public void updateSubTask() {
        List<String> currentList = subTasks.getValue();
        if (currentList!=null) {
            subTasks.setValue(currentList);
        }
    }
    public void updateMoveSubTask(int fromPosition, int toPosition) {
        List<String> currentList = subTasks.getValue();
        if (currentList != null) {
            Collections.swap(currentList, fromPosition, toPosition);
            subTasks.setValue(currentList);
        }
    }




}
