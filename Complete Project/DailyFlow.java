import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Collections;
import java.util.Comparator;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

// =======================
// CATEGORY CONSTANTS
// =======================
final class Category {
    public static final String STUDY = "Study";
    public static final String PRAYER = "Prayer";
    public static final String EXERCISE = "Exercise";
    public static final String SLEEP = "Sleep";
    public static final String MEAL = "Meal";
    public static final String PERSONAL = "Personal";

    public static final String[] ALL = {STUDY, PRAYER, EXERCISE, SLEEP, MEAL, PERSONAL};

    private Category() {
    }
}

// =======================
// ABSTRACT CLASS: Task
// =======================
abstract class Task {
    private String taskName;
    private String scheduledTime;
    private String priority;
    private boolean completed;

    public Task(String taskName, String scheduledTime, String priority) {
        this.taskName = taskName;
        this.scheduledTime = scheduledTime;
        this.priority = priority;
        this.completed = false;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getScheduledTime() {
        return scheduledTime;
    }

    public String getPriority() {
        return priority;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setScheduledTime(String scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    abstract String getCategory();

    public String toString() {
        String status;
        if (completed) {
            status = "Done";
        } else {
            status = "Pending";
        }

        return taskName + " | " + getCategory() + " | " + scheduledTime + " | " + priority + " | " + status;
    }

    // Serialize to a single pipe-delimited line for persistence.
    public String toFileLine() {
        return getCategory() + "|" + taskName + "|" + scheduledTime + "|" + priority + "|" + completed;
    }
}

// =======================
// CHILD CLASSES
// =======================
class StudyTask extends Task {
    public StudyTask(String taskName, String scheduledTime, String priority) {
        super(taskName, scheduledTime, priority);
    }

    @Override
    String getCategory() {
        return Category.STUDY;
    }
}

class HealthTask extends Task {
    private String category;

    public HealthTask(String taskName, String category, String scheduledTime, String priority) {
        super(taskName, scheduledTime, priority);
        this.category = category;
    }

    @Override
    String getCategory() {
        return category;
    }
}

class PersonalTask extends Task {
    private String category;

    public PersonalTask(String taskName, String category, String scheduledTime, String priority) {
        super(taskName, scheduledTime, priority);
        this.category = category;
    }

    @Override
    String getCategory() {
        return category;
    }
}

// =======================
// TASK FACTORY
// =======================
class TaskFactory {
    public static Task create(String category, String name, String time, String priority) {
        if (Category.STUDY.equals(category)) {
            return new StudyTask(name, time, priority);
        } else if (Category.EXERCISE.equals(category) || Category.SLEEP.equals(category)
                || Category.MEAL.equals(category)) {
            return new HealthTask(name, category, time, priority);
        } else {
            return new PersonalTask(name, category, time, priority);
        }
    }
}

// =======================
// TIME VALIDATION HELPER
// =======================
class TimeValidator {
    // Matches times like "09:00 PM", "9:00 AM", "12:30 pm"
    private static final Pattern TIME_PATTERN =
            Pattern.compile("^(0?[1-9]|1[0-2]):[0-5][0-9]\\s?[APap][Mm]$");

    public static boolean isValid(String time) {
        return time != null && TIME_PATTERN.matcher(time.trim()).matches();
    }
}

// =======================
// FILE PERSISTENCE
// =======================
class TaskStorage {
    private static final String FILE_NAME = "dailyflow_tasks.txt";

    public static void save(DailyLog log) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            writer.println(log.getDate());
            for (Task t : log.getTasks()) {
                writer.println(t.toFileLine());
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not save tasks: " + e.getMessage());
        }
    }

    public static DailyLog load(String fallbackDate) {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return new DailyLog(fallbackDate);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String date = reader.readLine();
            if (date == null) {
                date = fallbackDate;
            }

            DailyLog log = new DailyLog(date);
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split("\\|");
                if (parts.length < 5) {
                    continue;
                }

                String category = parts[0];
                String name = parts[1];
                String time = parts[2];
                String priority = parts[3];
                boolean completed = Boolean.parseBoolean(parts[4]);

                Task t = TaskFactory.create(category, name, time, priority);
                t.setCompleted(completed);
                log.addTask(t);
            }

            return log;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not load saved tasks: " + e.getMessage());
            return new DailyLog(fallbackDate);
        }
    }
}

