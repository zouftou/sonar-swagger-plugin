package org.sonar.plugins.swagger.api.visitors.issue;

import com.google.common.base.Preconditions;

import java.io.File;
import javax.annotation.Nullable;

import org.sonar.plugins.swagger.api.SwaggerCheck;

public class LineIssue implements Issue {

	private final SwaggerCheck check;
	private final File file;
	private Double cost;
	private final String message;
	private final int line;

	public LineIssue(SwaggerCheck check, File file, int line, String message) {
		Preconditions.checkArgument(line > 0);
		this.check = check;
		this.file = file;
		this.message = message;
		this.line = line;
		this.cost = null;
	}

	public String message() {
		return message;
	}

	public int line() {
		return line;
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

}
