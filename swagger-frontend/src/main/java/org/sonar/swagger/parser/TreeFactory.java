package org.sonar.swagger.parser;

import com.google.common.collect.Lists;
import com.sonar.sslr.api.typed.Optional;
import org.sonar.swagger.tree.impl.*;
import org.sonar.plugins.swagger.api.tree.*;

import java.util.List;

public class TreeFactory {

  public SwaggerTree json(Optional<SyntaxToken> byteOrderMark, Optional<ValueTree> value, SyntaxToken eof) {
    return new SwaggerTreeImpl(byteOrderMark.orNull(), value.orNull(), eof);
  }

  public ObjectTree object(InternalSyntaxToken leftSpace, Optional<SeparatedList<PairTree>> pairs) {
    return new ObjectTreeImpl(leftSpace, pairs.orNull());
  }

  public ArrayTree array(InternalSyntaxToken leftSpace, Optional<SeparatedList<ValueTree>> values) {
    return new ArrayTreeImpl(leftSpace, values.orNull());
  }

  public PairTree pair(KeyTree key, SyntaxToken colon, ValueTree value) {
    return new PairTreeImpl(key, colon, value);
  }

  public KeyTree key(SyntaxToken key) {
    return new KeyTreeImpl(key);
  }

  public ValueTree value(Tree value) {
    return new ValueTreeImpl(value);
  }

  public SeparatedList<ValueTree> valueList(ValueTree value, Optional<List<Tuple<InternalSyntaxToken, ValueTree>>> subsequentValues) {
    List<ValueTree> values = Lists.newArrayList();
    List<InternalSyntaxToken> commas = Lists.newArrayList();

    values.add(value);

    if (subsequentValues.isPresent()) {
      for (Tuple<InternalSyntaxToken, ValueTree> t : subsequentValues.get()) {
        commas.add(t.first());
        values.add(t.second());
      }
    }

    return new SeparatedList<>(values, commas);
  }

  public SeparatedList<PairTree> pairList(PairTree pair, Optional<List<Tuple<InternalSyntaxToken, PairTree>>> subsequentPairs) {
    List<PairTree> pairs = Lists.newArrayList();
    List<InternalSyntaxToken> commas = Lists.newArrayList();

    pairs.add(pair);

    if (subsequentPairs.isPresent()) {
      for (Tuple<InternalSyntaxToken, PairTree> t : subsequentPairs.get()) {
        commas.add(t.first());
        pairs.add(t.second());
      }
    }

    return new SeparatedList<>(pairs, commas);
  }

  public StringTree string(SyntaxToken token) {
    return new StringTreeImpl(token);
  }

  public LiteralTree number(SyntaxToken token) {
    return new NumberTreeImpl(token);
  }

  public LiteralTree falsee(SyntaxToken token) {
    return new FalseTreeImpl(token);
  }

  public LiteralTree truee(SyntaxToken token) {
    return new TrueTreeImpl(token);
  }

  public LiteralTree nul(SyntaxToken token) {
    return new NullTreeImpl(token);
  }

  public static class Tuple<T, U> {

    private final T first;
    private final U second;

    public Tuple(T first, U second) {
      super();

      this.first = first;
      this.second = second;
    }

    public T first() {
      return first;
    }

    public U second() {
      return second;
    }
  }

  private static <T, U> Tuple<T, U> newTuple(T first, U second) {
    return new Tuple<>(first, second);
  }

  public <T, U> Tuple<T, U> newTuple1(T first, U second) {
    return newTuple(first, second);
  }

  public <T, U> Tuple<T, U> newTuple2(T first, U second) {
    return newTuple(first, second);
  }
}
