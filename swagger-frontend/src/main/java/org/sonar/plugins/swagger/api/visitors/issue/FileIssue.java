package org.sonar.plugins.swagger.api.visitors.issue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

import org.sonar.plugins.swagger.api.SwaggerCheck;
import org.sonar.plugins.swagger.api.tree.Tree;

public class FileIssue implements Issue {

	private final SwaggerCheck check;
	private final File file;
	private Double cost;
	private final String message;
	private final List<IssueLocation> secondaryLocations;

	public FileIssue(SwaggerCheck check, File file, String message) {
		this.check = check;
		this.file = file;
		this.message = message;
		this.secondaryLocations = new ArrayList<>();
		this.cost = null;
	}

	public String message() {
		return message;
	}

	@Override
	public SwaggerCheck check() {
		return check;
	}

	public File file() {
		return file;
	}

	@Nullable
	@Override
	public Double cost() {
		return cost;
	}

	@Override
	public Issue cost(double cost) {
		this.cost = cost;
		return this;
	}

	public List<IssueLocation> secondaryLocations() {
		return secondaryLocations;
	}

	public FileIssue secondary(Tree tree, String message) {
		secondaryLocations.add(new IssueLocation(file, tree, message));
		return this;
	}

	public FileIssue secondary(File file, Tree tree, String message) {
		secondaryLocations.add(new IssueLocation(file, tree, message));
		return this;
	}

}
