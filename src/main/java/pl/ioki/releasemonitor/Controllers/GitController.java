package pl.ioki.releasemonitor.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.ioki.releasemonitor.Exceptions.DataNotReadyYetException;
import pl.ioki.releasemonitor.Jobs.GitJobs;
import pl.ioki.releasemonitor.Models.BranchDiff;
import pl.ioki.releasemonitor.Models.Commit;
import pl.ioki.releasemonitor.Services.Git;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("git")
public class GitController {

    @Autowired
    private Git git;

    @Autowired
    private GitJobs gitJobs;

    @Value("${git.directory}")
    private String gitPath;

    @PostConstruct
    private void init() {
        // TODO move this to parameters and set relative to app location or from commandline parameters
        gitJobs.getGit().setPathToGit(gitPath);
    }

    @RequestMapping("commits")
    public List<Commit> getCommits(
            @RequestParam(required = true, value = "sourceBranch") String sourceBranch,
            @RequestParam(required = true, value = "destinationBranch") String destinationBranch

    ) {
        BranchDiff branchDiff = gitJobs.getBranchDifference(sourceBranch, destinationBranch);
        if (branchDiff != null) {
            return branchDiff.getCommitList();
        }

        // If there is no result, enqueue branch to fetch and throw exception
        gitJobs.enqueueBranch(sourceBranch, destinationBranch);
        throw new DataNotReadyYetException();
    }

    @RequestMapping("release-tags")
    public List<String> getReleaseTags() {

        return gitJobs.getReleaseTagList();
    }

    @RequestMapping("release-branches")
    public List<String> getReleaseBranches() {

        return gitJobs.getReleaseBranchesList();
    }
}
