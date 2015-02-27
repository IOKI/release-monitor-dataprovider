package pl.ioki.releasemonitor.Models;

import java.util.Date;
import java.util.Objects;

public class Commit {
    private String hash;
    private String author;
    private String jiraTask;
    private String message;
    private Date date;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getJiraTask() {
        return jiraTask;
    }

    public void setJiraTask(String jiraTask) {
        this.jiraTask = jiraTask;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hash, author, jiraTask, message, date);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Commit other = (Commit) obj;
        return Objects.equals(this.hash, other.hash) && Objects.equals(this.author, other.author) && Objects.equals(this.jiraTask, other.jiraTask) && Objects.equals(this.message, other.message) && Objects.equals(this.date, other.date);
    }

    @Override
    public String toString() {
        return String.format("Commit{date=%s, message='%s', jiraTask='%s', author='%s', hash='%s'}", date, message, jiraTask, author, hash);
    }
}
