package com.pandawork.core.search.utils;

import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;

public final class ScopedAnalyzer extends Analyzer implements Cloneable {
	private Analyzer globalAnalyzer;
	private Map<String, Analyzer> scopedAnalyzers = new HashMap<String, Analyzer>();

	public ScopedAnalyzer() {
	}

	private ScopedAnalyzer( Analyzer globalAnalyzer, Map<String, Analyzer> scopedAnalyzers) {
		this.globalAnalyzer = globalAnalyzer;
		for ( Map.Entry<String, Analyzer> entry : scopedAnalyzers.entrySet() ) {
			addScopedAnalyzer( entry.getKey(), entry.getValue() );
		}
	}

	public void setGlobalAnalyzer( Analyzer globalAnalyzer ) {
		this.globalAnalyzer = globalAnalyzer;
	}

	public void addScopedAnalyzer( String scope, Analyzer scopedAnalyzer ) {
		scopedAnalyzers.put(scope, scopedAnalyzer);
	}

	public TokenStream tokenStream( String fieldName, Reader reader ) {
		return getAnalyzer(fieldName).tokenStream(fieldName, reader);
	}

	public int getPositionIncrementGap( String fieldName ) {
		return getAnalyzer(fieldName).getPositionIncrementGap(fieldName);
	}

	private Analyzer getAnalyzer( String fieldName ) {
		Analyzer analyzer = scopedAnalyzers.get(fieldName);
		if ( analyzer == null ) {
			analyzer = globalAnalyzer;
		}
		return analyzer;
	}

	public ScopedAnalyzer clone() {
		ScopedAnalyzer clone = new ScopedAnalyzer( globalAnalyzer, scopedAnalyzers );
		return clone;
	}
}
