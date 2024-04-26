package tasks.lab04.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.container.ArrayTaskList;
import tasks.model.Task;
import tasks.services.TasksService;

import java.util.Date;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TaskServiceTest {
    private TasksService tasksService;
    private ArrayTaskList mockTaskList;
    private Task mockTask;

    @BeforeEach
    public void setUp() {
        mockTaskList = mock(ArrayTaskList.class);
        tasksService = new TasksService(mockTaskList);
        mockTask = mock(Task.class);
    }

    @Test
    public void testFilterTasks() {
        when(mockTaskList.getAll()).thenReturn(java.util.Collections.singletonList(mockTask));
        Date start = new Date();
        Date end = new Date(start.getTime() + 1000 * 60 * 60 * 24);
        Iterable<Task> filteredTasks = tasksService.filterTasks(start, end);
        assertNotNull("Filtered tasks should not be null", filteredTasks);
    }

    @Test
    public void testGetObservableList() {
        when(mockTaskList.getAll()).thenReturn(java.util.Collections.singletonList(mockTask));
        assertNotNull("Observable list should not be null", tasksService.getObservableList());
    }
}
