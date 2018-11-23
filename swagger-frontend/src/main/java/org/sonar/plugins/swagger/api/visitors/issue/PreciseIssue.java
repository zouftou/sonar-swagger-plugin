package org.sonar.plugins.swagger.api.visitors.issue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

import org.sonar.plugins.swagger.api.SwaggerCheck;
import org.sonar.plugins.swagger.api.tree.Tree;

public class PreciseIssue implements Issue {

	private SwaggerCheck check;
	private Double cost;
	private IssueLocation primaryLocation;
	private List<IssueLocation> secondaryLocations;

	public PreciseIssue(SwaggerCheck check, IssueLocation primaryLocation) {
		this.check = check;
		this.primaryLocation = primaryLocation;
		this.secondaryLocations = new ArrayList<>();
		this.cost = null;
	}

	@Override
	public SwaggerCheck check() {
		return check;
	}

	@Nullable
	@Override
	public Double cost() {
		return cost;
	}

	@Override
	public PreciseIssue cost(double cost) {
		this.cost = cost;
		return this;
	}

	public IssueLocation primaryLocation() {
		return primaryLocation;
	}

	public List<IssueLocation> secondaryLocations() {
		return secondaryLocations;
	}

	public PreciseIssue secondary(Tree tree, String message) {
		secondaryLocations.add(new IssueLocation(primaryLocation.file(), tree, message));
		return this;
	}

	public PreciseIssue secondary(File file, Tree tree, String message) {
		secondaryLocations.add(new IssueLocation(file, tree, message));
		return this;
	}

}
