package org.sonar.swagger.checks.generic;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.swagger.api.tree.SwaggerTree;
import org.sonar.plugins.swagger.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.swagger.checks.Tags;
import org.sonar.swagger.visitors.CharsetAwareVisitor;

import com.google.common.io.Files;

@Rule(key = "tab-character", name = "Tabulation characters should not be used", priority = Priority.MINOR, tags = {
		Tags.CONVENTION })
@ActivatedByDefault
@SqaleConstantRemediation("2min")
public class TabCharacterCheck extends DoubleDispatchVisitorCheck implements CharsetAwareVisitor {

	private Charset charset;

	@Override
	public void setCharset(Charset charset) {
		this.charset = charset;
	}

	@Override
	public void visitSwagger(SwaggerTree tree) {
		List<String> lines;
		try {
			lines = Files.readLines(getContext().getFile(), charset);
		} catch (IOException e) {
			throw new IllegalStateException("Check json:" + this.getClass().getAnnotation(Rule.class).key()
					+ ": Error while reading " + getContext().getFile().getName(), e);
		}
		for (String line : lines) {
			if (line.contains("\t")) {
				addFileIssue("Replace all tab characters in this file by sequences of whitespaces.");
				break;
			}
		}
		super.visitSwagger(tree);
	}
	
}
