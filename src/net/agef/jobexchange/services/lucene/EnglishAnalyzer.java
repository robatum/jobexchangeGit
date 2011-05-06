package net.agef.jobexchange.services.lucene;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.snowball.SnowballFilter;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;

public class EnglishAnalyzer extends Analyzer {
    /**
     * {@inheritDoc}
     */
    @Override
    public TokenStream tokenStream(String fieldName, Reader reader) {
        TokenStream result = new StandardTokenizer(null, reader);
        result = new StandardFilter(result);
        result = new LowerCaseFilter(result);
        result = new SnowballFilter(result, fieldName);
        return result;
    }

}

