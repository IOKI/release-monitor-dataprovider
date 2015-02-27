package pl.ioki.releasemonitor.Jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.ioki.releasemonitor.Models.BranchDiff;
import pl.ioki.releasemonitor.Models.Commit;
import pl.ioki.releasemonitor.Services.Git;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GitJobs {

    @Autowired
    private Git git;

    private Map<String, BranchDiff> branchesList = new ConcurrentHashMap<String, BranchDiff>();
    private List<String> releaseTagList = new ArrayList<String>();
    private List<String> releaseBranchesList = new ArrayList<String>();

    @Scheduled(fixedDelay = 60000)
    public void getCommitsForBranches() {
        for(BranchDiff branch : branchesList.values()) {
            List<Commit> commitList = git.getDifference(branch.getSource(), branch.getTarget());

            branch.setCommitList(commitList);
            branch.setReady(true);
        }
    }

    @Scheduled(fixedDelay = 60000)
    public void getReleaseTags() {
        releaseTagList = git.getReleaseTags();
    }

    @Scheduled(fixedDelay = 60000)
    public void getReleaseBranches() {
        releaseBranchesList = git.getReleaseBranches();
    }

    public void enqueueBranch(String sourceBranch, String destinationBranch) {
        BranchDiff branchDiff = new BranchDiff(sourceBranch, destinationBranch);
        String key = branchDiff.key();

        if (!branchesList.containsKey(key)) {
            branchesList.put(key, branchDiff);
        }
    }

    public BranchDiff getBranchDifference(String sourceBranch, String destinationBranch) {
        BranchDiff branchDiff = new BranchDiff(sourceBranch, destinationBranch);
        branchDiff = branchesList.get(branchDiff.key());

        if (branchDiff == null || !branchDiff.isReady()) {
            return null;
        }

        return branchDiff;
    }

    public List<String> getReleaseTagList() {
        return releaseTagList;
    }

    public void setReleaseTagList(List<String> releaseTagList) {
        this.releaseTagList = releaseTagList;
    }

    public List<String> getReleaseBranchesList() {
        return releaseBranchesList;
    }

    public void setReleaseBranchesList(List<String> releaseBranchesList) {
        this.releaseBranchesList = releaseBranchesList;
    }

    public Git getGit() {
        return git;
    }
}
