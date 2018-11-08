package org.sonar.swagger.parser;

import com.google.common.collect.Lists;
import com.sonar.sslr.api.typed.Optional;
import org.sonar.swagger.tree.impl.*;
import org.sonar.plugins.swagger.api.tree.*;

import java.util.List;

public class TreeFactory {
  
  public SwaggerTree swagger(Optional<SyntaxToken> byteOrderMark, Optional<SeparatedList<PairTree>> pairs, SyntaxToken eof) {
    return new SwaggerTreeImpl(byteOrderMark.orNull(), pairs.orNull(), eof);
  }

  public ObjectTree object(ObjectEntryTree entry, Optional<List<Tuple<InternalSyntaxToken, ObjectEntryTree>>> subsequentEntries) {
    List<ObjectEntryTree> entries = Lists.newArrayList();
    List<InternalSyntaxToken> indentations = Lists.newArrayList();

    entries.add(entry);

    if (subsequentEntries.isPresent()) {
      for (Tuple<InternalSyntaxToken, ObjectEntryTree> t : subsequentEntries.get()) {
    	indentations.add(t.first());
        entries.add(t.second());
      }
    }

    return new ObjectTreeImpl(new SeparatedList<>(entries, indentations));
  }
  
  public ObjectEntryTree objectEntry(SyntaxToken indentation, PairTree pair) {
    return new ObjectEntryTreeImpl(indentation, pair);
  }

  public ArrayTree array(ArrayEntryTree entry, Optional<List<Tuple<InternalSyntaxToken, ArrayEntryTree>>> subsequentEntries) {
    List<ArrayEntryTree> entries = Lists.newArrayList();
    List<InternalSyntaxToken> newLines = Lists.newArrayList();

    entries.add(entry);

    if (subsequentEntries.isPresent()) {
      for (Tuple<InternalSyntaxToken, ArrayEntryTree> t : subsequentEntries.get()) {
    	newLines.add(t.first());
        entries.add(t.second());
      }
    }

    return new ArrayTreeImpl(new SeparatedList<>(entries, newLines));
  }
  
  public ArrayEntryTree arrayEntry(SyntaxToken minus, SyntaxToken space, ValueTree value) {
    return new ArrayEntryTreeImpl(minus, space, value);
  }
  
  public PairTree pair(KeyTree key, SyntaxToken colon, SyntaxToken lineOrSpace, ValueTree value) {
    return new PairTreeImpl(key, colon, lineOrSpace, value);
  }

  public KeyTree key(SyntaxToken key) {
    return new KeyTreeImpl(key);
  }

  public ValueTree value(Tree value) {
    return new ValueTreeImpl(value);
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
