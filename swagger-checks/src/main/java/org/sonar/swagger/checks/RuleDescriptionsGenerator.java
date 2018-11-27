package org.sonar.swagger.checks;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;

public class RuleDescriptionsGenerator {

	public void generateHtmlRuleDescription(String templatePath, String outputPath) throws IOException {
		try (OutputStream fileOutputStream = new FileOutputStream(outputPath)) {
			Writer writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream, Charsets.UTF_8));
			writer.flush();
			writer.close();
		} catch (IOException e) {
			throw new IllegalStateException("Could not generate the HTML description.", e);
		}
	}

	private String generateHtmlTable(List<String> elements) {
		StringBuilder html = new StringBuilder("<table style=\"border: 0;\">");
		List<List<String>> subLists = Lists.partition(elements, 3);
		for (List<String> subList : subLists) {
			html.append("<tr>");
			for (String element : subList) {
				html.append("<td style=\"border: 0;\">").append(element).append("</td>");
			}
			html.append("</tr>");
		}
		html.append("</table>");
		return html.toString();
	}
	
}
