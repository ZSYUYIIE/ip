# User Guide

Zwee is a **desktop app for managing tasks**, optimized for use via a Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, Zwee can get your task management duties done faster than traditional GUI apps.

## Quick start

1. **Ensure you have Java 17 installed** on your Computer.

   * **Mac users:** Ensure you have the precise JDK version prescribed.
2. **Download the latest `.jar` file** from the releases page.
3. **Copy the file to the folder** you want to use as the home folder for Zwee.
4. **Open a command terminal**, `cd` into the folder you put the jar file in, and use the `java -jar zwee.jar` command to run the application.

   * A GUI should appear in a few seconds.

   *(Add your UI screenshot here: `![Zwee UI](images/Ui.png)`)*
5. **Type the command in the command box and press Enter** to execute it. e.g. typing `list` and pressing Enter will display your current tasks.

**Some example commands you can try:**

* `list` : Lists all active tasks.
* `todo read book` : Adds a Todo task to your list.
* `delete 2` : Deletes the 2nd task shown in the current list.
* `archive 1` : Moves the 1st task from your active list to the archive.
* `bye` : Exits the app.

Refer to the **Features** below for details of each command.

---

## Features

> **Notes about the command format:**
>
> * Words in `UPPER_CASE` are the parameters to be supplied by the user.
>   * e.g. in `todo DESCRIPTION`, `DESCRIPTION` is a parameter which can be used as `todo read book`.
> * Items with `/` before them are specific tags required for dates.
>   * e.g. `deadline DESCRIPTION /by DATE`
> * Accepted date formats are `yyyy-MM-dd` (e.g., 2024-12-01), `d/M/yyyy`, and `d/M/yyyy HHmm`.
> * Extraneous parameters for commands that do not take in parameters (such as `list`, `viewarchive`, and `bye`) will be ignored.
>   * e.g. if the command specifies `list 123`, it will be interpreted as `list`.
> * If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.

### Adding a Todo : `todo`

Adds a task without any date or time attached to it.

**Format:** `todo DESCRIPTION`

**Examples:**

* `todo read book`
* `todo buy groceries`

### Adding a Deadline : `deadline`

Adds a task that needs to be done before a specific date/time.

**Format:** `deadline DESCRIPTION /by DATE`

**Examples:**

* `deadline submit report /by 2024-12-01`
* `deadline return library book /by 2/12/2024 1800`

### Adding an Event : `event`

Adds a task that starts at a specific time and ends at a specific time.

**Format:** `event DESCRIPTION /from START_DATE /to END_DATE`

**Examples:**

* `event project meeting /from 2024-12-01 /to 2024-12-02`

### Listing all tasks : `list`

Shows a list of all active tasks in your slate.

**Format:** `list`

### Marking a task as done : `mark`

Marks an existing task in the list as completed.

**Format:** `mark INDEX`

* Marks the task at the specified `INDEX`.
* The index refers to the index number shown in the displayed task list.
* The index **must be a positive integer** 1, 2, 3, …

**Examples:**

* `list` followed by `mark 2` marks the 2nd task in the list as done.

### Unmarking a task : `unmark`

Marks an existing task in the list as not done.

**Format:** `unmark INDEX`

**Examples:**

* `unmark 1` marks the 1st task as incomplete.

### Locating tasks by description: `find`

Finds tasks whose descriptions contain the given keyword.

**Format:** `find KEYWORD`

* The search is case-sensitive.
* Only the description is searched.

**Examples:**

* `find book` returns `read book` and `return library book`

### Deleting a task : `delete`

Deletes the specified task from the active list permanently.

**Format:** `delete INDEX`

* Deletes the task at the specified `INDEX`.
* The index **must be a positive integer** 1, 2, 3, …

**Examples:**

* `delete 3` deletes the 3rd task in the active list.

### Archiving a task : `archive`

Moves the specified task from the active list to the archive file, keeping your main list clean while preserving history.

**Format:** `archive INDEX`

**Examples:**

* `archive 1` moves the 1st active task into the archive.

### Viewing archived tasks : `viewarchive`

Shows a list of all tasks currently stored in the archive.

**Format:** `viewarchive`

### Restoring an archived task : `unarchive`

Moves a specified task from the archive back into your active list.

**Format:** `unarchive INDEX`

* The index refers to the index number shown when using the `viewarchive` command.

**Examples:**

* `viewarchive` followed by `unarchive 2` restores the 2nd archived task to your main list.

### Exiting the program : `bye`

Exits the program.

**Format:** `bye`

---

## Saving the data

Zwee data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

## Editing the data file

Zwee data are saved automatically as text files at `[JAR file location]/data/zwee.txt` (active tasks) and `[JAR file location]/data/archive.txt` (archived tasks). Advanced users are welcome to update data directly by editing those data files.

> **Caution:** If your changes to the data file make its format invalid (e.g., missing the `|` separators or using incorrect done flags), Zwee will discard the corrupted line or fail to load. Hence, it is recommended to take a backup of the file before editing it.
> Edit the data file only if you are confident that you can update it correctly following the exact `TYPE | STATUS | DESCRIPTION | DATES` format.

---

## FAQ

**Q: How do I transfer my data to another Computer?** **A:** Install the app in the other computer and overwrite the empty `data` folder it creates with the `data` folder that contains the `zwee.txt` and `archive.txt` files of your previous Zwee home folder.

## Known issues

* When using multiple screens, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete any layout preference files created by the OS/application before running the application again.
* If a date is formatted incorrectly outside of the accepted bounds, the application may default to treating it as a standard string or throw an error depending on the strictness of the date parser.

---

## Command summary

| Action                 | Format, Examples                                                                                                       |
| :--------------------- | :--------------------------------------------------------------------------------------------------------------------- |
| **Add Todo**     | `todo DESCRIPTION<br>`e.g., `todo read book`                                                                       |
| **Add Deadline** | `deadline DESCRIPTION /by DATE<br>`e.g., `deadline submit report /by 2024-12-01`                                   |
| **Add Event**    | `event DESCRIPTION /from START_DATE /to END_DATE<br>`e.g., `event project meeting /from 2024-12-01 /to 2024-12-02` |
| **List**         | `list`                                                                                                               |
| **Mark**         | `mark INDEX<br>`e.g., `mark 1`                                                                                     |
| **Unmark**       | `unmark INDEX<br>`e.g., `unmark 1`                                                                                 |
| **Find**         | `find KEYWORD<br>`e.g., `find book`                                                                                |
| **Delete**       | `delete INDEX<br>`e.g., `delete 3`                                                                                 |
| **Archive**      | `archive INDEX<br>`e.g., `archive 2`                                                                               |
| **View Archive** | `viewarchive`                                                                                                        |
| **Unarchive**    | `unarchive INDEX<br>`e.g., `unarchive 1`                                                                           |
| **Exit**         | `bye`                                                                                                                |