// =======================
// DailyLog CLASS
// =======================
class DailyLog {
    private String date;
    private ArrayList<Task> tasks;

    public DailyLog(String date) {
        this.date = date;
        tasks = new ArrayList<Task>();
    }

    public String getDate() {
        return date;
    }

    public void addTask(Task t) {
        tasks.add(t);
    }

    public void deleteTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
        }
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void sortByTime() {
        Collections.sort(tasks, new Comparator<Task>() {
            @Override
            public int compare(Task a, Task b) {
                return timeToMinutes(a.getScheduledTime()) - timeToMinutes(b.getScheduledTime());
            }
        });
    }

    private int timeToMinutes(String time) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("hh:mm a");
            Date d = fmt.parse(time.trim().toUpperCase());
            return d.getHours() * 60 + d.getMinutes();
        } catch (Exception e) {
            return Integer.MAX_VALUE;
        }
    }

    public int getTotalTasks() {
        return tasks.size();
    }

    public int getCompletedTasks() {
        int count = 0;

        for (Task t : tasks) {
            if (t.isCompleted()) {
                count++;
            }
        }

        return count;
    }

    public int getCompletionPercentage() {
        if (tasks.size() == 0) {
            return 0;
        }

        return (getCompletedTasks() * 100) / tasks.size();
    }
}

// =======================
// RoutineSuggester CLASS
// =======================
class RoutineSuggester {
    public String suggestRoutine(ArrayList<Task> tasks) {
        if (tasks.size() < 3) {
            return "Default Beginner Routine:\n\n"
                    + "05:00 AM - Fajr Prayer\n"
                    + "08:00 AM - Breakfast / Meal\n"
                    + "10:00 AM - Study Session\n"
                    + "01:00 PM - Lunch / Rest\n"
                    + "05:00 PM - Exercise / Walk\n"
                    + "09:00 PM - OOP Revision\n"
                    + "11:00 PM - Sleep\n\n"
                    + "Reason: Less task data is available, so this is a beginner-friendly routine.";
        }

        String studyTime = mostCommonTime(tasks, Category.STUDY);
        String prayerTime = mostCommonTime(tasks, Category.PRAYER);
        String exerciseTime = mostCommonTime(tasks, Category.EXERCISE);
        String mealTime = mostCommonTime(tasks, Category.MEAL);
        String sleepTime = mostCommonTime(tasks, Category.SLEEP);

        String routine = "Suggested Routine Based on Current Tasks:\n\n";

        if (!prayerTime.equals("")) {
            routine += prayerTime + " - Prayer\n";
        }

        if (!exerciseTime.equals("")) {
            routine += exerciseTime + " - Exercise\n";
        }

        if (!mealTime.equals("")) {
            routine += mealTime + " - Meal\n";
        }

        if (!studyTime.equals("")) {
            routine += studyTime + " - Study\n";
        }

        if (!sleepTime.equals("")) {
            routine += sleepTime + " - Sleep\n";
        }

        routine += "\nConfidence: Based on your current session tasks.";

        return routine;
    }

    private String mostCommonTime(ArrayList<Task> tasks, String category) {
        HashMap<String, Integer> counts = new HashMap<String, Integer>();

        for (Task t : tasks) {
            if (t.getCategory().equals(category)) {
                String time = t.getScheduledTime();
                counts.put(time, counts.getOrDefault(time, 0) + 1);
            }
        }

        String bestTime = "";
        int bestCount = 0;

        for (String time : counts.keySet()) {
            int count = counts.get(time);
            if (count > bestCount) {
                bestCount = count;
                bestTime = time;
            }
        }

        return bestTime;
    }
}

