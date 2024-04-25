package tasks.lab04.integration;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import tasks.container.ArrayTaskList;
import tasks.model.Task;
import tasks.services.TasksService;

import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TaskServiceArrayTaskTest {
    private TasksService taskService;
    private ArrayTaskList arrayTaskList;
    private Task mockTask1, mockTask2;

    @Before
    public void setUp() {
        arrayTaskList = new ArrayTaskList();
        taskService = new TasksService(arrayTaskList);

        mockTask1 = Mockito.mock(Task.class);
        mockTask2 = Mockito.mock(Task.class);


        when(mockTask1.nextTimeAfter(any(Date.class))).thenReturn(new Date());
        when(mockTask2.nextTimeAfter(any(Date.class))).thenReturn(new Date());


        arrayTaskList.add(mockTask1);
        arrayTaskList.add(mockTask2);
    }

    @Test
    public void whenFilterTasksCalled_thenCorrectTasksFiltered() {
        Date start = new Date();
        Date end = new Date(start.getTime() + 1000 * 60 * 60 * 24);
        Iterable<Task> filtered = taskService.filterTasks(start, end);


        assertNotNull("Filtered tasks should not be null", filtered);
        for (Task task : filtered) {
            verify(task, times(1)).nextTimeAfter(any(Date.class));
        }
    }

    @Test
    public void whenFilterTasksOnlyActiveTasksWithinTimeFrameAreFiltered() {
        // Arrange
        when(mockTask1.isActive()).thenReturn(true);
        when(mockTask2.isActive()).thenReturn(false);

        when(mockTask1.nextTimeAfter(any(Date.class)))
                .thenAnswer(invocation -> {
                    Date current = invocation.getArgument(0);
                    Date end = new Date(current.getTime() + 1000 * 60 * 60);
                    return current.before(end) ? new Date() : null;
                });


        when(mockTask2.nextTimeAfter(any(Date.class))).thenReturn(null);

        Date start = new Date();
        Date end = new Date(start.getTime() + 1000 * 60 * 60 * 24);

        // Act
        Iterable<Task> filtered = taskService.filterTasks(start, end);

        // Assert
        assertTrue("Filtered tasks should contain mockTask1", contains(filtered, mockTask1));
        assertFalse("Filtered tasks should not contain mockTask2", contains(filtered, mockTask2));
    }

    private boolean contains(Iterable<Task> tasks, Task taskToFind) {
        for (Task task : tasks) {
            if (task == taskToFind) {
                return true;
            }
        }
        return false;
    }
}
