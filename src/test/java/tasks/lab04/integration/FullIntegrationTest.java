package tasks.lab04.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.container.ArrayTaskList;
import tasks.model.Task;
import tasks.services.TasksService;

import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FullIntegrationTest {
    private TasksService taskService;
    private ArrayTaskList arrayTaskList;
    private Task task1, task2;

    @BeforeEach
    public void setUp() {
        arrayTaskList = new ArrayTaskList();
        taskService = new TasksService(arrayTaskList);


        task1 = new Task("Task 1", new Date());
        task2 = new Task("Task 2", new Date(System.currentTimeMillis() + 1000 * 60 * 60));


        arrayTaskList.add(task1);
        arrayTaskList.add(task2);
    }

    @Test
    public void whenFilterTasksCalled_thenCorrectTasksFiltered() {
        task1.setActive(true);
        task2.setActive(true);

        Date start = new Date();
        Date end = new Date(start.getTime() + 1000 * 60 * 60 * 24);
        Iterable<Task> filtered = taskService.filterTasks(start, end);


        boolean foundTask1 = false, foundTask2 = false;
        for (Task task : filtered) {
            if (task.equals(task1)) foundTask1 = true;
            if (task.equals(task2)) foundTask2 = true;
        }
        assertFalse("Filtered tasks should not contain task1", foundTask1);
        assertTrue("Filtered tasks should contain task2", foundTask2);
    }

    @Test
    public void whenFilterTasksWithInactiveTasks_thenTheyAreNotFiltered() {
        task1.setActive(false);
        task2.setActive(false);

        Date start = new Date();
        Date end = new Date(start.getTime() + 1000 * 60 * 60 * 24);
        Iterable<Task> filtered = taskService.filterTasks(start, end);


        for (Task task : filtered) {
            assertFalse("Inactive tasks should not be filtered", task.isActive());
        }
    }
}