// =======================
// StatsManager CLASS
// =======================
class StatsManager {
    public String getStats(DailyLog log) {
        ArrayList<Task> tasks = log.getTasks();

        if (tasks.size() == 0) {
            return "No tasks available for stats.";
        }

        String mostCompletedCategory = getMostCompletedCategory(tasks);
        String mostSkippedCategory = getMostSkippedCategory(tasks);

        String stats = "DailyFlow Stats Dashboard\n\n";
        stats += "Date: " + log.getDate() + "\n";
        stats += "Total Tasks: " + log.getTotalTasks() + "\n";
        stats += "Completed Tasks: " + log.getCompletedTasks() + "\n";
        stats += "Completion Percentage: " + log.getCompletionPercentage() + "%\n\n";
        stats += "Most Completed Category: " + mostCompletedCategory + "\n";
        stats += "Most Skipped Category: " + mostSkippedCategory + "\n\n";

        if (log.getCompletionPercentage() == 100) {
            stats += "Streak Status: Excellent! All tasks completed today.";
        } else if (log.getCompletionPercentage() >= 50) {
            stats += "Streak Status: Good progress. Try to complete remaining tasks.";
        } else {
            stats += "Streak Status: Low completion. Focus on high priority tasks first.";
        }

        return stats;
    }

    private String getMostCompletedCategory(ArrayList<Task> tasks) {
        HashMap<String, Integer> counts = new HashMap<String, Integer>();

        for (Task t : tasks) {
            if (t.isCompleted()) {
                String c = t.getCategory();
                counts.put(c, counts.getOrDefault(c, 0) + 1);
            }
        }

        return bestKey(counts);
    }

    private String getMostSkippedCategory(ArrayList<Task> tasks) {
        HashMap<String, Integer> counts = new HashMap<String, Integer>();

        for (Task t : tasks) {
            if (!t.isCompleted()) {
                String c = t.getCategory();
                counts.put(c, counts.getOrDefault(c, 0) + 1);
            }
        }

        return bestKey(counts);
    }

    private String bestKey(HashMap<String, Integer> counts) {
        String result = "None";
        int max = 0;

        for (String key : counts.keySet()) {
            int count = counts.get(key);
            if (count > max) {
                max = count;
                result = key;
            }
        }

        return result;
    }
}

// =======================
// MAIN GUI CLASS
// =======================
public class DailyFlow extends JFrame implements ActionListener {

    DailyLog todayLog;
    JPanel mainPanel;
    JPanel navPanel;

    JButton homeBtn, addBtn, historyBtn, routineBtn, statsBtn;
    JButton saveTaskBtn, deleteTaskBtn, editTaskBtn, toggleCompleteBtn, refreshHomeBtn;

    JTextField taskNameField;
    JTextField timeField;
    JComboBox<String> categoryBox;
    JComboBox<String> priorityBox;

    JTextArea displayArea;
    JProgressBar progressBar;

    JTable taskTable;
    DefaultTableModel tableModel;

    // Tracks whether the Add Task form is editing an existing task (-1 = new task)
    int editingIndex = -1;

    Color green = new Color(34, 139, 34);
    Color lightGreen = new Color(220, 255, 220);

