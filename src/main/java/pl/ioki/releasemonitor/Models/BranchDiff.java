package pl.ioki.releasemonitor.Models;

import java.util.List;
import java.util.Objects;

public class BranchDiff {
    private String source;
    private String target;
    private List<Commit> commitList;
    private boolean ready = false;

    public BranchDiff(String source, String target, List<Commit> commitList) {
        this.source = source;
        this.target = target;
        this.commitList = commitList;
    }

    public BranchDiff(String source, String target) {
        this.source = source;
        this.target = target;
    }


    public String key() {
        return String.format("%s-%s", source, target);
    }


    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public List<Commit> getCommitList() {
        return commitList;
    }

    public void setCommitList(List<Commit> commitList) {
        this.commitList = commitList;
    }

    @Override
    public String toString() {
        return String.format("BranchDiff{source='%s', target='%s', commitList=%s}", source, target, commitList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, target);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final BranchDiff other = (BranchDiff) obj;
        return Objects.equals(this.source, other.source) && Objects.equals(this.target, other.target);
    }
}
