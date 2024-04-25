package tasks.lab04.unit;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tasks.container.ArrayTaskList;
import tasks.model.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArrayTaskListTest {
    private ArrayTaskList list;
    private Task task1;
    private Task task2;

    @BeforeEach
    public void setUp() {
        list = new ArrayTaskList();
        task1 = Mockito.mock(Task.class);
        task2 = Mockito.mock(Task.class);
        list.add(task1);
    }

    @Test
    public void addTask() {
        list.add(task2);
        assertEquals(2, list.size());
    }

    @Test
    public void removeTask() {
        list.remove(task1);
        assertEquals(0, list.size());
    }
}

