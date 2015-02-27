package pl.ioki.releasemonitor.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ioki.releasemonitor.Models.Commit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class Git {

    public static final String GIT_LOG_SCRIPT = "git-log.sh";
    public static final String GIT_TAGS_SCRIPT = "git-tags.sh";
    public static final String GIT_BRANCHES_SCRIPT = "git-branches.sh";
    public static final String NEWLINE = "\n";

    public static final Pattern JIRA_TASK_PATTERN = Pattern.compile("^([A-Za-z]*\\-[0-9]*)");
    public static final String GIT_SEPARATOR = "@@@";

    @Autowired
    private Shell shell;

    private String pathToGit;

    public List<Commit> getDifference(String sourceBranch, String targetBranch) {
        List<Commit> commitList = new ArrayList<Commit>();

        String rawLog = this.fetchRawLog(sourceBranch, targetBranch);

        String[] commits = rawLog.split("\\n");

        for (String rawCommit : commits) {
            Commit commit = parseRawCommitMessage(rawCommit);
            if (commit != null) {
                commitList.add(commit);
            }
        }

        return commitList;
    }

    public List<String> getReleaseTags() {
        List<String> tagList = new ArrayList<String>();

        String rawTags = this.fetchRawTags();
        tagList = Arrays.asList(rawTags.split(NEWLINE));

        return tagList;
    }

    public List<String> getReleaseBranches() {
        List<String> branchesList = new ArrayList<String>();

        String rawBranches = this.fetchRawBranches();
        branchesList = Arrays.asList(rawBranches.split(NEWLINE));

        return branchesList;
    }

    public Commit parseRawCommitMessage(String rawMessage) {
        Commit commit = null;

        String[] rawCommit = rawMessage.split(GIT_SEPARATOR, -1);
        if (rawCommit.length == 4) {
            commit = new Commit();
            commit.setHash(rawCommit[0]);
            commit.setAuthor(rawCommit[1]);
            commit.setMessage(rawCommit[3]);
            commit.setJiraTask(getJiraTaskFromMessage(commit.getMessage()));
        }

        return commit;
    }

    private String fetchRawTags() {
        return shell.executeCommand("sh " + pathToGit + "/" + GIT_TAGS_SCRIPT);
    }

    private String fetchRawBranches() {
        return shell.executeCommand("sh " + pathToGit + "/" + GIT_BRANCHES_SCRIPT);
    }

    private String fetchRawLog(String sourceBranch, String targetBranch) {
        return shell.executeCommand("sh " + pathToGit + "/" + GIT_LOG_SCRIPT + " " + sourceBranch + " " + targetBranch);
    }

    private String getJiraTaskFromMessage(String message) {
        String jiraTask = null;
        Matcher matcher = JIRA_TASK_PATTERN.matcher(message);
        if (matcher.find()) {
            jiraTask = matcher.group(1);
        }

        return jiraTask;
    }

    public void setPathToGit(String pathToGit) {
        this.pathToGit = pathToGit;
    }

    public String getPathToGit() {
        return pathToGit;
    }

    public Shell getShell() {
        return shell;
    }

    public void setShell(Shell shell) {
        this.shell = shell;
    }
}