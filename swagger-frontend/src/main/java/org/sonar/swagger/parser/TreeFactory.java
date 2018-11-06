package org.sonar.swagger.parser;

import com.google.common.collect.Lists;
import com.sonar.sslr.api.typed.Optional;
import org.sonar.swagger.tree.impl.*;
import org.sonar.plugins.swagger.api.tree.*;

import java.util.List;

public class TreeFactory {
	
  public SwaggerTree swagger(Optional<SyntaxToken> byteOrderMark, Optional<SeparatedList<ValueTree>> values, SyntaxToken eof) {
	    return new SwaggerTreeImpl(byteOrderMark.orNull(), values.orNull(), eof);
  }

  public ObjectTree object(KeyTree key, InternalSyntaxToken colon,InternalSyntaxToken newLine, SeparatedList<PairTree> pairs) {
    return new ObjectTreeImpl(key, colon,newLine, pairs);
  }

  public ArrayTree array(KeyTree key, InternalSyntaxToken colon, InternalSyntaxToken newLine, List<ArrayEntryTree> values) {
    return new ArrayTreeImpl(key, colon, newLine, values);
  }

  public ArrayEntryTree arrayEntry(SyntaxToken minus, SyntaxToken whitespace,ValueTree value) {
	  return new ArrayEntryTreeImpl(minus, whitespace, value);
  }
  
  public PairTree pair(SyntaxToken indentation, KeyTree key, SyntaxToken colon, SyntaxToken whitespace, ValueTree value) {
    return new PairTreeImpl(indentation, key, colon, whitespace, value);
  }
  
  public SimplePairTree simplePair(KeyTree key, SyntaxToken colon, SyntaxToken whitespace, LiteralTree value) {
    return new SimplePairTreeImpl(key, colon, whitespace, value);
  }
  
  public KeyTree key(SyntaxToken key) {
    return new KeyTreeImpl(key);
  }

  public ValueTree value(Tree value) {
    return new ValueTreeImpl(value);
  }

  public SeparatedList<ArrayEntryTree> entryList(ArrayEntryTree entry, Optional<List<Tuple<InternalSyntaxToken, ArrayEntryTree>>> subsequentValues) {
    List<ArrayEntryTree> values = Lists.newArrayList();
    List<InternalSyntaxToken> newLines = Lists.newArrayList();

    values.add(entry);

    if (subsequentValues.isPresent()) {
      for (Tuple<InternalSyntaxToken, ArrayEntryTree> t : subsequentValues.get()) {
    	newLines.add(t.first());
        values.add(t.second());
      }
    }

    return new SeparatedList<>(values, newLines);
  }
  
  public SeparatedList<ValueTree> valueList(ValueTree entry, Optional<List<Tuple<InternalSyntaxToken, ValueTree>>> subsequentValues) {
    List<ValueTree> values = Lists.newArrayList();
    List<InternalSyntaxToken> newLines = Lists.newArrayList();

    values.add(entry);

    if (subsequentValues.isPresent()) {
      for (Tuple<InternalSyntaxToken, ValueTree> t : subsequentValues.get()) {
    	newLines.add(t.first());
        values.add(t.second());
      }
    }

    return new SeparatedList<>(values, newLines);
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
