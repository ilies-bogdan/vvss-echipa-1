package tasks.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tasks.container.ArrayTaskList;
import tasks.model.Task;
import tasks.model.TasksOperations;

import java.util.Date;


public class TasksService {

    private ArrayTaskList tasks;

    public TasksService(ArrayTaskList tasks){
        this.tasks = tasks;
    }

    public ObservableList<Task> getObservableList() {
        return FXCollections.observableArrayList(tasks.getAll());
    }

    public Iterable<Task> filterTasks(Date start, Date end){
        TasksOperations tasksOps = new TasksOperations(getObservableList());
        Iterable<Task> filtered = tasksOps.incoming(start,end);
        //Iterable<Task> filtered = tasks.incoming(start, end);

        return filtered;
    }
}
