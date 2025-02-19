package com.example.todolist.ui;

import static android.view.View.VISIBLE;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;
import com.example.todolist.data.local.pref.PrefManager;
import com.example.todolist.databinding.ActivityAddTaskBinding;
import com.example.todolist.databinding.DialogAddCategoryBinding;
import com.example.todolist.databinding.DialogChooseCategoryBinding;
import com.example.todolist.databinding.DialogCustomReminderBinding;
import com.example.todolist.databinding.DialogSetTaskDateBinding;
import com.example.todolist.domain.usecase.AddCategoriesToRefUseCase;
import com.example.todolist.domain.usecase.DateFormattingUseCase;
import com.example.todolist.domain.usecase.DeleteCategoriesFromRefUseCase;
import com.example.todolist.domain.usecase.GetCategoriesFromRefUseCase;
import com.example.todolist.ui.adapter.CategoryAdapter;
import com.example.todolist.ui.adapter.PriorityAdapter;
import com.example.todolist.ui.adapter.RemindAdapter;
import com.example.todolist.ui.adapter.RepeatAdapter;
import com.example.todolist.ui.adapter.SubTaskAdapter;
import com.example.todolist.ui.viewmodel.AddTaskViewModel;
import com.example.todolist.ui.viewmodel.CalendarViewModel;
import com.example.todolist.ui.viewmodel.CategoryViewModel;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.robertlevonyan.views.chip.Chip;
import com.robertlevonyan.views.chip.OnCloseClickListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddTaskActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    private static final int START_DAY_KEY=1;
    private static final int END_DAY_KEY=2;
    private static final int START_TIME_KEY=3;
    private static final int END_TIME_KEY=4;
    private static final int GET_DAY_KEY=1;
    private static final int GET_TIME_KEY=2;
    private int selectedView;

    //async

    private final Handler handler=new Handler(Looper.getMainLooper());
    //layout binding
    private ActivityAddTaskBinding binding;
    private DialogSetTaskDateBinding taskDateBinding;
    private DialogChooseCategoryBinding categoryBinding;
    private DialogAddCategoryBinding addCategoryBinding;
    private DialogCustomReminderBinding reminderBinding;

    //adapter
    SubTaskAdapter subTaskAdapter;
    CategoryAdapter categoryAdapter;
    RemindAdapter remindAdapter;
    RepeatAdapter repeatAdapter;
    PriorityAdapter priorityAdapter;

    //viewmodel
    protected AddTaskViewModel mainViewModel;
    protected CalendarViewModel calendarViewModel;
    protected CategoryViewModel categoryViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        initLayoutBinding();
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
        initViewModel();
        initSettingAsync();

    }
    public void initLayoutBinding(){
        binding=ActivityAddTaskBinding.inflate(getLayoutInflater());
    }
    public void init() {
        setSupportActionBar(binding.tbAddTask);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.tbAddTask.setNavigationOnClickListener(view->finish());
    }
    public void initViewModel(){
        mainViewModel=new ViewModelProvider(this).get(AddTaskViewModel.class);
        calendarViewModel=new ViewModelProvider(this).get(CalendarViewModel.class);
        categoryViewModel=new ViewModelProvider(this).get(CategoryViewModel.class);
    }
    public void initSettingAsync() {
        settingCategory();
        settingTime();
        settingPriority();
        settingRepeat();
        settingReminder();
        settingAddSubtask();
        settingGetTaskInfo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);

    }
    //usefull method
    private <T> void observeLiveData(LiveData<T> liveData, Observer<T> observer) {
        liveData.observe(this, observer);
    }

    //-------------Setting time---------------
    public void settingTime(){
        //update ui
        observeLiveData(mainViewModel.getDate(),s->binding.tieSetDate.setText(s));
        observeLiveData(mainViewModel.getTime(),s->binding.tieSetTime.setText(s));
        observeLiveData(mainViewModel.getDeadlineDate(),s->binding.tieSetDeadlineDate.setText(s));
        observeLiveData(mainViewModel.getDeadlineTime(),s->binding.tieSetDeadlineTime.setText(s));
        observeLiveData(mainViewModel.getIsEnableSetTime(),this::setTimeLayout);
        observeLiveData(mainViewModel.getIsEnableSetDeadlineTime(),this::setDeadlineLayout);

        //click event
        binding.tieSetDate.setOnClickListener(view->callDialogSettingTime());
        binding.tieSetTime.setOnClickListener(view->{
                    PopupMenu popupMenu = new PopupMenu(AddTaskActivity.this, view);
                    popupMenu.getMenu().add("None");
                    popupMenu.getMenu().add("Custom");

                    popupMenu.setOnMenuItemClickListener(menuItem -> {
                        if(menuItem.getTitle().equals("None")){
                            mainViewModel.setTime("None");
                        }else{
                            callDialogSetTime();
                        }
                        return false;
                    });
                    popupMenu.show();
                });
        binding.tieSetDeadlineDate.setOnClickListener(view->{
                    PopupMenu popupMenu = new PopupMenu(AddTaskActivity.this, view);
                    popupMenu.getMenu().add("None");
                    popupMenu.getMenu().add("Custom");

                    popupMenu.setOnMenuItemClickListener(menuItem -> {
                        if(menuItem.getTitle().equals("None")){
                            mainViewModel.setDeadlineDate("None");
                        }else{
                            callDialogDeadlineDate();
                        }
                        return false;
                    });
                    popupMenu.show();
                });
        binding.tieSetDeadlineTime.setOnClickListener(view->{
            PopupMenu popupMenu = new PopupMenu(AddTaskActivity.this, view);
            popupMenu.getMenu().add("None");
            popupMenu.getMenu().add("Custom");

            popupMenu.setOnMenuItemClickListener(menuItem -> {
                if(menuItem.getTitle().equals("None")){
                    mainViewModel.setDeadlineTime("None");
                }else{
                    callDialogDeadlineTime();
                }
                return false;
            });

            popupMenu.show();
        });
    }
    public void setTimeLayout(Boolean b){
        if (b){
            binding.tilSetTime.setVisibility(VISIBLE);
            binding.tvSetTime.setVisibility(VISIBLE);
        }
        else {
            binding.tilSetTime.setVisibility(View.GONE);
            binding.tvSetTime.setVisibility(View.GONE);
        }
    }
    public void setDeadlineLayout(Boolean b){
        if (b){
            binding.tilSetDeadlineTime.setVisibility(VISIBLE);
        }
        else {
            binding.tilSetDeadlineTime.setVisibility(View.GONE);
        }
    }
    public void callDialogSettingTime(){
        Dialog dialog=new Dialog(this);
        taskDateBinding=DialogSetTaskDateBinding.inflate(getLayoutInflater());
        dialog.setCancelable(true);
        dialog.setContentView(taskDateBinding.getRoot());
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();

        Observer<String> chosenOptionObserve=s->{
            if(s.equals("None")){
                taskDateBinding.mbtnChosenTime.setVisibility(View.GONE);
            }else {
                taskDateBinding.mbtnChosenTime.setVisibility(View.VISIBLE);
            }
            taskDateBinding.tvChosenOption.setText(s);
        };
        Observer<String> todayDateObserve=s->taskDateBinding.mbtnTodayOption.setText("Today"+"   "+s);
        Observer<String> tomorrowDateObserve=s->taskDateBinding.mbtnTomorrowOption.setText("Tomorrow" + "   " + s);
        Observer<String> nextWeekObserve=s->taskDateBinding.mbtnNextWeekOption.setText("Next week" + "  " + s);
        Observer<String> customObserve=s->taskDateBinding.mbtnCustomOption.setText("Custom" + "  " + s);

        observeLiveData(calendarViewModel.getTodayDate(),todayDateObserve);
        observeLiveData(calendarViewModel.getTomorrowDate(),tomorrowDateObserve);
        observeLiveData(calendarViewModel.getNextWeekDate(),nextWeekObserve);
        observeLiveData(calendarViewModel.getCustomDate(),customObserve);
        observeLiveData(calendarViewModel.getChosenTimeOption(),chosenOptionObserve);

        dialog.setOnDismissListener(dialogInterface -> {
            calendarViewModel.getChosenTimeOption().removeObserver(chosenOptionObserve);
            calendarViewModel.getTodayDate().removeObserver(todayDateObserve);
            calendarViewModel.getTomorrowDate().removeObserver(tomorrowDateObserve);
            calendarViewModel.getNextWeekDate().removeObserver(nextWeekObserve);
            calendarViewModel.getCustomDate().removeObserver(customObserve);
        });
        taskDateBinding.mbgOptions.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if(checkedId==R.id.mbtnTodayOption&&isChecked){
                    calendarViewModel.setChosenTimeOption("Today");
                    return;
                }
                if(checkedId==R.id.mbtnTomorrowOption&&isChecked){
                    calendarViewModel.setChosenTimeOption("Tomorrow");
                }
                if(checkedId==R.id.mbtnNextWeekOption&&isChecked){
                    calendarViewModel.setChosenTimeOption("Next week");
                    return;
                }
                if(checkedId==R.id.mbtnCustomOption&&isChecked){
                    calendarViewModel.setChosenTimeOption("Custom");
                    return;
                }
                if(checkedId==R.id.mbtnNoneOption&&isChecked){
                    calendarViewModel.setChosenTimeOption("None");
                    return;
                }
            }
        });

        taskDateBinding.mbtnCustomOption.setOnClickListener(view->callDialogSetTime());
        taskDateBinding.mbtnCustomOption.setOnClickListener(view->callDialogCustomDate());
        taskDateBinding.btnCancel.setOnClickListener(view->dialog.dismiss());
        taskDateBinding.btnOke.setOnClickListener(view-> {
            int checkedsId = taskDateBinding.mbgOptions.getCheckedButtonId();
            if (checkedsId == R.id.mbtnTodayOption) {
                mainViewModel.setDate(calendarViewModel.getTodayDate().getValue().trim());
            }
            if (checkedsId == R.id.mbtnTomorrowOption) {
                mainViewModel.setDate(calendarViewModel.getTomorrowDate().getValue().trim());
            }
            if (checkedsId == R.id.mbtnNextWeekOption) {
                mainViewModel.setDate(calendarViewModel.getNextWeekDate().getValue().trim());
            }
            if (checkedsId == R.id.mbtnCustomOption) {
                mainViewModel.setDate(calendarViewModel.getCustomDate().getValue().trim());
            }
            if (checkedsId == R.id.mbtnNoneOption) {
                mainViewModel.setDate("None");
            }
            dialog.dismiss();
        });
        taskDateBinding.mbtnChosenTime.setOnClickListener(view->callDialogSetTime());
    }
    public void callDialogCustomDate(){
        DatePickerDialog datePicker=DatePickerDialog.newInstance(
                this,
                calendarViewModel.getCalendar(calendarViewModel.getCustomDate().getValue(),GET_DAY_KEY).get(Calendar.YEAR),
                calendarViewModel.getCalendar(calendarViewModel.getCustomDate().getValue(),GET_DAY_KEY).get(Calendar.MONTH),
                calendarViewModel.getCalendar(calendarViewModel.getCustomDate().getValue(),GET_DAY_KEY).get(Calendar.DAY_OF_MONTH)
        );
        datePicker.setAccentColor(ContextCompat.getColor(this,R.color.green));
        datePicker.show(getSupportFragmentManager(),"datePicker");
        datePicker.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                taskDateBinding.mbgOptions.check(R.id.mbtnNoneOption);
            }
        });
    }
    public void callDialogSetTime(){
        TimePickerDialog timePicker=TimePickerDialog.newInstance(
                this,
                calendarViewModel.getTodayCalendar().get(Calendar.HOUR_OF_DAY),
                calendarViewModel.getTodayCalendar().get(Calendar.MINUTE),
                true);
        timePicker.setAccentColor(ContextCompat.getColor(this,R.color.green));
        timePicker.show(getSupportFragmentManager(),"timePicker");
    }
    public void callDialogDeadlineDate(){
        DatePickerDialog datePicker=DatePickerDialog.newInstance(
                this,
                calendarViewModel.getTodayCalendar().get(Calendar.YEAR),
                calendarViewModel.getTodayCalendar().get(Calendar.MONTH),
                calendarViewModel.getTodayCalendar().get(Calendar.DAY_OF_MONTH)
        );
        datePicker.setAccentColor(ContextCompat.getColor(this,R.color.green));
        datePicker.show(getSupportFragmentManager(),"deadlineDatePicker");
    }
    public void callDialogDeadlineTime(){
        TimePickerDialog timePicker=TimePickerDialog.newInstance(
                this,
                calendarViewModel.getTodayCalendar().get(Calendar.HOUR_OF_DAY),
                calendarViewModel.getTodayCalendar().get(Calendar.MINUTE),
                true);
        timePicker.setAccentColor(ContextCompat.getColor(this,R.color.green));
        timePicker.show(getSupportFragmentManager(),"deadlineTimePicker");
    }
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        switch (view.getTag()){
            case "datePicker":{
                calendarViewModel.setCustomDate(calendarViewModel.getDateFormatting(year, monthOfYear + 1, dayOfMonth));
                break;
            }
            case "deadlineDatePicker":{
                mainViewModel.setDeadlineDate(calendarViewModel.getDateFormatting(year,monthOfYear+1,dayOfMonth));
                break;
            }
            default:break;
        }


    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        switch (view.getTag()){
            case "timePicker":{
                mainViewModel.setTime(calendarViewModel.getTimeFormatting(hourOfDay,minute));
                break;
            }
            case "deadlineTimePicker":{
                mainViewModel.setDeadlineTime(calendarViewModel.getTimeFormatting(hourOfDay,minute));
                break;
            }
            default:break;
        }

    }
    //----------Setting Priority--------------------
    public void settingPriority(){
        priorityAdapter=new PriorityAdapter(AddTaskActivity.this,new ArrayList<>());
        binding.tiePriority.setAdapter(priorityAdapter);
        observeLiveData(mainViewModel.getPriorityItems(),this::updatePriority);
    }
    public void updatePriority(List<String> strings){
        priorityAdapter.updateData(strings);

        if(binding.tiePriority.getOnItemClickListener()==null){
            binding.tiePriority.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    mainViewModel.setTaskPriority(priorityAdapter.getItem(i));
                    binding.tiePriority.setText(priorityAdapter.getItem(i),false);
                    switch (i){
                        case 0:{
                            binding.tiePriority.setTextColor(ContextCompat.getColor(AddTaskActivity.this,R.color.red));
                            binding.tiePriority.setCompoundDrawableTintList(ColorStateList.valueOf(ContextCompat.getColor(AddTaskActivity.this,R.color.red)));
                            break;
                        }
                        case 1:{
                            binding.tiePriority.setTextColor(ContextCompat.getColor(AddTaskActivity.this,R.color.orange));
                            binding.tiePriority.setCompoundDrawableTintList(ColorStateList.valueOf(ContextCompat.getColor(AddTaskActivity.this,R.color.orange)));
                            break;
                        }
                        case 2:{
                            binding.tiePriority.setTextColor(ContextCompat.getColor(AddTaskActivity.this,R.color.green));
                            binding.tiePriority.setCompoundDrawableTintList(ColorStateList.valueOf(ContextCompat.getColor(AddTaskActivity.this,R.color.green)));
                            break;
                        }
                        case 3:{
                            binding.tiePriority.setTextColor(ContextCompat.getColor(AddTaskActivity.this,R.color.grey));
                            binding.tiePriority.setCompoundDrawableTintList(ColorStateList.valueOf(ContextCompat.getColor(AddTaskActivity.this,R.color.grey)));
                            break;
                        }
                        default:break;
                    }
                }

            });
            binding.tiePriority.performCompletion();
            binding.tiePriority.getOnItemClickListener()
                    .onItemClick(null, null, 3, 0);
        }
    }

    //-----------setting Category------------------
    public void settingCategory(){
        observeLiveData(mainViewModel.getTaskCategories(),s->updateChipGroup(s));
        observeLiveData(categoryViewModel.getCategoryName(),s->categoryViewModel.addCategoryItem(s));
        binding.ivAddCategory.setOnClickListener(view-> callDialogChooseCategory());
    }
    public void updateChipGroup(List<String> items) {
        binding.cgCategory.removeAllViews();
        for (String item : items) {
            Chip chipTemp = new Chip(this);
            chipTemp.setChipBackgroundColor(ContextCompat.getColor(this, R.color.green));
            chipTemp.setChipTextColor(ContextCompat.getColor(this, R.color.white));
            chipTemp.setText(item);
            chipTemp.setClosable(true);
            chipTemp.setChipCloseColor(R.color.red);
            chipTemp.setChipCloseColor(ContextCompat.getColor(this,R.color.red));
            chipTemp.setClickable(true);
            chipTemp.setOnCloseClickListener(new OnCloseClickListener() {
                @Override
                public void onCloseClick(@NonNull View view) {
                    mainViewModel.deleteTaskCategory(item);
                }
            });
            binding.cgCategory.addView(chipTemp);
        }


    }

    public void callDialogChooseCategory(){
        categoryBinding=DialogChooseCategoryBinding.inflate(getLayoutInflater());
        Dialog dialog=new Dialog(this);
        dialog.setContentView(categoryBinding.getRoot());
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        categoryAdapter=new CategoryAdapter(new ArrayList<>());
        categoryBinding.rvCategory.setAdapter(categoryAdapter);
        observeLiveData(categoryViewModel.getCategoryItems(),s->categoryAdapter.updateData(s));
        categoryBinding.mbtnOke.setOnClickListener(view->{
                    if(!categoryAdapter.getSelectedName().isEmpty()){
                        categoryViewModel.setCategoryName(categoryAdapter.getSelectedName());
                    }
                    dialog.dismiss();
                });
        categoryBinding.mbtnCancel.setOnClickListener(view->dialog.dismiss());
        categoryBinding.mbtnAddCategory.setOnClickListener(view->callDialogAddCategory());
        dialog.show();

    }
    public void callDialogAddCategory() {
        addCategoryBinding=DialogAddCategoryBinding.inflate(getLayoutInflater());
        Dialog dialog=new Dialog(this);
        dialog.setContentView(addCategoryBinding.getRoot());
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
        addCategoryBinding.mbtnCancel.setOnClickListener(view->dialog.dismiss());
        addCategoryBinding.mbtnConfirm.setOnClickListener(view->{
            if(addCategoryBinding.tieCategoryName.getText().toString().isEmpty()){
                dialog.dismiss();
                return;
            }
            categoryViewModel.addCategoryItem(addCategoryBinding.tieCategoryName.getText().toString());
            dialog.dismiss();
        });
    }
    public void settingRepeat(){
        repeatAdapter=new RepeatAdapter(AddTaskActivity.this,new ArrayList<>());
        binding.tieRepeat.setAdapter(repeatAdapter);
        observeLiveData(mainViewModel.getRepeatOptions(),s-> {
            repeatAdapter.updateData(s);
            binding.tieRepeat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String selectedItem=repeatAdapter.getItem(i);
                    binding.tieRepeat.setText(selectedItem,false);
                    mainViewModel.setTaskRepeat(selectedItem);
                }
            });
            binding.tieRepeat.performCompletion();
            binding.tieRepeat.getOnItemClickListener()
                    .onItemClick(null, null, 0, 0);
        });

    }
    //-----------------Setting reminder---------------
    public void settingReminder(){
        mainViewModel.getTaskReminder().observe(this, pair-> {
            if (pair.second.equals("None")) {
                binding.tieReminder.setText("None", false);
                return;
            }
            binding.tieReminder.setText(pair.first + " " + pair.second + " before", false);
        });

        remindAdapter=new RemindAdapter(AddTaskActivity.this,new ArrayList<>());
        binding.tieReminder.setAdapter(remindAdapter);

        mainViewModel.getRemindOptions().observe(this, lists->{
            remindAdapter.updateItems(lists);
            binding.tieReminder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String selectedItem=remindAdapter.getItem(i);
                    binding.tieReminder.setText(selectedItem,false);
                    switch (remindAdapter.getItem(i)){
                        case "Custom":{
                            callDialogCustomRemindTime();
                            break;
                        }
                        case "None":{
                            mainViewModel.setTaskReminder(new Pair<>(0,"None"));
                            break;
                        }
                        case "5 minutes before":{
                            mainViewModel.setTaskReminder(new Pair<>(5,"Minute"));
                            break;
                        }
                        case "30 minutes before":{
                            mainViewModel.setTaskReminder(new Pair<>(30,"Minute"));
                            break;
                        }
                        case "1 hour before":{
                            mainViewModel.setTaskReminder(new Pair<>(1,"Hour"));
                            break;
                        }
                        case "12 hours before":{
                            mainViewModel.setTaskReminder(new Pair<>(12,"Hour"));
                            break;
                        }
                        case "1 day before":{
                            mainViewModel.setTaskReminder(new Pair<>(1,"Day"));
                        }
                        case "1 week before":{
                            mainViewModel.setTaskReminder(new Pair<>(1,"Week"));
                            break;
                        }
                        default:break;
                    }
                }
            });
            binding.tieReminder.performCompletion();
            binding.tieReminder.getOnItemClickListener()
                    .onItemClick(null, null, 0, 0);
        });
    }
    public void callDialogCustomRemindTime(){
        reminderBinding=DialogCustomReminderBinding.inflate(getLayoutInflater());
        Dialog dialog=new Dialog(this);
        dialog.setContentView(reminderBinding.getRoot());
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
        String[] timeOptions=getResources().getStringArray(R.array.timeOptions);
        ArrayAdapter<String> timeAdapter=new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item,timeOptions
        );

        reminderBinding.tieChoose.setAdapter(timeAdapter);
        reminderBinding.tieChoose.setText(timeOptions[0],false);
        reminderBinding.btnCancel.setOnClickListener(view->dialog.dismiss());
        reminderBinding.btnOke.setOnClickListener(view->{
            mainViewModel.setTaskReminder(new Pair<>(
                    Integer.parseInt(reminderBinding.tieInput.getText().toString())
                    ,reminderBinding.tieChoose.getText().toString()));
            dialog.dismiss();
        });
    }
    //-----------------setting add subtask------------------
    public void settingAddSubtask(){
        subTaskAdapter=new SubTaskAdapter(new ArrayList<>(),
                position->mainViewModel.removeSubTaskAt(position),
                (s,position)->mainViewModel.editSubTask(s, position),
                (fromPosition,toPosition)->mainViewModel.updateMoveSubTask(fromPosition,toPosition)
        );


        binding.rvSubTask.setAdapter(subTaskAdapter);
        mainViewModel.getSubTasks().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                int oldSize = subTaskAdapter.getItemCount();
                int newSize = strings.size();
                if (oldSize != newSize) {
                    subTaskAdapter.updateData(strings);
                    binding.rvSubTask.post(() -> {
                        if (subTaskAdapter.getItemCount() > 0) {
                            binding.nsvAddTaskContent.fullScroll(View.FOCUS_DOWN);
                        }
                    });
                } else {
                    List<String> oldList = new ArrayList<>(subTaskAdapter.getSubTasks());

                    for (int i = 0; i < newSize; i++) {
                        if (!strings.get(i).equals(oldList.get(i))) {
                            int fromPosition = oldList.indexOf(strings.get(i));
                            if (fromPosition != -1 && fromPosition != i) {
                                subTaskAdapter.moveItem(fromPosition, i);
                                return;
                            }
                        }
                    }

                    for (int i = 0; i < newSize; i++) {
                        if (!subTaskAdapter.getSubTasks().get(i).equals(strings.get(i))) {
                            subTaskAdapter.getSubTasks().set(i, strings.get(i));
                            subTaskAdapter.notifyItemChanged(i);
                            return;
                        }
                    }
                }
            }
        });

        binding.llSubTask.setOnClickListener(view->mainViewModel.addSubTask(""));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();

                mainViewModel.updateMoveSubTask(fromPosition,toPosition);
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            }
        });

        itemTouchHelper.attachToRecyclerView(binding.rvSubTask);

    }

    public void settingGetTaskInfo(){

    }


}