    public DailyFlow() {
        String todayDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        todayLog = TaskStorage.load(todayDate);

        setTitle("DailyFlow - Smart Daily Task Tracker");
        setSize(900, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                TaskStorage.save(todayLog);
                dispose();
                System.exit(0);
            }
        });

        createNavPanel();

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.white);

        add(navPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

        showHomeScreen();

        setVisible(true);
    }

    void createNavPanel() {
        navPanel = new JPanel();
        navPanel.setBackground(green);
        navPanel.setLayout(new GridLayout(1, 5));

        homeBtn = new JButton("Home");
        addBtn = new JButton("Add Task");
        historyBtn = new JButton("History");
        routineBtn = new JButton("My Routine");
        statsBtn = new JButton("Stats");

        JButton[] buttons = {homeBtn, addBtn, historyBtn, routineBtn, statsBtn};

        for (JButton b : buttons) {
            b.setBackground(green);
            b.setForeground(Color.white);
            b.setFont(new Font("Arial", Font.BOLD, 15));
            b.addActionListener(this);
            navPanel.add(b);
        }
    }

    void clearMainPanel() {
        mainPanel.removeAll();
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    // =======================
    // HOME SCREEN (now table-based)
    // =======================
    void showHomeScreen() {
        clearMainPanel();
        editingIndex = -1;

        todayLog.sortByTime();

        JPanel top = new JPanel();
        top.setLayout(new GridLayout(3, 1));
        top.setBackground(lightGreen);

        JLabel title = new JLabel("DailyFlow - Today's Tasks", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setForeground(green);

        JLabel dateLabel = new JLabel("Date: " + todayLog.getDate(), JLabel.CENTER);
        dateLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel quote = new JLabel(getQuote(), JLabel.CENTER);
        quote.setFont(new Font("Arial", Font.ITALIC, 15));

        top.add(title);
        top.add(dateLabel);
        top.add(quote);

        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(todayLog.getCompletionPercentage());
        progressBar.setStringPainted(true);

        String[] columns = {"#", "Task", "Category", "Time", "Priority", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        taskTable = new JTable(tableModel);
        taskTable.setRowHeight(26);
        taskTable.setFont(new Font("Arial", Font.PLAIN, 14));
        taskTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        taskTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        refreshTaskTable();

        JScrollPane scroll = new JScrollPane(taskTable);

        JPanel bottom = new JPanel();
        bottom.setLayout(new FlowLayout());
        bottom.setBackground(Color.white);

        toggleCompleteBtn = new JButton("Toggle Complete");
        editTaskBtn = new JButton("Edit Selected");
        deleteTaskBtn = new JButton("Delete Selected");
        refreshHomeBtn = new JButton("Refresh");

        toggleCompleteBtn.addActionListener(this);
        editTaskBtn.addActionListener(this);
        deleteTaskBtn.addActionListener(this);
        refreshHomeBtn.addActionListener(this);

        bottom.add(toggleCompleteBtn);
        bottom.add(editTaskBtn);
        bottom.add(deleteTaskBtn);
        bottom.add(refreshHomeBtn);

        mainPanel.add(top, BorderLayout.NORTH);
        mainPanel.add(progressBar, BorderLayout.SOUTH);
        mainPanel.add(scroll, BorderLayout.CENTER);
        mainPanel.add(bottom, BorderLayout.PAGE_END);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    void refreshTaskTable() {
        tableModel.setRowCount(0);
        ArrayList<Task> tasks = todayLog.getTasks();

        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            String status = t.isCompleted() ? "Done" : "Pending";
            tableModel.addRow(new Object[]{
                    i + 1, t.getTaskName(), t.getCategory(), t.getScheduledTime(), t.getPriority(), status
            });
        }

        if (progressBar != null) {
            progressBar.setValue(todayLog.getCompletionPercentage());
            progressBar.setString(todayLog.getCompletionPercentage() + "%");
        }
    }

    int getSelectedTaskIndex() {
        int row = taskTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a task first.");
            return -1;
        }
        return row;
    }

    void toggleSelectedComplete() {
        int index = getSelectedTaskIndex();
        if (index == -1) {
            return;
        }

        Task t = todayLog.getTasks().get(index);
        t.setCompleted(!t.isCompleted());
        TaskStorage.save(todayLog);
        refreshTaskTable();
    }

    String getQuote() {
        String[] quotes = {
                "Small steps every day lead to big results.",
                "Discipline beats motivation.",
                "Plan your day, control your life.",
                "A productive day starts with one completed task.",
                "Do the important task first."
        };

        int index = (int) (Math.random() * quotes.length);
        return quotes[index];
    }

    // =======================
    // ADD / EDIT TASK SCREEN
    // =======================
    void showAddTaskScreen() {
        clearMainPanel();

        boolean isEditing = editingIndex != -1;

        JPanel form = new JPanel();
        form.setLayout(null);
        form.setBackground(Color.white);

        JLabel title = new JLabel(isEditing ? "Edit Task" : "Add New Task", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(green);
        title.setBounds(250, 20, 300, 40);
        form.add(title);

        JLabel nameLabel = new JLabel("Task Name:");
        nameLabel.setBounds(180, 100, 150, 30);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 15));
        form.add(nameLabel);

        taskNameField = new JTextField();
        taskNameField.setBounds(350, 100, 250, 30);
        form.add(taskNameField);

        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setBounds(180, 150, 150, 30);
        categoryLabel.setFont(new Font("Arial", Font.BOLD, 15));
        form.add(categoryLabel);

        categoryBox = new JComboBox<String>(Category.ALL);
        categoryBox.setBounds(350, 150, 250, 30);
        form.add(categoryBox);

        JLabel timeLabel = new JLabel("Time (e.g. 09:00 PM):");
        timeLabel.setBounds(180, 200, 180, 30);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 15));
        form.add(timeLabel);

        timeField = new JTextField("09:00 PM");
        timeField.setBounds(350, 200, 250, 30);
        form.add(timeField);

        JLabel priorityLabel = new JLabel("Priority:");
        priorityLabel.setBounds(180, 250, 150, 30);
        priorityLabel.setFont(new Font("Arial", Font.BOLD, 15));
        form.add(priorityLabel);

        String[] priorities = {"High", "Medium", "Low"};
        priorityBox = new JComboBox<String>(priorities);
        priorityBox.setBounds(350, 250, 250, 30);
        form.add(priorityBox);

        // Pre-fill fields if editing
        if (isEditing) {
            Task t = todayLog.getTasks().get(editingIndex);
            taskNameField.setText(t.getTaskName());
            categoryBox.setSelectedItem(t.getCategory());
            timeField.setText(t.getScheduledTime());
            priorityBox.setSelectedItem(t.getPriority());
        }

        saveTaskBtn = new JButton(isEditing ? "Update Task" : "Save Task");
        saveTaskBtn.setBounds(350, 320, 150, 40);
        saveTaskBtn.setBackground(green);
        saveTaskBtn.setForeground(Color.white);
        saveTaskBtn.setFont(new Font("Arial", Font.BOLD, 15));
        saveTaskBtn.addActionListener(this);
        form.add(saveTaskBtn);

        mainPanel.add(form, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    void saveTask() {
        String name = taskNameField.getText().trim();
        String category = categoryBox.getSelectedItem().toString();
        String time = timeField.getText().trim();
        String priority = priorityBox.getSelectedItem().toString();

        if (name.isEmpty() || time.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter task name and time.");
            return;
        }

        if (!TimeValidator.isValid(time)) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid time in format hh:mm AM/PM (e.g. 09:00 PM).");
            return;
        }

        if (editingIndex == -1) {
            Task task = TaskFactory.create(category, name, time, priority);
            todayLog.addTask(task);
            JOptionPane.showMessageDialog(this, "Task Added Successfully!");
        } else {
            boolean wasCompleted = todayLog.getTasks().get(editingIndex).isCompleted();
            Task updated = TaskFactory.create(category, name, time, priority);
            updated.setCompleted(wasCompleted);
            todayLog.getTasks().set(editingIndex, updated);
            JOptionPane.showMessageDialog(this, "Task Updated Successfully!");
            editingIndex = -1;
        }

        TaskStorage.save(todayLog);

        taskNameField.setText("");
        timeField.setText("09:00 PM");

        showHomeScreen();
    }

    void editSelectedTask() {
        int index = getSelectedTaskIndex();
        if (index == -1) {
            return;
        }
        editingIndex = index;
        showAddTaskScreen();
    }

    // =======================
    // HISTORY SCREEN
    // =======================
    void showHistoryScreen() {
        clearMainPanel();

        displayArea = new JTextArea();
        displayArea.setFont(new Font("Arial", Font.PLAIN, 16));
        displayArea.setEditable(false);

        String text = "History Screen\n\n";
        text += "Tasks are now saved to disk (dailyflow_tasks.txt) and persist between sessions.\n\n";
        text += "Date: " + todayLog.getDate() + "\n";
        text += "Completed: " + todayLog.getCompletedTasks() + "/" + todayLog.getTotalTasks() + "\n";
        text += "Completion: " + todayLog.getCompletionPercentage() + "%\n\n";
        text += "Tasks:\n";

        ArrayList<Task> tasks = todayLog.getTasks();

        if (tasks.size() == 0) {
            text += "No tasks available.";
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                text += (i + 1) + ". " + tasks.get(i).toString() + "\n";
            }
        }

        displayArea.setText(text);

        mainPanel.add(new JScrollPane(displayArea), BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    // =======================
    // ROUTINE SCREEN
    // =======================
    void showRoutineScreen() {
        clearMainPanel();

        displayArea = new JTextArea();
        displayArea.setFont(new Font("Arial", Font.PLAIN, 16));
        displayArea.setEditable(false);

        RoutineSuggester suggester = new RoutineSuggester();
        displayArea.setText(suggester.suggestRoutine(todayLog.getTasks()));

        mainPanel.add(new JScrollPane(displayArea), BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    // =======================
    // STATS SCREEN
    // =======================
    void showStatsScreen() {
        clearMainPanel();

        displayArea = new JTextArea();
        displayArea.setFont(new Font("Arial", Font.PLAIN, 16));
        displayArea.setEditable(false);

        StatsManager stats = new StatsManager();
        displayArea.setText(stats.getStats(todayLog));

        mainPanel.add(new JScrollPane(displayArea), BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    // =======================
    // DELETE TASK (with confirmation, selection-based)
    // =======================
    void deleteSelectedTask() {
        int index = getSelectedTaskIndex();
        if (index == -1) {
            return;
        }

        Task t = todayLog.getTasks().get(index);
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Delete task \"" + t.getTaskName() + "\"?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            todayLog.deleteTask(index);
            TaskStorage.save(todayLog);
            JOptionPane.showMessageDialog(this, "Task Deleted Successfully!");
            refreshTaskTable();
        }
    }

    // =======================
    // ACTION HANDLING
    // =======================
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == homeBtn) {
            showHomeScreen();
        } else if (e.getSource() == addBtn) {
            editingIndex = -1;
            showAddTaskScreen();
        } else if (e.getSource() == historyBtn) {
            showHistoryScreen();
        } else if (e.getSource() == routineBtn) {
            showRoutineScreen();
        } else if (e.getSource() == statsBtn) {
            showStatsScreen();
        } else if (e.getSource() == saveTaskBtn) {
            saveTask();
        } else if (e.getSource() == deleteTaskBtn) {
            deleteSelectedTask();
        } else if (e.getSource() == editTaskBtn) {
            editSelectedTask();
        } else if (e.getSource() == toggleCompleteBtn) {
            toggleSelectedComplete();
        } else if (e.getSource() == refreshHomeBtn) {
            showHomeScreen();
        }
    }

    // =======================
    // MAIN METHOD
    // =======================
    public static void main(String[] args) {
        DailyFlow app = new DailyFlow();
    }
}
