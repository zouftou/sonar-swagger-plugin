package org.sonar.plugins.swagger;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.rule.Checks;
import org.sonar.api.rule.RuleKey;
import org.sonar.plugins.swagger.api.CustomSwaggerRulesDefinition;
import org.sonar.plugins.swagger.api.SwaggerCheck;
import org.sonar.plugins.swagger.api.visitors.TreeVisitor;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class SwaggerChecks {

  private final CheckFactory checkFactory;
  private Set<Checks<SwaggerCheck>> checksByRepository = Sets.newHashSet();

  private SwaggerChecks(CheckFactory checkFactory) {
    this.checkFactory = checkFactory;
  }

  public static SwaggerChecks createSwaggerCheck(CheckFactory checkFactory) {
    return new SwaggerChecks(checkFactory);
  }

  public SwaggerChecks addChecks(String repositoryKey, Iterable<Class> checkClass) {
    checksByRepository.add(checkFactory
      .<SwaggerCheck>create(repositoryKey)
      .addAnnotatedChecks(checkClass));

    return this;
  }

  public SwaggerChecks addCustomChecks(@Nullable CustomSwaggerRulesDefinition[] customRulesDefinitions) {
	    if (customRulesDefinitions != null) {

	      for (CustomSwaggerRulesDefinition rulesDefinition : customRulesDefinitions) {
	        addChecks(rulesDefinition.repositoryKey(), Lists.newArrayList(rulesDefinition.checkClasses()));
	      }
	    }
	    return this;
	  }
  
  public List<SwaggerCheck> all() {
    List<SwaggerCheck> allVisitors = Lists.newArrayList();

    for (Checks<SwaggerCheck> checks : checksByRepository) {
      allVisitors.addAll(checks.all());
    }

    return allVisitors;
  }

  public List<TreeVisitor> visitorChecks() {
    List<TreeVisitor> checks = new ArrayList<>();
    for (SwaggerCheck check : all()) {
      if (check instanceof TreeVisitor) {
        checks.add((TreeVisitor) check);
      }
    }

    return checks;
  }

  @Nullable
  public RuleKey ruleKeyFor(SwaggerCheck check) {
    RuleKey ruleKey;

    for (Checks<SwaggerCheck> checks : checksByRepository) {
      ruleKey = checks.ruleKey(check);

      if (ruleKey != null) {
        return ruleKey;
      }
    }
    return null;
  }

}
