package org.sonar.plugins.swagger.api;

import com.google.common.collect.ImmutableList;
import org.sonar.api.ExtensionPoint;
import org.sonar.api.batch.BatchSide;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.squidbridge.annotations.AnnotationBasedRulesDefinition;

/**
 * Extension point to create custom rule repository for JSON.
 */
@ExtensionPoint
@BatchSide
public abstract class CustomSwaggerRulesDefinition implements RulesDefinition {

  /**
   * Defines rule repository with check metadata from check classes' annotations.
   * This method should be overridden if check metadata are provided via another format,
   * e.g: XMl, JSON.
   */
  @Override
  public void define(Context context) {
    NewRepository repo = context.createRepository(repositoryKey(), "swagger").setName(repositoryName());
    new AnnotationBasedRulesDefinition(repo, "swagger").addRuleClasses(false, ImmutableList.copyOf(checkClasses()));
    repo.done();
  }

  /**
   * Name of the custom rule repository.
   */
  public abstract String repositoryName();

  /**
   * Key of the custom rule repository.
   */
  public abstract String repositoryKey();

  /**
   * Array of the custom rules classes.
   */
  public abstract Class[] checkClasses();
}
