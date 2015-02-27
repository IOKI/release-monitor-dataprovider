package pl.ioki.releasemonitor.Services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import pl.ioki.releasemonitor.Models.Commit;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class GitTest {

    private static final String MESSAGE_1 = "hash 1@@@Author 1@@@Date 1@@@Message 1";
    private static final String MESSAGE_WITH_JIRA_TASK = "hash 1@@@Author 1@@@Date 1@@@TASK-123 Message 1";
    private static final String MESSAGE_2 = "hash 2@@@Author 2@@@Date 2@@@Message 2";
    private static final String BRANCH_1 = "branch1";
    private static final String BRANCH_2 = "branch2";
    private static final String TAG_2 = "tag2";
    private static final String TAG_1 = "tag1";
    private static final String EXPECTED_AUTHOR_1 = "Author 1";
    private static final String EXPECTED_HASH_1 = "hash 1";
    private static final String EXPECTED_MESSAGE_1 = "Message 1";
    private static final String EXPECTED_AUTHOR_2 = "Author 2";
    private static final String EXPECTED_HASH_2 = "hash 2";
    private static final String EXPECTED_MESSAGE_2 = "Message 2";
    private static final String EXPECTED_PATH_TO_GIT = "path to git";
    private static final String EXPECTED_JIRA_TASK = "TASK-123";
    private static final String EXPECTED_MESSAGE_WITH_JIRA_TASK = "TASK-123 Message 1";
    private static final String TEST_COMMIT_LOG = MESSAGE_1 + "\n" + MESSAGE_2;
    private static final String TEST_RAW_TAG_LIST = TAG_1 + "\n" + TAG_2;
    private static final String TEST_RAW_BRANCHES_LIST = BRANCH_1 + "\n" + BRANCH_2;

    private Git sut;

    @Mock
    private Shell shellMock;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        sut = new Git();
        sut.setShell(shellMock);
    }

    @Test
    public void testGetDifference() throws Exception {
        when(shellMock.executeCommand(anyString())).thenReturn(TEST_COMMIT_LOG);

        List<Commit> expectedCommitList = new ArrayList<Commit>();
        Commit commit1 = new Commit();
        commit1.setAuthor(EXPECTED_AUTHOR_1);
        commit1.setHash(EXPECTED_HASH_1);
        commit1.setMessage(EXPECTED_MESSAGE_1);
        expectedCommitList.add(commit1);

        Commit commit2 = new Commit();
        commit2.setAuthor(EXPECTED_AUTHOR_2);
        commit2.setHash(EXPECTED_HASH_2);
        commit2.setMessage(EXPECTED_MESSAGE_2);
        expectedCommitList.add(commit2);

        assertEquals(expectedCommitList, sut.getDifference(BRANCH_1, BRANCH_2));
    }

    @Test
    public void testGetReleaseTags() throws Exception {
        when(shellMock.executeCommand(anyString())).thenReturn(TEST_RAW_TAG_LIST);

        List<String> expectedTagList = new ArrayList<String>();
        expectedTagList.add(TAG_1);
        expectedTagList.add(TAG_2);

        assertEquals(expectedTagList, sut.getReleaseTags());
    }

    @Test
    public void testGetReleaseBranches() throws Exception {
        when(shellMock.executeCommand(anyString())).thenReturn(TEST_RAW_BRANCHES_LIST);

        List<String> expectedBranchesList = new ArrayList<String>();
        expectedBranchesList.add(BRANCH_1);
        expectedBranchesList.add(BRANCH_2);

        assertEquals(expectedBranchesList, sut.getReleaseBranches());
    }

    @Test
    public void testParseRawCommitMessage() throws Exception {
        Commit expectedCommit = new Commit();
        expectedCommit.setAuthor(EXPECTED_AUTHOR_1);
        expectedCommit.setHash(EXPECTED_HASH_1);
        expectedCommit.setMessage(EXPECTED_MESSAGE_1);

        assertEquals(expectedCommit, sut.parseRawCommitMessage(MESSAGE_1));
    }

    @Test
    public void testParseRawCommitMessageWithJiraMessage() throws Exception {
        Commit expectedCommit = new Commit();
        expectedCommit.setAuthor(EXPECTED_AUTHOR_1);
        expectedCommit.setHash(EXPECTED_HASH_1);
        expectedCommit.setMessage(EXPECTED_MESSAGE_WITH_JIRA_TASK);
        expectedCommit.setJiraTask(EXPECTED_JIRA_TASK);

        assertEquals(expectedCommit, sut.parseRawCommitMessage(MESSAGE_WITH_JIRA_TASK));
    }

    @Test
    public void testSetPathToGit() throws Exception {
        assertEquals(null, sut.getPathToGit());
        sut.setPathToGit(EXPECTED_PATH_TO_GIT);
        assertEquals(EXPECTED_PATH_TO_GIT, sut.getPathToGit());
    }

    @Test
    public void testGetShell() throws Exception {
        assertEquals(shellMock, sut.getShell());
    }
}