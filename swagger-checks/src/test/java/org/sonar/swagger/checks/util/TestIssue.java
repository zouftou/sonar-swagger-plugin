package org.sonar.swagger.checks.util;

import java.util.ArrayList;
import java.util.List;

public class TestIssue {

	private Integer startLine;
	private Integer startColumn;
	private Integer endLine;
	private Integer endColumn;
	private Double cost;
	private String message;
	private List<Integer> secondaryLines;

	public TestIssue(String message) {
		this.message = message;
		secondaryLines = new ArrayList<>();
	}

	public String message() {
		return message;
	}

	public Integer starLine() {
		return startLine;
	}

	public TestIssue starLine(int starLine) {
		this.startLine = starLine;
		return this;
	}

	public Integer endLine() {
		return endLine;
	}

	public TestIssue endLine(int endLine) {
		this.endLine = endLine;
		return this;
	}

	public Integer startColumn() {
		return startColumn;
	}

	public TestIssue startColumn(int startColumn) {
		this.startColumn = startColumn;
		return this;
	}

	public Integer endColumn() {
		return endColumn;
	}

	public TestIssue endColumn(int endColumn) {
		this.endColumn = endColumn;
		return this;
	}

	public TestIssue cost(double cost) {
		this.cost = cost;
		return this;
	}

	public Double cost() {
		return cost;
	}

	public List<Integer> secondaryLines() {
		return secondaryLines;
	}

	public TestIssue addSecondaryLine(int line) {
		secondaryLines.add(line);
		return this;
	}

}
